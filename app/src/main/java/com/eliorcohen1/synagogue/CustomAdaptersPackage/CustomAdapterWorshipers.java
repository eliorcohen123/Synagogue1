package com.eliorcohen1.synagogue.CustomAdaptersPackage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.ModelsPackage.TotalModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomAdapterWorshipers extends RecyclerView.Adapter<CustomAdapterWorshipers.ViewHolder> implements Filterable {

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView textName, textPhone;
        private RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.name);
            textPhone = itemView.findViewById(R.id.numPhone);
            relativeLayout = itemView.findViewById(R.id.relative1);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("בחר פעילות");
            MenuItem share = menu.add(Menu.NONE, 1, 1, "שתף פרטי מתפלל");

            share.setOnMenuItemClickListener(onChange);
        }

        private final MenuItem.OnMenuItemClickListener onChange = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                TotalModel current = adapterListFiltered.get(getAdapterPosition());
                if (item.getItemId() == 1) {
                    String name = current.getName();
                    String phone = current.getNumPhone();
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "שם: " + name + "\nטלפון: " + phone);
                    sendIntent.setType("text/plain");
                    mInflater.getContext().startActivity(sendIntent);
                }
                return false;
            }
        };
    }

    private List<TotalModel> list_data;
    private Context context;
    private final LayoutInflater mInflater;
    private List<TotalModel> adapterListFiltered;

    public CustomAdapterWorshipers(List<TotalModel> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
        this.adapterListFiltered = list_data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_worshipers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final TotalModel listData = adapterListFiltered.get(position);
        holder.textName.setText(listData.getName());
        holder.textPhone.setText(listData.getNumPhone());
        holder.relativeLayout.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                String phone = "tel:" + listData.getNumPhone();
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(phone));
                context.startActivity(i);
            } else {
                ActivityCompat.requestPermissions((Activity) mInflater.getContext(), new String[]{Manifest.permission.CALL_PHONE}, 0);
            }
        });

        setFadeAnimation(holder.itemView);
    }

    public void setNames(List<TotalModel> names) {
        adapterListFiltered = names;
        Collections.sort(adapterListFiltered, (obj1, obj2) -> obj1.getName().compareToIgnoreCase(obj2.getName()));
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return adapterListFiltered.size();
    }

    public TotalModel getPlaceAtPosition(int position) {
        return adapterListFiltered.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    adapterListFiltered = list_data;
                } else {
                    List<TotalModel> filteredList = new ArrayList<>();
                    for (TotalModel row : list_data) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    adapterListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = adapterListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                adapterListFiltered = (ArrayList<TotalModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1500);
        view.startAnimation(anim);
    }

}
