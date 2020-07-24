package com.example.mob204_ps10389_lequangminh.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob204_ps10389_lequangminh.R;
import com.example.mob204_ps10389_lequangminh.model.TheLoai;

import java.util.List;


public class AdapterSpinnerTheLoai extends ArrayAdapter<TheLoai> {

    public List<TheLoai> listData;
    public Context context;
    public int resource;


    public AdapterSpinnerTheLoai(@NonNull Context context, int resource, @NonNull List<TheLoai> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listData = objects;
    }

    class viewHolder {
        TextView tvTheLoaiSpinner;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        viewHolder view;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner_theloai, parent, false);
            view = new viewHolder();
            view.tvTheLoaiSpinner = convertView.findViewById(R.id.tvTheLoaiSpiner);
            convertView.setTag(view);
        } else {
            view = (viewHolder) convertView.getTag();
        }
        view.tvTheLoaiSpinner.setText(listData.get(position).getMaTheLoai() + " | " + listData.get(position).getTenTheLoai());
        return convertView;
    }
}
