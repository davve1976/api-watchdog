export default function HistoryTable({ history }) {
    return (
        <table>
            <thead>
                <tr>
                    <th>URL</th>
                    <th>Status</th>
                    <th>Response Time (ms)</th>
                    <th>Error</th>
                    <th>Timestamp</th>
                </tr>
            </thead>
            <tbody>
                {history.map((item) => (
                    <tr key={item.id}>
                        <td>{item.url}</td>
                        <td>{item.status}</td>
                        <td>{item.responseTimeMs}</td>
                        <td>{item.error || "-"}</td>
                        <td>{item.timestamp}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
}
