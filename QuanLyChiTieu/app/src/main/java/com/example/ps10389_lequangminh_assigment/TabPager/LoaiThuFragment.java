package com.example.ps10389_lequangminh_assigment.TabPager;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ps10389_lequangminh_assigment.Adapter.LoaiChiAdapter;
import com.example.ps10389_lequangminh_assigment.Adapter.LoaiThuAdapter;
import com.example.ps10389_lequangminh_assigment.DAO.LoaiThuDAO;
import com.example.ps10389_lequangminh_assigment.Model.LoaiThu;
import com.example.ps10389_lequangminh_assigment.R;

import java.util.ArrayList;
import java.util.List;

public class LoaiThuFragment extends Fragment {
    RecyclerView recyclerView_Loai_Thu;
    FloatingActionButton btnFab;
    Button btnThem;
    Button btnHuy;
    EditText edtTenLoaiThu;
    LoaiThu loaiThu;
    LoaiThuDAO loaiThuDAO;
    LoaiThuAdapter adapter;
    List<LoaiThu> listdata;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_thu, container, false);
        listdata = new ArrayList<>();
        //set du lieu len rycycler view
        recyclerView_Loai_Thu = view.findViewById(R.id.recyclerview_loai_thu);
        recyclerView_Loai_Thu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_Loai_Thu.setLayoutManager(layoutManager);
        btnFab = view.findViewById(R.id.fab); //nút float action button
        loaiThuDAO = new LoaiThuDAO(getContext());
        LoadDuLieuLoaiThu();
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });
        return view;
    }

    private void ShowDialog() {
        final Dialog add_loaithu_layout = new Dialog(getContext());
        add_loaithu_layout.setTitle("Thêm Loại Thu");
        add_loaithu_layout.setCancelable(false);
        add_loaithu_layout.setContentView(R.layout.item_loai_thu_dialog);
        add_loaithu_layout.getWindow().setBackgroundDrawableResource(R.color.white);
        btnThem = add_loaithu_layout.findViewById(R.id.btnThem);
        btnHuy = add_loaithu_layout.findViewById(R.id.btnHuy);
        edtTenLoaiThu = add_loaithu_layout.findViewById(R.id.edtTenLoaiThu);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemLoaiThu();
                add_loaithu_layout.cancel();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_loaithu_layout.cancel();
            }
        });
        add_loaithu_layout.show();
    }

    private void ThemLoaiThu() {
        loaiThu = new LoaiThu(edtTenLoaiThu.getText().toString());
        long check = loaiThuDAO.AddItem(loaiThu);
        if (check > 0) {
            LoadDuLieuLoaiThu();
            Toast.makeText(getContext(), "Thêm Thành Công!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Thêm Thất Bại!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void LoadDuLieuLoaiThu() {
        listdata = loaiThuDAO.getLoaiThu();
        adapter = new LoaiThuAdapter(listdata, getContext());
        recyclerView_Loai_Thu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
