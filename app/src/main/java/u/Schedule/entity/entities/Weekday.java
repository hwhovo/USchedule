package u.Schedule.entity.entities;

import android.content.Context;

import u.Schedule.R;

public final class Weekday {
    public static final int Երկուշաբթի = 1, //"Երկուշաբթի",
    Երեքշաբթի = 2,//"Երեքշաբթի",
    Չորեքշաբթի = 3,//"Չորեքշաբթի",
    Հինգշաբթի = 4,//"Հինգշաբթի",
    Ուրբաթ = 5,//"Ուրբաթ",
    Շաբաթ = 6,//"Շաբաթ",
    Կիրակի = 7;//"Կիրակի";

    public static String GetWeekday(int number, Context context)
    {
        final String[] days = context.getResources().getStringArray(R.array.days);
        return days[number];
    }

}
