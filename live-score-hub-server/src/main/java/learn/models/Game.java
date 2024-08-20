package learn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    @JsonProperty("game_id")
    private int id;
    @JsonProperty("home_id")
    private Team home;
    @JsonProperty("away_id")
    private Team away;
    @JsonProperty("game_date")
    private LocalDateTime gameDate;
    @JsonProperty("game_status")
    private String status;
    private int period;
    private League league;
    @JsonProperty("time_remaining")
    private String timeRemaining;
    @JsonProperty("home_score")
    private int homeScore;
    @JsonProperty("away_score")
    private int awayScore;
    @JsonProperty("away_period_scores")
    private String homePeriodScores;
    @JsonProperty("home_period_scores")
    private String awayPeriodScores;

    public Game() {}

    public Game(String awayPeriodScores, String homePeriodScores, int awayScore, int homeScore, String timeRemaining, League league, int period, String status, LocalDateTime gameDate, Team away, Team home, int id) {
        this.awayPeriodScores = awayPeriodScores;
        this.homePeriodScores = homePeriodScores;
        this.awayScore = awayScore;
        this.homeScore = homeScore;
        this.timeRemaining = timeRemaining;
        this.league = league;
        this.period = period;
        this.status = status;
        this.gameDate = gameDate;
        this.away = away;
        this.home = home;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Team getHome() {
        return home;
    }

    public void setHome(Team home) {
        this.home = home;
    }

    public Team getAway() {
        return away;
    }

    public void setAway(Team away) {
        this.away = away;
    }

    public LocalDateTime getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDateTime gameDate) {
        this.gameDate = gameDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(String timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public String getHomePeriodScores() {
        return homePeriodScores;
    }

    public void setHomePeriodScores(String homePeriodScores) {
        this.homePeriodScores = homePeriodScores;
    }

    public String getAwayPeriodScores() {
        return awayPeriodScores;
    }

    public void setAwayPeriodScores(String awayPeriodScores) {
        this.awayPeriodScores = awayPeriodScores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && period == game.period && homeScore == game.homeScore && awayScore == game.awayScore && Objects.equals(home, game.home) && Objects.equals(away, game.away) && Objects.equals(gameDate, game.gameDate) && Objects.equals(status, game.status) && league == game.league && Objects.equals(timeRemaining, game.timeRemaining) && Objects.equals(homePeriodScores, game.homePeriodScores) && Objects.equals(awayPeriodScores, game.awayPeriodScores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, home, away, gameDate, status, period, league, timeRemaining, homeScore, awayScore, homePeriodScores, awayPeriodScores);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", home=" + home +
                ", away=" + away +
                ", gameDate=" + gameDate +
                ", status='" + status + '\'' +
                ", period=" + period +
                ", league=" + league +
                ", timeRemaining='" + timeRemaining + '\'' +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                ", homePeriodScores='" + homePeriodScores + '\'' +
                ", awayPeriodScores='" + awayPeriodScores + '\'' +
                '}';
    }
}