package com.example.eseos.ui.members;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eseos.R;

public class MembersFragment extends Fragment {

    private MembersViewModel membersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        membersViewModel =
                ViewModelProviders.of(this).get(MembersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_members, container, false);
        return root;
    }
}