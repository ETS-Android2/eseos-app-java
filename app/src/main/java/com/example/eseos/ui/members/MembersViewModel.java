package com.example.eseos.ui.members;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MembersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MembersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is members fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}