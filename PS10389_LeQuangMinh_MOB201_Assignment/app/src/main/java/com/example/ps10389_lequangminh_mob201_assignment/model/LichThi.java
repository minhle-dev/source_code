package com.example.ps10389_lequangminh_mob201_assignment.model;

public class LichThi {
    String idLichThi;
    String ghiChu;
    String lichThi;
    String tenMonHoc;

    public LichThi() {
    }



    public LichThi(  String idLichThi, String ghiChu, String lichThi, String tenMonHoc) {
        this.idLichThi = idLichThi;
        this.ghiChu = ghiChu;
        this.lichThi = lichThi;
        this.tenMonHoc = tenMonHoc;
    }

    public String getIdLichThi() {
        return idLichThi;
    }

    public void setIdLichThi(String idLichThi) {
        this.idLichThi = idLichThi;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getLichThi() {
        return lichThi;
    }

    public void setLichThi(String lichThi) {
        this.lichThi = lichThi;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }
}
