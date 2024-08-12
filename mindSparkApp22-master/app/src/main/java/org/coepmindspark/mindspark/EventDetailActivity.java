package org.coepmindspark.mindspark;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Random;

import org.coepmindspark.mindspark.R;

public class EventDetailActivity extends AppCompatActivity implements OnMapReadyCallback{
    Toolbar toolbar;
    TextView title , date,description_view, fees_view, price_view;
    Button register_btn1,register_btn2, share_btn;
    double x_cord, y_cord;
    String venue;
    MapView mapView;
    View eventDetailAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String movieUrl = getIntent().getExtras().getString("imgURL");
        String coverUrl = getIntent().getExtras().getString("imgCover");
        String des = getIntent().getExtras().getString("des").replace("⬜","\n");
        String regLink = getIntent().getExtras().getString("regLink");
        String fees = getIntent().getExtras().getString("fees");
        venue = getIntent().getExtras().getString("venue");
        String price = getIntent().getExtras().getString("price");
        String winners = getIntent().getExtras().getString("winners");
        x_cord =getIntent().getExtras().getDouble("x_cord");
        y_cord = getIntent().getExtras().getDouble("y_cord");
        title = findViewById(R.id.detail_movie_title);
        date = findViewById(R.id.detail_date);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        description_view = findViewById(R.id.detail_movie_desc);
        price_view = findViewById(R.id.detail_price);
        fees_view = findViewById(R.id.detail_fees);
        register_btn1 = findViewById(R.id.register_btn1);
        register_btn2 = findViewById(R.id.register_btn2);
        share_btn = findViewById(R.id.share_btn);

        description_view.setText(des);
        price_view.setText(String.format("Prize: %s", price));
        fees_view.setText(String.format("Fees: %s", fees));

        ImageView movieThumbnailImg = findViewById(R.id.detail_movie_img);
        ImageView winnersimg = findViewById(R.id.winners_img);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("title"));
        title.setText(getIntent().getExtras().getString("title"));
        long timestamp = getIntent().getExtras().getLong("timestamp");
        Glide.with(EventDetailActivity.this).load(movieUrl).transform(new CenterCrop(), new RoundedCorners(25)).placeholder(R.drawable.placeholder_bg).error(R.drawable.eventerror).into(movieThumbnailImg);
        ImageView movieCoverImg = findViewById(R.id.detail_movie_cover);
        Glide.with(EventDetailActivity.this).load(coverUrl).transform(new CenterCrop()).placeholder(R.drawable.placeholder_bg).error(R.drawable.techbg).into(movieCoverImg);
        eventDetailAnimation = findViewById(R.id.event_detail_animationView);
        Timestamp timeStamp = new Timestamp(timestamp);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
        if(timestamp==0){
            date.setText("Coming soon...");
        }else{
            date.setText(simpleDateFormat.format(timeStamp));
        }

        TextView address = findViewById(R.id.address);
        address.setText(venue);
        address.setOnClickListener(view -> {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+x_cord+","+y_cord);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        });
        register_btn1.setOnClickListener(view -> {
            if(!Objects.equals(regLink, "NA")){
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(regLink));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    EventDetailActivity.this.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    intent.setPackage(null);
                    EventDetailActivity.this.startActivity(intent);
                }
            }else{
                Toast.makeText(this, "Registrations Will Start Soon!", Toast.LENGTH_SHORT).show();
            }

        });
        register_btn2.setOnClickListener(view -> {
            if(!Objects.equals(regLink, "NA")){
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(regLink));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    EventDetailActivity.this.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    intent.setPackage(null);
                    EventDetailActivity.this.startActivity(intent);
                }
            }else{
                Toast.makeText(this, "Registrations Will Start Soon!", Toast.LENGTH_SHORT).show();
            }
        });
        if(winners.equals("NA")){
            winnersimg.getLayoutParams().height = 0;
            winnersimg.getLayoutParams().width = 10;
            winnersimg.requestLayout();

        }
        else{
            Glide.with(EventDetailActivity.this).load(winners).into(winnersimg);
        }


        share_btn.setOnClickListener(view -> {
            eventDetailAnimation.setVisibility(View.VISIBLE);
            Thread thread = new Thread(() -> {
                try {
                    URL url = null;
                    try {
                        url = new URL(movieUrl);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    HttpURLConnection connection = null;
                    try {
                        assert url != null;
                        connection = (HttpURLConnection) url.openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert connection != null;
                    connection.setDoInput(true);
                    try {
                        connection.connect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    InputStream input = null;
                    try {
                        input = connection.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap imgBitmap = BitmapFactory.decodeStream(input);
                    Random rand = new Random();
                    int randNo = rand.nextInt(100000);
                    String imgBitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),imgBitmap,"MSIMG:"+randNo,null);
                    Uri imgBitmapUri = Uri.parse(imgBitmapPath);

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
                    shareIntent.setType("image/png");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    shareIntent.putExtra(Intent.EXTRA_TEXT,"Hey! Got the Event on MindSpark App that you May be interested in \nRegister for the Event : " + regLink);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Event from MindSpark'22 :"+getIntent().getStringExtra("title"));
                    try {
                        startActivity(Intent.createChooser(shareIntent,"Share with"));
                    } catch (ActivityNotFoundException ex) {
                        Toast.makeText(EventDetailActivity.this, "Error: "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    eventDetailAnimation.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            thread.start();
        });
        mapView.getMapAsync(EventDetailActivity.this);


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(x_cord, y_cord))
                .title(venue));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(x_cord,y_cord), 17.0f));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}