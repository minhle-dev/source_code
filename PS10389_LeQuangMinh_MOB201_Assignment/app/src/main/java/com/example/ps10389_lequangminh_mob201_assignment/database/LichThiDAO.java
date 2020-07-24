package com.example.ps10389_lequangminh_mob201_assignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ps10389_lequangminh_mob201_assignment.model.LichThi;
import com.example.ps10389_lequangminh_mob201_assignment.model.MonHoc;

import java.util.ArrayList;

public class LichThiDAO {
    SQLiteDatabase db;
    public static final String TAG = "ltDAO";

    public LichThiDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<LichThi> getMHbyMaMonHoc(String TENMONHOC) {
        ArrayList<LichThi> xemqlsv = new ArrayList<>();
        Cursor c = db.query("LICHTHI", null, "TENMONHOC=?", new String[]{TENMONHOC}, null, null, null);
        while (c.moveToNext()) {
            LichThi mh = new LichThi();
            mh.setIdLichThi(c.getString(c.getColumnIndex("IDLICHTHI")));
            mh.setTenMonHoc(c.getString(c.getColumnIndex("TENMONHOC")));
            mh.setLichThi(c.getString(c.getColumnIndex("LICHTHI")));
            mh.setGhiChu(c.getString(c.getColumnIndex("GHICHU")));

            xemqlsv.add(mh);
        }
        return xemqlsv;
    }

    public ArrayList<LichThi> getAllLICHTHI() {
        ArrayList<LichThi> xemqlsv = new ArrayList<>();
        Cursor c = db.query("LICHTHI", null, null, null, null, null, null);
        while (c.moveToNext()) {
            LichThi mh = new LichThi();
            mh.setIdLichThi(c.getString(c.getColumnIndex("IDLICHTHI")));
            mh.setTenMonHoc(c.getString(c.getColumnIndex("TENMONHOC")));
            mh.setLichThi(c.getString(c.getColumnIndex("LICHTHI")));
            mh.setGhiChu(c.getString(c.getColumnIndex("GHICHU")));

            xemqlsv.add(mh);
        }
        return xemqlsv;
    }


    public int ThemLichThi(LichThi mh) {
        ContentValues values = new ContentValues();
        values.put("IDLICHTHI",mh.getIdLichThi());
        values.put("TENMONHOC", mh.getTenMonHoc());
        values.put("LICHTHI", mh.getLichThi());
        values.put("GHICHU", mh.getGhiChu());
        try {
            if (db.insert("LICHTHI", null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;


    }

    public int SuaLich(LichThi mh) {
        ContentValues values = new ContentValues();
        values.put("IDLICHTHI",mh.getIdLichThi());
        values.put("TENMONHOC", mh.getTenMonHoc());
        values.put("LICHTHI", mh.getLichThi());
        values.put("GHICHU", mh.getGhiChu());
        int result = db.update("LICHTHI", values, "IDLICHTHI=?", new String[]{mh.getIdLichThi()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int XoaLichThi(String mh) {
        int result = db.delete("LICHTHI", "IDLICHTHI=?", new String[]{mh});
        if (result == 0)
            return -1;
        return 1;
    }


}


