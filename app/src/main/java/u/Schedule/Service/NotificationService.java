package u.Schedule.Service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import u.Schedule.File.FileRW;
import u.Schedule.R;
import u.Schedule.entity.globals;
import u.Schedule.Activitys.MainActivity;
import u.Schedule.entity.entities.Bunch;
import u.Schedule.entity.entities.Lesson_Group;
import u.Schedule.entity.entities.Lesson_Sub_Group;
import u.Schedule.entity.favouriteBunches;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service{

    private Timer mTimer;
    private static final  int NOTIFICATION_FINAL  = 17;
    private Notification notification;
    private Notification notification2;
    private boolean is_Lesson;
    private int lesson_Number;
    private int g_Start_Bunch_LessonCount;
    private int g_Start_Bunch_length;
  /*  private Date date(){
            Date date = new Date();
            date.setTime(30000);
         return date;
   }*/

    public void onCreate() {
        super.onCreate();
        Log.i("LOG","RUN");


        try{g_Start_Bunch_LessonCount = globals.g_Start_Bunch_LessonCount; }catch(Exception e){}
        try{g_Start_Bunch_length = globals.g_Start_Bunch.length();}catch(Exception e){}
        mTimer = new Timer();
        mTimer.schedule(timerTask, 3000, 57000);
        // void scheduleAtFixedRate (TimerTask task, Date when, long period)-sksuma when i mej nshac jamankic
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Log.e("Log", "Runing");
            try {
                if (notificationSchedule())
                    notifiy();
            } catch (Exception e) {
            }
        }
    };



    public void onDestroy() {
        try{
            mTimer.cancel();
            timerTask.cancel();
            Log.i("log","STOP");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent("com.schedule.Notification2");
        intent.putExtra("yourvalue","torestore");
        sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try{

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public  void notifiy(){

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("RSSPullService");


        Intent myIntent = new Intent(this, MainActivity.class);
      //  Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(""));
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,myIntent,0/*Intent.FLAG_ACTIVITY_NEW_TASK*/);

        Notification.Builder builder;

        builder = new  Notification.Builder(getApplicationContext())
                .setContentTitle(getNotificationString())//lesson_Number + " դասաժամի դասամիջոցը" + (is_Lesson ? )
                .setContentText("USchedule")
                .setContentIntent(pendingIntent)
                //.setDefaults(Notification.DEFAULT_SOUND)
                //.setVibrate()
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher);
        notification = new Notification();
        notification2 = new Notification();
        notification2 = builder.build();
        notification = builder.build();


        //get value is settings ringtone and vibrate

        if (loadVibrate())
            notification.defaults = Notification.DEFAULT_VIBRATE;//| Notification.DEFAULT_LIGHTS;
        if (loadRingtone())
            notification2.defaults = Notification.DEFAULT_SOUND;// | Notification.DEFAULT_LIGHTS;


            // long[] vibrate1 = new long[]{1500,1000,1500,1000};
        //Boolean bool = Boolean.parseBoolean(SettingsActivity.NotificationPreferenceFragment.getVibrateOnOf().toString());
      /*  if (bool)
            notification.vibrate = vibrate1;*/

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_FINAL,notification);
        notificationManager.notify(NOTIFICATION_FINAL,notification2);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public boolean notificationSchedule() {
        Calendar cal = Calendar.getInstance();

        int i;

        haveDefaultBunch();
        if (cal.get(Calendar.AM_PM) == Calendar.SUNDAY)
            i = (cal.get(Calendar.HOUR) + 12) * 60 + cal.get(Calendar.MINUTE);
        else
            i = cal.get(Calendar.HOUR) * 60 + cal.get(Calendar.MINUTE);

        int timeNumber = 0;
        boolean isLesson = false;

        if (i == 570) {
            timeNumber = 1;
            isLesson = true;
        } else if (i == 615) {
            timeNumber = 1;
            isLesson = false;
        } else if (i == 620) {
            timeNumber = 2;
            isLesson = true;
        } else if (i == 665) {
            timeNumber = 2;
            isLesson = false;
        } else if (i == 675) {
            timeNumber = 3;
            isLesson = true;
        } else if (i == 720) {
            timeNumber = 3;
            isLesson = false;
        } else if (i == 725) {
            timeNumber = 4;
            isLesson = true;
        } else if (i == 770) {
            timeNumber = 4;
            isLesson = false;
        } else if (i == 790) {
            timeNumber = 5;
            isLesson = true;
        } else if (i == 835) {
            timeNumber = 5;
            isLesson = false;
        } else if (i == 840) {
            timeNumber = 6;
            isLesson = true;
        } else if (i == 885) {
            timeNumber = 6;
            isLesson = false;
        } else if (i == 895) {
            timeNumber = 7;
            isLesson = true;
        } else if (i == 940) {
            timeNumber = 7;
            isLesson = false;
        } else if (i == 945) {
            timeNumber = 8;
            isLesson = true;
        } else if (i == 990) {
            timeNumber = 8;
            isLesson = false;
        } else if (i == 1000) {
            timeNumber = 9;
            isLesson = true;
        } else if (i == 1045) {
            timeNumber = 9;
            isLesson = false;
        } else if (i == 1050) {
            timeNumber = 10;
            isLesson = true;
        } else if (i == 1095) {
            timeNumber = 10;
            isLesson = false;
        }

        is_Lesson = isLesson;
        lesson_Number = timeNumber;

        Log.i("log","is_Lesson = " + String.valueOf(isLesson));
        Log.i("log","lesson_Number = " + String.valueOf(timeNumber));

        Log.i("log","g_Start_Bunch_LessonCount = " + String.valueOf(g_Start_Bunch_LessonCount));
        Log.i("log","g_Start_Bunch.length = " +String.valueOf(g_Start_Bunch_length));

        Log.i("log","resault = "+String.valueOf(timeNumber != 0 && g_Start_Bunch_LessonCount*2  > timeNumber && g_Start_Bunch_length > 0));


        return timeNumber != 0 && g_Start_Bunch_LessonCount*2  > timeNumber && g_Start_Bunch_length> 0;
    }



    private String getNotificationString()
    {
        String b = Locale.getDefault().getDisplayLanguage();
        String lessonString = "";
        switch(b)
        {
            case "русский":

                lessonString = is_Lesson ? "Начало " : "Конец ";
                lessonString += String.valueOf(lesson_Number) + "-";
                lessonString += lesson_Number == 3 ? "его ":"ого ";
lessonString+="урока.";
                break;
            case "Armenian":

                lessonString = String.valueOf(lesson_Number);
                lessonString += lesson_Number == 1 ? "-ին":"-րդ";
                lessonString += " դասի ";
                lessonString += is_Lesson ? "սկիզբ:" : "ավարտ:";

                break;
            default:   // "English" and "us"
                lessonString = is_Lesson ? "Start the " : "Complete the ";
                lessonString += String.valueOf(lesson_Number);

                    switch(lesson_Number) {
                        case 1:
                            lessonString += "-st";
                            break;
                        case 2:
                            lessonString += "-nd";
                            break;
                        case 3:
                            lessonString += "-rd";
                            break;
                        default:
                            lessonString += "-th";
                            break;
                    }

                lessonString += " lesson.";
                break;
        }

        return lessonString;
    }


    public boolean haveDefaultBunch()
    {
        Bunch bunch;
        int count = 0;
        FileRW.read(getApplicationContext());
        bunch = favouriteBunches.CompareBunchHashCode(FileRW.read(getApplicationContext()));


        for (Lesson_Sub_Group subGroup :
                getLessonGroup(bunch.getBunch(), getCurrentWeekday(), getCurrentNumerator() ).getLessons()) {
            if(subGroup.getRoom().length() > 2 || subGroup.getSubject().length() > 2)
            {
                count++;}
        }
        g_Start_Bunch_length = bunch.getBunch().length();
        g_Start_Bunch_LessonCount = count;

        return bunch.getBunch() != null && bunch.getBunch() != "";
    }
    public int getCurrentWeekday() {
        Calendar cal = Calendar.getInstance();
        int weekdaynumber = cal.get(Calendar.DAY_OF_WEEK) - 1;

        return weekdaynumber;
    }
    public Lesson_Group getLessonGroup(String bunch, int weekday, int numerator) {
        Lesson_Group lg = new Lesson_Group("", 1, 1, new Lesson_Sub_Group[]{});

        for (Lesson_Group item : globals.LessonsList) {
            if (item.getBunch().equals(bunch) && item.getWeekday() == weekday && item.getNumerator() == numerator)
                lg = new Lesson_Group(item.getBunch(), item.getWeekday(), item.getNumerator(), item.getLessons());
        }

        return lg;
    }
    public int getCurrentNumerator() {
        Calendar cal = Calendar.getInstance();
        int numerator;
        numerator = cal.get(Calendar.WEEK_OF_YEAR) % 2 == 0 ? 1 : 0;
        return numerator;
    }

    public boolean loadVibrate()
    {
        String content = FileRW.read(getApplicationContext(), "vibrate");

        return content.hashCode() == "true".hashCode();
    }
    public boolean loadRingtone()
    {
        String content = FileRW.read(getApplicationContext(), "ringtone");

        return content.hashCode() == "true".hashCode();
    }
}
/*
    public void loadFavouriteBunchesList(Context con)
    {
        Bunch bunch = null;
        ArrayList<Integer> notify = new ArrayList<Integer>();
        String fileContent = "";

        fileContent = FileRW.read(con, "notificationContext");

        if(fileContent == "")
            return;

        for (String item:
                fileContent.split(",")) {
            if(item != "") {
                try{
                if(item != null)
                    notify.add(Integer.getInteger(item));}catch(Exception e){}
            }
        }
        g_Start_Bunch_LessonCount = notify.get(0);
        g_Start_Bunch_length = notify.get(1);
    }*/