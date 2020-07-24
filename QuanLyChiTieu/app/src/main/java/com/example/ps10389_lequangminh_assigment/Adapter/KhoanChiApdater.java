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

import com.example.ps10389_lequangminh_assigment.Model.KhoanChi;
import com.example.ps10389_lequangminh_assigment.Model.KhoanThu;
import com.example.ps10389_lequangminh_assigment.R;

import java.util.ArrayList;
import java.util.List;


class KhoanChiViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener {

    public TextView tvKhoanChi, tvSoTienChi, tvNgayChi,tvTenKhoanChi;
    public List<KhoanChi> chiList;
    public Context context;
    public CardView cv_Khoan_chi;


    public KhoanChiViewHolder(View itemView, Context context, List<KhoanChi> list) {
        super(itemView);
        this.chiList = list;
        this.context = context;
        tvKhoanChi = itemView.findViewById(R.id.tvKhoanChi);
        tvSoTienChi = itemView.findViewById(R.id.tvSoTienChi);
        tvNgayChi = itemView.findViewById(R.id.tvNgayChi);
        tvTenKhoanChi = itemView.findViewById(R.id.tvTenKhoanChi);
        cv_Khoan_chi = itemView.findViewById(R.id.mCardViewChi);
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

public class KhoanChiApdater extends RecyclerView.Adapter<KhoanChiViewHolder> {

    private List<KhoanChi> listData = new ArrayList<>();
    private Context context;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public KhoanChiApdater(List<KhoanChi> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public KhoanChiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_khoan_chi, parent, false);
        return new KhoanChiViewHolder(itemView, context, listData);
    }


    @Override
    public void onBindViewHolder(final KhoanChiViewHolder holder, int position) {
        holder.tvKhoanChi.setText("Loại: "+listData.get(position).getLoaiChi());
        holder.tvSoTienChi.setText("$: "+listData.get(position).getSoTienChi() + " VNĐ");
        holder.tvNgayChi.setText(listData.get(position).getNgayChi());
        holder.tvTenKhoanChi.setText("Tên: "+listData.get(position).getTenKhoanChi());
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
