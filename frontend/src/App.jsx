import { useEffect, useState } from "react";
import { apiCheck, fetchHistory } from "./api.js";
import CheckForm from "./components/CheckForm.jsx";
import HistoryTable from "./components/HistoryTable.jsx";
import ResponseChart from "./components/ResponseChart.jsx";

export default function App() {
    const [history, setHistory] = useState([]);
    const [loading, setLoading] = useState(true);

    async function loadHistory() {
        setLoading(true);
        const data = await fetchHistory();
        setHistory(data);
        setLoading(false);
    }

    async function handleCheck(url) {
        await apiCheck(url);
        await loadHistory();
    }

    useEffect(() => {
        loadHistory();
    }, []);

    return (
        <div className="container">
            <h1>API Watchdog Dashboard</h1>

            <CheckForm onCheck={handleCheck} />

            <h2>Recent Checks</h2>
            {loading ? (
                <p>Loading...</p>
            ) : (
                <>
                    <ResponseChart history={history} />
                    <HistoryTable history={history} />
                </>
            )}
        </div>
    );
}
