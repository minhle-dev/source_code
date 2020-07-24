package com.example.ps10389_lequangminh_mob201_assignment.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.ps10389_lequangminh_mob201_assignment.R;
import com.example.ps10389_lequangminh_mob201_assignment.database.SinhVienDAO;
import com.example.ps10389_lequangminh_mob201_assignment.model.SinhVien;

import java.util.List;

public class AdapterSinhVien extends RecyclerView.Adapter<AdapterSinhVien.ViewHolder>{
    public Context context;
    public List<SinhVien> arrSinhVien;
    public LayoutInflater inflater;
    public SinhVienDAO sinhVienDAO;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public AdapterSinhVien(Context context, List<SinhVien> arrSinhVien) {
        this.context = context;
        this.arrSinhVien = arrSinhVien;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    //tao view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_sinh_vien,null);
        return new ViewHolder(view);
    }
    //lay du lieu
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        SinhVien sinhVien = arrSinhVien.get(position);
        holder.tvMaSv.setText("Mã SV: "+sinhVien.getIdSinhVien());
        holder.tvTenSinhVien.setText("Tên:  "+sinhVien.getTenSinhVien());
        holder.tvNgaySinh.setText("Ngày sinh: "+sinhVien.getNgaySinh());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SinhVienDAO  sinhVienDAO = new SinhVienDAO(context);
                sinhVienDAO.XoaSinhVien(arrSinhVien.get(position).getIdSinhVien());
                arrSinhVien.remove(position);
                notifyDataSetChanged();
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }
    //lay ve so luong ite,
    @Override
    public int getItemCount() {
        return arrSinhVien.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView ivIcon;
        TextView tvTenSinhVien;
        TextView tvNgaySinh;
        ImageView ivDelete;
        TextView tvMaSv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivIcon = itemView.findViewById(R.id.ivIcon);
            this.tvTenSinhVien = itemView.findViewById(R.id.tvTenSinhVien);
            this.tvMaSv = itemView.findViewById(R.id.tvMaSinhVien);
            this.tvNgaySinh = itemView.findViewById(R.id.tvNgaySinh);
            this.ivDelete = itemView.findViewById(R.id.ivDelete);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null

            menu.add(Menu.NONE, R.id.context_edit,
                    Menu.NONE, "Sửa thông tin");
        }
    }
    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }





}
