package com.example.ps10389_lequangminh_assigment.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    //LOAI THU
    public static final String TB_LOAITHU="LOAITHU";
    public static final String TB_LOAITHU_ID="ID";
    public static final String TB_LOAITHU_TENLOAITHU="TENLOAITHU";
    //KHOAN THU
    public static final String TB_KHOANTHU="KHOANTHU";
    public static final String TB_KHOANTHU_ID="ID";
    public static final String TB_KHOANTHU_NGAY="NGAY";
    public static final String TB_KHOANTHU_SOTIEN="SOTIEN";
    public static final String TB_KHOANTHU_LOAITHU="LOAITHUKHOANTHU";
    public static final String TB_KHOANTHU_TENKHOANTHU="TENKHOANTHU";
    //LOAICHI
    public static final String TB_LOAICHI="LOAICHI";
    public static final String TB_LOAICHI_ID="ID";
    public static final String TB_LOAICHI_TENLOAICHI="TENLOAICHI";
    //KHOAN CHI
    public static final String TB_KHOANCHI="KHOANCHI";
    public static final String TB_KHOANCHI_ID="ID";
    public static final String TB_KHOANCHI_NGAY="NGAY";
    public static final String TB_KHOANCHI_SOTIEN="SOTIEN";
    public static final String TB_KHOANCHI_LOAICHI="LOAICHIKHOANCHI";
    public static final String TB_KHOANCHI_TENKHOANCHI="TENKHOANCHI";
    //TEN DATABASE
    public final static String DBNAME="QUANLYCHITIEU";
    public final static int DBVERSION=21;
    //

    public DbHelper( Context context) {
        super(context,DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_Loaithu = "CREATE TABLE " + TB_LOAITHU + " ( " + TB_LOAITHU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_LOAITHU_TENLOAITHU + " TEXT ) ";
        db.execSQL(tb_Loaithu);
        //---------------------------------------//
        String tb_Khoanthu = "CREATE TABLE " + TB_KHOANTHU + " ( " + TB_KHOANTHU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_KHOANTHU_NGAY + " TEXT, "
                + TB_KHOANTHU_SOTIEN + "  INTEGER,"
                + TB_KHOANTHU_TENKHOANTHU + " TEXT, "
                + TB_KHOANTHU_LOAITHU + " TEXT ) ";
        db.execSQL(tb_Khoanthu);
        //---------------------------------------//
        String tb_Loaichi = "CREATE TABLE " + TB_LOAICHI + " ( " + TB_LOAICHI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_LOAICHI_TENLOAICHI + " TEXT ) ";
        db.execSQL(tb_Loaichi);
        //---------------------------------------//
        String tb_Khoanchi = "CREATE TABLE " + TB_KHOANCHI + " ( " + TB_KHOANCHI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_KHOANCHI_NGAY + " TEXT, "
                + TB_KHOANCHI_SOTIEN + " INTEGER, "
                + TB_KHOANCHI_TENKHOANCHI + " TEXT, "
                + TB_KHOANCHI_LOAICHI + " TEXT)";
        db.execSQL(tb_Khoanchi);
        //---------------------------------------//

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TB_LOAITHU);
        db.execSQL("DROP TABLE IF EXISTS "+ TB_LOAICHI);
        db.execSQL("DROP TABLE IF EXISTS "+ TB_KHOANCHI);
        db.execSQL("DROP TABLE IF EXISTS "+ TB_KHOANTHU);

    }

    public SQLiteDatabase Open(){
        return this.getWritableDatabase();
    }


}
