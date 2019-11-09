package com.example.eseos.ui.management;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ManagementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManagementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is management fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}