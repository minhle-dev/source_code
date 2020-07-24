package com.example.mob204_ps10389_lequangminh.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mob204_ps10389_lequangminh.R;
import com.example.mob204_ps10389_lequangminh.dao.HoaDonChiTietDAO;
import com.example.mob204_ps10389_lequangminh.model.HoaDonChiTiet;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends BaseAdapter {
    List<HoaDonChiTiet> arrHoaDonChiTiet;
    public Activity context;
    public LayoutInflater inflater;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    public CartAdapter(Activity context, List<HoaDonChiTiet> arrayHoaDonChiTiet) {
        super();
        this.context = context;
        this.arrHoaDonChiTiet = arrayHoaDonChiTiet;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
    }

    @Override
    public int getCount() {
        return arrHoaDonChiTiet.size();
    }

    @Override
    public Object getItem(int position) {
        return arrHoaDonChiTiet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_cart, null);
            holder.txtMaSach = convertView.findViewById(R.id.tvMaSach);
            holder.txtTenSach = convertView.findViewById(R.id.tvTenSach);
            holder.txtSoLuong = convertView.findViewById(R.id.tvSoLuong);
            holder.txtGiaBia = convertView.findViewById(R.id.tvGiaBia);
            holder.txtThanhTien = convertView.findViewById(R.id.tvThanhTien);
            holder.imgDelete = convertView.findViewById(R.id.ivDelete);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hoaDonChiTietDAO.deleteHoaDonChiTietByID(String.valueOf(arrHoaDonChiTiet.get(position).getMaHDCT()));
                    arrHoaDonChiTiet.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        HoaDonChiTiet _entry =  arrHoaDonChiTiet.get(position);
        holder.txtMaSach.setText("Mã sách: " + _entry.getSach().getMaSach());
        holder.txtTenSach.setText("Tên sách: "+_entry.getSach().getTenSach());
        holder.txtSoLuong.setText("Số lượng: " + _entry.getSoLuongMua());
        holder.txtGiaBia.setText("Giá bìa: " +NumberFormat.getNumberInstance(Locale.US).format(_entry.getSach().getGiaBia())  + " vnd");
        holder.txtThanhTien.setText("Thành tiền: " + NumberFormat.getNumberInstance(Locale.US).format(_entry.getSoLuongMua() * _entry.getSach().getGiaBia())  + " vnd");
        return convertView;
    }

    public static class ViewHolder {
        TextView txtMaSach;
        TextView txtTenSach;
        TextView txtSoLuong;
        TextView txtGiaBia;
        TextView txtThanhTien;
        ImageView imgDelete;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<HoaDonChiTiet> items) {
        this.arrHoaDonChiTiet = items;
        notifyDataSetChanged();
    }
}
