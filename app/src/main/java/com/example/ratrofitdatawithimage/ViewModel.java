package com.example.ratrofitdatawithimage;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.io.File;

public class ViewModel extends AndroidViewModel {
    Repository repository;
    public ViewModel(@NonNull Application application) {
        super(application);
        if(repository==null){
            repository=new Repository();
        }
    }

    LiveData<Response> uploadItemImage(File file , ModelUploadInfo modelUploadInfo , ProgressRequestBody progressRequestBody){
        Log.d("viewmodel", modelUploadInfo+"uploadItemImage: "+file);
        return  repository.uploadItemImage(file,modelUploadInfo,progressRequestBody);
    }

}
