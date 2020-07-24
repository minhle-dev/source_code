package com.example.ps10389_lequangminh_assigment.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ps10389_lequangminh_assigment.Model.KhoanThu;
import com.example.ps10389_lequangminh_assigment.R;

import java.util.ArrayList;
import java.util.List;


class KhoanThuViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener {

    public TextView tvKhoanThu, tvSoTien, tvNgay,tvTenKhoanThu;
    public List<KhoanThu> thuList;
    public Context context;
    public CardView cv_Khoan_thu;


    public KhoanThuViewHolder(View itemView, Context context, List<KhoanThu> list) {
        super(itemView);
        this.thuList = list;
        this.context = context;
        tvKhoanThu = itemView.findViewById(R.id.tvKhoanThu);
        tvSoTien = itemView.findViewById(R.id.tvSoTien);
        tvNgay = itemView.findViewById(R.id.tvNgay);
        tvTenKhoanThu = itemView.findViewById(R.id.tvTenKhoanThu);
        cv_Khoan_thu = itemView.findViewById(R.id.mCardView);
        itemView.setOnCreateContextMenuListener(this);

    }






    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //menuInfo is null
        menu.add(Menu.NONE, R.id.context_view,
                Menu.NONE, R.string.xem);
        menu.add(Menu.NONE, R.id.context_edit,
                Menu.NONE, R.string.sua);
        menu.add(Menu.NONE, R.id.context_delete,
                Menu.NONE, R.string.xoa);

    }
}

public class KhoanThuApdater extends RecyclerView.Adapter<KhoanThuViewHolder> {

    private List<KhoanThu> listData;
    private Context context;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public KhoanThuApdater(List<KhoanThu> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public KhoanThuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_khoan_thu, parent, false);
        return new KhoanThuViewHolder(itemView, context, listData);
    }


    @Override
    public void onBindViewHolder(final KhoanThuViewHolder holder, int position) {
        holder.tvKhoanThu.setText("Loại: "+listData.get(position).getLoaiThu());
        holder.tvSoTien.setText("$: "+listData.get(position).getSoTienThu() + " VNĐ");
        holder.tvNgay.setText(listData.get(position).getNgayThu());
        holder.tvTenKhoanThu.setText("Tên: "+listData.get(position).getTenKhoanThu());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }



}
