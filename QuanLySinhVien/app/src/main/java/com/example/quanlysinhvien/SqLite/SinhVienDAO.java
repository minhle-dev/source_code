package com.example.quanlysinhvien.SqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlysinhvien.Model.LopHoc;
import com.example.quanlysinhvien.Model.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class SinhVienDAO {
    SQLiteDatabase db;

    public SinhVienDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    public ArrayList<SinhVien> xemQLSV(String MALOP){
        ArrayList<SinhVien> xemqlsv=new ArrayList<>();
        Cursor c=db.query("SINHVIEN",null,"MALOP=?",new String[]{MALOP},null,null,null);
        while (c.moveToNext())
        {
            SinhVien sv =new SinhVien();
            sv.setTenSinhVien(c.getString(c.getColumnIndex("TENSINHVIEN")));
            sv.setNgaySinh(c.getString(c.getColumnIndex("NGAYSINH")));
            sv.setMaLop(c.getString(c.getColumnIndex("MALOP")));

            xemqlsv.add(sv);
        }
        return xemqlsv;
    }


    public long ThemSinhVien(SinhVien sv)
    {
        ContentValues values = new ContentValues();
        values.put("TENSINHVIEN",sv.getTenSinhVien());
        values.put("NGAYSINH",sv.getNgaySinh());
        values.put("MALOP",sv.getMaLop());

        return db.insert("SINHVIEN", null,values);

    }
    public int SuaSinhVien(SinhVien sv){
        ContentValues values = new ContentValues();
        values.put("TENSINHVIEN",sv.getTenSinhVien());
        values.put("NGAYSINH",sv.getNgaySinh());
        values.put("MALOP",sv.getMaLop());

        return db.update("SINHVIEN",values,"MALOP=?",new String[]{sv.getMaLop()});
    }

    public  int XoaSinhVien(SinhVien sv){
        return  db.delete("SINHVIEN","TENSINHVIEN=?",new String[]{sv.getTenSinhVien()});
    }




}


