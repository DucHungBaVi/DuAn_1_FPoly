/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import interfacee.HoaDonChiTietInterface;
import interfacee.KhuyenMaiService;
import interfacee.ThuongHieuService;
import interfacee.hoaDonInterface;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.HoaDon;
import model.SanPhamChiTiet;
import model.ThuongHieu;
import repository.HoaDonChiTietImpl;
import repository.HoaDonService;
import repository.KhuyenMaiService_IMPL;
import repository.SanPhamChiTietService;
import repository.ThuongHieuServiceImpl;



/**
 *
 * @author duong
 */
public class BanHang extends javax.swing.JPanel {
   private hoaDonInterface hoadonService = (hoaDonInterface) new HoaDonService();
   private SanPhamChiTietService chiTietSpService = new SanPhamChiTietService();
   private HoaDonChiTietInterface hoaDonChiTietService = new HoaDonChiTietImpl();
   private ThuongHieuService thuongHieuService = new ThuongHieuServiceImpl();
   private KhuyenMaiService khuyenMaiService = new KhuyenMaiService_IMPL();
   private List<model.KhuyenMai> listKhuyenMai = khuyenMaiService.getAll();
   DecimalFormat decimalFormat = new DecimalFormat("#,###");

    /**
     * Creates new form BanHang
     */
    public BanHang() {
        initComponents();
        loadHoaDon();
        loadCategoryThuongHieu();
        DefaultTableModel giohangModel = (DefaultTableModel) tbl_gioHang1.getModel();
        giohangModel.setRowCount(0);
    }
    
    private void loadHoaDon() {
        List<HoaDon> list = hoadonService.getAll();
        DefaultTableModel model = (DefaultTableModel) tbl_hoadon1.getModel();
        model.setRowCount(0);

        for (int i = 0; i < list.size(); i++) {
            HoaDon hoaDon = list.get(i);
            if (hoaDon.getTinhTrang() == 0) {
                model.addRow(new Object[]{
                    hoaDon.getId(), hoaDon.getMa(), hoaDon.getIDKhachHang() == 0 ,
                    hoaDon.getTongTien(), hoaDon.getTinhTrang() == 0 ? "Chưa Thanh Toán" : "Đã Thanh Toán", hoaDon.getNgayTao()
                });
            }
        }
    }
    
    
     private SanPhamChiTiet getSPCTbyIDSP(String id) {
        List<SanPhamChiTiet> listSPCT = chiTietSpService.getAll();
        for (SanPhamChiTiet sanPhamChiTiet : listSPCT) {
            if (sanPhamChiTiet.getId().equalsIgnoreCase(id)) {
                SanPhamChiTiet spct = new SanPhamChiTiet();
                spct.setId(sanPhamChiTiet.getId());
                spct.setTenSp(sanPhamChiTiet.getTenSp());
                spct.setSoLuongTon(sanPhamChiTiet.getSoLuongTon());
                spct.setGiaBan(sanPhamChiTiet.getGiaBan());
                spct.setGiaNhap(sanPhamChiTiet.getGiaNhap());
                spct.setNhaSx(sanPhamChiTiet.getNhaSx());
                spct.setChatLieu(sanPhamChiTiet.getChatLieu());
                spct.setKhuyenMai(sanPhamChiTiet.getKhuyenMai());
                spct.setMauSac(sanPhamChiTiet.getMauSac());
                spct.setThuongHieu(sanPhamChiTiet.getThuongHieu());
                spct.setMoTa(sanPhamChiTiet.getMoTa());
                spct.setKichCo(sanPhamChiTiet.getKichCo());
                return spct;
            }

        }
        return null;
    }
    
    private void loadGioHangByIDHD(int idHD) {
        List<SanPhamChiTiet> listSPCTLoad = new ArrayList<>();
        List<Integer> listSoLuong = new ArrayList<>();

        for (model.HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietService.getAllHDCT()) {
            if (hoaDonChiTiet.getHaoDon().getId() == idHD) {
                listSPCTLoad.add(getSPCTbyIDSP(hoaDonChiTiet.getSanPham().getId()));
                listSoLuong.add(hoaDonChiTiet.getSoluong());
            }
        }

        DefaultTableModel model = (DefaultTableModel) tbl_gioHang1.getModel();
        model.setRowCount(0);

        for (int i = 0; i < listSPCTLoad.size(); i++) {
            SanPhamChiTiet sanPhamChiTiet = listSPCTLoad.get(i);
            int soLuong = listSoLuong.get(i); // Retrieve the corresponding quantity
            model.addRow(new Object[]{
                sanPhamChiTiet.getId(),
                sanPhamChiTiet.getTenSp().getTen(),
                sanPhamChiTiet.getChatLieu(),
                sanPhamChiTiet.getMauSac(),
                sanPhamChiTiet.getKichCo(),
                sanPhamChiTiet.getGiaBan(),
                soLuong, // Display the quantity in the table column
                sanPhamChiTiet.getGiaBan() * soLuong
            });

        }

    }
    
    private void loadCategoryThuongHieu() {
        List<ThuongHieu> listThuongHieu = thuongHieuService.getAll();
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbo_filterThuongHieu1.getModel();
        if (!listThuongHieu.isEmpty()) {
            for (ThuongHieu thuongHieu : listThuongHieu) {
                model.addElement(thuongHieu.getTen());
            }
        }
        if (!listKhuyenMai.isEmpty()) {
            for (model.KhuyenMai khuyenMai : listKhuyenMai) {
                cbx_khuyenMai1.addItem(khuyenMai.getTenKhuyenMai());
            }

        }
    }
    
    private void applyKhuyenMai() {
        double giaTienGiam = 0;
        try {
            double tongTien = Double.parseDouble(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 3).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chưa chọn hóa đơn áp dụng voucher !");
            return;
        }
        boolean isAvaliable = true;
        double tongTien = Double.parseDouble(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 3).toString());
        String inputKhuyenMai = cbx_khuyenMai1.getSelectedItem() + "";
        for (model.KhuyenMai khuyenMai : listKhuyenMai) {

            if (khuyenMai.getTenKhuyenMai().equals(inputKhuyenMai)) {

                if (khuyenMai.getSoLuong() <= 0) {
                    isAvaliable = false;
                } else {
                    if (khuyenMai.getHinhThucKM().equals("VND")) {
                        giaTienGiam = Double.parseDouble(khuyenMai.getGiaTriGiam());
                        lbl_giamVoucher1.setText(decimalFormat.format(giaTienGiam));
//                        lbl_tienPhaitra1.setText(decimalFormat.format((tongTien - giaTienGiam)));
                        isAvaliable = true;
                        return;
                    } else if (khuyenMai.getHinhThucKM().equals("%")) {
                        giaTienGiam = tongTien * (Double.parseDouble(khuyenMai.getGiaTriGiam())) / 100;
                        lbl_giamVoucher1.setText(decimalFormat.format(giaTienGiam));
//                        lbl_tienPhaitra1.setText(decimalFormat.format((tongTien - giaTienGiam)));
                        isAvaliable = true;
                        return;
                    }
                }

            } else {
                isAvaliable = false;
            }
        }
        if (!isAvaliable) {
            JOptionPane.showMessageDialog(this, "Khuyến mãi không chính xác hoặc đã hết!");
            return;
        }
    }
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_gioHang1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_hoadon1 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_sanPham = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnTaoHoaDon1 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblTienThua1 = new javax.swing.JLabel();
        lbl_tenKH1 = new javax.swing.JLabel();
        lbl_sdtKH1 = new javax.swing.JLabel();
        lbTongTien1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbl_giamVoucher1 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lbl_tienPhaitra1 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtTienKhachTra1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        lb_idHD1 = new javax.swing.JLabel();
        cbx_khuyenMai1 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        txtSpSearch1 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        cbo_filterThuongHieu1 = new javax.swing.JComboBox<>();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        btnThemSpVaoHD1 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setMaximumSize(new java.awt.Dimension(940, 580));
        jPanel3.setMinimumSize(new java.awt.Dimension(940, 580));
        jPanel3.setName(""); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_gioHang1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "IDSP", "Tên SP", "Chất Liệu", "Màu Sắc", "Kích Cỡ", "Đơn Giá", "Số Lượng", "Thành Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tbl_gioHang1);

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 580, 147));

        jLabel9.setText("Hóa đơn");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 9, 100, -1));

        jLabel10.setText("Giỏ hàng");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 190, 60, -1));

        tbl_hoadon1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "MaHD", "Khách Hàng", "Tổng tiền", "Tình trạng", "Ngày tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_hoadon1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_hoadon1MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_hoadon1);

        jPanel3.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 31, 670, 147));

        tbl_sanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tên SP", "Chất liệu", "Kích Cỡ", "Màu Sắc", "Thương Hiệu", "Số Lượng ", "Giá Bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_sanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_sanPhamMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_sanPhamMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_sanPhamMousePressed(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_sanPham);

        jPanel3.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 670, 147));

        jPanel4.setBackground(new java.awt.Color(153, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setBackground(new java.awt.Color(0, 153, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Khách hàng");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, 34));

        jButton4.setBackground(new java.awt.Color(0, 153, 51));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Làm mới");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 460, 90, 35));

        btnTaoHoaDon1.setBackground(new java.awt.Color(0, 153, 51));
        btnTaoHoaDon1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoHoaDon1.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoHoaDon1.setText("Tạo hóa đơn");
        btnTaoHoaDon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHoaDon1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnTaoHoaDon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, -1, 35));

        jButton9.setBackground(new java.awt.Color(0, 153, 51));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Thanh toán");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, 160, 40));

        jLabel11.setText("SĐT");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 40, -1));

        jLabel12.setText("Tên khách hàng");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel13.setText("Giảm giá voucher");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, 20));

        jLabel15.setText("Tổng tiền");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        lblTienThua1.setText("...");
        jPanel4.add(lblTienThua1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 130, -1));

        lbl_tenKH1.setText("...");
        jPanel4.add(lbl_tenKH1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 160, -1));

        lbl_sdtKH1.setText("...");
        jPanel4.add(lbl_sdtKH1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 160, -1));

        lbTongTien1.setText("...");
        jPanel4.add(lbTongTien1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 130, -1));

        jLabel18.setText("Tiền phải trả:");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, 20));

        lbl_giamVoucher1.setText("...");
        jPanel4.add(lbl_giamVoucher1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 130, -1));

        jLabel20.setText("Chọn Khuyến Mãi:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel23.setText("Tiền Khách Đưa");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, -1, -1));

        lbl_tienPhaitra1.setText("...");
        jPanel4.add(lbl_tienPhaitra1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 130, 20));

        jLabel24.setText("Tiền trả lại khách");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, -1, -1));

        txtTienKhachTra1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtTienKhachTra1InputMethodTextChanged(evt);
            }
        });
        txtTienKhachTra1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachTra1ActionPerformed(evt);
            }
        });
        txtTienKhachTra1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTienKhachTra1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienKhachTra1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTienKhachTra1KeyTyped(evt);
            }
        });
        jPanel4.add(txtTienKhachTra1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 340, 130, 30));

        jLabel25.setText("ID hóa đơn:");
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        lb_idHD1.setText("...");
        jPanel4.add(lb_idHD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 90, 20));

        cbx_khuyenMai1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn khuyến mãi" }));
        cbx_khuyenMai1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_khuyenMai1ItemStateChanged(evt);
            }
        });
        cbx_khuyenMai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_khuyenMai1ActionPerformed(evt);
            }
        });
        jPanel4.add(cbx_khuyenMai1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 170, 30));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 0, 260, 570));

        jLabel26.setText("Sản phẩm");
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 395, -1, -1));

        txtSpSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSpSearch1ActionPerformed(evt);
            }
        });
        jPanel3.add(txtSpSearch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 382, 150, 30));

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search_24px.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 380, 40, 30));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(102, 102, 102));
        jLabel27.setText("Lọc Thương Hiệu");
        jPanel3.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 360, -1, -1));

        cbo_filterThuongHieu1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All" }));
        cbo_filterThuongHieu1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo_filterThuongHieu1ItemStateChanged(evt);
            }
        });
        cbo_filterThuongHieu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbo_filterThuongHieu1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cbo_filterThuongHieu1MouseEntered(evt);
            }
        });
        cbo_filterThuongHieu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_filterThuongHieu1ActionPerformed(evt);
            }
        });
        cbo_filterThuongHieu1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cbo_filterThuongHieu1PropertyChange(evt);
            }
        });
        jPanel3.add(cbo_filterThuongHieu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 380, 130, 30));

        jButton11.setBackground(new java.awt.Color(0, 204, 0));
        jButton11.setText("Xóa hết");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 230, 80, 30));

        jButton12.setBackground(new java.awt.Color(0, 204, 0));
        jButton12.setText("Sửa Số Lượng");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 290, 110, 30));

        btnThemSpVaoHD1.setBackground(new java.awt.Color(0, 204, 0));
        btnThemSpVaoHD1.setText("Thêm Sản Phẩm");
        btnThemSpVaoHD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSpVaoHD1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnThemSpVaoHD1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 370, 130, 40));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(102, 102, 102));
        jLabel28.setText("Tìm theo tên");
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 360, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 936, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 936, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 572, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_hoadon1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_hoadon1MouseClicked
        try {
            Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");
        } catch (Exception e) {
            return;
        }
        int idHD = Integer.parseInt(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 0) + "");

        loadGioHangByIDHD(idHD);
        
    }//GEN-LAST:event_tbl_hoadon1MouseClicked

    private void tbl_sanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sanPhamMouseClicked
        
    }//GEN-LAST:event_tbl_sanPhamMouseClicked

    private void tbl_sanPhamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sanPhamMouseEntered
        
    }//GEN-LAST:event_tbl_sanPhamMouseEntered

    private void tbl_sanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sanPhamMousePressed
        
    }//GEN-LAST:event_tbl_sanPhamMousePressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnTaoHoaDon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHoaDon1ActionPerformed
        
    }//GEN-LAST:event_btnTaoHoaDon1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void txtTienKhachTra1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtTienKhachTra1InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachTra1InputMethodTextChanged

    private void txtTienKhachTra1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachTra1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachTra1ActionPerformed

    private void txtTienKhachTra1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachTra1KeyPressed

    }//GEN-LAST:event_txtTienKhachTra1KeyPressed

    private void txtTienKhachTra1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachTra1KeyReleased
        
    }//GEN-LAST:event_txtTienKhachTra1KeyReleased

    private void txtTienKhachTra1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienKhachTra1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachTra1KeyTyped

    private void cbx_khuyenMai1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_khuyenMai1ItemStateChanged
        if (!(cbx_khuyenMai1.getSelectedItem() + "").equalsIgnoreCase("Chọn khuyến mãi")) {
            applyKhuyenMai();
        } else {
            lbl_giamVoucher1.setText("");
            try {
                double tongTien = Double.parseDouble(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 3).toString());
            } catch (Exception e) {
                return;
            }
            double tongTien = Double.parseDouble(tbl_hoadon1.getValueAt(tbl_hoadon1.getSelectedRow(), 3).toString());
            lbl_tienPhaitra1.setText(decimalFormat.format(tongTien) + "");
        }
    }//GEN-LAST:event_cbx_khuyenMai1ItemStateChanged

    private void cbx_khuyenMai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_khuyenMai1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbx_khuyenMai1ActionPerformed

    private void txtSpSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSpSearch1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSpSearch1ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void cbo_filterThuongHieu1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo_filterThuongHieu1ItemStateChanged
        
    }//GEN-LAST:event_cbo_filterThuongHieu1ItemStateChanged

    private void cbo_filterThuongHieu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbo_filterThuongHieu1MouseClicked
        
    }//GEN-LAST:event_cbo_filterThuongHieu1MouseClicked

    private void cbo_filterThuongHieu1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbo_filterThuongHieu1MouseEntered
        
    }//GEN-LAST:event_cbo_filterThuongHieu1MouseEntered

    private void cbo_filterThuongHieu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_filterThuongHieu1ActionPerformed
        
    }//GEN-LAST:event_cbo_filterThuongHieu1ActionPerformed

    private void cbo_filterThuongHieu1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cbo_filterThuongHieu1PropertyChange
        
    }//GEN-LAST:event_cbo_filterThuongHieu1PropertyChange

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed

    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        
    }//GEN-LAST:event_jButton12ActionPerformed

    private void btnThemSpVaoHD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSpVaoHD1ActionPerformed
        
        

    }//GEN-LAST:event_btnThemSpVaoHD1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTaoHoaDon1;
    private javax.swing.JButton btnThemSpVaoHD1;
    private javax.swing.JComboBox<String> cbo_filterThuongHieu1;
    private javax.swing.JComboBox<String> cbx_khuyenMai1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lbTongTien1;
    private javax.swing.JLabel lb_idHD1;
    private javax.swing.JLabel lblTienThua1;
    private javax.swing.JLabel lbl_giamVoucher1;
    private javax.swing.JLabel lbl_sdtKH1;
    private javax.swing.JLabel lbl_tenKH1;
    private javax.swing.JLabel lbl_tienPhaitra1;
    private javax.swing.JTable tbl_gioHang1;
    private javax.swing.JTable tbl_hoadon1;
    private javax.swing.JTable tbl_sanPham;
    private javax.swing.JTextField txtSpSearch1;
    private javax.swing.JTextField txtTienKhachTra1;
    // End of variables declaration//GEN-END:variables
}
