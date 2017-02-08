package u.Schedule.Title;


import u.Schedule.Fragments.FavoriteFragment;
import u.Schedule.Fragments.HomeFragment;
import u.Schedule.Fragments.LessonFragment;

public class getTitle {


    private HomeFragment homeFragment = new HomeFragment();
    private FavoriteFragment favoriteFragment = new FavoriteFragment();
    private LessonFragment lessonFragment = new LessonFragment();


    private int titleHome = homeFragment.getTitleHome();
    private int titleFavorite = favoriteFragment.getTitleFavorite();
    private int titleLesson = lessonFragment.getTitleLesson();


    public getTitle() {
    }

    public  int getTitleFavorite2(){return titleFavorite;}


    public int getTitleHome() {
        return titleHome;
    }

    public int getTitleLesson() {
        return titleHome;
    }

}
