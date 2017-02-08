package u.Schedule.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import u.Schedule.R;
import u.Schedule.entity.globals;
import u.Schedule.File.FileRW;
import u.Schedule.entity.entities.Bunch;
import u.Schedule.entity.entities.Univercity;
import u.Schedule.entity.favouriteBunches;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
private static int Them = 1;
    private final int titleHome = R.string.app_name;
    private String spinnerUniversityValue;
    private String spinnerCourseValue;
    private String spinnerBunchValue;
    private  View view;


    private Fragment fragment = null;

    public int getTitleHome() {
        return titleHome;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_main, container, false);
        globals.fragmentId = 1;


        initNextButton();
        getSpinnerUniversityValue();
        setSpinnerFacultyValue();
        getSpinnerCourseValue();
        getSpinnerBunchValue();

      //  changeOrientation();




        final Spinner univercityList = (Spinner) view.findViewById(R.id.univercitys);
        final Spinner facultyList = (Spinner) view.findViewById(R.id.facultys);
        final Spinner coursesList = (Spinner) view.findViewById(R.id.courses);
        final Spinner bunchesList = (Spinner) view.findViewById(R.id.bunches);



        univercityList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setSpinnerFacultyValue();
                globals.g_Univercity = univercityList.getSelectedItem().toString();
                globals.g_UnivercityPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        facultyList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                getSpinnerCourseValue();
                globals.g_Faculty = facultyList.getSelectedItem().toString();
                globals.g_FacultyPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        coursesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                getSpinnerBunchValue();
                try{globals.g_Course = Integer.parseInt(coursesList.getSelectedItem().toString());}catch(Exception e){}
                globals.g_CoursePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        bunchesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                globals.g_Bunch = bunchesList.getSelectedItem().toString();
                globals.g_BunchPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return  view;
    }



    private int _getScreenOrientation(){
        return getResources().getConfiguration().orientation;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    public String getSpinnerUniversityValue() {

        final String[] universitys = new String[]{getResources().getString(R.string.UniversityEmpty), Univercity.ՀԱՊՀ}; //getResources().getStringArray(R.array.universitys);
        Spinner university = (Spinner) view.findViewById(R.id.univercitys);
        ArrayAdapter<String> UniversityAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, universitys);
        UniversityAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        university.setAdapter(UniversityAdapter);

        spinnerUniversityValue = university.getSelectedItem().toString();

        return spinnerUniversityValue;
    }


    public void setSpinnerFacultyValue() {
        List<String> facs = new ArrayList<String>();
        facs.add(getResources().getString(R.string.FacultyEmpty));
        Spinner university = (Spinner) view.findViewById(R.id.univercitys);

        if(university.getSelectedItem().toString().length() > 0)
        for(Bunch item:globals.BunchesList)
            if(item.getUnivercity().equals(university.getSelectedItem().toString()) && !facs.contains(item.getFaculty())) {
                facs.add(item.getFaculty());
            }

        Spinner faculty = (Spinner) view.findViewById(R.id.facultys);
        ArrayAdapter<String> FacultyAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, facs);
        FacultyAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        faculty.setAdapter(FacultyAdapter);
    }

    public String getSpinnerCourseValue() {
        Spinner faculty = (Spinner) view.findViewById(R.id.facultys);
        List<String> Courses = new ArrayList<>();
        Courses.add(getResources().getString(R.string.CourseEmpty));
        Spinner university = (Spinner) view.findViewById(R.id.univercitys);

        if(faculty.getSelectedItem().toString().length() > 0)
            for(Bunch item:globals.BunchesList)
                if(item.getFaculty().equals(faculty.getSelectedItem().toString())
                        && item.getUnivercity().equals(university.getSelectedItem().toString())
                        && !Courses.contains(String.valueOf(item.getCourse()))) {
                    Courses.add(String.valueOf(item.getCourse()));
                }

        Spinner courses = (Spinner) view.findViewById(R.id.courses);
        ArrayAdapter<String> CourseAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Courses);
        CourseAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        courses.setAdapter(CourseAdapter);

        spinnerCourseValue = courses.getSelectedItem().toString();
        return spinnerCourseValue;
    }

    public boolean haveDefaultBunch()
    {
        Bunch bunch;

        FileRW.read(getActivity());
        bunch = favouriteBunches.CompareBunchHashCode(FileRW.read(getActivity()));
        if(bunch != null)
        {
            globals.g_Start_Bunch = bunch.getBunch();
            globals.g_Univercity = bunch.getUnivercity();
            globals.g_Faculty = bunch.getFaculty();
            globals.g_Bunch = globals.g_Start_Bunch;
            globals.g_Course = bunch.getCourse();
        }
        return globals.g_Start_Bunch != null && globals.g_Start_Bunch != "";
    }

    public String getSpinnerBunchValue() {
        Spinner faculty = (Spinner) view.findViewById(R.id.facultys);
        List<String> Bunches = new ArrayList<>();
        Bunches.add(getResources().getString(R.string.BunchEmpty));
        Spinner university = (Spinner) view.findViewById(R.id.univercitys);
        Spinner courses = (Spinner) view.findViewById(R.id.courses);

        if(courses.getSelectedItem().toString().length() > 0)
            for(Bunch item:globals.BunchesList)
                if(String.valueOf(item.getCourse()).equals(courses.getSelectedItem().toString())
                        && item.getFaculty().equals(faculty.getSelectedItem().toString())
                        && item.getUnivercity().equals(university.getSelectedItem().toString())
                        && !Bunches.contains(item.getCourse())) {
                    Bunches.add(String.valueOf(item.getBunch()));
                }


        Spinner bunches = (Spinner) view.findViewById(R.id.bunches);
        ArrayAdapter<String> BunchesAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, Bunches);
        BunchesAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        bunches.setAdapter(BunchesAdapter);

        spinnerBunchValue = bunches.getSelectedItem().toString();
        return spinnerBunchValue;
    }


    private int getSpinnerFacultyValue() {
        Spinner spinner = (Spinner) view.findViewById(R.id.facultys);
        int spinnerFacultyValue = spinner.getSelectedItemPosition();
        return spinnerFacultyValue;
    }
    private void loadTheme() {
        String str = FileRW.read(getContext(), "theme");
        final int first = "1".hashCode();

        if ("3".equals(str.hashCode()))
            Them = 3;
        else if ("2".equals(str.hashCode()))
            Them = 2;
        else
            Them = 1;

    }
    private void initNextButton(){
        Button button = (Button)view.findViewById(R.id.button);
        int color = R.color.colorPrimary;
        loadTheme();

        switch (Them)
        {
            case 1:
                color  = R.color.colorPrimary;
                break;
            case 2:
                color  = R.color.colorPrimaryRed;
                break;
            case 3:
                color  = R.color.colorPrimaryBlue;
                break;
        }
        button.setBackgroundColor(getResources().getColor(color));
        button.setTextColor(Color.WHITE);
        button.setText(getResources().getString(R.string.select));
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(getActivity(),"OK",Toast.LENGTH_SHORT).show();
                    if(globals.g_Bunch.length() > 1 && globals.g_Bunch.hashCode() != getResources().getString(R.string.BunchEmpty).hashCode()) {
                        fragment = new LessonFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_conteyner, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });
        }
    }




}


