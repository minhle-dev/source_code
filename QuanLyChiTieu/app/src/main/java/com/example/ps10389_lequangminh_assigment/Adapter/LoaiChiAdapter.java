package com.example.ps10389_lequangminh_assigment.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ps10389_lequangminh_assigment.DAO.LoaiChiDAO;
import com.example.ps10389_lequangminh_assigment.Model.LoaiChi;
import com.example.ps10389_lequangminh_assigment.Model.LoaiThu;
import com.example.ps10389_lequangminh_assigment.R;

import java.util.List;


class LoaiChiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_LoaiChi;
    public ImageButton btnDel, btnEdit;


    public LoaiChiViewHolder(View itemView) {
        super(itemView);

        txt_LoaiChi = itemView.findViewById(R.id.txtLoaiChi);
        btnDel = itemView.findViewById(R.id.btnDel);
        btnEdit = itemView.findViewById(R.id.btnEdit);
    }

    @Override
    public void onClick(View view) {

    }
}

public class LoaiChiAdapter extends RecyclerView.Adapter<LoaiChiViewHolder> {

    private List<LoaiChi> listData ;
    private Context context;
    LoaiChiAdapter loaiChiAdapter = this;
    LoaiChiDAO loaiChiDAO;

    public LoaiChiAdapter(List<LoaiChi> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public LoaiChiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_loai_chi, parent, false);
        return new LoaiChiViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final LoaiChiViewHolder holder, final int position) {

        loaiChiDAO = new LoaiChiDAO(context);

        holder.txt_LoaiChi.setText("Tên: "+listData.get(position).getTenLoaiChi());
        //xoá sửa
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Bạn có chắc chắn xoá?",5000)
                        .setActionTextColor(Color.RED)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DeleteItemLoaiChi(position);
                            }
                        })
                        .show();
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogEditLoaiChi(position);
            }
        });

    }
    //xoá
    private void DeleteItemLoaiChi(int position) {
        boolean check = loaiChiDAO.deleteItemLoaiChi(String.valueOf(listData.get(position).get_idLoaiChi()));
        if (check) {
            Toast.makeText(context, "Xoá thành công!!", Toast.LENGTH_SHORT).show();
            listData.clear();
            listData = loaiChiDAO.getLoaiChi();
            loaiChiAdapter.notifyDataSetChanged();
        } else
            Toast.makeText(context, "Không thành công!!", Toast.LENGTH_SHORT).show();
    }
    //sửa
    private void ShowDialogEditLoaiChi(final int position) {
        final LoaiChi loaiChi = listData.get(position);
        final EditText editName;
        Button btnEdit, btnHuy;
        final Dialog add_edit_layout = new Dialog(context);
        add_edit_layout.setTitle("Thêm Loại Chi ");
        add_edit_layout.setCancelable(false);
        add_edit_layout.setContentView(R.layout.item_sua);
        add_edit_layout.getWindow().setBackgroundDrawableResource(R.color.white);
        btnEdit = add_edit_layout.findViewById(R.id.btnEdit);
        btnHuy = add_edit_layout.findViewById(R.id.btnCancel);
        editName = add_edit_layout.findViewById(R.id.edtNameEdit);
        editName.setText(loaiChi.getTenLoaiChi());
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {loaiChi.setTenLoaiChi(editName.getText().toString());
                boolean check = loaiChiDAO.UpdateLoaiChi(loaiChi);
                if (check) {
                    Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                    listData.clear();
                    listData = loaiChiDAO.getLoaiChi();
                    loaiChiAdapter.notifyDataSetChanged();
                } else
                    Toast.makeText(context, "Không thành công!!", Toast.LENGTH_SHORT).show();
                add_edit_layout.cancel();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_edit_layout.cancel();
            }
        });
        add_edit_layout.show();
    }



    @Override
    public int getItemCount() {
        return listData.size();
    }


}
