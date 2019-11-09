package com.example.eseos.ui.apropos;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;

import com.example.eseos.R;

public class AProposFragment extends Fragment {

    private AProposViewModel AProposViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AProposViewModel =
                ViewModelProviders.of(this).get(AProposViewModel.class);
        View root = inflater.inflate(R.layout.fragment_apropos, container, false);
        /*final TextView textView = root.findViewById(R.id.text_apropos);
        AProposViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/




        TextClock clock = root.findViewById(R.id.textClock);
        return root;
    }
}