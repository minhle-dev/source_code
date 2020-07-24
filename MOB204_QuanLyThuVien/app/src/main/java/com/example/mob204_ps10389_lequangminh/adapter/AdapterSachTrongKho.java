package com.example.mob204_ps10389_lequangminh.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob204_ps10389_lequangminh.R;
import com.example.mob204_ps10389_lequangminh.dao.SachDAO;
import com.example.mob204_ps10389_lequangminh.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class AdapterSachTrongKho extends RecyclerView.Adapter<AdapterSachTrongKho.ViewHolder> implements Filterable {
    public Context context;
    public List<Sach> arrSach;
    public List<Sach> arrSortSach;
    public LayoutInflater inflater;
    public SachDAO sachDAO;
    private int position;
    private Filter sachFilter;


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AdapterSachTrongKho(Context context, List<Sach> arrSach) {
        this.context = context;
        this.arrSach = arrSach;
        this.arrSortSach = arrSach;


        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    //tao view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_sach_trong_kho, null);
        return new ViewHolder(view);
    }

    //lay du lieu
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Sach sach = arrSach.get(position);
        if (position % 5 ==0) {
            holder.ivIcon.setImageResource(R.drawable.book1);
        }else if (position % 5 == 1){
            holder.ivIcon.setImageResource(R.drawable.book2);
        }else if (position % 5 == 2){
            holder.ivIcon.setImageResource(R.drawable.book3);
        }else if (position % 5 == 3){
            holder.ivIcon.setImageResource(R.drawable.book4);
        } else {
            holder.ivIcon.setImageResource(R.drawable.book5);
        }
        holder.tvMaSach.setText("Mã sách:  " + sach.getMaSach()+" | "+sach.getTenSach());
        holder.tvSoLuong.setText("Số lượng: " + sach.getSoLuong() + "");
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SachDAO sachDAO = new SachDAO(context);
                sachDAO.deleteSachByID(arrSach.get(position).getMaSach());
                arrSach.remove(position);
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
        return arrSach.size();
    }

    //fiterable


    @Override
    public Filter getFilter() {
        if (sachFilter == null)
            sachFilter = new CustomFilter();
        return sachFilter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
// We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortSach;
                results.count = arrSortSach.size();
            } else {
                List<Sach> lsSach = new ArrayList<Sach>();
                for (Sach p : arrSach) {
                    if
                    (p.getMaSach().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsSach.add(p);
                }
                results.values = lsSach;
                results.count = lsSach.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            arrSach = (List<Sach>) results.values;
            notifyDataSetChanged();
        }
    }

    ;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView ivIcon;
        TextView tvMaSach;
        TextView tvSoLuong;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivIcon = itemView.findViewById(R.id.ivIcon);
            this.tvMaSach = itemView.findViewById(R.id.tvMaSach);
            this.tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            this.ivDelete = itemView.findViewById(R.id.ivDelete);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null
            menu.add(Menu.NONE, R.id.context_viewsach,
                    Menu.NONE, "Xem thông tin");
            menu.add(Menu.NONE, R.id.context_editsach,
                    Menu.NONE, "Sửa thông tin");
            menu.add(Menu.NONE, R.id.context_deletesach,
                    Menu.NONE, "Xóa");
        }
    }

}
