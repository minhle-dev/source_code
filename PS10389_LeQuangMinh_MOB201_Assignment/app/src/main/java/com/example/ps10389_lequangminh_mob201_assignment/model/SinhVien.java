package com.example.ps10389_lequangminh_mob201_assignment.model;

public class SinhVien {
    String idSinhVien;
    String tenSinhVien;
    String ngaySinh;
    String maLop;

    public SinhVien() {
    }

    public SinhVien(  String idSinhVien,String tenSinhVien, String ngaySinh, String maLop) {
        this.tenSinhVien = tenSinhVien;
        this.ngaySinh = ngaySinh;
        this.maLop = maLop;
        this.idSinhVien = idSinhVien;
    }

    public String getIdSinhVien() {
        return idSinhVien;
    }

    public void setIdSinhVien(String idSinhVien) {
        this.idSinhVien = idSinhVien;
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

    @Override
    public String toString() {
        return "SinhVien{" +
                "idSinhVien=" + idSinhVien +
                ", tenSinhVien='" + tenSinhVien + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", maLop='" + maLop + '\'' +
                '}';
    }
}
