package com.example.eseos.ui.club_devis;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
