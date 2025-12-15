export default function StatusCard({ history }) {
  if (!history.length) return <p>Ingen data än.</p>;

  const last = history[0];

  return (
    <div style={{
      padding: 20, 
      marginBottom: 20,
      borderRadius: 10,
      background: last.status === 0 ? "#ffcccc" : "#ccffcc"
    }}>
      <h3>Senaste status för: {last.url}</h3>
      <p>Statuskod: {last.status}</p>
      <p>Responstid: {last.responseTimeMs} ms</p>
      {last.error && <p>Fel: {last.error}</p>}
    </div>
  );
}
