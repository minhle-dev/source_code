package com.example.quanlysinhvien.Model;

public class SinhVien {
    int maSinhVien;
    String tenSinhVien;
    String ngaySinh;
    String maLop;

    public SinhVien() {
    }

    public SinhVien(int maSinhVien, String tenSinhVien, String ngaySinh, String maLop) {
        this.maSinhVien = maSinhVien;
        this.tenSinhVien = tenSinhVien;
        this.ngaySinh = ngaySinh;
        this.maLop = maLop;
    }

    public int getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(int maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getTenSinhVien() {
        return tenSinhVien;
    }

    public void setTenSinhVien(String tenSinhVien) {
        this.tenSinhVien = tenSinhVien;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }
}
