package com.example.eseos.ui.management;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eseos.R;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


public class ManagementFragment extends Fragment implements NoticeDialogFragment.NoticeDialogListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_management, container, false);        // Inflate the layout for this fragment

        Button nyButton = root.findViewById(R.id.button_new_year);

        nyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new PassationDialogFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "confirm");
            }
        });


        return root;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //TODO : Modifier les accès du président et du nouveau bureau
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
