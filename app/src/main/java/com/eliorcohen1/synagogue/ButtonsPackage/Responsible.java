package com.eliorcohen1.synagogue.ButtonsPackage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.eliorcohen1.synagogue.CustomAdapterPackage.CustomAdapterResponsible;
import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.StartPackage.MainActivity;
import com.eliorcohen1.synagogue.StartPackage.TotalModel;

import java.util.ArrayList;

public class Responsible extends AppCompatActivity {

    private ListView listView;
    private Button backResponsible;
    private ArrayList<TotalModel> arrayList;
    private CustomAdapterResponsible customAdapterResponsible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsible);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("בית הכנסת נווה צדק");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backResponsible = findViewById(R.id.backResponsible);

        listView = findViewById(R.id.listResponsible);
        arrayList = new ArrayList<>();
        arrayList.add(new TotalModel("מוטי כהן", "|", "054-4917147"));
        arrayList.add(new TotalModel("שלום נסים", "|", "052-9426607"));
        arrayList.add(new TotalModel("אבי קריאף", "|", "054-4807328"));
        customAdapterResponsible = new CustomAdapterResponsible(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(customAdapterResponsible);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ActivityCompat.checkSelfPermission(Responsible.this.getApplicationContext(), Manifest.permission.CALL_PHONE) ==
                        PackageManager.PERMISSION_GRANTED) {
                    String phone = "tel:" + arrayList.get(position).getPhone();
                    Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(phone));
                    startActivity(i);
                } else {
                    ActivityCompat.requestPermissions(Responsible.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
                }
            }
        });

        backResponsible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Responsible.this, MainActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listView);  // Sets off the menu in Responsible
    }


    // Sets off the menu of list_menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.listre_menu, menu);
    }

    // Options in the list_menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;
        switch (item.getItemId()) {
            case R.id.shareIntent:  // Share the content of movie
                String name = arrayList.get(listPosition).getName();
                String phone = arrayList.get(listPosition).getPhone();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "שם: " + name + "\nטלפון: " + phone);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }
        return super.onContextItemSelected(item);
    }

}
