package com.example.ps10389_lequangminh_mob201_assignment.tabfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ps10389_lequangminh_mob201_assignment.R;
import com.example.ps10389_lequangminh_mob201_assignment.adapter.AdapterLopHoc;
import com.example.ps10389_lequangminh_mob201_assignment.adapter.AdapterMonHoc;
import com.example.ps10389_lequangminh_mob201_assignment.adapter.AdapterSinhVien;
import com.example.ps10389_lequangminh_mob201_assignment.adapter.SpinnerLopAdapter;
import com.example.ps10389_lequangminh_mob201_assignment.database.LopHocDAO;
import com.example.ps10389_lequangminh_mob201_assignment.database.MonHocDAO;
import com.example.ps10389_lequangminh_mob201_assignment.database.SinhVienDAO;
import com.example.ps10389_lequangminh_mob201_assignment.model.LopHoc;
import com.example.ps10389_lequangminh_mob201_assignment.model.MonHoc;
import com.example.ps10389_lequangminh_mob201_assignment.model.SinhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class FragmentLichHoc extends Fragment {
    FloatingActionButton btnFabMH;

    MonHocDAO monHocDAO;
    LopHocDAO lopHocDAO;
    List<MonHoc> listMonHoc;
    List<LopHoc> listlop;
    AdapterMonHoc apdater;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView_mh;
    Spinner spMonHoc, spListMon;
    EditText edtTenMonHoc, edtLichHoc, edtID;
    SpinnerLopAdapter adapterSpinner;
    Button btnXoaTrang, btnLuuLop;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lich_hoc, container, false);
        monHocDAO = new MonHocDAO(getContext());
        lopHocDAO = new LopHocDAO(getContext());
        //anhxa
        recyclerView_mh = view.findViewById(R.id.recycle_lichHoc);
        spMonHoc = view.findViewById(R.id.spMaLop);
        spListMon = view.findViewById(R.id.spListLopLH);
        recyclerView_mh.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_mh.setLayoutManager(layoutManager);


        btnFabMH = view.findViewById(R.id.fabMH);

        listMonHoc = new ArrayList<>();
        listlop = new ArrayList<>();
        listlop = lopHocDAO.xemDSLop();

        adapterSpinner = new SpinnerLopAdapter(getContext(), (ArrayList<LopHoc>) listlop);
        spListMon.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        setHasOptionsMenu(true);

        //thêm

        //
        spListMon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                final String maLop = listlop.get(i).getMaLop();
                //
                Toast.makeText(getContext(), maLop, Toast.LENGTH_SHORT).show();
                btnFabMH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShowDialogThemMon(maLop);
                    }
                });
                //
                listMonHoc = monHocDAO.getMHbyMaLop(maLop);
                AdapterMonHoc adapterMonHoc = new AdapterMonHoc(getContext(), listMonHoc);
                recyclerView_mh.setAdapter(adapterMonHoc);
                adapterMonHoc.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void ShowDialogThemMon(final String maLop) {
        monHocDAO = new MonHocDAO(getContext());
        final List<MonHoc> list = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thêm Môn Học");
        builder.setIcon(R.drawable.ic_add_24dp);
        View mview = getLayoutInflater().inflate(R.layout.custom_dialog_them_mon_hoc, null);
        edtID = mview.findViewById(R.id.edtID);
        edtTenMonHoc = mview.findViewById(R.id.edtTenMonHoc);
        edtLichHoc = mview.findViewById(R.id.edtLichHoc);
        spMonHoc = mview.findViewById(R.id.spMaLop);
        btnXoaTrang = mview.findViewById(R.id.btnXoaTrang);
        btnLuuLop = mview.findViewById(R.id.btnLuuLop);
        adapterSpinner = new SpinnerLopAdapter(getActivity(), (ArrayList<LopHoc>) listlop);
        spMonHoc.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        builder.setView(mview);


        btnXoaTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtID.setText("");
                edtTenMonHoc.setText("");
                edtLichHoc.setText("");
            }
        });
        btnLuuLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LopHoc lopHoc = new LopHoc();
                int pos = spMonHoc.getSelectedItemPosition();
                String MaLop = listlop.get(pos).getMaLop();
                lopHoc.setMaLop(MaLop);
                boolean error = true;
                if (edtTenMonHoc.getText().toString().equals("") || edtTenMonHoc.getText().toString() == null) {
                    edtTenMonHoc.setError("Không được để trống!!");
                    error = false;
                } else if (edtLichHoc.getText().toString().equals("") || edtLichHoc.getText().toString() == null) {
                    edtLichHoc.setError("Không được để trống!!");
                    error = false;
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getMaMonHoc().equals(edtID.getText().toString())) {
                            Toast.makeText(getContext(), "Không được nhập trùng mã môn học", Toast.LENGTH_SHORT).show();
                            error = false;
                        }
                    }
                }
                if (error == true) {
                    long check = monHocDAO.ThemMonHoc(new MonHoc(edtID.getText().toString(),
                            edtTenMonHoc.getText().toString(),
                            edtLichHoc.getText().toString(),
                            MaLop
                    ));
                    if (check > 0) {
                        Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        LoadListMonHoc(maLop);

                    } else {
                        Toast.makeText(getContext(), "Không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.show();
    }
    ////////////////sua

    private void ShowDialogSuaMon(final String maLop) {
        monHocDAO = new MonHocDAO(getContext());
        MonHoc pos = listMonHoc.get(Integer.parseInt(maLop));
        final List<MonHoc> list = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thêm Môn Học");
        builder.setIcon(R.drawable.ic_add_24dp);
        View mview = getLayoutInflater().inflate(R.layout.custom_dialog_sua_mon_hoc, null);
        edtID = mview.findViewById(R.id.edtID);
        edtTenMonHoc = mview.findViewById(R.id.edtTenMonHoc);
        edtLichHoc = mview.findViewById(R.id.edtLichHoc);
        spMonHoc = mview.findViewById(R.id.spMaLop);
        btnXoaTrang = mview.findViewById(R.id.btnXoaTrang);
        btnLuuLop = mview.findViewById(R.id.btnLuuLop);
        adapterSpinner = new SpinnerLopAdapter(getActivity(), (ArrayList<LopHoc>) listlop);
        spMonHoc.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();
        /////////
        edtID.setText(pos.getMaMonHoc());
        edtTenMonHoc.setText(pos.getTenMonHoc());
        edtLichHoc.setText(pos.getLichHoc());


        builder.setView(mview);

        btnXoaTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtID.setText("");
                edtTenMonHoc.setText("");
                edtLichHoc.setText("");
            }
        });
        btnLuuLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LopHoc lopHoc = new LopHoc();
                int pos = spMonHoc.getSelectedItemPosition();
                String MaLop = listlop.get(pos).getMaLop();
                lopHoc.setMaLop(MaLop);
                boolean error = true;
                if (edtTenMonHoc.getText().toString().equals("") || edtTenMonHoc.getText().toString() == null) {
                    edtTenMonHoc.setError("Không được để trống!!");
                    error = false;
                } else if (edtLichHoc.getText().toString().equals("") || edtLichHoc.getText().toString() == null) {
                    edtLichHoc.setError("Không được để trống!!");
                    error = false;
                }
                if (error == true) {
                    long check = monHocDAO.SuaMonHoc(new MonHoc(edtID.getText().toString(),
                            edtTenMonHoc.getText().toString(),
                            edtLichHoc.getText().toString(),
                            MaLop
                    ));
                    if (check > 0) {
                        Toast.makeText(getContext(), "Sửa thành công!", Toast.LENGTH_SHORT).show();
                        LoadListMonHoc(maLop);

                    } else {
                        Toast.makeText(getContext(), "Không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.show();
    }

    ////////////////////click card v
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;

        try {
            position = ((AdapterMonHoc) recyclerView_mh.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {

            case R.id.context_edit_mon:
                ShowDialogSuaMon(String.valueOf(position));
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void LoadListMonHoc(String maLop) {
        listMonHoc = monHocDAO.getMHbyMaLop(maLop);
        apdater = new AdapterMonHoc(getContext(), listMonHoc);
        recyclerView_mh.setAdapter(apdater);
        apdater.notifyDataSetChanged();
    }


}
