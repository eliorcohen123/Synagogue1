package com.eliorcohen1.synagogue.PagesPackage;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.eliorcohen1.synagogue.CustomAdaptersPackage.CustomAdapterResponsibles;
import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.ModelsPackage.TotalModel;

import java.util.ArrayList;

public class ResponsiblesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backResponsible;
    private ArrayList<TotalModel> arrayList;
    private RecyclerView rv;
    private CustomAdapterResponsibles adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsibles);

        initUI();
        initListeners();
        initRecyclerView();
        showUI();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backResponsible = findViewById(R.id.backResponsible);
        rv = findViewById(R.id.listResponsible);

        arrayList = new ArrayList<TotalModel>();
    }

    private void initListeners() {
        backResponsible.setOnClickListener(this);
    }

    private void initRecyclerView() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showUI() {
        arrayList.add(new TotalModel("אבי קריאף", "054-4807328"));
        arrayList.add(new TotalModel("שלום ניסים", "052-9426607"));
        arrayList.add(new TotalModel("מוטי כהן", "054-4917147"));
        adapter = new CustomAdapterResponsibles(arrayList, this);
        adapter.setNames(arrayList);
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backResponsible:
                onBackPressed();
                break;
        }
    }

}
