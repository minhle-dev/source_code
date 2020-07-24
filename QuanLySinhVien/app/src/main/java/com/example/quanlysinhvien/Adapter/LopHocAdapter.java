package com.example.quanlysinhvien.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlysinhvien.Model.LopHoc;
import com.example.quanlysinhvien.R;

import java.util.ArrayList;

public class LopHocAdapter extends BaseAdapter {
    Context context;
    ArrayList<LopHoc> dulieu;

    public LopHocAdapter(Context context, ArrayList<LopHoc> dulieu) {
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
            convertView=layoutInflater.inflate(R.layout.listviewaddlop_layout,null);
        }

        //ánh xạ
        TextView tvSTT =convertView.findViewById(R.id.tvSTT);
        TextView tvMaLop =convertView.findViewById(R.id.tvMaLop);
        TextView tvTenLop=convertView.findViewById(R.id.tvTenLop);

        //gắn dữ liệu vào

        LopHoc w=dulieu.get(position);
        tvSTT.setText(position+1+"");
        tvMaLop.setText(w.getMaLop());
        tvTenLop.setText(w.getTenLop());
        return convertView;
    }
    }

