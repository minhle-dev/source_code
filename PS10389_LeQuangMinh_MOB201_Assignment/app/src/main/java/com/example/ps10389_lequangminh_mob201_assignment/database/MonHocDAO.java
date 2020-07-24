package com.example.ps10389_lequangminh_mob201_assignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ps10389_lequangminh_mob201_assignment.model.MonHoc;
import com.example.ps10389_lequangminh_mob201_assignment.model.SinhVien;

import java.util.ArrayList;

public class MonHocDAO {
    SQLiteDatabase db;
    public static final String TAG = "mhDAO";

    public MonHocDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<MonHoc> getMHbyMaLop(String MALOP) {
        ArrayList<MonHoc> xemqlsv = new ArrayList<>();
        Cursor c = db.query("MONHOC", null, "MALOP=?", new String[]{MALOP}, null, null, null);
        while (c.moveToNext()) {
            MonHoc mh = new MonHoc();
            mh.setMaMonHoc(c.getString(c.getColumnIndex("IDMONHOC")));
            mh.setTenMonHoc(c.getString(c.getColumnIndex("TENMONHOC")));
            mh.setLichHoc(c.getString(c.getColumnIndex("LICHHOC")));
            mh.setMaLop(c.getString(c.getColumnIndex("MALOP")));

            xemqlsv.add(mh);
        }
        return xemqlsv;
    }

    public ArrayList<MonHoc> getAllMonHoc() {
        ArrayList<MonHoc> xemqlsv = new ArrayList<>();
        Cursor c = db.query("MONHOC", null, null, null, null, null, null);
        while (c.moveToNext()) {
            MonHoc mh = new MonHoc();
            mh.setMaMonHoc(c.getString(c.getColumnIndex("IDMONHOC")));
            mh.setTenMonHoc(c.getString(c.getColumnIndex("TENMONHOC")));
            mh.setLichHoc(c.getString(c.getColumnIndex("LICHHOC")));
            mh.setMaLop(c.getString(c.getColumnIndex("MALOP")));

            xemqlsv.add(mh);
        }
        return xemqlsv;
    }


    public int ThemMonHoc(MonHoc mh) {
        ContentValues values = new ContentValues();
        values.put("IDMONHOC",mh.getMaMonHoc());
        values.put("TENMONHOC", mh.getTenMonHoc());
        values.put("LICHHOC", mh.getLichHoc());
        values.put("MALOP", mh.getMaLop());
        try {
            if (db.insert("MONHOC", null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;


    }

    public int SuaMonHoc(MonHoc mh) {
        ContentValues values = new ContentValues();
        values.put("IDMONHOC",mh.getMaMonHoc());
        values.put("TENMONHOC", mh.getTenMonHoc());
        values.put("LICHHOC", mh.getLichHoc());
        values.put("MALOP", mh.getMaLop());
        int result = db.update("MONHOC", values, "IDMONHOC=?", new String[]{mh.getMaMonHoc()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int XoaMonHoc(String mh) {
        int result = db.delete("MONHOC", "IDMONHOC=?", new String[]{mh});
        if (result == 0)
            return -1;
        return 1;
    }


}


