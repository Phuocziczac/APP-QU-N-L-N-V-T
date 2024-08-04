/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package form;

import DAO.ProductDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import model.PanelInfo;
import model.Products;

/**
 *
 * @author ASUS
 */
public class NewJFrame extends javax.swing.JFrame {

    int panelCount = 0;
    int gapX = 254;
    int gapY = 247;
    List<JPanel> panels;
    List<Products> listproducts;
    ProductDAO productDAO = new ProductDAO();
    List<PanelInfo> panelInfos = new ArrayList<>();
    private List<String> selectedProductNames = new ArrayList<>();

    private static final String PANEL_HEIGHT_KEY = "panel_height";
    private static final int DEFAULT_HEIGHT = 500; // Giá trị mặc định nếu không có giá trị được lưu trữ

    private void savePanelHeight(int height) {
        Preferences prefs = Preferences.userNodeForPackage(NewJFrame.class);
        prefs.putInt(PANEL_HEIGHT_KEY, height);
    }

    private int loadPanelHeight() {
        Preferences prefs = Preferences.userNodeForPackage(NewJFrame.class);
        return prefs.getInt(PANEL_HEIGHT_KEY, DEFAULT_HEIGHT);
    }

    private void loadPanelInfoFromFile() {
        File file = new File("panel_info.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<PanelInfo> loadedPanelInfos = (List<PanelInfo>) ois.readObject();
                panelInfos.addAll(loadedPanelInfos);  // Thêm tất cả các panelInfos từ tệp vào danh sách
                for (PanelInfo panelInfo : loadedPanelInfos) {
                    JPanel loadedPanel = createPanelFromInfo(panelInfo);
                    panels.add(loadedPanel);
                    jPanel3.add(loadedPanel);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found. Creating a new one.");
            savePanelInfoToFile();  // Tạo một tệp mới nếu nó không tồn tại
        }
        jPanel3.revalidate();
        jPanel3.repaint();
    }

// Trong phương thức createPanelFromInfo của NewJFrame
// Trong phương thức createPanelFromInfo của NewJFrame
    private JPanel createPanelFromInfo(PanelInfo panelInfo) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.red);

        JCheckBox cbx = new JCheckBox();
        cbx.setSelected(panelInfo.isCheckboxSelected());

        // Khởi tạo danh sách các tên sản phẩm đã chọn
        // Sử dụng dữ liệu từ PanelInfo để tạo JLabel và ImageIcon
        String productName = panelInfo.getProductName();
        String imagePath = panelInfo.getImagePath();
        Float price = panelInfo.getPrice();
        String priceText = String.format("%.2f", price); // Định dạng giá thành chuỗi với hai chữ số thập phân

        JLabel nameLabel = new JLabel(productName);
        JLabel imageLabel = new JLabel();

        // Tạo ImageIcon từ đường dẫn hình ảnh
        ImageIcon imageIcon = new ImageIcon(imagePath);
        // Thay đổi kích thước của hình ảnh
        Image scaledImage = imageIcon.getImage().getScaledInstance(192, 140, Image.SCALE_SMOOTH);
        // Tạo ImageIcon mới với kích thước đã điều chỉnh
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(scaledImageIcon);

        JLabel priceLabel = new JLabel(priceText);
        panel.add(cbx);
        panel.add(nameLabel);
        panel.add(imageLabel);
        panel.add(priceLabel);

        // Set the bounds for the panel
        int x = (countPanelsInJPanel() % 3) * gapX;
        int y = (countPanelsInJPanel() / 3) * gapY;
        panel.setBounds(x, y, 192, 187);

        return panel;
    }

    private int countPanelsInJPanel() {
        int count = 0;

        for (Component component : jPanel3.getComponents()) {
            System.out.println("Component class: " + component.getClass().getName());

            if (component instanceof JPanel && component != jPanel3) {
                count++;
            }
        }

        System.out.println("Number of panels: " + count);

        return count;
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.red);
        JCheckBox cbx = new JCheckBox();
        JLabel label = new JLabel("Tên sản phẩm");
        JLabel label2 = new JLabel("\n IMG");
        JLabel label3 = new JLabel("0");// Thiết lập văn bản cho label2

        panel.add(cbx);
        panel.add(label);
        panel.add(label2);
        panel.add(label3);

        // Sử dụng layout manager hoặc phương thức để cài đặt vị trí
        // Ví dụ sử dụng FlowLayout
        // Tính toán vị trí x và y cho panel mới
        int x = (countPanelsInJPanel() % 3) * gapX;
        int y = (countPanelsInJPanel() / 3) * gapY;
        System.out.println("x: " + x + ", y: " + y);

        // Set the bounds for the panel
        panel.setBounds(x, y, 192, 187);
        if (y > jPanel3.getHeight()) {
            int currentHeight = jPanel3.getHeight();
            int newHeight = currentHeight + 250;
            savePanelHeight(newHeight);
            jPanel3.setPreferredSize(new Dimension(jPanel3.getWidth(), newHeight));
            jPanel3.revalidate();
        }

        // Thêm panelInfo vào danh sách panelInfos
        panelInfos.add(new PanelInfo(cbx.isSelected(), label.getText(), label2.getText(), Float.parseFloat(label3.getText())));

        return panel;
    }

    private boolean checkCollision(JPanel newPanel) {
        if (panels == null) {
            System.err.println("panels is null");
            return false; // Hoặc có thể xử lý nếu panels là null ở đây
        }

        Rectangle newBounds = newPanel.getBounds();

        // Kiểm tra va chạm với các panel con của jPanel3
        for (Component component : jPanel3.getComponents()) {
            if (component instanceof JPanel) {
                JPanel existingPanel = (JPanel) component;
                Rectangle existingBounds = existingPanel.getBounds();

                if (newBounds.intersects(existingBounds)) {
                    return true; // Có va chạm
                }
            }
        }

        return false; // Không có va chạm
    }

    private void adjustPosition(JPanel panel) {
        // Điều chỉnh vị trí của panel mới (tăng x lên 10 pixel, giữ nguyên y)
        panel.setLocation(panel.getX() + 10, panel.getY());
    }

    private void removeSelectedPanel() {
        try {
            // Tạo một danh sách tạm thời để lưu trữ các sản phẩm cần xóa
            List<Products> productNamesToRemove = new ArrayList<>();

            // Xác định các sản phẩm được chọn để xóa
            for (Component component : jPanel3.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel panel = (JPanel) component;
                    for (Component innerComponent : panel.getComponents()) {
                        if (innerComponent instanceof JCheckBox) {
                            JCheckBox checkBox = (JCheckBox) innerComponent;
                            if (checkBox.isSelected()) {
                                JLabel nameLabel = (JLabel) panel.getComponent(1); // Lấy JLabel chứa tên sản phẩm
                                String productName = nameLabel.getText(); // Lấy tên sản phẩm từ JLabel
                                Products productToRemove = new Products(productName); // Tạo đối tượng Products từ tên sản phẩm
                                productNamesToRemove.add(productToRemove); // Thêm tên sản phẩm vào danh sách
                            }
                        }
                    }
                }
            }

            // Thực hiện xóa các sản phẩm trong CSDL
            for (Products product : productNamesToRemove) {
                productDAO.delete(product);
            }
loadPanelInfoFromDatabase();
            // Cập nhật giao diện người dùng sau khi xóa dữ liệu từ CSDL
            // Ví dụ: removeSelectedPanelsFromUI(productNamesToRemove);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void savePanelInfoToFile() {
        // Lưu panelInfos vào tệp tin
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("panel_info.ser"))) {
            oos.writeObject(new ArrayList<>(panelInfos)); // Save the list of PanelInfo
            System.out.println("Save successful");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Trong vòng lặp
    private void applySavedPanelSize() {
        int savedHeight = loadPanelHeight();
        jPanel3.setPreferredSize(new Dimension(jPanel3.getWidth(), savedHeight));
        jPanel3.revalidate();
    }

    public void editInfo() {
        for (Component component : jPanel3.getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                if (panel.getComponentCount() > 0) {
                    Component innerComponent = panel.getComponent(0);
                    if (innerComponent instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) innerComponent;
                        if (checkBox.isSelected()) {

                            JLabel nameLabel = (JLabel) panel.getComponent(1);
                            JLabel priceLabel = (JLabel) panel.getComponent(3);
                            String productName = nameLabel.getText();
                            String priceStr = priceLabel.getText();
                            float price = Float.parseFloat(priceStr);

                            // Lấy thông tin sản phẩm từ panelInfos
                            String category = productDAO.selectById(productName).getCategoriesName(); // Thay bằng cách lấy category tương ứng với sản phẩm
                            String description = productDAO.selectById(productName).getDescription(); // Thay bằng cách lấy description tương ứng với sản phẩm
                            int quantity = productDAO.selectById(productName).getQuantityInStock(); // Thay bằng cách lấy quantity tương ứng với sản phẩm
                            String imagePath = productDAO.selectById(productName).getImgProduct(); // Thay bằng cách lấy đường dẫn ảnh tương ứng với sản phẩm

                            // Mở cửa sổ EditProduct và truyền thông tin sản phẩm
                            EditProduct editProduct = new EditProduct(this, rootPaneCheckingEnabled);
                            editProduct.setProductInfo(productName, category, description, price, quantity, imagePath);
                            editProduct.setVisible(true);
                        }
                    }
                }
            }
        }
    }

    void openEditProduct() {
        EditProduct editProduct = new EditProduct(this, rootPaneCheckingEnabled);
        editProduct.setVisible(true);

    }

    private void loadPanelInfoFromDatabase() {
        panelInfos.clear();

        // Thực hiện tải dữ liệu từ cơ sở dữ liệu và cập nhật danh sách panelInfos
        // Đoạn mã xử lý việc tải dữ liệu từ cơ sở dữ liệu ở đây
        // Sau khi tải dữ liệu, cần cập nhật lại giao diện người dùng
        jPanel3.removeAll();
        List<PanelInfo> panelInfoList = PanelInfo.createPanelInfoListFromDatabase();

        for (PanelInfo panelInfo : panelInfoList) {
            JPanel newPanel = createPanelFromInfo(panelInfo);
            panels.add(newPanel);
            jPanel3.add(newPanel);
        }

        jPanel3.revalidate();
        jPanel3.repaint();
    }

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
        panels = new ArrayList<>();
        panelInfos = new ArrayList<>();
        this.productDAO = productDAO;
// Thêm dòng này để khởi tạo productDAO
        loadPanelInfoFromDatabase();

        applySavedPanelSize();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//      updatePanelInfos();

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
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );

        jButton3.setText("LOAD");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("SAVE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("REMOVE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnedit.setText("EDIT");
        btnedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditActionPerformed(evt);
            }
        });

        jButton5.setText("jButton5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("loaddulieu");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Pay");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnedit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(btnedit)
                    .addComponent(jButton1)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jButton7))
                .addGap(0, 26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SwingUtilities.invokeLater(() -> {
            JPanel newPanel = createPanel();

            while (checkCollision(newPanel)) {
                adjustPosition(newPanel);
            }

            panels.add(newPanel);
            jPanel3.add(newPanel);

            savePanelInfoToFile();  // Lưu thông tin của tất cả các panel sau khi thêm panel mới
            jPanel3.revalidate();
            jPanel3.repaint();
        });
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        savePanelInfoToFile();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        loadPanelInfoFromFile();   // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        removeSelectedPanel();  // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        editInfo();

// TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btneditActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        loadPanelInfoFromDatabase();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        List<Products> selectedProductNames = new ArrayList<>();
// Lấy danh sách các tên sản phẩm được chọn từ các JCheckBox
        for (Component component : jPanel3.getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (Component innerComponent : panel.getComponents()) {
                    if (innerComponent instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) innerComponent;
                        if (checkBox.isSelected()) {
                            JLabel nameLabel = (JLabel) panel.getComponent(1);
                            JLabel priceLabel = (JLabel) panel.getComponent(3);
                            String productName = nameLabel.getText();
                            String priceStr = priceLabel.getText();

                            // Chuyển đổi giá từ dạng chuỗi sang dạng số
                            try {
                                float price = Float.parseFloat(priceStr);

                                // Tạo đối tượng Products mới và thêm vào danh sách
                                selectedProductNames.add(new Products(productName, price));
                            } catch (NumberFormatException e) {
                                // Xử lý nếu giá không hợp lệ
                                System.err.println("Giá không hợp lệ: " + priceStr);
                            }
                        }
                    }
                }
            }
        }

        PAYform payForm = new PAYform(selectedProductNames);
// Hiển thị dữ liệu trên bảng khi tạo mới PAYform
        payForm.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnedit;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
