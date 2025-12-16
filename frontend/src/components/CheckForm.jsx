import { useState } from "react";

export default function CheckForm({ onCheck }) {
    const [url, setUrl] = useState("");

    return (
        <div className="check-form">
            <input
                type="text"
                placeholder="Enter URL to check"
                value={url}
                onChange={(e) => setUrl(e.target.value)}
            />
            <button onClick={() => onCheck(url)}>Run Check</button>
        </div>
    );
}
