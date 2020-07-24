package com.example.ps10389_lequangminh_assigment.Model;

public class KhoanChi {
    int _Id;
    String SoTienChi;
    String TenKhoanChi;
    String NgayChi;
    String LoaiChi;

    public KhoanChi() {
    }

    public KhoanChi(int _Id,   String soTienChi, String tenKhoanChi, String ngayChi, String loaiChi) {
        this._Id = _Id;
            SoTienChi = soTienChi;
        TenKhoanChi = tenKhoanChi;
        NgayChi = ngayChi;
        LoaiChi = loaiChi;
    }

    public int get_Id() {
        return _Id;
    }

    public void set_Id(int _Id) {
        this._Id = _Id;
    }


    public  String getSoTienChi() {
        return SoTienChi;
    }

    public void setSoTienChi(String soTienChi) {
        SoTienChi = soTienChi;
    }

    public String getTenKhoanChi() {
        return TenKhoanChi;
    }

    public void setTenKhoanChi(String tenKhoanChi) {
        TenKhoanChi = tenKhoanChi;
    }

    public String getNgayChi() {
        return NgayChi;
    }

    public void setNgayChi(String ngayChi) {
        NgayChi = ngayChi;
    }

    public String getLoaiChi() {
        return LoaiChi;
    }

    public void setLoaiChi(String loaiChi) {
        LoaiChi = loaiChi;
    }
}


