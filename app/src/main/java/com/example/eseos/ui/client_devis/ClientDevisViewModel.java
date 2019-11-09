package com.example.eseos.ui.client_devis;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ClientDevisViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ClientDevisViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}