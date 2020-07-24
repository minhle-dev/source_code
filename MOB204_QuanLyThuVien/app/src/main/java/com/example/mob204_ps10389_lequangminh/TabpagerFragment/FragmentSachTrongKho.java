package com.example.mob204_ps10389_lequangminh.TabpagerFragment;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob204_ps10389_lequangminh.R;
import com.example.mob204_ps10389_lequangminh.XemThongTinSachActivity;
import com.example.mob204_ps10389_lequangminh.adapter.AdapterSachTrongKho;
import com.example.mob204_ps10389_lequangminh.adapter.AdapterSpinnerTheLoai;
import com.example.mob204_ps10389_lequangminh.dao.SachDAO;
import com.example.mob204_ps10389_lequangminh.dao.TheLoaiDAO;
import com.example.mob204_ps10389_lequangminh.model.Sach;
import com.example.mob204_ps10389_lequangminh.model.TheLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class FragmentSachTrongKho extends Fragment {

    RecyclerView recyclerView_SachTrongKho;

    FloatingActionButton btnFabSachTrongKho;

    RecyclerView.LayoutManager layoutManager;
    Spinner TheLoaiSp;
    EditText edtMaSach, edtTenSach, edtTacGia, edtNXB, edtGiaBia, edtSoLuong;


    TheLoaiDAO theLoaiDAO;
    SachDAO sachDAO;
    List<TheLoai> listTheLoai;
    List<Sach> listSach;
    AdapterSachTrongKho apdater;
    AdapterSpinnerTheLoai adapterSpinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach_trong_kho, container, false);

        theLoaiDAO = new TheLoaiDAO(getContext());
        sachDAO = new SachDAO(getContext());

        recyclerView_SachTrongKho = view.findViewById(R.id.recycle_sachTrongKho);
        recyclerView_SachTrongKho.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_SachTrongKho.setLayoutManager(new GridLayoutManager(getContext(),3));

        btnFabSachTrongKho = view.findViewById(R.id.fabSachTrongKho);

        listTheLoai = new ArrayList<>();
        listSach = new ArrayList<>();

        listTheLoai = theLoaiDAO.getAllTheLoai();
        setHasOptionsMenu(true);

        //thêm
        btnFabSachTrongKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogThemSach();
            }
        });

        LoadListSach();

        return view;
    }

    //thêm sách mới
    public void ShowDialogThemSach() {
        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Thêm Sách ");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_dialog_them_sach, null);

        edtMaSach = view_Add.findViewById(R.id.edtMaSach);
        edtTenSach = view_Add.findViewById(R.id.edtTenSach);
        edtTacGia = view_Add.findViewById(R.id.edtTacGia);
        edtNXB = view_Add.findViewById(R.id.edtNXB);
        edtGiaBia = view_Add.findViewById(R.id.edtGiaBia);
        edtSoLuong = view_Add.findViewById(R.id.edtSoLuong);
        TheLoaiSp = view_Add.findViewById(R.id.theloaiSpinner);

        adapterSpinner = new AdapterSpinnerTheLoai(getContext(), R.layout.item_spinner_theloai, listTheLoai);
        TheLoaiSp.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        aLertDialog.setIcon(R.drawable.ic_add_black_24dp);


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
                Sach sach = new Sach();
                TheLoai theLoai = new TheLoai();
                String MaTheLoai = TheLoaiSp.getSelectedItem().toString();
                String MaSach = (edtMaSach.getText().toString());
                String TenSach = edtTenSach.getText().toString();
                String NXB = edtNXB.getText().toString();
                Double GiaBia = Double.valueOf(edtGiaBia.getText().toString());
                String TacGia = edtTacGia.getText().toString();
                String SoLuong = edtSoLuong.getText().toString();


                sach.setMaSach(MaSach);
                sach.setGiaBia((GiaBia));
                sach.setTacGia(TacGia);
                sach.setTenSach(TenSach);
                sach.setNXB(NXB);
                sach.setSoLuong((SoLuong));
                theLoai.setMaTheLoai(MaTheLoai);

                sachDAO = new SachDAO(getContext());
                Sach sach1 = new Sach(MaSach, MaTheLoai, TenSach, TacGia, NXB, GiaBia, SoLuong);
                try {
                    if (sachDAO.inserSach(sach1) > 0) {
                        dialog.dismiss();
                        LoadListSach();
                        Toast.makeText(getContext(), "Thêm thành công",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }

            public int checkPositionTheLoai(String strTheLoai) {
                for (int i = 0; i < listTheLoai.size(); i++) {
                    if (strTheLoai.equals(listTheLoai.get(i).getMaTheLoai())) {
                        return i;
                    }
                }
                return 0;
            }


        });
    }
    //sửa sách đã thêm
    public void ShowDialogSuaSach(int position) {
        final Sach posSach= listSach.get(position);

        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Sửa Thông Tin Sách ");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_dialog_sua_sach, null);

        edtMaSach = view_Add.findViewById(R.id.edtMaSach);
        edtTenSach = view_Add.findViewById(R.id.edtTenSach);
        edtTacGia = view_Add.findViewById(R.id.edtTacGia);
        edtNXB = view_Add.findViewById(R.id.edtNXB);
        edtGiaBia = view_Add.findViewById(R.id.edtGiaBia);
        edtSoLuong = view_Add.findViewById(R.id.edtSoLuong);
        TheLoaiSp = view_Add.findViewById(R.id.theloaiSpinner);

        adapterSpinner = new AdapterSpinnerTheLoai(getContext(), R.layout.item_spinner_theloai, listTheLoai);
        TheLoaiSp.setAdapter(adapterSpinner);
        adapterSpinner.notifyDataSetChanged();

        //settext len dialog sửa sách
        //set text len dialog
        edtMaSach.setText(posSach.getMaSach());
        edtTenSach.setText(posSach.getTenSach());
        edtTacGia.setText(posSach.getTacGia());
        edtNXB.setText(posSach.getNXB());
        edtGiaBia.setText(posSach.getGiaBia()+"");
        edtSoLuong.setText(posSach.getSoLuong());


        aLertDialog.setIcon(R.drawable.ic_add_black_24dp);


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
                Sach sach = new Sach();
                TheLoai theLoai = new TheLoai();
                String MaTheLoai = TheLoaiSp.getSelectedItem().toString();
                String MaSach = (edtMaSach.getText().toString());
                String TenSach = edtTenSach.getText().toString();
                String NXB = edtNXB.getText().toString();
                Double GiaBia = Double.valueOf(edtGiaBia.getText().toString());
                String TacGia = edtTacGia.getText().toString();
                String SoLuong = (edtSoLuong.getText().toString());


                sach.setMaSach(MaSach);
                sach.setGiaBia((GiaBia));
                sach.setTacGia(TacGia);
                sach.setTenSach(TenSach);
                sach.setNXB(NXB);
                sach.setSoLuong((SoLuong));
                theLoai.setMaTheLoai(MaTheLoai);

                sachDAO = new SachDAO(getContext());
                Sach sach1 = new Sach(MaSach, MaTheLoai, TenSach, TacGia, NXB, GiaBia, SoLuong);
                try {
                    if (sachDAO.updateSach(sach1) > 0) {
                        dialog.dismiss();
                        LoadListSach();
                        Toast.makeText(getContext(), "Sửa thành công",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Sửa thất bại",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
            }

            public int checkPositionTheLoai(String strTheLoai) {
                for (int i = 0; i < listTheLoai.size(); i++) {
                    if (strTheLoai.equals(listTheLoai.get(i).getMaTheLoai())) {
                        return i;
                    }
                }
                return 0;
            }


        });
    }

    //context menu xoa sua
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = -1;
        try {
            position = ((AdapterSachTrongKho) recyclerView_SachTrongKho.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.context_deletesach:
                SachDAO sachDAO = new SachDAO(getContext());
                sachDAO.deleteSachByID(listSach.get(position).getMaSach());
                listSach.remove(position);
                LoadListSach();
                break;
            case R.id.context_viewsach:
                Intent intent = new Intent(this.getContext(), XemThongTinSachActivity.class);
                Bundle b = new Bundle();
                b.putString("MASACH", listSach.get(position).getMaSach());
                b.putString("MATHELOAI", listTheLoai.get(position).getMaTheLoai());
                b.putString("TENSACH", listSach.get(position).getTenSach());
                b.putString("NXB", listSach.get(position).getNXB());
                b.putString("TACGIA", listSach.get(position).getTacGia());
                b.putString("GIABIA", String.valueOf(listSach.get(position).getGiaBia()));
                b.putString("SOLUONG", listSach.get(position).getSoLuong());

                intent.putExtras(b);
                getContext().startActivity(intent);

                break;
            case R.id.context_editsach:
                ShowDialogSuaSach(position);
                LoadListSach();
                break;
        }
        return super.onContextItemSelected(item);
    }
    //search sasch
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_sach,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.widget.SearchView searchView = (android.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void LoadListSach() {

        listSach = sachDAO.getAllSach();

        apdater = new AdapterSachTrongKho(getContext(), listSach);
        recyclerView_SachTrongKho.setAdapter(apdater);
        apdater.notifyDataSetChanged();
    }

}
