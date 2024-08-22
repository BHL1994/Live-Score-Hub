import React, { useState, useEffect, useContext } from 'react';
import { useParams } from 'react-router-dom';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import ScoreCard from './ScoreCard';
import AuthContext from '../Context/AuthContext';
import '../Games.css';

export default function Games() {
    const [date, setDate] = useState(new Date());
    const [games, setGames] = useState([]);
    const { league } = useParams();
    const [ws, setWs] = useState(null); 
    const auth = useContext(AuthContext);

    const formattedDate = date.toLocaleDateString('en-US', {
        month: 'long',
        day: 'numeric',
        year: 'numeric'
    });

    useEffect(() => {
        const fetchGames = async () => {
            try {
                setGames([]);
                const localDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
                const formattedDateForAPI = localDate.toISOString().split('T')[0];
                console.log(`Fetching games for: League=${league}, Date=${formattedDateForAPI}`);

                const response = await fetch(
                    `http://localhost:8080/api/games?league=${league}&date=${formattedDateForAPI}`
                );

                if (response.status === 204) {
                    console.log("No games for today.");
                    setGames([]);
                } else if (response.ok) {
                    const gamesData = await response.json();
                    setGames(gamesData);
                } else {
                    console.error('Failed to fetch games:', response.status, response.statusText);
                    setGames([]);
                }
            } catch (error) {
                console.error('Fetch error:', error);
                setGames([]);
            }
        };

        fetchGames();
    }, [league, date]);

    useEffect(() => {
        const socket = new WebSocket('ws://localhost:8080/live-scores');
        setWs(socket);

        socket.onmessage = (event) => {
            try {
                const gameUpdate = JSON.parse(event.data);
                setGames((prevGames) => {
                    return prevGames.map((game) =>
                        game.game_id === gameUpdate.game_id ? { ...game, ...gameUpdate } : game
                    );
                });
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
            <h2 className="games-title mb-5">
                {league} Games for {formattedDate}
            </h2>
            <div className="d-flex justify-content-center mb-4">
                <DatePicker
                    inline
                    selected={date}
                    onChange={(date) => setDate(date)}
                    className="custom-datepicker"
                    calendarClassName="custom-calendar"
                />
            </div>
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
                        <p className='no-games-message'>No games available for this date.</p>
                    )}
                </div>
            </div>
        </div>
    );
}
