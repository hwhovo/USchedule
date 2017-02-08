package u.Schedule.entity;

import android.content.Context;

import u.Schedule.File.FileRW;
import u.Schedule.entity.entities.Bunch;

import java.util.ArrayList;

/**
 * Created by Hovo CRM on 9/5/2016.
 */
public class favouriteBunches {

    public static void loadFavouriteBunchesList(Context con)
    {
        Bunch bunch = null;
        globals.favouriteBunchesList = new ArrayList<String>();
        String fileContent = "";

        fileContent = FileRW.read(con, globals.favouriteBunchPrefix);

        if(fileContent.equals(""))
            return;

        for (String item:
                fileContent.split(",")) {
            if(!item.equals(""))
 {
             try{   bunch = CompareBunchHashCode(item);}catch(Exception e){}
if(bunch != null)
                globals.favouriteBunchesList.add(bunch.getBunch());
            }
        }
    }


    //Bunch parser -- hash to bunch converter
    public static Bunch CompareBunchHashCode(String hash)
    {
        if(hash.equals(""))
            return null;
        int i = Integer.parseInt(hash);
        Bunch bunch;

        for (Bunch b: globals.BunchesList) {
            if(b.getBunch().hashCode() == i) {
                {
                    bunch = b;
                    return bunch;
                }
            }
        }
        return null;
    }



  /*  public static void deleteFavouriteBunch(String bunch, Context con) {
        int index = -1;
        loadFavouriteBunchesList(con);
        for (favouriteBunches item:
                globals.favouriteBunchesList) {
            if(item.bunch == bunch) {
                index = item.fileIndex;
                        break;
            }
        }

        boolean a = true;
        if(index != -1)
            a = FileRW.deleteFile(globals.favouriteBunchPrefix + String.valueOf(index));
            //  FileRW.write("", con,globals.favouriteBunchPrefix + String.valueOf(index));
        loadFavouriteBunchesList(con);

    }*/

    public static void removeFavouriteBunch(String bunch, Context con) {
        loadFavouriteBunchesList(con);

        String data = "";


        for (String item:
                globals.favouriteBunchesList) {
            if(!item.equals(bunch))
                data  += String.valueOf(item.hashCode()) + ",";
        }

        FileRW.write(data, con, globals.favouriteBunchPrefix, false);

        loadFavouriteBunchesList(con);
    }



    public static void addFavouriteBunch(String bunch, Context con) {
        loadFavouriteBunchesList(con);

        for (String item:
                globals.favouriteBunchesList) {
            if(item.equals(bunch))
                return;
        }

        String data = "";


        for (String item:
                globals.favouriteBunchesList) {
            data  += String.valueOf(item.hashCode()) + ",";

        }

        data += bunch.hashCode();

        FileRW.write(data, con, globals.favouriteBunchPrefix, false);

        loadFavouriteBunchesList(con);
    }
}
