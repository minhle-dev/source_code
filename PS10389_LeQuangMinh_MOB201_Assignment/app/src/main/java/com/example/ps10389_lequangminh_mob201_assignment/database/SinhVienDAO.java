package com.example.ps10389_lequangminh_mob201_assignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ps10389_lequangminh_mob201_assignment.model.SinhVien;

import java.util.ArrayList;

public class SinhVienDAO {
    SQLiteDatabase db;
    public static final String TAG = "svDAO";
    public SinhVienDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<SinhVien> getSVbyMaLop(String MALOP) {
        ArrayList<SinhVien> xemqlsv = new ArrayList<>();
        Cursor c = db.query("SINHVIEN", null, "MALOP=?", new String[]{MALOP}, null, null, null);
        while (c.moveToNext()) {
            SinhVien sv = new SinhVien();
            sv.setIdSinhVien(c.getString(c.getColumnIndex("MASINHVIEN")));
            sv.setTenSinhVien(c.getString(c.getColumnIndex("TENSINHVIEN")));
            sv.setNgaySinh(c.getString(c.getColumnIndex("NGAYSINH")));
            sv.setMaLop(c.getString(c.getColumnIndex("MALOP")));

            xemqlsv.add(sv);
        }
        return xemqlsv;
    }

    public ArrayList<SinhVien> getAllSv() {
        ArrayList<SinhVien> xemqlsv = new ArrayList<>();
        Cursor c = db.query("SINHVIEN", null, null, null, null, null, null);
        while (c.moveToNext()) {
            SinhVien sv = new SinhVien();
            sv.setIdSinhVien(c.getString(c.getColumnIndex("MASINHVIEN")));
            sv.setTenSinhVien(c.getString(c.getColumnIndex("TENSINHVIEN")));
            sv.setNgaySinh(c.getString(c.getColumnIndex("NGAYSINH")));
            sv.setMaLop(c.getString(c.getColumnIndex("MALOP")));

            xemqlsv.add(sv);
        }
        return xemqlsv;
    }


    public int ThemSinhVien(SinhVien sv) {
        ContentValues values = new ContentValues();
        values.put("MASINHVIEN",sv.getIdSinhVien());
        values.put("TENSINHVIEN", sv.getTenSinhVien());
        values.put("NGAYSINH", sv.getNgaySinh());
        values.put("MALOP", sv.getMaLop());
        try {
            if (db.insert("SINHVIEN", null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;



    }

    public int SuaSinhVien(SinhVien sv) {
        ContentValues values = new ContentValues();
        values.put("MASINHVIEN",sv.getIdSinhVien());
        values.put("TENSINHVIEN", sv.getTenSinhVien());
        values.put("NGAYSINH", sv.getNgaySinh());
        values.put("MALOP", sv.getMaLop());
        int result = db.update("SINHVIEN", values, "MASINHVIEN=?", new String[]{sv.getIdSinhVien()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int XoaSinhVien(String sv) {
        int result= db.delete("SINHVIEN", "MASINHVIEN=?", new String[]{sv});
        if (result == 0)
            return -1;
        return 1;
    }


}


