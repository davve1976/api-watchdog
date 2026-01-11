// Always call backend through the same server the frontend is served from
// Reverse proxy in NGINX or Railway handles /api → backend
const API_BASE = "/api";

const API_KEY = "changeme123";

export async function apiCheck(url) {
    const response = await fetch(`${API_BASE}/public/check?url=${encodeURIComponent(url)}`, {
        headers: {
            "X-API-Key": API_KEY,
        },
    });
    return response.json();
}

export async function fetchHistory() {
    const res = await fetch(`${API_BASE}/history`, {
        headers: {
            "X-API-Key": API_KEY,
        },
    });
    return res.json();
}
