package learn.models;

public enum NotificationType {
    PRE_GAME("PRE_GAME"),
    QUARTER_START("QUARTER_START"),
    QUARTER_END("QUARTER_END"),
    GAME_END("GAME_END");

    private final String name;

    NotificationType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
