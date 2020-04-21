package com.example.petinfo.ui.assignment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AssignmentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AssignmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Assignment fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}