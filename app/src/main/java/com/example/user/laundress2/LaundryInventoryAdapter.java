package com.example.user.laundress2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LaundryInventoryAdapter extends BaseAdapter {
    private final Context context;
    LaundryInventoryAdapter.ItemHolder itemHolder;
    ArrayList<AddLaundryDetailList> addLaundryDetailLists;
    AddLaundryDetailList addLaundryDetailList;
    ArrayList<String> list;

    public LaundryInventoryAdapter(Context context, ArrayList<AddLaundryDetailList> addLaundryDetailLists) {
        this.context = context;
        this.addLaundryDetailLists = addLaundryDetailLists;
    }

    @Override
    public int getCount() {
        return addLaundryDetailLists.size();
    }

    @Override
    public Object getItem(int position) {
        return addLaundryDetailLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemHolder = new LaundryInventoryAdapter.ItemHolder();
        convertView = layoutInflater.inflate(R.layout.inventory_adapter, parent, false);
        itemHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        itemHolder.itemdet = (TextView) convertView.findViewById(R.id.itemdet);
        itemHolder.photo = (ImageView) convertView.findViewById(R.id.photo);
        itemHolder.itemdet.setText(addLaundryDetailLists.get(position).getAllNameDet());
        itemHolder.noofpieces = convertView.findViewById(R.id.noofpieces);
        //itemHolder.noofpieces1 = convertView.findViewById(R.id.noofpieces1);

        Picasso.get().load(addLaundryDetailLists.get(position).getPhoto()).into(itemHolder.photo);

        /*if(addLaundryDetailLists.get(position).getItemNoofPieces() == 1){
            itemHolder.noofpieces.setVisibility(View.GONE);
            //itemHolder.noofpieces1.setVisibility(View.VISIBLE);
            itemHolder.noofpieces1.setText(""+addLaundryDetailLists.get(position).getItemNoofPieces());
        }*/
        if(addLaundryDetailLists.get(position).getItemNoofPieces() >= 1) {
            list = new ArrayList<String>();
            for (int i = 1; i <= addLaundryDetailLists.get(position).getItemNoofPieces(); i++) {
                if (list.indexOf(i) < 0) {
                    list.add(String.valueOf(i));
                } else {
                    list.remove(String.valueOf(i));
                }
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            itemHolder.noofpieces.setAdapter(dataAdapter);
        }



        itemHolder.checkBox.setTag(addLaundryDetailLists.get(position));
        itemHolder.checkBox.setOnClickListener( new View.OnClickListener()
        {
            public void onClick(View v)
            {
                CheckBox cb = (CheckBox) v;
                AddLaundryDetailList addLaundryDetailList = (AddLaundryDetailList) cb.getTag();

               /* Toast.makeText(context, "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(),
                        Toast.LENGTH_LONG).show();
*/
                addLaundryDetailList.setSelected(cb.isChecked());
                addLaundryDetailList.setSelectitemNoofPieces(itemHolder.nop);

            }
        });

        itemHolder.noofpieces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int getPosition = (Integer) parent.getTag();
                itemHolder.nop = Integer.parseInt(parent.getItemAtPosition(position).toString());
                // addLaundryDetailList.setSelectitemNoofPieces(itemclr);
                //Toast.makeText(context, "value " + itemHolder.nop, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        itemHolder.noofpieces.setTag(position);
        itemHolder.checkBox.setChecked(addLaundryDetailLists.get(position).isSelected());
        return convertView;
    }

    private class ItemHolder {
        TextView itemdet, noofpieces1;
        CheckBox checkBox;
        Spinner noofpieces;
        ImageView photo;
        int nop;
    }
}
