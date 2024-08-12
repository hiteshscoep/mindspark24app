package org.coepmindspark.mindspark;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class Onboarding extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutIndicators;
    private TextView actionBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_onboarding);
        setupOnBoardingItems();

        ViewPager2 onBoardingViewPager = findViewById(R.id.onboardingViewPager);
        layoutIndicators = findViewById(R.id.layoutIndicators);
        actionBtn = findViewById(R.id.action_btn);

        FirebaseMessaging.getInstance().subscribeToTopic("notification");

        setOnboardingIndicators();
        setCurrentOnboardinIndicator(0);

        onBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardinIndicator(position);
            }
        });

        actionBtn.setOnClickListener(view -> {
            if (onBoardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                onBoardingViewPager.setCurrentItem(onBoardingViewPager.getCurrentItem() + 1);
            } else {
                startActivity(new Intent(Onboarding.this, Login.class));
                finish();
            }
        });
        onBoardingViewPager.setAdapter(onboardingAdapter);
    }

    private void setupOnBoardingItems(){
        List<OnboardingItem> onboardingItems = new ArrayList<>();
        onboardingItems.add(new OnboardingItem(R.drawable.img1, "Welcome to MindSpark!", "MindSpark is the annual, national level technical festival of College of Engineering, Pune. MindSpark is one of the biggest technical college fests in the country."));
        onboardingItems.add(new OnboardingItem(R.drawable.img2, "Get Notified !", "Stay Tuned by getting Notifications from latest Events & Workshops of MindSpark & offers from Sponsors "));
        onboardingItems.add(new OnboardingItem(R.drawable.img3, "Get into MindSpark Family", "Explore & Register for the Events & Workshops, get updated by notifications, participate in event, join the Family "));

        onboardingAdapter = new OnboardingAdapter(getApplicationContext(),onboardingItems);
    }

    private void setOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.setMargins(8,0,8,0);

        for (int i = 0; i<indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutIndicators.addView(indicators[i]);
        }


    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardinIndicator(int index) {
        int childCount = layoutIndicators.getChildCount();

        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.onboarding_indicator_active
                        )
                );

            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.onboarding_indicator_inactive
                        )
                );
            }

        }

        if (index == onboardingAdapter.getItemCount() - 1) {
            actionBtn.setText("FINISH ✅");
        } else {
            actionBtn.setText("NEXT ⏩");
        }
    }
}