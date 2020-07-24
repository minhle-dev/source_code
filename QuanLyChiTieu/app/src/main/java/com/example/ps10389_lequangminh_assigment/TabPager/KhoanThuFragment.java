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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ps10389_lequangminh_assigment.Adapter.AdapterSpinnerLoaiThu;
import com.example.ps10389_lequangminh_assigment.Adapter.KhoanChiApdater;
import com.example.ps10389_lequangminh_assigment.Adapter.KhoanThuApdater;
import com.example.ps10389_lequangminh_assigment.DAO.KhoanThuDAO;
import com.example.ps10389_lequangminh_assigment.DAO.LoaiThuDAO;
import com.example.ps10389_lequangminh_assigment.Model.KhoanThu;
import com.example.ps10389_lequangminh_assigment.Model.LoaiThu;
import com.example.ps10389_lequangminh_assigment.R;
import com.example.ps10389_lequangminh_assigment.XemKhoanThuActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;


public class KhoanThuFragment extends Fragment {
    RecyclerView recyclerView_KhoanThu;

    FloatingActionButton btnFabKhoanThu;

    RecyclerView.LayoutManager layoutManager;
    Spinner LoaiThuSp;
    EditText edtSoTien, edtTenKhoanThu;

    Button btnNgay;

    LoaiThuDAO loaiThuDAO;
    KhoanThuDAO khoanThuDAO;
    List<LoaiThu> listdata;
    List<KhoanThu> listKhoanThu;
    KhoanThuApdater apdater;
    AdapterSpinnerLoaiThu adapterSpinner;
    int pos = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_khoan_thu, container, false);

        loaiThuDAO = new LoaiThuDAO(getContext());
        khoanThuDAO = new KhoanThuDAO(getContext());

        recyclerView_KhoanThu = view.findViewById(R.id.recyclerview_khoan_thu);
        recyclerView_KhoanThu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_KhoanThu.setLayoutManager(layoutManager);
        btnFabKhoanThu = view.findViewById(R.id.fab_khoanthu);

        listdata = new ArrayList<>();
        listKhoanThu = new ArrayList<>();

        listdata = loaiThuDAO.getLoaiThu();
        Log.d("size", String.valueOf(listdata.size()));
        btnFabKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });
        LoadListKhoanThu();


        return view;
    }

    //context menu xoa sua
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = ((KhoanThuApdater) recyclerView_KhoanThu.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.context_delete:
                boolean check = khoanThuDAO.xoaItemKhoanThu(String.valueOf(listKhoanThu.get(position).get_Id()));
                if (check) {
                    Toast.makeText(getContext(), "Xoá thành công!!", Toast.LENGTH_SHORT).show();
                    listKhoanThu.clear();
                    listKhoanThu = khoanThuDAO.getKhoanThu();
                    apdater.notifyDataSetChanged();
                } else
                    Toast.makeText(getContext(), "Không thành công!!", Toast.LENGTH_SHORT).show();
                LoadListKhoanThu();
                break;
            case R.id.context_view:
                KhoanThu khoanThu=listKhoanThu.get(position);
                Intent intent=new Intent(this.getContext(), XemKhoanThuActivity.class);
                intent.putExtra("ngay",khoanThu.getNgayThu());
                intent.putExtra("sotien",khoanThu.getSoTienThu());
                intent.putExtra("tenkhoanthu",khoanThu.getTenKhoanThu());
                intent.putExtra("loaithu",khoanThu.getLoaiThu());
                getContext().startActivity(intent);
                break;
            case R.id.context_edit:
                ShowDialogupdate();
                LoadListKhoanThu();
                break;


        }
        return super.onContextItemSelected(item);
    }

    private void ShowDialog() {

        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Thêm Khoản Thu ");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_them_khoan_thu_dialog, null);
        btnNgay = view_Add.findViewById(R.id.btnChonNgay);
        LoaiThuSp = view_Add.findViewById(R.id.loaithuSpinner);
        edtSoTien = view_Add.findViewById(R.id.edtSoTien);
        edtTenKhoanThu = view_Add.findViewById(R.id.edtTenKhoanThu);

        adapterSpinner = new AdapterSpinnerLoaiThu(getContext(), R.layout.item_spinner_loaithu, listdata);
        LoaiThuSp.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        aLertDialog.setIcon(R.drawable.ic_add_circle_outline_black_24dp);

        btnNgay.setOnClickListener(new View.OnClickListener() {
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
                KhoanThu khoanThu = new KhoanThu();
                String Ngay = btnNgay.getText().toString();
                String LoaiThu = LoaiThuSp.getSelectedItem().toString();
                Integer SoTien = Integer.parseInt(edtSoTien.getText().toString());
                String TenKhoanThu= edtTenKhoanThu.getText().toString();
                khoanThu.setLoaiThu(LoaiThu);
                khoanThu.setTenKhoanThu(TenKhoanThu);
                khoanThu.setSoTienThu(SoTien);
                khoanThu.setNgayThu(Ngay);

                if (Ngay.equals("Chọn Ngày")) {
                    err = false;
                    Toast.makeText(getContext(), "Vui Lòng Chọn Ngày", Toast.LENGTH_SHORT).show();
                } else if (SoTien.equals("")) {
                    err = false;
                    Toast.makeText(getContext(), "Vui Lòng Nhập Số Tiền", Toast.LENGTH_SHORT).show();
                } else if (TenKhoanThu.equals("")) {
                    err = false;
                    Toast.makeText(getContext(), "Vui Lòng Nhập Tên Khoản Thu ", Toast.LENGTH_SHORT).show();}
                if (err == true) {
                    long check = khoanThuDAO.AddKhoanThu(khoanThu);
                    if (check > 0) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                        LoadListKhoanThu();
                    } else {
                        Toast.makeText(getContext(), "Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void ShowDialogupdate() {

        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Sửa Khoản Thu ");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_them_khoan_thu_dialog, null);
        btnNgay = view_Add.findViewById(R.id.btnChonNgay);
        LoaiThuSp = view_Add.findViewById(R.id.loaithuSpinner);
        edtSoTien = view_Add.findViewById(R.id.edtSoTien);
        edtTenKhoanThu = view_Add.findViewById(R.id.edtTenKhoanThu);

        adapterSpinner = new AdapterSpinnerLoaiThu(getContext(), R.layout.item_spinner_loaithu, listdata);
        LoaiThuSp.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        aLertDialog.setIcon(R.drawable.ic_add_circle_outline_black_24dp);

        btnNgay.setOnClickListener(new View.OnClickListener() {
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
                KhoanThu khoanThu = new KhoanThu();
                String Ngay = btnNgay.getText().toString();
                String LoaiThu = LoaiThuSp.getSelectedItem().toString();
                Integer SoTien = Integer.parseInt(edtSoTien.getText().toString());
                String TenKhoanThu= edtTenKhoanThu.getText().toString();
                khoanThu.setLoaiThu(LoaiThu);
                khoanThu.setTenKhoanThu(TenKhoanThu);
                khoanThu.setSoTienThu(SoTien);
                khoanThu.setNgayThu(Ngay);
                if (Ngay.equals("Chọn Ngày")) {
                    err = false;
                    Toast.makeText(getContext(), "Vui Lòng Chọn Ngày", Toast.LENGTH_SHORT).show();
                } else if (SoTien.equals("")) {
                    err = false;
                    Toast.makeText(getContext(), "Vui Lòng Nhập Số Tiền", Toast.LENGTH_SHORT).show();
                } else if (TenKhoanThu.equals("")) {
                    err = false;
                    Toast.makeText(getContext(), "Vui Lòng Nhập Tên Khoản Thu ", Toast.LENGTH_SHORT).show();}
                if (err == true) {
                   Boolean check = khoanThuDAO.UpdateKhoanThu(khoanThu);
                    if (check ) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                        LoadListKhoanThu();
                    } else {
                        Toast.makeText(getContext(), "Thất Bại", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    private void LoadListKhoanThu() {
        listKhoanThu = khoanThuDAO.getKhoanThu();
        apdater = new KhoanThuApdater(listKhoanThu, getContext());
        recyclerView_KhoanThu.setAdapter(apdater);
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
                btnNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, Year, Month, Day);
        datePickerDialog.show();
    }


}
