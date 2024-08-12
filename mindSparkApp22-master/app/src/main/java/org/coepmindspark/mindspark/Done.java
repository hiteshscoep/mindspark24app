package org.coepmindspark.mindspark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.coepmindspark.mindspark.fragment.MoreFragment;

public class Done extends AppCompatActivity {
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        back = findViewById(R.id.done_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent someIntent = new Intent(Done.this, PhotoWithMS.class);
                startActivity(someIntent);
            }
        });

    }
}