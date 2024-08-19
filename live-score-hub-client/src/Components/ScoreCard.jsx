import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const ScoreCard = ({ game }) => {
  const isLive = game.game_status === 'in progress' || game.game_status === 'live';
  const isFinal = game.game_status === 'final';
  const isCanceled = game.game_status === 'canceled';
  const isScheduled = game.game_status === 'scheduled';
  const homeScoreHigher = game.home_score > game.away_score;
  const homeScoreClass = isLive ? (homeScoreHigher ? 'text-success' : 'text-danger') : '';
  const awayScoreClass = isLive ? (!homeScoreHigher ? 'text-success' : 'text-danger') : '';

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

  return (
    <div className="card mb-3">
      <div className="card-body d-flex justify-content-between align-items-center">
        <div className="team-info text-center">
          <h6 className="text-muted">{game.home_id.city}</h6>
          <h5 className="card-title">{game.home_id.team}</h5>
          <img src={game.home_id.logo_url} alt={game.home_id.name} className="img-fluid" style={{ maxWidth: '50px' }} />
          <p className={`card-text ${homeScoreClass}`}>{game.home_score}</p>
        </div>
        <div className="game-status text-center">
          <h6 className="text-muted">{gameStatusSymbol}</h6>
          {isLive && (
            <p className="text-warning">
              {game.league === 'MLB' ? `Inning: ${game.inning}` : `${game.time_remaining}, Q${game.period}`}
            </p>
          )}
        </div>
        <div className="team-info text-center">
          <h6 className="text-muted">{game.away_id.city}</h6>
          <h5 className="card-title">{game.away_id.team}</h5>
          <img src={game.away_id.logo_url} alt={game.away_id.name} className="img-fluid" style={{ maxWidth: '50px' }} />
          <p className={`card-text ${awayScoreClass}`}>{game.away_score}</p>
        </div>
      </div>
    </div>
  );
};

export default ScoreCard;
