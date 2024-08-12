package org.coepmindspark.mindspark;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.coepmindspark.mindspark.fragment.BlogFragment;
import org.coepmindspark.mindspark.fragment.HomeFragment;
import org.coepmindspark.mindspark.fragment.ModuleFragment;
import org.coepmindspark.mindspark.fragment.MoreFragment;
import org.coepmindspark.mindspark.fragment.TimelineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frame_layout);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            View home_btn = findViewById(R.id.home_btn);
            View cat_btn = findViewById(R.id.category_btn);
            View timeline_btn = findViewById(R.id.timeline_btn);
            View more_btn = findViewById(R.id.more_btn);
            View blog_btn = findViewById(R.id.blog_btn);
            ImageView img = findViewById(R.id.home_img_logo);

            switch (item.getItemId()){
                case R.id.home_btn:
                    HomeFragment homeFragment = new HomeFragment();
                    selectedFragment(homeFragment);
                    home_btn.setSelected(true);
                    cat_btn.setSelected(false);
                    timeline_btn.setSelected(false);
                    more_btn.setSelected(false);
                    blog_btn.setSelected(false);
                    img.setVisibility(View.VISIBLE);
                    toolbar.setTitle("");
                    break;
                case R.id.category_btn:
                    ModuleFragment moduleFragment = new ModuleFragment();
                    selectedFragment(moduleFragment);
                    home_btn.setSelected(false);
                    cat_btn.setSelected(true);
                    timeline_btn.setSelected(false);
                    more_btn.setSelected(false);
                    blog_btn.setSelected(false);
                    img.setVisibility(View.GONE);
                    toolbar.setTitle("Offers ");
                    break;
                case R.id.timeline_btn:
                    TimelineFragment timelineFragment = new TimelineFragment();
                    selectedFragment(timelineFragment);
                    home_btn.setSelected(false);
                    cat_btn.setSelected(false);
                    timeline_btn.setSelected(true);
                    more_btn.setSelected(false);
                    blog_btn.setSelected(false);
                    img.setVisibility(View.GONE);
                    toolbar.setTitle("Event Timeline");
                    break;
                case R.id.more_btn:
                    MoreFragment moreFragment = new MoreFragment();
                    selectedFragment(moreFragment);
                    home_btn.setSelected(false);
                    cat_btn.setSelected(false);
                    timeline_btn.setSelected(false);
                    more_btn.setSelected(true);
                    blog_btn.setSelected(false);
                    img.setVisibility(View.VISIBLE);
                    toolbar.setTitle("");
                    break;
                case R.id.blog_btn:
                    BlogFragment blogFragment = new BlogFragment();
                    selectedFragment(blogFragment);
                    home_btn.setSelected(false);
                    cat_btn.setSelected(false);
                    timeline_btn.setSelected(false);
                    more_btn.setSelected(false);
                    blog_btn.setSelected(true);
                    img.setVisibility(View.GONE);
                    toolbar.setTitle("MindSpark Monday");
                    break;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.home_btn);

    }

    private void selectedFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}