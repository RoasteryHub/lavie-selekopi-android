package com.example.iampamungkas.selekopi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by iampamungkas on 2/26/17.
 */

public class ImageResponse {

    @SerializedName("success")
    @Expose
    private String success;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("error")
    @Expose
    private String error;

    public ImageResponse(){}

    public String getImage() {
        return image;
    }

    public String getSuccess(){
        return success;
    }

    public String getError(){
        return this.error;
    }

    @Override
    public String toString() {
        if (this.image != null){
            return "ImageResponse{" +
                    "success='" + success + '\'' +
                    ", image='" + "ImageBase64Contain" + '\'' +
                    ", error='" + error + '\'' +
                    '}';
        } else {
            return "ImageResponse{" +
                    "success='" + success + '\'' +
                    ", image='" + image + '\'' +
                    ", error='" + error + '\'' +
                    '}';
        }
    }
}
