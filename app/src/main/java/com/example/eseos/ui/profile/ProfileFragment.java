package com.example.eseos.ui.profile;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.eseos.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView profilePicture = (ImageView) root.findViewById(R.id.imageViewProfilePicture);

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref",MODE_PRIVATE);

        int dimenProfilePicture = (int) Math.round(pref.getInt("ScreenWidth",0)*0.3);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dimenProfilePicture, dimenProfilePicture);
        layoutParams.setMargins(30,30,30,30);
        profilePicture.setLayoutParams(layoutParams);

        Switch switchTheme = root.findViewById(R.id.switchTheme);

        final MediaPlayer mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.music);


        switchTheme.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Snackbar.make(root, "T'as vraiment cru qu'il y avait un mode clair ?", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                }
                else {
                    //mediaPlayer.pause(); En vrai ça serait cool de ne pas pouvoir stopper la musique non ? x)
                }
            }
        });

        return root;
    }
}