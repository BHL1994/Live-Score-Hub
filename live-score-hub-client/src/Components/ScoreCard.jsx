import React, { useState, useEffect, useContext } from 'react';
import 'bootstrap-icons/font/bootstrap-icons.css';
import AuthContext from '../Context/AuthContext';
import { useLocation } from 'react-router-dom';

const ScoreCard = ({ game, isLoggedIn }) => {
    const auth = useContext(AuthContext);
    const [isFavorited, setIsFavorited] = useState(false);

    useEffect(() => {
        const checkFavoriteStatus = async () => {
            if (isLoggedIn) {
                try {
                    const response = await fetch(
                        `http://localhost:8080/api/notifications/user/${auth.user.app_user_id}/game/${game.game_id}`
                    );

                    if (response.status === 200) {
                        setIsFavorited(true);
                    } else if (response.status === 204) {
                        setIsFavorited(false);
                    } else {
                        console.error('Failed to check favorite status:', response.status, response.statusText);
                    }
                } catch (error) {
                    console.error('Fetch error:', error);
                }
            }
        };
    
        checkFavoriteStatus();
    }, [game, isLoggedIn, auth.user.app_user_id]);

    const handleFavoriteClick = async () => {
        try {
            if (isFavorited) {
                const response = await fetch(`http://localhost:8080/api/notifications?user_id=${auth.user.app_user_id}&game_id=${game.game_id}`, {
                    method: 'DELETE',
                    headers: {
                        Authorization: `Bearer ${auth.user.token}`,
                    },
                });
                if (response.status === 204) {
                    setIsFavorited(false);
                } else {
                    console.error('Failed to remove favorite:', response.status, response.statusText);
                }
            } else {
                const response = await fetch(`http://localhost:8080/api/notifications`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${auth.user.token}`,
                    },
                    body: JSON.stringify({
                        user_id: auth.user.app_user_id,
                        game_id: { 
                            game_id: game.game_id 
                        },
                        notification_type: 'PRE_GAME',
                        notification_time: game.game_date,
                    }),
                });
                if (response.status === 200) {
                    setIsFavorited(true);
                } else {
                    console.error('Failed to add favorite:', response.status, response.statusText);
                }
            }
        } catch (error) {
            console.error('Error handling favorite click:', error);
        }
    };

    const isLive = game.game_status === 'in progress' || game.game_status === 'live';
    const isFinal = game.game_status === 'final';
    const isCanceled = game.game_status === 'canceled';
    const isScheduled = game.game_status === 'scheduled';
    const homeScoreHigher = game.home_score > game.away_score;
    const homeScoreClass = (isLive || isFinal) ? (homeScoreHigher ? 'text-success' : 'text-danger') : '';
    const awayScoreClass = (isLive || isFinal) ? (!homeScoreHigher ? 'text-success' : 'text-danger') : '';

    const location = useLocation();
    const isMyGamesPage = location.pathname === '/mygames';

    const gameDateUTC = new Date(game.game_date);
    let options;

    if (isMyGamesPage && isScheduled) {
        options = {
            timeZone: 'America/New_York',
            month: 'short',
            day: 'numeric',
            hour: 'numeric',
            minute: '2-digit',
            hour12: true,
        };
    } else {
        options = {
            timeZone: 'America/New_York',
            hour: 'numeric',
            minute: '2-digit',
            hour12: true,
        };
    }

    const formattedTime = gameDateUTC.toLocaleString('en-US', options) + ' EST';
    const gameStatusSymbol = isFinal ? 'Final' : 
        isCanceled ? 'Canceled' : isLive ? 'Live' : isScheduled ? `${formattedTime}` : '';
    const totalPeriods = game.period;
    const isTopInning = totalPeriods % 2 === 0;

    const inningNumber = isTopInning
        ? totalPeriods
        : totalPeriods;

    const inningLabel = isTopInning ? 'Top' : 'Bot';

    const gameMiddleText = isLive && game.league === 'MLB' ? (
        <div className="d-flex flex-column align-items-center">
            <span>{inningLabel}</span>
            <span>{inningNumber}{inningNumber === 1 ? 'st' : inningNumber === 2 ? 'nd' : inningNumber === 3 ? 'rd' : 'th'}</span>
        </div>
    ) : (
        <div className="d-flex flex-column align-items-center" style={{ fontSize: '0.75rem' }}>
            <span>Q{game.period}</span>
            <span>{game.time_remaining}</span>
        </div>
    );




    return (
        <div className="card mb-3">
        <h5 className="card-header">
            {isLive ? <span className="text-danger">{gameStatusSymbol}</span> : gameStatusSymbol}
        </h5>
        <div className="card-body d-flex justify-content-between align-items-center">
            <div className="d-flex align-items-center" style={{ flexBasis: '33%', justifyContent: 'flex-start' }}>
            <div className="text-left">
                <h6 className="text-muted">{game.away_id.city}</h6>
                <h5 className="card-title">{game.away_id.team}</h5>
                <img src={game.away_id.logo_url} alt={game.away_id.name} className="img-fluid" style={{ maxWidth: '50px' }} />
            </div>
            </div>
            <div className="d-flex align-items-center justify-content-center" style={{ flexBasis: '33%', textAlign: 'center' }}>
            {(isLive || isFinal) && (
                <p className={`card-text ${awayScoreClass}`} style={{ fontSize: '2rem', fontWeight: 'bold', margin: '0 0.5rem' }}>{game.away_score}</p>
            )}
            {isLive && (
                <div className="text-danger mx-1" style={{ margin: '0 0.5rem' }}>
                    {gameMiddleText}
                </div>
            )}
            {(isLive || isFinal) && (
                <p className={`card-text ${homeScoreClass}`} style={{ fontSize: '2rem', fontWeight: 'bold', margin: '0 0.5rem' }}>
                    {game.home_score}
                </p>
            )}
            </div>
            <div className="d-flex align-items-center" style={{ flexBasis: '33%', justifyContent: 'flex-end' }}>
            <div className="text-right">
                <h6 className="text-muted">{game.home_id.city}</h6>
                <h5 className="card-title">{game.home_id.team}</h5>
                <img src={game.home_id.logo_url} alt={game.home_id.name} className="img-fluid" style={{ maxWidth: '50px' }} />
            </div>
            </div>
        </div>
        <div className="card-footer d-flex justify-content-between">
            <a href={`https://tickets.${game.league.toLowerCase()}.com`} className="btn btn-link">Buy Tickets</a>
            {isScheduled && isLoggedIn && !isMyGamesPage && (
                <button 
                    onClick={handleFavoriteClick} 
                    className="btn btn-link"
                    style={{color: isFavorited ? 'yellow' : '#000', textShadow: '0px 0px 1px black' }}
                    >
                    <i className={`bi ${isFavorited ? 'bi-star-fill' : 'bi-star'}`}></i>
                </button>
                )}
            <a href={`https://stats.${game.league.toLowerCase()}.com`} className="btn btn-link">View Stats</a>
        </div>
        </div>
    );
};

export default ScoreCard;
