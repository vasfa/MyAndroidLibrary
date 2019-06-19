package ir.mlibs.rolet;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoletFragment extends Fragment {

    View view = null;
    private TextView selectedPositionText;
    //    Button btns;
    ImageView im;
    ImageView iv_role;
    EditText et;
    int i1 = -1;
    boolean isEnable = false;
    int selectedButton = 0;
    ImageView iv_close;
    private SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;

    String not_title = "", not_small_content = "", not_big_content = "";
    int not_Id = -1;

    MediaPlayer mediaPlayer_chance;
    MediaPlayer mediaPlayer_roll;
    MediaPlayer mediaPlayer_popup;


    public RoletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_rolet, container, false);
        mediaPlayer_chance = MediaPlayer.create(getActivity(), R.raw.voice_1);
        mediaPlayer_roll = MediaPlayer.create(getActivity(), R.raw.voice_2);
        mediaPlayer_popup = MediaPlayer.create(getActivity(), R.raw.voice_3);
        initialize();
        return view;
    }

    int pos = 0;
    String message1 = "";
    String message2 = "";
    String message3 = "";
    String FromRolet = "FromRolet";

    private void initialize() {

        try {

            IntentFilter filter_POSITIVE = new IntentFilter();
            filter_POSITIVE.addAction(FromRolet);
            filter_POSITIVE.addCategory(Intent.CATEGORY_DEFAULT);
            getActivity().registerReceiver(broadcastReceiver, filter_POSITIVE);

            im = (ImageView) view.findViewById(R.id.img);

            iv_role = (ImageView) view.findViewById(R.id.ImageView_role);

//            float degrees = Float.parseFloat(resItem.DS.get(1));
//            i1 = Integer.parseInt(resItem.DS.get(6));
//            turn(degrees);

            et = (EditText) view.findViewById(R.id.edittext_id);
            selectedPositionText = (TextView) view.findViewById(R.id.selected_position_text);


            iv_role.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    IntentFilter filter_POSITIVE = new IntentFilter();
                    filter_POSITIVE.addAction(FromRolet);
                    filter_POSITIVE.addCategory(Intent.CATEGORY_DEFAULT);
                    getActivity().registerReceiver(broadcastReceiver, filter_POSITIVE);

                    sendBroadcast();

                }
            });

        } catch (Exception ex) {
            String asd = "";
        }


    }


    public void turn(float degrees) {
        //float degrees = (float) (Float.parseFloat(et.getText().toString())*(-22.5));// ((float) -22.5*period)-count;


        RotateAnimation anim = new RotateAnimation
                (0, degrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(5000);
        anim.setFillEnabled(true);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (mediaPlayer_chance != null && mediaPlayer_chance.isPlaying()) {
                    mediaPlayer_chance.stop();
                }

                mediaPlayer_roll = MediaPlayer.create(getActivity(), R.raw.voice_2);
                mediaPlayer_roll.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                switch (i1 % 16) {
                    case 0:
                        //selectedPositionText.setText("بن ریحون" + "  برنده شدید");
                        break;
                    case 1:
                        //selectedPositionText.setText("شارژ 5000 تومانی" + "  برنده شدید");
                        break;
                    case 2:
                        //selectedPositionText.setText("شارژ 2000 تومانی" + "  برنده شدید");
                        break;
                    case 3:
                        //selectedPositionText.setText("شارژ 1000 تومانی" + "  برنده شدید");
                        break;
                    case 4:
                        //selectedPositionText.setText("1000 ویز" + "  برنده شدید");
                        break;
                    case 5:
                        //selectedPositionText.setText("500 ویز" + "  برنده شدید");
                        break;
                    case 6:
                        //selectedPositionText.setText("100 ویز" + "  برنده شدید");
                        break;
                    case 7:
                        //selectedPositionText.setText("50 ویز" + "  برنده شدید");
                        break;
                    case 8:
                        //selectedPositionText.setText("30 ویز" + "  برنده شدید");
                        break;
                    case 9:
                        //selectedPositionText.setText("10 ویز" + "  برنده شدید");
                        break;
                    case 10:
                        //selectedPositionText.setText("پوچ");
                        break;
                    case 11:
                        //selectedPositionText.setText("Iphone 7pluse" + "  برنده شدید");
                        break;
                    case 12:
                        //selectedPositionText.setText("samsung galaxy s7" + "  برنده شدید");
                        break;
                    case 13:
                        //selectedPositionText.setText("xbox one" + "  برنده شدید");
                        break;
                    case 14:
                        //selectedPositionText.setText("بلیت سینما" + "  برنده شدید");
                        break;
                    case 15:
                        //selectedPositionText.setText("بن دیجی کالا" + "  برنده شدید");
                        break;


                }
                isEnable = true;
                if (mediaPlayer_roll != null && mediaPlayer_roll.isPlaying()) {

                    mediaPlayer_roll.stop();
                }

                try {

                    mediaPlayer_popup = MediaPlayer.create(getActivity(), R.raw.voice_3);
                    mediaPlayer_popup.start();
                } catch (Exception ex) {

                }

//                CustomMessageBox.MainMessageBoxForAllResults(not_title, not_big_content, MainRoletActivity.this, true,
//                        true, true, false, "باشه", "خیر", messageEnum.None, "");
                //Simple_big_text(not_title, not_small_content, not_big_content, not_Id);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        im.startAnimation(anim);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
        if (mediaPlayer_chance != null || mediaPlayer_chance.isPlaying()) {
            if (mediaPlayer_chance.isPlaying())
                mediaPlayer_chance.stop();
            mediaPlayer_chance.release();
        }
        if (mediaPlayer_roll != null || mediaPlayer_roll.isPlaying()) {
            if (mediaPlayer_roll.isPlaying())
                mediaPlayer_roll.stop();
            mediaPlayer_roll.release();
        }
        if (mediaPlayer_popup != null || mediaPlayer_popup.isPlaying()) {
            if (mediaPlayer_popup.isPlaying())
                mediaPlayer_popup.stop();
            mediaPlayer_popup.release();
        }
    }

    //    static String BroadCastNames = "All";
    public void sendBroadcast() {

        Intent broadcast = new Intent();
        broadcast.putExtra("BroadCastNames", FromRolet);
        broadcast.setAction(FromRolet);
        broadcast.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().sendBroadcast(broadcast);

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//
            String TYPES = intent.getStringExtra("BroadCastNames");
            if (TYPES.equals("FromRolet")) {
//do nothing
            } else {


                if (TYPES.equals("LoadImages")) {

                } else {
                    String ROLEDEGREE = intent.getStringExtra("ROLEDEGREE");
                    String COUNTER = intent.getStringExtra("COUNTER");
                    float degrees = Float.parseFloat(ROLEDEGREE);
                    i1 = Integer.parseInt(COUNTER);
                    turn(degrees);
                }
            }

        }
    };

}