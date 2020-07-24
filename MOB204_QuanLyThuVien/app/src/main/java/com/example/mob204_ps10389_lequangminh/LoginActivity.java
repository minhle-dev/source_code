package com.example.mob204_ps10389_lequangminh;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mob204_ps10389_lequangminh.dao.NguoiDungDAO;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout relley1, rellay2;
    EditText edtUsername, edtPassword;
    Button btnLogin, btnDangKy1;
    CheckBox chkLuuThongTin, chkHienMatKhau;
    SharedPreferences luutru;
    String strUser, strPass;
    NguoiDungDAO nguoiDungDAO;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            relley1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        //phần loa icon màn hình chờ
        handler.postDelayed(runnable, 1500);
        nguoiDungDAO = new NguoiDungDAO(LoginActivity.this);


//        //luu thongtin
//        luutru = getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
//        //nap thong tin len form tu sharedPreference
//        Boolean luuthongtin = luutru.getBoolean("save_information", false);
//        if (luuthongtin) {
//            edtUsername.setText(luutru.getString("USERNAME", ""));
//            edtPassword.setText(luutru.getString("PASSWORD", ""));
//            chkLuuThongTin.setChecked(true);
//        }

        chkHienMatKhau.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void XuLyDangNhap(View view) {
//        String username = edtUsername.getText().toString();
//        String password = edtPassword.getText().toString();
        //luu thong tin lai
//        if (chkLuuThongTin.isChecked()) {
//            SharedPreferences.Editor editor = luutru.edit();
//            editor.putString("username", username);
//            editor.putString("password", password);
//            editor.putBoolean("save_information", chkLuuThongTin.isChecked());
//            editor.commit();
//        }
//        //
//
//        SharedPreferences pref = getSharedPreferences("dangnhap", MODE_PRIVATE);
//
//        String ten = pref.getString("taikhoan", "a");
//        String mk = pref.getString("matkhau", "a");
//
//
//        if (!username.equalsIgnoreCase(ten) || !password.equalsIgnoreCase(mk)) {
//            Toast.makeText(this, "Tên tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
//        } else if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
//            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
//
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//        }
        strUser = edtUsername.getText().toString();
        strPass = edtPassword.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không được bỏ trống",
                    Toast.LENGTH_SHORT).show();
        } else {
            if (nguoiDungDAO.checkLogin(strUser, strPass) > 0) {
                rememberUser(strUser, strPass, chkLuuThongTin.isChecked());
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else if
            (strUser.equalsIgnoreCase("admin") && strPass.equalsIgnoreCase("admin")) {
                rememberUser(strUser, strPass, chkLuuThongTin.isChecked());
                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không đúng",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void rememberUser(String u, String p, boolean b) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!b) {
//xoa tinh trang luu tru truoc do
            edit.clear();
        } else {
//luu du lieu
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", b);

        }
//luu lai toan bo
        edit.commit();
    }

//    public void XuLyDangKy(View view) {
//        final Dialog dialog = new Dialog(this);
//
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dangky_layout);
//
//        final EditText tenDangNhap = dialog.findViewById(R.id.edtTaiKhoanDangKy);
//        final EditText matKhau = dialog.findViewById(R.id.edtNhapMatKhau);
//        Button dangKy = dialog.findViewById(R.id.btnDangKy);
//
//        dangKy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences sharedPreferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                String tk = tenDangNhap.getText().toString().trim();
//                String pass = matKhau.getText().toString().trim();
//
//                editor.putString("taikhoan", tk);
//                editor.putString("matkhau", pass);
//
//                editor.commit();
//                Toast.makeText(LoginActivity.this, "Đăng Kí Thành Công", Toast.LENGTH_SHORT).show();
//                dialog.cancel();
//            }
//        });
//        dialog.show();
//    }

    public void anhxa() {
        //ánh xạ phần đnhap
        edtUsername =  findViewById(R.id.edtUsername);
        edtPassword =  findViewById(R.id.edtPassword);
        btnLogin =  findViewById(R.id.btnLogin);
        btnDangKy1 = findViewById(R.id.btnDangKi1);
        chkLuuThongTin = findViewById(R.id.chkLuuThongTin);
        chkHienMatKhau = findViewById(R.id.chkHienMatKhau);
        relley1 =  findViewById(R.id.rellay1);
        rellay2 =  findViewById(R.id.rellay2);
    }

}
