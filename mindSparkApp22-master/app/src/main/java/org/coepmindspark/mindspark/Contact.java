package org.coepmindspark.mindspark;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.Objects;

import org.coepmindspark.mindspark.R;

public class Contact extends AppCompatActivity {
    EditText msg, name, email;
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = findViewById(R.id.contact_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Contact");
        Button send = findViewById(R.id.contact_send_button);
        send.setOnClickListener(view -> {
            if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(msg.getText().toString()) || TextUtils.isEmpty(name.getText().toString())){
                Toast.makeText(Contact.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "mindsparkapp@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "My subject");
                intent.putExtra(Intent.EXTRA_SUBJECT, "MS'22 App review");
                intent.putExtra(Intent.EXTRA_TEXT, name.getText().toString() + "\n" + msg.getText().toString());
                startActivity(intent);
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView coverImg = findViewById(R.id.contact_coverimg);
        ImageView mainImg = findViewById(R.id.contact_logo);
        msg = findViewById(R.id.message);
        name = findViewById(R.id.name);
        email = findViewById(R.id.EmailAddress);

        Glide.with(getApplicationContext()).load(getDrawable(R.drawable.msblue)).placeholder(R.drawable.placeholder_bg).transform(new CenterCrop(), new RoundedCorners(750)).into(mainImg);
        coverImg.setImageResource(R.drawable.particled_back);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}