package com.example.ps10389_lequangminh_assigment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ps10389_lequangminh_assigment.Database.DbHelper;
import com.example.ps10389_lequangminh_assigment.Model.LoaiChi;
import com.example.ps10389_lequangminh_assigment.Model.LoaiThu;

import java.util.ArrayList;
import java.util.List;

public class LoaiThuDAO {
    SQLiteDatabase database, database1;
    String TB = "TB";

    public LoaiThuDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.Open();
        database1 =dbHelper.getReadableDatabase();
    }


    public List<LoaiThu> getLoaiThu() {

        List<LoaiThu> list = new ArrayList<>();

        String TruyVan1 = " SELECT * FROM " + DbHelper.TB_LOAITHU;

        Cursor cursor = database.rawQuery(TruyVan1, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LoaiThu loaiThu = new LoaiThu();
            loaiThu.set_idLoaiThu(cursor.getInt(cursor.getColumnIndex(DbHelper.TB_LOAITHU_ID)));
            loaiThu.setTenLoaiThu(cursor.getString(cursor.getColumnIndex(DbHelper.TB_LOAITHU_TENLOAITHU)));
            list.add(loaiThu);
            cursor.moveToNext();
        }
        return list;
    }

    public long AddItem(LoaiThu loaiThu) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbHelper.TB_LOAITHU_TENLOAITHU, loaiThu.getTenLoaiThu());
        long check = database.insert(DbHelper.TB_LOAITHU, null, contentValues);
        Log.d(TB, String.valueOf(check));
        return check;
    }

    public boolean UpdateLoaiThu(LoaiThu loaiThu) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.TB_LOAITHU_TENLOAITHU, loaiThu.getTenLoaiThu());
        long check = database.update(DbHelper.TB_LOAITHU, contentValues, DbHelper.TB_LOAITHU_ID + " = " + loaiThu.get_idLoaiThu(), null);
        if (check != 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean deleteItem(String id) {

        long check = database.delete(DbHelper.TB_LOAITHU, DbHelper.TB_LOAITHU_ID + " =? ", new String[]{String.valueOf(id)});
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }


}

