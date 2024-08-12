package org.coepmindspark.mindspark.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import org.coepmindspark.mindspark.EventDetailActivity;
import org.coepmindspark.mindspark.EventItemClickListener;
import org.coepmindspark.mindspark.Events;
import org.coepmindspark.mindspark.EventsAdapter;
import org.coepmindspark.mindspark.Slider;
import org.coepmindspark.mindspark.SliderAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.coepmindspark.mindspark.R;

public class HomeFragment extends Fragment implements EventItemClickListener {


    public HomeFragment(){

    }
    List<Slider> sliderList;
    SliderAdapter sliderAdapter;
    ViewPager2 viewPager2;
    private final Handler sliderHandler = new Handler();
    List<Events> eventList;
    RecyclerView ModuleRV;
    FirebaseDatabase database;
    EventsAdapter eventsAdapter;
    boolean isLoaded = false;
    List<Events> allEvents, structura, dynamus, codifica, voltus, logica, robotica, accelero, prodigium, amuzia, illuminati, cognito;
    View loading;
    FirebaseFirestore db;
    View mainView;
    int millis =1000;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        sliderList = new ArrayList<>();
        db= FirebaseFirestore.getInstance();
        loading = root.findViewById(R.id.main_animationView);
        mainView = root.findViewById(R.id.main_view);
        viewPager2 = root.findViewById(R.id.slider);
        database = FirebaseDatabase.getInstance("https://mindspark22-default-rtdb.asia-southeast1.firebasedatabase.app/");
        View no_internet = root.findViewById(R.id.no_internet);

        sliderAdapter = new SliderAdapter(getActivity(),sliderList);
        TabLayout moduleTabs = root.findViewById(R.id.tabModule);
        TextView trendingText = root.findViewById(R.id.trending_title);
        ImageView trendingImg = root.findViewById(R.id.trending_img);
        View trending = root.findViewById(R.id.trending);
        ModuleRV = root.findViewById(R.id.moduleRV);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Handler handler = new Handler();
        // Define the code block to be executed
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                if(isNetAvailable()){

                    if(isLoaded){
                        no_internet.setVisibility(View.GONE);
                        loading.setVisibility(View.GONE);
                        mainView.setVisibility(View.VISIBLE);
                    }else{
                        no_internet.setVisibility(View.GONE);
                        loading.setVisibility(View.VISIBLE);
                        mainView.setVisibility(View.GONE);

                    }

                }
                else{
                    no_internet.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    mainView.setVisibility(View.GONE);
                }
                if(isLoaded){
                    millis = 10000000;
                }
                else{
                    millis = 500;
                }
                handler.postDelayed(this, millis);
            }
        };
        handler.post(runnableCode);
        db.collection("slider")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Slider sliderModel = document.toObject(Slider.class);
                            sliderList.add(sliderModel);
                            sliderAdapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


        viewPager2.setAdapter(sliderAdapter);

        viewPager2.setOffscreenPageLimit(5);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);

        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3500);
            }
        });

        TabLayout indicator = root.findViewById(R.id.indicator);

        new TabLayoutMediator(indicator, viewPager2,true, (tab, position) -> tab.setIcon(Drawable.createFromPath("@drawable/indicator_selector"))).attach();
        Context context =getContext();


        db.collection("trending").document("kpVG2i7XiEZ8cxOQwV5L").get().addOnCompleteListener(

                task -> {
                    trendingText.setText(Objects.requireNonNull(task.getResult().get("title")).toString());

                    assert context != null;
                    Glide.with(context).load(task.getResult().get("title_img")).into(trendingImg);

                    trending.setOnClickListener(view -> {
                        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(Objects.requireNonNull(task.getResult().get("url")).toString()));
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try {
                            HomeFragment.this.startActivity(intent2);
                        } catch (ActivityNotFoundException ex) {
                            // Chrome browser presumably not installed so allow user to choose instead
                            intent2.setPackage(null);
                            HomeFragment.this.startActivity(intent2);
                        }
                    });
                }
        );


        eventList = new ArrayList<>();
        allEvents = new ArrayList<>();
        structura = new ArrayList<>();
        dynamus = new ArrayList<>();
        codifica = new ArrayList<>();
        voltus = new ArrayList<>();
        logica = new ArrayList<>();
        robotica = new ArrayList<>();
        accelero = new ArrayList<>();
        prodigium = new ArrayList<>();
        amuzia = new ArrayList<>();
        illuminati = new ArrayList<>();
        cognito = new ArrayList<>();


        RecyclerView eventRV = root.findViewById(R.id.RV_Popular);


        database.getReference().child("Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Events events = dataSnapshot.getValue(Events.class);
                    assert events != null;
                    if(events.getIsPopular() == 1){
                        eventList.add(events);
                    }
                    Log.d("M", " "+events.getTitle());
                    switch (events.getModule()) {
                        case "structura":
                            structura.add(events);
                            break;
                        case "dynamus":
                            dynamus.add(events);
                            break;
                        case "codifica":
                            codifica.add(events);
                            break;
                        case "voltus":
                            voltus.add(events);
                            break;
                        case "logica":
                            logica.add(events);
                            break;
                        case "robotica":
                            robotica.add(events);
                            break;
                        case "accelero":
                            accelero.add(events);
                            break;
                        case "prodigium":
                            prodigium.add(events);
                            break;
                        case "amuzia":
                            amuzia.add(events);
                            break;
                        case "illuminati":
                            illuminati.add(events);
                            break;
                        case "cognito":
                            cognito.add(events);
                            break;
                    }
                    allEvents.add(events);
                }
                eventsAdapter.notifyDataSetChanged();
                isLoaded = true;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Error Occurred: "+ error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        getStructura();

        moduleTabs.addTab(moduleTabs.newTab().setText("struktura"));
        moduleTabs.addTab(moduleTabs.newTab().setText("potentia"));//dynamus
        moduleTabs.addTab(moduleTabs.newTab().setText("codifica"));
        moduleTabs.addTab(moduleTabs.newTab().setText("voltus"));
        moduleTabs.addTab(moduleTabs.newTab().setText("avionica"));//accelero
        moduleTabs.addTab(moduleTabs.newTab().setText("illuminati"));
        moduleTabs.addTab(moduleTabs.newTab().setText("amuzia"));
        moduleTabs.addTab(moduleTabs.newTab().setText("prodigium"));
        moduleTabs.addTab(moduleTabs.newTab().setText("logica"));
        moduleTabs.addTab(moduleTabs.newTab().setText("robotica"));
        moduleTabs.addTab(moduleTabs.newTab().setText("substantia"));//Cognito
        moduleTabs.setTabGravity(TabLayout.GRAVITY_FILL);
        moduleTabs.setTabTextColors(ColorStateList.valueOf(Color.WHITE));



        moduleTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        getStructura();
                        break;
                    case 1:
                        getDynamus();
                        break;
                    case 2:
                        getCodifica();
                        break;
                    case 3:
                        getVoltus();
                        break;
                    case 4:
                        getAccelero();
                        break;
                    case 5:
                        getIlluminati();
                        break;
                    case 6:
                        getAmuzia();
                        break;
                    case 7:
                        getProdigium();
                        break;
                    case 8:
                        getLogica();
                        break;
                    case 9:
                        getRobotica();
                        break;
                    case 10:
                        getCognito();
                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        eventsAdapter = new EventsAdapter(getActivity(),eventList, this);
        eventRV.setAdapter(eventsAdapter);
        eventRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));



        return root;

    }

    public boolean isNetAvailable(){
        try {
            ConnectivityManager connectivityManager =  (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
        }catch (Exception e ){
            final boolean b = false;
            return b;
        }
    }

    //@Override
    public void onEventClick(Events event, ImageView movieImageView) {

        Intent intent = new Intent(getActivity(), EventDetailActivity.class);
        intent.putExtra("title",event.getTitle());
        intent.putExtra("imgURL",event.getThumbnail());
        intent.putExtra("imgCover",event.getCoverPhoto());
        intent.putExtra("des",event.getDescription());
        intent.putExtra("timestamp",event.getTimestamp());
        intent.putExtra("regLink",event.getEventLink());
        intent.putExtra("x_cord",event.getX_cord());
        intent.putExtra("y_cord",event.getY_cord());
        intent.putExtra("venue",event.getVenue());
        intent.putExtra("price",event.getPrice());
        intent.putExtra("fees",event.getFees());
        intent.putExtra("winners",event.getWinners());


        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                movieImageView,"sharedName");

        startActivity(intent,options.toBundle());



    }
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if(viewPager2.getCurrentItem()<4){
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
            else{
                viewPager2.setCurrentItem(0);
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 2000);
    }

    private void getStructura(){
        eventsAdapter = new EventsAdapter(getActivity(),structura, this);
        ModuleRV.setAdapter(eventsAdapter);
        ModuleRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));

    }
    private void getDynamus(){
        eventsAdapter = new EventsAdapter(getActivity(),dynamus, this);
        ModuleRV.setAdapter(eventsAdapter);
        ModuleRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void getCodifica(){
        eventsAdapter = new EventsAdapter(getActivity(),codifica, this);
        ModuleRV.setAdapter(eventsAdapter);
        ModuleRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void getVoltus(){
        eventsAdapter = new EventsAdapter(getActivity(),voltus,this);
        ModuleRV.setAdapter(eventsAdapter);
        ModuleRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void getLogica(){
        eventsAdapter = new EventsAdapter(getActivity(),logica, this);
        ModuleRV.setAdapter(eventsAdapter);
        ModuleRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void getRobotica(){
        eventsAdapter = new EventsAdapter(getActivity(),robotica,this);
        ModuleRV.setAdapter(eventsAdapter);
        ModuleRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void getAccelero(){
        eventsAdapter = new EventsAdapter(getActivity(),accelero,this);
        ModuleRV.setAdapter(eventsAdapter);
        ModuleRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void getProdigium(){
        eventsAdapter = new EventsAdapter(getActivity(),prodigium,this);
        ModuleRV.setAdapter(eventsAdapter);
        ModuleRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void getAmuzia(){
        eventsAdapter = new EventsAdapter(getActivity(),amuzia,this);
        ModuleRV.setAdapter(eventsAdapter);
        ModuleRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void getIlluminati(){
        eventsAdapter = new EventsAdapter(getActivity(),illuminati,this);
        ModuleRV.setAdapter(eventsAdapter);
        ModuleRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void getCognito(){
        eventsAdapter = new EventsAdapter(getActivity(),cognito,this);
        ModuleRV.setAdapter(eventsAdapter);
        ModuleRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }


}