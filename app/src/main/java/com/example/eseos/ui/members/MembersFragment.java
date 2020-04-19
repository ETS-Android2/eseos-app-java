package com.example.eseos.ui.members;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eseos.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MembersFragment extends Fragment {

    private MembersViewModel membersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_members, container, false);

        return root;
    }
}