package u.Schedule.File;


import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileWriteReade {

    private static final String saveBunch = "saveBunch123";

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
        String temp = "";
        File fil = new File(saveBunch);

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
}
