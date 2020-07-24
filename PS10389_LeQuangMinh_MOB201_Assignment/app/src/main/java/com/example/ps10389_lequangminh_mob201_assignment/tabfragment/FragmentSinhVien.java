package com.example.ps10389_lequangminh_mob201_assignment.tabfragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps10389_lequangminh_mob201_assignment.R;
import com.example.ps10389_lequangminh_mob201_assignment.adapter.AdapterSinhVien;
import com.example.ps10389_lequangminh_mob201_assignment.adapter.SpinnerLopAdapter;
import com.example.ps10389_lequangminh_mob201_assignment.database.LopHocDAO;
import com.example.ps10389_lequangminh_mob201_assignment.database.SinhVienDAO;
import com.example.ps10389_lequangminh_mob201_assignment.model.LopHoc;
import com.example.ps10389_lequangminh_mob201_assignment.model.SinhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;


public class FragmentSinhVien extends Fragment {

    FloatingActionButton btnFabSV;

    SinhVienDAO sinhVienDAO;
    LopHocDAO lopHocDAO;
    List<SinhVien> listSinhVien;
    List<LopHoc> listlop;
    AdapterSinhVien apdater;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView_sv;
    Spinner lopHocSp, spListLop;
    EditText edtTenSinhVien, edtMaSV;
    TextView tvNgaySinh;
    SpinnerLopAdapter adapterSpinner;
    ImageView ivEdit;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sinh_vien, container, false);
        sinhVienDAO = new SinhVienDAO(getContext());
        lopHocDAO = new LopHocDAO(getContext());
        //anhxa
        recyclerView_sv = view.findViewById(R.id.recycle_sinhVien);
        lopHocSp = view.findViewById(R.id.lopHocSpinner);
        spListLop = view.findViewById(R.id.spListLop);
        recyclerView_sv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_sv.setLayoutManager(layoutManager);


        btnFabSV = view.findViewById(R.id.fabSV);

        listSinhVien = new ArrayList<>();
        listlop = new ArrayList<>();
        listlop = lopHocDAO.xemDSLop();

        adapterSpinner = new SpinnerLopAdapter(getContext(), (ArrayList<LopHoc>) listlop);
        spListLop.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        setHasOptionsMenu(true);

        //thêm
        //
        spListLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                final String maLop = listlop.get(i).getMaLop();
                //
                Toast.makeText(getContext(), maLop, Toast.LENGTH_SHORT).show();
                btnFabSV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShowDialogThemSV(maLop);
                    }
                });
                //
                listSinhVien = sinhVienDAO.getSVbyMaLop(maLop);
                AdapterSinhVien adapterSinhVien = new AdapterSinhVien(getContext(), listSinhVien);
                recyclerView_sv.setAdapter(adapterSinhVien);
                adapterSinhVien.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }


    private void ShowDialogThemSV(final String maLop) {
        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Thêm Sinh Viên ");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_dialog_them_sinh_vien, null);

        edtMaSV = view_Add.findViewById(R.id.edtMaSinhVien);
        edtTenSinhVien = view_Add.findViewById(R.id.edtTenSinhVien);
        tvNgaySinh = view_Add.findViewById(R.id.tvNgaySinh);
        lopHocSp = view_Add.findViewById(R.id.lopHocSpinner);

        adapterSpinner = new SpinnerLopAdapter(getActivity(), (ArrayList<LopHoc>) listlop);
        lopHocSp.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        aLertDialog.setIcon(R.drawable.ic_add_24dp);

        tvNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                int pos = lopHocSp.getSelectedItemPosition();
                String MaLop = listlop.get(pos).getMaLop();
                SinhVien sv = new SinhVien();
                LopHoc lopHoc = new LopHoc();

                String MaSinhVien = (edtMaSV.getText().toString());
                String TenSinhVien = (edtTenSinhVien.getText().toString());
                String NgaySinh = tvNgaySinh.getText().toString();

                sv.setIdSinhVien(MaSinhVien);
                sv.setTenSinhVien(TenSinhVien);
                sv.setNgaySinh(NgaySinh);
                lopHoc.setMaLop(MaLop);

                SinhVien sinhVien = new SinhVien(MaSinhVien, TenSinhVien, NgaySinh, MaLop);

                if (edtMaSV.getText().toString().equals("") || edtMaSV.getText().toString() == null) {
                    edtMaSV.setError("Không được để trống!!");
                    return;
                } else if (edtTenSinhVien.getText().toString().equals("") || edtTenSinhVien.getText().toString() == null) {
                    edtTenSinhVien.setError("Không được để trống!!");
                    return;
                } else if (tvNgaySinh.getText().toString().equals("") || tvNgaySinh.getText().toString() == null) {
                    tvNgaySinh.setError("Không được để trống!!");
                    return;
                } else {
                    for (int i = 0; i < listSinhVien.size(); i++) {
                        if (listSinhVien.get(i).getIdSinhVien().equals(edtMaSV.getText().toString())) {
                            Toast.makeText(getContext(), "Không được nhập trùng mã sinh viên!!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }


                if (sinhVienDAO.ThemSinhVien(sinhVien) == -1) {
                    Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getContext(), "Thêm thành công.", Toast.LENGTH_SHORT).show();
                    LoadListSV(maLop);
                    dialog.dismiss();
                }
            }
        });
    }

    private void ShowDialogSuaSV(final String maLop) {
        final SinhVien posSv= listSinhVien.get(Integer.parseInt(maLop));
        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Sửa Sinh Viên ");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_dialog_sua_sinh_vien, null);
        edtMaSV = view_Add.findViewById(R.id.edtMaSinhVien);
        edtTenSinhVien = view_Add.findViewById(R.id.edtTenSinhVien);
        tvNgaySinh = view_Add.findViewById(R.id.tvNgaySinh);
        lopHocSp = view_Add.findViewById(R.id.lopHocSpinner);

        adapterSpinner = new SpinnerLopAdapter(getActivity(), (ArrayList<LopHoc>) listlop);
        lopHocSp.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        edtMaSV.setText(posSv.getIdSinhVien());
        edtTenSinhVien.setText(posSv.getTenSinhVien());


        aLertDialog.setIcon(R.drawable.ic_add_24dp);
        tvNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                int pos = lopHocSp.getSelectedItemPosition();
                String MaLop = listlop.get(pos).getMaLop();
                SinhVien sv = new SinhVien();
                LopHoc lopHoc = new LopHoc();


                String MaSinhVien = (edtMaSV.getText().toString());
                String TenSinhVien = (edtTenSinhVien.getText().toString());
                String NgaySinh = tvNgaySinh.getText().toString();

                sv.setIdSinhVien(MaSinhVien);
                sv.setTenSinhVien(TenSinhVien);
                sv.setNgaySinh(NgaySinh);
                lopHoc.setMaLop(MaLop);

                SinhVien sinhVien = new SinhVien(MaSinhVien, TenSinhVien, NgaySinh, MaLop);


                if (edtMaSV.getText().toString().equals("") || edtMaSV.getText().toString() == null) {
                    edtMaSV.setError("Không được để trống!!");
                    return;
                } else if (edtTenSinhVien.getText().toString().equals("") || edtTenSinhVien.getText().toString() == null) {
                    edtTenSinhVien.setError("Không được để trống!!");
                    return;
                } else if (tvNgaySinh.getText().toString().equals("") || tvNgaySinh.getText().toString() == null) {
                    tvNgaySinh.setError("Không được để trống!!");
                    return;
                }


                if (sinhVienDAO.SuaSinhVien(sinhVien) == -1) {
                    Toast.makeText(getContext(), "Sủa không thành công", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getContext(), "Sửa thành công.", Toast.LENGTH_SHORT).show();
                    LoadListSV(maLop);
                    dialog.dismiss();
                }
            }
        });
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
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                tvNgaySinh.setText(sdf.format(calendar.getTime()));
            }
        }, Year, Month, Day);
        datePickerDialog.show();
    }

    //context menu xoa sua
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;

        try {
            position = ((AdapterSinhVien) recyclerView_sv.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {

            case R.id.context_edit:
                ShowDialogSuaSV(String.valueOf(position));
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void LoadListSV(String maLop) {
        listSinhVien = sinhVienDAO.getSVbyMaLop(maLop);
        apdater = new AdapterSinhVien(getContext(), listSinhVien);
        recyclerView_sv.setAdapter(apdater);
        apdater.notifyDataSetChanged();
    }

}
