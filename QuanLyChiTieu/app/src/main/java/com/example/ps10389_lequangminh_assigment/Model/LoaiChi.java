package com.example.ps10389_lequangminh_assigment.Model;

public class LoaiChi {
    int _idLoaiChi;
    String tenLoaiChi;

    public LoaiChi() {
    }

    public LoaiChi(String tenLoaiChi) {
        this._idLoaiChi = _idLoaiChi;
        this.tenLoaiChi = tenLoaiChi;
    }

    public int get_idLoaiChi() {
        return _idLoaiChi;
    }

    public void set_idLoaiChi(int _idLoaiChi) {
        this._idLoaiChi = _idLoaiChi;
    }

    public String getTenLoaiChi() {
        return tenLoaiChi;
    }

    public void setTenLoaiChi(String tenLoaiChi) {
        this.tenLoaiChi = tenLoaiChi;
    }

    @Override
    public String toString() {
        return tenLoaiChi;
    }
}
