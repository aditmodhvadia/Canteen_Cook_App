package com.example.canteencookapp.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.canteencookapp.Activities.OrderListActivity;
import com.example.canteencookapp.R;

public class MyNotificationService extends Service {
    public MyNotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();

//        todo: add listener for data change in database
        customNotification(intent.getStringExtra("Category"));


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();

    }

    public void customNotification(String CATEGORY) {
        createNotificationChannel();

        Intent i = new Intent(this, OrderListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("Category", CATEGORY);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setContentTitle("New Order")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("A new Order is Placed")
                .setVibrate(new long[]{0, 400, 200, 400})
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Blah Blah Blah"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setColorized(true)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentIntent(pi);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(0, mBuilder.build());


    }

    private void createNotificationChannel() {
//        create notification channel only for Builds greater than Oreo(8.0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Order Channel";
            String description = "Primary display";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("default", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
