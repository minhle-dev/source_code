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
import com.example.ps10389_lequangminh_mob201_assignment.database.LopHocDAO;
import com.example.ps10389_lequangminh_mob201_assignment.database.MonHocDAO;
import com.example.ps10389_lequangminh_mob201_assignment.model.MonHoc;

import java.util.List;

public class AdapterMonHoc extends RecyclerView.Adapter<AdapterMonHoc.ViewHolder> {
    public Context context;
    public List<MonHoc> arrMonHoc;
    public LayoutInflater inflater;
    public LopHocDAO lopHocDAO;
    public MonHocDAO monHocDAO;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AdapterMonHoc(Context context, List<MonHoc> arrMonHoc) {
        this.context = context;
        this.arrMonHoc = arrMonHoc;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    //tao view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_lich_hoc, null);
        return new ViewHolder(view);
    }

    //lay du lieu
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        MonHoc monHoc = arrMonHoc.get(position);
        holder.tvMaLop.setText("Mã lớp:   " + monHoc.getMaLop());
        holder.tvIDMon.setText("ID:   " + monHoc.getMaMonHoc());
        holder.tvTenMonHoc.setText("Tên môn: "+monHoc.getTenMonHoc());
        holder.tvLichHoc.setText("Lịch học: "+monHoc.getLichHoc());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monHocDAO = new MonHocDAO(context);
                monHocDAO.XoaMonHoc(arrMonHoc.get(position).getMaMonHoc());
                arrMonHoc.remove(position);
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
        return arrMonHoc.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView ivIcon;
        TextView tvMaLop;
        TextView tvIDMon;
        TextView tvTenMonHoc;
        TextView tvLichHoc;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivIcon = itemView.findViewById(R.id.ivIcon);
            this.tvMaLop = itemView.findViewById(R.id.tvMaLop);
            this.tvIDMon = itemView.findViewById(R.id.tvIDMon);
            this.tvLichHoc = itemView.findViewById(R.id.tvLichHoc);
            this.ivDelete = itemView.findViewById(R.id.ivDelete);
            this.tvTenMonHoc = itemView.findViewById(R.id.tvTenMonHoc);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null

            menu.add(Menu.NONE, R.id.context_edit_mon,
                    Menu.NONE, "Sửa Môn Học");
          ;
        }
    }


}
