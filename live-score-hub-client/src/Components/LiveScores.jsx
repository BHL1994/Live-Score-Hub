import React, { useEffect, useState } from 'react';

const LiveScores = () => {
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    const ws = new WebSocket('ws://localhost:8080/live-scores');

    ws.onopen = () => {
      console.log('WebSocket connection opened');
    };

    ws.onerror = (error) => {
        console.error('WebSocket Error:', error);
      };

    ws.onmessage = (event) => {
      console.log('Message received:', event.data);
      setMessages((prevMessages) => [...prevMessages, event.data]);
    };

    ws.onclose = () => {
      console.log('WebSocket connection closed');
    };

    return () => {
      ws.close();
    };
  }, []);

  return (
    <div>
      <h1>Live Scores</h1>
      <ul>
        {messages.map((message, index) => (
          <li key={index}>{message}</li>
        ))}
      </ul>
    </div>
  );
};

export default LiveScores;
