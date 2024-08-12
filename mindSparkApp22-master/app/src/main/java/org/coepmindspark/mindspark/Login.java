package org.coepmindspark.mindspark;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {
    View google_btn;
    GoogleSignInClient gsc;
    FirebaseAuth auth;
    View loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        google_btn = findViewById(R.id.google_btn);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this , gso);
        auth =FirebaseAuth.getInstance();
        loading = findViewById(R.id.login_animationView);
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if(acc!=null){
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                google_btn.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                signIn();
            }
        });
    }

    int RC_SIGN_IN=65;
    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                Toast.makeText(Login.this,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                google_btn.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent= new Intent(Login.this,MainActivity.class);
                            startActivity(intent);

                            //updateUI(user);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            google_btn.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                        }
                    }
                });

    }
}