package Fragments;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import ir.mlibs.notificationlibrary.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SideMenuFragment extends Fragment {

    ImageView iv_profile_image;
    LinearLayout LL_MyProfile;
    LinearLayout LL_Inbox;
    TextView TV_InboxCount;
    LinearLayout LL_My_Account_awards;
    LinearLayout LL_Video_Followers;
    TextView TV_Followers_count;
    LinearLayout LL_Invite_friends;
    LinearLayout LL_My_points;
    LinearLayout LL_RegisterChecker;
    LinearLayout LL_about;

    private SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;
    TextView TV_name;


    public SideMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_side_menu, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("NOTIFICATIONDATA", getActivity().MODE_PRIVATE);
        String SP_ActivationCode = sharedPreferences.getString("ACTIVATIONCODE", "");


        iv_profile_image = (ImageView) getActivity().findViewById(R.id.profile_image);
        String profile_url = sharedPreferences.getString("NewProfileImage", "");
        if (profile_url != null && !profile_url.equals("")) {
            String CurrentString = profile_url;

            Picasso.with(getActivity())
                    .load(CurrentString)
                    .placeholder(R.drawable.personlogo)   // optional
                    .error(R.drawable.personlogo)      // optional
                    .into(iv_profile_image);
            String names = "";
        }else{
            //show default image
            // iv_profile_image.setImageResource(R.drawable.category2);
        }
        LL_MyProfile = (LinearLayout) getActivity().findViewById(R.id.Linearlayout_MyProfile);
        LL_Inbox = (LinearLayout) getActivity().findViewById(R.id.Linearlayout_Inbox);
        TV_InboxCount = (TextView) getActivity().findViewById(R.id.TextView_InboxCount);
        LL_My_Account_awards = (LinearLayout) getActivity().findViewById(R.id.Linearlayout_My_Account_awards);
        LL_Video_Followers = (LinearLayout) getActivity().findViewById(R.id.Linearlayout_Video_Followers);
        TV_Followers_count = (TextView) getActivity().findViewById(R.id.TextView_Followers_count);
        LL_Invite_friends = (LinearLayout) getActivity().findViewById(R.id.Linearlayout_Invite_friends);
        LL_My_points = (LinearLayout) getActivity().findViewById(R.id.Linearlayout_My_points);
        LL_RegisterChecker = (LinearLayout) getActivity().findViewById(R.id.linearLayout_registerChecker);
        LL_about = (LinearLayout) getActivity().findViewById(R.id.Linearlayout_about);
        TV_name = (TextView) getActivity().findViewById(R.id.textView_Name);

        String name = sharedPreferences.getString("USERNAME", "");
        String namse = sharedPreferences.getString("PERSONNAME", "");
        
        sharedPreferences = getActivity().getSharedPreferences("NOTIFICATIONDATA", getActivity().MODE_PRIVATE);
        String SP_Msisdn = sharedPreferences.getString("MSISDN", "");
        if (SP_Msisdn.equals("") || SP_Msisdn.length() <= 0) {
            TV_name.setText(sharedPreferences.getString("PERSONNAME", "کاربر مهمان"));
        } else {
            TV_name.setText(sharedPreferences.getString("PERSONNAME", ""));
        }


        iv_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences = getActivity().getSharedPreferences("NOTIFICATIONDATA", getActivity().MODE_PRIVATE);
                String SP_ActivationCode = sharedPreferences.getString("ACTIVATIONCODE", "");
                String SP_Msisdn = sharedPreferences.getString("MSISDN", "");
                if (SP_Msisdn.equals("") || SP_Msisdn.length() <= 0) {
//                    UsermessageBox(TextMessages.NewRegisterMessage);

                    return;

                } else if (SP_ActivationCode.equals("") || SP_ActivationCode.length() <= 0) {
//                    UsermessageBox(TextMessages.NewRegisterMessage);

                    return;

                }
//                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
//
//                startActivity(intent);
            }
        });
        LL_MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
//                startActivity(intent);
            }
        });
        LL_Inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), InboxMessageActivity.class);
//                startActivity(intent);
            }
        });
        LL_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), NewAboutActivity.class);
//                startActivity(intent);
            }
        });
        LL_My_Account_awards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Intent intent = new Intent(getActivity(), GiftActivity.class);
//                startActivity(intent);
            }
        });
        LL_Video_Followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), NewFollowersVideoActivity.class);
//                startActivity(intent);
            }
        });

        LL_Invite_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), NewInviteFriendsActivity.class);
//                startActivity(intent);
            }
        });
        LL_My_points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getActivity(), InvitationActivity.class);
//                startActivity(intent);
            }
        });

    }


    public void changeMyImage() {
        sharedPreferences = getActivity().getSharedPreferences("NOTIFICATIONDATA", getActivity().MODE_PRIVATE);
        String SP_Msisdn = sharedPreferences.getString("MSISDN", "");
        if (SP_Msisdn.equals("") || SP_Msisdn.length() <= 0) {
            return;
        }
        // ImageView iv_profile_image = (ImageView) getActivity().findViewById(R.id.profile_image);
        //iv_profile_image.destroyDrawingCache();
        //iv_profile_image.setImageResource(R.drawable.ic_person);
        String profile_url = sharedPreferences.getString("NewProfileImage", "");
        String old_profile_url = sharedPreferences.getString("OldProfileImage", "");
        String SdCard_profile_url = sharedPreferences.getString("SDCardProfileImage", "");
        String personName = sharedPreferences.getString("PERSONNAME", "کاربر مهمان");
        TextView TV_Title = (TextView) getActivity().findViewById(R.id.textView_Name);
        TV_Title.setText(personName);
        if (old_profile_url.equals(profile_url))
            return;
        else {
            File imgFile = new File(SdCard_profile_url);

            if (imgFile.exists()) {

                iv_profile_image.setImageDrawable(null);
                iv_profile_image.setImageURI(Uri.fromFile(imgFile));

                sharedPreferences = getActivity().getSharedPreferences("NOTIFICATIONDATA", getActivity().MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("OldProfileImage", profile_url);
                editor.commit();


            }

        }

    }

    public void show_hide_items(boolean b_profile,boolean b_notifications,
                          boolean b_videos,boolean b_invitefriends,
                          boolean b_friendcode,boolean b_gift,boolean b_about)
    {
        if(b_profile)
        {
            showitems(LL_MyProfile);
        }else
        {
            hideitems(LL_MyProfile);
        }

        if(b_notifications)
        {
            showitems(LL_Inbox);
        }else
        {
            hideitems(LL_Inbox);
        }

        if(b_videos)
        {
            showitems(LL_Video_Followers);
        }else
        {
            hideitems(LL_Video_Followers);
        }

        if(b_invitefriends)
        {
            showitems(LL_Invite_friends);
        }else
        {
            hideitems(LL_Invite_friends);
        }
        if(b_friendcode)
        {
            showitems(LL_My_points);
        }else
        {
            hideitems(LL_My_points);
        }
        if(b_gift)
        {
            showitems(LL_My_Account_awards);
        }else
        {
            hideitems(LL_My_Account_awards);
        }
        if(b_about)
        {
            showitems(LL_about);
        }else
        {
            hideitems(LL_about);
        }

    }


    private void showitems(LinearLayout L_Layout)
    {
        try{
            L_Layout.setVisibility(View.VISIBLE);
        }
        catch (Exception ex)
        {

        }
    }

    private void hideitems(LinearLayout L_Layout)
    {
        try{
            L_Layout.setVisibility(View.GONE);
        }
        catch (Exception ex)
        {

        }
    }



}

