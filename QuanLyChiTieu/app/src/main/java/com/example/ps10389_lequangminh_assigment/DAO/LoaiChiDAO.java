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

public class LoaiChiDAO {
    SQLiteDatabase database,database1;
    String TB="TB";
    public LoaiChiDAO(Context context)
    {
        DbHelper dbHelper=new DbHelper(context);
        database=dbHelper.Open();
        database1=dbHelper.getReadableDatabase();
    }


    public List<LoaiChi> getLoaiChi(){

        List<LoaiChi> list=new ArrayList<>();

        String TruyVan=" SELECT * FROM " + DbHelper.TB_LOAICHI;
        Cursor cursor=database.rawQuery(" SELECT * FROM " + DbHelper.TB_LOAICHI,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiChi loaiChi=new LoaiChi();
            loaiChi.set_idLoaiChi(cursor.getInt(cursor.getColumnIndex(DbHelper.TB_LOAICHI_ID)));
            loaiChi.setTenLoaiChi(cursor.getString(cursor.getColumnIndex(DbHelper.TB_LOAICHI_TENLOAICHI)));
            list.add(loaiChi);
            cursor.moveToNext();
        }
        return list;
    }


    public long AddItem(LoaiChi loaiChi)
    {
        ContentValues contentValues=new ContentValues();

        contentValues.put(DbHelper.TB_LOAICHI_TENLOAICHI,loaiChi.getTenLoaiChi());
        long check=database.insert(DbHelper.TB_LOAICHI,null,contentValues);
        Log.d(TB, String.valueOf(check));
        return check;
    }

    public boolean UpdateLoaiChi(LoaiChi loaiChi){

        ContentValues contentValues=new ContentValues();

        contentValues.put(DbHelper.TB_LOAICHI_TENLOAICHI,(loaiChi.getTenLoaiChi()));

        long check=database.update(DbHelper.TB_LOAICHI,contentValues,DbHelper.TB_LOAICHI_ID + " = " +loaiChi.get_idLoaiChi(),null);

        if(check !=0){
            return true;
        }else {
            return false;
        }

    }
    public boolean deleteItemLoaiChi(String id){

        long check= database.delete(DbHelper.TB_LOAICHI,DbHelper.TB_LOAICHI_ID+"=?",new String[]{String.valueOf(id)});
        if (check !=0){
            return true;
        }
        else {
            return false;
        }
    }


}

