package com.example.eseos.ui.planning;

import android.content.Context;
import android.util.Log;

import com.example.eseos.R;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private static String[] TAB_TITLES = new String[]{"Today", "Tomorrow", "Day 3"};
    private final Context mContext;
    private Calendar calendar;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        calendar = Calendar.getInstance();
        Log.d("CALENDAR",calendar.getTime().toString());
        ManageCalendar manageCalendar = new ManageCalendar();

        String closed_days = mContext.getResources().getString(R.string.closed_days);
        String today = mContext.getResources().getString(R.string.tab_day_today);
        String tomorrow = mContext.getResources().getString(R.string.tab_day_tomorrow);


        TAB_TITLES = manageCalendar.nextDays(closed_days, today, tomorrow);
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
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}