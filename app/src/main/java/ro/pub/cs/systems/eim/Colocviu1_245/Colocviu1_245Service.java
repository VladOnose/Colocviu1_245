package ro.pub.cs.systems.eim.Colocviu1_245;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class Colocviu1_245Service extends Service {
     class ProcessingThread extends Thread {
        private int sum;
        private Context context;

        public ProcessingThread(Context context, int sum) {
            this.sum = sum;
            this.context = context;
        }

        public void sendSum() {
            Intent intent = new Intent();
            intent.putExtra(Constants.FORM_SERVICE, String.valueOf(sum) + "@" + new SimpleDateFormat("yyyy/mm/dd hh:mm").format(Calendar.getInstance().getTime()));
            context.sendBroadcast(intent);
        }

        public void run() {
            try {
                Thread.sleep(2000);
                sendSum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Colocviu1_245Service() {
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
         int sum = intent.getExtras().getInt(Constants.TO_SERVICE);
         ProcessingThread thread = new ProcessingThread(this, sum);
        return START_REDELIVER_INTENT;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
