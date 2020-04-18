package com.eliorcohen1.synagogue.ClassesPackage;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eliorcohen1.synagogue.CustomAdapterPackage.AdapterWorshipers;
import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.Models.TotalModel;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

public class Worshipers extends AppCompatActivity implements View.OnClickListener {

    private Button backWorshipers, btnWrite;
    private ArrayList<TotalModel> arrayList;
    private RecyclerView rv;
    private AdapterWorshipers adapter;
    private SearchView searchView;
    private Paint p;
    private Firebase firebase;
    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worshipers);

        initUI();
        initListeners();
        initRecyclerView();
        getReadFirebase();
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backWorshipers = findViewById(R.id.backWorshipers);
        btnWrite = findViewById(R.id.btnWrite);
        rv = findViewById(R.id.listWorshipers);

        arrayList = new ArrayList<TotalModel>();
        p = new Paint();

        btnWrite.setVisibility(View.GONE);

        Firebase.setAndroidContext(this);
        firebase = new Firebase(getString(R.string.API_KEY_Firebase));

        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        assert currentFirebaseUser != null;
        if (Objects.requireNonNull(currentFirebaseUser.getEmail()).equals(getString(R.string.API_KEY_Email1)) ||
                currentFirebaseUser.getEmail().equals(getString(R.string.API_KEY_Email2)) ||
                currentFirebaseUser.getEmail().equals(getString(R.string.API_KEY_Email3))) {
            enableSwipe();

            btnWrite.setVisibility(View.VISIBLE);
        }
    }

    private void initListeners() {
        backWorshipers.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
    }

    private void initRecyclerView() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
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

    private void enableSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                TotalModel totalModel = adapter.getPlaceAtPosition(position);

                if (direction == ItemTouchHelper.LEFT) {
                    Toast.makeText(Worshipers.this, "עורך: " + totalModel.getName(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Worshipers.this, EditWorshipers.class);
                    intent.putExtra(getString(R.string.worshipers_name), totalModel.getName());
                    intent.putExtra(getString(R.string.worshipers_numPhone), totalModel.getNumPhone());
                    startActivity(intent);
                } else {
                    Toast.makeText(Worshipers.this, "מוחק: " + totalModel.getName(), Toast.LENGTH_LONG).show();

                    firebase.getRef().child(totalModel.getNumPhone()).removeValue();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX < 0) {
                        p.setColor(Color.parseColor("#D80000"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.editicon);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.deletedicon);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWrite:
                Intent intent = new Intent(this, AddWorshipers.class);
                startActivity(intent);
                break;
            case R.id.backWorshipers:
                Intent intentMain = new Intent(this, MainActivity.class);
                startActivity(intentMain);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                try {
                    adapter.getFilter().filter(query);
                } catch (Exception e) {

                }
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
