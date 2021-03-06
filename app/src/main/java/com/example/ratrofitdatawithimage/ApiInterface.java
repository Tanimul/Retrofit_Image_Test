package com.example.ratrofitdatawithimage;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @Multipart
    @POST("uploadItemImage.php")
    Observable<Response> uploadImage(@Part MultipartBody.Part file,@Part("info") RequestBody description);
}
