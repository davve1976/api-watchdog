import { LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid } from "recharts";

export default function ResponseChart({ data }) {
  return (
    <LineChart width={800} height={300} data={data}>
      <CartesianGrid stroke="#ccc" />
      <XAxis dataKey="timestamp" hide />
      <YAxis />
      <Tooltip />
      <Line type="monotone" dataKey="responseTimeMs" stroke="#8884d8" />
    </LineChart>
  );
}
