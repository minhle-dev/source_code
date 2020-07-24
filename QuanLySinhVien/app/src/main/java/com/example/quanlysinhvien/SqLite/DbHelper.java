package com.example.quanlysinhvien.SqLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public final static String DBNAME="QUANLYSINHVIEN";
    public final static int DBVERSION=6;



    public DbHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String lenhSql= "CREATE TABLE  LOPHOC( " +
                        "MALOP TEXT PRIMARY KEY NOT NULL UNIQUE, "+
                        "TENLOP TEXT NOT NULL)";
        db.execSQL(lenhSql);

        String lenhSql1= "CREATE TABLE  SINHVIEN( " +
                "MASINHVIEN INTEGER PRIMARY KEY autoincrement, "+
                "TENSINHVIEN TEXT NOT NULL, "+
                "NGAYSINH TEXT NOT NULL, "+
                "MALOP TEXT, FOREIGN KEY (MALOP) REFERENCES LOPHOC(MALOP))";
        //vì sinh viên có thể không có lớp nào nên khỏi not null
        db.execSQL(lenhSql1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE LOPHOC";
        db.execSQL(sql);

        String sql1="DROP TABLE SINHVIEN";
        db.execSQL(sql1);

        onCreate(db);
    }
}
