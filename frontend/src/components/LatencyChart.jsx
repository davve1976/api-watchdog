import { Line } from "react-chartjs-2";

export default function LatencyChart({ data }) {
  const chartData = {
    labels: data.map((d) => d.timestamp),
    datasets: [
      {
        label: "Latency (ms)",
        data: data.map((d) => d.responseTimeMs),
        borderColor: "#4e73df",
        borderWidth: 2
      }
    ]
  };

  return (
    <div className="card">
      <h2>Latency Over Time</h2>
      <Line data={chartData} />
    </div>
  );
}
