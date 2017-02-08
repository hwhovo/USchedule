package u.Schedule.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import u.Schedule.entity.entities.Bunch;
import u.Schedule.entity.entities.Lesson_Group;
import u.Schedule.entity.entities.Lesson_Sub_Group;
import u.Schedule.entity.entities.Time;
import u.Schedule.entity.globals;
import u.Schedule.entity.TimeList;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "ScheduleDB";
    public static final String DBLOCATION = "/data/data/u.Schedule/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context,DBNAME,null,1);
        this.mContext = context;


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDatabase(){
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()){
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath,null, SQLiteDatabase.OPEN_READWRITE);
    }

    public  void closeDatabase(){
        if (mDatabase != null){
            mDatabase.close();
        }
    }

    public void loadBunchesList(){
        globals.BunchesList = new ArrayList<Bunch>();
        Bunch bunch = null;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT UniversityName,FacultyName,CourseNumber ,bunchName FROM BUNCHES " +
                "LEFT JOIN Faculties ON Bunches.facultyId = Faculties.ID " +
                "LEFT JOIN Universities ON Universities.Id = Faculties.UniversityId " +
                "LEFT JOIN Courses ON Courses.ID = Bunches.courseId", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            bunch = new Bunch(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3));
            globals.BunchesList.add(bunch);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        Log.w("MainActivity","load_Bunch_List Copied");

    }

    public void loadLessonsList(){
        globals.LessonsList = new ArrayList<Lesson_Group>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT bunchName, Weekday, Numerator " +
                ",Time1.Subject, Time1.Room, Time1.UniversityName, Time1.LessonTimeId " +
                ",Time2.Subject, Time2.Room, Time2.UniversityName, Time2.LessonTimeId " +
                ",Time3.Subject, Time3.Room, Time3.UniversityName, Time3.LessonTimeId " +
                ",Time4.Subject, Time4.Room, Time4.UniversityName, Time4.LessonTimeId " +
                "FROM BUNCHES  " +
                "LEFT JOIN Lessons ON Lessons.BunchId = Bunches.Id " +
                "LEFT JOIN  " +
                "(SELECT LessonId, Subject, Room, UniversityName, LessonTimeId  FROM LessonItems " +
                "LEFT JOIN Lessons ON Lessons.Id = LessonItems.LessonId " +
                "LEFT JOIN Bunches ON Lessons.BunchId = Bunches.Id " +
                "LEFT JOIN Faculties ON Bunches.facultyId = Faculties.ID " +
                "LEFT JOIN Universities ON Universities.Id = Faculties.UniversityId  " +
                "WHERE LessonTimeId = 1) as Time1 ON Time1.LessonId = Lessons.Id " +
                "LEFT JOIN  " +
                "(SELECT LessonId, Subject, Room, UniversityName, LessonTimeId  FROM LessonItems " +
                "LEFT JOIN Lessons ON Lessons.Id = LessonItems.LessonId " +
                "LEFT JOIN Bunches ON Lessons.BunchId = Bunches.Id " +
                "LEFT JOIN Faculties ON Bunches.facultyId = Faculties.ID " +
                "LEFT JOIN Universities ON Universities.Id = Faculties.UniversityId  " +
                "WHERE LessonTimeId = 2) as Time2 ON Time2.LessonId = Lessons.Id " +
                "LEFT JOIN  " +
                "(SELECT LessonId, Subject, Room, UniversityName, LessonTimeId  FROM LessonItems " +
                "LEFT JOIN Lessons ON Lessons.Id = LessonItems.LessonId " +
                "LEFT JOIN Bunches ON Lessons.BunchId = Bunches.Id " +
                "LEFT JOIN Faculties ON Bunches.facultyId = Faculties.ID " +
                "LEFT JOIN Universities ON Universities.Id = Faculties.UniversityId  " +
                "WHERE LessonTimeId = 3) as Time3 ON Time3.LessonId = Lessons.Id " +
                "LEFT JOIN " +
                "(SELECT LessonId, Subject, Room, UniversityName, LessonTimeId  FROM LessonItems " +
                "LEFT JOIN Lessons ON Lessons.Id = LessonItems.LessonId " +
                "LEFT JOIN Bunches ON Lessons.BunchId = Bunches.Id " +
                "LEFT JOIN Faculties ON Bunches.facultyId = Faculties.ID " +
                "LEFT JOIN Universities ON Universities.Id = Faculties.UniversityId " +
                "WHERE LessonTimeId = 4) as Time4 ON Time4.LessonId = Lessons.Id ", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            globals.LessonsList.add(new Lesson_Group(cursor.getString(0), cursor.getInt(1), cursor.getInt(2),
                    new Lesson_Sub_Group[]
                            {
                                    new Lesson_Sub_Group(cursor.getString(3), cursor.getString(4), TimeList.getTime(cursor.getString(5), cursor.getInt(6))),
                                    new Lesson_Sub_Group(cursor.getString(7), cursor.getString(8), TimeList.getTime(cursor.getString(9), cursor.getInt(10))),
                                    new Lesson_Sub_Group(cursor.getString(11), cursor.getString(12), TimeList.getTime(cursor.getString(13), cursor.getInt(14))),
                                    new Lesson_Sub_Group(cursor.getString(15), cursor.getString(16), TimeList.getTime(cursor.getString(17), cursor.getInt(18)))}
            ));

            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        Log.w("MainActivity","loadLesson_List Copied");

    }

    public void loadLessonTimes(){
        globals.TimesList = new ArrayList<>();

        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT UniversityName, LessonNumber , LessonStartTime, LessonEndTime FROM LessonTimes " +
                "LEFT JOIN Universities ON Universities.Id = LessonTimes.UniversityId", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            globals.TimesList.add(new Time(cursor.getString(0), cursor.getInt(1), cursor.getString(2) + " - " + cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        Log.w("MainActivity","loadLessonTimes Copied");

    }

    //region Example DataBase Using
/*
    public List<Product> getListProduct(){
        Product product = null;
        List<Product> productList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from EMP_TABLE", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            product = new Product(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3));
            productList.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return productList;
    }*/
    //endregion
}
