package com.example.ratrofitdatawithimage;

public interface ImageUploadCallbacks {
    void onProgressUpdate(int percentage);

    void onError();

    void onFinish();
}
