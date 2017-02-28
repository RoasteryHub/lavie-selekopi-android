package com.example.iampamungkas.selekopi.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by g40 on 27/02/17.
 */

public class AppHelper {

    // Convert bitmap to file and save
    // and temporary save it
    public static File bitmapToFile(Bitmap imageBitmap) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        // save to JPEG, & compress it,
        // TODO: modified this, mungkin bisa nurunin akurasi deteksi kalo kompresi nya terlalu kecil
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image_kopi_temp.jpg");
        try {
            // delete previous file
            if (file.exists()) file.delete();

            file.createNewFile();

            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;

    }
}
