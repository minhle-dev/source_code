package com.example.quanlysinhvien;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;

import com.example.quanlysinhvien.Adapter.LopHocAdapter;
import com.example.quanlysinhvien.Adapter.SinhVienAdapter;
import com.example.quanlysinhvien.Adapter.SpinnerLopAdapter;
import com.example.quanlysinhvien.Model.LopHoc;
import com.example.quanlysinhvien.Model.SinhVien;
import com.example.quanlysinhvien.SqLite.LopHocDAO;
import com.example.quanlysinhvien.SqLite.SinhVienDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QLSVActivity extends AppCompatActivity {
    EditText edtTenSinhVien;
    TextView tvNgaySinh;
    Spinner spLop;
    Button btnThemSV, btnXoaSv, btnSuaSv;
    SinhVienDAO svDao;
    LopHocDAO lopDao;
    ArrayList<SinhVien> list = new ArrayList<>();
    ArrayList<LopHoc> listlop = new ArrayList<>();
    ListView lvQLSV;
    SpinnerLopAdapter spinnerLopAdapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    static final String TAG="QLSV";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlsv);
        anhxa();

        // Làm chọn ngày cho ngày sinh
        tvNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog= new DatePickerDialog(
                        QLSVActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                Log.d(TAG,"onDateSet:mm/dd/yyyy: "+dayOfMonth+"/"+month+"/"+year);
                String date = "   "+dayOfMonth +"/"+month+"/"+year;
                tvNgaySinh.setText(date);
            }
        } ;


        lvQLSV = findViewById(R.id.lvQLSV);
        list = new ArrayList<>();
        svDao = new SinhVienDAO(this);
        lopDao = new LopHocDAO(this);
        //set malop vao spinner
        listlop = (ArrayList<LopHoc>) lopDao.getMaLop();
        spinnerLopAdapter = new SpinnerLopAdapter(this,listlop);
        spLop.setAdapter(spinnerLopAdapter);


        //click hien len listv
        lvQLSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SinhVien sinhVien = list.get(position);
                edtTenSinhVien.setText(sinhVien.getTenSinhVien());
                tvNgaySinh.setText(sinhVien.getNgaySinh());
            }
        });


        //click vao spinner de chon lop
        spLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
           public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {
                //Tạo 1 biến chạy
                //Tạo final cho nó để nó có thể duyệt trong override khác
                final String maLop = listlop.get(position).getMaLop();
                Toast.makeText(QLSVActivity.this, maLop, Toast.LENGTH_SHORT).show();
                ///Tất cả thêm xóa sửa sẽ nằm trong này vì trong này là Selected
                list= svDao.xemQLSV(maLop);
                SinhVienAdapter adaptersv = new SinhVienAdapter(QLSVActivity.this, list);
                lvQLSV.setAdapter(adaptersv);

                //button them sv
                btnThemSV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SinhVien sv = new SinhVien();
                        sv.setTenSinhVien(edtTenSinhVien.getText().toString());
                        sv.setNgaySinh(tvNgaySinh.getText().toString());
                        sv.setMaLop(maLop);
                        if (edtTenSinhVien.getText().toString().equals("") ||edtTenSinhVien.getText().toString()==null){
                            edtTenSinhVien.setError("Không được để trống!!");
                            return;
                        }else if (tvNgaySinh.getText().toString().equals("") ||tvNgaySinh.getText().toString()==null){
                            tvNgaySinh.setError("Không được để trống!!");
                            return;}
                            if (svDao.ThemSinhVien(sv)==-1){
                                Toast.makeText(getApplicationContext(),"Thêm không thành công",Toast.LENGTH_SHORT).show();
                                return;
                            }else {
                            Toast.makeText(getApplicationContext(),"Thêm thành công.",Toast.LENGTH_SHORT).show();}
                            list= svDao.xemQLSV(maLop);
                            SinhVienAdapter adaptersv = new SinhVienAdapter(QLSVActivity.this, list);
                            lvQLSV.setAdapter(adaptersv);
                        }
                });

                btnXoaSv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SinhVien sv = new SinhVien();
                        sv.setTenSinhVien(edtTenSinhVien.getText().toString());
                        sv.setNgaySinh(tvNgaySinh.getText().toString());
                        if (edtTenSinhVien.getText().toString().equals("") ||edtTenSinhVien.getText().toString()==null){
                            edtTenSinhVien.setError("Không được để trống!!");
                            return;
                        }else if (tvNgaySinh.getText().toString().equals("") ||tvNgaySinh.getText().toString()==null){
                            tvNgaySinh.setError("Không được để trống!!");
                            return;}
                        if (svDao.XoaSinhVien(sv) ==-1){
                            Toast.makeText(getApplicationContext(),"Xóa không thành công.",Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                        Toast.makeText(getApplicationContext(),"Xóa thành công.",Toast.LENGTH_SHORT).show();}
                        list= svDao.xemQLSV(maLop);
                        SinhVienAdapter adaptersv = new SinhVienAdapter(QLSVActivity.this, list);
                        lvQLSV.setAdapter(adaptersv);
                    }
                });
                btnSuaSv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SinhVien sv = new SinhVien();
                        sv.setTenSinhVien(edtTenSinhVien.getText().toString());
                        sv.setNgaySinh(tvNgaySinh.getText().toString());
                        sv.setMaLop(maLop);
                        if (edtTenSinhVien.getText().toString().equals("") ||edtTenSinhVien.getText().toString()==null){
                            edtTenSinhVien.setError("Không được để trống!!");
                            return;
                        }else if (tvNgaySinh.getText().toString().equals("") ||tvNgaySinh.getText().toString()==null){
                            tvNgaySinh.setError("Không được để trống!!");
                            return;}
                        if (svDao.SuaSinhVien(sv)==-1){
                            Toast.makeText(getApplicationContext(),"Sửa không thành công.",Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                        Toast.makeText(getApplicationContext(),"Sửa thành công.",Toast.LENGTH_SHORT).show();}
                        list= svDao.xemQLSV(maLop);
                        SinhVienAdapter adaptersv = new SinhVienAdapter(QLSVActivity.this, list);
                        lvQLSV.setAdapter(adaptersv);
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void anhxa(){
        edtTenSinhVien = findViewById(R.id.edtTenSv);
        tvNgaySinh = findViewById(R.id.tvNgaySinh);
        spLop = findViewById(R.id.spLop);
        btnThemSV = findViewById(R.id.btnThemSV);
        btnSuaSv = findViewById(R.id.btnSuaSV);
        btnXoaSv = findViewById(R.id.btnXoaSV);
    }





}
