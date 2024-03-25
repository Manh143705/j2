/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package j2;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class QLNV extends javax.swing.JFrame {
private List<Staff> list = new ArrayList<>();
    DefaultTableModel tblModel;
    public QLNV() {
        initComponents();
        setLocationRelativeTo(null);
        initThamNien();
        clock();
    }
     String time;
    SimpleDateFormat timeFormat;
    Calendar calendar;

    void setTime() {
        while (true) {
            time = timeFormat.format(calendar.getInstance().getTime());
            lbl_DongHo.setText(time);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void clock() {
        timeFormat = new SimpleDateFormat("HH : mm : ss");
        new Thread(this::setTime).start();
    }

    public  void initData(){
        list.add(new Staff("KHv@1","Nguyễn Thúy Hằng",false,2));
        list.add(new Staff("KHv@2","Trần Tuấn Phong",true,14));
        list.add(new Staff("KHv@3","Vũ Văn Nguyên",true,13));
        list.add(new Staff("KHv@4","Nguyễn Hoàng Tiến",true,1));
        list.add(new Staff("KHv@5","Chu Thị Ngân",false,5));
    }
    public void initTable(){
        tblModel = (DefaultTableModel) tblStaff.getModel();
        String[] cols = new String[] {"Mã NV","Tên NV","Giới tính","Thâm niên","Thưởng"};
        tblModel.setColumnIdentifiers(cols);
    }
    public void fillToTable(){
        tblModel.setRowCount(0);
        for (Staff nv : list){
            Object[] row = new Object[]{nv.getMaNV(),nv.getTenNV(),nv.getGioiTinh()?"Nam" : "Nữ",nv.getThamNien(),nv.tinhThuong()};
            tblModel.addRow(row);
        }
    }
    public void initThamNien(){
        String[] thamNien = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};
        DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<>(thamNien);
        cboThamNien.setModel(boxModel);
    }
   public void addStaff() {
    String maNV = txtMa.getText().trim();
    String tenNV = txtName.getText().trim();
    String thamNienStr = cboThamNien.getSelectedItem().toString();
    int thamNien;
    try {
        thamNien = Integer.parseInt(thamNienStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Thâm niên không hợp lệ!");
        return;
    }
    boolean gioiTinhNam = rbnNam.isSelected();
    boolean gioiTinhNu = rbnNu.isSelected(); // Giả sử rbnNu là tên biến của radio button cho giới tính nữ
    if (maNV.isEmpty() || tenNV.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Mã nhân viên và tên không được để trống!");
        return;
    }
    if (!gioiTinhNam && !gioiTinhNu) {
        JOptionPane.showMessageDialog(this, "Bạn phải chọn giới tính!");
        return;
    }
    Staff nv = new Staff(maNV, tenNV, gioiTinhNam, thamNien); // Giả sử Staff chỉ chấp nhận giới tính nam là true hoặc false
    int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thêm nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
    if (confirmResult == JOptionPane.YES_OPTION) {
        list.add(nv);
        fillToTable();
        txtMa.setText("");
        txtName.setText("");
        cboThamNien.setSelectedIndex(0);
        rbnNam.setSelected(true);
        rbnNu.setSelected(false);
        JOptionPane.showMessageDialog(this, "Đã thêm nhân viên mới thành công!");
    }
}

public void removeStaff() {
    if (tblStaff.getSelectedRow() != -1) {
        int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirmResult == JOptionPane.YES_OPTION) {
            int index = tblStaff.getSelectedRow();
            list.remove(index);
            fillToTable();
            JOptionPane.showMessageDialog(this, "Đã xoá!");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa!");
    }
}
    
public void showDetail() {
    int index = tblStaff.getSelectedRow();
    if (index != -1) {
        txtMa.setText(list.get(index).getMaNV());
        txtName.setText(list.get(index).getTenNV());
        cboThamNien.setSelectedItem(list.get(index).getThamNien());
        if (list.get(index).isGender()) {
            rbnNam.setSelected(true);
        } else {
            rbnNu.setSelected(true);
        }
    }
}
   public void updateStaff() {
    int index = tblStaff.getSelectedRow();
    if (index >= 0) {
        Staff nv = list.get(index);
        String maNV = txtMa.getText();
        String tenNV = txtName.getText();
        
        if (maNV.isEmpty() || tenNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên và tên nhân viên không được để trống!");
            return;
        }
        
        nv.setMaNV(maNV);
        nv.setTenNV(tenNV);
        nv.setThamNien(cboThamNien.getSelectedIndex() + 1);
        boolean gender = rbnNam.isSelected();
        nv.setGender(gender);
        
        fillToTable();
        JOptionPane.showMessageDialog(this, "Đã cập nhật !");
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên !");
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cboThamNien = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStaff = new javax.swing.JTable();
        rbnNam = new javax.swing.JRadioButton();
        rbnNu = new javax.swing.JRadioButton();
        lbl_DongHo = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Quản Lý Nhân Viên");

        jLabel8.setText("Mã nhân viên");

        txtMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaActionPerformed(evt);
            }
        });

        jLabel9.setText("Tên nhân viên");

        jLabel10.setText("Giới tính");

        jLabel11.setText("Thâm niên");

        cboThamNien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboThamNien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThamNienActionPerformed(evt);
            }
        });

        jLabel12.setText("Tháng");

        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnReset.setText("Ghi file");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        tblStaff.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblStaff.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblStaffAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                tblStaffAncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tblStaff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStaffMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblStaff);

        buttonGroup1.add(rbnNam);
        rbnNam.setText("Nam");

        buttonGroup1.add(rbnNu);
        rbnNu.setText("Nữ");

        lbl_DongHo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl_DongHo.setForeground(new java.awt.Color(255, 0, 0));
        lbl_DongHo.setText("00:02:30");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(btnUpdate)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAdd)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)
                                .addGap(18, 18, 18)
                                .addComponent(btnReset))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cboThamNien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbnNam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbnNu))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                                .addComponent(txtMa))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(51, 51, 51)
                        .addComponent(lbl_DongHo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbl_DongHo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(rbnNam)
                    .addComponent(rbnNu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cboThamNien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnReset))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        removeStaff();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setSelectedFile(new File("NhanVien.txt"));
    int result = fileChooser.showSaveDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        if (!file.getName().endsWith(".txt")) {
            file = new File(file.getPath() + ".txt");
        }
        int confirmResult = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn lưu thông tin nhân viên vào file này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirmResult == JOptionPane.YES_OPTION) {
            try (FileWriter fw = new FileWriter(file)) {
                fw.write("MaNV;TenNV;ThamNien;GioiTinh\n");
                for (Staff nv : list) {
                    fw.write(nv.getMaNV() + ";" + nv.getTenNV() + ";"
                            + nv.getThamNien() + ";" + nv.isGender() + "\n");
                }
                JOptionPane.showMessageDialog(this, "Đã lưu thông tin nhân viên vào file!");

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi ghi file!");
            }
        }
    }
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addStaff();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateStaff();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaActionPerformed

    }//GEN-LAST:event_txtMaActionPerformed

    private void cboThamNienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThamNienActionPerformed

    }//GEN-LAST:event_cboThamNienActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        initData();
        initTable();
        fillToTable();
    }//GEN-LAST:event_formWindowOpened

    private void tblStaffAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblStaffAncestorAdded

    }//GEN-LAST:event_tblStaffAncestorAdded

    private void tblStaffAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblStaffAncestorMoved

    }//GEN-LAST:event_tblStaffAncestorMoved

    private void tblStaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStaffMouseClicked
        showDetail();
    }//GEN-LAST:event_tblStaffMouseClicked

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(QLNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLNV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboThamNien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_DongHo;
    private javax.swing.JRadioButton rbnNam;
    private javax.swing.JRadioButton rbnNu;
    private javax.swing.JTable tblStaff;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
