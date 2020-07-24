package com.example.ps10389_lequangminh_mob201_assignment.model;

public class LopHoc {

    String maLop;
    String tenLop;

    public LopHoc() {
    }

    public LopHoc(String maLop, String tenLop) {
        this.maLop = maLop;
        this.tenLop = tenLop;

    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }



    @Override
    public String toString() {
        return "LopHoc{" +
                "maLop='" + maLop + '\'' +
                ", tenLop='" + tenLop + '\'' +
                '}';
    }
}

