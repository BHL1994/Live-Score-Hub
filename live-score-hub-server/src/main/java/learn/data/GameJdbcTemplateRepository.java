package learn.data;

import learn.models.Game;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class GameJdbcTemplateRepository implements GameRepository{
    private final JdbcTemplate jdbcTemplate;


    public GameJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game findById(int id) {
        final String sql = """
                select
                    g.game_id,
                    g.game_date,
                    g.game_status,
                    g.period,
                    g.league,
                    g.time_remaining,
                    g.home_score,
                    g.away_score,
                    g.home_period_scores,
                    g.away_period_scores,
                    h.team_id as home_team_id,
                    h.name as home_name,
                    h.city as home_city,
                    h.team as home_team,
                    h.league as home_league,
                    h.abbreviation as home_abbreviation,
                    h.logo_url as home_logo_url,
                    a.team_id as away_team_id,
                    a.name as away_name,
                    a.city as away_city,
                    a.team as away_team,
                    a.league as away_league,
                    a.abbreviation as away_abbreviation,
                    a.logo_url as away_logo_url
                from game g
                inner join team h on g.home_id = h.team_id
                inner join team a on g.away_id = a.team_id
                where g.game_id = ?;
                """;

        return jdbcTemplate.query(sql, new GameMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public List<Game> findByDate(LocalDateTime date) {
        final String sql = """
                select
                    g.game_id,
                    g.game_date,
                    g.game_status,
                    g.period,
                    g.league,
                    g.time_remaining,
                    g.home_score,
                    g.away_score,
                    g.home_period_scores,
                    g.away_period_scores,
                    h.team_id as home_team_id,
                    h.name as home_name,
                    h.city as home_city,
                    h.team as home_team,
                    h.league as home_league,
                    h.abbreviation as home_abbreviation,
                    h.logo_url as home_logo_url,
                    a.team_id as away_team_id,
                    a.name as away_name,
                    a.city as away_city,
                    a.team as away_team,
                    a.league as away_league,
                    a.abbreviation as away_abbreviation,
                    a.logo_url as away_logo_url
                from game g
                inner join team h on g.home_id = h.team_id
                inner join team a on g.away_id = a.team_id
                where g.game_date = ?;
                """;

        return jdbcTemplate.query(sql, new GameMapper(), date.toLocalDate());
    }

    @Override
    public Game findByDateAndTeams(LocalDateTime date, String league, String homeName, String awayName) {
        final String sql = """
                select
                    g.game_id,
                    g.game_date,
                    g.game_status,
                    g.period,
                    g.league,
                    g.time_remaining,
                    g.home_score,
                    g.away_score,
                    g.home_period_scores,
                    g.away_period_scores,
                    h.team_id as home_team_id,
                    h.name as home_name,
                    h.city as home_city,
                    h.team as home_team,
                    h.league as home_league,
                    h.abbreviation as home_abbreviation,
                    h.logo_url as home_logo_url,
                    a.team_id as away_team_id,
                    a.name as away_name,
                    a.city as away_city,
                    a.team as away_team,
                    a.league as away_league,
                    a.abbreviation as away_abbreviation,
                    a.logo_url as away_logo_url
                from game g
                inner join team h on g.home_id = h.team_id
                inner join team a on g.away_id = a.team_id
                where (h.name = ? and h.league = ?) and (a.name = ? and a.league = ?) and g.game_date = ?;
                """;
        return jdbcTemplate.query(sql, new GameMapper(), homeName, league, awayName, league, date).stream().findFirst().orElse(null);
    }

    @Override
    public List<Game> findByTeam(String city, String team) {
        final String sql = """
                select
                    g.game_id,
                    g.game_date,
                    g.game_status,
                    g.period,
                    g.league,
                    g.time_remaining,
                    g.home_score,
                    g.away_score,
                    g.home_period_scores,
                    g.away_period_scores,
                    h.team_id as home_team_id,
                    h.name as home_name,
                    h.city as home_city,
                    h.team as home_team,
                    h.league as home_league,
                    h.abbreviation as home_abbreviation,
                    h.logo_url as home_logo_url,
                    a.team_id as away_team_id,
                    a.name as away_name,
                    a.city as away_city,
                    a.team as away_team,
                    a.league as away_league,
                    a.abbreviation as away_abbreviation,
                    a.logo_url as away_logo_url
                from game g
                inner join team h on g.home_id = h.team_id
                inner join team a on g.away_id = a.team_id
                where h.city = ? and h.team = ? or a.city = ? and a.team = ?;
                """;
        return jdbcTemplate.query(sql, new GameMapper(), city, team, city, team);
    }

    public List<Game> findGamesByLeagueAndDate(String league, LocalDate date) {
        final String sql = """
                select
                    g.game_id,
                    g.game_date,
                    g.game_status,
                    g.period,
                    g.league,
                    g.time_remaining,
                    g.home_score,
                    g.away_score,
                    g.home_period_scores,
                    g.away_period_scores,
                    h.team_id as home_team_id,
                    h.name as home_name,
                    h.city as home_city,
                    h.team as home_team,
                    h.league as home_league,
                    h.abbreviation as home_abbreviation,
                    h.logo_url as home_logo_url,
                    a.team_id as away_team_id,
                    a.name as away_name,
                    a.city as away_city,
                    a.team as away_team,
                    a.league as away_league,
                    a.abbreviation as away_abbreviation,
                    a.logo_url as away_logo_url
                from game g
                inner join team h on g.home_id = h.team_id
                inner join team a on g.away_id = a.team_id
                where g.league = ? and DATE(g.game_date) = ?;
                """;

        return jdbcTemplate.query(sql, new GameMapper(), league, Timestamp.valueOf(date.atStartOfDay()));
    }

    @Override
    public Game add(Game game) {
        final String sql = """
                    insert into game (game_id, home_id, away_id, game_date, game_status, period, league, time_remaining, home_score, away_score, home_period_scores, away_period_scores)
                    values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                    """;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, game.getId());
            ps.setInt(2, game.getHome().getId());
            ps.setInt(3, game.getAway().getId());
            ps.setTimestamp(4, Timestamp.valueOf(game.getGameDate()));
            ps.setString(5, game.getStatus());
            ps.setInt(6, game.getPeriod());
            ps.setString(7, game.getLeague().toString());
            ps.setString(8, game.getTimeRemaining());
            ps.setInt(9, game.getHomeScore());
            ps.setInt(10, game.getAwayScore());
            ps.setString(11, game.getHomePeriodScores());
            ps.setString(12, game.getAwayPeriodScores());
            return ps;
        }, keyHolder);

        if(rowsAffected > 0) {
            return game;
        }

        return null;
    }

    @Override
    public boolean update(Game game) {
        final String sql = """
                update game set
                    home_id = ?,
                    away_id = ?,
                    game_date = ?,
                    game_status = ?,
                    period = ?,
                    league = ?,
                    time_remaining = ?,
                    home_score = ?,
                    away_score = ?,
                    home_period_scores = ?,
                    away_period_scores = ?
                where game_id = ?;
                """;

        return jdbcTemplate.update(sql,
                game.getHome().getId(),
                game.getAway().getId(),
                Timestamp.valueOf(game.getGameDate()),
                game.getStatus(),
                game.getPeriod(),
                game.getLeague().toString(),
                game.getTimeRemaining(),
                game.getHomeScore(),
                game.getAwayScore(),
                game.getHomePeriodScores(),
                game.getAwayPeriodScores(),
                game.getId()) > 0;
    }

    @Override
    public boolean delete(int id) {
        final String sql = "delete from game where game_id = ?";

        return jdbcTemplate.update(sql, id) > 0;
    }
}
