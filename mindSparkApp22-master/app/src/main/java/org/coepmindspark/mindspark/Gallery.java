package org.coepmindspark.mindspark;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class Gallery extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<GalleryModel> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery2);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        GalleryAdapter galleryAdapter = new GalleryAdapter(list, this);
        rv.setAdapter(galleryAdapter);
        Toolbar toolbar = findViewById(R.id.gallery_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Gallery");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db.collection("gallery").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for (QueryDocumentSnapshot document : task.getResult()) {
                    GalleryModel galleryModel = document.toObject(GalleryModel.class);
                    list.add(galleryModel);
                    galleryAdapter.notifyDataSetChanged();
                }

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}