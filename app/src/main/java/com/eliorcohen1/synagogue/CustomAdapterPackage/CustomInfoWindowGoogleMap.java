package com.eliorcohen1.synagogue.CustomAdapterPackage;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.Models.TotalModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx) {
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.map_custom_infowindow, null);

        TextView name = view.findViewById(R.id.nameInfo);
        ImageView img = view.findViewById(R.id.pic);
        TextView distance = view.findViewById(R.id.distanceInfo);

        name.setText(marker.getTitle());

        TotalModel infoWindowData = (TotalModel) marker.getTag();

        assert infoWindowData != null;
        int imageId = context.getResources().getIdentifier(infoWindowData.getImageView().toLowerCase(),
                "drawable", context.getPackageName());
        img.setImageResource(imageId);
        distance.setText(infoWindowData.getDistance());

        return view;
    }

}
