package com.example.mob204_ps10389_lequangminh.TabpagerFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob204_ps10389_lequangminh.LoginActivity;
import com.example.mob204_ps10389_lequangminh.R;
import com.example.mob204_ps10389_lequangminh.XemThongTinNguoiDungActivity;
import com.example.mob204_ps10389_lequangminh.XemThongTinTheLoaiActivity;
import com.example.mob204_ps10389_lequangminh.adapter.AdapterNguoiDung;
import com.example.mob204_ps10389_lequangminh.adapter.AdapterTheLoai;
import com.example.mob204_ps10389_lequangminh.dao.NguoiDungDAO;
import com.example.mob204_ps10389_lequangminh.dao.TheLoaiDAO;
import com.example.mob204_ps10389_lequangminh.model.NguoiDung;
import com.example.mob204_ps10389_lequangminh.model.TheLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class FragmentTheLoaiSach extends Fragment {

    FloatingActionButton btnFabTheLoai;
    EditText edMaTheLoai, edTenTheLoai, edViTri, edMoTa;

    TheLoaiDAO theLoaiDAO;
    List<TheLoai> listTheLoai;
    AdapterTheLoai apdater;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView_TheLoai;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_the_loai_sach, container, false);

        theLoaiDAO = new TheLoaiDAO(getContext());
        //anhxa
        recyclerView_TheLoai = view.findViewById(R.id.recycle_theLoai);
        recyclerView_TheLoai.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_TheLoai.setLayoutManager(layoutManager);


        listTheLoai = new ArrayList<>();

        listTheLoai = theLoaiDAO.getAllTheLoai();
        Log.d("size", String.valueOf(listTheLoai.size()));
        //
        btnFabTheLoai = view.findViewById(R.id.fabTheLoai);
        btnFabTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogThemTheLoai();
            }
        });

        LoadListTheLoai();


        return view;
    }

    //context menu xoa sua
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = -1;
        try {
            position = ((AdapterTheLoai) recyclerView_TheLoai.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.context_delete:
                TheLoaiDAO theLoaiDAO = new TheLoaiDAO(getContext());
                theLoaiDAO.deleteTheLoaiByID(listTheLoai.get(position).getMaTheLoai());
                listTheLoai.remove(position);
                LoadListTheLoai();
                break;
            case R.id.context_view:
                Intent intent = new Intent(this.getContext(), XemThongTinTheLoaiActivity.class);
                Bundle b = new Bundle();
                b.putString("MATHELOAI", listTheLoai.get(position).getMaTheLoai());
                b.putString("TENTHELOAI", listTheLoai.get(position).getTenTheLoai());
                b.putString("VITRI", String.valueOf(listTheLoai.get(position).getViTri()));
                b.putString("MOTA", listTheLoai.get(position).getMoTa());
                intent.putExtras(b);
                getContext().startActivity(intent);
                break;
            case R.id.context_edit:
                ShowDialogUpdateTheLoai(position);
                LoadListTheLoai();
                break;
        }
        return super.onContextItemSelected(item);
    }

    // them the loai sách
    private void ShowDialogThemTheLoai() {
        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Thêm Thể Loại");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_dialog_them_the_loai, null);
        //anh xa
        edMaTheLoai = view_Add.findViewById(R.id.edMaTheLoai);
        edTenTheLoai = view_Add.findViewById(R.id.edTenTheLoai);
        edViTri = view_Add.findViewById(R.id.edViTri);
        edMoTa = view_Add.findViewById(R.id.edMoTa);
        //su kien click
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
                TheLoai theLoai = new TheLoai();
                String MaTheLoai = edMaTheLoai.getText().toString();
                String TenTheLoai = edTenTheLoai.getText().toString();
                Integer ViTri = Integer.valueOf((edViTri.getText().toString()));
                String MoTa = edMoTa.getText().toString();
                //
                theLoai.setMaTheLoai(MaTheLoai);
                theLoai.setTenTheLoai(TenTheLoai);
                theLoai.setViTri(Integer.parseInt(String.valueOf(ViTri)));
                theLoai.setMoTa(MoTa);
                theLoaiDAO = new TheLoaiDAO(getContext());
                TheLoai tl = new TheLoai(MaTheLoai, TenTheLoai, MoTa, ViTri);
                try {
                    if (validateForm() > 0) {
                        if (theLoaiDAO.inserTheLoai(tl) > 0) {
                            Toast.makeText(getContext(), "Thêm thành công",
                                    Toast.LENGTH_SHORT).show();
                            LoadListTheLoai();
                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
                LoadListTheLoai();
                dialog.dismiss();
                return;
            }


        });
    }

    private void ShowDialogUpdateTheLoai(int position) {
        final TheLoai theLoai = listTheLoai.get(position);

        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Sửa Thông Tin Thể Loại");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_dialog_sua_the_loai, null);
        //anh xa
        edMaTheLoai = view_Add.findViewById(R.id.edMaTheLoai);
        edTenTheLoai = view_Add.findViewById(R.id.edTenTheLoai);
        edViTri = view_Add.findViewById(R.id.edViTri);
        edMoTa = view_Add.findViewById(R.id.edMoTa);
        //set text len dialog

        edMaTheLoai.setText(theLoai.getMaTheLoai());
        edTenTheLoai.setText(theLoai.getTenTheLoai());
        edViTri.setText(String.valueOf(theLoai.getViTri()));
        edMoTa.setText(theLoai.getMoTa());
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
                TheLoai theLoai = new TheLoai();
                String MaTheLoai = edMaTheLoai.getText().toString();
                String TenTheLoai = edTenTheLoai.getText().toString();
                Integer ViTri = Integer.valueOf((edViTri.getText().toString()));
                String MoTa = edMoTa.getText().toString();
                //
                theLoai.setMaTheLoai(MaTheLoai);
                theLoai.setTenTheLoai(TenTheLoai);
                theLoai.setViTri(Integer.parseInt(String.valueOf(ViTri)));
                theLoai.setMoTa(MoTa);
                theLoaiDAO = new TheLoaiDAO(getContext());
                TheLoai tl = new TheLoai(MaTheLoai, TenTheLoai, MoTa, ViTri);
                try {
                    if (validateForm() > 0) {
                        if (theLoaiDAO.updateTheLoai(tl) > 0) {
                            Toast.makeText(getContext(), "Thêm thành công",
                                    Toast.LENGTH_SHORT).show();
                            LoadListTheLoai();
                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
                LoadListTheLoai();
                dialog.dismiss();
                return;
            }


        });
    }


    public int validateForm() {
        int check = 1;
        if (edMaTheLoai.getText().length() == 0 || edTenTheLoai.getText().length() == 0
                || edMoTa.getText().length() == 0 || edViTri.getText().length() == 0
        ) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông ",
                    Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;

    }


    private void LoadListTheLoai() {
        listTheLoai = theLoaiDAO.getAllTheLoai();
        apdater = new AdapterTheLoai(getContext(), listTheLoai);
        recyclerView_TheLoai.setAdapter(apdater);
        apdater.notifyDataSetChanged();
    }

}
