import React from 'react';

const ScoreCard = ({ game }) => {
  return (
    <div className="scorecard">
      <h3>{game.home_id.name} vs {game.away_id.name}</h3>
      <p>{game.home_score} - {game.away_score}</p> 
      <p>Status: {game.game_status}</p> 
      {game.time_remaining && <p>Time Remaining: {game.time_remaining}</p>}
    </div>
  );
};

export default ScoreCard;
