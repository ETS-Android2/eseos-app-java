package com.example.eseos.ui.planning;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ManageCalendar {
    public String[] nextDays(String closed_days, String today, String tomorrow) {
        String[] TAB_TITLES = new String[3];
        Calendar calendar = Calendar.getInstance();

        int today_date = calendar.getTime().getDate();

        for(int j = 0; j <= 2; j++) {
            while(closed_days.contains(calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.US))){
                calendar.add(Calendar.DAY_OF_WEEK,1);
            }

            if (today_date == calendar.getTime().getDate()) {
                TAB_TITLES[j] = today;
            } else if (today_date == calendar.getTime().getDate()-1) {
                TAB_TITLES[j] = tomorrow;
            } else {
                TAB_TITLES[j] = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US);
            }
            calendar.add(Calendar.DAY_OF_WEEK,1);
        }
        return TAB_TITLES;
    }

    public Date[] nextDates(String closed_days) {
        Date[] TAB_DATES = new Date[3];
        Calendar calendar = Calendar.getInstance();

        for(int j = 0; j <= 2; j++) {
            while(closed_days.contains(calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.US))){
                calendar.add(Calendar.DAY_OF_WEEK,1);
            }
            TAB_DATES[j] = calendar.getTime();

            calendar.add(Calendar.DAY_OF_WEEK,1);
        }

        return TAB_DATES;
    }

}
