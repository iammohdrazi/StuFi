package com.example.petinfo.ui.students;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StudentsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StudentsViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}