package org.coepmindspark.mindspark;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class Profile extends AppCompatActivity {

    GoogleSignInClient gsc;
    EditText name, email;
    ImageView profileImg;
    Button signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Profile");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profileEmailAddress);
        signout = findViewById(R.id.logout_button);
        profileImg = findViewById(R.id.profile_logo);
        ImageView coverImg =  findViewById(R.id.profile_coverimg);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if(acc!=null){
            String personName = acc.getDisplayName();
            String personEmail = acc.getEmail();
            Uri dp = acc.getPhotoUrl();
            if(dp!=null){
                Glide.with(getApplicationContext()).load(dp).transform(new CenterCrop(), new RoundedCorners(750)).into(profileImg);
            }else{
                Glide.with(getApplicationContext()).load("https://cdn3d.iconscout.com/3d/premium/thumb/student-5796558-4841557.png").transform(new CenterCrop(), new RoundedCorners(750)).into(profileImg);
            }
            name.setText(personName);
            email.setText(personEmail);

        }
        Glide.with(getApplicationContext()).load("https://cdn4.vectorstock.com/i/1000x1000/91/98/abstract-technology-background-vector-8119198.jpg").centerCrop().into(coverImg);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(Profile.this, Login.class));
                    }
                });
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}