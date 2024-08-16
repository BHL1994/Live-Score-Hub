package learn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {
    @JsonProperty("notification_id")
    private int id;
    @JsonProperty("user_id")
    private AppUser appUser;
    @JsonProperty("game_id")
    private Game game;
    @JsonProperty("notification_type")
    private NotificationType notificationType;
    @JsonProperty("notification_time")
    private LocalDateTime notificationTime;

    public Notification(){}

    public Notification(int id, AppUser appUser, Game game, NotificationType notificationType,
                        LocalDateTime notificationTime) {
        this.id = id;
        this.appUser = appUser;
        this.game = game;
        this.notificationType = notificationType;
        this.notificationTime = notificationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return id == that.id && appUser == that.appUser && game == that.game && notificationType == that.notificationType && Objects.equals(notificationTime, that.notificationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appUser, game, notificationType, notificationTime);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", userId=" + appUser +
                ", gameId=" + game +
                ", notificationType=" + notificationType +
                ", notificationTime=" + notificationTime +
                '}';
    }
}