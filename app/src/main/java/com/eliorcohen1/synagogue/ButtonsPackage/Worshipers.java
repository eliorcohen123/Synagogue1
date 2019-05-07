package com.eliorcohen1.synagogue.ButtonsPackage;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.eliorcohen1.synagogue.CustomAdapterPackage.AdapterWorshipers;
import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.StartPackage.MainActivity;
import com.eliorcohen1.synagogue.StartPackage.TotalModel;

import java.util.ArrayList;

public class Worshipers extends AppCompatActivity {

    private Button backWorshipers;
    private ArrayList<TotalModel> arrayList;
    private RecyclerView rv;
    private AdapterWorshipers adapter;
    private android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worshipers);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backWorshipers = findViewById(R.id.backWorshipers);

        rv = findViewById(R.id.listWorshipers);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        adapter = new AdapterWorshipers(arrayList, this);

        backWorshipers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Worshipers.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayList = new ArrayList<TotalModel>();
        arrayList.add(new TotalModel("צבי נעמת", "|", "052-8345360"));
        arrayList.add(new TotalModel("אבי קריאף", "|", "054-4807328"));
        arrayList.add(new TotalModel("אברהם כהנא", "|", "054-7684689"));
        arrayList.add(new TotalModel("נסים ביטון", "|", "050-2441207"));
        arrayList.add(new TotalModel("מאיר שניאור", "|", "050-8512125"));
        arrayList.add(new TotalModel("יוני עוקשי", "|", "052-6678063"));
        arrayList.add(new TotalModel("יאיר נעמת", "|", "050-9915165"));
        arrayList.add(new TotalModel("אשר אפוטה", "|", "052-9452458"));
        arrayList.add(new TotalModel("יואב אליהו", "|", "052-2402723"));
        arrayList.add(new TotalModel("אליסף ורמר", "|", "050-6247116"));
        arrayList.add(new TotalModel("אריה דרעי", "|", "050-7751518"));
        arrayList.add(new TotalModel("יגאל דרי", "|", "050-2638434"));
        arrayList.add(new TotalModel("מאיר ג'יבלי", "|", "054-5591351"));
        arrayList.add(new TotalModel("רפי לוי", "|", "050-6496994"));
        arrayList.add(new TotalModel("אמנון יששכר", "|", "050-5383682"));
        arrayList.add(new TotalModel("שלמה מלכה", "|", "052-8301356"));
        arrayList.add(new TotalModel("מוטי כהן", "|", "054-4917147"));
        arrayList.add(new TotalModel("איתן כהן", "|", "058-6100120"));
        arrayList.add(new TotalModel("אביעד קמיר", "|", "052-8155575"));
        arrayList.add(new TotalModel("יחזקאל כהן", "|", "050-4998927"));
        arrayList.add(new TotalModel("גדי משה", "|", "050-9915390"));
        arrayList.add(new TotalModel("רפי סיסו", "|", "054-3981499"));
        arrayList.add(new TotalModel("פרג' נסים", "|", "052-9462268"));
        arrayList.add(new TotalModel("גבי מור יוסף", "|", "052-5609489"));
        arrayList.add(new TotalModel("מאיר בן אבו", "|", "050-9916845"));
        arrayList.add(new TotalModel("נדב לוי", "|", "052-6761308"));
        arrayList.add(new TotalModel("נסים שלום", "|", "052-9426607"));
        arrayList.add(new TotalModel("אסף יחזקאל", "|", "050-7512403"));
        arrayList.add(new TotalModel("יוסף כהן", "|", "053-6214322"));
        arrayList.add(new TotalModel("אבי גבסי", "|", "050-8510530"));
        arrayList.add(new TotalModel("צח כהן", "|", "050-2344938"));
        arrayList.add(new TotalModel("כפיר עזו", "|", "053-4441329"));
        arrayList.add(new TotalModel("דוד גינון", "|", "053-7210595"));
        arrayList.add(new TotalModel("שמעון מור", "|", "050-2758925"));
        arrayList.add(new TotalModel("ששון יחזקאל", "|", "050-6667024"));
        arrayList.add(new TotalModel("יוסף ביטון", "|", "050-6218017"));
        arrayList.add(new TotalModel("רמי יעקב", "|", "050-5352594"));
        arrayList.add(new TotalModel("פנחס כהן", "|", "050-6230799"));
        arrayList.add(new TotalModel("אייל בן יהודה", "|", "054-8018505"));
        arrayList.add(new TotalModel("נתנאל שרון", "|", "052-6789088"));
        arrayList.add(new TotalModel("אביאל נהרי", "|", "055-6838347"));
        arrayList.add(new TotalModel("יונתן רחימי", "|", "052-5273942"));
        arrayList.add(new TotalModel("משה אמסלם", "|", "052-5918535"));
        arrayList.add(new TotalModel("יהודה שמואל", "|", "054-4326990"));
        arrayList.add(new TotalModel("שלום דגן", "|", "050-9300086"));
        arrayList.add(new TotalModel("דוד זריהן", "|", "050-9916281"));
        arrayList.add(new TotalModel("יוסף מרדכי", "|", "050-3930719"));
        arrayList.add(new TotalModel("חנן דדון", "|", "050-8107336"));
        adapter = new AdapterWorshipers(arrayList, this);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
