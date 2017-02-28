package com.example.iampamungkas.selekopi.interfaces;

import com.example.iampamungkas.selekopi.models.ImageResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by iampamungkas on 2/28/17.
 */

public interface SeleKopiAPI {
    @Multipart
    @POST("KopiSelection/")
    Call<ImageResponse> getImage(@Part MultipartBody.Part imageRequest);
}
