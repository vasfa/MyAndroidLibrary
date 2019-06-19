package ir.mlibs.sliderfragmentgallery;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.wang.avi.AVLoadingIndicatorView;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import mysliderlibraryimageprogressbar.LoadImageGlide;
import mysliderlibraryimageprogressbar.MyProgressTarget;
import mysliderlibraryimageprogressbar.ProgressTarget;

import static ir.mlibs.sliderfragmentgallery.Setting.IsDestroyed;
import static ir.mlibs.sliderfragmentgallery.Setting.image_list;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageVideoSoundFragment extends Fragment {


    LayoutInflater layoutInflater;
    Context context;
    TouchImageViewFragment imageDisplay;
    ImageView iv_full_image_without_zoom;
    AVLoadingIndicatorView avis;
    ProgressBar pb;
    TextView tv;

    //these are for video and voice
    LinearLayout ll_Linearlayout_main_videoView;
    VideoView myVideoView;
    String Video_Sound_Url = "";
    LinearLayout ll_SmoothProgressBar;
    //    SeekBar seekBar;
    DiscreteSeekBar seekBar;
    ImageView iv_pause, iv_play, iv_main;
    ImageView iv_off, iv_on, iv_repeat, iv_repeat2;
    int lastvolume = 0;
    private AudioManager audioManager = null;
    boolean newplay = true;
    TextView tv_durationleft, tv_total_duration;
    Boolean isvideo = false;
    Boolean isAparat = false;
    boolean isstarted = false;
    boolean isCanceled = false;
    boolean isOnlyMusic = false;
    String MainURL = "";
    MediaPlayer mediaPlayer;
    Handler mHandler;
    int mediaFileLengthInMilliseconds;
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    String Title = "";
    LinearLayout ll_videoView;
    Boolean islooping = false;
    int pos = 1;
    int fragVal;
    View viewLayout = null;
    Boolean isFirstPaly = false;

    public ImageVideoSoundFragment() {
        // Required empty public constructor
    }

    static ImageVideoSoundFragment init(int val) {
        ImageVideoSoundFragment truitonFrag = new ImageVideoSoundFragment();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        truitonFrag.setArguments(args);
        return truitonFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            stopPosition = savedInstanceState.getInt("position");
        }
        fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewLayout = inflater.inflate(R.layout.fragment_image_video_sound, container, false);

        try {
            ll_videoView = (LinearLayout) viewLayout.findViewById(R.id.Linearlayout_videoView);
            ll_Linearlayout_main_videoView = (LinearLayout) viewLayout.findViewById(R.id.Linearlayout_main_videoView);
            imageDisplay = (TouchImageViewFragment) viewLayout.findViewById(R.id.full_image);
            iv_full_image_without_zoom = (ImageView) viewLayout.findViewById(R.id.full_image_without_zoom);
            avis = (AVLoadingIndicatorView) viewLayout.findViewById(R.id.aviLoading);
            pb = (ProgressBar) viewLayout.findViewById(R.id.progressbar);
            tv = (TextView) viewLayout.findViewById(R.id.TextView_title);

        } catch (Exception ex) {
            String assd = "";
        }

        if (image_list.get(fragVal).getIsonlySound()) {
            try {
                if (imageDisplay != null)
                    imageDisplay.setVisibility(View.GONE);
                if (ll_videoView != null)
//                    if (!image_list.get(fragVal).getIsvideo())
                    ll_videoView.setVisibility(View.GONE);
            } catch (Exception ex) {

            }
        }else{
            if (imageDisplay != null)
                imageDisplay.setVisibility(View.GONE);
            if (ll_videoView != null)
                ll_videoView.setVisibility(View.VISIBLE);
        }

//!Setting.isOnlySound
        if (!image_list.get(fragVal).getIsvideo() && !image_list.get(fragVal).getIsonlySound()) {
            ll_Linearlayout_main_videoView.setVisibility(View.GONE);
            ProgressTarget<String, Bitmap> target;
            if (image_list.get(fragVal).getIsimageZoomable()) {
                iv_full_image_without_zoom.setVisibility(View.GONE);
                imageDisplay.setVisibility(View.VISIBLE);
                target = new MyProgressTarget<>(new BitmapImageViewTarget(imageDisplay), pb, imageDisplay, tv, avis);
                LoadImageGlide.fillGlide(image_list.get(fragVal).getImageURLPath(),
                        target,
                        pb,
                        tv,
                        1,
                        1,
                        "",
                        null,
                        getActivity(),
                        "",
                        ".silders",
                        false,
                        avis);
            } else {
                iv_full_image_without_zoom.setVisibility(View.VISIBLE);
                imageDisplay.setVisibility(View.GONE);
                target = new MyProgressTarget<>(new BitmapImageViewTarget(iv_full_image_without_zoom), pb, iv_full_image_without_zoom, tv, avis);
                LoadImageGlide.fillGlide(image_list.get(fragVal).getImageURLPath(),
                        target,
                        pb,
                        tv,
                        1,
                        1,
                        "",
                        null,
                        getActivity(),
                        "",
                        ".silders",
                        false,
                        avis);
            }
        } else {
            ll_Linearlayout_main_videoView.setVisibility(View.VISIBLE);
            Initialize();
        }



        return viewLayout;
    }

    //these are for video and sound
    private void Initialize() {
        try {
            Setting.mc = getActivity();
            Setting.mcbool = true;
            Video_Sound_Url = image_list.get(fragVal).getVideoURLPath();
            isvideo = image_list.get(fragVal).getIsvideo();
            isOnlyMusic = image_list.get(fragVal).getIsonlySound();
            isAparat = image_list.get(fragVal).getIsaparat();


            ll_SmoothProgressBar = (LinearLayout) viewLayout.findViewById(R.id.SmoothProgressBar_playvideo);
            ll_SmoothProgressBar.setVisibility(View.GONE);

            initControls();
        } catch (Exception ex) {
            String sad = "";
        }
    }

    private void initControls() {
        try {


            iv_pause = (ImageView) viewLayout.findViewById(R.id.Imageview_pause);
            iv_play = (ImageView) viewLayout.findViewById(R.id.Imageview_play);
            iv_main = (ImageView) viewLayout.findViewById(R.id.Imageview_Main);
            iv_off = (ImageView) viewLayout.findViewById(R.id.ImageView_sound_turn_off);
            iv_on = (ImageView) viewLayout.findViewById(R.id.ImageView_sound_turn_on);
            iv_repeat = (ImageView) viewLayout.findViewById(R.id.Imageview_repeat);
            iv_repeat2 = (ImageView) viewLayout.findViewById(R.id.Imageview_repeat2);
            seekBar = (DiscreteSeekBar) viewLayout.findViewById(R.id.seekBar);
            // if (!Setting.isOnlySound) {
            iv_full_image_without_zoom.setVisibility(View.VISIBLE);
            imageDisplay.setVisibility(View.GONE);
            {
                ProgressTarget<String, Bitmap> target;
                target = new MyProgressTarget<>(new BitmapImageViewTarget(iv_full_image_without_zoom), pb, iv_full_image_without_zoom, tv, avis);
                LoadImageGlide.fillGlide(image_list.get(fragVal).getImageURLPath(),
                        target,
                        pb,
                        tv,
                        1,
                        1,
                        "",
                        null,
                        getActivity(),
                        "",
                        ".silders",
                        false,
                        avis);
            }
            {

                ProgressTarget<String, Bitmap> target;
                target = new MyProgressTarget<>(new BitmapImageViewTarget(iv_main), pb, iv_full_image_without_zoom, tv, avis);
                LoadImageGlide.fillGlide(image_list.get(fragVal).getImageURLPath(),
                        target,
                        pb,
                        tv,
                        1,
                        1,
                        "",
                        null,
                        getActivity(),
                        "",
                        ".silders",
                        false,
                        avis);
            }

            //}

            iv_repeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mediaPlayer != null) {
                        if (islooping) {
                            islooping = false;
                            mediaPlayer.setLooping(false);
                        } else {
                            islooping = true;
                            mediaPlayer.setLooping(true);
                        }
                    }
                    iv_repeat2.setVisibility(View.VISIBLE);
                    iv_repeat.setVisibility(View.GONE);
                }
            });
            iv_repeat2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mediaPlayer != null) {
                        if (islooping) {
                            islooping = false;
                            mediaPlayer.setLooping(false);
                        } else {
                            islooping = true;
                            mediaPlayer.setLooping(true);
                        }
                    }
                    iv_repeat.setVisibility(View.VISIBLE);
                    iv_repeat2.setVisibility(View.GONE);
                }
            });
            seekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                @Override
                public void onProgressChanged(DiscreteSeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        // this is when actually seekbar has been seeked to a new position
                        myVideoView.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

                }
            });
            audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            myVideoView = (VideoView) viewLayout.findViewById(R.id.videoView_Play);
            if (image_list.get(fragVal).getIsvideo()) {
                ll_videoView.setVisibility(View.VISIBLE);
                iv_repeat.setVisibility(View.GONE);
                iv_repeat2.setVisibility(View.GONE);
            } else {
                ll_videoView.setVisibility(View.GONE);
            }
//            myVideoView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    if (myVideoView!=null && myVideoView.isPlaying()) {
//                        myVideoView.pause();
//                        iv_pause.setVisibility(View.GONE);
//                        iv_play.setVisibility(View.VISIBLE);
//                        //iv_Play_video_fake.setVisibility(View.VISIBLE);
//                        // iv_pause_fake.setVisibility(View.VISIBLE);
//                    } else {
//
//                        if (mediaPlayer != null) {
//                            if (mediaPlayer.isPlaying()) {
//
//                                mediaPlayer.pause();
//
//                            } else {
//                                myVideoView.start();
//                                iv_pause.setVisibility(View.VISIBLE);
//                                iv_play.setVisibility(View.GONE);
//                                seekBar.setMax(myVideoView.getDuration());
//                                seekBar.postDelayed(onEverySecond, 1000);
//                            }
//                        } else {
//                            playmedia();
//                        }
//
//                        //iv_Play_video_fake.setVisibility(View.GONE);
//                    }
//                    return false;
//                }
//            });
            tv_durationleft = (TextView) viewLayout.findViewById(R.id.textview_duration_left);
            tv_total_duration = (TextView) viewLayout.findViewById(R.id.textview_Total_duration);
            iv_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (image_list.get(fragVal).getIsvideo()) {
                        if (myVideoView.isPlaying()) {
                            myVideoView.pause();
                            iv_pause.setVisibility(View.GONE);
                            iv_play.setVisibility(View.VISIBLE);
                            //iv_Play_video_fake.setVisibility(View.VISIBLE);
                            // iv_pause_fake.setVisibility(View.VISIBLE);
                        }
                    } else {
                        iv_pause.setVisibility(View.GONE);
                        iv_play.setVisibility(View.VISIBLE);
                        if (mediaPlayer != null) {
                            if (mediaPlayer.isPlaying()) {

                                mediaPlayer.pause();

                            }
                        }
                    }

                }
            });
            iv_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isFirstPaly = true;
                    playmedia();
                }
            });
            pos = 0;
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    // Do something after 5s = 5000ms
//                    playmedia();
//                }
//            }, 500);

            lastvolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            iv_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastvolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
                    iv_off.setVisibility(View.GONE);
                    iv_on.setVisibility(View.VISIBLE);

                }
            });

            iv_on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, lastvolume, 0);
                    iv_on.setVisibility(View.GONE);
                    iv_off.setVisibility(View.VISIBLE);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected class AsyncCallVideo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... param) {
            // TODO Auto-generated method stub

            return image_list.get(fragVal).getIsaparat() ? RunVideo(param[0]) : param[0];
//            if (PathExists.equals("0")) {
//                return RunVideo(param[0]);
//            } else
//                return param[0];
        }

        @Override
        protected void onPostExecute(String URL) {
            // TODO Auto-generated method stub
            super.onPostExecute(URL);
            iv_full_image_without_zoom.setVisibility(View.GONE);
            imageDisplay.setVisibility(View.GONE);
            try {
                ll_SmoothProgressBar.setVisibility(View.VISIBLE);
//                http://aparat.com/etc/api/videooffact/videohash/lpZiX
                if (URL.equals("error")) {
                    // UsermessageBox(TextMessages.VideoNotFound);
                    ll_SmoothProgressBar.setVisibility(View.GONE);
                    iv_pause.setVisibility(View.GONE);
                    iv_play.setVisibility(View.VISIBLE);

                    Toast.makeText(getActivity(), "VideoNotFound", Toast.LENGTH_SHORT).show();
                    return;
                }


//                myVideoView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        if (myVideoView.isPlaying()) {
//                            myVideoView.pause();
//                            iv_pause.setVisibility(View.GONE);
//                            iv_play.setVisibility(View.VISIBLE);
//                            //iv_Play_video_fake.setVisibility(View.VISIBLE);
//                            // iv_pause_fake.setVisibility(View.VISIBLE);
//                        } else {
//                            myVideoView.start();
//                            iv_pause.setVisibility(View.VISIBLE);
//                            iv_play.setVisibility(View.GONE);
//                            seekBar.setMax(myVideoView.getDuration());
//                            seekBar.postDelayed(onEverySecond, 1000);
//                            //iv_Play_video_fake.setVisibility(View.GONE);
//                        }
//                        return false;
//                    }
//                });
                myVideoView.setMediaController(null);
                //dialog = CustomProgressDialog.PROGRESSBAR(PlayVideoActivity.this);
                ll_SmoothProgressBar.setVisibility(View.VISIBLE);
                // URL="http://79.175.173.243:8014/images/ans/2/2_device_5965757a-2497-4569-9658-2141e39fa0cd636514478061094705test.mp4";
                Uri videoUri = Uri.parse(URL);


                if (myVideoView.getVisibility() == View.GONE)
                    myVideoView.setVisibility(View.VISIBLE);
                myVideoView.setVideoURI(videoUri);

//                MediaController media_Controller = new MediaController(
//                        getActivity());

                myVideoView
                        .setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(
                                    MediaPlayer mp) {
                                // TODO Auto-generated method
                                String ssd = "";
                                ssd = "asds";
                                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                                    @Override
                                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START)
                                            ll_SmoothProgressBar.setVisibility(View.VISIBLE);
                                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
                                            ll_SmoothProgressBar.setVisibility(View.GONE);
                                        if (what == 3)
                                            ll_SmoothProgressBar.setVisibility(View.GONE);
                                        return false;
                                    }
                                });
                                ll_SmoothProgressBar.setVisibility(View.GONE);
                                myVideoView.start();

                                seekBar.setMax(myVideoView.getDuration());
                                seekBar.postDelayed(onEverySecond, 1000);
                                //myVideoView.pause();


                                int duration = mp.getDuration() / 1000;
                                int hours = duration / 3600;
                                int minutes = (duration / 60) - (hours * 60);
                                int seconds = duration - (hours * 3600) - (minutes * 60);
                                String formatted = "00:00";
                                if (hours > 0) {
                                    formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
                                } else {
                                    formatted = String.format("%02d:%02d", minutes, seconds);
                                }
//                                String formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
                                tv_total_duration.setText(formatted);
                                if (image_list.get(fragVal).getIsvideo())
                                    iv_main.setVisibility(View.GONE);
                                else
                                    iv_main.setVisibility(View.VISIBLE);

                            }
                        });

                myVideoView
                        .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                            @Override
                            public void onCompletion(
                                    MediaPlayer vmp) {
                                iv_pause.setVisibility(View.GONE);
                                iv_play.setVisibility(View.VISIBLE);

                                newplay = true;
                                ll_SmoothProgressBar.setVisibility(View.GONE);

                            }
                        });

            } catch (Exception e) {
                newplay = false;
                // TODO Auto-generated catch block
                e.printStackTrace();
                ll_SmoothProgressBar.setVisibility(View.GONE);

            }

            myVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    String asd = "";
                    asd = "asdsad";
                    return false;
                }
            });


        }
    }

    private Runnable onEverySecond = new Runnable() {

        @Override
        public void run() {

            if (seekBar != null) {
                seekBar.setProgress(myVideoView.getCurrentPosition());
                Long h = TimeUnit.MILLISECONDS.toHours(myVideoView.getCurrentPosition());
                Long m = TimeUnit.MILLISECONDS.toMinutes(myVideoView.getCurrentPosition()) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(myVideoView.getCurrentPosition()));
                Long s = TimeUnit.MILLISECONDS.toSeconds(myVideoView.getCurrentPosition()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(myVideoView.getCurrentPosition()));
                if (h > 0) {
                    String hms = String.format("%d:%02d:%02d", h
                            , m
                            , s // The change is in this line
                    );
                    tv_durationleft.setText(hms);
                } else {
                    String hms = String.format("%02d:%02d", m
                            , s // The change is in this line
                    );
                    tv_durationleft.setText(hms);
                }


            }

            if (myVideoView.isPlaying()) {
                seekBar.postDelayed(onEverySecond, 1000);
            }

        }
    };

    private String RunVideo(String VideoHash) {
        try {

            try {
                if (VideoHash == null)
                    return "";
                if (VideoHash.equals(""))
                    return "";


                String VIDEO_URL = VideoHash;
                String result = null;
                StringBuffer sb = new StringBuffer();
                InputStream is = null;

                URL url = null;
                try {
                    url = new URL(VIDEO_URL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    ll_SmoothProgressBar.setVisibility(View.GONE);
                    return "error";
                }
                HttpURLConnection connection = null;
                try {
                    assert url != null;
                    connection = (HttpURLConnection) url
                            .openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                    ll_SmoothProgressBar.setVisibility(View.GONE);
                    return "error";
                }
                assert connection != null;
                connection.setDoInput(true);
                try {
                    connection.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                    ll_SmoothProgressBar.setVisibility(View.GONE);
                    return "error";
                }
                try {
                    is = new BufferedInputStream(
                            connection.getInputStream());
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(is));
                    String inputLine = "";
                    while ((inputLine = br.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    ll_SmoothProgressBar.setVisibility(View.GONE);
                    return "error";
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {

                        }
                    }

                }


                JSONObject obj = null;
                try {
                    obj = new JSONObject(result);
                    int a = 0;
                    a = obj.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ll_SmoothProgressBar.setVisibility(View.GONE);
                    return "error";
                }
                String firstLinkInSrc = null;
                try {
                    assert obj != null;
                    int totalItems = obj.getJSONObject("videooffact").getJSONArray("stream").length();
                    for (int i = 0; i < totalItems; i++) {
                        String p = obj.getJSONObject("videooffact").getJSONArray("stream").getJSONObject(i).getString("profile").replace("p", "");
                        Integer pi = Integer.parseInt(p);

                        if (pi >= 700) {


                            // HD_profile = pi.toString();
                            //  HD_size = obj.getJSONObject("videooffact").getJSONArray("stream").getJSONObject(i).getString("size");
                            firstLinkInSrc = obj
                                    .getJSONObject("videooffact")
                                    .getJSONArray("stream")
                                    .getJSONObject(i).getJSONArray("src")
                                    .getString(0);

                            // IsaddedProfile = true;
                        }
                        if (pi < 700) {
                            // LW_profile = pi.toString();
                            // LW_size = obj.getJSONObject("videooffact").getJSONArray("stream").getJSONObject(i).getString("size");
                            firstLinkInSrc = obj
                                    .getJSONObject("videooffact")
                                    .getJSONArray("stream")
                                    .getJSONObject(i).getJSONArray("src")
                                    .getString(0);
                            String s = "";
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    ll_SmoothProgressBar.setVisibility(View.GONE);
                    return "error";
                }
                return firstLinkInSrc;

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                ll_SmoothProgressBar.setVisibility(View.GONE);
                return "error";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ll_SmoothProgressBar.setVisibility(View.GONE);
            return "error";
        }

    }


    public void PlayUrl(String URL) {
        MainURL = URL;
        iv_pause.setVisibility(View.VISIBLE);
        iv_play.setVisibility(View.GONE);
        // MainURL = URL;
        if (!isstarted)
            StartMusic(MainURL);
        else {

            iv_pause.setVisibility(View.VISIBLE);
            iv_play.setVisibility(View.GONE);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.start();
        }
    }

    public void StartMusic(final String url) {
//        if (!NetworkManager.isConnected(LandScapeMediaPlayerActivity.this)) {
//            return;
//        }

        if (mediaPlayer != null) {

            if (mHandler != null)
                mHandler.removeCallbacksAndMessages(null);
            if (mediaPlayer.isPlaying()) {

                mediaPlayer.seekTo(0);
                seekBar.setProgress(0);
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();

            } else {

                mediaPlayer.seekTo(0);
                seekBar.setProgress(0);
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();

            }
        }
//        if (iv_pause.getVisibility()!=View.VISIBLE) {
        if (true) {

            try {
                if (!url.contains("http")) {
                    //
                    //sound link not found
                    //

                    iv_pause.setVisibility(View.GONE);
                    iv_play.setVisibility(View.VISIBLE);
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {

                            mediaPlayer.pause();

                        }
                    }


                    ll_SmoothProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "فایل صوتی مورد نظر قابل اجرا نمی باشد.", Toast.LENGTH_LONG).show();
                    isCanceled = true;
                    //ll_SmoothProgressBar.setVisibility(View.GONE);
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.seekTo(0);
                            seekBar.setProgress(0);

                            mediaPlayer.stop();
                            mediaPlayer.release();


                            mediaPlayer = new MediaPlayer();
                        } else {

                            mediaPlayer.seekTo(0);
                            seekBar.setProgress(0);

                            mediaPlayer.release();


                            mediaPlayer = new MediaPlayer();

                        }
                    }
                    //dialog.dismiss();
                    return;
                }
                isstarted = true;
                seekBar.setEnabled(true);
                seekBar.setVisibility(View.VISIBLE);
                new CountDownTimer(2000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        if (Setting.mcbool) {
                            // seekBar.setEnabled(true);
                            //mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(url));
                            if(IsDestroyed)
                                return;
                            mediaPlayer = new MediaPlayer();

                            try {
                                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                mediaPlayer.setDataSource(getActivity(), Uri.parse(url));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (isCanceled) {
                                if (mediaPlayer != null) {
                                    if (mediaPlayer.isPlaying()) {
                                        mediaPlayer.seekTo(0);
                                        seekBar.setProgress(0);

                                        mediaPlayer.stop();
                                        mediaPlayer.release();


                                        mediaPlayer = new MediaPlayer();
                                    } else {

                                        mediaPlayer.seekTo(0);
                                        seekBar.setProgress(0);

                                        mediaPlayer.release();


                                        mediaPlayer = new MediaPlayer();

                                    }
                                }

                                isCanceled = false;
                                return;
                            }

                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {


                                    mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                                        @Override
                                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                                            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                                                ll_SmoothProgressBar.setVisibility(View.VISIBLE);
                                            }
                                            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                                                ll_SmoothProgressBar.setVisibility(View.GONE);
                                            }
                                            if (what == 3) {
                                                ll_SmoothProgressBar.setVisibility(View.GONE);
                                            }
                                            return false;
                                        }
                                    });


                                    mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL

                                    primarySeekBarProgressUpdater();
                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    mediaPlayer.start();
                                    //dialog.dismiss();

                                    int duration = mp.getDuration() / 1000;
                                    int hours = duration / 3600;
                                    int minutes = (duration / 60) - (hours * 60);
                                    int seconds = duration - (hours * 3600) - (minutes * 60);
                                    String formatted = "00:00";
                                    if (hours > 0) {
                                        formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
                                    } else {
                                        formatted = String.format("%02d:%02d", minutes, seconds);
                                    }
//                                String formatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
                                    tv_total_duration.setText(formatted);
                                    ll_SmoothProgressBar.setVisibility(View.GONE);

                                }
                            });
                            mediaPlayer.prepareAsync();
                            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                                @Override
                                public void onBufferingUpdate(MediaPlayer mp, final int percent) {
                                    mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                                        @Override
                                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                                            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                                                ll_SmoothProgressBar.setVisibility(View.VISIBLE);
                                            }
                                            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                                                ll_SmoothProgressBar.setVisibility(View.GONE);
                                            }
                                            if (what == 3) {
                                                ll_SmoothProgressBar.setVisibility(View.GONE);
                                            }
                                            return false;
                                        }
                                    });
                                }
                            });
                            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                                @Override
                                public boolean onError(MediaPlayer mp, int what, int extra) {
                                    // this.playlist="ONERROR";
                                    switch (what) {
                                        case MediaPlayer.MEDIA_ERROR_UNKNOWN: {
                                            //Log.e("TAG2", "unknown media playback error");
                                        }
                                        break;
                                        case MediaPlayer.MEDIA_ERROR_SERVER_DIED: {
                                            //Log.e("TAG2", "server connection died");
                                        }
                                        default: {
                                            //Log.e("TAG2", "generic audio playback error");
                                        }
                                        break;
                                    }

                                    switch (extra) {

                                        case MediaPlayer.MEDIA_ERROR_IO: {
                                            ll_SmoothProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "ارتباط با سرور قطع شده است.", Toast.LENGTH_LONG).show();
                                            // Log.e("TAG2", "IO media error");
                                        }
                                        break;
                                        case MediaPlayer.MEDIA_ERROR_MALFORMED: {
                                            ll_SmoothProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "ارتباط با سرور قطع شده است.", Toast.LENGTH_LONG).show();
                                            // Log.e("TAG2", "media error, malformed");
                                        }
                                        break;
                                        case MediaPlayer.MEDIA_ERROR_UNSUPPORTED: {
                                            ll_SmoothProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "فایل صوتی مورد نظر قابل اجرا نمی باشد.", Toast.LENGTH_LONG).show();
                                            // Log.e("TAG2", "unsupported media content");
                                        }
                                        break;
                                        case MediaPlayer.MEDIA_ERROR_TIMED_OUT: {
                                            ll_SmoothProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "ارتباط با سرور قطع شده است.", Toast.LENGTH_LONG).show();
                                            // Log.e("TAG2", "media timeout error");
                                        }
                                        break;
                                        case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK: {
                                            ll_SmoothProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "ارتباط با سرور قطع شده است.", Toast.LENGTH_LONG).show();
                                            //Log.e("TAG2", "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
                                        }
                                        break;
                                        case MediaPlayer.MEDIA_ERROR_SERVER_DIED: {
                                            ll_SmoothProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "ارتباط با سرور قطع شده است.", Toast.LENGTH_LONG).show();
                                            //Log.e("TAG2", "MEDIA_ERROR_SERVER_DIED");
                                        }
                                        break;
                                        case MediaPlayer.MEDIA_ERROR_UNKNOWN: {
                                            ll_SmoothProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "فایل مورد نظر قابل اجرا نمی باشد.", Toast.LENGTH_LONG).show();
                                            //Log.e("TAG2", "MEDIA_ERROR_UNKNOWN");
                                        }
                                        break;
                                        default: {
                                            ll_SmoothProgressBar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "فایل مورد نظر قابل اجرا نمی باشد.", Toast.LENGTH_LONG).show();
                                            //Log.e("TAG2", "unknown playback error");
                                        }
                                        break;
                                    }


                                    mediaPlayer.seekTo(0);
                                    seekBar.setProgress(0);
                                    mediaPlayer.stop();
                                    mediaPlayer.release();


                                    mediaPlayer = new MediaPlayer();
                                    //dialog.dismiss();


                                    return true;
                                }
                            });

                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {


                                    iv_pause.setVisibility(View.GONE);
                                    iv_play.setVisibility(View.VISIBLE);
                                    mediaPlayer.seekTo(0);
                                    seekBar.setProgress(0);

                                }
                            });
                        }

                    }

                }.start();

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.start();
            primarySeekBarProgressUpdater();

        }

    }

    private void primarySeekBarProgressUpdater() {
//Make sure you update Seekbar on UI thread
        mHandler = new Handler();
        ((Activity) getActivity()).runOnUiThread(new Runnable() {

            @Override
            public void run() {
//                if(mediaPlayer==null)
//                    return;
                long totalDuration = mediaPlayer.getDuration();
                long currentDuration = mediaPlayer.getCurrentPosition();

//                // Displaying Total Duration time
//                songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
//                // Displaying time completed playing
//                songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

                // Updating progress bar
                final int progress = (int) (getProgressPercentage(currentDuration, totalDuration));
                //Log.d("Progress", ""+progress);

                Long h = TimeUnit.MILLISECONDS.toHours(mediaPlayer.getCurrentPosition());
                Long m = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition()) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(mediaPlayer.getCurrentPosition()));
                Long s = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition()));
                if (h > 0) {
                    String hms = String.format("%d:%02d:%02d", h
                            , m
                            , s // The change is in this line
                    );
                    tv_durationleft.setText(hms);
                } else {
                    String hms = String.format("%02d:%02d", m
                            , s // The change is in this line
                    );
                    tv_durationleft.setText(hms);
                }


                seekBar.setProgress(progress);
                mHandler.postDelayed(this, 100);

            }
        });

    }

    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

//    @Override
//    public void onBackPressed() {
//        finish();
//
//    }

    public void ChangeSound() {
        try {
            Video_Sound_Url = "";
            if (mediaPlayer != null) {
                mHandler.removeCallbacksAndMessages(null);
                if (mediaPlayer.isPlaying()) {
                    isstarted = false;


                    iv_play.setVisibility(View.VISIBLE);
                    iv_pause.setVisibility(View.GONE);

                    mediaPlayer.stop();
                    mediaPlayer.release();

                    iv_pause.setEnabled(false);
                    iv_play.setEnabled(true);

                    mediaPlayer = new MediaPlayer();
                    seekBar.setProgress(0);
                    mediaPlayer.seekTo(0);

                } else {
                    isstarted = false;
                    iv_play.setVisibility(View.VISIBLE);
                    iv_pause.setVisibility(View.GONE);


                    mediaPlayer.release();

                    iv_pause.setEnabled(false);
                    iv_play.setEnabled(true);

                    mediaPlayer = new MediaPlayer();
                    seekBar.setProgress(0);
                    mediaPlayer.seekTo(0);

                }
            }
        } catch (Exception ex) {

        }

    }


    public void playmedia() {
        try {
            if (image_list.get(fragVal).getIsvideo()) {
                if (myVideoView.isPlaying()) {
                } else {
                    if (myVideoView.getDuration() <= 0) {
                        myVideoView.start();
                        iv_pause.setVisibility(View.VISIBLE);
                        iv_play.setVisibility(View.GONE);

                        seekBar.setMax(myVideoView.getDuration());
                        seekBar.postDelayed(onEverySecond, 1000);

                        if (newplay) {

                            new AsyncCallVideo().execute(image_list.get(fragVal).getVideoURLPath());
                        }
                    } else {
                        myVideoView.start();
                        iv_pause.setVisibility(View.VISIBLE);
                        iv_play.setVisibility(View.GONE);
                        seekBar.setMax(myVideoView.getDuration());
                        seekBar.postDelayed(onEverySecond, 1000);
                    }

                }
            } else {
                ll_SmoothProgressBar.setVisibility(View.VISIBLE);
                iv_pause.setVisibility(View.VISIBLE);
                iv_play.setVisibility(View.GONE);

                if (image_list.get(fragVal).getVideoURLPath().equals(""))
                    PlayUrl("");
                else {
                    PlayUrl(image_list.get(fragVal).getVideoURLPath());
                }
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void onDestroy() {

        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
        IsDestroyed=true;
        if (mediaPlayer != null) {

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(0);
                seekBar.setProgress(0);

                mediaPlayer.stop();
                mediaPlayer.release();

            }
        }
    }

    boolean isPaused = false;

    // @Override
    public void onPauses() {
        super.onPause();
        try {

            if (image_list.get(fragVal).getIsvideo()) {
                if (myVideoView.isPlaying()) {
                    isPaused = true;
                    myVideoView.pause();
                    iv_pause.setVisibility(View.GONE);
                    iv_play.setVisibility(View.VISIBLE);
                    //iv_Play_video_fake.setVisibility(View.VISIBLE);
                    // iv_pause_fake.setVisibility(View.VISIBLE);
                }
            } else {
                iv_pause.setVisibility(View.GONE);
                iv_play.setVisibility(View.VISIBLE);
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {

                        mediaPlayer.pause();

                    }
                }
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {

            if (image_list.get(fragVal).getIsvideo()) {
                if (myVideoView.isPlaying()) {
                    isPaused = true;
                    myVideoView.pause();
//                    iv_pause.setVisibility(View.GONE);
//                    iv_play.setVisibility(View.VISIBLE);
                    //iv_Play_video_fake.setVisibility(View.VISIBLE);
                    // iv_pause_fake.setVisibility(View.VISIBLE);
                }
            } else {
                iv_pause.setVisibility(View.GONE);
                iv_play.setVisibility(View.VISIBLE);
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {

                        mediaPlayer.pause();

                    }
                }
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {

            if (image_list.get(fragVal).getIsvideo()) {
                if (isFirstPaly) {
                    if (myVideoView.isPlaying()) {
                        isPaused = true;
                        myVideoView.pause();
//                    iv_pause.setVisibility(View.GONE);
//                    iv_play.setVisibility(View.VISIBLE);
                        //iv_Play_video_fake.setVisibility(View.VISIBLE);
                        // iv_pause_fake.setVisibility(View.VISIBLE);
                    } else {
                        myVideoView.seekTo(stopPosition);
                        myVideoView.start();
                        iv_pause.setVisibility(View.VISIBLE);
                        iv_play.setVisibility(View.GONE);
                    }
                }

            } else {
                iv_pause.setVisibility(View.GONE);
                iv_play.setVisibility(View.VISIBLE);
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {

                        mediaPlayer.pause();

                    }
                }
            }
        } catch (Exception ex) {

        }
    }

    int stopPosition = 0;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(myVideoView!=null)
        {
            stopPosition = myVideoView.getCurrentPosition();
            myVideoView.pause();
            outState.putInt("position", stopPosition);
        }

    }
}
