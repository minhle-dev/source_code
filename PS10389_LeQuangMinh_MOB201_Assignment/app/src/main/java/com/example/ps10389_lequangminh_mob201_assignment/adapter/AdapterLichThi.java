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
import com.example.ps10389_lequangminh_mob201_assignment.database.LichThiDAO;
import com.example.ps10389_lequangminh_mob201_assignment.database.LopHocDAO;
import com.example.ps10389_lequangminh_mob201_assignment.model.LichThi;
import com.example.ps10389_lequangminh_mob201_assignment.model.LopHoc;

import java.util.List;

public class AdapterLichThi extends RecyclerView.Adapter<AdapterLichThi.ViewHolder> {
    public Context context;
    public List<LichThi> arrLichThi;
    public LayoutInflater inflater;
    public LopHocDAO lopHocDAO;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AdapterLichThi(Context context, List<LichThi> arrLichThi) {
        this.context = context;
        this.arrLichThi = arrLichThi;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    //tao view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_lich_thi, null);
        return new ViewHolder(view);
    }

    //lay du lieu
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        LichThi lichThi = arrLichThi.get(position);
        holder.tvTenMonHoc.setText("Tên môn: " + lichThi.getTenMonHoc());
        holder.tvID.setText("ID:  " + lichThi.getIdLichThi());
        holder.tvLichThi.setText("Lịch Thi: " + lichThi.getLichThi());
        holder.tvGhiChu.setText("Ghi chú: " + lichThi.getGhiChu());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LichThiDAO lichThiDAO = new LichThiDAO(context);
                lichThiDAO.XoaLichThi(arrLichThi.get(position).getIdLichThi());
                arrLichThi.remove(position);
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
        return arrLichThi.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView ivIcon;
        TextView tvID;
        TextView tvTenMonHoc;
        TextView tvLichThi;
        TextView tvGhiChu;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivIcon = itemView.findViewById(R.id.ivIcon);
            this.tvTenMonHoc = itemView.findViewById(R.id.tvTenMonHoc);
            this.tvLichThi = itemView.findViewById(R.id.tvLichThi);
            this.tvID = itemView.findViewById(R.id.tvID);
            this.tvGhiChu = itemView.findViewById(R.id.tvGhiChu);
            this.ivDelete = itemView.findViewById(R.id.ivDelete);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null

            menu.add(Menu.NONE, R.id.context_edit_lich,
                    Menu.NONE, "Sửa thông tin");

        }
    }


}
