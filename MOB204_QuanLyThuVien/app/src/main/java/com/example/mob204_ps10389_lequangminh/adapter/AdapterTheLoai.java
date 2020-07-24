package com.example.mob204_ps10389_lequangminh.adapter;

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

import com.example.mob204_ps10389_lequangminh.R;
import com.example.mob204_ps10389_lequangminh.dao.NguoiDungDAO;
import com.example.mob204_ps10389_lequangminh.dao.TheLoaiDAO;
import com.example.mob204_ps10389_lequangminh.model.NguoiDung;
import com.example.mob204_ps10389_lequangminh.model.TheLoai;

import java.util.List;

public class AdapterTheLoai extends RecyclerView.Adapter<AdapterTheLoai.ViewHolder>{
    public Context context;
    public List<TheLoai> arrTheLoai;
    public LayoutInflater inflater;
    public TheLoaiDAO theLoaiDAO;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public AdapterTheLoai(Context context, List<TheLoai> arrTheLoai) {
        this.context = context;
        this.arrTheLoai = arrTheLoai;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    //tao view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_the_loai,null);
        return new ViewHolder(view);
    }
    //lay du lieu
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        TheLoai theLoai = arrTheLoai.get(position);
        holder.tvMaTheLoai.setText("Mã thể loại:  "+theLoai.getMaTheLoai());
        holder.tvTenTheLoai.setText("Tên thể loại: "+theLoai.getTenTheLoai());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TheLoaiDAO  theLoaiDAO= new TheLoaiDAO(context);
                theLoaiDAO.deleteTheLoaiByID(arrTheLoai.get(position).getMaTheLoai());
                arrTheLoai.remove(position);
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
        return arrTheLoai.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView ivIcon;
        TextView tvTenTheLoai;
        TextView tvMaTheLoai;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivIcon = itemView.findViewById(R.id.ivIcon);
            this.tvTenTheLoai = itemView.findViewById(R.id.tvTenTheLoai);
            this.tvMaTheLoai = itemView.findViewById(R.id.tvMaTheLoai);
            this.ivDelete = itemView.findViewById(R.id.ivDelete);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null
            menu.add(Menu.NONE, R.id.context_view,
                    Menu.NONE, "Xem thông tin");
            menu.add(Menu.NONE, R.id.context_edit,
                    Menu.NONE, "Sửa thông tin");
            menu.add(Menu.NONE, R.id.context_delete,
                    Menu.NONE, "Xóa");
        }
    }


}
