package com.example.quanlysinhvien;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DangKyActivity extends AppCompatActivity {
    EditText edtTaiKhoanDangKy, edtNhapMatKhau, edtNhapLaiMatKhau;
    Button btnDangKy, btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        setContentView(R.layout.activity_dangky);
        Intent intent = getIntent();

        anhxa();
    }



    public void anhxa(){
        edtTaiKhoanDangKy = findViewById(R.id.edtTaiKhoanDangKy);
        edtNhapMatKhau = findViewById(R.id.edtNhapMatKhau);
        edtNhapLaiMatKhau = findViewById(R.id.edtNhapLaiMatKhau);
        btnDangKy = findViewById(R.id.btnDangKi);
        btnThoat = findViewById(R.id.btnThoat);
    }

    public void XyLyDangKyTK(View view){

        if (edtNhapMatKhau.getText().toString().equals(edtNhapLaiMatKhau.getText().toString())){
            Intent intent = new Intent(DangKyActivity.this,MainActivity.class);
            //truyền tk mat khau qua bên đăng nhập luôn
            intent.putExtra("Tai Khoan",edtTaiKhoanDangKy.getText().toString());
            intent.putExtra("Mat Khau",edtNhapMatKhau.getText().toString());

            startActivity(intent);
            Toast toast= Toast.makeText(getApplicationContext(),"Đăng ký thành công.",Toast.LENGTH_SHORT);
            toast.show();
        }else {
            Toast toast= Toast.makeText(getApplicationContext(),"Mật khẩu không giống nhau.",Toast.LENGTH_SHORT);
            toast.show();}
    }

    public void thoatDangKy(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(DangKyActivity.this);
        builder.setTitle("Bạn có muốn thoát?");
        builder.setMessage("Hãy lựa chọn bên dưới để xác nhận.");
        builder.setIcon(R.drawable.ic_cancel);
        builder.setCancelable(false);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}




