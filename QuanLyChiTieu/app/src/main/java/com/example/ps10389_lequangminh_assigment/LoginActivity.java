package com.example.ps10389_lequangminh_assigment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    RelativeLayout relley1, rellay2;
    EditText edtUsername, edtPassword;
    Button btnLogin, btnDangKy1;
    CheckBox chkLuuThongTin, chkHienMatKhau;
    SharedPreferences luutru;

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

        //luu thongtin
        luutru = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        //nap thong tin len form tu sharedPreference
        Boolean luuthongtin = luutru.getBoolean("save_information", false);
        if (luuthongtin) {
            edtUsername.setText(luutru.getString("username", ""));
            edtPassword.setText(luutru.getString("password", ""));
            chkLuuThongTin.setChecked(true);
        }

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
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        //luu thong tin lai
        if (chkLuuThongTin.isChecked()) {
            SharedPreferences.Editor editor = luutru.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putBoolean("save_information", chkLuuThongTin.isChecked());
            editor.commit();
        }
        //

        SharedPreferences pref = getSharedPreferences("dangnhap", MODE_PRIVATE);

        String ten = pref.getString("taikhoan", "a");
        String mk = pref.getString("matkhau", "a");


        if (!username.equalsIgnoreCase(ten) || !password.equalsIgnoreCase(mk)) {
            Toast.makeText(this, "Tên tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
        } else if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(LoginActivity.this, IndexActivity.class));
        }

    }

    public void XuLyDangKy(View view) {
        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dangky_layout);

        final EditText tenDangNhap = dialog.findViewById(R.id.edtTaiKhoanDangKy);
        final EditText matKhau = dialog.findViewById(R.id.edtNhapMatKhau);
        Button dangKy = dialog.findViewById(R.id.btnDangKy);

        dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String tk = tenDangNhap.getText().toString().trim();
                String pass = matKhau.getText().toString().trim();

                editor.putString("taikhoan", tk);
                editor.putString("matkhau", pass);

                editor.commit();
                Toast.makeText(LoginActivity.this, "Đăng Kí Thành Công", Toast.LENGTH_SHORT).show();
                dialog.cancel();


            }
        });
        dialog.show();
    }

    public void anhxa() {
        //ánh xạ phần đnhap
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnDangKy1 = findViewById(R.id.btnDangKi1);
        chkLuuThongTin = findViewById(R.id.chkLuuThongTin);
        chkHienMatKhau = findViewById(R.id.chkHienMatKhau);
        relley1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
    }

}
