package com.eliorcohen1.synagogue.StartPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.eliorcohen1.synagogue.R;

public class FragmentActivityMy extends AppCompatActivity {

    Button backMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backMap = findViewById(R.id.backMap);

        backMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FragmentActivityMy.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
