package u.Schedule.entity.entities;


public class Time {
    private String univercity;
    private int number;
    private String time;

    public String getUnivercity() {
        return univercity;
    }

    public int getNumber() {
        return number;
    }

    public String getTime() {
        return time;
    }

    public Time(String univercity, int number, String time) {

        this.univercity = univercity;
        this.number = number;
        this.time = time;
    }
}
