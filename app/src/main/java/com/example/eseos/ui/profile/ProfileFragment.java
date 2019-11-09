package com.example.eseos.ui.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.eseos.R;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView profilePicture = (ImageView) root.findViewById(R.id.imageViewProfilePicture);

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref",MODE_PRIVATE);

        int dimenProfilePicture = (int) Math.round(pref.getInt("ScreenWidth",0)*0.3);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(dimenProfilePicture, dimenProfilePicture);
        layoutParams.setMargins(30,30,30,30);
        profilePicture.setLayoutParams(layoutParams);

        return root;
    }
}