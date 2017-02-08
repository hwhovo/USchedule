package u.Schedule.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import u.Schedule.Activitys.MainActivity;
import u.Schedule.File.FileRW;
import u.Schedule.R;
import u.Schedule.entity.entities.Bunch;
import u.Schedule.entity.entities.Lesson_Sub_Group;
import u.Schedule.entity.favouriteBunches;
import u.Schedule.entity.globals;
import u.Schedule.entity.entities.Lesson_Group;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class LessonFragment extends ListFragment {

    private View view;
    private int spinnerDaysValue = 0;
    private int spinnerHamarichValue = 0;//Numerator = 0  deNumerator = 1
    private Boolean CacboxBool = false;
    private Boolean SwitchBool = false;
    float x1, x2;
    private final int titleHome = R.string.app_name;

    public int getTitleLesson() {
        return titleHome;
    }

    public LessonFragment() {
        // Required empty public constructor
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        onSaveInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lesson, container, false);
        globals.fragmentId = 11;

        initFavoriteChacBox();
        DaysValue();
        HamarichValue();
        initSwitch();


        initialize();
        loadFavouriteBunch();

        //Set Default bunch button click event
        Switch defaultBunch = (Switch) view.findViewById(R.id.select_bunch);

        defaultBunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_bunch_click();
            }
        });
        //set default bunch click event

        CheckBox favoriteCheckbox = (CheckBox) view.findViewById(R.id.favoriteChacbox);

        favoriteCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouriteBunchSave();
            }
        });


        return view;
    }

    public void loadFavouriteBunch() {
        favouriteBunches.loadFavouriteBunchesList(getActivity());
        CheckBox favouriteBunchCheckBox = (CheckBox) view.findViewById(R.id.favoriteChacbox);

        for (String item :
                globals.favouriteBunchesList) {
            if (item.equals(globals.g_Bunch))
                favouriteBunchCheckBox.setChecked(true);
        }
    }


    public Boolean initFavoriteChacBox() {
        CheckBox checkboxvariable = (CheckBox) view.findViewById(R.id.favoriteChacbox);

        checkboxvariable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CacboxBool = isChecked;
            }

        });

        return CacboxBool;
    }

    private void DaysValue() {

        final String[] days = getResources().getStringArray(R.array.days);
        Spinner spinner = (Spinner) view.findViewById(R.id.days);
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, days);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(LTRadapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position == 0) {
                    position = getCurrentWeekday();
                    globals.g_Numerator = getCurrentNumerator();
                }

                spinnerDaysValue = position;
                globals.g_Weekday = position;

                initialize();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    private void HamarichValue() {
        final String[] values = getResources().getStringArray(R.array.hamarich_haytarar);
        Spinner spinner1 = (Spinner) view.findViewById(R.id.hamarich);
        ArrayAdapter<String> LTRadapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        LTRadapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(LTRadapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                spinnerHamarichValue = position;
                globals.g_Numerator = position;

                initialize();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void AddLessons() {

        final String[] data = getResources().getStringArray(R.array.lessons);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
        // ListView lw = (ListView) view.findViewById(R.id.lessonList);
        //lw.setAdapter(adapter);
        //setListAdapter(adapter);
    }

    public Boolean initSwitch() {

        Switch SelectBunchDefault = (Switch) view.findViewById(R.id.select_bunch);

        SelectBunchDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SwitchBool = isChecked;

                if (isChecked == true)
                    globals.g_Start_Bunch = globals.g_Bunch;
            }
        });

        return SwitchBool;
    }

    public int getSpinnerDaysValue() {
        return spinnerDaysValue;
    }

    public int getSpinnerHamarichValue() {
        return spinnerHamarichValue;
    }


    public void initialize() {
        String bunch;
        int numerator;
        int weekday;
        if(globals.g_Bunch.equals("Bunch") || globals.g_Bunch.equals("Խումբ") ||globals.g_Bunch.equals("Группа"))
            globals.g_Bunch = globals.g_Start_Bunch;
        if (globals.g_Bunch != null)
            bunch = globals.g_Bunch;
        else
            bunch = globals.g_Department;


        for (Bunch item :
                globals.BunchesList) {
            if (item.getBunch().equals(bunch)) {
                globals.g_Univercity = item.getUnivercity();
                globals.g_Faculty = item.getFaculty();
                globals.g_Course = item.getCourse();
            }
        }

        if (globals.g_Numerator != -1)
            numerator = globals.g_Numerator;
        else {
            numerator = getCurrentNumerator();
            globals.g_Numerator = numerator;
        }

        if (globals.g_Weekday != -1)
            weekday = globals.g_Weekday;
        else {
            weekday = getCurrentWeekday();
            globals.g_Weekday = weekday;
        }

        if (bunch == null || bunch.equals(""))
            gotoHomeFragment();

        Spinner numeratorSpinner = (Spinner) view.findViewById(R.id.hamarich);
        numeratorSpinner.setSelection(globals.g_Numerator);

        Spinner weekdaySpinner = (Spinner) view.findViewById(R.id.days);
        weekdaySpinner.setSelection(globals.g_Weekday);


        Switch togButton = (Switch) view.findViewById(R.id.select_bunch);

        if(globals.g_Start_Bunch != null)
        togButton.setChecked(globals.g_Start_Bunch.equals(bunch));

        ArrayList<ArrayList<String>> hashList = new ArrayList<ArrayList<String>>();
        ArrayList<String> lessonList = new ArrayList<String>();

        TextView weekdayNumber = (TextView) view.findViewById(R.id.weekdayNumber);

        weekdayNumber.setText(String.valueOf(GetCurrentWeekdayNumberFromSeptember()));



        for (Lesson_Sub_Group subGroup :
                getLessonGroup(bunch, weekday, numerator).getLessons()) {
            if(subGroup.getRoom().length() > 2 || subGroup.getSubject().length() > 2)
            {String hp = "";
            hp += subGroup.getTime();
            hp += "\t\t" + subGroup.getRoom();
            hp += "\n" + subGroup.getSubject();

            lessonList.add(hp);}
        }
if(lessonList.size() == 0)
    lessonList.add("\t\t" + getResources().getString(R.string.emptyLesson));

        ArrayAdapter<String> customAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lessonList);




        MainActivity.setToolbarTitle(globals.g_Bunch);

        getCurrentLesson();

        boolean t;

        if (globals.g_Weekday == getCurrentWeekday() && globals.g_Numerator == getCurrentNumerator()) {
            if(globals.g_Start_Bunch != null)

                if (globals.g_Start_Bunch.equals(bunch))
                globals.g_Start_Bunch_LessonCount = lessonList.size();

            globals.g_Current_Bunch_LessonCount = lessonList.size();

           // fillCurrentTime(true, lessonList.size());
            t = true;
        } else
            //fillCurrentTime(false, lessonList.size());
        t = false;

        setListAdapter(loadLessons(getLessonGroup(bunch, weekday, numerator).getLessons(), t, lessonList.size()) );
    }

    private SimpleAdapter loadLessons(ArrayList<Lesson_Sub_Group> lessons, boolean t , int count) {
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
int index = 1;

        for (Lesson_Sub_Group subGroup :
                lessons) {
            if(subGroup.getRoom().length() > 2 || subGroup.getSubject().length() > 2) {
                String hp = "";
                hp += subGroup.getTime();
                hp += "\t\t" + subGroup.getRoom();

                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("txt", hp);
                hm.put("cur", subGroup.getSubject());
                if (currentTime(t, count) != -1 && globals.g_currentTime == index)
                    if (currentTime(t, count) == R.drawable.green)
                        hm.put("flag", Integer.toString(R.drawable.green));
                    else
                        hm.put("flag", Integer.toString(R.drawable.yellow));



                aList.add(hm);
                index++;
            }
        }

        if(aList.size() == 0) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("txt", "\t\t\t" + getResources().getString(R.string.emptyLesson));
            aList.add(hm);
        }
        // Keys used in Hashmap
        String[] from = { "flag","txt","cur" };

        // Ids of views in listview_layout
        int[] to = { R.id.flag,R.id.txt,R.id.cur};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), aList, R.layout.simpleadapteritem, from, to);


        return adapter;
    }

    public int currentTime(boolean t, int count) {
        Calendar cal = Calendar.getInstance();
        int weekdaynumber = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? 7 : cal.get(Calendar.DAY_OF_WEEK) - 1;


        if (weekdaynumber < 6) {

            if (globals.g_currentTime >= 1 && globals.g_currentTime <=globals.g_Current_Bunch_LessonCount  && t && globals.g_currentTime <= count) {

                if (globals.g_isLesson)
                    return R.drawable.green;
                else
                    return R.drawable.yellow;
            }
        }

        return -1;
    }

    private void gotoHomeFragment() {
        Fragment fragment = new HomeFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_conteyner, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public int getCurrentWeekday() {
        Calendar cal = Calendar.getInstance();
        int weekdaynumber = cal.get(Calendar.DAY_OF_WEEK) - 1;

        return weekdaynumber;
    }

    public int GetCurrentWeekdayNumberFromSeptember() {
        Calendar cal = Calendar.getInstance();
        int startWeekdayNumber = cal.get(Calendar.WEEK_OF_YEAR);

        int currentWeekdaynumber = 6;


        return (startWeekdayNumber - currentWeekdaynumber) > 0 ? (startWeekdayNumber - currentWeekdaynumber) : 0;
    }

    public void getCurrentLesson() {
        Calendar cal = Calendar.getInstance();
        int i;

        if (cal.get(Calendar.AM_PM) == Calendar.SUNDAY)
            i = (cal.get(Calendar.HOUR) + 12) * 60 + cal.get(Calendar.MINUTE);
        else
            i = cal.get(Calendar.HOUR) * 60 + cal.get(Calendar.MINUTE);

        int timeNumber = 0;
        boolean isLesson = false;

        if (i >= 570 && i <= 615) {
            timeNumber = 1;
            isLesson = true;
        } else if (i > 615 && i <= 620) {
            timeNumber = 1;
            isLesson = false;
        } else if (i > 620 && i <= 665) {
            timeNumber = 1;
            isLesson = true;
        } else if (i > 665 && i <= 675) {
            timeNumber = 1;
            isLesson = false;
        } else if (i > 675 && i <= 720) {
            timeNumber = 2;
            isLesson = true;
        } else if (i > 720 && i <= 725) {
            timeNumber = 2;
            isLesson = false;
        } else if (i > 725 && i <= 770) {
            timeNumber = 2;
            isLesson = true;
        } else if (i > 770 && i <= 790) {
            timeNumber = 2;
            isLesson = false;
        } else if (i > 790 && i <= 835) {
            timeNumber = 3;
            isLesson = true;
        } else if (i > 835 && i <= 840) {
            timeNumber = 3;
            isLesson = false;
        } else if (i > 840 && i <= 885) {
            timeNumber = 3;
            isLesson = true;
        } else if (i > 885 && i <= 895) {
            timeNumber = 3;
            isLesson = false;
        } else if (i > 895 && i <= 940) {
            timeNumber = 4;
            isLesson = true;
        } else if (i > 940 && i <= 945) {
            timeNumber = 4;
            isLesson = false;
        } else if (i > 945 && i <= 990) {
            timeNumber = 4;
            isLesson = true;
        } else if (i > 990 && i <= 1000) {
            timeNumber = 4;
            isLesson = false;
        } else if (i > 1000 && i <= 1045) {
            timeNumber = 5;
            isLesson = true;
        } else if (i > 1045 && i <= 1050) {
            timeNumber = 5;
            isLesson = false;
        } else if (i > 1050 && i <= 1095) {
            timeNumber = 5;
            isLesson = true;
        }

        globals.g_isLesson = isLesson;
        globals.g_currentTime = timeNumber;
    }


    public int getCurrentNumerator() {
        Calendar cal = Calendar.getInstance();
        int numerator;
        numerator = cal.get(Calendar.WEEK_OF_YEAR) % 2 == 0 ? 1 : 0;
        return numerator;
    }


    public static Lesson_Group getLessonGroup(String bunch, int weekday, int numerator) {
        Lesson_Group lg = new Lesson_Group("", 1, 1, new Lesson_Sub_Group[]{});

        for (Lesson_Group item : globals.LessonsList) {
            if (item.getBunch().equals(bunch) && item.getWeekday() == weekday && item.getNumerator() == numerator)
                lg = new Lesson_Group(item.getBunch(), item.getWeekday(), item.getNumerator(), item.getLessons());
        }

        return lg;
    }

   /* public void fillCurrentTime(boolean t, int count) {
        Calendar cal = Calendar.getInstance();
        int weekdaynumber = cal.get(Calendar.DAY_OF_WEEK) == 1 ? 7 : cal.get(Calendar.DAY_OF_WEEK) - 1;


        if (weekdaynumber < 6) {
            int i = 0;
            ArrayList<Integer> iList = new ArrayList<>();

            TextView listItem = (TextView) view.findViewById(R.id.text1);
            int height = 96;
            ImageView rb;

            iList.add(R.id.imageView1);
            iList.add(R.id.imageView2);
            iList.add(R.id.imageView3);
            iList.add(R.id.imageView4);
            iList.add(R.id.imageView5);


            for (int item : iList) {
                rb = (ImageView) view.findViewById(item);
                rb.setVisibility(View.INVISIBLE);
                rb.getLayoutParams().height = height;
            }

            if (globals.g_currentTime >= 1 && globals.g_currentTime <=globals.g_Current_Bunch_LessonCount  && t && globals.g_currentTime <= count) {
                rb = (ImageView) view.findViewById(iList.get(globals.g_currentTime - 1));
                rb.setVisibility(View.VISIBLE);


                ///Picture Lesson && Racess
                if (globals.g_isLesson)
                    rb.setImageResource(R.drawable.green);
                else
                    rb.setImageResource(R.drawable.yellow);
            }
        }
    }

*/
    public void select_bunch_click() {
        Switch tg = (Switch) view.findViewById(R.id.select_bunch);
        if (!tg.isChecked())
            globals.g_Start_Bunch = null;
        else
            globals.g_Start_Bunch = globals.g_Bunch != null ?
                    globals.g_Bunch : globals.g_Department;

        if (globals.g_Start_Bunch == null)
            FileRW.write("", getActivity());
        else
            FileRW.write(globals.g_Start_Bunch, getActivity());
    }  // SERIALIZE


    public void favouriteBunchSave() {
        CheckBox tg = (CheckBox) view.findViewById(R.id.favoriteChacbox);
        if (!tg.isChecked())
            favouriteBunches.removeFavouriteBunch(globals.g_Bunch, getActivity());
        else
            favouriteBunches.addFavouriteBunch(globals.g_Bunch, getActivity());

    }  // SERIALIZE
}

/*
public boolean slideRightLeft(View view, MotionEvent touchevent)
{
    int weekdaynum;
    switch (touchevent.getAction()) {
        // when user first touches the screen we get x and y coordinate
        case MotionEvent.ACTION_DOWN: {
            x1 = touchevent.getX();
            break;
        }
        case MotionEvent.ACTION_UP: {
            x2 = touchevent.getX();

            //if left to right sweep event on screen
            if (x1 < x2) {
                if (globals.g_Weekday == 1 || globals.g_Weekday > 5) {
                    weekdaynum = 5;
                    globals.g_Numerator = 1 - globals.g_Numerator;
                }
                else
                    weekdaynum = globals.g_Weekday - 1;

                globals.g_Weekday = weekdaynum;
                if(globals.g_Popup_Status != null)
                    globals.g_Popup_Status.dismiss();
            }

            // if right to left sweep event on screen
            if (x1 > x2) {
                if (globals.g_Weekday >= 5) {
                    weekdaynum = 1;
                    globals.g_Numerator = 1 - globals.g_Numerator;
                }
                else
                    weekdaynum = globals.g_Weekday + 1;

                globals.g_Weekday = weekdaynum;
                if(globals.g_Popup_Status != null)
                    globals.g_Popup_Status.dismiss();
            }

            initialize();

            break;
        }
    }
    return false;
}*/




