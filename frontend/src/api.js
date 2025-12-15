const API_KEY = "changeme123";

export async function apiCheck(url) {
  const response = await fetch(`/api/check?url=${encodeURIComponent(url)}`, {
    headers: {
      "X-API-Key": API_KEY,
    },
  });
  return response.json();
}

export async function fetchHistory() {
  const res = await fetch(`/api/history`, {
    headers: { "X-API-Key": API_KEY },
  });
  return res.json();
}
