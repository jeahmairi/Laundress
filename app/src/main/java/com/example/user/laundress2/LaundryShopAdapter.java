package com.example.user.laundress2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LaundryShopAdapter extends BaseAdapter {

    private final Context context;
    private final String[] laundryDetailName;
    private final int[] laundryDetailId;

    public LaundryShopAdapter(Context context, String[] laundryDetailName, int[] laundryDetailId) {
        this.context = context;
        this.laundryDetailName = laundryDetailName;
        this.laundryDetailId = laundryDetailId;
    }
    @Override
    public int getCount() {
        return 0;
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
            gridViewAndroid = inflater.inflate(R.layout.laundryshopadapter, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.lsname);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.lspic);
            textViewAndroid.setText(laundryDetailName[position]);
            imageViewAndroid.setImageResource(laundryDetailId[position]);
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}
