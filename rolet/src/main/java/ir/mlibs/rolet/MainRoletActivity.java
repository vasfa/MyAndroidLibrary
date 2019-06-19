package ir.mlibs.rolet;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainRoletActivity extends AppCompatActivity {

    private TextView selectedPositionText;
    Button btns;
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


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rolet);
        mediaPlayer_chance = MediaPlayer.create(MainRoletActivity.this, R.raw.voice_1);
        mediaPlayer_roll = MediaPlayer.create(MainRoletActivity.this, R.raw.voice_2);
        mediaPlayer_popup = MediaPlayer.create(MainRoletActivity.this, R.raw.voice_3);
        initialize();
    }

    int pos = 0;
    String message1 = "";
    String message2 = "";
    String message3 = "";

    private void initialize() {

        try {

            im = (ImageView) findViewById(R.id.img);

            iv_role = (ImageView) findViewById(R.id.ImageView_role);

//            float degrees = Float.parseFloat(resItem.DS.get(1));
//            i1 = Integer.parseInt(resItem.DS.get(6));
//            turn(degrees);

            et = (EditText) findViewById(R.id.edittext_id);
            selectedPositionText = (TextView) findViewById(R.id.selected_position_text);


            iv_role.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isEnable)
                        return;
                    isEnable = false;
                    if (mediaPlayer_popup != null && mediaPlayer_popup.isPlaying()) {
                        mediaPlayer_popup.stop();
                    }
                    mediaPlayer_chance = MediaPlayer.create(MainRoletActivity.this, R.raw.voice_1);

                    mediaPlayer_chance.start();
                    if (pos < 3) {
                        sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                        String RoletQ = sharedPreferences.getString("RoletQ", "");
                        if (!RoletQ.equals("1")) {

                            editor = sharedPreferences.edit();
                            editor.putString("RoletQ", "1");
                            editor.commit();
//                            CustomMessageBox.MainMessageBoxForAllResults("توجه", message1, MainRoletActivity.this, true,
//                                    true, true, true, "بازی می کنم", "منصرف شدم", messageEnum.doAction, messageEnum.noAction);
                        } else {
                           // roll(1);
                        }

                    } else {
                        isEnable = true;
//                        CustomMessageBox.MainMessageBoxForAllResults("توجه", message2, MainRoletActivity.this, true,
//                                true, true, false, "باشه", "خیر", messageEnum.None, "");
                    }

                }
            });


            btns = (Button) findViewById(R.id.btn);
            btns.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Random r = new Random();
                        i1 = r.nextInt(79 - 32) + 32;
                        float degrees = (float) (i1 * (-22.5));// ((float) -22.5*period)-count;
                        turn(degrees);

                    } catch (Exception ex) {
                        String asd = "";
                    }

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

                mediaPlayer_roll = MediaPlayer.create(MainRoletActivity.this, R.raw.voice_2);
                mediaPlayer_roll.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                switch (i1 % 16) {
                    case 0:
                        //selectedPositionText.setText("شارژ 1000 تومانی" + "  برنده شدید");
                        break;
                    case 1:
                        //selectedPositionText.setText("شارژ 5000 تومانی" + "  برنده شدید");
                        break;
                    case 2:
                        //selectedPositionText.setText("شارژ 2000 تومانی" + "  برنده شدید");
                        break;


                }
//                isEnable = true;
//                if (mediaPlayer_roll != null && mediaPlayer_roll.isPlaying()) {
//
//                    mediaPlayer_roll.stop();
//                }
//
//                try{
//
//                    mediaPlayer_popup = MediaPlayer.create(MainRoletActivity.this, R.raw.voice_3);
//                    mediaPlayer_popup.start();
//                }
//                catch (Exception ex)
//                {
//
//                }

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
    protected void onDestroy() {
        super.onDestroy();

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





}
