export default function HistoryTable({ data }) {
  return (
    <div className="card">
      <h2>History</h2>
      <table>
        <thead>
          <tr>
            <th>URL</th>
            <th>Status</th>
            <th>Latency</th>
            <th>Timestamp</th>
            <th>Error</th>
          </tr>
        </thead>
        <tbody>
          {data.map((r) => (
            <tr key={r.id}>
              <td>{r.url}</td>
              <td>
                <span className={r.status === 0 ? "down" : "up"}>
                  {r.status}
                </span>
              </td>
              <td>{r.responseTimeMs} ms</td>
              <td>{r.timestamp}</td>
              <td>{r.error || "-"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
