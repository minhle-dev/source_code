package com.example.ps10389_lequangminh_assigment.TabPager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ps10389_lequangminh_assigment.Adapter.AdapterSpinnerLoaiChi;
import com.example.ps10389_lequangminh_assigment.Adapter.KhoanChiApdater;
import com.example.ps10389_lequangminh_assigment.DAO.KhoanChiDAO;
import com.example.ps10389_lequangminh_assigment.DAO.LoaiChiDAO;
import com.example.ps10389_lequangminh_assigment.Model.KhoanChi;
import com.example.ps10389_lequangminh_assigment.Model.KhoanThu;
import com.example.ps10389_lequangminh_assigment.Model.LoaiChi;
import com.example.ps10389_lequangminh_assigment.R;
import com.example.ps10389_lequangminh_assigment.XemKhoanChiActivity;
import com.example.ps10389_lequangminh_assigment.XemKhoanThuActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class KhoanChiFragment extends Fragment {
    RecyclerView recyclerView_KhoanChi;

    FloatingActionButton btnFabKhoanChi;

    RecyclerView.LayoutManager layoutManager;
    Spinner LoaiChiSp;
    EditText edtSoTienChi, edtTenKhoanChi;

    Button btnNgayChi;

    LoaiChiDAO loaiChiDAO;
    KhoanChiDAO khoanChiDAO;
    List<LoaiChi> listdata;
    List<KhoanChi> listKhoanChi;
    KhoanChiApdater apdater;
    AdapterSpinnerLoaiChi adapterSpinner;
    int pos = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_khoan_chi, container, false);

        loaiChiDAO = new LoaiChiDAO(getContext());
        khoanChiDAO = new KhoanChiDAO(getContext());

        recyclerView_KhoanChi = view.findViewById(R.id.recyclerview_khoan_chi);
        recyclerView_KhoanChi.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_KhoanChi.setLayoutManager(layoutManager);
        btnFabKhoanChi = view.findViewById(R.id.fab_khoan_chi);

        listdata = new ArrayList<>();
        listKhoanChi = new ArrayList<>();

        listdata = loaiChiDAO.getLoaiChi();
        Log.d("size", String.valueOf(listdata.size()));
        btnFabKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });
        LoadListKhoanChi();


        return view;
    }

    //context menu xoa sua
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = ((KhoanChiApdater) recyclerView_KhoanChi.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.context_delete:
                boolean check = khoanChiDAO.xoaItemKhoanChi(String.valueOf(listKhoanChi.get(position).get_Id()));
                if (check) {
                    Toast.makeText(getContext(), "Xoá thành công!!", Toast.LENGTH_SHORT).show();
                    listKhoanChi.clear();
                    listKhoanChi = khoanChiDAO.getKhoanChi();
                    apdater.notifyDataSetChanged();
                } else
                    Toast.makeText(getContext(), "Không thành công!!", Toast.LENGTH_SHORT).show();
                LoadListKhoanChi();
                break;
            case R.id.context_view:
                KhoanChi khoanChi=listKhoanChi.get(position);
                Intent intent=new Intent(this.getContext(), XemKhoanChiActivity.class);
                intent.putExtra("ngaychi",khoanChi.getNgayChi());
                intent.putExtra("sotienchi",khoanChi.getSoTienChi());
                intent.putExtra("tenkhoanchi",khoanChi.getTenKhoanChi());
                intent.putExtra("loaichi",khoanChi.getLoaiChi());
                getContext().startActivity(intent);
                break;

            case R.id.context_edit:
                ShowDialogUpdate();
                LoadListKhoanChi();
                break;


        }
        return super.onContextItemSelected(item);
    }

    private void ShowDialog() {

        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Thêm Khoản Chi ");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_them_khoan_chi_dialog, null);

        btnNgayChi = view_Add.findViewById(R.id.btnChonNgayChi);
        LoaiChiSp = view_Add.findViewById(R.id.loaichiSpinner);
        edtSoTienChi = view_Add.findViewById(R.id.edtSoTienChi);
        edtTenKhoanChi= view_Add.findViewById(R.id.edtTenKhoanChi);


        adapterSpinner = new AdapterSpinnerLoaiChi(getContext(), R.layout.item_spinner_loaichi, listdata);
        LoaiChiSp.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        aLertDialog.setIcon(R.drawable.ic_add_circle_outline_black_24dp);

        btnNgayChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDate();
            }
        });

        aLertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        aLertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        aLertDialog.setView(view_Add);
        final AlertDialog dialog = aLertDialog.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean err = true;
                KhoanChi khoanChi = new KhoanChi();
                String Ngay = btnNgayChi.getText().toString();
                String LoaiChi = LoaiChiSp.getSelectedItem().toString();
                Integer SoTien = Integer.parseInt(edtSoTienChi.getText().toString());
                String TenKhoanChi = edtTenKhoanChi.getText().toString();
                khoanChi.setLoaiChi(LoaiChi);
                khoanChi.setTenKhoanChi(TenKhoanChi);
                khoanChi.setSoTienChi(SoTien);
                khoanChi.setNgayChi(Ngay);
               if (Ngay.equals("Chọn Ngày")) {
                    err = false;
                    Toast.makeText(getContext(), "Vui Lòng Chọn Ngày", Toast.LENGTH_SHORT).show();
                } else if (SoTien.equals("")) {
                    err = false;
                    Toast.makeText(getContext(), "Vui Lòng Nhập Số Tiền", Toast.LENGTH_SHORT).show();
                }else if (TenKhoanChi.equals("")) {
                   err = false;
                   Toast.makeText(getContext(), "Vui Lòng Nhập Tên Khoảng Chi", Toast.LENGTH_SHORT).show();}
                if (err == true) {
                    long check = khoanChiDAO.themKhoanChi(khoanChi);
                    if (check > 0) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        LoadListKhoanChi();
                    } else {
                        Toast.makeText(getContext(), "Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    //sửa
    private void ShowDialogUpdate() {

        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Sửa Khoản Chi ");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_them_khoan_chi_dialog, null);

        btnNgayChi = view_Add.findViewById(R.id.btnChonNgayChi);
        LoaiChiSp = view_Add.findViewById(R.id.loaichiSpinner);
        edtSoTienChi = view_Add.findViewById(R.id.edtSoTienChi);
        edtTenKhoanChi= view_Add.findViewById(R.id.edtTenKhoanChi);


        adapterSpinner = new AdapterSpinnerLoaiChi(getContext(), R.layout.item_spinner_loaichi, listdata);
        LoaiChiSp.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        aLertDialog.setIcon(R.drawable.ic_add_circle_outline_black_24dp);

        btnNgayChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDate();
            }
        });

        aLertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        aLertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        aLertDialog.setView(view_Add);
        final AlertDialog dialog = aLertDialog.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean err = true;
                KhoanChi khoanChi = new KhoanChi();
                String Ngay = btnNgayChi.getText().toString();
                String LoaiChi = LoaiChiSp.getSelectedItem().toString();
                Integer SoTien = Integer.parseInt(edtSoTienChi.getText().toString());
                String TenKhoanChi = edtTenKhoanChi.getText().toString();
                khoanChi.setLoaiChi(LoaiChi);
                khoanChi.setTenKhoanChi(TenKhoanChi);
                khoanChi.setSoTienChi(SoTien);
                khoanChi.setNgayChi(Ngay);
                if (Ngay.equals("Chọn Ngày")) {
                    err = false;
                    Toast.makeText(getContext(), "Vui Lòng Chọn Ngày", Toast.LENGTH_SHORT).show();
                } else if (SoTien.equals("")) {
                    err = false;
                    Toast.makeText(getContext(), "Vui Lòng Nhập Số Tiền", Toast.LENGTH_SHORT).show();
                }else if (TenKhoanChi.equals("")) {
                    err = false;
                    Toast.makeText(getContext(), "Vui Lòng Nhập Tên Khoảng Chi", Toast.LENGTH_SHORT).show();}
                if (err == true) {
                    Boolean check = khoanChiDAO.UpdateKhoanChi(khoanChi);
                    if (check ) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                        LoadListKhoanChi();
                    } else {
                        Toast.makeText(getContext(), "Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void LoadListKhoanChi() {
        listKhoanChi = khoanChiDAO.getKhoanChi();
        apdater = new KhoanChiApdater(listKhoanChi, getContext());
        recyclerView_KhoanChi.setAdapter(apdater);
        apdater.notifyDataSetChanged();

    }
    public void ChooseDate() {
        final Calendar calendar = Calendar.getInstance();
        //Date
        int Day = calendar.get(Calendar.DAY_OF_MONTH);
        int Month = calendar.get(Calendar.MONTH);
        int Year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                btnNgayChi.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, Year, Month, Day);
        datePickerDialog.show();
    }

}
