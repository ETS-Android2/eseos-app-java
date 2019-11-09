package com.example.eseos.ui.club_devis;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ClubDevisViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ClubDevisViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
