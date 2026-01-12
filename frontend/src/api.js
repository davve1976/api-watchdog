// Always call backend through the same server the frontend is served from
// Reverse proxy in NGINX or Railway handles /api → backend
const API_BASE_URL = import.meta.env.VITE_API_BACKEND_URL || "http://localhost:8080/api";

const API_BASE = API_BASE_URL + "/api";

const API_KEY = "changeme123";

export async function apiCheck(url) {
	let fixedUrl = url.trim();

	if (!fixedUrl.startsWith("http://") && !fixedUrl.startsWith("https://")) {
	  fixedUrl = "https://" + fixedUrl;
	}

    const response = await fetch(`${API_BASE}/public/check?url=${encodeURIComponent(fixedUrl)}`, {
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