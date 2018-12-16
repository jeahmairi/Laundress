package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LaundryShopAdapter extends BaseAdapter {
    private final Context context;
    private final String[] laundryDetailName;
    private final String[] lsLocation;
    private final String[] lsmeter;
    private final int[] laundryDetailId;

    public LaundryShopAdapter(Context context, String[] laundryDetailName,String[] lsLocation, String[] lsmeter, int[] laundryDetailId) {
        this.context = context;
        this.laundryDetailName = laundryDetailName;
        this.lsLocation = lsLocation;
        this.lsmeter = lsmeter;
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
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(context);
            gridViewAndroid = inflater.inflate(R.layout.laundryshopadapter, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.lsname);
            TextView location = (TextView) gridViewAndroid.findViewById(R.id.lslocation);
            TextView meter = (TextView) gridViewAndroid.findViewById(R.id.lsmeters);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.lspic);
            Button btnchoose = (Button) gridViewAndroid.findViewById(R.id.btnchoose);
            textViewAndroid.setText(laundryDetailName[position]);
            location.setText(lsLocation[position]);
            meter.setText(lsmeter[position]);
            imageViewAndroid.setImageResource(laundryDetailId[position]);
            btnchoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChooseLaundryShop.class);
                    context.startActivity(intent);
                }
            });
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}
