package reboot.com.firich.rebootschedule;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BackgroundService extends IntentService {
    private boolean bDebugOn = true;
    String strTagUtil = "Reboot.";

    private void dump_trace( String bytTrace)
    {
        if (bDebugOn)
            Log.d(strTagUtil, bytTrace);
    }
    public BackgroundService() {
        super("BackgroundService");
        /*
        // Custom date format
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        // Set the time for the nightly reboot
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        d1 = calendar.getTime();
        Calendar calendar2 =Calendar.getInstance();
        d2 = calendar2.getTime();

        long diff = d2.getTime() - d1.getTime();
        //long diffMinutes = diff / (60 * 1000) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        long reminderOfMin = minutes % 3;
        boolean IsReboot =  (reminderOfMin ==0);
        if (IsReboot) {
            //PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
            //pm.reboot("Reboot");
        }
        */
    }
    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        dump_trace("onHandleIntent start");
        long endTime = System.currentTimeMillis() + 2*60*1000;
        //

        dump_trace("Start="+getCurrentDate());
        //call MainActivity to show test devices screen.
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime - System.currentTimeMillis());
                    dump_trace("endTime="+endTime);
                    dump_trace("End="+getCurrentDate());
                    //RebootNow();
                    //shutdown_now();

                    dump_trace("onHandleIntent:run:to turn on screen by start new activity:");


                    /*
                    Intent intentTimeout = new Intent(this,MainActivity.class);

                    intentTimeout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intentTimeout.putExtra("from_sleep_schedule", true);
                    startActivity(intentTimeout);
                    */

                    dump_trace("startActivity:from_sleep_schedule");

                } catch (Exception e) {
                }
            }
        }
    }



    public void RebootNow() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        dump_trace("Now rebooting...");
        pm.reboot(null);
    }

    private void shutdown_now()
    {
        Intent i = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        i.putExtra("android.intent.extra.KEY_CONFIRM",false);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BackgroundService.this.startActivity(i);
    }

    String getCurrentDate()
    {
        Date newdate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = format.format(newdate);
        return date;
    }
}
