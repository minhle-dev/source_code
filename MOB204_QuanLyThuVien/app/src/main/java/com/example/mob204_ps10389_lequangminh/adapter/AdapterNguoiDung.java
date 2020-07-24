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
import com.example.mob204_ps10389_lequangminh.model.NguoiDung;


import java.util.List;

public class AdapterNguoiDung extends RecyclerView.Adapter<AdapterNguoiDung.ViewHolder>{
    public Context context;
    public List<NguoiDung> arrNguoiDung;
    public LayoutInflater inflater;
    public NguoiDungDAO nguoiDungDAO;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public AdapterNguoiDung(Context context,List<NguoiDung> arrNguoiDung) {
        this.context = context;
        this.arrNguoiDung = arrNguoiDung;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    //tao view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_nguoi_dung,null);
        return new ViewHolder(view);
    }
    //lay du lieu
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        NguoiDung nguoiDung = arrNguoiDung.get(position);
        holder.tvName.setText("Username: "+nguoiDung.getUserName());
        holder.tvPhone.setText("Phone:    "+nguoiDung.getPhone());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NguoiDungDAO  nguoiDungDAO = new NguoiDungDAO(context);
                nguoiDungDAO.deleteNguoiDungByID(arrNguoiDung.get(position).getUserName());
                arrNguoiDung.remove(position);
                notifyDataSetChanged();
            }
        });
        if (position % 4 ==0) {
            holder.ivIcon.setImageResource(R.drawable.emone);
        }else if (position % 4 == 1){
            holder.ivIcon.setImageResource(R.drawable.emtwo);
        }else if (position % 4 == 2){
            holder.ivIcon.setImageResource(R.drawable.emthree);
        } else {
            holder.ivIcon.setImageResource(R.drawable.man);
        }
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
        return arrNguoiDung.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView ivIcon;
        TextView tvName;
        TextView tvPhone;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivIcon = itemView.findViewById(R.id.ivIcon);
            this.tvName = itemView.findViewById(R.id.tvName);
            this.tvPhone = itemView.findViewById(R.id.tvPhone);
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
