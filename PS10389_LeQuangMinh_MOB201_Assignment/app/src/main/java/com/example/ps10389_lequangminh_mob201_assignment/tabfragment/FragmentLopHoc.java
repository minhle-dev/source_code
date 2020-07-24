package com.example.ps10389_lequangminh_mob201_assignment.tabfragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.ps10389_lequangminh_mob201_assignment.adapter.AdapterSinhVien;
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


public class FragmentLopHoc extends Fragment {

    FloatingActionButton btnFabLopHoc;

    LopHocDAO lopHocDAO;
    List<LopHoc> listlop;
    AdapterLopHoc apdater;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView_LopHoc;

    EditText edtMaLop, edtTenLop, edtLichHoc;
    TextView tvLichThi;
    Button btnXoaTrang, btnLuuLop;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lop_hoc, container, false);

        lopHocDAO = new LopHocDAO(getContext());
        //anhxa
        recyclerView_LopHoc = view.findViewById(R.id.recycle_lopHoc);
        recyclerView_LopHoc.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_LopHoc.setLayoutManager(layoutManager);


        listlop = new ArrayList<>();

        listlop = lopHocDAO.xemDSLop();
        Log.d("size", String.valueOf(listlop.size()));
        //
        btnFabLopHoc = view.findViewById(R.id.fabLopHoc);
        btnFabLopHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogThemLop();
            }
        });

        LoadListLopHoc();

        return view;
    }


    private void ShowDialogThemLop() {
        lopHocDAO = new LopHocDAO(getContext());
        final List<LopHoc> list = new ArrayList<>();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thêm Lớp Học");
        builder.setIcon(R.drawable.ic_add_24dp);
        View mview = getLayoutInflater().inflate(R.layout.custom_dialog_them_lop, null);
        edtMaLop = mview.findViewById(R.id.edtMaLop);
        edtTenLop = mview.findViewById(R.id.edtTenLop);

        btnXoaTrang = mview.findViewById(R.id.btnXoaTrang);
        btnLuuLop = mview.findViewById(R.id.btnLuuLop);

        builder.setView(mview);


        btnXoaTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMaLop.setText("");
                edtTenLop.setText("");
            }
        });
        btnLuuLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = true;
                if (edtMaLop.getText().toString().equals("") || edtMaLop.getText().toString() == null) {
                    edtMaLop.setError("Không được để trống!!");
                    error = false;
                } else if (edtTenLop.getText().toString().equals("") || edtTenLop.getText().toString() == null) {
                    edtTenLop.setError("Không được để trống!!");
                    error = false;
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getMaLop().equals(edtMaLop.getText().toString())) {
                            Toast.makeText(getContext(), "Không được nhập trùng mã lớp", Toast.LENGTH_SHORT).show();
                            error = false;
                        }
                    }
                }
                if (error == true) {
                    long check = lopHocDAO.ThemLop(new LopHoc(
                            edtMaLop.getText().toString(),
                            edtTenLop.getText().toString()
                    ));
                    if (check > 0) {
                        Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        LoadListLopHoc();
                    } else {
                        Toast.makeText(getContext(), "Không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.show();
    }

    //sua lop
    private void ShowDialogSuaLop(int i) {
        final LopHoc pos = listlop.get(i);
        lopHocDAO = new LopHocDAO(getContext());
        final List<LopHoc> list = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Chỉnh Sửa Lớp Học");
        builder.setIcon(R.drawable.ic_add_24dp);
        View mview = getLayoutInflater().inflate(R.layout.custom_dialog_sua_lop, null);
        edtMaLop = mview.findViewById(R.id.edtMaLop);
        edtTenLop = mview.findViewById(R.id.edtTenLop);
        btnXoaTrang = mview.findViewById(R.id.btnXoaTrang);
        btnLuuLop = mview.findViewById(R.id.btnLuuLop);
        //set text
        edtMaLop.setText(pos.getMaLop());
        edtTenLop.setText(pos.getTenLop());

        builder.setView(mview);
        btnXoaTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMaLop.setText("");
                edtTenLop.setText("");
            }
        });
        btnLuuLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = true;
                if (edtMaLop.getText().toString().equals("") || edtMaLop.getText().toString() == null) {
                    edtMaLop.setError("Không được để trống!!");
                    error = false;
                } else if (edtTenLop.getText().toString().equals("") || edtTenLop.getText().toString() == null) {
                    edtTenLop.setError("Không được để trống!!");
                    error = false;
                }
                if (error == true) {
                    long check = lopHocDAO.SuaLop(new LopHoc(
                            edtMaLop.getText().toString(),
                            edtTenLop.getText().toString()));
                    if (check > 0) {
                        Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                        LoadListLopHoc();
                    } else {
                        Toast.makeText(getContext(), "Không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.show();
    }

    //
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

    //
    //context menu xoa sua
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;

        try {
            position = ((AdapterLopHoc) recyclerView_LopHoc.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {

            case R.id.context_edit_lop:
                ShowDialogSuaLop(position);
                break;
        }
        return super.onContextItemSelected(item);
    }


    private void LoadListLopHoc() {
        listlop = lopHocDAO.xemDSLop();
        apdater = new AdapterLopHoc(getContext(), listlop);
        recyclerView_LopHoc.setAdapter(apdater);
        apdater.notifyDataSetChanged();
    }
}
