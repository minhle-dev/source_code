package com.example.ps10389_lequangminh_mob201_assignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.ps10389_lequangminh_mob201_assignment.model.LopHoc;

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
       int result= db.update("LOPHOC",values,"MALOP=?",new String[]{lh.getMaLop()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public  int XoaLop(String lh){
       int result = db.delete("LOPHOC","MALOP=?",new String[]{lh});
        if (result == 0)
            return -1;
        return 1;
    }



}


