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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob204_ps10389_lequangminh.R;
import com.example.mob204_ps10389_lequangminh.dao.HoaDonChiTietDAO;
import com.example.mob204_ps10389_lequangminh.dao.HoaDonDAO;
import com.example.mob204_ps10389_lequangminh.dao.NguoiDungDAO;
import com.example.mob204_ps10389_lequangminh.model.HoaDon;
import com.example.mob204_ps10389_lequangminh.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class AdapterHoaDon extends RecyclerView.Adapter<AdapterHoaDon.ViewHolder> implements Filterable {
    public Context context;
    public List<HoaDon> arrHoaDon;
    public List<HoaDon> arrSortHoaDon;
    public LayoutInflater inflater;
    public Filter hoaDonFilter;
    public HoaDonChiTietDAO hoaDonChiTietDAO;

    private int position;



    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AdapterHoaDon(Context context, List<HoaDon> arrHoaDon) {
        this.context = context;
        this.arrHoaDon = arrHoaDon;
        this.arrSortHoaDon = arrHoaDon;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    //tao view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_hoa_don, null);
        return new ViewHolder(view);
    }

    //lay du lieu
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        HoaDon hoaDon = arrHoaDon.get(position);
        holder.tvMaHoaDon.setText("Mã hóa đơn: "+hoaDon.getMaHoaDon());
        holder.tvNgayMua.setText(""+hoaDon.getNgayMua());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                HoaDonChiTietDAO hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
                if
                (hoaDonChiTietDAO.checkHoaDon(arrHoaDon.get(position).getMaHoaDon())){
                    Toast.makeText(context,"Bạn phải xoá hoá đơn chi tiết trước khi xoá hoá đơn này",Toast.LENGTH_LONG).show();
                }else {
                    hoaDonDAO.deleteHoaDonByID(arrHoaDon.get(position).getMaHoaDon());
                    arrHoaDon.remove(position);
                    notifyDataSetChanged();
                }
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
        return arrHoaDon.size();
    }
    //search hoa hon

    public Filter getFilter() {
        if (hoaDonFilter == null)
            hoaDonFilter = new CustomFilter();
        return hoaDonFilter;
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = arrSortHoaDon;
                results.count = arrSortHoaDon.size();
            } else {
                List<HoaDon> lsHoaDon = new ArrayList<HoaDon>();
                for (HoaDon p : arrHoaDon) {
                    if
                    (p.getMaHoaDon().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        lsHoaDon.add(p);
                }
                results.values = lsHoaDon;
                results.count = lsHoaDon.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
// Now we have to inform the adapter about the new list filtered

            arrHoaDon = (List<HoaDon>) results.values;
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView ivIcon;
        TextView tvMaHoaDon;
        TextView tvNgayMua;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivIcon = itemView.findViewById(R.id.ivIcon);
            this.tvMaHoaDon = itemView.findViewById(R.id.tvMaHoaDon);
            this.tvNgayMua = itemView.findViewById(R.id.tvNgayMua);
            this.ivDelete = itemView.findViewById(R.id.ivDelete);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //
            menu.add(Menu.NONE, R.id.context_viewhd,
                    Menu.NONE, "Xem chi tiết hóa đơn");
            menu.add(Menu.NONE, R.id.context_edithd,
                    Menu.NONE, "Sửa thông tin");
        }

    }
}