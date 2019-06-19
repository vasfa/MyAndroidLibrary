package ir.mlibs.varitynotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ss();
    }


    public void ss()
    {

        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custome_notif);
        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.second_costume_notif);

        remoteViews.setTextViewText(R.id.nt1, "");
        remoteViews.setTextViewText(R.id.nt2, "");
        remoteViews.setImageViewResource(R.id.ni1, R.drawable.back_fake);
        rv.setTextViewText(R.id.n2t, "");
//        rv.setTextViewText(R.id.n2t,"this is big VIEW !!!");
        rv.setImageViewResource(R.id.n2i, R.drawable.category2);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(NotificationsActivity.this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("asdad")
                        .setContentText("ertertert")
                        .setCustomBigContentView(remoteViews)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= 21)
            mBuilder.setVibrate(new long[0]);

        final Notification notification = mBuilder.build();
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification.bigContentView = rv;
        }
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(6, notification);
        Picasso.with(NotificationsActivity.this).load("http://maroofshow.com/image/950620-mohammad.jpg").into(rv, R.id.n2i, 6, notification);

    }
}
