package com.example.quanlysinhvien;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlysinhvien.Model.LopHoc;
import com.example.quanlysinhvien.SqLite.LopHocDAO;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    Button btnDangXuat;
    TextView tvNguoidung;
    Button btnAdd, btnList, btnQLSV, btnXoaTrang, btnLuuLop;
    Intent intent;
    EditText edtMaLop, edtTenLop;
    LopHocDAO lopHocDAO;
    List<LopHoc> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        anhxa();;
        //truyen ten admin qua
        Intent intentTruyen = getIntent();
        String TaiKhoan = intentTruyen.getStringExtra("Tai Khoan");
        tvNguoidung.setText(TaiKhoan);
    }

    public void anhxa(){
        tvNguoidung = findViewById(R.id.tvNguoidung);
        btnAdd = findViewById(R.id.btnAddLop);
        btnList = findViewById(R.id.btnListLop);
        btnQLSV = findViewById(R.id.btnQLSV);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        edtMaLop = findViewById(R.id.edtMaLop);
        edtTenLop = findViewById(R.id.edtTenLop);
        btnXoaTrang = findViewById(R.id.btnXoaTrang);
        btnLuuLop = findViewById(R.id.btnLuuLop);



    }
    public  void onQLSV(View view){
        intent = new Intent(HomeActivity.this,QLSVActivity.class);
        startActivity(intent);

    }

    public void AddLop(View view){
        //
        lopHocDAO = new LopHocDAO(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Thêm Lớp Học");
        builder.setIcon(R.drawable.ic_add_black);
        View mview = getLayoutInflater().inflate(R.layout.custom_dialog_addlop,null);
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
                if (edtMaLop.getText().toString().equals("") ||edtMaLop.getText().toString()==null){
                    edtMaLop.setError("Không được để trống!!");
                    error = false;
                }else if (edtTenLop.getText().toString().equals("") ||edtTenLop.getText().toString()==null){
                    edtTenLop.setError("Không được để trống!!");
                    error = false;
                }else {
                    for (  int i = 0; i<list.size();i++){
                        if (list.get(i).getMaLop().equals(edtMaLop.getText().toString())){
                            Toast.makeText(HomeActivity.this,"Không được nhập trùng mã lớp",Toast.LENGTH_SHORT).show();
                            error = false;
                        }
                    }
                }
                if (error==true){
                    long check = lopHocDAO.ThemLop(new LopHoc(
                            edtMaLop.getText().toString(),
                            edtTenLop.getText().toString()
                    ));
                    if (check>0){
                        Toast.makeText(HomeActivity.this,"Thêm thành công!",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(HomeActivity.this,"Không thành công",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.show();
    }
    //click de qua trang danh sach lop
    public void onListLop(View view){
        startActivity(new Intent(HomeActivity.this,ListLopActivity.class));
    }
    //
    //nut dang xuat
    public void xuLyDangXuat(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Bạn có muốn đăng xuất không?");
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
