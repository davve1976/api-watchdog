export default function MonitorTable({ history }) {
  return (
    <table border="1" cellPadding="8" style={{ marginTop: 30 }}>
      <thead>
        <tr>
          <th>Tid</th>
          <th>URL</th>
          <th>Status</th>
          <th>Responstid (ms)</th>
          <th>Fel</th>
        </tr>
      </thead>
      <tbody>
        {history.map((h) => (
          <tr key={h.id}>
            <td>{h.timestamp}</td>
            <td>{h.url}</td>
            <td>{h.status}</td>
            <td>{h.responseTimeMs}</td>
            <td>{h.error || "-"}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
