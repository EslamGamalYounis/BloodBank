package com.example.mybloodyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mybloodyapp.R;
import com.example.mybloodyapp.data.model.city.CityData;

import java.util.ArrayList;
import java.util.List;

public class EmptySpinnerAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflter;
    public List<CityData> generalResponseDataList = new ArrayList<>();
    public int selectedId = 0;

    public EmptySpinnerAdapter(Context applicationContext) {
        this.context = applicationContext;
       // this.generalResponseDataList = generalResponseDataList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<CityData> generalResponseDataList, String hint) {
        this.generalResponseDataList =new ArrayList<>();
        this.generalResponseDataList.add(new CityData(0, hint));
        this.generalResponseDataList.addAll(generalResponseDataList);
    }

    @Override
    public int getCount() {
        //        return generalResponseDataList.size();
        return generalResponseDataList.size();
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
        convertView = inflter.inflate(R.layout.custom_spinner_items, null);

        TextView names = (TextView) convertView.findViewById(R.id.text_spinner_item);

        names.setText(generalResponseDataList.get(position).getName());
        selectedId = generalResponseDataList.get(position).getId();

        return convertView;
    }
}
