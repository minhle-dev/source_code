package com.example.ps10389_lequangminh_assigment.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ps10389_lequangminh_assigment.Database.DbHelper;
import com.example.ps10389_lequangminh_assigment.Model.KhoanChi;
import com.example.ps10389_lequangminh_assigment.Model.KhoanThu;

import java.util.ArrayList;
import java.util.List;

public class KhoanChiDAO {
    SQLiteDatabase database,database2;
    String TB="TB";
    public KhoanChiDAO(Context context){
        DbHelper dbHelper =new DbHelper(context);
        database=dbHelper.Open();
        database2 =dbHelper.getReadableDatabase();
    }

    public List<KhoanChi> getKhoanChi(){

        List<KhoanChi> list=new ArrayList<>();

        String TruyVan=" SELECT * FROM " + DbHelper.TB_KHOANCHI;

        Cursor cursor=database.rawQuery(TruyVan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            KhoanChi khoanChi=new KhoanChi();
            khoanChi.set_Id(cursor.getInt(cursor.getColumnIndex(DbHelper.TB_KHOANCHI_ID)));
            khoanChi.setNgayChi(cursor.getString(cursor.getColumnIndex(DbHelper.TB_KHOANCHI_NGAY)));
            khoanChi.setLoaiChi(cursor.getString(cursor.getColumnIndex(DbHelper.TB_KHOANCHI_LOAICHI)));
            khoanChi.setTenKhoanChi(cursor.getString(cursor.getColumnIndex(DbHelper.TB_KHOANCHI_TENKHOANCHI)));
            khoanChi.setSoTienChi(cursor.getString(cursor.getColumnIndex(DbHelper.TB_KHOANCHI_SOTIEN)));
            list.add(khoanChi);
            cursor.moveToNext();
        }
        return list;
    }

    public long themKhoanChi(KhoanChi khoanChi)
    {
        ContentValues contentValues=new ContentValues();

        contentValues.put(DbHelper.TB_KHOANCHI_NGAY,khoanChi.getNgayChi());
        contentValues.put(DbHelper.TB_KHOANCHI_SOTIEN,khoanChi.getSoTienChi());
        contentValues.put(DbHelper.TB_KHOANCHI_LOAICHI,khoanChi.getLoaiChi());
        contentValues.put(DbHelper.TB_KHOANCHI_TENKHOANCHI,khoanChi.getTenKhoanChi());
        long check=database.insert(DbHelper.TB_KHOANCHI,null,contentValues);
        Log.d(TB, String.valueOf(check));
        return check;
    }

    public boolean xoaItemKhoanChi(String id) {

        long check = database.delete(DbHelper.TB_KHOANCHI, DbHelper.TB_KHOANCHI_ID + " =? ", new String[]{String.valueOf(id)});
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean UpdateKhoanChi(KhoanChi khoanChi) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.TB_KHOANCHI_NGAY,khoanChi.getNgayChi());
        contentValues.put(DbHelper.TB_KHOANCHI_SOTIEN,khoanChi.getSoTienChi());
        contentValues.put(DbHelper.TB_KHOANCHI_TENKHOANCHI,khoanChi.getTenKhoanChi());
        contentValues.put(DbHelper.TB_KHOANCHI_LOAICHI,khoanChi.getLoaiChi());
        long check = database.update(DbHelper.TB_KHOANCHI,contentValues,DbHelper.TB_KHOANCHI_LOAICHI + "=?" ,new String[]{String.valueOf(khoanChi.getLoaiChi())});
        if (check != 0) {
            return true;
        } else {
            return false;
        }
    }

    //TINH TONG TIEN KHOAN Chi
    public int SumTienKhoanChi(){
        String truyvan = "SELECT SUM(SOTIEN) AS TONGTIENCHI FROM "+DbHelper.TB_KHOANCHI;

        Cursor cursor = database2.rawQuery(truyvan, null);

        int sotien = 0;
        while (cursor.moveToNext()){
            sotien = cursor.getInt(cursor.getColumnIndex("TONGTIENCHI"));
        }

        return sotien;
    }

}
