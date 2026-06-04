import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 50 },  // ramp-up: 0 → 50 usuarios en 10s
        { duration: '30s', target: 50 },  // carga sostenida: 50 usuarios por 30s
        { duration: '10s', target: 0 },   // ramp-down: 50 → 0 usuarios en 10s
    ],
    thresholds: {
        http_req_duration: ['p(95)<500'],  // El 95% de las request deben completarse antes de los 500ms
        http_req_failed: ['rate<0.01'],    // Agrego ratio de tolerancia de 1% de errores
    },
};

const BASE_URL = 'http://localhost:8080';
const MAX_POLL_ATTEMPTS = 10;
const POLL_INTERVAL = 0.5;

// Polling a GET /count hasta que el evento de Kafka sea procesado y persistido (para evitar sleep)
function waitForCount(searchId, expectedMinCount) {
    let count = 0;
    let attempts = 0;

    while (count < expectedMinCount && attempts < MAX_POLL_ATTEMPTS) {
        sleep(POLL_INTERVAL);
        const response = http.get(`${BASE_URL}/api/v1/hotels/count?searchId=${searchId}`);
        if (response.status === 200) {
            count = JSON.parse(response.body).count;
        }
        attempts++;
    }

    return count;
}

export default function () {

    // POST /search
    const payload = JSON.stringify({
        hotelId: '1234aBc',
        checkIn: '29/12/2027',
        checkOut: '31/12/2027',
        ages: [30, 29, 1, 3],
    });

    const headers = { 'Content-Type': 'application/json' };

    const postResponse = http.post(`${BASE_URL}/api/v1/hotels/search`, payload, { headers });

    const postOk = check(postResponse, {
        'POST /search status is 200': (r) => r.status === 200,
        'POST /search returns searchId': (r) => JSON.parse(r.body).searchId !== undefined,
    });

    if (!postOk) return;

    const searchId = JSON.parse(postResponse.body).searchId;

    const count = waitForCount(searchId, 1);

    check({ count }, {
        'GET /count returns count >= 1': (c) => c.count >= 1,
    });
}