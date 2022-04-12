package com.example.ratrofitdatawithimage;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Repository {
    private static final String TAG = "repository";

    private ApiInterface apiInterface;

    public Repository() {
        apiInterface = ApiClient.getClient();

    }

    public LiveData<Response> uploadItemImage(File file ,String test, ProgressRequestBody progressRequestBody){
        final MutableLiveData<Response> response=new MutableLiveData<>();

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(),progressRequestBody);
        Gson gson = new Gson();
        String patientData = gson.toJson(test);

        RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, patientData);
        Log.d(TAG, "imageUpload: repo called");
        apiInterface.uploadImage(description,body).subscribeOn(Schedulers.io())
                //.timeout(120000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response modelUploadResponse) throws Exception {
                        Log.d(TAG, "accept: "+modelUploadResponse);
                        response.postValue(modelUploadResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "imageUpload: error:"+throwable.getLocalizedMessage());
                        response.postValue(null);
                    }
                });
        return response;
    }
}
