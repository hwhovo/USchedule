package u.Schedule.entity.entities;


public class Lesson_Sub_Group {
    private String subject;
    private String room;
    private String time;

    public String getTime() {
        return time;
    }

    public String getSubject() { return subject; }

    public String getRoom() {
        return room;
    }

    public Lesson_Sub_Group(String subject, String room, String time) {

        this.subject = subject;
        this.room = room;
        this.time = time;
    }
}
