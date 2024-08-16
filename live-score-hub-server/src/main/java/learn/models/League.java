package learn.models;

public enum League {
    NFL("NFL"),
    NBA("NBA"),
    MLB("MLB"),
    NHL("NHL"),
    NCAAB("NCAAB"),
    NCAAF("NCAAF");

    private final String name;

    League(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
