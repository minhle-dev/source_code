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
import com.example.ps10389_lequangminh_mob201_assignment.database.SinhVienDAO;
import com.example.ps10389_lequangminh_mob201_assignment.model.LopHoc;
import com.example.ps10389_lequangminh_mob201_assignment.model.SinhVien;

import java.util.List;

public class AdapterLopHoc extends RecyclerView.Adapter<AdapterLopHoc.ViewHolder>{
    public Context context;
    public List<LopHoc> arrLopHoc;
    public LayoutInflater inflater;
    public LopHocDAO lopHocDAO;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public AdapterLopHoc(Context context, List<LopHoc> arrLopHoc) {
        this.context = context;
        this.arrLopHoc = arrLopHoc;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    //tao view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_lop_hoc,null);
        return new ViewHolder(view);
    }
    //lay du lieu
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        LopHoc lopHoc = arrLopHoc.get(position);
        holder.tvMaLop.setText("Mã lớp:   "+lopHoc.getMaLop());
        holder.tvTenLop.setText("Tên Lớp: "+lopHoc.getTenLop());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LopHocDAO  lopHocDAO = new LopHocDAO(context);
                lopHocDAO.XoaLop(arrLopHoc.get(position).getMaLop());
                arrLopHoc.remove(position);
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
        return arrLopHoc.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView ivIcon;
        TextView tvMaLop;
        TextView tvTenLop;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivIcon = itemView.findViewById(R.id.ivIcon);
            this.tvMaLop = itemView.findViewById(R.id.tvMaLop);
            this.tvTenLop = itemView.findViewById(R.id.tvTenlop);
            this.ivDelete = itemView.findViewById(R.id.ivDelete);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null
            menu.add(Menu.NONE, R.id.context_edit_lop,
                    Menu.NONE, "Sửa tên lớp");

        }
    }


}
