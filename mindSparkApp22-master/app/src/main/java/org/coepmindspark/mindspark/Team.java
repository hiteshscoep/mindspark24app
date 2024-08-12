package org.coepmindspark.mindspark;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Team extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        Toolbar toolbar = findViewById(R.id.team_toolbar);
        toolbar.setTitle("Team");
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView rv = findViewById(R.id.team_rv);
        View progress = findViewById(R.id.team_animationView);
        ArrayList<org.coepmindspark.mindspark.TeamModel> teamList= new ArrayList<>();
        org.coepmindspark.mindspark.TeamAdapter teamAdapter = new org.coepmindspark.mindspark.TeamAdapter(getApplicationContext(),teamList);
        db.collection("team")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                org.coepmindspark.mindspark.TeamModel offersModel = document.toObject(org.coepmindspark.mindspark.TeamModel.class);
                                teamList.add(offersModel);
                                teamAdapter.notifyDataSetChanged();
                            }

                            rv.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);

                        }
                        else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        rv.setAdapter(teamAdapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}