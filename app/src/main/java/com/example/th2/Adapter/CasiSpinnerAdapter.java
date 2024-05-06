package com.example.th2.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.th2.R;
import com.example.th2.model.Casi;

import java.util.List;

public class CasiSpinnerAdapter extends ArrayAdapter<Casi> {
    Context context;
    List<Casi> listcasi;

    public CasiSpinnerAdapter(@NonNull Context context,List<Casi> listcasi) {
        super(context, R.layout.item_spinner_casi, listcasi);
        this.context = context;
        this.listcasi = listcasi;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(listcasi.get(position).getName());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setText(listcasi.get(position).getName());
        return label;
    }
}
