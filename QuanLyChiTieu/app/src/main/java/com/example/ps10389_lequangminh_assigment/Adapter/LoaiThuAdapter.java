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


import com.example.ps10389_lequangminh_assigment.DAO.LoaiThuDAO;
import com.example.ps10389_lequangminh_assigment.Model.LoaiThu;
import com.example.ps10389_lequangminh_assigment.R;


import java.util.List;


class LoaiThuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_LoaiThu;
    public ImageButton btnDel, btnEdit;


    public LoaiThuViewHolder(View itemView) {
        super(itemView);

        txt_LoaiThu = itemView.findViewById(R.id.txtLoaiThu);
        btnDel = itemView.findViewById(R.id.btnDel);
        btnEdit = itemView.findViewById(R.id.btnEdit);
    }

    @Override
    public void onClick(View view) {

    }
}

public class LoaiThuAdapter extends RecyclerView.Adapter<LoaiThuViewHolder> {

    private List<LoaiThu> listData ;
    private Context context;
    LoaiThuAdapter loaiThuAdapter = this;
    LoaiThuDAO loaiThuDAO;

    public LoaiThuAdapter(List<LoaiThu> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public LoaiThuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_loai_thu, parent, false);
        return new LoaiThuViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final LoaiThuViewHolder holder, final int position) {

        loaiThuDAO = new LoaiThuDAO(context);

        holder.txt_LoaiThu.setText("Tên: "+listData.get(position).getTenLoaiThu());
        //xoá sửa
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Bạn có chắc chắn xoá?",5000)
                        .setActionTextColor(Color.RED)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DeleteItemLoaiThu(position);
                            }
                        })
                        .show();
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogEditLoaiThu(position);
            }
        });

    }

    private void ShowDialogEditLoaiThu(final int position) {
        final LoaiThu loaiThu = listData.get(position);
        final EditText editName;
        Button btnEdit, btnHuy;
        final Dialog add_edit_layout = new Dialog(context);
        add_edit_layout.setTitle("Sửa Loại Thu ");
        add_edit_layout.setCancelable(false);
        add_edit_layout.setContentView(R.layout.item_sua);
        add_edit_layout.getWindow().setBackgroundDrawableResource(R.color.white);
        btnEdit = add_edit_layout.findViewById(R.id.btnEdit);
        btnHuy = add_edit_layout.findViewById(R.id.btnCancel);
        editName = add_edit_layout.findViewById(R.id.edtNameEdit);
        editName.setText(loaiThu.getTenLoaiThu());
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaiThu.setTenLoaiThu(editName.getText().toString());
                boolean check = loaiThuDAO.UpdateLoaiThu(loaiThu);
                if (check) {
                    Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                    listData.clear();
                    listData = loaiThuDAO.getLoaiThu();
                    loaiThuAdapter.notifyDataSetChanged();
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

    private void DeleteItemLoaiThu(int position) {
        boolean check = loaiThuDAO.deleteItem(String.valueOf(listData.get(position).get_idLoaiThu()));
        if (check) {
            Toast.makeText(context, "Xoá thành công!!", Toast.LENGTH_SHORT).show();
            listData.clear();
            listData = loaiThuDAO.getLoaiThu();
            loaiThuAdapter.notifyDataSetChanged();
        } else
            Toast.makeText(context, "Không thành công!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


}
