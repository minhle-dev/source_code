package com.example.ps10389_lequangminh_assigment.Model;

public class LoaiThu {
    int _idLoaiThu;
    String tenLoaiThu;

    public LoaiThu() {
    }

    public LoaiThu(String tenLoaiThu) {
        this._idLoaiThu = _idLoaiThu;
        this.tenLoaiThu = tenLoaiThu;
    }

    public int get_idLoaiThu() {
        return _idLoaiThu;
    }

    public void set_idLoaiThu(int _idLoaiThu) {
        this._idLoaiThu = _idLoaiThu;
    }

    public String getTenLoaiThu() {
        return tenLoaiThu;
    }

    public void setTenLoaiThu(String tenLoaiThu) {
        this.tenLoaiThu = tenLoaiThu;
    }

    @Override
    public String toString() {
        return tenLoaiThu;
    }
}
