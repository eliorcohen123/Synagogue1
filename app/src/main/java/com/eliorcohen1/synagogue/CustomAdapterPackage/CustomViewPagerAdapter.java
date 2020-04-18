package com.eliorcohen1.synagogue.CustomAdapterPackage;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eliorcohen1.synagogue.R;
import com.eliorcohen1.synagogue.ModelsPackage.TotalModel;

import java.util.ArrayList;

public class CustomViewPagerAdapter extends PagerAdapter {

    private ArrayList<TotalModel> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;

    public CustomViewPagerAdapter(Context context, ArrayList<TotalModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.custom_view_pager, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.imageView1);

        imageView.setImageResource(imageModelArrayList.get(position).getImage_drawable());

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
