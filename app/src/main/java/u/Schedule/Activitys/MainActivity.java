package u.Schedule.Activitys;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import u.Schedule.File.FileRW;
import u.Schedule.Fragments.FavoriteFragment;
import u.Schedule.Fragments.HomeFragment;
import u.Schedule.R;
import u.Schedule.Title.getTitle;
import u.Schedule.database.DatabaseHelper;
import u.Schedule.entity.entities.Bunch;
import u.Schedule.entity.favouriteBunches;
import u.Schedule.entity.globals;
import u.Schedule.Fragments.LessonFragment;

import u.Schedule.Settings.SettingsActivity;


public class MainActivity extends AppCompatActivity {

    private static int Them = 1;
    private static final int LAYOUT = R.layout.activity_main;
    final Context con = this;
    private ActionBarDrawerToggle toggle;
    private static Toolbar toolbar = null;
    private NavigationView navigationView = null;
    private DrawerLayout drawerLayout;
    getTitle gettitle = new getTitle();
    private DatabaseHelper mDatabaseHelper;

    SharedPreferences sPref;
    private final String SAVED_BOOLEAN = "saved_boolean";

public static int getThem() {
    return Them;
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(LAYOUT);
    //    loadTheme();
        MainActivity ma = this;
        new MyAsync(ma).execute();
         //  loadDataBase();

       setTheme(R.style.AppTheme);

        setThemeActionBar();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_conteyner, new HomeFragment()).commit();
        }

        initToolbar();
        initNavigationView();
        loadOrientationValue();

        favouriteBunches.loadFavouriteBunchesList(con);




        //int fragmentChoice = haveDefaultBunch() ? 11 : 1 ;

      //  replaceFragement(fragmentChoice);

       // setThemeActionBar();
    }



    @Override
    protected void onResume() {
        super.onResume();
     //   loadRepositoresFromDB();
    }

    //region Load DataBase
    public void loadDataBase()
    {
        mDatabaseHelper = new DatabaseHelper(this);

        //check exist database
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if (!database.exists()){
            mDatabaseHelper.getReadableDatabase();

            //Copy db

            if (copyDatabase(this)){
            //    Toast.makeText(this,"Copy database succes 1",Toast.LENGTH_SHORT).show();
               // loadRepositoresFromDB();
              //  Toast.makeText(this,"Copy database succes 2",Toast.LENGTH_SHORT).show();
            }else {
             //   Toast.makeText(this,"Copy database error",Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    public void loadRepositoresFromDB()
    {
        mDatabaseHelper.loadLessonTimes();
        mDatabaseHelper.loadBunchesList();
        mDatabaseHelper.loadLessonsList();
    }

    private  boolean copyDatabase(Context context){

        try{
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0){
                outputStream.write(buff,0,length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity","DB Copied");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //endregion




    private void loadTheme() {
        String str = FileRW.read(getApplicationContext(), "theme");

        if ("3".equals(str))
            Them = 3;
        else if ("2".equals(str))
            Them = 2;
        else
            Them = 1;

    }
    private void setThemeActionBar(){
        loadTheme();
        int theme = Them;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        int color = R.color.colorPrimaryDark;
        if (theme ==1) {
            assert toolbar != null;
            color = R.color.colorPrimaryDark;
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }
        if (theme == 2){
            assert toolbar != null;
            color = R.color.colorPrimaryDarkRed;

            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryRed));//(R.color.colorPrimaryRed);
        }
        if (theme == 3){
            assert toolbar != null;
            color = R.color.colorPrimaryDarkBlue;

            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryBlue));//(R.color.colorPrimaryBlue);
        }
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

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    private   void saveOrientationValue() {
        boolean bool = SettingsActivity.getSwitchChange();
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(SAVED_BOOLEAN,bool);
        ed.commit();
      //  Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
    }
    private void loadOrientationValue(){
        boolean bool = SettingsActivity.getSwitchChange();
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        boolean booleanValue = sPref.getBoolean(SAVED_BOOLEAN,bool);
        if (booleanValue){
           // Toast.makeText(MainActivity.this, "Value loaded", Toast.LENGTH_SHORT).show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }else{
          //  Toast.makeText(MainActivity.this, "Value Noloaded", Toast.LENGTH_SHORT).show();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        }
    }


    @Override
    public void onBackPressed() {

        if(haveDefaultBunch()) {
            if (globals.fragmentId == 11)
                finish();
            else
                replaceFragement(11);//go to Lesson Fragment

        }else
        if(globals.fragmentId == 1)
        {

            finish();
        }
        else
        { replaceFragement(1);//go to Home Fragment

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

       /* menu.getItem(R.id.HomeItem).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return false;
            }
        });*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about:
                        ToolbarItemSelected(2);
                        break;
                    case R.id.FeedBack:
                        ToolbarItemSelected(1);
                        break;
                    }
                return true;
            }
        });

        toolbar.inflateMenu(R.menu.menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private void initNavigationView() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.view_navigation_open,R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.SettingsItem:

                        replaceFragement(0);
                        break;
                    case R.id.HomeItem:
                        toolbar.setTitle(gettitle.getTitleHome());
                        replaceFragement(1);
                        break;
                    case R.id.ShareItem:
                        replaceFragement(2);
                        break;
                    case R.id.Update:
                        replaceFragement(3);
                        break;
                    case R.id.FavoriteItem:
                        toolbar.setTitle(gettitle.getTitleFavorite2());
                        replaceFragement(12);
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void replaceFragement(int pos ){
        Log.w("MainActivity","Replace Fragment Copied");

        Fragment fragment = null;

        switch (pos){
            case 0:
               Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
               startActivity(intent);
                //fragment =  new SettingsFragment();
                break;
            case 1:
                fragment = new HomeFragment();
                break;
            case 2:
                initSharing();
                break;
            case 3:
                initUpdate();
                break;
            case 10:
              //  fragment = new AboutFragment();
                break;
            case 11:
                fragment = new LessonFragment();
                break;
            case 12:
                fragment = new FavoriteFragment();
                break;
        }
        if (null != fragment){

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_conteyner, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }



    private void initSharing(){

        Intent openLinIntent = new Intent(android.content.Intent.ACTION_SEND);
        openLinIntent.setType("text/plain");
        openLinIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        openLinIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Share_IN));
        openLinIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=u.Schedule&hl=ru");
        startActivity(Intent.createChooser(openLinIntent, getString(R.string.Share_IN)));
            }

    private void initUpdate(){
        Uri address = Uri.parse("https://play.google.com/store/apps/details?id=u.Schedule&hl=ru");
        Intent update = new Intent(Intent.ACTION_VIEW,address);
        startActivity(update);
    }

    private void ToolbarItemSelected(int pos){
        //get information in phone
        String DeviceInfo=getString(R.string.Debug_Info_title);
        DeviceInfo += "\n OS Version: " + System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
        DeviceInfo += "\n OS API Level: " + android.os.Build.VERSION.SDK_INT;
        DeviceInfo += "\n Device: " + android.os.Build.DEVICE;
        DeviceInfo += "\n Model (and Product): " + android.os.Build.MODEL + " ("+ android.os.Build.PRODUCT + ")" + "\n";

            // 1-> Feedback
        if (pos == 1){

            Intent Email = new Intent(Intent.ACTION_SEND);
            Email.setType("text/email");
            Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "app.uschedule@gmail.com" });
            Email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Uschedule_FeedBack_title));
            Email.putExtra(Intent.EXTRA_TEXT, DeviceInfo);
            try {
                startActivity(Intent.createChooser(Email, getString(R.string.Send_email)));
            } catch (android.content.ActivityNotFoundException ex) {
              /*  Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();*/
            }
        }
            // 2 -> About
        if (pos == 2){
            String link = "<a href=\"https://web.facebook.com/universityschedule/?ref=aymt_homepage_panel\">\nFacebook</a>";
            String message = getString(R.string.About_Context) + "\n" + getString(R.string.About_Context_Facebook) + link ;
            Spanned myMessage = Html.fromHtml(message);

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.About)
                    .setMessage(myMessage)
                    .setIcon(R.drawable.ic_information_outline)
                    .setCancelable(false)
                    .setNegativeButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            TextView msgTxt = (TextView) alert.findViewById(android.R.id.message);
            msgTxt.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveOrientationValue();
    }

    public boolean haveDefaultBunch()
    {
        Log.w("MainActivity","haveDefaultBunch Copied");

        Bunch bunch;

       // FileRW.read(con);
        bunch = favouriteBunches.CompareBunchHashCode(FileRW.read(con));
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

    public static void setToolbarTitle(String str)
    {
        toolbar.setTitle(str);
    }
}
