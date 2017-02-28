package com.example.iampamungkas.selekopi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iampamungkas.selekopi.interfaces.SeleKopiAPI;
import com.example.iampamungkas.selekopi.models.ImageRequest;
import com.example.iampamungkas.selekopi.models.ImageResponse;
import com.example.iampamungkas.selekopi.util.AppHelper;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeleksiKopi extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton fab;
    ImageView imageView;
    static final int CAM_REQUEST =1;
    private SeleKopiAPI service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleksi_kopi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        imageView =(ImageView) findViewById(R.id.image_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.79:8000/")
                //.baseUrl("http://127.0.0.1:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(SeleKopiAPI.class);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (camera_intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(camera_intent, CAM_REQUEST);
                }
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            final Bitmap imageBitmap = (Bitmap) extras.get("data");
            //Sending Post Request to server
            ImageRequest imageRequest = new ImageRequest();
            imageRequest.setImage(imageBitmap);

            // disable view image in first time
            //imageView.setImageBitmap(imageBitmap);

            // Change Request Boy to ImageFiles
            File fileImage = AppHelper.bitmapToFile(imageBitmap);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), fileImage);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", fileImage.getName(), requestFile);

            Call<ImageResponse> imageResponseCall = service.getImage(filePart);
            imageResponseCall.enqueue(new Callback<ImageResponse>() {
                @Override
                public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                    int statusCode = response.code();
                    Log.d("MainActivity", "response body "+ response.body());

                    ImageResponse imageResponse = response.body();
                    byte[] decodedString = Base64.decode(imageResponse.getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imageView.setImageBitmap(decodedByte);

                    // set status code
                    //mTextStatusCode.setText("Status Code " + String.valueOf(statusCode));
                }

                @Override
                public void onFailure(Call<ImageResponse> call, Throwable t) {
                    //imageView.setImageBitmap(imageBitmap);
                    Log.d("MainActivity", "onFailure:"+ t.getMessage());

                    // set status code
                    //mTextStatusCode.setText("Failed Connection");

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seleksi_kopi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
