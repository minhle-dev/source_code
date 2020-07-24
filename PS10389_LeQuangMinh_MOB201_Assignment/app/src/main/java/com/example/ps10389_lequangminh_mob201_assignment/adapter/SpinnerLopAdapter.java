package com.example.ps10389_lequangminh_mob201_assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ps10389_lequangminh_mob201_assignment.R;
import com.example.ps10389_lequangminh_mob201_assignment.model.LopHoc;

import java.util.ArrayList;

public class SpinnerLopAdapter extends BaseAdapter {
    Context context;
    ArrayList<LopHoc> dulieu;
    public SpinnerLopAdapter(Context context, ArrayList<LopHoc> dulieu){
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
        if(convertView==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.list_spinner_layout,null);
        }

        //ánh xạ
        TextView tvspinner =convertView.findViewById(R.id.tvSpinerMaLop);


        //gắn dữ liệu vào
        LopHoc lop =dulieu.get(position);
        tvspinner.setText(lop.getMaLop() + " | "+lop.getTenLop());




        return convertView;
    }
    }

