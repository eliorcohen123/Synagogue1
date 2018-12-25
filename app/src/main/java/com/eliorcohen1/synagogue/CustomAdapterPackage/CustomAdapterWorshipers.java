package com.eliorcohen1.synagogue.CustomAdapterPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eliorcohen1.synagogue.StartPackage.TotalModel;
import com.eliorcohen1.synagogue.R;

import java.util.ArrayList;

public class CustomAdapterWorshipers extends BaseAdapter implements Filterable {

    private ArrayList<TotalModel> mOriginalValues;
    private ArrayList<TotalModel> mDisplayedValues;
    private LayoutInflater inflater;

    public CustomAdapterWorshipers(Context context_, ArrayList<TotalModel> movie_) {
        mOriginalValues = movie_;
        mDisplayedValues = movie_;
        inflater = LayoutInflater.from(context_);
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        LinearLayout myLinear;
        TextView tvName, tvHyphen, tvPrice;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.worshipers_adapter, null);
            holder.myLinear = convertView.findViewById(R.id.myLinear);
            holder.tvName = convertView.findViewById(R.id.name);
            holder.tvHyphen = convertView.findViewById(R.id.hyphen);
            holder.tvPrice = convertView.findViewById(R.id.phone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(mDisplayedValues.get(position).name);
        holder.tvHyphen.setText(mDisplayedValues.get(position).hyphen);
        holder.tvPrice.setText(mDisplayedValues.get(position).phone + "");
        return convertView;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDisplayedValues = (ArrayList<TotalModel>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<TotalModel> FilteredArrList = new ArrayList<TotalModel>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<TotalModel>(mDisplayedValues); // saves the original data in mOriginalValues
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).name;
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new TotalModel(mOriginalValues.get(i).name, mOriginalValues.get(i).hyphen, mOriginalValues.get(i).phone));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

}
