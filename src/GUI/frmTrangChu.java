/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BLL.BUS_KhachHang;
import ConnectSQLSever.ConnectToMSSQL;
import DAL.DAL_Account;
import DAL.DAL_NhanVien;
import DTO.DTO_Account;
import DTO.DTO_KhachHang;
import DTO.DTO_NhanVien;
import DTO.DTO_PhanQuyen;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class frmTrangChu extends javax.swing.JFrame {
    ConnectToMSSQL a = new ConnectToMSSQL();
    Connection conn = null;
    ResultSet rs=null;
    PreparedStatement ps=null;
    Statement st = null;
    int nhanVien = 0;
    int khachhang;
    BUS_KhachHang bus_kh;
    /**
     * Creates new form frmTrangChu
     */
    
    public frmTrangChu() {
        initComponents();
        
    }
    public void hienThiCBBGioiTinh()
    {
        jCBBGioiTinh_NV.addItem("Nam");
        jCBBGioiTinh_NV.addItem("Nữ");
    }
    public void hienThiCBBPhanQuyen()
    {
        jCBBPhanQuyen_NV.addItem("Quản lý");
        jCBBPhanQuyen_NV.addItem("Nhân viên");
    }
    public boolean kiemTraNhanVien()
    {
            if(jTFTenNhanVien_NV.getText().equals(""))
             {
                 JOptionPane.showMessageDialog(null, "Tên nhân viên không được rỗng");
                 jTFTenNhanVien_NV.requestFocus();
                 return false;
             }
             else if(jTFEmail_NV.getText().equals(""))
             {
                 JOptionPane.showMessageDialog(null, "Email không được rỗng");
                 jTFEmail_NV.requestFocus();
                 return false;
             }
             else if(jTFSDT_NV.getText().equals(""))
             {
                 JOptionPane.showMessageDialog(null, "SDT không được rỗng");
                 jTFSDT_NV.requestFocus();
                 return false;
             }
             else if(jTFDiaChi_NV.getText().equals(""))
             {
                 JOptionPane.showMessageDialog(null, "Địa chỉ không được rỗng");
                 jTFDiaChi_NV.requestFocus();
                 return false;
             }
             else if(jTFTaiKhoan_NV.getText().equals(""))
             {
                 JOptionPane.showMessageDialog(null, "Tài khoản không được rỗng");
                 jTFTaiKhoan_NV.requestFocus();
                 return false;
             }
             else if(jTFMatKhau_NV.getText().equals(""))
             {
                 JOptionPane.showMessageDialog(null, "Mật khẩu không được rỗng");
                 jTFMatKhau_NV.requestFocus();
                 return false;
             }
             else if(jTFEmail_NV.getText().matches("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")==false)
             {
                 JOptionPane.showMessageDialog(null, "Email không đúng định dạng");
                 jTFEmail_NV.requestFocus();
                 return false;
             }
             else if(jTFSDT_NV.getText().matches("\\d{10}"))
             {
                 JOptionPane.showMessageDialog(null, "SDT không đúng định dạng");
                 jTFSDT_NV.requestFocus();
                 return false;
             }
        return true;
    }
    public void ResetNhanVien()
    {
        jTFMaNhanVien_NV.setText("");
        jTFTenNhanVien_NV.setText("");
        jDCNgayVaoLam_NV.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
        //jTFNgaySinh_NV.setText("");
        jTFEmail_NV.setText("");
        jTFSDT_NV.setText("");
        jTFDiaChi_NV.setText("");
        jCBBPhanQuyen_NV.setSelectedIndex(1);
        jTFTaiKhoan_NV.setText("");
        jTFMatKhau_NV.setText("");
    }
    public void setEnable_BtnNhanVien(boolean b)
    {
        jBtnThemNhanVien.setEnabled(b);
        jBtnSuaNhanVien.setEnabled(b);
        jBtnXoaNhanVien.setEnabled(b);
        jBtnLamMoiNhanVien.setEnabled(b);
        jBtnLuuNhanVien.setEnabled(b);
        
    }
    public void setEnable_txtNhanVien(boolean b)
    {
        jTFTenNhanVien_NV.setEnabled(b);
        jDCNgayVaoLam_NV.setEnabled(b);
        jDCNgaySinh_NV.setEnabled(b);
        jCBBGioiTinh_NV.setEnabled(b);
        jTFEmail_NV.setEnabled(b);
        jTFSDT_NV.setEnabled(b);
        jTFDiaChi_NV.setEnabled(b);
        jCBBPhanQuyen_NV.setEnabled(b);
        jTFTaiKhoan_NV.setEnabled(b);
        jTFMatKhau_NV.setEnabled(b);
    }
    public void hienThiDanhSachNhanVien()
    {
        hienThiCBBGioiTinh();
        hienThiCBBPhanQuyen();
        setEnable_txtNhanVien(false);
        setEnable_BtnNhanVien(false);
        jBtnThemNhanVien.setEnabled(true);
        ArrayList<DTO_NhanVien> dsNhanVien = new DAL_NhanVien().layDanhSachNhanVien();
        Object[] obj = new Object[]{"STT", "Mã Nhân Viên", "Tên Nhân Viên", "Ngày Sinh","Ngày vào làm",
            "Giới Tính", "Email","SDT","Chức vụ","Địa chỉ","Tài khoản","Mật khẩu"};  
        DefaultTableModel model = new DefaultTableModel(obj, 0);
        jTableDanhSachNhanVien.setModel(model);
        int i=1;
        
        for(DTO_NhanVien nv : dsNhanVien)
        {
            DTO_Account acc = new DTO_Account();
            DTO_PhanQuyen quyen = new DTO_PhanQuyen();
            //DAL_Account dAL_Account = new DAL_Account();
            acc = new DAL_Account().layThongTinTaiKhoan(nv.getIdAccount());
            if(nv.getIdPhanQuyen()==1)
            {
                quyen.setTenQuyen("Quản lý");
            }
            else if (nv.getIdPhanQuyen()==2)
            {
                quyen.setTenQuyen("Nhân viên");
            }
            
            
            model.addRow(new Object[]{i++,nv.getId(),nv.getHoTen(),nv.getNgaySinh(),nv.getNgayVaoLam()
                    ,nv.getGioiTinh(),nv.getEmail(),nv.getSdt(),quyen.getTenQuyen(),nv.getDiaChi(),acc.getUserName()
                    ,acc.getPassWord()
            });
        }
        jTableDanhSachNhanVien.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if(jTableDanhSachNhanVien.getSelectedRow()>=0)
                {
                    setEnable_txtNhanVien(false);
                    jBtnLamMoiNhanVien.setEnabled(false);
                    jBtnSuaNhanVien.setEnabled(true);
                    jBtnXoaNhanVien.setEnabled(true);
                    jBtnLamMoiNhanVien.setEnabled(true);
                    jTFMaNhanVien_NV.setText(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 1)+"");
                    jTFTenNhanVien_NV.setText(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 2)+"");
                    ////////
                    jDCNgayVaoLam_NV.setDate(java.sql.Date.valueOf(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 3)+""));
                    jDCNgaySinh_NV.setDate(java.sql.Date.valueOf(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 4)+""));
                    ///////
                    jCBBGioiTinh_NV.setSelectedItem(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 5)+"");
                    jTFEmail_NV.setText(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 6)+"");
                    jTFSDT_NV.setText(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 7)+"");
                    jCBBPhanQuyen_NV.setSelectedItem(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 8)+"");
                    jTFDiaChi_NV.setText(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 9)+"");
                    jTFTaiKhoan_NV.setText(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 10)+"");
                    jTFMatKhau_NV.setText(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 11)+"");
                }
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPaneQuanLy = new javax.swing.JTabbedPane();
        jTabbedPaneHoaDon = new javax.swing.JTabbedPane();
        jPanelPhieuBan = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanelThongTinChung_PhieuBan = new javax.swing.JPanel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jCBNhanVien_PhieuBan = new javax.swing.JComboBox<>();
        jLabel99 = new javax.swing.JLabel();
        jCBKhachHang_PhieuBan = new javax.swing.JComboBox<>();
        jLabel100 = new javax.swing.JLabel();
        jDateChooserNgayLapPhieu_PhieuBan = new com.toedter.calendar.JDateChooser();
        jLabel101 = new javax.swing.JLabel();
        jTFMaPhieuBan_PhieuBan = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        jTFTongTien_PhieuBan = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTAGhiChu_PhieuBan = new javax.swing.JTextArea();
        jPanelThongTinHangHoa_PhieuBan = new javax.swing.JPanel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jCBMaHang_PhieuBan = new javax.swing.JComboBox<>();
        jCBTenHang_MaPhieuBan = new javax.swing.JComboBox<>();
        jTFSoLuong_PhieuBan = new javax.swing.JTextField();
        jTFGiaBan_PhieuBan = new javax.swing.JTextField();
        jTFThanhTien_PhieuBan = new javax.swing.JTextField();
        jBtnThem_PhieuBan = new javax.swing.JButton();
        jBtnXoa_PhieuBan = new javax.swing.JButton();
        jBtnLapPhieu_PhieuBan = new javax.swing.JButton();
        jBtnHuy_PhieuBan = new javax.swing.JButton();
        jPanelDanhSachChiTiet_PhieuBan = new javax.swing.JPanel();
        jScrollPaneDSMatHang_PhieuBan = new javax.swing.JScrollPane();
        jTableMatHang_PhieuBan = new javax.swing.JTable();
        jPanelPhieuNhap = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanelThongTinChung_PhieuNhap = new javax.swing.JPanel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jCBNhanVien_PhieuNhap = new javax.swing.JComboBox<>();
        jLabel112 = new javax.swing.JLabel();
        jCBNhaCungCap_PhieuNhap = new javax.swing.JComboBox<>();
        jLabel113 = new javax.swing.JLabel();
        jDateChooserNgayLapPhieu_PhieuNhap = new com.toedter.calendar.JDateChooser();
        jLabel114 = new javax.swing.JLabel();
        jTFMaPhieuBan_PhieuNhap = new javax.swing.JTextField();
        jLabel115 = new javax.swing.JLabel();
        jTFTongTien_PhieuNhap = new javax.swing.JTextField();
        jLabel116 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTAGhiChu_PhieuNhap = new javax.swing.JTextArea();
        jPanelThongTinHangHoa_PhieuNhap = new javax.swing.JPanel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jCBMaHang_PhieuNhap = new javax.swing.JComboBox<>();
        jCBTenHang_MaPhieuNhap = new javax.swing.JComboBox<>();
        jTFSoLuong_PhieuNhap = new javax.swing.JTextField();
        jTFGiaBan_PhieuNhap = new javax.swing.JTextField();
        jTFThanhTien_PhieuNhap = new javax.swing.JTextField();
        jBtnThem_PhieuNhap = new javax.swing.JButton();
        jBtnXoa_PhieuNhap = new javax.swing.JButton();
        jBtnLapPhieu_PhieuNhap = new javax.swing.JButton();
        jBtnHuy_PhieuNhap = new javax.swing.JButton();
        jPanelChiTietHangHoa_PhieuNhap = new javax.swing.JPanel();
        jScrollPaneDSMatHang_PhieuNhap = new javax.swing.JScrollPane();
        jTableMatHang_PhieuNhap = new javax.swing.JTable();
        jPanelDanhSachPhieuBan = new javax.swing.JPanel();
        jSPDanhSachPhieuBan = new javax.swing.JScrollPane();
        jTableDanhSachPhieuBan = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPaneChiTietPhieuBan = new javax.swing.JScrollPane();
        jTableChiTietPhieuBan = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jTFMaHoaDon_DSPB = new javax.swing.JTextField();
        jPanelDanhSachPhieuNhap = new javax.swing.JPanel();
        jSPDanhSachPhieuNhap = new javax.swing.JScrollPane();
        jTableDanhSachPhieuBan1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPaneChiTietPhieuNhap = new javax.swing.JScrollPane();
        jTableChiTietPhieuNhap = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jTFMaHoaDon_DSPN = new javax.swing.JTextField();
        jPanelHang = new javax.swing.JPanel();
        jPanelDanhSachHangHoa = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTFMaHang_HangHoa = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTFTenHang_HangHoa = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jCBDonViTinh_HangHoa = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jCBNhomHang_HangHoa = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jTFGiaNhap_HangHoa = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTFGiaBan_HangHoa = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTAThanhPhan = new javax.swing.JTextArea();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTACongDung = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jTFTimKiem_HangHoa = new javax.swing.JTextField();
        jBtnTimKiem_HangHoa = new javax.swing.JButton();
        jBtnTatCa_HangHoa = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        jPanelNhanVien = new javax.swing.JPanel();
        jPanelDanhSachNhanVien = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jTFMaNhanVien_NV = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTFTenNhanVien_NV = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jCBBGioiTinh_NV = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jTFEmail_NV = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTFSDT_NV = new javax.swing.JTextField();
        jCBBPhanQuyen_NV = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTFDiaChi_NV = new javax.swing.JTextArea();
        jLabel32 = new javax.swing.JLabel();
        jTFTaiKhoan_NV = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jTFMatKhau_NV = new javax.swing.JTextField();
        jBtnThemNhanVien = new javax.swing.JButton();
        jBtnXoaNhanVien = new javax.swing.JButton();
        jBtnSuaNhanVien = new javax.swing.JButton();
        jBtnLuuNhanVien = new javax.swing.JButton();
        jBtnLamMoiNhanVien = new javax.swing.JButton();
        jDCNgayVaoLam_NV = new com.toedter.calendar.JDateChooser();
        jDCNgaySinh_NV = new com.toedter.calendar.JDateChooser();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTableDanhSachNhanVien = new javax.swing.JTable();
        jPanelNhaCungCap = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableDanhSachNhaCungCap = new javax.swing.JTable();
        jTextField12 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jPanelKhachHang = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtTimKiemKhachHang = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnTatCaKhachHang = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        txtMaKhachHang = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        jLabel54 = new javax.swing.JLabel();
        btnThemKhachHang = new javax.swing.JButton();
        btnSuaKhachHang = new javax.swing.JButton();
        btnXoaKhachHang = new javax.swing.JButton();
        btnLuuKhachHang = new javax.swing.JButton();
        btnHuyKhachHang = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhachHangs = new javax.swing.JTable();
        jTabbedPaneThongKe = new javax.swing.JTabbedPane();
        jPanelThongTinNhanVien = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField3 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPaneQuanLy.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTabbedPaneQuanLy.setMinimumSize(new java.awt.Dimension(150, 615));
        jTabbedPaneQuanLy.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneQuanLyStateChanged(evt);
            }
        });

        jTabbedPaneHoaDon.setForeground(new java.awt.Color(0, 153, 204));
        jTabbedPaneHoaDon.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        jLabel97.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel97.setText("Thông tin chung");

        jLabel98.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel98.setText("Nhân viên");

        jCBNhanVien_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCBNhanVien_PhieuBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel99.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel99.setText("Khách hàng");

        jCBKhachHang_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCBKhachHang_PhieuBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel100.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel100.setText("Ngày lập phiếu");

        jDateChooserNgayLapPhieu_PhieuBan.setDateFormatString("dd/MM/yyyy");
        jDateChooserNgayLapPhieu_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel101.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel101.setText("Mã phiếu");

        jTFMaPhieuBan_PhieuBan.setEditable(false);
        jTFMaPhieuBan_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel102.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel102.setText("Tổng tiền");

        jTFTongTien_PhieuBan.setEditable(false);
        jTFTongTien_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel103.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel103.setText("Ghi chú");

        jTAGhiChu_PhieuBan.setColumns(20);
        jTAGhiChu_PhieuBan.setRows(5);
        jScrollPane12.setViewportView(jTAGhiChu_PhieuBan);

        javax.swing.GroupLayout jPanelThongTinChung_PhieuBanLayout = new javax.swing.GroupLayout(jPanelThongTinChung_PhieuBan);
        jPanelThongTinChung_PhieuBan.setLayout(jPanelThongTinChung_PhieuBanLayout);
        jPanelThongTinChung_PhieuBanLayout.setHorizontalGroup(
            jPanelThongTinChung_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelThongTinChung_PhieuBanLayout.createSequentialGroup()
                .addGroup(jPanelThongTinChung_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelThongTinChung_PhieuBanLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelThongTinChung_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel98)
                            .addComponent(jLabel101)
                            .addComponent(jLabel100)
                            .addComponent(jLabel99)
                            .addComponent(jLabel102)
                            .addComponent(jLabel103))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelThongTinChung_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFMaPhieuBan_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCBNhanVien_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCBKhachHang_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooserNgayLapPhieu_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTFTongTien_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelThongTinChung_PhieuBanLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelThongTinChung_PhieuBanLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jCBKhachHang_PhieuBan, jCBNhanVien_PhieuBan, jDateChooserNgayLapPhieu_PhieuBan, jScrollPane12, jTFMaPhieuBan_PhieuBan, jTFTongTien_PhieuBan});

        jPanelThongTinChung_PhieuBanLayout.setVerticalGroup(
            jPanelThongTinChung_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelThongTinChung_PhieuBanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelThongTinChung_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel101)
                    .addComponent(jTFMaPhieuBan_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelThongTinChung_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel98)
                    .addComponent(jCBNhanVien_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelThongTinChung_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel99)
                    .addComponent(jCBKhachHang_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelThongTinChung_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel100)
                    .addComponent(jDateChooserNgayLapPhieu_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 27, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelThongTinChung_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel102)
                    .addComponent(jTFTongTien_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelThongTinChung_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel103)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(182, Short.MAX_VALUE))
        );

        jLabel104.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel104.setText("Thông tin hàng hóa");

        jLabel105.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel105.setText("Mã hàng");

        jLabel106.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel106.setText("Tên hàng");

        jLabel107.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel107.setText("Số lượng");

        jLabel108.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel108.setText("Giá bán");

        jLabel109.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel109.setText("Thành tiền");

        jCBMaHang_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCBMaHang_PhieuBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCBTenHang_MaPhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCBTenHang_MaPhieuBan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTFSoLuong_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTFGiaBan_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTFThanhTien_PhieuBan.setEditable(false);
        jTFThanhTien_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jBtnThem_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jBtnThem_PhieuBan.setText("Thêm");

        jBtnXoa_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jBtnXoa_PhieuBan.setText("Xóa");

        jBtnLapPhieu_PhieuBan.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jBtnLapPhieu_PhieuBan.setText("Lập phiếu");

        jBtnHuy_PhieuBan.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jBtnHuy_PhieuBan.setText("Hủy");

        javax.swing.GroupLayout jPanelThongTinHangHoa_PhieuBanLayout = new javax.swing.GroupLayout(jPanelThongTinHangHoa_PhieuBan);
        jPanelThongTinHangHoa_PhieuBan.setLayout(jPanelThongTinHangHoa_PhieuBanLayout);
        jPanelThongTinHangHoa_PhieuBanLayout.setHorizontalGroup(
            jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createSequentialGroup()
                        .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel105)
                            .addComponent(jLabel106)
                            .addComponent(jLabel107)
                            .addComponent(jLabel108)
                            .addComponent(jLabel109))
                        .addGap(43, 43, 43)
                        .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFGiaBan_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createSequentialGroup()
                                .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCBTenHang_MaPhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCBMaHang_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTFSoLuong_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTFThanhTien_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(49, 49, 49)
                                .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createSequentialGroup()
                                        .addComponent(jBtnLapPhieu_PhieuBan)
                                        .addGap(18, 18, 18)
                                        .addComponent(jBtnHuy_PhieuBan))
                                    .addComponent(jBtnThem_PhieuBan)
                                    .addComponent(jBtnXoa_PhieuBan)))))
                    .addComponent(jLabel104))
                .addContainerGap(103, Short.MAX_VALUE))
        );

        jPanelThongTinHangHoa_PhieuBanLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jCBMaHang_PhieuBan, jCBTenHang_MaPhieuBan, jTFGiaBan_PhieuBan, jTFSoLuong_PhieuBan, jTFThanhTien_PhieuBan});

        jPanelThongTinHangHoa_PhieuBanLayout.setVerticalGroup(
            jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel104)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel105)
                    .addComponent(jCBMaHang_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnThem_PhieuBan))
                .addGap(10, 10, 10)
                .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createSequentialGroup()
                        .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel106)
                            .addComponent(jCBTenHang_MaPhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel107)
                            .addComponent(jTFSoLuong_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel108)
                            .addComponent(jTFGiaBan_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel109)
                            .addComponent(jTFThanhTien_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelThongTinHangHoa_PhieuBanLayout.createSequentialGroup()
                        .addComponent(jBtnXoa_PhieuBan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelThongTinHangHoa_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnLapPhieu_PhieuBan)
                            .addComponent(jBtnHuy_PhieuBan))))
                .addContainerGap())
        );

        jPanelThongTinHangHoa_PhieuBanLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jCBMaHang_PhieuBan, jCBTenHang_MaPhieuBan, jTFGiaBan_PhieuBan, jTFSoLuong_PhieuBan, jTFThanhTien_PhieuBan});

        jScrollPaneDSMatHang_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTableMatHang_PhieuBan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTableMatHang_PhieuBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hàng", "Tên hàng", "Số lượng", "Giá bán", "Thành tiền"
            }
        ));
        jScrollPaneDSMatHang_PhieuBan.setViewportView(jTableMatHang_PhieuBan);
        if (jTableMatHang_PhieuBan.getColumnModel().getColumnCount() > 0) {
            jTableMatHang_PhieuBan.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        javax.swing.GroupLayout jPanelDanhSachChiTiet_PhieuBanLayout = new javax.swing.GroupLayout(jPanelDanhSachChiTiet_PhieuBan);
        jPanelDanhSachChiTiet_PhieuBan.setLayout(jPanelDanhSachChiTiet_PhieuBanLayout);
        jPanelDanhSachChiTiet_PhieuBanLayout.setHorizontalGroup(
            jPanelDanhSachChiTiet_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneDSMatHang_PhieuBan)
        );
        jPanelDanhSachChiTiet_PhieuBanLayout.setVerticalGroup(
            jPanelDanhSachChiTiet_PhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneDSMatHang_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanelThongTinChung_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelThongTinHangHoa_PhieuBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelDanhSachChiTiet_PhieuBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelThongTinChung_PhieuBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanelThongTinHangHoa_PhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelDanhSachChiTiet_PhieuBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelPhieuBanLayout = new javax.swing.GroupLayout(jPanelPhieuBan);
        jPanelPhieuBan.setLayout(jPanelPhieuBanLayout);
        jPanelPhieuBanLayout.setHorizontalGroup(
            jPanelPhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelPhieuBanLayout.setVerticalGroup(
            jPanelPhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPaneHoaDon.addTab("Phiếu bán", jPanelPhieuBan);

        jLabel110.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel110.setText("Thông tin chung");

        jLabel111.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel111.setText("Nhân viên");

        jCBNhanVien_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCBNhanVien_PhieuNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel112.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel112.setText("Nhà cung cấp");

        jCBNhaCungCap_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCBNhaCungCap_PhieuNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel113.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel113.setText("Ngày lập phiếu");

        jDateChooserNgayLapPhieu_PhieuNhap.setDateFormatString("dd/MM/yyyy");
        jDateChooserNgayLapPhieu_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel114.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel114.setText("Mã phiếu");

        jTFMaPhieuBan_PhieuNhap.setEditable(false);
        jTFMaPhieuBan_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel115.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel115.setText("Tổng tiền");

        jTFTongTien_PhieuNhap.setEditable(false);
        jTFTongTien_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel116.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel116.setText("Ghi chú");

        jTAGhiChu_PhieuNhap.setColumns(20);
        jTAGhiChu_PhieuNhap.setRows(5);
        jScrollPane13.setViewportView(jTAGhiChu_PhieuNhap);

        javax.swing.GroupLayout jPanelThongTinChung_PhieuNhapLayout = new javax.swing.GroupLayout(jPanelThongTinChung_PhieuNhap);
        jPanelThongTinChung_PhieuNhap.setLayout(jPanelThongTinChung_PhieuNhapLayout);
        jPanelThongTinChung_PhieuNhapLayout.setHorizontalGroup(
            jPanelThongTinChung_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelThongTinChung_PhieuNhapLayout.createSequentialGroup()
                .addGroup(jPanelThongTinChung_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelThongTinChung_PhieuNhapLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelThongTinChung_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel111)
                            .addComponent(jLabel114)
                            .addComponent(jLabel113)
                            .addComponent(jLabel112)
                            .addComponent(jLabel115)
                            .addComponent(jLabel116))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelThongTinChung_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFMaPhieuBan_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCBNhanVien_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCBNhaCungCap_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooserNgayLapPhieu_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTFTongTien_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelThongTinChung_PhieuNhapLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelThongTinChung_PhieuNhapLayout.setVerticalGroup(
            jPanelThongTinChung_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelThongTinChung_PhieuNhapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelThongTinChung_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel114)
                    .addComponent(jTFMaPhieuBan_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelThongTinChung_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel111)
                    .addComponent(jCBNhanVien_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelThongTinChung_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel112)
                    .addComponent(jCBNhaCungCap_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelThongTinChung_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel113)
                    .addComponent(jDateChooserNgayLapPhieu_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 27, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelThongTinChung_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel115)
                    .addComponent(jTFTongTien_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelThongTinChung_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel116)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(182, Short.MAX_VALUE))
        );

        jLabel117.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel117.setText("Thông tin hàng hóa");

        jLabel118.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel118.setText("Mã hàng");

        jLabel119.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel119.setText("Tên hàng");

        jLabel120.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel120.setText("Số lượng");

        jLabel121.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel121.setText("Giá nhập");

        jLabel122.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel122.setText("Thành tiền");

        jCBMaHang_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCBMaHang_PhieuNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCBTenHang_MaPhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCBTenHang_MaPhieuNhap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTFSoLuong_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTFGiaBan_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTFThanhTien_PhieuNhap.setEditable(false);
        jTFThanhTien_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jBtnThem_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jBtnThem_PhieuNhap.setText("Thêm");

        jBtnXoa_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jBtnXoa_PhieuNhap.setText("Xóa");

        jBtnLapPhieu_PhieuNhap.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jBtnLapPhieu_PhieuNhap.setText("Lập phiếu");

        jBtnHuy_PhieuNhap.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jBtnHuy_PhieuNhap.setText("Hủy");

        javax.swing.GroupLayout jPanelThongTinHangHoa_PhieuNhapLayout = new javax.swing.GroupLayout(jPanelThongTinHangHoa_PhieuNhap);
        jPanelThongTinHangHoa_PhieuNhap.setLayout(jPanelThongTinHangHoa_PhieuNhapLayout);
        jPanelThongTinHangHoa_PhieuNhapLayout.setHorizontalGroup(
            jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createSequentialGroup()
                        .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel118)
                            .addComponent(jLabel119)
                            .addComponent(jLabel120)
                            .addComponent(jLabel121)
                            .addComponent(jLabel122))
                        .addGap(43, 43, 43)
                        .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFGiaBan_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createSequentialGroup()
                                .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jCBMaHang_PhieuNhap, 0, 222, Short.MAX_VALUE)
                                    .addComponent(jTFSoLuong_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTFThanhTien_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCBTenHang_MaPhieuNhap, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(49, 49, 49)
                                .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createSequentialGroup()
                                        .addComponent(jBtnLapPhieu_PhieuNhap)
                                        .addGap(18, 18, 18)
                                        .addComponent(jBtnHuy_PhieuNhap))
                                    .addComponent(jBtnThem_PhieuNhap)
                                    .addComponent(jBtnXoa_PhieuNhap)))))
                    .addComponent(jLabel117))
                .addContainerGap(103, Short.MAX_VALUE))
        );

        jPanelThongTinHangHoa_PhieuNhapLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jCBMaHang_PhieuNhap, jCBTenHang_MaPhieuNhap, jTFGiaBan_PhieuNhap, jTFSoLuong_PhieuNhap, jTFThanhTien_PhieuNhap});

        jPanelThongTinHangHoa_PhieuNhapLayout.setVerticalGroup(
            jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel117)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel118)
                    .addComponent(jCBMaHang_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnThem_PhieuNhap))
                .addGap(10, 10, 10)
                .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createSequentialGroup()
                        .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel119)
                            .addComponent(jCBTenHang_MaPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel120)
                            .addComponent(jTFSoLuong_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel121)
                            .addComponent(jTFGiaBan_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel122)
                            .addComponent(jTFThanhTien_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelThongTinHangHoa_PhieuNhapLayout.createSequentialGroup()
                        .addComponent(jBtnXoa_PhieuNhap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelThongTinHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnLapPhieu_PhieuNhap)
                            .addComponent(jBtnHuy_PhieuNhap))))
                .addContainerGap())
        );

        jScrollPaneDSMatHang_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTableMatHang_PhieuNhap.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTableMatHang_PhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hàng", "Tên hàng", "Số lượng", "Giá bán", "Thành tiền"
            }
        ));
        jScrollPaneDSMatHang_PhieuNhap.setViewportView(jTableMatHang_PhieuNhap);
        if (jTableMatHang_PhieuNhap.getColumnModel().getColumnCount() > 0) {
            jTableMatHang_PhieuNhap.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        javax.swing.GroupLayout jPanelChiTietHangHoa_PhieuNhapLayout = new javax.swing.GroupLayout(jPanelChiTietHangHoa_PhieuNhap);
        jPanelChiTietHangHoa_PhieuNhap.setLayout(jPanelChiTietHangHoa_PhieuNhapLayout);
        jPanelChiTietHangHoa_PhieuNhapLayout.setHorizontalGroup(
            jPanelChiTietHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneDSMatHang_PhieuNhap)
        );
        jPanelChiTietHangHoa_PhieuNhapLayout.setVerticalGroup(
            jPanelChiTietHangHoa_PhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneDSMatHang_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanelThongTinChung_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelThongTinHangHoa_PhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelChiTietHangHoa_PhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelThongTinChung_PhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanelThongTinHangHoa_PhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelChiTietHangHoa_PhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelPhieuNhapLayout = new javax.swing.GroupLayout(jPanelPhieuNhap);
        jPanelPhieuNhap.setLayout(jPanelPhieuNhapLayout);
        jPanelPhieuNhapLayout.setHorizontalGroup(
            jPanelPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelPhieuNhapLayout.setVerticalGroup(
            jPanelPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPaneHoaDon.addTab("Phiếu nhập", jPanelPhieuNhap);

        jTableDanhSachPhieuBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã phiếu", "Nhân viên", "Khách hàng", "Ngày nhập hóa đơn", "Tổng tiền", "Ghi chú"
            }
        ));
        jSPDanhSachPhieuBan.setViewportView(jTableDanhSachPhieuBan);
        if (jTableDanhSachPhieuBan.getColumnModel().getColumnCount() > 0) {
            jTableDanhSachPhieuBan.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jLabel2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 204));
        jLabel2.setText("Chi tiết phiếu bán");

        jTableChiTietPhieuBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hàng", "Tên hàng", "Số lượng", "Giá bán", "Thành tiền"
            }
        ));
        jScrollPaneChiTietPhieuBan.setViewportView(jTableChiTietPhieuBan);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneChiTietPhieuBan, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneChiTietPhieuBan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("Mã hóa đơn");

        jTFMaHoaDon_DSPB.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanelDanhSachPhieuBanLayout = new javax.swing.GroupLayout(jPanelDanhSachPhieuBan);
        jPanelDanhSachPhieuBan.setLayout(jPanelDanhSachPhieuBanLayout);
        jPanelDanhSachPhieuBanLayout.setHorizontalGroup(
            jPanelDanhSachPhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSPDanhSachPhieuBan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1175, Short.MAX_VALUE)
            .addGroup(jPanelDanhSachPhieuBanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDanhSachPhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDanhSachPhieuBanLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTFMaHoaDon_DSPB, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelDanhSachPhieuBanLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelDanhSachPhieuBanLayout.setVerticalGroup(
            jPanelDanhSachPhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDanhSachPhieuBanLayout.createSequentialGroup()
                .addComponent(jSPDanhSachPhieuBan, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDanhSachPhieuBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFMaHoaDon_DSPB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPaneHoaDon.addTab("Danh sách phiếu bán", jPanelDanhSachPhieuBan);

        jTableDanhSachPhieuBan1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã phiếu", "Nhân viên", "Khách hàng", "Ngày nhập hóa đơn", "Tổng tiền", "Ghi chú"
            }
        ));
        jSPDanhSachPhieuNhap.setViewportView(jTableDanhSachPhieuBan1);
        if (jTableDanhSachPhieuBan1.getColumnModel().getColumnCount() > 0) {
            jTableDanhSachPhieuBan1.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jLabel4.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 204));
        jLabel4.setText("Chi tiết hóa đơn");

        jTableChiTietPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hàng hóa", "Tên hàng", "Số lượng", "Giá bán", "Thành tiền"
            }
        ));
        jScrollPaneChiTietPhieuNhap.setViewportView(jTableChiTietPhieuNhap);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneChiTietPhieuNhap, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneChiTietPhieuNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Mã hóa đơn");

        jTFMaHoaDon_DSPN.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanelDanhSachPhieuNhapLayout = new javax.swing.GroupLayout(jPanelDanhSachPhieuNhap);
        jPanelDanhSachPhieuNhap.setLayout(jPanelDanhSachPhieuNhapLayout);
        jPanelDanhSachPhieuNhapLayout.setHorizontalGroup(
            jPanelDanhSachPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSPDanhSachPhieuNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1175, Short.MAX_VALUE)
            .addGroup(jPanelDanhSachPhieuNhapLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDanhSachPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDanhSachPhieuNhapLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jTFMaHoaDon_DSPN, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelDanhSachPhieuNhapLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelDanhSachPhieuNhapLayout.setVerticalGroup(
            jPanelDanhSachPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDanhSachPhieuNhapLayout.createSequentialGroup()
                .addComponent(jSPDanhSachPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDanhSachPhieuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTFMaHoaDon_DSPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPaneHoaDon.addTab("Danh sách phiếu nhập", jPanelDanhSachPhieuNhap);

        jTabbedPaneQuanLy.addTab("Giao dịch", new javax.swing.ImageIcon(getClass().getResource("/image/hoadon.png")), jTabbedPaneHoaDon, ""); // NOI18N

        jLabel16.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel16.setText("Thông tin hàng hóa");

        jLabel17.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel17.setText("Mã hàng");

        jTFMaHang_HangHoa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel18.setText("Tên hàng");

        jTFTenHang_HangHoa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel19.setText("Đơn vị tính");

        jCBDonViTinh_HangHoa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCBDonViTinh_HangHoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel20.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel20.setText("Nhóm hàng");

        jCBNhomHang_HangHoa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCBNhomHang_HangHoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel21.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel21.setText("Giá nhập");

        jTFGiaNhap_HangHoa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel33.setText("Giá bán");

        jTFGiaBan_HangHoa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTextField14.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextField14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                d(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Số lượng");

        jLabel34.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel34.setText("Thành phần");

        jTAThanhPhan.setColumns(20);
        jTAThanhPhan.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTAThanhPhan.setRows(5);
        jScrollPane5.setViewportView(jTAThanhPhan);

        jLabel35.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel35.setText("Công dụng");

        jTACongDung.setColumns(20);
        jTACongDung.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTACongDung.setRows(5);
        jScrollPane8.setViewportView(jTACongDung);

        jButton4.setText("Thêm");

        jButton5.setText("Sửa");

        jButton12.setText("Xóa");

        jButton13.setText("Lưu");

        jButton14.setText("Làm mới");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel17)
                                        .addComponent(jLabel18)
                                        .addComponent(jLabel19)
                                        .addComponent(jLabel20))
                                    .addGap(340, 340, 340))
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addGap(128, 128, 128)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jCBNhomHang_HangHoa, 0, 304, Short.MAX_VALUE)
                                        .addComponent(jCBDonViTinh_HangHoa, 0, 304, Short.MAX_VALUE)
                                        .addComponent(jTFTenHang_HangHoa, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                                        .addComponent(jTFMaHang_HangHoa))))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel21)
                                        .addComponent(jLabel33))
                                    .addGap(359, 359, 359))
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addGap(128, 128, 128)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTFGiaBan_HangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTFGiaNhap_HangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel5)
                            .addComponent(jLabel35))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                            .addComponent(jTextField14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton12)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton14)
                                    .addComponent(jButton13))))))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane5, jScrollPane8});

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton12, jButton13, jButton14, jButton4, jButton5});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel17)
                                .addComponent(jTFMaHang_HangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTFTenHang_HangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel34))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(jCBDonViTinh_HangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel20)
                                    .addComponent(jCBNhomHang_HangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel21)
                                    .addComponent(jTFGiaNhap_HangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTFGiaBan_HangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35)))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jButton5))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jButton13)
                                .addGap(12, 12, 12)
                                .addComponent(jButton14)))
                        .addGap(18, 18, 18)
                        .addComponent(jButton12)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane5, jScrollPane8});

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton12, jButton13, jButton14, jButton4, jButton5});

        jTFTimKiem_HangHoa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jBtnTimKiem_HangHoa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jBtnTimKiem_HangHoa.setText("Tìm kiếm");

        jBtnTatCa_HangHoa.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jBtnTatCa_HangHoa.setText("Tất cả");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã hàng", "Tên hàng", "Đơn vị tính", "Nhóm hàng", "Giá nhập", "Giá bán", "Thành phần", "Công dụng", "Tồn kho"
            }
        ));
        jScrollPane10.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jLabel36.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel36.setText("Danh sách hàng hóa");

        javax.swing.GroupLayout jPanelDanhSachHangHoaLayout = new javax.swing.GroupLayout(jPanelDanhSachHangHoa);
        jPanelDanhSachHangHoa.setLayout(jPanelDanhSachHangHoaLayout);
        jPanelDanhSachHangHoaLayout.setHorizontalGroup(
            jPanelDanhSachHangHoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDanhSachHangHoaLayout.createSequentialGroup()
                .addGroup(jPanelDanhSachHangHoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelDanhSachHangHoaLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTFTimKiem_HangHoa, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnTimKiem_HangHoa)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnTatCa_HangHoa)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelDanhSachHangHoaLayout.setVerticalGroup(
            jPanelDanhSachHangHoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDanhSachHangHoaLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDanhSachHangHoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDanhSachHangHoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelDanhSachHangHoaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtnTimKiem_HangHoa)
                            .addComponent(jBtnTatCa_HangHoa))
                        .addComponent(jTFTimKiem_HangHoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelHangLayout = new javax.swing.GroupLayout(jPanelHang);
        jPanelHang.setLayout(jPanelHangLayout);
        jPanelHangLayout.setHorizontalGroup(
            jPanelHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1180, Short.MAX_VALUE)
            .addGroup(jPanelHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelDanhSachHangHoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelHangLayout.setVerticalGroup(
            jPanelHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 678, Short.MAX_VALUE)
            .addGroup(jPanelHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelDanhSachHangHoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneQuanLy.addTab("Hàng hóa", jPanelHang);

        jPanelNhanVien.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelNhanVienComponentShown(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel39.setText("Danh sách nhân viên");

        jTextField11.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jButton6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton6.setText("Tìm kiếm");

        jButton7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton7.setText("Tất cả");

        jLabel40.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel40.setText("Thông tin nhân viên");

        jTFMaNhanVien_NV.setEditable(false);
        jTFMaNhanVien_NV.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel14.setText("Mã nhân viên");

        jTFTenNhanVien_NV.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel24.setText("Tên nhân viên");

        jLabel25.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel25.setText("Ngày vào làm");

        jLabel26.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel26.setText("Ngày sinh");

        jLabel27.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel27.setText("Giới tính");

        jCBBGioiTinh_NV.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel28.setText("Email");

        jTFEmail_NV.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel29.setText("Số điện thoại");

        jTFSDT_NV.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jCBBPhanQuyen_NV.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel30.setText("Quyền");

        jLabel31.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel31.setText("Địa chỉ");

        jTFDiaChi_NV.setColumns(20);
        jTFDiaChi_NV.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFDiaChi_NV.setRows(5);
        jScrollPane3.setViewportView(jTFDiaChi_NV);

        jLabel32.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel32.setText("Tài khoản");

        jTFTaiKhoan_NV.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel41.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel41.setText("Mật khẩu");

        jTFMatKhau_NV.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jBtnThemNhanVien.setText("Thêm");
        jBtnThemNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnThemNhanVienActionPerformed(evt);
            }
        });

        jBtnXoaNhanVien.setText("Xóa");
        jBtnXoaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnXoaNhanVienActionPerformed(evt);
            }
        });

        jBtnSuaNhanVien.setText("Sửa");
        jBtnSuaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSuaNhanVienActionPerformed(evt);
            }
        });

        jBtnLuuNhanVien.setText("Lưu");
        jBtnLuuNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLuuNhanVienActionPerformed(evt);
            }
        });

        jBtnLamMoiNhanVien.setText("Làm mới");
        jBtnLamMoiNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLamMoiNhanVienActionPerformed(evt);
            }
        });

        jDCNgayVaoLam_NV.setDateFormatString("yyyy-MM-dd");
        jDCNgayVaoLam_NV.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jDCNgaySinh_NV.setDateFormatString("yyyy-MM-dd");
        jDCNgaySinh_NV.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel40)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28))
                .addGap(23, 23, 23)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFMaNhanVien_NV, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBBGioiTinh_NV, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jDCNgaySinh_NV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDCNgayVaoLam_NV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTFTenNhanVien_NV))
                    .addComponent(jTFEmail_NV))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel32)
                            .addComponent(jLabel41))
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel31)
                        .addComponent(jLabel29)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFMatKhau_NV, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBBPhanQuyen_NV, 0, 283, Short.MAX_VALUE)
                    .addComponent(jTFTaiKhoan_NV, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTFSDT_NV)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnXoaNhanVien)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnThemNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBtnSuaNhanVien))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnLuuNhanVien)
                            .addComponent(jBtnLamMoiNhanVien))))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jCBBGioiTinh_NV, jTFEmail_NV, jTFMaNhanVien_NV, jTFTenNhanVien_NV});

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jCBBPhanQuyen_NV, jTFMatKhau_NV, jTFTaiKhoan_NV});

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBtnLamMoiNhanVien, jBtnLuuNhanVien, jBtnSuaNhanVien, jBtnThemNhanVien, jBtnXoaNhanVien});

        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jTFMaNhanVien_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jTFTenNhanVien_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDCNgayVaoLam_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(2, 2, 2)))
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDCNgaySinh_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCBBGioiTinh_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFEmail_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(jLabel41))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel31)
                                .addGap(44, 168, Short.MAX_VALUE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                    .addComponent(jTFSDT_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCBBPhanQuyen_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel30))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTFTaiKhoan_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel32))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTFMatKhau_NV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jBtnThemNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jBtnLuuNhanVien))
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(57, 57, 57)
                                        .addComponent(jBtnXoaNhanVien))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jBtnLamMoiNhanVien)
                                            .addComponent(jBtnSuaNhanVien))
                                        .addGap(57, 57, 57)))))
                        .addContainerGap())))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBtnLamMoiNhanVien, jBtnLuuNhanVien, jBtnSuaNhanVien, jBtnThemNhanVien, jBtnXoaNhanVien});

        jTableDanhSachNhanVien.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableDanhSachNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane11.setViewportView(jTableDanhSachNhanVien);

        javax.swing.GroupLayout jPanelDanhSachNhanVienLayout = new javax.swing.GroupLayout(jPanelDanhSachNhanVien);
        jPanelDanhSachNhanVien.setLayout(jPanelDanhSachNhanVienLayout);
        jPanelDanhSachNhanVienLayout.setHorizontalGroup(
            jPanelDanhSachNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDanhSachNhanVienLayout.createSequentialGroup()
                .addGroup(jPanelDanhSachNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDanhSachNhanVienLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelDanhSachNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelDanhSachNhanVienLayout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6)
                                .addGap(18, 18, 18)
                                .addComponent(jButton7)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jScrollPane11))
                .addContainerGap())
        );
        jPanelDanhSachNhanVienLayout.setVerticalGroup(
            jPanelDanhSachNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDanhSachNhanVienLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDanhSachNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addGroup(jPanelDanhSachNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField11)
                        .addComponent(jButton6)
                        .addComponent(jButton7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
        );

        javax.swing.GroupLayout jPanelNhanVienLayout = new javax.swing.GroupLayout(jPanelNhanVien);
        jPanelNhanVien.setLayout(jPanelNhanVienLayout);
        jPanelNhanVienLayout.setHorizontalGroup(
            jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelDanhSachNhanVien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelNhanVienLayout.setVerticalGroup(
            jPanelNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelDanhSachNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 798, Short.MAX_VALUE)
        );

        jTabbedPaneQuanLy.addTab("Nhân viên", jPanelNhanVien);

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 153, 255));
        jLabel23.setText("Danh sách nhà cung cấp");

        jTableDanhSachNhaCungCap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NCC", "Tên NCC", "Địa chỉ", "Số điện thoại", "Email", "Nợ cần trả", "Tổng tiền mua"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableDanhSachNhaCungCap);
        if (jTableDanhSachNhaCungCap.getColumnModel().getColumnCount() > 0) {
            jTableDanhSachNhaCungCap.getColumnModel().getColumn(0).setMaxWidth(50);
            jTableDanhSachNhaCungCap.getColumnModel().getColumn(7).setResizable(false);
        }

        jTextField12.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jButton8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton8.setText("Tìm kiếm");

        jButton9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButton9.setText("Tất cả");

        jLabel42.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(51, 153, 255));
        jLabel42.setText("Thông tin nhà cung cấp");

        jLabel43.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel43.setText("Mã nhà cung cấp");

        jTextField19.setEditable(false);
        jTextField19.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTextField20.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel44.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel44.setText("Tên nhà cung cấp");

        jLabel45.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel45.setText("Số điên thoại");

        jTextField21.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTextField22.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel46.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel46.setText("Email");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane6.setViewportView(jTextArea1);

        jLabel47.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel47.setText("Địa chỉ");

        jLabel48.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel48.setText("Ghi chú");

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane7.setViewportView(jTextArea2);

        jButton26.setText("jButton26");

        jButton27.setText("jButton27");

        jButton28.setText("jButton28");

        jButton29.setText("jButton29");

        jButton30.setText("jButton30");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(jLabel44)
                            .addComponent(jLabel45)
                            .addComponent(jLabel46))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField22)
                            .addComponent(jTextField21)
                            .addComponent(jTextField20)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel48)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jButton27)
                                .addGap(18, 18, 18)
                                .addComponent(jButton30))
                            .addComponent(jButton28)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton29)))))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel10Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton26, jButton27, jButton28, jButton29, jButton30});

        jPanel10Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField19, jTextField20, jTextField21, jTextField22});

        jPanel10Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane6, jScrollPane7});

        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton27)
                            .addComponent(jButton30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton28))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel43)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel47)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel44))
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel45)
                                    .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel48))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel46)
                                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel10Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton26, jButton27, jButton28, jButton29, jButton30});

        jPanel10Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jScrollPane6, jScrollPane7});

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField12)
                        .addComponent(jButton8)
                        .addComponent(jButton9))
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelNhaCungCapLayout = new javax.swing.GroupLayout(jPanelNhaCungCap);
        jPanelNhaCungCap.setLayout(jPanelNhaCungCapLayout);
        jPanelNhaCungCapLayout.setHorizontalGroup(
            jPanelNhaCungCapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelNhaCungCapLayout.setVerticalGroup(
            jPanelNhaCungCapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPaneQuanLy.addTab("Nhà cung cấp", jPanelNhaCungCap);

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 204, 204));
        jLabel22.setText("Danh sách khách hàng");

        txtTimKiemKhachHang.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        btnTimKiem.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnTatCaKhachHang.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnTatCaKhachHang.setText("Tất cả");
        btnTatCaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTatCaKhachHangActionPerformed(evt);
            }
        });

        jLabel49.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(51, 153, 255));
        jLabel49.setText("Thông tin khách hàng");

        jLabel50.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel50.setText("Mã khách hàng");

        txtMaKhachHang.setEditable(false);
        txtMaKhachHang.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel51.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel51.setText("Tên khách hàng");

        txtTenKhachHang.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel52.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel52.setText("Số điên thoại");

        txtSoDienThoai.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel53.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel53.setText("Email");

        txtEmail.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        txtDiaChi.setColumns(20);
        txtDiaChi.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtDiaChi.setRows(5);
        jScrollPane9.setViewportView(txtDiaChi);

        jLabel54.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel54.setText("Địa chỉ");

        btnThemKhachHang.setText("Thêm");
        btnThemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhachHangActionPerformed(evt);
            }
        });

        btnSuaKhachHang.setText("Sửa");
        btnSuaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKhachHangActionPerformed(evt);
            }
        });

        btnXoaKhachHang.setText("Xóa");
        btnXoaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKhachHangActionPerformed(evt);
            }
        });

        btnLuuKhachHang.setText("Lưu");
        btnLuuKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuKhachHangActionPerformed(evt);
            }
        });

        btnHuyKhachHang.setText("Hủy");
        btnHuyKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel49)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51)
                    .addComponent(jLabel52))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenKhachHang)
                    .addComponent(txtMaKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                    .addComponent(txtSoDienThoai))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53)
                    .addComponent(jLabel54))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEmail)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSuaKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoaKhachHang))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLuuKhachHang)
                    .addComponent(btnHuyKhachHang))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtMaKhachHang, txtSoDienThoai, txtTenKhachHang});

        jPanel11Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnHuyKhachHang, btnLuuKhachHang, btnSuaKhachHang, btnThemKhachHang, btnXoaKhachHang});

        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel49)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel50)
                                    .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel51))
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel54)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel52)
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLuuKhachHang))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSuaKhachHang)
                            .addComponent(btnHuyKhachHang))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaKhachHang))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel53)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(60, 60, 60))
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnHuyKhachHang, btnLuuKhachHang, btnSuaKhachHang, btnThemKhachHang, btnXoaKhachHang});

        tblKhachHangs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã khách hàng", "Tên Khách hàng", "Số điện thoại", "Email", "Địa chỉ"
            }
        ));
        tblKhachHangs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhachHangs);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnTimKiem)
                                .addGap(18, 18, 18)
                                .addComponent(btnTatCaKhachHang))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiemKhachHang)
                        .addComponent(btnTimKiem)
                        .addComponent(btnTatCaKhachHang))
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelKhachHangLayout = new javax.swing.GroupLayout(jPanelKhachHang);
        jPanelKhachHang.setLayout(jPanelKhachHangLayout);
        jPanelKhachHangLayout.setHorizontalGroup(
            jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1180, Short.MAX_VALUE)
            .addGroup(jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelKhachHangLayout.setVerticalGroup(
            jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
            .addGroup(jPanelKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneQuanLy.addTab("Khách hàng", jPanelKhachHang);
        jTabbedPaneQuanLy.addTab("Thống kê", jTabbedPaneThongKe);

        jLabel37.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel37.setText("Thông tin cá nhân");

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Mã nhân viên");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Tên");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("Ngày vào làm");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel9.setText("Ngày sinh");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel10.setText("Giới tính");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel11.setText("Địa chỉ");

        jLabel12.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel12.setText("Email");

        jLabel13.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel13.setText("Số điện thoại");

        jLabel15.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel15.setText("Tài khoản");

        jLabel38.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel38.setText("Mật khẩu");

        jTextField1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTextField2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jDateChooser1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jDateChooser2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextField3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTextField8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextArea3.setRows(5);
        jScrollPane1.setViewportView(jTextArea3);

        jTextField9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jTextField10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jButton1.setText("Sửa thông tin");

        jButton2.setText("Cập nhật");

        jButton3.setText("Đăng xuất");

        javax.swing.GroupLayout jPanelThongTinNhanVienLayout = new javax.swing.GroupLayout(jPanelThongTinNhanVien);
        jPanelThongTinNhanVien.setLayout(jPanelThongTinNhanVienLayout);
        jPanelThongTinNhanVienLayout.setHorizontalGroup(
            jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelThongTinNhanVienLayout.createSequentialGroup()
                .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelThongTinNhanVienLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel11)
                            .addComponent(jLabel38)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addComponent(jTextField8)
                            .addComponent(jScrollPane1)
                            .addComponent(jTextField9)
                            .addComponent(jTextField10)
                            .addComponent(jTextField1)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelThongTinNhanVienLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel37)))
                .addGap(660, 660, 660))
        );

        jPanelThongTinNhanVienLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2, jButton3});

        jPanelThongTinNhanVienLayout.setVerticalGroup(
            jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelThongTinNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelThongTinNhanVienLayout.createSequentialGroup()
                        .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addGroup(jPanelThongTinNhanVienLayout.createSequentialGroup()
                                .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14)
                                .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel38)
                                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanelThongTinNhanVienLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(146, Short.MAX_VALUE))
        );

        jPanelThongTinNhanVienLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2, jButton3});

        jTabbedPaneQuanLy.addTab("Tài khoản", jPanelThongTinNhanVien);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPaneQuanLy, javax.swing.GroupLayout.DEFAULT_SIZE, 1185, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneQuanLy, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void d(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_d
        // TODO add your handling code here:
    }//GEN-LAST:event_d

    private void jPanelNhanVienComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelNhanVienComponentShown
        // TODO add your handling code here:
        
        //jTableDanhSachNhanVien.removeAll();
        hienThiDanhSachNhanVien();
    }//GEN-LAST:event_jPanelNhanVienComponentShown

    private void jBtnThemNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnThemNhanVienActionPerformed
        // TODO add your handling code here:
        DTO_NhanVien nv = new DTO_NhanVien();
        ResetNhanVien();
        setEnable_txtNhanVien(true);
        setEnable_BtnNhanVien(false);
        jBtnLuuNhanVien.setEnabled(true);
        nhanVien =1;
    }//GEN-LAST:event_jBtnThemNhanVienActionPerformed

    private void jBtnLamMoiNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLamMoiNhanVienActionPerformed
        // TODO add your handling code here:
        ResetNhanVien();
    }//GEN-LAST:event_jBtnLamMoiNhanVienActionPerformed

    private void jBtnSuaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSuaNhanVienActionPerformed
         // TODO add your handling code here:
         setEnable_txtNhanVien(true);
         setEnable_BtnNhanVien(false);
         jBtnLuuNhanVien.setEnabled(true);
         nhanVien=2;
    }//GEN-LAST:event_jBtnSuaNhanVienActionPerformed

    private void jBtnLuuNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLuuNhanVienActionPerformed
         // TODO add your handling code here:
         //Thêm
         DAL_Account dALAccount = new DAL_Account();
         DTO_Account ac = new DTO_Account();
         DAL_NhanVien dalNV = new DAL_NhanVien();
         if(nhanVien==1)
         {
             DTO_NhanVien nv = new DTO_NhanVien();
             nv.setHoTen(jTFTenNhanVien_NV.getText());
             nv.setNgayVaoLam(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDCNgayVaoLam_NV.getDate())));
                nv.setNgaySinh(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDCNgaySinh_NV.getDate())));
             nv.setGioiTinh(jCBBGioiTinh_NV.getSelectedItem().toString());
             nv.setEmail(jTFEmail_NV.getText());
             nv.setSdt(jTFSDT_NV.getText());
             nv.setDiaChi(jTFDiaChi_NV.getText());
             int quyen =0;
             if(jCBBPhanQuyen_NV.getSelectedItem().toString()=="Quản lý")
                 quyen=1;
             else
                 quyen=2;
             nv.setIdPhanQuyen(quyen);
             dALAccount.taoTaiKhoan(jTFTaiKhoan_NV.getText(), jTFMatKhau_NV.getText());
             ac = dALAccount.layIDTaiKhoan(jTFTaiKhoan_NV.getText(), jTFMatKhau_NV.getText());
             System.out.println("IDAccount: "+ac.getId());
             nv.setIdAccount(ac.getId());
             //Kiểm tra
             if(kiemTraNhanVien())
             {
             //dalNV.themNhanVien(nv);
             int kt = dalNV.themNhanVien(nv);
             System.out.println(kt);
             if(kt!=0)
             {
                 quyen=0;
                 hienThiDanhSachNhanVien();
                 JOptionPane.showMessageDialog(rootPane, "Thêm thành công!");
             }
             else{
                 JOptionPane.showMessageDialog(rootPane, "Thêm thất bại");
             }
             }
             
             
         }
         else if(nhanVien==2)
         {
             if(kiemTraNhanVien())
             {
                String user = (String) jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 10);
                String pass = (String) jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 11);
                //System.out.println(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 1));
                ac = dALAccount.layIDTaiKhoan(user, pass);
                //sua tài khoản
                dALAccount.suaTaiKhoan(jTFTaiKhoan_NV.getText(), jTFMatKhau_NV.getText(),ac.getId());
                //set thông tin nhân viên
                DTO_NhanVien nv = new DTO_NhanVien();
                nv.setHoTen(jTFTenNhanVien_NV.getText());
                nv.setNgayVaoLam(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDCNgayVaoLam_NV.getDate())));
                nv.setNgaySinh(java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDCNgaySinh_NV.getDate())));
                 System.out.println("Ngày vào làm: "+java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDCNgayVaoLam_NV.getDate())));
                 System.out.println("Ngày sinh: "+java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDCNgaySinh_NV.getDate())));
                 System.out.println("Ngày vào làm nv: "+nv.getNgayVaoLam());
                 System.out.println("Ngày sinh nv: "+nv.getNgaySinh());
                
                 nv.setGioiTinh(jCBBGioiTinh_NV.getSelectedItem().toString());
                nv.setEmail(jTFEmail_NV.getText());
                nv.setSdt(jTFSDT_NV.getText());
                nv.setDiaChi(jTFDiaChi_NV.getText());
                int quyen =0;
                if(jCBBPhanQuyen_NV.getSelectedItem().toString()=="Quản lý")
                    quyen=1;
                else
                    quyen=2;
                nv.setIdPhanQuyen(quyen);
                nv.setId(ac.getId());
                dalNV.suaNhanVien(nv);
                //jTableDanhSachNhanVien.setRow
                hienThiDanhSachNhanVien();
             }
         }
    }//GEN-LAST:event_jBtnLuuNhanVienActionPerformed

    private void jBtnXoaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnXoaNhanVienActionPerformed
         // TODO add your handling code here:
         DAL_NhanVien dalNV = new DAL_NhanVien();
         DAL_Account dalAcc = new DAL_Account();
         DTO_Account account= new DTO_Account();
         if(JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không?","Xác nhận",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
         {
             String user = (String) jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 10);
             String pass = (String) jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 11);
             System.out.println(jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 1));
             account = dalAcc.layIDTaiKhoan(user, pass);
             dalAcc.xoaTaiKhoan(account.getId());
             dalNV.xoaNhanVien((int) jTableDanhSachNhanVien.getValueAt(jTableDanhSachNhanVien.getSelectedRow(), 1));
             hienThiDanhSachNhanVien();
         }
    }//GEN-LAST:event_jBtnXoaNhanVienActionPerformed

    private void jTabbedPaneQuanLyStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneQuanLyStateChanged
        // TODO add your handling code here:
        int index = jTabbedPaneQuanLy.getSelectedIndex();
        
        if (index == 4)
        {
            LoadDataKhachHang();
            EnableFormKhachHang(false);
            
            btnSuaKhachHang.setEnabled(false);
            btnXoaKhachHang.setEnabled(false);

        }
    }//GEN-LAST:event_jTabbedPaneQuanLyStateChanged

    private void btnThemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhachHangActionPerformed
        // TODO add your handling code here:
        
        resetFormKhachHang();
        EnableFormKhachHang(true);
        
        btnThemKhachHang.setEnabled(false);
        btnSuaKhachHang.setEnabled(false);
        btnXoaKhachHang.setEnabled(false);
        khachhang = 1;
    }//GEN-LAST:event_btnThemKhachHangActionPerformed

    private void btnXoaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKhachHangActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog((Component) null, "Bạn chắc chắn muốn xóa khách hàng ?",
        "alert", JOptionPane.OK_CANCEL_OPTION);
        
        if (result ==0 )
        {
            deleteKhachHang();
        }
    }//GEN-LAST:event_btnXoaKhachHangActionPerformed

    private void tblKhachHangsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangsMouseClicked
        // TODO add your handling code here:
        if (khachhang != 2)
        {
            LoadDataToForm();
            btnSuaKhachHang.setEnabled(true);
            btnXoaKhachHang.setEnabled(true);
        }
    }//GEN-LAST:event_tblKhachHangsMouseClicked

    private void btnSuaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKhachHangActionPerformed
        // TODO add your handling code here:
        
        EnableBtnKhachHang(false);
        EnableFormKhachHang(true);
        khachhang =2;
    }//GEN-LAST:event_btnSuaKhachHangActionPerformed

    private void btnTatCaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTatCaKhachHangActionPerformed
        // TODO add your handling code here:
        LoadDataKhachHang();
    }//GEN-LAST:event_btnTatCaKhachHangActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        bus_kh = new BUS_KhachHang();
        ArrayList<DTO_KhachHang> khachHangs = bus_kh.searchKhachHang(txtTimKiemKhachHang.getText());
        DefaultTableModel model = (DefaultTableModel) tblKhachHangs.getModel();
        model.setNumRows(0);
        if (khachHangs.size() > 0)
        {           
            for (DTO_KhachHang kh : khachHangs)
            {
                model.addRow(new Object[]{
                    kh.getId(), kh.getName(), kh.getSdt(), kh.getEmail(), kh.getAddress()
                });
            }
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnLuuKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuKhachHangActionPerformed
        // TODO add your handling code here:
        if (khachhang == 1)
        {
            addKhachHang();
        }
        else if (khachhang == 2)
        {
            updateKhachHang();
        }
    }//GEN-LAST:event_btnLuuKhachHangActionPerformed

    private void btnHuyKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyKhachHangActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog((Component) null, "Bạn chắc chắn muốn hủy ?",
        "alert", JOptionPane.OK_CANCEL_OPTION);
        
        if (result ==0 )
        {
            EnableFormKhachHang(false);
            resetFormKhachHang();
            khachhang = 0;
        }
    }//GEN-LAST:event_btnHuyKhachHangActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTrangChu().setVisible(true);
            }
        });
    }
    
    // Khách hàng

    private void LoadDataKhachHang()
    {
        bus_kh = new BUS_KhachHang();
        ArrayList<DTO_KhachHang> khachHangs = bus_kh.getListKhachHang();
        
        if (khachHangs.size() > 0)
        {
            DefaultTableModel model = (DefaultTableModel) tblKhachHangs.getModel();

//            model.setColumnIdentifiers(new Object[]{
//                "Mà khách hàng", "Tên khách hàng", "Số điện thoại", "Email", "Địa chỉ"
//            });
            model.setNumRows(0);
            
            for (DTO_KhachHang kh : khachHangs)
            {
                model.addRow(new Object[]{
                    kh.getId(), kh.getName(), kh.getSdt(), kh.getEmail(), kh.getAddress()
                });
            }
        }
    }
    
    private boolean kiemTraKhachHang()
    {
        String name = txtTenKhachHang.getText();
        if (name.trim().length() == 0 )
        {
            JOptionPane.showMessageDialog(rootPane, "Bạn phải nhập tên khách hàng !");
            return false;
        }
        
        
        try {
            int sdt = Integer.parseInt(txtSoDienThoai.getText());
            
            if (txtSoDienThoai.getText().trim().length() != 10 )
            {
                JOptionPane.showMessageDialog(rootPane, "Số điện thoại phải có 10 chữ số !");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Bạn phải nhập đúng số điện thoại !");
            return false;
        }
        
        if (!kiemTraEmail(txtEmail.getText()))
        {
            JOptionPane.showMessageDialog(rootPane, "Email không hợp lệ");
            return false;
        }
        
        if (txtDiaChi.getText().length() == 0 )
        {
            JOptionPane.showMessageDialog(rootPane, "Bạn phải nhập địa chỉ !");
            return false;
        }
        
        return true;
    }
    
    private boolean kiemTraEmail(String email)
    {
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        
        return email.matches(EMAIL_REGEX);
    }
    
    private void LoadDataToForm()
    {
        int index = tblKhachHangs.getSelectedRow();
        
        try {
                String id = tblKhachHangs.getValueAt(index, 0).toString();
                String name = (String) tblKhachHangs.getValueAt(index, 1);
                String sdt = (String) tblKhachHangs.getValueAt(index, 2);
                String email = (String) tblKhachHangs.getValueAt(index, 3);
                String address = (String) tblKhachHangs.getValueAt(index, 4);
                
                txtMaKhachHang.setText(id);
                txtTenKhachHang.setText(name);
                txtSoDienThoai.setText(sdt);
                txtEmail.setText(email);
                txtDiaChi.setText(address);
            } catch (Exception e) {
            } 
    }
    
    private void resetFormKhachHang()
    {
        txtMaKhachHang.setText("");
        txtTenKhachHang.setText("");
        txtSoDienThoai.setText("");
        txtEmail.setText("");
        txtDiaChi.setText("");
        
        btnThemKhachHang.setEnabled(true);
        btnSuaKhachHang.setEnabled(false);
        btnXoaKhachHang.setEnabled(false);
    }
    
    private void EnableFormKhachHang(boolean enable)
    {
       txtTenKhachHang.setEnabled(enable);
       txtSoDienThoai.setEnabled(enable);
       txtEmail.setEnabled(enable);
       txtDiaChi.setEnabled(enable);
       btnLuuKhachHang.setEnabled(enable);
       btnHuyKhachHang.setEnabled(enable);
    }
    
    private void EnableBtnKhachHang(boolean enable)
    {
        btnThemKhachHang.setEnabled(enable);
        btnSuaKhachHang.setEnabled(enable);
        btnXoaKhachHang.setEnabled(enable);
    }
    
    private void addKhachHang()
    {
        try {
            if (kiemTraKhachHang())
            {
                DTO_KhachHang kh = new DTO_KhachHang();
                kh.setName(txtTenKhachHang.getText());
                kh.setSdt(txtSoDienThoai.getText());
                kh.setEmail(txtEmail.getText());
                kh.setAddress(txtDiaChi.getText());
                
                bus_kh = new BUS_KhachHang();
                if (bus_kh.addKhachHang(kh))
                {            
                    JOptionPane.showMessageDialog(rootPane, "Thêm khách hàng thành công !");
                    LoadDataKhachHang();
                    EnableFormKhachHang(false);
                    resetFormKhachHang();
                    khachhang = 0;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Có lỗi khi thêm khách hàng !");
        }
    }
    
    private void updateKhachHang()
    {
        try {
            if (kiemTraKhachHang())
            {
                DTO_KhachHang kh = new DTO_KhachHang();
                kh.setId(Integer.parseInt(txtMaKhachHang.getText()));
                kh.setName(txtTenKhachHang.getText());
                kh.setSdt(txtSoDienThoai.getText());
                kh.setEmail(txtEmail.getText());
                kh.setAddress(txtDiaChi.getText());
                
                bus_kh = new BUS_KhachHang();
                if (bus_kh.updateKhachHang(kh))
                {            
                    JOptionPane.showMessageDialog(rootPane, "Cập nhật khách hàng thành công !");
                    LoadDataKhachHang();
                    resetFormKhachHang();
                    EnableFormKhachHang(false);
                    khachhang = 0;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Có lỗi khi cập nhật khách hàng !");
        }
    }
    
    private void deleteKhachHang()
    {
        int index = tblKhachHangs.getSelectedRow();
        
        try {
            bus_kh = new BUS_KhachHang();
                if (bus_kh.deleteKhachHang(Integer.parseInt(txtMaKhachHang.getText())))
                {
                    LoadDataKhachHang();
                    resetFormKhachHang();
                    JOptionPane.showMessageDialog(rootPane, "Xóa khách hàng thành công");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Có lỗi khi xóa khách hàng !");
            } 
    }
    // End KhachHang

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuyKhachHang;
    private javax.swing.JButton btnLuuKhachHang;
    private javax.swing.JButton btnSuaKhachHang;
    private javax.swing.JButton btnTatCaKhachHang;
    private javax.swing.JButton btnThemKhachHang;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaKhachHang;
    private javax.swing.JButton jBtnHuy_PhieuBan;
    private javax.swing.JButton jBtnHuy_PhieuNhap;
    private javax.swing.JButton jBtnLamMoiNhanVien;
    private javax.swing.JButton jBtnLapPhieu_PhieuBan;
    private javax.swing.JButton jBtnLapPhieu_PhieuNhap;
    private javax.swing.JButton jBtnLuuNhanVien;
    private javax.swing.JButton jBtnSuaNhanVien;
    private javax.swing.JButton jBtnTatCa_HangHoa;
    private javax.swing.JButton jBtnThemNhanVien;
    private javax.swing.JButton jBtnThem_PhieuBan;
    private javax.swing.JButton jBtnThem_PhieuNhap;
    private javax.swing.JButton jBtnTimKiem_HangHoa;
    private javax.swing.JButton jBtnXoaNhanVien;
    private javax.swing.JButton jBtnXoa_PhieuBan;
    private javax.swing.JButton jBtnXoa_PhieuNhap;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jCBBGioiTinh_NV;
    private javax.swing.JComboBox<String> jCBBPhanQuyen_NV;
    private javax.swing.JComboBox<String> jCBDonViTinh_HangHoa;
    private javax.swing.JComboBox<String> jCBKhachHang_PhieuBan;
    private javax.swing.JComboBox<String> jCBMaHang_PhieuBan;
    private javax.swing.JComboBox<String> jCBMaHang_PhieuNhap;
    private javax.swing.JComboBox<String> jCBNhaCungCap_PhieuNhap;
    private javax.swing.JComboBox<String> jCBNhanVien_PhieuBan;
    private javax.swing.JComboBox<String> jCBNhanVien_PhieuNhap;
    private javax.swing.JComboBox<String> jCBNhomHang_HangHoa;
    private javax.swing.JComboBox<String> jCBTenHang_MaPhieuBan;
    private javax.swing.JComboBox<String> jCBTenHang_MaPhieuNhap;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDCNgaySinh_NV;
    private com.toedter.calendar.JDateChooser jDCNgayVaoLam_NV;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooserNgayLapPhieu_PhieuBan;
    private com.toedter.calendar.JDateChooser jDateChooserNgayLapPhieu_PhieuNhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelChiTietHangHoa_PhieuNhap;
    private javax.swing.JPanel jPanelDanhSachChiTiet_PhieuBan;
    private javax.swing.JPanel jPanelDanhSachHangHoa;
    private javax.swing.JPanel jPanelDanhSachNhanVien;
    private javax.swing.JPanel jPanelDanhSachPhieuBan;
    private javax.swing.JPanel jPanelDanhSachPhieuNhap;
    private javax.swing.JPanel jPanelHang;
    private javax.swing.JPanel jPanelKhachHang;
    private javax.swing.JPanel jPanelNhaCungCap;
    private javax.swing.JPanel jPanelNhanVien;
    private javax.swing.JPanel jPanelPhieuBan;
    private javax.swing.JPanel jPanelPhieuNhap;
    private javax.swing.JPanel jPanelThongTinChung_PhieuBan;
    private javax.swing.JPanel jPanelThongTinChung_PhieuNhap;
    private javax.swing.JPanel jPanelThongTinHangHoa_PhieuBan;
    private javax.swing.JPanel jPanelThongTinHangHoa_PhieuNhap;
    private javax.swing.JPanel jPanelThongTinNhanVien;
    private javax.swing.JScrollPane jSPDanhSachPhieuBan;
    private javax.swing.JScrollPane jSPDanhSachPhieuNhap;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JScrollPane jScrollPaneChiTietPhieuBan;
    private javax.swing.JScrollPane jScrollPaneChiTietPhieuNhap;
    private javax.swing.JScrollPane jScrollPaneDSMatHang_PhieuBan;
    private javax.swing.JScrollPane jScrollPaneDSMatHang_PhieuNhap;
    private javax.swing.JTextArea jTACongDung;
    private javax.swing.JTextArea jTAGhiChu_PhieuBan;
    private javax.swing.JTextArea jTAGhiChu_PhieuNhap;
    private javax.swing.JTextArea jTAThanhPhan;
    private javax.swing.JTextArea jTFDiaChi_NV;
    private javax.swing.JTextField jTFEmail_NV;
    private javax.swing.JTextField jTFGiaBan_HangHoa;
    private javax.swing.JTextField jTFGiaBan_PhieuBan;
    private javax.swing.JTextField jTFGiaBan_PhieuNhap;
    private javax.swing.JTextField jTFGiaNhap_HangHoa;
    private javax.swing.JTextField jTFMaHang_HangHoa;
    private javax.swing.JTextField jTFMaHoaDon_DSPB;
    private javax.swing.JTextField jTFMaHoaDon_DSPN;
    private javax.swing.JTextField jTFMaNhanVien_NV;
    private javax.swing.JTextField jTFMaPhieuBan_PhieuBan;
    private javax.swing.JTextField jTFMaPhieuBan_PhieuNhap;
    private javax.swing.JTextField jTFMatKhau_NV;
    private javax.swing.JTextField jTFSDT_NV;
    private javax.swing.JTextField jTFSoLuong_PhieuBan;
    private javax.swing.JTextField jTFSoLuong_PhieuNhap;
    private javax.swing.JTextField jTFTaiKhoan_NV;
    private javax.swing.JTextField jTFTenHang_HangHoa;
    private javax.swing.JTextField jTFTenNhanVien_NV;
    private javax.swing.JTextField jTFThanhTien_PhieuBan;
    private javax.swing.JTextField jTFThanhTien_PhieuNhap;
    private javax.swing.JTextField jTFTimKiem_HangHoa;
    private javax.swing.JTextField jTFTongTien_PhieuBan;
    private javax.swing.JTextField jTFTongTien_PhieuNhap;
    private javax.swing.JTabbedPane jTabbedPaneHoaDon;
    private javax.swing.JTabbedPane jTabbedPaneQuanLy;
    private javax.swing.JTabbedPane jTabbedPaneThongKe;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTableChiTietPhieuBan;
    private javax.swing.JTable jTableChiTietPhieuNhap;
    private javax.swing.JTable jTableDanhSachNhaCungCap;
    private javax.swing.JTable jTableDanhSachNhanVien;
    private javax.swing.JTable jTableDanhSachPhieuBan;
    private javax.swing.JTable jTableDanhSachPhieuBan1;
    private javax.swing.JTable jTableMatHang_PhieuBan;
    private javax.swing.JTable jTableMatHang_PhieuNhap;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTable tblKhachHangs;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaKhachHang;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTimKiemKhachHang;
    // End of variables declaration//GEN-END:variables
}
