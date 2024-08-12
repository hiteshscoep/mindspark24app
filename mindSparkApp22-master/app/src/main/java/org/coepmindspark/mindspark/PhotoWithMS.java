package org.coepmindspark.mindspark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;


public class PhotoWithMS extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE = 101;
    private View btn;
    StorageReference ref;
    ProgressBar progressBar;
    EditText insta_id;
    View animation;
    String personEmail;
    Button submit_btn;
    Uri uri;
    TextView filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_with_ms);

        Toolbar toolbar = findViewById(R.id.photowithms_toolbar);
        toolbar.setTitle("Photo with MindSpark");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn = findViewById(R.id.photowithmsbtn);
        submit_btn = findViewById(R.id.photowithmssubmitbtn);
        filename = findViewById(R.id.photowithmstextView13);
        insta_id = findViewById(R.id.insta_id);
        progressBar = findViewById(R.id.photowithmsprogressBar);
        animation = findViewById(R.id.photoanimationView);
        ref = FirebaseStorage.getInstance().getReference().child("PhotoWithMS");
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if(acc!=null){
            personEmail = acc.getEmail();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(insta_id.getText().toString())){
                    Toast.makeText(PhotoWithMS.this, "Please enter Instagram ID", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_CODE_IMAGE);
                }

            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri!=null) {
                    progressBar.setProgress(10);
                    Date today = new Date();
                    Timestamp ts1 = new Timestamp(today.getTime());
                    long tsTime1 = ts1.getTime();
                    ref.child(insta_id.getText().toString() + "ML" + personEmail + "TMS" + tsTime1 + ".jpg").putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressBar.setProgress(0);
                            filename.setText("Select image to upload");
                            insta_id.setText("");
                            AlertDialog.Builder dialog = new AlertDialog.Builder(PhotoWithMS.this);
                            dialog.setMessage("Image has been uploaded. It will be soon on our media handles");
                            dialog.setTitle("Upload Successful!");
                            dialog.setIcon(R.drawable.upload);
                            dialog.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.dismiss();
                                        }
                                    });

                            AlertDialog alertDialog = dialog.create();
                            alertDialog.show();
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            int progress = (int) ((90 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount()) + 10;
                            progressBar.setMax(100);
                            progressBar.setProgress(progress);
                        }
                    });
                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(PhotoWithMS.this);
                    dialog.setMessage("Please select image .");
                    dialog.setTitle("No image selected");
                    dialog.setIcon(R.drawable.upload);
                    dialog.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && data!=null){
            uri = data.getData();
            Cursor returnCursor =
                    getContentResolver().query(uri, null, null, null, null);
            /*
             * Get the column indexes of the data in the Cursor,
             * move to the first row in the Cursor, get the data,
             * and display it.
             */

            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            filename.setText(returnCursor.getString(nameIndex));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}