package ir.mlibs.varitynotification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.PowerManager;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ir.mlibs.varitynotification.RestApi.AndroidDataItems;

import static android.content.Context.POWER_SERVICE;

/**
 * Created by vali on 2018-10-23.
 */

public class NotificationButtonReceiver extends BroadcastReceiver {

    private SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String YES_ACTION = "YES_ACTION";
        String NO_ACTION = "NO_ACTION";

        if (YES_ACTION.equals(action)) {
            String DataItems = "";
            String NotificationId = "";
            NotificationId = intent.getStringExtra("NotificationId");
            DataItems = intent.getStringExtra("DataItems");
            if(!DismissNotification(context,NotificationId))
            {
                CallIntent(DataItems, context);


                NotificationManager notificationManager = (NotificationManager) context.getApplicationContext()
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(Integer.parseInt(NotificationId));
            }

        } else if (NO_ACTION.equals(action)) {

            String NotificationId = "";
            NotificationId = intent.getStringExtra("NotificationId");

            NotificationManager notificationManager = (NotificationManager) context.getApplicationContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(Integer.parseInt(NotificationId));
        }
    }

    public void CallIntent(String DataItems, Context main_context) {
        try {
            //retDi.DS.get(0)-->notificationId
            //retDi.DS.get(1)-->notifyType
            //retDi.DS.get(2)-->title
            //retDi.DS.get(3)-->small Message
            //retDi.DS.get(4)-->big Message
            //retDi.DS.get(5)-->Notification positive button title
            //retDi.DS.get(6)-->Notification negetive button title

            //retDi.DB.get(0)-->turnOnScreen
            //retDi.DB.get(1)-->have positive button in notifications
            //retDi.DB.get(2)-->have negetive button in notifications
            //retDi.DB.get(3)-->Show notifications


            AndroidDataItems retDi = null;
            try {
                final ObjectMapper om = new ObjectMapper();
                retDi = om.readValue(DataItems, AndroidDataItems.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

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
//                    intent=new Intent(main_context,OnClickBroadcastReceivers.class);
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

            if(intent!=null) {
                main_context.sendBroadcast(intent);
//                main_context.startActivity(intent);
            }
        } catch (Exception ex) {
            String asd="";
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

    public boolean DismissNotification(Context Dismisscontext,String noti_id)
    {
        try{
            boolean dismissNotifi=false;
            sharedPreferences = Dismisscontext.getSharedPreferences("NOTIFICATION_DATA",Dismisscontext.MODE_PRIVATE);
            String not_ides = sharedPreferences.getString("not_ides", "");
            String not_timing = sharedPreferences.getString("not_timing", "");
            if(!not_ides.equals(""))
            {

                String[] iid=not_ides.split(";");
                String[] itiming=not_timing.split(";");
                ArrayList<String> dat=new ArrayList<>();
                int pos=-1;
                int main_pos=-1;
                for(String data:iid)
                {
                    pos++;
                    if(data.equals(noti_id))
                    {
                        main_pos=pos;
                        break;
                    }

                }

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String newdate = dateFormat.format(date);
                long timeInMilliseconds=0L;
                try {
                    Date mDate = dateFormat.parse(newdate);
                    timeInMilliseconds = mDate.getTime();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(main_pos!=-1)
                {
                    if(Long.parseLong(itiming[pos])<Long.parseLong(timeInMilliseconds+""))
                    {
                        NotificationManager notificationManager =
                                (NotificationManager) Dismisscontext.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancel(Integer.parseInt(noti_id));

                        dismissNotifi=true;
                        String n_noti_id_new="";
                        String n_timing_new="";
                        int fake_pos=-1;
                        for(String data:iid)
                        {
                            fake_pos++;
                            if(main_pos!=fake_pos)
                            {
                                if(n_noti_id_new.equals(""))
                                    n_noti_id_new=data;
                                else
                                    n_noti_id_new+=";"+data;
                            }

                        }

                        fake_pos=-1;
                        for(String data:itiming)
                        {
                            fake_pos++;
                            if(main_pos!=fake_pos)
                            {
                                if(n_timing_new.equals(""))
                                    n_timing_new=data;
                                else
                                    n_timing_new+=";"+data;
                            }

                        }

                        editor = sharedPreferences.edit();
                        editor.putString("not_ides", n_noti_id_new);
                        editor.putString("not_timing", n_timing_new);
                        editor.commit();

                        return dismissNotifi;
                    }else{
                        return dismissNotifi;
                    }
                }else{
                    return dismissNotifi;
                }




            }
            else{
                return dismissNotifi;
            }
        }
        catch (Exception ex)
        {
            return false;
        }
    }
}
