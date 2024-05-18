/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

/**
 *
 * @author duong
 */
public class KhuyenMai {
    private int id;
    private String tenKhuyenMai;
    private String hinhThucKM;
    private String giaTriGiam;
    private int soLuong;
    private String codeKhuyenMai;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    public void setTenKhuyenMai(String tenKhuyenMai) {
        this.tenKhuyenMai = tenKhuyenMai;
    }

    public String getHinhThucKM() {
        return hinhThucKM;
    }

    public void setHinhThucKM(String hinhThucKM) {
        this.hinhThucKM = hinhThucKM;
    }
    
    public String getGiaTriGiam() {
        return giaTriGiam;
    }

    public void setGiaTriGiam(String giaTriGiam) {
        this.giaTriGiam = giaTriGiam;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

   

    public class KMData {

        private static List<String> data = new ArrayList<>();

        public static List<String> getdata() {
            data.add("%");
            data.add("VND");
            return data;
        }
    }
}
