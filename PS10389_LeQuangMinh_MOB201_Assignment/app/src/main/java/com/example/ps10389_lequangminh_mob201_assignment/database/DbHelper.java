package com.example.ps10389_lequangminh_mob201_assignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public final static String DBNAME = "QUANLYSINHVIEN";
    public final static int DBVERSION = 12;


    public DbHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String lenhSql = "CREATE TABLE  LOPHOC( " +
                "TENLOP TEXT NOT NULL PRIMARY KEY ," +
                "MALOP TEXT NOT NULL)";
        db.execSQL(lenhSql);

        String lenhSql1 = "CREATE TABLE  SINHVIEN( " +
                "MASINHVIEN TEXT PRIMARY KEY NOT NULL, " +
                "TENSINHVIEN TEXT NOT NULL, " +
                "NGAYSINH TEXT NOT NULL, " +
                "MALOP TEXT, FOREIGN KEY (MALOP) REFERENCES LOPHOC(MALOP))";
        db.execSQL(lenhSql1);

        String lenhSql2 = "CREATE TABLE  MONHOC( " +
                "IDMONHOC TEXT PRIMARY KEY, " +
                "TENMONHOC TEXT NOT NULL," +
                "LICHHOC TEXT NOT NULL," +
                "MALOP TEXT NOT NULL)";
        db.execSQL(lenhSql2);
        String lenhSql3 = "CREATE TABLE  LICHTHI( " +
                "IDLICHTHI TEXT PRIMARY KEY, " +
                "LICHTHI TEXT NOT NULL," +
                "GHICHU TEXT NOT NULL," +
                "TENMONHOC TEXT NOT NULL)";
        db.execSQL(lenhSql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE LOPHOC";
        db.execSQL(sql);

        String sql1 = "DROP TABLE SINHVIEN";
        db.execSQL(sql1);

        String sql2 = "DROP TABLE MONHOC";
        db.execSQL(sql2);

        String sql3 = "DROP TABLE LICHTHI";
        db.execSQL(sql3);


        onCreate(db);
    }
}
