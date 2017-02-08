package u.Schedule.entity;

import android.widget.PopupWindow;

import java.util.ArrayList;

import u.Schedule.entity.entities.Bunch;
import u.Schedule.entity.entities.Lesson_Group;
import u.Schedule.entity.entities.Time;


public class globals {
    public static int fragmentId = -1;
    public static ArrayList<Time> TimesList = new ArrayList<Time>();
    public static ArrayList<Lesson_Group> LessonsList = new ArrayList<Lesson_Group>();
    public static ArrayList<Bunch> BunchesList = new ArrayList<Bunch>();
    public static String g_Univercity;
    public static String g_Faculty;
    public static String g_Department;
    public static int g_Course;
    public static String g_Bunch;
    public static int g_UnivercityPosition = -1;
    public static int g_FacultyPosition = -1;
    public static int g_CoursePosition = -1;
    public static int g_BunchPosition = -1;
    public static int g_Weekday = -1;
    public static int g_Numerator = -1;
    public static int g_currentTime;
    public static boolean g_isLesson;
    public static int g_lessonItem;
    public static String g_Start_Bunch;
    public static PopupWindow g_Popup_Status;
    public final static String favouriteBunchPrefix = "favouriteBunchN";
    public static ArrayList<String> favouriteBunchesList;
    public static int g_Start_Bunch_LessonCount;
    public static int g_Current_Bunch_LessonCount;
    public static boolean g_OrintationIsOn = false;
    public static int color = android.R.color.holo_orange_dark;
    public static boolean isLanguageChanged = false;
}
