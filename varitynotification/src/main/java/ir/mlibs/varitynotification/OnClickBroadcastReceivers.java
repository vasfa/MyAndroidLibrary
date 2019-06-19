package ir.mlibs.varitynotification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ir.mlibs.varitynotification.RestApi.AndroidDataItems;

/**
 * Created by vali on 2018-10-23.
 */

public class OnClickBroadcastReceivers  extends BroadcastReceiver {

    private SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;

    @Override
    public void onReceive(Context context, Intent intent) {

        String TYPE = intent.getExtras().getString("TYPE");
        String Content = intent.getExtras().getString("Content");
        String noti_id = intent.getExtras().getString("noti_id")+"";
        String myclass = intent.getExtras().getString("myclass", "");

        if(!DismissNotification(context,noti_id))
        {
            try{
                if (TYPE.equals("1")) { //--------------------open app

                    Intent appintent = new Intent(context.getPackageManager().getLaunchIntentForPackage(Content));
                    appintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(appintent);


                } else if (TYPE.equals("2")) { //--------------------open site

                    try {
                        Intent siteintent = new Intent(Intent.ACTION_VIEW, Uri.parse(Content));
                        siteintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(siteintent);
                    } catch (Exception ex) {
                        String asd = "";
                    }

                }

                else if (TYPE.equals("3")) {//--------------------open activity

                    Class myActivity = null;
                    try {
                        myActivity = Class.forName(myclass);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    Intent sintent = new Intent(context, myActivity);
                    sintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    sintent.putExtra("DataItemsِ", Content);
                    context.startActivity(sintent);


                } else if (TYPE.equals("4")) {//--------------------Call

                    try {
                        if(Content.contains("*"))
                        {
                            String USSD_CODE = Content + Uri.encode("#");
                            Intent sintent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + USSD_CODE));
                            sintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(sintent);

                        }else{
                            Intent sintent = new Intent(Intent.ACTION_VIEW);
                            sintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            String tel = "tel:" + Content;
                            sintent.setData(Uri.parse(tel));
                            context.startActivity(sintent);
                        }


                    } catch (Exception ex) {
                        String asd = "";
                    }

                }else if (TYPE.equals("5")) {//--------------------Send sms

                    try {
                        Intent sintent = new Intent(Intent.ACTION_VIEW);
                        sintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        sintent.setType("vnd.android-dir/mms-sms");
                        sintent.putExtra("address", Content);
                        sintent.putExtra("sms_body", myclass);
                        context.startActivity(sintent);
                    } catch (Exception ex) {
                        String asd = "";
                    }


                }else if (TYPE.equals("6")) {//--------------------Send Email

                    try {
                        AndroidDataItems retDi = null;
                        try {
                            final ObjectMapper om = new ObjectMapper();
                            retDi = om.readValue(Content, AndroidDataItems.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String[] emailAccoutTo=retDi.DS.get(9).split(";");
                        String[] emailAccoutCC=retDi.DS.get(10).split(";");
                        Intent sintent = new Intent(context, EmailActivity.class);
                        sintent.putExtra("noti_id", retDi.DS.get(0));
                        sintent.putExtra("emailAccoutTo", emailAccoutTo);
                        sintent.putExtra("emailAccoutCC", emailAccoutCC);
                        sintent.putExtra("emailTitle", retDi.DS.get(11));
                        sintent.putExtra("emailmessage", retDi.DS.get(12));
                        sintent.putExtra("emailChosserTitle", retDi.DS.get(13));
                        context.startActivity(sintent);
                    } catch (Exception ex) {
                        String asd = "";
                    }


                }else if (TYPE.equals("7")) {//--------------------do nothing

                    try {
                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancel(Integer.parseInt(noti_id));
                    } catch (Exception ex) {
                        String asd = "";
                    }


                }
                else if (TYPE.equals("8")) { //--------------------download app

                    try {
                        if (NetworkManager.isConnected(context)) {
                            if (!Content.equals("")) {
                                AndroidDataItems retDi = null;
                                try {
                                    final ObjectMapper om = new ObjectMapper();
                                    retDi = om.readValue(Content, AndroidDataItems.class);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                String[] items = Content.split(";");
                                dowunloadNewApp(retDi.DS.get(9), retDi.DS.get(10), false, context);
                            }

                        }
                    } catch (Exception ex) {
                        String asd = "";
                    }


                }
            }catch (Exception ex)
            {

            }
        }



    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String newdate = dateFormat.format(date);
        return newdate.replaceAll(" ", "_");

    }

    public void dowunloadNewApp(String url, String App_name, final boolean directInstall, final Context context) {
        try {
            try {

                final String appName = App_name + "_" + getDateTime() + ".apk";

                AndroidNetworking.download(url, Environment.getExternalStorageDirectory() + "", appName)
                        .setTag("downloadapk")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .setDownloadProgressListener(new DownloadProgressListener() {
                            @Override
                            public void onProgress(long bytesDownloaded, long totalBytes) {
                                // do anything with progress
                            }
                        })
                        .startDownload(new DownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                // do anything after completion

                                if (directInstall) {
                                    File files = new File(Environment.getExternalStorageDirectory() +
                                            File.separator + appName);
                                    Intent sintent = new Intent(Intent.ACTION_VIEW);
                                    sintent.setDataAndType(Uri.fromFile(files),
                                            "application/vnd.android.package-archive");
                                    sintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without
                                    context.startActivity(sintent);
                                }

                                String IMEI_MOBILE = "";
                                String SOFT_NAME = BuildConfig.VERSION_NAME;
                                String SOFT_VERSION = BuildConfig.VERSION_CODE + "";
                                TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                try {
                                    IMEI_MOBILE = tel.getDeviceId().toString();
                                } catch (Exception ex) {
                                    IMEI_MOBILE = "";
                                }



                            }

                            @Override
                            public void onError(ANError error) {
                                // handle error
                                String IMEI_MOBILE = "";
                                String SOFT_NAME = BuildConfig.VERSION_NAME;
                                String SOFT_VERSION = BuildConfig.VERSION_CODE + "";
                                TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                try {
                                    IMEI_MOBILE = tel.getDeviceId().toString();
                                } catch (Exception ex) {
                                    IMEI_MOBILE = "";
                                }

                            }
                        });
            } catch (Exception ex) {
                String sdsdsds = ex.getMessage().toString();
            }

        } catch (Exception ex) {

        }
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