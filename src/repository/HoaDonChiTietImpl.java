/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import interfacee.HoaDonChiTietInterface;
import java.sql.*;
import model.HoaDonChiTiet;
import JDBCUtil.ConenctionProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.HoaDon;
import model.SanPhamChiTiet;

/**
 *
 * @author trant
 */
public class HoaDonChiTietImpl implements HoaDonChiTietInterface {

    Connection conn = ConenctionProvider.getConnection();

    @Override
    public List<HoaDonChiTiet> getAllHDCT() {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM HoaDonChiTiet";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                SanPhamChiTiet spct = new SanPhamChiTiet();
                HoaDon hd = new HoaDon();
                spct.setId(rs.getString("IDCTSP"));
                hd.setId(rs.getInt("IDHD"));
                hdct.setHaoDon(hd);
                hdct.setSanPham(spct);
                hdct.setDonGia(rs.getDouble("DonGia"));
                hdct.setSoluong(rs.getInt("soLuong"));
                list.add(hdct);
            }
            Collections.reverse(list);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

   
}
