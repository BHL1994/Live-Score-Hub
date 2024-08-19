import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import ScoreCard from './ScoreCard';
import { useEffect } from 'react';

export default function Games() {
    const [date, setDate] = useState(new Date());
    const [games, setGames] = useState([]);
    const { league } = useParams(); 

    const formattedDate = date.toLocaleDateString('en-US', {
        month: 'long',
        day: 'numeric',
        year: 'numeric'
    });

    useEffect(() => {
        const fetchGames = async () => {
            try {
                setGames([]);
                const formattedDateForAPI = date.toISOString().split('T')[0];
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
    
    return (
        <div className="container mx-auto text-center">
            <h2>
                {league} Games for {formattedDate}
            </h2>
            <div className="d-flex justify-content-center">
                <DatePicker
                    inline
                    selected={date}
                    onChange={(date) => setDate(date)}
                />
            </div>
            <div className="game-list m-5">
                <div className="row justify-content-center">
                    {games.length > 0 ? (
                        games.map((game, index) => (
                            <div 
                                className={`col-md-5 mb-4 mx-3 ${games.length % 2 !== 0 && index === games.length - 1 ? 'mx-auto' : ''}`} 
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
                                <ScoreCard game={game} />
                            </div>
                        ))
                    ) : (
                        <p>No games available for this date.</p>
                    )}
                </div>
            </div>
        </div>
    );
}
