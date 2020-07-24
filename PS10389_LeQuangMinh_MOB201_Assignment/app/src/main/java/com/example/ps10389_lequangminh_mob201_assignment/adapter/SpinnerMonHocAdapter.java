package com.example.ps10389_lequangminh_mob201_assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ps10389_lequangminh_mob201_assignment.R;
import com.example.ps10389_lequangminh_mob201_assignment.model.LopHoc;
import com.example.ps10389_lequangminh_mob201_assignment.model.MonHoc;

import java.util.ArrayList;

public class SpinnerMonHocAdapter extends BaseAdapter {
    Context context;
    ArrayList<MonHoc> dulieu;

    public SpinnerMonHocAdapter(Context context, ArrayList<MonHoc> dulieu) {
        this.context = context;
        this.dulieu = dulieu;
    }

    @Override
    public int getCount() {
        return dulieu.size();
    }

    @Override
    public Object getItem(int position) {
        return dulieu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_spinner_mon_hoc, null);
        }

        //ánh xạ
        TextView tvspinner = convertView.findViewById(R.id.tvSpinerMonHoc);


        //gắn dữ liệu vào
        MonHoc mon = dulieu.get(position);
        tvspinner.setText(mon.getTenMonHoc());


        return convertView;
    }
}

