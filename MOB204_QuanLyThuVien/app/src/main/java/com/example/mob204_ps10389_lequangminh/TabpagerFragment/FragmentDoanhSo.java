package com.example.mob204_ps10389_lequangminh.TabpagerFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mob204_ps10389_lequangminh.R;
import com.example.mob204_ps10389_lequangminh.dao.HoaDonChiTietDAO;

import java.text.NumberFormat;
import java.util.Locale;


public class FragmentDoanhSo extends Fragment {
    TextView tvNgay, tvThang, tvNam;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_so, container, false);
        tvNgay = view.findViewById(R.id.tvThongKeNgay);
        tvThang = view.findViewById(R.id.tvThongKeThang);
        tvNam = view.findViewById(R.id.tvThongKeNam);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(getContext());

        //tong tien thu
        double sumDay = hoaDonChiTietDAO.getDoanhThuTheoNgay();
        double sumMonth = hoaDonChiTietDAO.getDoanhThuTheoThang();
        double sumYear = hoaDonChiTietDAO.getDoanhThuTheoNam();
        tvNgay.setText("Hôm nay:    " + NumberFormat.getNumberInstance(Locale.US).format(sumDay) + " VNĐ");
        tvThang.setText("Tháng này: " + NumberFormat.getNumberInstance(Locale.US).format(sumMonth) + " VNĐ");
        tvNam.setText("Năm này:    " + NumberFormat.getNumberInstance(Locale.US).format(sumYear) + " VNĐ");


        return view;
    }

}
