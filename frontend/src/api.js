// Byt API-bas beroende på miljö
export const API_BASE = import.meta.env.PROD
    ? "https://api-watchdog-production-1893.up.railway.app"
    : "http://localhost:8080";

// Om du vill behålla default API-KEY
const API_KEY = "changeme123";

// =======================
// Offentlig check (ingen API-key)
// =======================
export async function apiCheck(url) {
    const response = await fetch(`${API_BASE}/api/check?url=${encodeURIComponent(url)}`);
    return response.json();
}

// =======================
// Säker check (med API-key)
// =======================
export async function secureCheck(url, apiKey = API_KEY) {
    const response = await fetch(`${API_BASE}/api/check?url=${encodeURIComponent(url)}`, {
        headers: {
            "X-API-Key": apiKey,
        },
    });
    return response.json();
}

// =======================
// Hämta historik
// =======================
export async function fetchHistory() {
    const res = await fetch(`${API_BASE}/api/history`, {
        headers: { "X-API-Key": API_KEY },
    });
    return res.json();
}
