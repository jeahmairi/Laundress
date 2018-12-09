package com.example.user.laundress2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LaundryDetailsAdapter extends BaseAdapter {
    private final Context context;
    private final String[] laundryDetailName;
    private final int[] laundryDetailId;

    public LaundryDetailsAdapter(Context context, String[] laundryDetailName, int[] laundryDetailId) {
        this.context = context;
        this.laundryDetailName = laundryDetailName;
        this.laundryDetailId = laundryDetailId;
    }

    @Override
    public int getCount() {
        return laundryDetailName.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(context);
            gridViewAndroid = inflater.inflate(R.layout.clntlaundrydet_layout, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(laundryDetailName[position]);
            imageViewAndroid.setImageResource(laundryDetailId[position]);
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}
