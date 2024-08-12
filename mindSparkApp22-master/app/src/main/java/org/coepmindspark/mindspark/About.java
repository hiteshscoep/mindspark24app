package org.coepmindspark.mindspark;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.Objects;

import org.coepmindspark.mindspark.R;

public class About extends AppCompatActivity {

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("About MindSpark");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView coverImg = findViewById(R.id.about_coverimg);
        ImageView mainImg = findViewById(R.id.about_logo);

        Glide.with(getApplicationContext()).load(getDrawable(R.drawable.msblue)).placeholder(R.drawable.placeholder_bg).transform(new CenterCrop(), new RoundedCorners(750)).into(mainImg);
        coverImg.setImageResource(R.drawable.particled_back);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}