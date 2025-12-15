import { useEffect, useState } from "react";
import { apiCheck, fetchHistory } from "./api.js";
import StatusCard from "./components/StatusCard.jsx";
import MonitorTable from "./components/MonitorTable.jsx";
import ResponseChart from "./components/ResponseChart.jsx";

export default function App() {
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(false);

  async function refreshHistory() {
    const data = await fetchHistory();
    setHistory(data);
  }

  useEffect(() => {
    refreshHistory();
  }, []);

  async function runCheck(url) {
    setLoading(true);
    await apiCheck(url);
    await refreshHistory();
    setLoading(false);
  }

  return (
    <div style={{ padding: 30, fontFamily: "Arial" }}>
      <h1>API Watchdog Dashboard</h1>

      <StatusCard history={history} />

      <button onClick={() => runCheck("https://google.com")} disabled={loading}>
        Testa Google
      </button>

      <MonitorTable history={history} />

      <h2>Responstid över tid</h2>
      <ResponseChart data={history} />
    </div>
  );
}
