package com.eliorcohen1.synagogue.CustomAdapterPackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eliorcohen1.synagogue.StartPackage.TotalModel;
import com.eliorcohen1.synagogue.R;

import java.util.ArrayList;

public class CustomAdapterResponsible extends ArrayAdapter<TotalModel> {

    private Context mContext;  //Context
    private ArrayList<TotalModel> mResponsibleList;  // ArrayList of TotalModel

    public CustomAdapterResponsible(Context context_, int simple_list_item_1, ArrayList<TotalModel> movie_) {
        super(context_, 0, movie_);
        mContext = context_;
        mResponsibleList = movie_;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.responsible_adapter, parent, false);
        }

        TotalModel currentMap = getItem(position);  // Position of items

        if (currentMap != null) {  // If the position of the items not null
            // Put the text in responsibleName
            TextView name1 = listItem.findViewById(R.id.name);
            name1.setText(String.valueOf(currentMap.getName()));

            TextView hyphen1 = listItem.findViewById(R.id.hyphen);
            hyphen1.setText(String.valueOf(currentMap.getHyphen()));

            TextView phone1 = listItem.findViewById(R.id.phone);
            phone1.setText(String.valueOf(currentMap.getPhone()));
        }
        return listItem;
    }

}