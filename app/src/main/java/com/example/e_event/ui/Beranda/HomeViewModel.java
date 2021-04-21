package com.example.e_event.ui.Beranda;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_event.DataEvent;
import com.example.e_event.Event;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    private DataEvent event;

    public HomeViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }
}