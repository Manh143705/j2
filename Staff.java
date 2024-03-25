/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package j2;

/**
 *
 * @author ADMIN
 */
public class Staff {
    private String maNV;
    private String tenNV;
    private Boolean gioiTinh;
    private int thamNien;

    public Staff(String maNV,String tenNV,boolean gioiTinh,int thamNien) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.thamNien = thamNien;
    }

   

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setThamNien(int thamNien) {
        this.thamNien = thamNien;
    }

    public int getThamNien() {
        return thamNien;
    }
    
    public int tinhThuong() {
        if (thamNien > 12) {
            return 500000;
        } else {
            return 200000;
        }
    }
    public String getGender() {
    return gioiTinh ? "Nam" : "Nữ";
}  
     public void setGender(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public boolean isGender() {
    return this.gioiTinh;
}

  
}
