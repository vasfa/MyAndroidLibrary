package ir.mlibs.varitynotification;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ir.mlibs.varitynotification.RestApi.AndroidDataItems;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.POWER_SERVICE;

/**
 * Created by vali on 2018-02-28.
 */

public class notification {
    Context main_context;
//    DataItems = Fake_notificationData(DataItems,"1");

    public void All_Notification_Those_Are_Only_Text(Context main_context,
                                                     String DataItems,
                                                     String NotificationLogo
    ) {
        this.main_context = main_context;
        DataItems = Fake_notificationData(DataItems,"1");
        if (NotificationLogo.equals("")) {
            _Only_Text(DataItems, null);
        } else {
            new sendNotification(main_context, DataItems)
                    .execute(NotificationLogo);
        }

    }
    public void All_Notification_Those_Are_Image(Context main_context,
                                                 String DataItems,
                                                 String NotificationBigImage
    ) {
        this.main_context = main_context;

        new generatePictureStyleNotification(main_context, DataItems).execute(NotificationBigImage);

    }




    private void _Only_Text(String DataItems, Bitmap bitmap) {
        //retDi.DS.get(0)-->notificationId
        //retDi.DS.get(1)-->notifyType
        //retDi.DS.get(2)-->title
        //retDi.DS.get(3)-->small Message
        //retDi.DS.get(4)-->big Message
        //retDi.DS.get(5)-->Notification positive button title
        //retDi.DS.get(6)-->Notification negetive button title
        //retDi.DS.get(7)-->NotificationImage
        //retDi.DS.get(8)-->ExpiteDate

        //retDi.DB.get(0)-->turnOnScreen
        //retDi.DB.get(1)-->have positive button in notification
        //retDi.DB.get(2)-->have negetive button in notification
        //retDi.DB.get(3)-->Show notification


        AndroidDataItems retDi = null;
        try {
            final ObjectMapper om = new ObjectMapper();
            retDi = om.readValue(DataItems, AndroidDataItems.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (retDi.DB.get(0)) {//turnOnScreen
            PowerManager.WakeLock screenLock = ((PowerManager) main_context.getSystemService(POWER_SERVICE)).newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
            screenLock.acquire();
        }
        Notification_sound(main_context, null);
        Intent intent = null;
        switch (retDi.DS.get(1)) {//notifyType

            case "1": {//open application
                //retDi.DS.get(9)-->packageName
                if (isAppInstalled(main_context, retDi.DS.get(9)))//packageName
                {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + retDi.DS.get(9))
                            , main_context, OnClickBroadcastReceivers.class);
                    intent.putExtra("TYPE", "1");
                    intent.putExtra("Content", retDi.DS.get(9));
                    intent.putExtra("noti_id", retDi.DS.get(0));
                    intent.putExtra("myclass", "");
                }
            }
            break;
            case "2": {//open site
                //retDi.DS.get(9)-->URL
                if (!retDi.DS.get(9).startsWith("http://") && !retDi.DS.get(9).startsWith("https://"))
                    retDi.DS.set(9, "http://" + retDi.DS.get(9));
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(retDi.DS.get(5)), main_context, OnClickBroadcastReceivers.class);
                intent.putExtra("TYPE", "2");
                intent.putExtra("Content", retDi.DS.get(9));
                intent.putExtra("noti_id", retDi.DS.get(0));
                intent.putExtra("myclass", "");
            }
            break;
            case "3": {
                //retDi.DS.get(7)-->pName
                //retDi.DS.get(8)-->activityName
                //retDi.DL--->Activity Exteras
                if (retDi.DS.get(9).equals("") || retDi.DS.get(10).equals("")) {
                    return;
                } else {
                    Class myclass = null;
                    String paths = "";
                    paths = retDi.DS.get(9) + "." + retDi.DS.get(10);

                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""), main_context, OnClickBroadcastReceivers.class);
                    intent.putExtra("TYPE", "3");
                    intent.putExtra("Content", DataItems); //retDi.DL--->Activity Exteras
                    intent.putExtra("noti_id", retDi.DS.get(0));
                    intent.putExtra("myclass", paths);
                }


            }
            break;
            case "4": {
                //retDi.DS.get(7)-->number

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""), main_context, OnClickBroadcastReceivers.class);
                intent.putExtra("TYPE", "4");
                intent.putExtra("Content", retDi.DS.get(9));
                intent.putExtra("noti_id", retDi.DS.get(0));
                intent.putExtra("myclass", "");
            }
            break;
            case "5": {
                //retDi.DS.get(7)-->number
                //retDi.DS.get(8)-->message text

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""), main_context, OnClickBroadcastReceivers.class);
                intent.putExtra("TYPE", "5");
                intent.putExtra("Content", retDi.DS.get(9));
                intent.putExtra("noti_id", retDi.DS.get(0));
                intent.putExtra("myclass", retDi.DS.get(10));
            }
            break;
            case "6": {
                //retDi.DS.get(7)-->emailAccoutTo
                //retDi.DS.get(8)-->emailAccoutCC
                //retDi.DS.get(9)-->emailTitle
                //retDi.DS.get(10)-->emailmessage
                //retDi.DS.get(11)-->emailChosserTitle

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""), main_context, OnClickBroadcastReceivers.class);
                intent.putExtra("TYPE", "6");
                intent.putExtra("Content", DataItems);
                intent.putExtra("noti_id", retDi.DS.get(0));
                intent.putExtra("myclass", "");
            }
            break;
            case "7": {
//                NotificationManager notificationManager = (NotificationManager) main_context.getApplicationContext()
//                        .getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.cancel(NOTIFICATIONID);

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""), main_context, OnClickBroadcastReceivers.class);
                intent.putExtra("TYPE", "7");
                intent.putExtra("Content", "");
                intent.putExtra("noti_id", retDi.DS.get(0));
                intent.putExtra("myclass", "");
            }
            break;
            case "8": {//this is for downloading file
//                NotificationManager notificationManager = (NotificationManager) main_context.getApplicationContext()
//                        .getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.cancel(NOTIFICATIONID);

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""), main_context, OnClickBroadcastReceivers.class);
                intent.putExtra("TYPE", "8");
                intent.putExtra("Content", retDi.DS.get(9));
                intent.putExtra("noti_id", retDi.DS.get(0));
                intent.putExtra("myclass", "");
            }
            break;


        }

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(main_context,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent deleteintent = new Intent(main_context, OnCancelBroadcastReceiver.class);
        PendingIntent deletependingIntent = PendingIntent.getBroadcast(main_context, 0, deleteintent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(main_context)
                .setAutoCancel(true)
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(BitmapFactory.decodeResource(main_context.getResources(), R.drawable.ic_launcher))
                .setContentTitle(retDi.DS.get(2))
                .setContentText(retDi.DS.get(3))
                .setContentIntent(pendingIntent)
                .setDeleteIntent(deletependingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{500, 500, 700});

        if (bitmap != null)
            builder.setLargeIcon(bitmap);

        Actions(builder, DataItems);


        if (Build.VERSION.SDK_INT >= 21) builder.setVibrate(new long[0]);

        CharSequence cs = retDi.DS.get(4);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(cs));

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) main_context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(Integer.parseInt(retDi.DS.get(0)), notification);


    }


    private class sendNotification extends AsyncTask<String, Void, Bitmap> {

        Context ctx;
        String DataItems;

        public sendNotification(Context context, String DataItems) {
            super();
            this.ctx = context;
            this.DataItems = DataItems;

        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {

                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            super.onPostExecute(result);
            try {
                _Only_Text(DataItems, result);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /////////////////////////////////
    /////////////////////////////////


    public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

        private Context mContext;
        String DataItems;


        public generatePictureStyleNotification(Context context, String DataItems) {
            super();
            this.mContext = context;
            this.DataItems = DataItems;

        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            {
                try {
                    //retDi.DS.get(0)-->notificationId
                    //retDi.DS.get(1)-->notifyType
                    //retDi.DS.get(2)-->title
                    //retDi.DS.get(3)-->small Message
                    //retDi.DS.get(4)-->big Message
                    //retDi.DS.get(5)-->Notification positive button title
                    //retDi.DS.get(6)-->Notification negetive button title

                    //retDi.DB.get(0)-->turnOnScreen
                    //retDi.DB.get(1)-->have positive button in notification
                    //retDi.DB.get(2)-->have negetive button in notification
                    //retDi.DB.get(3)-->Show notification


                    AndroidDataItems retDi = null;
                    try {
                        final ObjectMapper om = new ObjectMapper();
                        retDi = om.readValue(DataItems, AndroidDataItems.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (retDi.DB.get(0)) {//turnOnScreen
                        PowerManager.WakeLock screenLock = ((PowerManager) main_context.getSystemService(POWER_SERVICE)).newWakeLock(
                                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
                        screenLock.acquire();
                    }
                    Notification_sound(main_context, null);
                    Intent intent = null;
                    switch (retDi.DS.get(1)) {//notifyType

                        case "1": {//open application
                            //retDi.DS.get(9)-->packageName
                            if (isAppInstalled(main_context, retDi.DS.get(9)))//packageName
                            {
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + retDi.DS.get(9))
                                        , main_context, OnClickBroadcastReceivers.class);
                                intent.putExtra("TYPE", "1");
                                intent.putExtra("Content", retDi.DS.get(9));
                                intent.putExtra("noti_id", retDi.DS.get(0));
                                intent.putExtra("myclass", "");
                            }
                        }
                        break;
                        case "2": {//open site
                            //retDi.DS.get(9)-->URL
                            if (!retDi.DS.get(9).startsWith("http://") && !retDi.DS.get(9).startsWith("https://"))
                                retDi.DS.set(9, "http://" + retDi.DS.get(9));
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(retDi.DS.get(5)), main_context, OnClickBroadcastReceivers.class);
                            intent.putExtra("TYPE", "2");
                            intent.putExtra("Content", retDi.DS.get(9));
                            intent.putExtra("noti_id", retDi.DS.get(0));
                            intent.putExtra("myclass", "");
                        }
                        break;
                        case "3": {
                            //retDi.DS.get(7)-->pName
                            //retDi.DS.get(8)-->activityName
                            //retDi.DL--->Activity Exteras
                            if (retDi.DS.get(9).equals("") || retDi.DS.get(10).equals("")) {
                                return;
                            } else {
                                Class myclass = null;
                                String paths = "";
                                paths = retDi.DS.get(9) + "." + retDi.DS.get(10);

                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""), main_context, OnClickBroadcastReceivers.class);
                                intent.putExtra("TYPE", "3");
                                intent.putExtra("Content", DataItems); //retDi.DL--->Activity Exteras
                                intent.putExtra("noti_id", retDi.DS.get(0));
                                intent.putExtra("myclass", paths);
                            }


                        }
                        break;
                        case "4": {
                            //retDi.DS.get(7)-->number

                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""), main_context, OnClickBroadcastReceivers.class);
                            intent.putExtra("TYPE", "4");
                            intent.putExtra("Content", retDi.DS.get(9));
                            intent.putExtra("noti_id", retDi.DS.get(0));
                            intent.putExtra("myclass", "");
                        }
                        break;
                        case "5": {
                            //retDi.DS.get(7)-->number
                            //retDi.DS.get(8)-->message text

                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""), main_context, OnClickBroadcastReceivers.class);
                            intent.putExtra("TYPE", "5");
                            intent.putExtra("Content", retDi.DS.get(9));
                            intent.putExtra("noti_id", retDi.DS.get(0));
                            intent.putExtra("myclass", retDi.DS.get(10));
                        }
                        break;
                        case "6": {
                            //retDi.DS.get(7)-->emailAccoutTo
                            //retDi.DS.get(8)-->emailAccoutCC
                            //retDi.DS.get(9)-->emailTitle
                            //retDi.DS.get(10)-->emailmessage
                            //retDi.DS.get(11)-->emailChosserTitle

                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""), main_context, OnClickBroadcastReceivers.class);
                            intent.putExtra("TYPE", "6");
                            intent.putExtra("Content", DataItems);
                            intent.putExtra("noti_id", retDi.DS.get(0));
                            intent.putExtra("myclass", "");
                        }
                        break;
                        case "7": {
//                NotificationManager notificationManager = (NotificationManager) main_context.getApplicationContext()
//                        .getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.cancel(NOTIFICATIONID);

                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""), main_context, OnClickBroadcastReceivers.class);
                            intent.putExtra("TYPE", "7");
                            intent.putExtra("Content", "");
                            intent.putExtra("noti_id", retDi.DS.get(0));
                            intent.putExtra("myclass", "");
                        }
                        break;
                        case "8": {//this is for downloading file
//                NotificationManager notificationManager = (NotificationManager) main_context.getApplicationContext()
//                        .getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.cancel(NOTIFICATIONID);

                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""), main_context, OnClickBroadcastReceivers.class);
                            intent.putExtra("TYPE", "8");
                            intent.putExtra("Content", retDi.DS.get(9));
                            intent.putExtra("noti_id", retDi.DS.get(0));
                            intent.putExtra("myclass", "");
                        }
                        break;


                    }


                    Intent deleteintent = new Intent(main_context, OnCancelBroadcastReceiver.class);
                    PendingIntent deletependingIntent = PendingIntent.getBroadcast(main_context, 0, deleteintent, 0);


                    PendingIntent pendingIntent =
                            PendingIntent.getBroadcast(main_context,
                                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    final RemoteViews remoteViews = new RemoteViews(main_context.getPackageName(), R.layout.custome_notif);
                    RemoteViews rv = new RemoteViews(main_context.getPackageName(), R.layout.second_costume_notif);

                    remoteViews.setTextViewText(R.id.nt1, "");
                    remoteViews.setTextViewText(R.id.nt2, "");
                    remoteViews.setImageViewResource(R.id.ni1, R.drawable.back_fake);
                    rv.setTextViewText(R.id.n2t, retDi.DS.get(4));
                    rv.setTextViewText(R.id.n22t, retDi.DS.get(2));
                    rv.setImageViewBitmap(R.id.n2i, result);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(main_context)
                                    .setSmallIcon(getNotificationIcon())
                                    .setContentTitle(retDi.DS.get(2))
                                    .setContentText(retDi.DS.get(3))
                                    .setCustomBigContentView(remoteViews)
                                    .setContentIntent(pendingIntent)
                                    .setDeleteIntent(deletependingIntent)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                                    .setAutoCancel(true);

                    Actions(mBuilder, DataItems);


                    if (Build.VERSION.SDK_INT >= 21)
                        mBuilder.setVibrate(new long[0]);

                    final Notification notification = mBuilder.build();
                    if (Build.VERSION.SDK_INT >= 16) {
                        notification.bigContentView = rv;
                    }
                    NotificationManager mNotificationManager = (NotificationManager) main_context.getSystemService(NOTIFICATION_SERVICE);
                    mNotificationManager.notify(Integer.parseInt(retDi.DS.get(0)), notification);

                } catch (Exception ex) {
                    String asd = "";
                }

            }
        }
    }

    public void Actions(NotificationCompat.Builder action_builder,
                        String data_items) {

        AndroidDataItems retDi = null;
        try {
            final ObjectMapper om = new ObjectMapper();
            retDi = om.readValue(data_items, AndroidDataItems.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Yes intent
        if (retDi.DB.get(1)) {
            Intent yesReceive = new Intent();
            yesReceive.putExtra("DataItems", data_items);
            yesReceive.putExtra("NotificationId", retDi.DS.get(0));
            yesReceive.setAction("YES_ACTION");
            PendingIntent pendingIntentYes = PendingIntent.getBroadcast(main_context, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
            action_builder.addAction(R.drawable.menu2, retDi.DS.get(5), pendingIntentYes);
        }


//No intent
        if (retDi.DB.get(2)) {
            Intent noReceive = new Intent();
            noReceive.putExtra("NotificationId", retDi.DS.get(0));
            noReceive.setAction("NO_ACTION");
            PendingIntent pendingIntentNo = PendingIntent.getBroadcast(main_context
                    , 12345, noReceive, PendingIntent.FLAG_UPDATE_CURRENT);
            action_builder.addAction(R.drawable.menu3, retDi.DS.get(6), pendingIntentNo);
        }


    }

    //////////////////////////////////////
    ////////////////////////////////////////

    private int getNotificationIcon() {//false-->color notification   true---->black and white notification
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT <= Build.VERSION_CODES.M);
        return useWhiteIcon ? R.drawable.ic_launcher : R.drawable.ic_launcher;
    }

    public void Notification_sound(Context context, File SountPath) {
        Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context, defaultRingtoneUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }


    public String Fake_notificationData(String t,String typeMessage) {
        AndroidDataItems dataItems = new AndroidDataItems();
        dataItems.DS = new ArrayList<>();
        dataItems.DB = new ArrayList<>();

        dataItems.Id=Long.parseLong(typeMessage);
        //this is default
        dataItems.DS.add("0");
        dataItems.DS.add(t + "");
        dataItems.DS.add("btn_doNoting_notification");
        dataItems.DS.add("small");
        dataItems.DS.add("big");
        dataItems.DS.add("تایید");
        dataItems.DS.add("انصراف");
        dataItems.DS.add("");//notification logo or banner
        dataItems.DS.add("");//expire date


        //this is default
        dataItems.DB.add(true);//this is for turnOnScreen or not
        dataItems.DB.add(true);//this is ok button
        dataItems.DB.add(true);//this si no button
        dataItems.DB.add(true);//this is for showing notification or not


        int type = Integer.parseInt(t);
        switch (type) {
            case 1: {
                dataItems.DS.add("ir.vasfa.notificationlibrary");
            }
            break;
            case 2: {
                dataItems.DS.add("www.google.com");
            }
            break;
            case 3: {
                dataItems.DS.add("ir.vasfa.notificationlibrary");
                dataItems.DS.add("TestActivity");
            }
            break;
            case 4: {
                dataItems.DS.add("09195884906");
            }
            break;
            case 5: {
                dataItems.DS.add("09195884906");
                dataItems.DS.add("this is from notification");
            }
            break;
            case 6: {
                dataItems.DS.add("v.naeimabadi@gmail.com;vasfacompany@gmail.com");
                dataItems.DS.add("vasfa@gmail.com;company@gmail.com");
                dataItems.DS.add("Notification");
                dataItems.DS.add("this is notificaiton");
                dataItems.DS.add("Notificaiton1");
            }
            break;
            case 8: {
                dataItems.DS.add("download file url");
                dataItems.DS.add("file name");
            }
            break;
        }


        String resJson = "";
        final ObjectMapper om = new ObjectMapper();
        try {
            resJson = om.writer().withDefaultPrettyPrinter()
                    .writeValueAsString(dataItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resJson;
    }

}
