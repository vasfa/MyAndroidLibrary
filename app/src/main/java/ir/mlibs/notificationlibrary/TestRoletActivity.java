package ir.mlibs.notificationlibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ir.mlibs.rolet.RoletFragment;

public class TestRoletActivity extends AppCompatActivity {

    RoletFragment roletFragment;
    Button btn_showImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rolet);

        IntentFilter filter_POSITIVE = new IntentFilter();
        filter_POSITIVE.addAction(BroadCastNames);
        filter_POSITIVE.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(broadcastReceiver, filter_POSITIVE);

        btn_showImages= (Button) findViewById(R.id.showImages);
        btn_showImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BroadCastNames = "LoadImages";
                sendBroadcast();
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        roletFragment = (RoletFragment) fragmentManager.findFragmentById(R.id.fragment_rolets);

    }
    static String BroadCastNames = "LoadImages";
    static String ROLEIMAGEURL = "";
    static String ARROWIMAGEURL = "";
    static String BUTTONIMAGEURL = "";
    public void sendBroadcast() {

        if(BroadCastNames.equals("LoadImages"))
        {
            Intent broadcast = new Intent();
            broadcast.putExtra("BroadCastNames",BroadCastNames);
            broadcast.putExtra("ROLEIMAGEURL",ROLEIMAGEURL);
            broadcast.putExtra("ARROWIMAGEURL",ARROWIMAGEURL);
            broadcast.putExtra("BUTTONIMAGEURL",BUTTONIMAGEURL);
            broadcast.setAction(BroadCastNames);
            broadcast.addCategory(Intent.CATEGORY_DEFAULT);
            sendBroadcast(broadcast);
        }else{
            Intent broadcast = new Intent();
            broadcast.putExtra("BroadCastNames",BroadCastNames);
            broadcast.putExtra("ROLEIMAGEURL",ROLEIMAGEURL);
            broadcast.putExtra("ROLEDEGREE","-115.5");//(-22.5*5)-3
            broadcast.putExtra("COUNTER","3");
            broadcast.setAction(BroadCastNames);
            broadcast.addCategory(Intent.CATEGORY_DEFAULT);
            sendBroadcast(broadcast);
        }


    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//
            String TYPES = intent.getStringExtra("BroadCastNames");
            if (TYPES.equals("FromRolet")) {
                BroadCastNames = "ROLEIT";
                sendBroadcast();
            } else {

            }



        }
    };
}
