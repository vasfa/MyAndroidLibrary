package ir.mlibs.notificationlibrary;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import Fragments.FragmentSliderFragment;

public class FragmentSliderinFragmentActivity extends AppCompatActivity {

    FragmentSliderFragment fragmentSliderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_sliderin_fragment);


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentSliderFragment = (FragmentSliderFragment) fragmentManager.findFragmentById(R.id.fragment_FragmentSliderFragment);


    }
}
