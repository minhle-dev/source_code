package com.example.mob204_ps10389_lequangminh.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mob204_ps10389_lequangminh.R;
import com.example.mob204_ps10389_lequangminh.XemThongTinNguoiDungActivity;
import com.example.mob204_ps10389_lequangminh.adapter.AdapterNguoiDung;
import com.example.mob204_ps10389_lequangminh.dao.NguoiDungDAO;
import com.example.mob204_ps10389_lequangminh.model.NguoiDung;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;


public class FragmentNguoiDung extends Fragment {

    FloatingActionButton btnFabNguoiDung;
    EditText edUserName, edPassWord, edRePassWord, edPhone, edFullName;
    NguoiDungDAO nguoiDungDAO;
    List<NguoiDung> listNguoiDung;
    AdapterNguoiDung apdater;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView_NguoiDung;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nguoi_dung, container, false);

        nguoiDungDAO = new NguoiDungDAO(getContext());
        //anhxa
        recyclerView_NguoiDung = view.findViewById(R.id.recycle_nguoiDung);
        recyclerView_NguoiDung.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_NguoiDung.setLayoutManager(layoutManager);


        listNguoiDung = new ArrayList<>();

        listNguoiDung = nguoiDungDAO.getAllNguoiDung();
        Log.d("size", String.valueOf(listNguoiDung.size()));
        //
        btnFabNguoiDung = view.findViewById(R.id.fabNguoiDung);
        btnFabNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogThemUser();
            }
        });

        LoadListNguoiDung();
        return view;
    }


    private void ShowDialogThemUser() {
        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Thêm Người Dùng");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_dialog_them_nguoi_dung, null);
        edUserName = view_Add.findViewById(R.id.edUsername);
        edPassWord = view_Add.findViewById(R.id.edPassword);
        edRePassWord = view_Add.findViewById(R.id.edRePassword);
        edPhone = view_Add.findViewById(R.id.edPhone);
        edFullName = view_Add.findViewById(R.id.edFullName);
        //btnAddUser = view_Add.findViewById(R.id.btnAddUser);

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
                NguoiDung nguoiDung = new NguoiDung();
                String Username = edUserName.getText().toString();
                String PassWord = edPassWord.getText().toString();
                String rePassWord = (edRePassWord.getText().toString());
                String Phone = edPhone.getText().toString();
                String Fullname = edFullName.getText().toString();
                nguoiDung.setUserName(Username);
                nguoiDung.setPassword(PassWord);
                nguoiDung.setPassword(rePassWord);
                nguoiDung.setPhone(Phone);
                nguoiDung.setHoTen(Fullname);
                nguoiDungDAO = new NguoiDungDAO(getContext());
                NguoiDung user = new NguoiDung(Username, PassWord, rePassWord, Fullname);
                try {
                    if (validateForm() > 0) {
                        if (nguoiDungDAO.inserNguoiDung(user) > 0) {
                            Toast.makeText(getContext(), "Thêm thành công",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Thêm thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
                LoadListNguoiDung();
                dialog.dismiss();
                return;
            }


        });
    }
    //dialog sửa

    private void ShowDialogUpdateUser(int position) {
        final NguoiDung nguoiDung = listNguoiDung.get(position);

        AlertDialog.Builder aLertDialog = new AlertDialog.Builder(getContext());
        aLertDialog.setTitle("Sửa Thông Tin Người Dùng");
        aLertDialog.setMessage("Vui lòng nhập đủ thông tin!");
        LayoutInflater inflater = this.getLayoutInflater();
        View view_Add = inflater.inflate(R.layout.item_dialog_sua_nguoi_dung, null);
        edUserName = view_Add.findViewById(R.id.edUsername);
        edPassWord = view_Add.findViewById(R.id.edPassword);
        edRePassWord = view_Add.findViewById(R.id.edRePassword);
        edPhone = view_Add.findViewById(R.id.edPhone);
        edFullName = view_Add.findViewById(R.id.edFullName);
        //set text len dialog
        edUserName.setText(nguoiDung.getUserName());
        edPassWord.setText(nguoiDung.getPassword());
        edRePassWord.setText(nguoiDung.getPassword());
        edPhone.setText(nguoiDung.getPhone());
        edFullName.setText(nguoiDung.getHoTen());
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
                NguoiDung nguoiDung = new NguoiDung();
                String Username = edUserName.getText().toString();
                String PassWord = edPassWord.getText().toString();
                String rePassWord = (edRePassWord.getText().toString());
                String Phone = edPhone.getText().toString();
                String Fullname = edFullName.getText().toString();
                nguoiDung.setUserName(Username);
                nguoiDung.setPassword(PassWord);
                nguoiDung.setPassword(rePassWord);
                nguoiDung.setPhone(Phone);
                nguoiDung.setHoTen(Fullname);


                nguoiDungDAO = new NguoiDungDAO(getContext());
                NguoiDung user = new NguoiDung(Username, PassWord, rePassWord, Fullname);

                try {
                    if (validateForm() > 0) {
                        if (nguoiDungDAO.updateNguoiDung(user) > 0) {
                            Toast.makeText(getContext(), "Sửa thành công",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Sửa thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                }
                LoadListNguoiDung();
                dialog.dismiss();
                return;
            }


        });
    }

    //context menu xoa sua
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = -1;
        try {
            position = ((AdapterNguoiDung) recyclerView_NguoiDung.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.context_delete:
                NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(getContext());
                nguoiDungDAO.deleteNguoiDungByID(listNguoiDung.get(position).getUserName());
                listNguoiDung.remove(position);
                LoadListNguoiDung();
                break;
            case R.id.context_view:
                Intent intent = new Intent(this.getContext(), XemThongTinNguoiDungActivity.class);
                Bundle b = new Bundle();
                b.putString("USERNAME", listNguoiDung.get(position).getUserName());
                b.putString("PHONE", listNguoiDung.get(position).getPhone());
                b.putString("FULLNAME", listNguoiDung.get(position).getHoTen());
                b.putString("PASSWORD", listNguoiDung.get(position).getPassword());
                intent.putExtras(b);
                getContext().startActivity(intent);
                break;
            case R.id.context_edit:
                ShowDialogUpdateUser(position);
                LoadListNguoiDung();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void LoadListNguoiDung() {
        listNguoiDung = nguoiDungDAO.getAllNguoiDung();
        apdater = new AdapterNguoiDung(getContext(), listNguoiDung);
        recyclerView_NguoiDung.setAdapter(apdater);
        apdater.notifyDataSetChanged();


    }

    public int validateForm() {
        int check = 1;
        if (edUserName.getText().length() == 0 || edFullName.getText().length() == 0
                || edPhone.getText().length() == 0 || edPassWord.getText().length() == 0
                || edRePassWord.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông ",
                    Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (edUserName.getText().length() < 6) {
            Toast.makeText(getContext(), "Username phải nhiều hơn 6 ký tự ",
                    Toast.LENGTH_SHORT).show();
            check = -1;
        } else if (edPassWord.getText().length() < 6 || edRePassWord.getText().length() < 6) {
            Toast.makeText(getContext(), "Password phải nhiều hơn 6 ký tự ",
                    Toast.LENGTH_SHORT).show();
            check = -1;

        } else {
            String pass = edPassWord.getText().toString();
            String rePass = edRePassWord.getText().toString();
            if (!pass.equals(rePass)) {
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp",
                        Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;

    }
    /*
    public String checkNumberPhone(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            return "Chuỗi nhập vào không phải là số!";
        } else if (number.length() == 10 || number.length() == 11) {
            if (number.length() == 10) {
                if (number.substring(0, 2).equals("09")) {
                    return "Số điện thoại hợp lệ";
                } else {
                    return "số điện thoại không hợp lệ!";
                }
            } else if (number.substring(0, 2).equals("01")) {
                return "Số điện thoại hợp lệ";
            } else {
                return "số điện thoại không hợp lệ!";
            }
        } else {
            return "Độ dài chuỗi không hợp lệ!";
        }
    } */
}
