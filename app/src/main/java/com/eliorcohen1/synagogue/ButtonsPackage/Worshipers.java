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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.eliorcohen1.synagogue.CustomAdapterPackage.CustomAdapterWorshipers;
import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.StartPackage.MainActivity;
import com.eliorcohen1.synagogue.StartPackage.TotalModel;

import java.util.ArrayList;

public class Worshipers extends AppCompatActivity {

    private ListView listView;
    private Button backWorshipers;
    private ArrayList<TotalModel> arrayList;
    private EditText etSearch;
    private CustomAdapterWorshipers customAdapterWorshipers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worhipers);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("בית הכנסת נווה צדק");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        backWorshipers = findViewById(R.id.backWorshipers);

        listView = findViewById(R.id.listWorshipers);
        etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                try {
                    customAdapterWorshipers.getFilter().filter(s.toString());
                } catch (Exception e) {

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                etSearch.setHint("חפש מתפלל ברשימה...");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ActivityCompat.checkSelfPermission(Worshipers.this.getApplicationContext(), Manifest.permission.CALL_PHONE) ==
                        PackageManager.PERMISSION_GRANTED) {
                    String phone = "tel:" + arrayList.get(position).getPhone();
                    Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(phone));
                    startActivity(i);
                } else {
                    ActivityCompat.requestPermissions(Worshipers.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
                }
            }
        });

        backWorshipers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Worshipers.this, MainActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listView);  // Sets off the menu in Worshipers
    }


    @Override
    protected void onResume() {
        super.onResume();
        arrayList = new ArrayList<TotalModel>();
        arrayList.add(new TotalModel("צבי נעמת", "|", "052-8345360"));
        arrayList.add(new TotalModel("אברהם כהנא", "|", "054-7684689"));
        arrayList.add(new TotalModel("נסים ביטון", "|", "050-2441207"));
        arrayList.add(new TotalModel("מאיר שניאור", "|", "050-8512125"));
        arrayList.add(new TotalModel("יוני עוקשי", "|", "052-6678063"));
        arrayList.add(new TotalModel("אבי קריאף", "|", "054-4807328"));
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
        arrayList.add(new TotalModel("אביאל נהרי", "|", "055-6838347"));
        arrayList.add(new TotalModel("כפיר עזו", "|", "053-4441329"));
        arrayList.add(new TotalModel("דוד גינון", "|", "053-7210595"));
        arrayList.add(new TotalModel("שמעון מור", "|", "050-2758925"));
        arrayList.add(new TotalModel("ששון יחזקאל", "|", "050-6667024"));
        arrayList.add(new TotalModel("יוסף ביטון", "|", "050-6218017"));
        arrayList.add(new TotalModel("רמי יעקב", "|", "050-5352594"));
        arrayList.add(new TotalModel("פנחס כהן", "|", "050-6230799"));
        arrayList.add(new TotalModel("אייל בן יהודה", "|", "054-8018505"));
        arrayList.add(new TotalModel("נתנאל שרון", "|", "052-6789088"));
        arrayList.add(new TotalModel("יונתן רחימי", "|", "052-5273942"));
        arrayList.add(new TotalModel("משה אמסלם", "|", "052-5918535"));
        arrayList.add(new TotalModel("יהודה שמואל", "|", "054-4326990"));
        arrayList.add(new TotalModel("שלום דגן", "|", "050-9300086"));
        arrayList.add(new TotalModel("דוד זריהן", "|", "050-9916281"));
        arrayList.add(new TotalModel("יוסף מרדכי", "|", "050-3930719"));
        arrayList.add(new TotalModel("חנן דדון", "|", "050-8107336"));
        customAdapterWorshipers = new CustomAdapterWorshipers(Worshipers.this, arrayList);
        listView.setAdapter(customAdapterWorshipers);
    }

    // Sets off the menu of list_menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu, menu);
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
