package u.Schedule.Settings;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;


import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import u.Schedule.Activitys.MainActivity;
import u.Schedule.File.FileRW;
import u.Schedule.R;
import u.Schedule.Service.NotificationService;
import u.Schedule.entity.globals;


public class SettingsActivity extends AppCompatPreferenceActivity {

    private GoogleApiClient client;

    private static boolean switchChange = false;
    SwitchPreference switchPreference1;
    private static int Them = 1;


    private static boolean vibrate = false;
    private static boolean ringtone = false;

    private SharedPreferences sPref;
    private final String SAVED_BOOLEAN = "saved_boolean";


    private PendingIntent intent;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   loadTheme();
        setupActionBar();
        addPreferencesFromResource(R.xml.pref_general);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        setTheme();
        getOrientationEvent();
        getValueOfNotification();
        loadOrientationValue();
    }

    private void loadTheme() {
        String str = FileRW.read(getApplicationContext(), "theme");
        final int first = "1".hashCode();

        if ("3".hashCode() == str.hashCode())
            Them = 3;
        else if ("2".hashCode() == str.hashCode())
            Them = 2;
        else
            Them = 1;

    }

    //set Progrmme Theme

    private void setTheme(){
        ListPreference listPreference = (ListPreference) findPreference("settings_theme_list");

        final FrameLayout toolbar = (FrameLayout) findViewById(R.id.fragment_conteyner);

        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String value = (String) newValue;

                switch (value) {
                    case "1":
                        Them = 1;
                //        toolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                       // toolbar.setcolo`
                        finish();
                        startActivity(getIntent());

                        /*Toast.makeText(getApplicationContext(),
                                "Green",
                                Toast.LENGTH_SHORT).show();*/
                        // getActivity().setTheme(R.style.AppTheme);
                        break;
                    case "2":
                        Them = 2;
                        finish();
                        startActivity(getIntent());
//                        toolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));

                        /*Toast.makeText(getApplicationContext(),
                                "Red",
                                Toast.LENGTH_SHORT).show();*/
                        //getActivity().setTheme(R.style.AppThemeRed);
                        break;
                    case "3":
                        Them = 3;
                        finish();
                        startActivity(getIntent());
               //         toolbar.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));

                        /*Toast.makeText(getApplicationContext(),
                                "Blue",
                                Toast.LENGTH_SHORT).show();*/
                        //getActivity().setTheme(R.style.AppThemeBlue);
                }
                saveTheme(Them);

                return true;
            }
        });
    }
    private void setThemeActionBar(ActionBar toolbar){
        int theme = SettingsActivity.getThem();
        loadTheme();
        theme = Them;
        int color = R.color.colorPrimaryDark;

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (theme ==1) {
            color = R.color.colorPrimaryDark;
            toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
            //assert toolbar != null;
            //.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if (theme == 2){
            assert toolbar != null;
            color = R.color.colorPrimaryDarkRed;
            toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryRed)));

       //     toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryRed));//(R.color.colorPrimaryRed);
        }
        if (theme == 3){
            assert toolbar != null;
            color = R.color.colorPrimaryDarkBlue;
            toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimaryBlue)));

            //   toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryBlue));//(R.color.colorPrimaryBlue);
        }
        Them = theme;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            initStatusBarColor(color);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initStatusBarColor(int color) {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, color));

    }
    private  void saveOrientationValue() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(SAVED_BOOLEAN,switchChange);
        ed.apply();
        //Toast.makeText(SettingsActivity.this, "Saved", Toast.LENGTH_SHORT).show();
    }
    private void loadOrientationValue(){
        switchPreference1 =  (SwitchPreference) findPreference("Orientation_on_of_switch");
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        boolean booleanValue = sPref.getBoolean(SAVED_BOOLEAN,switchChange);
            switchPreference1.setChecked(booleanValue);
        if (booleanValue){
         //   Toast.makeText(SettingsActivity.this, "Value loaded", Toast.LENGTH_SHORT).show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }else{
         //   Toast.makeText(SettingsActivity.this, "Value Noloaded", Toast.LENGTH_SHORT).show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        }
    }


    //get orientation switch button value true or false
    private void getOrientationEvent(){
        switchPreference1 =  (SwitchPreference) findPreference("Orientation_on_of_switch");

        if (switchPreference1 != null)
            switchPreference1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                @Override
                public boolean onPreferenceChange(Preference preference, Object booleanValue) {
                    boolean bool = (Boolean) booleanValue;
                    switchChange = bool;
                    if (switchChange) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        saveOrientationValue();
                        globals.g_OrintationIsOn = true;
                    }
                    else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
                        saveOrientationValue();
                        globals.g_OrintationIsOn = false;
                    }
                    return true;
                }
            });
        else {
     /*       Toast.makeText(this.getApplicationContext(),
                    "NO",
                    Toast.LENGTH_SHORT).show();*/
        }
    }

    //restart the programm
    private void restart(){
        Preference restart = (Preference) findPreference("restart");

        intent = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), MainActivity.class), 0);

        restart.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, intent);
                System.exit(1);
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  loadTheme();
        setupActionBar();
    }

    //get Value of Notification switchButtons
    private void getValueOfNotification(){
        final SwitchPreference switchPreference = (SwitchPreference) findPreference("notifications_new_message");
        final SwitchPreference switchPreference1 = (SwitchPreference) findPreference("notifications_new_message_vibrate");
        final SwitchPreference switchPreferenceRingtone = (SwitchPreference) findPreference("notifications_new_message_ringtone");

        if (switchPreference != null) {

            switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

                @Override
                public boolean onPreferenceChange(Preference preference, Object booleanValue) {
                    switchPreference1.setChecked(false);
                    switchPreferenceRingtone.setChecked(false);
                    vibrate = false;
                    ringtone  = false;
                    saveVibrate();
                    saveRingtone();
                    boolean bool = (Boolean) booleanValue;
                    if (bool == true) {
                        startService(new Intent(getApplication(), NotificationService.class));
                    } else {
                        stopService(new Intent(getApplication(), NotificationService.class));
                    }

                    return true;


                }
            });
        }


        if (switchPreference1 != null) {
            switchPreference1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object vibrateSwitch1) {

                    boolean vibrateSwitch = (Boolean) vibrateSwitch1;
                    vibrate = vibrateSwitch;
                    saveVibrate();
                    return true;

                }
            });
        }


        if (switchPreferenceRingtone != null) {
            switchPreferenceRingtone.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object RingtoneValue) {
                    boolean RingtoneSwitch = (Boolean) RingtoneValue;
                    ringtone = RingtoneSwitch;
                    saveRingtone();
                    return true;
                }
            });
        }
    }

    //NOTIFICATION FUNCTIONS
    public static boolean isVibrate() {
        return vibrate;
    }

    public static boolean isRingtone() {
            return ringtone;
    }

    //GENERAL FUNCTIONS
    public static boolean getSwitchChange() {
        return switchChange;
    }

    public static int getThem() {

        return Them;
    }


    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up buttonselect in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getTitle());
            setThemeActionBar(actionBar);

        }
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }


    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveOrientationValue();
    }

    public void saveVibrate()
    {

        FileRW.write((vibrate?"true":"false"), getApplicationContext(), "vibrate", true);

    }
    public void saveRingtone()
    {

        FileRW.write((ringtone?"true":"false"), getApplicationContext(), "ringtone", true);

    }

    public void saveTheme(int colorNumber)
    {

        FileRW.write((String.valueOf(colorNumber)), getApplicationContext(), "theme", true);

    }
}
