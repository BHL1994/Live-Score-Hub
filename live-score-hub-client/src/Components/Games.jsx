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
        <div className='text-center'>
            <h2>
                {league} Games for {formattedDate}
            </h2>
            <DatePicker
                inline
                selected={date}
                onChange={(date) => setDate(date)}
            />
            <div className="game-list mt-4">
                {games.length > 0 ? (
                    games.map(game => <ScoreCard key={game.game_id} game={game} />)
                ) : (
                    <p>No games available for this date.</p>
                )}
            </div>
        </div>
    );
}
