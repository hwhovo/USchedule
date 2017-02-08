package u.Schedule.File;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileRW {

    private static final String saveBunch = "saveBunch1";
    /// Default Bunch ReadWrite ------ Start

    public static void write(String str, Context con) {
        try {
            String data;

               data  = String.valueOf(str.hashCode());

            FileOutputStream fOut = con.openFileOutput(saveBunch, Context.MODE_PRIVATE);

            fOut.flush();
            if(str != "")
            fOut.write(data.getBytes());
            fOut.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String read(Context con) {
        String temp = "";File fil = new File(saveBunch);

        try { if(fil.exists())
            return "";

            FileInputStream fin = con.openFileInput(saveBunch);
            int c;


            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
        } catch (Exception e) {
        }
        return temp;
    }

    /// Default Bunch ReadWrite ------ END


    /// Favourite Bunch ReadWrite ------ Start
    public static void write(String data, Context con, String fileName, boolean eraseFile) {
        try {
            FileOutputStream fOut = con.openFileOutput(fileName, Context.MODE_PRIVATE);

            fOut.flush();
            if(data != "" || eraseFile == true)
                fOut.write(data.getBytes());
            fOut.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static String read(Context con, String fileName) {
        String temp = "";File fil = new File(fileName);

        try { if(fil.exists())
            return "";

            FileInputStream fin = con.openFileInput(fileName);
            int c;


            while ((c = fin.read()) != -1) {
                temp = temp + Character.toString((char) c);
            }
        } catch (Exception e) {
        }
        return temp;
    }

 /*   public static int favouriteBunchCount()
    {
        File fil;
        int count = 0;
        ///fil = new File(globals.favouriteBunchPrefix + "1");

        for (int i = 1; i < 1000; i++)
        {
            fil = new File(globals.favouriteBunchPrefix + String.valueOf(i));

            if(fil.exists())
                ++count;
            else
                break;
        }

        return count;
    }
*/

/*    public static boolean deleteFile(String fileName)
    {
        File file = new File(fileName);


         return file.delete();
    }*/
    /// Favourite Bunch ReadWrite ------ End


}


