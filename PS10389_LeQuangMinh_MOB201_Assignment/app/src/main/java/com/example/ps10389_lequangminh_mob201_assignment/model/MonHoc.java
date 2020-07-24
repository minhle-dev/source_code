package com.example.ps10389_lequangminh_mob201_assignment.model;

public class MonHoc {
    String maMonHoc;
    String tenMonHoc;
    String lichHoc;
    String maLop;

    public MonHoc() {
    }

    public MonHoc(String maMonHoc, String tenMonHoc, String lichHoc, String maLop) {
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.lichHoc = lichHoc;
        this.maLop = maLop;
    }

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public String getLichHoc() {
        return lichHoc;
    }

    public void setLichHoc(String lichHoc) {
        this.lichHoc = lichHoc;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }
}
