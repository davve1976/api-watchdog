const API_BASE = import.meta.env.VITE_API_BASE;
const API_KEY = import.meta.env.VITE_API_KEY;

export async function apiCheck(url) {
    const response = await fetch(`${API_BASE}/api/check?url=${encodeURIComponent(url)}`, {
        headers: {
            "X-API-Key": API_KEY,
        },
    });
    return response.json();
}

export async function fetchHistory() {
    const res = await fetch(`${API_BASE}/api/history`, {
        headers: {
            "X-API-Key": API_KEY,
        },
    });
    return res.json();
}
