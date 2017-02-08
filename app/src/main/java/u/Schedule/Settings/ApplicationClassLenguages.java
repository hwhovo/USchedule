package u.Schedule.Settings;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

import u.Schedule.entity.globals;

public class ApplicationClassLenguages extends Application {

    private SharedPreferences preferences;
    private Locale locale;
    private String lang;
    //  private PendingIntent intent;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        changeLenguage();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        saveLanguag();
        /*Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);*/
    }

    private void changeLenguage(){
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        lang = preferences.getString("lang", "default");
        if (lang.equals("default")) {lang=getResources().getConfiguration().locale.getCountry();}
        locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
       // Log.i("Lang change", "Locale="+locale);
        getBaseContext().getResources().updateConfiguration(config, null);

    }



    private void saveLanguag(){
        locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);

    }



}