import { useState } from "react";
import { api } from "../api";

export default function CheckForm({ onChecked }) {
  const [url, setUrl] = useState("");
  const [result, setResult] = useState(null);

  const runCheck = async () => {
    if (!url) return;
    const res = await api.get("/api/check?url=" + url);
    setResult(res.data);
    onChecked();
  };

  return (
    <div className="card">
      <h2>Run a new check</h2>

      <input
        value={url}
        onChange={(e) => setUrl(e.target.value)}
        placeholder="https://example.com"
      />

      <button onClick={runCheck}>Check now</button>

      {result && (
        <div className="result">
          Status: {result.status} — Time: {result.responseTimeMs} ms
        </div>
      )}
    </div>
  );
}
