package u.Schedule.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      //  Log.i("Service Stops","Ohhh");
        context.startService(new Intent(context,NotificationService.class));
    }
}
