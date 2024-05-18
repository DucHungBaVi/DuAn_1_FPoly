/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import JDBCUtil.ConenctionProvider;
import java.util.List;
import model.KhuyenMai;
import repository.KhuyenMaiService_IMPL;
import java.sql.*;
import JDBCUtil.ConenctionProvider;
import interfacee.KhuyenMaiService;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhuyenMaiService_IMPL implements KhuyenMaiService {

    Connection con = ConenctionProvider.getConnection();

    @Override
    public List<KhuyenMai> getAll() {
        try {
            String sql = "select id,ten,hinhthuckm,giatrigiam,soluong,MaCode from KhuyenMai";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<KhuyenMai> list = new ArrayList<>();
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setId(rs.getInt(1));
                km.setTenKhuyenMai(rs.getString(2));
                km.setHinhThucKM(rs.getString(3));
                km.setGiaTriGiam(rs.getString(4));
                km.setSoLuong(rs.getInt(5));
                km.setCodeKhuyenMai(rs.getString("MaCode"));
                list.add(km);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    
   

}
