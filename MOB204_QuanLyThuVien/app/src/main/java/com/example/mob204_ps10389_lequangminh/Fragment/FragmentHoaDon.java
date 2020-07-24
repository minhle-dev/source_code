package com.example.mob204_ps10389_lequangminh.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob204_ps10389_lequangminh.HoaDonChiTietActivity;
import com.example.mob204_ps10389_lequangminh.ListHoaDonChiTietByIDActivity;
import com.example.mob204_ps10389_lequangminh.R;
import com.example.mob204_ps10389_lequangminh.XemThongTinNguoiDungActivity;
import com.example.mob204_ps10389_lequangminh.adapter.AdapterHoaDon;
import com.example.mob204_ps10389_lequangminh.adapter.AdapterNguoiDung;
import com.example.mob204_ps10389_lequangminh.adapter.AdapterSachTrongKho;
import com.example.mob204_ps10389_lequangminh.dao.HoaDonDAO;
import com.example.mob204_ps10389_lequangminh.dao.NguoiDungDAO;
import com.example.mob204_ps10389_lequangminh.model.HoaDon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;


public class FragmentHoaDon extends Fragment {
    RecyclerView recyclerView_HoaDon;
    FloatingActionButton btnFabHoaDon;
    RecyclerView.LayoutManager layoutManager;

    HoaDonDAO hoaDonDAO;
    List<HoaDon> listHoaDon;
    AdapterHoaDon apdater;

    EditText edtMaHoaDon;
    TextView tvNgayMua;
    SearchView svHoaDon;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        svHoaDon = view.findViewById(R.id.svHoaHon);
        hoaDonDAO = new HoaDonDAO(getContext());
        //anhxa
        recyclerView_HoaDon = view.findViewById(R.id.recycle_hoaDon);
        recyclerView_HoaDon.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_HoaDon.setLayoutManager(layoutManager);
        listHoaDon = new ArrayList<>();


        try {
            listHoaDon = hoaDonDAO.getAllHoaDon();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("size", String.valueOf(listHoaDon.size()));
        //
        btnFabHoaDon = view.findViewById(R.id.fabHoaDon);
        btnFabHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogThemHoaDon();
            }
        });

        svHoaDon.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                apdater.getFilter().filter(newText);
                return false;
            }
        });

        LoadListHoaDon();

        return view;
    }

    private void ShowDialogThemHoaDon() {
        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Thêm Hóa Đơn");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_dialog_them_hoa_don, null);
        //anh xa
        edtMaHoaDon = view_Add.findViewById(R.id.edMaHoaDon);
        tvNgayMua = view_Add.findViewById(R.id.tvNgayMua);
        //su kien click
        tvNgayMua.setOnClickListener(new View.OnClickListener() {
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

        aLertDialog.setIcon(R.drawable.ic_add_black_24dp);
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
                HoaDon hoaDon = new HoaDon();
                String MaHoaDon = edtMaHoaDon.getText().toString();
                //
                hoaDon.setMaHoaDon(MaHoaDon);


                hoaDonDAO = new HoaDonDAO(getContext());
                try {
                    if (validation() < 0) {
                        Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        HoaDon hoaDon1 = new HoaDon(MaHoaDon, sdf.parse(tvNgayMua.getText().toString()));
                        if (hoaDonDAO.inserHoaDon(hoaDon1) > 0) {
                            Toast.makeText(getContext(), "Thêm thành công",
                                    Toast.LENGTH_SHORT).show();
                            LoadListHoaDon();
                            Intent intent = new Intent(getActivity(), HoaDonChiTietActivity.class);
                            Bundle b = new Bundle();
                            b.putString("MAHOADON", edtMaHoaDon.getText().toString());
                            intent.putExtras(b);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
                LoadListHoaDon();
                dialog.dismiss();
                return;
            }


        });
    }
    ///

    //context menu xoa sua
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = -1;
        try {
            position = ((AdapterHoaDon) recyclerView_HoaDon.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {

            case R.id.context_viewhd:
                Intent intent = new Intent(this.getContext(), ListHoaDonChiTietByIDActivity.class);
                Bundle b = new Bundle();
                b.putString("MAHOADON", listHoaDon.get(position).getMaHoaDon());
                intent.putExtras(b);
                getContext().startActivity(intent);
                break;
            case R.id.context_edithd:
               Intent intent1 = new Intent(getActivity(), HoaDonChiTietActivity.class);
                            startActivity(intent1);
                break;
        }
        return super.onContextItemSelected(item);
    }



    private void LoadListHoaDon() {
        try {
            listHoaDon = hoaDonDAO.getAllHoaDon();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        apdater = new AdapterHoaDon(getContext(), listHoaDon);
        recyclerView_HoaDon.setAdapter(apdater);
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
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                tvNgayMua.setText(sdf.format(calendar.getTime()));
            }
        }, Year, Month, Day);
        datePickerDialog.show();
    }

    public int validation() {
        if
        (edtMaHoaDon.getText().toString().isEmpty() || tvNgayMua.getText().toString().isEmpty()
        ) {
            return -1;
        }
        return 1;
    }

}
