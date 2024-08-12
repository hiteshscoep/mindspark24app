package org.coepmindspark.mindspark;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        View delayed = findViewById(R.id.delayed);

        setStatusBarTransparent();
        ImageView sponsor = findViewById(R.id.splash_sponsor_logo);
        videoView= findViewById(R.id.video_view);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.splashvid);
        videoView.start();
        Glide.with(SplashScreen.this);
        new Handler().postDelayed(() -> {
            if(isOpenedAlready()){
                GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
                if(acc!=null){
                    Intent intent = new Intent(SplashScreen.this, org.coepmindspark.mindspark.MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashScreen.this, org.coepmindspark.mindspark.Login.class);
                    startActivity(intent);
                }
            }
            else {
                SharedPreferences.Editor editor = getSharedPreferences("slide", MODE_PRIVATE).edit();
                editor.putBoolean("slide", true);
                editor.apply();
                Intent intent = new Intent(SplashScreen.this, org.coepmindspark.mindspark.Onboarding.class);
                startActivity(intent);
            }
            finish();
        }, 6000);
        new Handler().postDelayed(() -> {
            delayed.setVisibility(View.VISIBLE);
        }, 1300);

    }

    private boolean isOpenedAlready(){
        SharedPreferences sharedPreferences = getSharedPreferences("slide", MODE_PRIVATE);
        return sharedPreferences.getBoolean("slide", false);
    }
    private void setStatusBarTransparent() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        View decorView = window.getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        window.setStatusBarColor(Color.TRANSPARENT);
    }
}