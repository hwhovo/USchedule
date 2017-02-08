package u.Schedule.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by hwhov on 2/6/2017.
 */
class MyAsync extends AsyncTask<String, Void, Void> {
    ProgressDialog pd;
    Context co;
    MainActivity ma;

    public MyAsync (MainActivity ma){
        this.ma= ma;
        this.co = ma;
        pd= new ProgressDialog(co);
    }

    @Override
    protected void onPreExecute() {
        this.pd.show();

        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(String... params) {
        ma.loadDataBase();
        ma.loadRepositoresFromDB();
        // do your database operations here
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        int fragmentChoice = ma.haveDefaultBunch() ? 11 : 1 ;

          ma.replaceFragement(fragmentChoice);

        pd.dismiss();
        // show db results and dismiss progress dialog pd.dismiss();
        super.onPostExecute(result);
    }
}