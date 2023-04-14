import React, {useEffect, useState} from 'react';
import './App.css';

function App() {

    const [messages, setMessages] = useState<{ id: string, description: string }[]>([]);
    const [loading, setLoading] = useState<boolean>(true)

    useEffect(() => {
        const eventSource = new EventSource('/api');
        eventSource.onmessage = (event) => {
            const eventData = JSON.parse(event.data);
            setMessages((prevMessages) => [...prevMessages, eventData]);
            setLoading(false)
        };

        eventSource.onerror = () => {
            setLoading(false)
        }

        return () => {
            eventSource.close();
        };
    }, []);


    return (
        <div className="App">
            <h1>Received Messages:</h1>
            {loading ?
                <>
                    <p>loading...</p>
                </>
                :
                <>
                    {messages.map((message) => (
                        <li key={message.id}>
                            <p>ID: {message.id}</p>
                            <p>Description: {message.description}</p>
                        </li>
                    ))}
                </>
            }
        </div>
    );
}

export default App;
