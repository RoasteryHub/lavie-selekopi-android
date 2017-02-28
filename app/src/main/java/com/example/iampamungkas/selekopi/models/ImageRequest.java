package com.example.iampamungkas.selekopi.models;

import android.graphics.Bitmap;

/**
 * Created by iampamungkas on 2/26/17.
 */

public class ImageRequest {
    private Bitmap image;

    public ImageRequest(){}

    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image){
        this.image= image;
    }
}