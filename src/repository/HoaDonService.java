/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import interfacee.hoaDonInterface;

import JDBCUtil.ConenctionProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.HoaDon;

/**
 *
 * @author duong
 */
public class HoaDonService implements hoaDonInterface {
    Connection con = ConenctionProvider.getConnection();

    public List<HoaDon> getAll() {

        try {
            List<HoaDon> list = new ArrayList<>();
            String sql = "SELECT * FROM HOADON";
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setId(rs.getInt("id"));
                hd.setIDKhachHang(rs.getInt("idkh"));
                hd.setIdUser(rs.getInt("idNV"));
                hd.setMa(rs.getString("Ma"));
                hd.setNgayThanhToan(rs.getDate("NgayThanhToan"));
                hd.setTinhTrang(rs.getInt("TinhTrang"));
                hd.setGhichu(rs.getString("ghiChu"));
                hd.setTongTien(rs.getDouble("TongTien"));
                hd.setNgayTao(rs.getDate("NgayTao"));
                list.add(hd);

            }
            Collections.reverse(list); 
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
