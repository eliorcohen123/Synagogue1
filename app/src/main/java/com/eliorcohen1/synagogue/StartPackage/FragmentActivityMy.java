package com.eliorcohen1.synagogue.StartPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.eliorcohen1.synagogue.R;

public class FragmentActivityMy extends AppCompatActivity implements View.OnClickListener {

    private Button backMap;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        initUI();
        initListeners();
        showUI();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backMap = findViewById(R.id.backMap);
        coordinatorLayout = findViewById(R.id.myContent);
    }

    private void initListeners() {
        backMap.setOnClickListener(this);
    }

    private void showUI() {
        Snackbar.make(coordinatorLayout, R.string.item_removed_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, v -> {
                    // Respond to the click, such as by undoing the modification that caused
                    // this message to be displayed
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backMap:
                Intent intent = new Intent(FragmentActivityMy.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

}
