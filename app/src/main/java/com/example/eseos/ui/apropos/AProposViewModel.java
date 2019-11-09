package com.example.eseos.ui.apropos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class AProposViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AProposViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is APropos fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}