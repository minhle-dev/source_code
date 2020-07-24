package com.example.quanlysinhvien;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relley1,rellay2;
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
        //ánh xạ phần đnhap
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword =(EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnDangKy1 = findViewById(R.id.btnDangKi1);
        chkLuuThongTin = findViewById(R.id.chkLuuThongTin);
        chkHienMatKhau = findViewById(R.id.chkHienMatKhau);

        //phần loa icon màn hình chờ
        relley1 = (RelativeLayout)findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout)findViewById(R.id.rellay2);

        handler.postDelayed(runnable, 1500);

        //phan truyen tai khoan mat khau qua
        Intent intent = getIntent();
        String TaiKhoan = intent.getStringExtra("Tai Khoan");
        String MatKhau = intent.getStringExtra("Mat Khau");
        edtUsername.setText(TaiKhoan);
        edtPassword.setText(MatKhau);
        //luu thongtin
        luutru = getSharedPreferences("myfile", Context.MODE_PRIVATE);
        //nap thong tin len form tu sharedPreference
        Boolean luuthongtin = luutru.getBoolean("save_information",false);
        if (luuthongtin) {
            edtUsername.setText(luutru.getString("username", ""));
            edtPassword.setText(luutru.getString("password", ""));
            chkLuuThongTin.setChecked(true);
        }

        chkHienMatKhau.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void XuLyDangNhap(View view){
        Intent intent = getIntent();
        String TaiKhoan = intent.getStringExtra("Tai Khoan");
        String MatKhau = intent.getStringExtra("Mat Khau");
        edtUsername.setText(TaiKhoan);
        edtPassword.setText(MatKhau);
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if (username.equals(TaiKhoan)&& password.equals(MatKhau)) {
            Toast.makeText(this,"Đăng nhập thành công.",Toast.LENGTH_SHORT).show();
            if (chkLuuThongTin.isChecked()){
                SharedPreferences.Editor editor = luutru.edit();
                editor.putString("username",username);
                editor.putString("password",password);
                editor.putBoolean("save_information",chkLuuThongTin.isChecked());
                editor.commit();
            }
        }
        Intent intent1 = new Intent(MainActivity.this,HomeActivity.class);

        //truyền tk mat khau qua bên đăng nhập luôn
        intent1.putExtra("Tai Khoan",edtUsername.getText().toString());
        startActivity(intent1);

    }

    public  void XuLyDangKy(View view){
        Intent intent = new Intent(MainActivity.this,DangKyActivity.class);
        startActivity(intent);
    }


}
