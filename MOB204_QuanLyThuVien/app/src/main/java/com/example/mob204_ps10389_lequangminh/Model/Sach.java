package com.example.mob204_ps10389_lequangminh.model;

public class Sach {
    private String maSach;
    private String maTheLoai;
    private String tenSach;
    private String tacGia;
    private String NXB;
    private Double giaBia;
    private String soLuong;

    public Sach() {
    }

    public Sach(String maSach, String maTheLoai, String tenSach, String tacGia, String NXB, Double giaBia, String soLuong) {
        this.maSach = maSach;
        this.maTheLoai = maTheLoai;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.NXB = NXB;
        this.giaBia = giaBia;
        this.soLuong = soLuong;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public Double getGiaBia() {
        return giaBia;
    }

    public void setGiaBia(Double giaBia) {
        this.giaBia = giaBia;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }
    @Override
    public String toString() {
        return "Sach{" +
                "maSach='" + maSach + '\'' +
                ", maTheLoai='" + maTheLoai + '\'' +
                ", tenSach='" + tenSach + '\'' +
                ", tacGia='" + tacGia + '\'' +
                ", NXB='" + NXB + '\'' +
                ", giaBia=" + giaBia +
                ", soLuong=" + soLuong +
                '}';
    }
}