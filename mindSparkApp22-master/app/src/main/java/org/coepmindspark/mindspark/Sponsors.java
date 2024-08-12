package org.coepmindspark.mindspark;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class Sponsors extends AppCompatActivity {
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);

        Toolbar toolbar = findViewById(R.id.sponsors_toolbar);
        toolbar.setTitle("Sponsors");
        setSupportActionBar(toolbar);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView rv = findViewById(R.id.sponsors_rv);
        View progress = findViewById(R.id.sponsors_animationView);
        ArrayList<org.coepmindspark.mindspark.SponsorsModel> sponsorsList= new ArrayList<>();
        org.coepmindspark.mindspark.SponsorsAdapter sponsorsAdapter = new org.coepmindspark.mindspark.SponsorsAdapter(getApplicationContext(),sponsorsList);
        db.collection("sponsors")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            org.coepmindspark.mindspark.SponsorsModel sponsorsModel = document.toObject(org.coepmindspark.mindspark.SponsorsModel.class);
                            sponsorsList.add(sponsorsModel);
                            sponsorsAdapter.notifyDataSetChanged();
                        }

                        rv.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);

                    }
                    else {
                        Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(sponsorsAdapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
