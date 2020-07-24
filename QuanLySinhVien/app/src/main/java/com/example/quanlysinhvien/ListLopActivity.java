package com.example.quanlysinhvien;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlysinhvien.Adapter.LopHocAdapter;
import com.example.quanlysinhvien.Model.LopHoc;
import com.example.quanlysinhvien.SqLite.LopHocDAO;

import java.util.ArrayList;
import java.util.List;

public class ListLopActivity extends AppCompatActivity {

    Button btnDangXuat;
    TextView tvNguoidung;
    Button btnAdd, btnList, btnQLSV, btnXoaTrang, btnLuuLop;
    EditText edtSTT,edtMaLop, edtTenLop;

    List<LopHoc> list = new ArrayList<>();
    LopHocDAO databaseLopHoc;

    LopHocAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lop);

        anhxa();
        listView = findViewById(R.id.lvAddLop);
        list = new ArrayList<>();
        databaseLopHoc = new LopHocDAO(this);
        LoadData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LopHoc nv = list.get(position);
                edtMaLop.setText(nv.getMaLop());
                edtTenLop.setText(nv.getTenLop());
            }
        });

    }

    private  void LoadData(){
        list = databaseLopHoc.xemDSLop();
        LopHocAdapter adapter = new LopHocAdapter(this, (ArrayList<LopHoc>) list);
        listView.setAdapter(adapter);
    }

    public void anhxa(){
        tvNguoidung = findViewById(R.id.tvNguoidung);
        btnAdd = findViewById(R.id.btnAddLop);
        btnList = findViewById(R.id.btnListLop);
        btnQLSV = findViewById(R.id.btnQLSV);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        edtMaLop = findViewById(R.id.edtMaLop);
        edtTenLop = findViewById(R.id.edtTenLop);
        edtSTT = findViewById(R.id.edtSTT);
        btnXoaTrang = findViewById(R.id.btnXoaTrang);
        btnLuuLop = findViewById(R.id.btnLuuLop);
    }
    public void onXoa(View view){
        LopHoc nv = new LopHoc();
        nv.setTenLop(edtTenLop.getText().toString());
        nv.setMaLop(edtMaLop.getText().toString());
        if (edtMaLop.getText().toString().equals("") ||edtMaLop.getText().toString()==null){
            edtMaLop.setError("Không được để trống!!");
            return;
        }else if (edtTenLop.getText().toString().equals("") ||edtTenLop.getText().toString()==null){
            edtTenLop.setError("Không được để trống!!");
            return;
        }
        if (databaseLopHoc.XoaLop(nv)==-1){
            Toast.makeText(getApplicationContext(),"Xoa khong thanh cong",Toast.LENGTH_SHORT).show();
            return;
        }else {
        Toast.makeText(getApplicationContext(),"Xoa thanh cong",Toast.LENGTH_SHORT).show();
        LoadData();}
    }
    public void onXoaTrang( View view){
        edtMaLop.setText("");
        edtTenLop.setText("");
    }
    public void onSua(View view){
        LopHoc sp1 = new LopHoc();
        sp1.setMaLop(edtMaLop.getText().toString());
        sp1.setTenLop(edtTenLop.getText().toString());
        if (edtMaLop.getText().toString().equals("") ||edtMaLop.getText().toString()==null){
            edtMaLop.setError("Không được để trống!!");
            return;
        }else if (edtTenLop.getText().toString().equals("") ||edtTenLop.getText().toString()==null){
            edtTenLop.setError("Không được để trống!!");
            return;
        }
        if (databaseLopHoc.SuaLop(sp1) == -1){
            Toast.makeText(getApplicationContext(),"Sửa không thành công",Toast.LENGTH_SHORT).show();
            return;
        }else {
        Toast.makeText(getApplicationContext(),"Sửa thành công",Toast.LENGTH_SHORT).show();
        LoadData();}
    }

}
