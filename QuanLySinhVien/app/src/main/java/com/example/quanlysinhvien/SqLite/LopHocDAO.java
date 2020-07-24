package com.example.quanlysinhvien.SqLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlysinhvien.Model.LopHoc;
import com.example.quanlysinhvien.Model.SinhVien;

import java.util.ArrayList;
import java.util.List;

public class LopHocDAO {

    SQLiteDatabase db;

    public LopHocDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    public ArrayList<LopHoc> xemDSLop(){
        ArrayList<LopHoc> xemdanhsach=new ArrayList<>();
        Cursor c=db.query("LOPHOC",null,null,null,null,null,null);
        while (c.moveToNext())
        {
            LopHoc lop =new LopHoc();
            lop.setMaLop(c.getString(c.getColumnIndex("MALOP")));
            lop.setTenLop(c.getString(c.getColumnIndex("TENLOP")));

            xemdanhsach.add(lop);
        }
        return xemdanhsach;
    }


    public long ThemLop(LopHoc lh)
    {
        ContentValues values = new ContentValues();
        values.put("MALOP",lh.getMaLop());
        values.put("TENLOP",lh.getTenLop());

        return db.insert("LOPHOC", null,values);

    }
    public int SuaLop(LopHoc lh){
        ContentValues values = new ContentValues();
        values.put("MALOP",lh.getMaLop());
        values.put("TENLOP",lh.getTenLop());

        return db.update("LOPHOC",values,"MALOP=?",new String[]{lh.getMaLop()});
    }

    public  int XoaLop(LopHoc lh){
        return  db.delete("LOPHOC","MALOP=?",new String[]{lh.getMaLop()});
    }

    public List<LopHoc> getMaLop(){
        List<LopHoc> dsLop = new ArrayList<>();
        String sql2 = "SELECT MALOP, TENLOP FROM LOPHOC";
        Cursor c = db.rawQuery(sql2, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            LopHoc malophoc = new LopHoc();
            malophoc.setMaLop(c.getString(c.getColumnIndex("MALOP")));
            malophoc.setTenLop(c.getString(c.getColumnIndex("TENLOP")));
            dsLop.add(malophoc);
            c.moveToNext();
        }
        c.close();
        return dsLop;
    }



}


