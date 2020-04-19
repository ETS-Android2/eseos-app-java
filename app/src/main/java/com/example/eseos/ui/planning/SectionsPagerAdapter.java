package com.example.eseos.ui.planning;

import android.content.Context;

import com.example.eseos.R;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_day_1, R.string.tab_day_2, R.string.tab_day_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public PlanningPageFragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlanningPageFragment.newInstance(position);

        /*switch(position) {
            case 0:
                Log.d("RETOUR_FRAG", "0");
                return PlanningPageFragment.newInstance(0);
            case 1:
                Log.d("RETOUR_FRAG", "1");
                return PlanningPageFragment.newInstance(1);
            case 2:
                Log.d("RETOUR_FRAG", "2");
                return PlanningPageFragment.newInstance(2);
            default:
                return null;
        }*/
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}