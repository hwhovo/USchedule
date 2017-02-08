package u.Schedule.entity.entities;

import java.util.ArrayList;
import java.util.Arrays;


public class Lesson_Group {
    private String bunch;
    private int weekday;
    private int numerator;
    private ArrayList<Lesson_Sub_Group> Lessons;

    public String getBunch() {
        return bunch;
    }

    public int getWeekday() {
        return weekday;
    }

    public int getNumerator() {
        return numerator;
    }

    public ArrayList<Lesson_Sub_Group> getLessons() {
        return Lessons;
    }

    public Lesson_Group(String bunch, int weekday, int numerator, Lesson_Sub_Group[] Lessons) {
        this.bunch = bunch;
        this.weekday = weekday;
        this.numerator = numerator;
        this.Lessons = new ArrayList<Lesson_Sub_Group>(Arrays.asList(Lessons));
    }

    public Lesson_Group(String bunch, int weekday, int numerator, ArrayList<Lesson_Sub_Group> Lessons) {
        this.bunch = bunch;
        this.weekday = weekday;
        this.numerator = numerator;
        this.Lessons = Lessons;
    }
}
