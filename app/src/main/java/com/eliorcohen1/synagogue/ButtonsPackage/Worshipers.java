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
import android.widget.Toast;

import com.eliorcohen1.synagogue.CustomAdapterPackage.AdapterWorshipers;
import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.StartPackage.TotalModel;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Worshipers extends AppCompatActivity implements View.OnClickListener {

    private Button backWorshipers, btnWriter;
    private ArrayList<TotalModel> arrayList;
    private RecyclerView rv;
    private AdapterWorshipers adapter;
    private android.support.v7.widget.SearchView searchView;
    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worshipers);

        initUI();
        initListeners();
        getReadFirebase();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backWorshipers = findViewById(R.id.backWorshipers);
        btnWriter = findViewById(R.id.btnWrite);

        rv = findViewById(R.id.listWorshipers);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<TotalModel>();

        Firebase.setAndroidContext(this);
        firebase = new Firebase(getString(R.string.API_KEY_Firebase));
    }

    private void initListeners() {
        backWorshipers.setOnClickListener(this);
        btnWriter.setOnClickListener(this);
    }

    private void getReadFirebase() {
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TotalModel totalModel = snapshot.getValue(TotalModel.class);
                        arrayList.add(totalModel);
                    }
                    adapter = new AdapterWorshipers(arrayList, Worshipers.this);
                    adapter.setNames(arrayList);
                    rv.setAdapter(adapter);
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWrite:
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert currentFirebaseUser != null;
                if (currentFirebaseUser.getUid().equals(getString(R.string.API_KEY_Res1))) {
                    Intent intent = new Intent(this, AddWorshipers.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "רק גבאים יכולים להוסיף מתפללים", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.backWorshipers:
                onBackPressed();
                break;
        }
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
