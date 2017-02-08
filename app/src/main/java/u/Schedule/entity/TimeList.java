package u.Schedule.entity;

import u.Schedule.entity.entities.Time;

import java.util.ArrayList;

/**
 * Created by Hovhannes on 06.02.2016.
 */
public class TimeList {
    public static String getTime(String univercity, int Number)
    {
        ArrayList<Time> timeList = globals.TimesList;

        for (Time tl:timeList ) {
            if(tl.getUnivercity().equals(univercity) && tl.getNumber() == Number)
                return tl.getTime();
        }
        return null;
    }
}
