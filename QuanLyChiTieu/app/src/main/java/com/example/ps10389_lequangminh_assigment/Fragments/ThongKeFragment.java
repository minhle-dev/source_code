package com.example.ps10389_lequangminh_assigment.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ps10389_lequangminh_assigment.DAO.KhoanChiDAO;
import com.example.ps10389_lequangminh_assigment.DAO.KhoanThuDAO;
import com.example.ps10389_lequangminh_assigment.R;

import java.text.NumberFormat;
import java.util.Locale;

public class ThongKeFragment extends Fragment {

    KhoanThuDAO khoanThuDAO;
    KhoanChiDAO khoanChiDAO;
    TextView tvTongTienThu, tvTongTienChi,tvTongTienTK;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke, container, false);
        //anh xa
        tvTongTienThu = view.findViewById(R.id.tvTongTienThu);
        tvTongTienChi = view.findViewById(R.id.tvTongTienChi);
        tvTongTienTK = view.findViewById(R.id.tvTongTienTietKiem);
        //
        khoanThuDAO = new KhoanThuDAO(getContext());
        khoanChiDAO = new KhoanChiDAO(getContext());
        //tong tien thu
        int tienThu = khoanThuDAO.SumTienKhoanThu();
        tvTongTienThu.setText(NumberFormat.getNumberInstance(Locale.US).format(tienThu) + " VNĐ");
        //tong tien chi
        int tienChi = khoanChiDAO.SumTienKhoanChi();
        tvTongTienChi.setText(NumberFormat.getNumberInstance(Locale.US).format(tienChi) + " VNĐ");
        //thu-chi
        int tienTK = tienThu - tienChi;
        tvTongTienTK.setText(NumberFormat.getNumberInstance(Locale.US).format(tienTK) + " VNĐ");

        return view;
    }
}
