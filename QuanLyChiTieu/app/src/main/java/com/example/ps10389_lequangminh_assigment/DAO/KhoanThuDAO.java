package com.example.ps10389_lequangminh_assigment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ps10389_lequangminh_assigment.Database.DbHelper;
import com.example.ps10389_lequangminh_assigment.Model.KhoanThu;

import java.util.ArrayList;
import java.util.List;

public class KhoanThuDAO {
    SQLiteDatabase database, database1;
    String TB = "TB";

    public KhoanThuDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.Open();
        database1 = dbHelper.getReadableDatabase();
    }

    public List<KhoanThu> getKhoanThu() {

        List<KhoanThu> list = new ArrayList<>();
        String TruyVan = " SELECT * FROM " + DbHelper.TB_KHOANTHU;

        Cursor cursor = database.rawQuery(TruyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            KhoanThu khoanThu = new KhoanThu();
            khoanThu.set_Id(cursor.getInt(cursor.getColumnIndex(DbHelper.TB_KHOANTHU_ID)));
            khoanThu.setNgayThu(cursor.getString(cursor.getColumnIndex(DbHelper.TB_KHOANTHU_NGAY)));
            khoanThu.setLoaiThu(cursor.getString(cursor.getColumnIndex(DbHelper.TB_KHOANTHU_LOAITHU)));
            khoanThu.setTenKhoanThu(cursor.getString(cursor.getColumnIndex(DbHelper.TB_KHOANTHU_TENKHOANTHU)));
            khoanThu.setSoTienThu(cursor.getString(cursor.getColumnIndex(DbHelper.TB_KHOANTHU_SOTIEN)));

            list.add(khoanThu);
            cursor.moveToNext();
        }
        return list;
    }


    public long AddKhoanThu(KhoanThu khoanThu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.TB_KHOANTHU_NGAY, khoanThu.getNgayThu());
        contentValues.put(DbHelper.TB_KHOANTHU_SOTIEN, khoanThu.getSoTienThu());
        contentValues.put(DbHelper.TB_KHOANTHU_LOAITHU, khoanThu.getLoaiThu());
        contentValues.put(DbHelper.TB_KHOANTHU_TENKHOANTHU, khoanThu.getTenKhoanThu());
        long check = database.insert(DbHelper.TB_KHOANTHU, null, contentValues);
        Log.d(TB, String.valueOf(check));
        return check;
    }

    public boolean xoaItemKhoanThu(String id) {

        long check = database.delete(DbHelper.TB_KHOANTHU, DbHelper.TB_KHOANTHU_ID + " =? ", new String[]{String.valueOf(id)});
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean UpdateKhoanThu(KhoanThu khoanThu) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.TB_KHOANTHU_NGAY, khoanThu.getNgayThu());
        contentValues.put(DbHelper.TB_KHOANTHU_SOTIEN, khoanThu.getSoTienThu());
        contentValues.put(DbHelper.TB_KHOANTHU_LOAITHU, khoanThu.getLoaiThu());
        contentValues.put(DbHelper.TB_KHOANTHU_TENKHOANTHU, khoanThu.getTenKhoanThu());
        long check = database.update(DbHelper.TB_KHOANTHU, contentValues, DbHelper.TB_KHOANTHU_LOAITHU + "=?", new String[]{String.valueOf(khoanThu.getLoaiThu())});
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }


    //TINH TONG TIEN KHOAN THU
    public int SumTienKhoanThu() {
        String truyvan = "SELECT SUM(SOTIEN) AS TONGTIENTHU FROM " + DbHelper.TB_KHOANTHU;

        Cursor cursor = database1.rawQuery(truyvan, null);

        int sotien = 0;
        while (cursor.moveToNext()) {
            sotien = cursor.getInt(cursor.getColumnIndex("TONGTIENTHU"));
        }

        return sotien;
    }

}
