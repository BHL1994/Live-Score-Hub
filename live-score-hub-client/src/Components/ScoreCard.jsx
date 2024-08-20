import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const ScoreCard = ({ game }) => {
  const isLive = game.game_status === 'in progress' || game.game_status === 'live';
  const isFinal = game.game_status === 'final';
  const isCanceled = game.game_status === 'canceled';
  const isScheduled = game.game_status === 'scheduled';
  const homeScoreHigher = game.home_score > game.away_score;
  const homeScoreClass = (isLive || isFinal) ? (homeScoreHigher ? 'text-success' : 'text-danger') : '';
  const awayScoreClass = (isLive || isFinal) ? (!homeScoreHigher ? 'text-success' : 'text-danger') : '';

  const gameDateUTC = new Date(game.game_date);
  const options = {
    timeZone: 'America/New_York',
    hour: 'numeric',
    minute: '2-digit',
    hour12: true,
  };
  const formattedTime = gameDateUTC.toLocaleString('en-US', options) + ' EST';

  const gameStatusSymbol = isFinal ? 'Final' :
                            isCanceled ? 'Canceled' : 
                            isLive ? 'Live' : 
                            isScheduled ? `${formattedTime}` : '';

  const gameMiddleText = isLive ? (
    game.league === 'MLB' 
    ? `${game.period} inning`
    : `${game.time_remaining}, Q${game.period}`
  ) : '';

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
            <p className="text-danger mx-1" style={{ fontSize: '1.5rem', margin: '0 0.5rem' }}>{gameMiddleText}</p>
          )}
          {(isLive || isFinal) && (
            <p className={`card-text ${homeScoreClass}`} style={{ fontSize: '2rem', fontWeight: 'bold', margin: '0 0.5rem' }}>{game.home_score}</p>
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
      <div className="card-footer text-body-secondary">
        2 days ago
      </div>
    </div>
  );
};

export default ScoreCard;
