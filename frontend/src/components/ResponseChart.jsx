import { LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid } from "recharts";

export default function ResponseChart({ history }) {
    return (
        <LineChart width={700} height={300} data={history}>
            <CartesianGrid stroke="#ccc" />
            <XAxis dataKey="timestamp" hide />
            <YAxis />
            <Tooltip />
            <Line type="monotone" dataKey="responseTimeMs" stroke="#8884d8" />
        </LineChart>
    );
}
