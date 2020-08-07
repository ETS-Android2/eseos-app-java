package com.example.eseos.ui.planning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.example.eseos.R;
import com.example.eseos.ui.planning.fabMenu.AddHourActivity;
import com.example.eseos.ui.planning.fabMenu.DelHourActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class PlanningFragment extends Fragment {

    private PlanningRecyclerViewAdapter planningRecyclerViewAdapter;

    public static PlanningFragment newInstance() {
        return new PlanningFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final FloatingActionButton fab, fabAdd, fabDel;
        final Animation fab_open_add, fab_close_add, fab_open_del, fab_close_del;

        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_planning, container, false);

        final boolean[] isFABOpen = new boolean[1];

        fab = root.findViewById(R.id.fab);
        fabAdd = root.findViewById(R.id.fabAdd);
        fabDel = root.findViewById(R.id.fabDel);

        fab_open_add = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open_add);
        fab_open_del = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open_del);
        fab_close_add = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close_add);
        fab_close_del = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close_del);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen[0]){
                    showFABMenu();
                }else{
                    closeFABMenu();
                Snackbar.make(view, "T'as cru que ça faisait quelque chose mdr ?", Snackbar.LENGTH_LONG) //Va disparaître
                        .setAction("Action", null).show();
            }
        }

            private void closeFABMenu() {
                isFABOpen[0]=false;
                fabAdd.startAnimation(fab_close_add);
                fabDel.startAnimation(fab_close_del);

            }

            private void showFABMenu() {
                isFABOpen[0]=true;

                fabAdd.startAnimation(fab_open_add);
                fabDel.startAnimation(fab_open_del);

                fabAdd.setClickable(true);
                fabDel.setClickable(true);
                fabAdd.animate().translationY(-350);
                fabDel.animate().translationY(-180);
            }


            });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();

                Intent homeIntent = new Intent(getContext(), AddHourActivity.class);
                startActivity(homeIntent);

            }

            private void closeFABMenu() {
                isFABOpen[0]=false;
                fabAdd.startAnimation(fab_close_add);
                fabDel.startAnimation(fab_close_del);

            }
        });

        fabDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();

                Intent homeIntent = new Intent(getContext(), DelHourActivity.class);
                startActivity(homeIntent);
            }

            private void closeFABMenu() {
                isFABOpen[0]=false;
                fabAdd.startAnimation(fab_close_add);
                fabDel.startAnimation(fab_close_del);

            }
        });

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this.getContext(), getChildFragmentManager());
        ViewPager viewPager = root.findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = root.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        return root;
    }

}
