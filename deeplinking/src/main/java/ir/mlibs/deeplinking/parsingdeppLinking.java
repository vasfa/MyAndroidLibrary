package ir.mlibs.deeplinking;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;

import java.util.List;

/**
 * Created by vali on 2018-03-03.
 */

public class parsingdeppLinking extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openDeepLink();
        finish();
    }

    private void openDeepLink() {
        try {
            Intent intent = getIntent();
            if (intent == null || intent.getData() == null) {

            }
            Uri data = intent.getData();


            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

            List<String> segments = data.getPathSegments();
            if (segments != null && segments.size()>0) {
                Intent rout = null;
                if (segments.size()==2 &&
                        "Home".equals(segments.get(0)) &&
                        "Play".equals(segments.get(1)))
                {
                    Class myclass = null;
                    try {
                        String paths = "ir.vasfa.notificationlibrary" + "." + "Test2Activity";
                        myclass = Class.forName(paths);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    rout = new Intent(getApplicationContext(), myclass);
                } else {
                    Class myclass = null;
                    try {
                        String paths = "ir.vasfa.notificationlibrary" + "." + "MainActivity";
                        myclass = Class.forName(paths);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    rout = new Intent(getApplicationContext(), myclass);
//                    return;
                }
                if (rout != null) {
                    stackBuilder.addNextIntentWithParentStack(rout);
                }
            }
            if (stackBuilder.getIntentCount() == 0) {
                Intent rout = null;
                Class myclass = null;
                try {
                    String paths = "ir.vasfa.notificationlibrary" + "." + "MainActivity";
                    myclass = Class.forName(paths);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                rout = new Intent(getApplicationContext(), myclass);
                stackBuilder.addNextIntent(rout);
            }

            stackBuilder.startActivities();

        } catch (Exception ex) {
            String asd="";
        }
    }
}
