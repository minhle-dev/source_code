package com.example.ps10389_lequangminh_mob201_assignment.tabfragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps10389_lequangminh_mob201_assignment.R;
import com.example.ps10389_lequangminh_mob201_assignment.adapter.AdapterLichThi;
import com.example.ps10389_lequangminh_mob201_assignment.adapter.AdapterMonHoc;
import com.example.ps10389_lequangminh_mob201_assignment.adapter.AdapterSinhVien;
import com.example.ps10389_lequangminh_mob201_assignment.adapter.SpinnerLopAdapter;
import com.example.ps10389_lequangminh_mob201_assignment.adapter.SpinnerMonHocAdapter;
import com.example.ps10389_lequangminh_mob201_assignment.database.LichThiDAO;
import com.example.ps10389_lequangminh_mob201_assignment.database.LopHocDAO;
import com.example.ps10389_lequangminh_mob201_assignment.database.MonHocDAO;
import com.example.ps10389_lequangminh_mob201_assignment.database.SinhVienDAO;
import com.example.ps10389_lequangminh_mob201_assignment.model.LichThi;
import com.example.ps10389_lequangminh_mob201_assignment.model.LopHoc;
import com.example.ps10389_lequangminh_mob201_assignment.model.MonHoc;
import com.example.ps10389_lequangminh_mob201_assignment.model.SinhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;


public class FragmentLichThi extends Fragment {

    FloatingActionButton btnFabLT;

    MonHocDAO monHocDAO;
    LichThiDAO lichThiDAO;
    List<MonHoc> listMonHoc;
    List<LichThi> listLichThi;
    AdapterLichThi apdater;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView_lt;
    TextView tvLichThi;
    EditText edtGhiChu, edtID;
    SpinnerMonHocAdapter adapterSpinner;
    Spinner spTenMonHoc, spListLichThi;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lich_thi, container, false);

        lichThiDAO = new LichThiDAO(getContext());
        monHocDAO = new MonHocDAO(getContext());
        //anhxa
        recyclerView_lt = view.findViewById(R.id.recycle_lichThi);
        spTenMonHoc = view.findViewById(R.id.monHocSpinner);
        spListLichThi = view.findViewById(R.id.spListLT);
        recyclerView_lt.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_lt.setLayoutManager(layoutManager);


        btnFabLT = view.findViewById(R.id.fabLT);

        listLichThi = new ArrayList<>();
        listMonHoc = new ArrayList<>();
        listMonHoc = monHocDAO.getAllMonHoc();

        adapterSpinner = new SpinnerMonHocAdapter(getContext(), (ArrayList<MonHoc>) listMonHoc);
        spListLichThi.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        setHasOptionsMenu(true);

        //thêm

        //
        spListLichThi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                final String monHoc = listMonHoc.get(i).getTenMonHoc();
                //
                Toast.makeText(getContext(), monHoc, Toast.LENGTH_SHORT).show();
                btnFabLT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShowDialogThemLich(monHoc);
                    }
                });
                //
                listLichThi = lichThiDAO.getMHbyMaMonHoc(monHoc);
                apdater = new AdapterLichThi(getContext(), listLichThi);
                recyclerView_lt.setAdapter(apdater);
                apdater.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void ShowDialogThemLich(final String monHoc) {
        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Thêm lịch thi ");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_dialog_them_lich_thi, null);

        edtGhiChu = view_Add.findViewById(R.id.edtGhiChu);
        tvLichThi = view_Add.findViewById(R.id.tvLichThi);
        spTenMonHoc = view_Add.findViewById(R.id.monHocSpinner);
        edtID = view_Add.findViewById(R.id.edtIDLichThi);

        adapterSpinner = new SpinnerMonHocAdapter(getActivity(), (ArrayList<MonHoc>) listMonHoc);
        spTenMonHoc.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        aLertDialog.setIcon(R.drawable.ic_add_24dp);

        tvLichThi.setOnClickListener(new View.OnClickListener() {
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
                int pos = spTenMonHoc.getSelectedItemPosition();
                String tenMonHoc = listMonHoc.get(pos).getTenMonHoc();

                LichThi lt = new LichThi();
                MonHoc mh = new MonHoc();

                String ID = edtID.getText().toString();
                String LichThi = (tvLichThi.getText().toString());
                String GhiChu = edtGhiChu.getText().toString();

                lt.setIdLichThi(ID);
                lt.setGhiChu(GhiChu);
                lt.setLichThi(LichThi);
                mh.setTenMonHoc(tenMonHoc);

                LichThi lichThi = new LichThi(ID, GhiChu, LichThi, tenMonHoc);
                if (edtID.getText().toString().equals("") || edtID.getText().toString() == null) {
                    edtID.setError("Không được để trống!!");
                    return;
                } else if (edtGhiChu.getText().toString().equals("") || edtGhiChu.getText().toString() == null) {
                    edtGhiChu.setError("Không được để trống!!");
                    return;
                } else if (tvLichThi.getText().toString().equals("") || tvLichThi.getText().toString() == null) {
                    tvLichThi.setError("Không được để trống!!");
                    return;
                } else {
                    for (int i = 0; i < listLichThi.size(); i++) {
                        if (listLichThi.get(i).getIdLichThi().equals(edtID.getText().toString())) {
                            Toast.makeText(getContext(), "Không được nhập trùng ID", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                if (lichThiDAO.ThemLichThi(lichThi) == -1) {
                    Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getContext(), "Thêm thành công.", Toast.LENGTH_SHORT).show();
                    LoadListSV(monHoc);
                    dialog.dismiss();
                }
            }
        });
    }
    ///////////////

    private void ShowDialogSuaLich(final String monHoc) {
        LichThi pos = listLichThi.get(Integer.parseInt(monHoc));

        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Sửa lịch thi ");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_dialog_sua_lich_thi, null);

        edtGhiChu = view_Add.findViewById(R.id.edtGhiChu);
        tvLichThi = view_Add.findViewById(R.id.tvLichThi);
        spTenMonHoc = view_Add.findViewById(R.id.monHocSpinner);
        edtID = view_Add.findViewById(R.id.edtIDLichThi);

        edtID.setText(pos.getIdLichThi());
        edtGhiChu.setText(pos.getGhiChu());
        tvLichThi.setText(pos.getLichThi());

        adapterSpinner = new SpinnerMonHocAdapter(getActivity(), (ArrayList<MonHoc>) listMonHoc);
        spTenMonHoc.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        aLertDialog.setIcon(R.drawable.ic_add_24dp);

        tvLichThi.setOnClickListener(new View.OnClickListener() {
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
                int pos = spTenMonHoc.getSelectedItemPosition();
                String tenMonHoc = listMonHoc.get(pos).getTenMonHoc();
                LichThi lt = new LichThi();
                MonHoc mh = new MonHoc();

                String ID = edtID.getText().toString();
                String LichThi = (tvLichThi.getText().toString());
                String GhiChu = edtGhiChu.getText().toString();

                lt.setIdLichThi(ID);
                lt.setGhiChu(GhiChu);
                lt.setLichThi(LichThi);
                mh.setTenMonHoc(tenMonHoc);

                LichThi lichThi = new LichThi(ID, GhiChu, LichThi, tenMonHoc);
                if (edtID.getText().toString().equals("") || edtID.getText().toString() == null) {
                    edtID.setError("Không được để trống!!");
                    return;
                } else if (edtGhiChu.getText().toString().equals("") || edtGhiChu.getText().toString() == null) {
                    edtGhiChu.setError("Không được để trống!!");
                    return;
                } else if (tvLichThi.getText().toString().equals("") || tvLichThi.getText().toString() == null) {
                    tvLichThi.setError("Không được để trống!!");
                    return;
                }
                if (lichThiDAO.SuaLich(lichThi) == -1) {
                    Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(getContext(), "Thêm thành công.", Toast.LENGTH_SHORT).show();
                    LoadListSV(monHoc);
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
                tvLichThi.setText(sdf.format(calendar.getTime()));
            }
        }, Year, Month, Day);
        datePickerDialog.show();
    }


    ////////////////////click card v
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;

        try {
            position = ((AdapterLichThi) recyclerView_lt.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {

            case R.id.context_edit_lich:
                ShowDialogSuaLich(String.valueOf(position));
                break;
        }
        return super.onContextItemSelected(item);
    }


    private void LoadListSV(String monHoc) {
        listLichThi = lichThiDAO.getMHbyMaMonHoc(monHoc);
        apdater = new AdapterLichThi(getContext(), listLichThi);
        recyclerView_lt.setAdapter(apdater);
        apdater.notifyDataSetChanged();
    }


}
