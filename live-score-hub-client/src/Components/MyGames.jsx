import React, { useState, useEffect, useContext } from 'react';
import ScoreCard from './ScoreCard';
import AuthContext from '../Context/AuthContext';

export default function MyGames() {
    const auth = useContext(AuthContext);
    const [games, setGames] = useState([]);
    const [ws, setWs] = useState(null);

    useEffect(() => {
        const fetchFavoritedGames = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/notifications/user/${auth.user.app_user_id}`);
                if (response.ok) {
                    const notifications = await response.json();
                    const fetchedGames = notifications.map(n => n.game);
                    setGames(fetchedGames);
                } else {
                    console.error('Failed to fetch favorited games:', response.status, response.statusText);
                }
            } catch (error) {
                console.error('Fetch error:', error);
            }
        };

        fetchFavoritedGames();
    }, [auth.user.app_user_id]);

    useEffect(() => {
        const socket = new WebSocket('ws://localhost:8080/live-scores');
        setWs(socket);

        socket.onmessage = (event) => {
            try {
                const gameUpdate = JSON.parse(event.data);
                setGames(prevGames => 
                    prevGames.map(game =>
                        game.game_id === gameUpdate.game_id ? { ...game, ...gameUpdate } : game
                    )
                );
            } catch (error) {
                console.error("Error parsing WebSocket message:", error, event.data);
            }
        };

        return () => {
            if (socket) {
                socket.close();
            }
        };
    }, []);

    return (
        <div className="container mx-auto text-center">
            <h2>My Games</h2>
            <div className="grid gap-3 mt-5">
                <div className="row justify-content-center">
                    {games.length > 0 ? (
                        games.map((game) => (
                            <div
                                className={`col-md-5 mb-4 mx-3`}
                                key={game.game_id}
                                style={{
                                    transition: 'transform 0.3s, box-shadow 0.3s',
                                    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
                                }}
                                onMouseEnter={(e) => {
                                    e.currentTarget.style.transform = 'scale(1.05)';
                                    e.currentTarget.style.boxShadow = '0 8px 16px rgba(0, 0, 0, 0.2)';
                                }}
                                onMouseLeave={(e) => {
                                    e.currentTarget.style.transform = 'scale(1)';
                                    e.currentTarget.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.1)';
                                }}
                            >
                                <ScoreCard game={game} isLoggedIn={!!auth.user} />
                            </div>
                        ))
                    ) : (
                        <p>You have no games saved.</p>
                    )}
                </div>
            </div>
        </div>
    );
}
