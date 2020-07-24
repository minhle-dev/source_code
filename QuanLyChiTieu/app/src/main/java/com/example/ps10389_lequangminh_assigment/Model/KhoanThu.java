package com.example.ps10389_lequangminh_assigment.Model;

public class KhoanThu {
    int _Id;
    String SoTienThu;
    String TenKhoanThu;
    String NgayThu;
    String LoaiThu;

    public KhoanThu() {
    }

    public KhoanThu(int _Id,  String soTienThu, String tenKhoanThu, String ngayThu, String loaiThu) {
        this._Id = _Id;

        SoTienThu = soTienThu;
        TenKhoanThu = tenKhoanThu;
        NgayThu = ngayThu;
        LoaiThu = loaiThu;
    }

    public int get_Id() {
        return _Id;
    }

    public void set_Id(int _Id) {
        this._Id = _Id;
    }


    public  String getSoTienThu() {
        return SoTienThu;
    }

    public void setSoTienThu( String soTienThu) {
        SoTienThu = soTienThu;
    }

    public String getTenKhoanThu() {
        return TenKhoanThu;
    }

    public void setTenKhoanThu(String tenKhoanThu) {
        TenKhoanThu = tenKhoanThu;
    }

    public String getNgayThu() {
        return NgayThu;
    }

    public void setNgayThu(String ngayThu) {
        NgayThu = ngayThu;
    }

    public String getLoaiThu() {
        return LoaiThu;
    }

    public void setLoaiThu(String loaiThu) {
        LoaiThu = loaiThu;
    }
}
