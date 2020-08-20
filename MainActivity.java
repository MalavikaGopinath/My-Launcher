package com.example.mylauncher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    private WallpaperManager wallpaperManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.menu_start: {
                        Intent intent = new Intent(getApplicationContext(), AppsListActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.menu_wallpaper : {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,  "Change Wallpaper"),1);
                        break;
                    }
                }

                return true;
            }
        });

        wallpaperManager = WallpaperManager.getInstance(this);
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {

            try {

                InputStream inputStream = getContentResolver().openInputStream(data.getData());

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                wallpaperManager.setBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}