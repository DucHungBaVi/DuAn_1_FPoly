/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import JDBCUtil.ConenctionProvider;
import interfacee.ThuongHieuService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ThuongHieu;

/**
 *
 * @author duong
 */
public class ThuongHieuServiceImpl implements ThuongHieuService{
    private Connection con = ConenctionProvider.getConnection();
    @Override
    public List<ThuongHieu> getAll() {
        try {
            String SQL = "SELECT * FROM ThuongHieu";
            
            Statement st =  con.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            List<ThuongHieu> List = new ArrayList<>();
            while(rs.next()){
            ThuongHieu cl = new ThuongHieu();
            cl.setId(rs.getInt(1));
            cl.setTen(rs.getString(2));
            
            List.add(cl);
            }
            return List;
        } catch (Exception e) {
        }
        return null;
    }
    
}
