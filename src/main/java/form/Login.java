/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package form;

import DAO.UserDAO;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import model.User;

/**
 *
 * @author ASUS
 */
public class Login extends javax.swing.JFrame {

    ImageIcon img = new ImageIcon("C:/Users/ASUS/OneDrive/Documents/NetBeansProjects/DA01/src/main/resources/icon/logo-dien-tu-dep-01-removebg-preview.png");
    Image icon = img.getImage();

    private int x = 0;
    UserDAO userDAO;
    User selectUser;

    /**
     * Creates new form Login
     * @param selectUser
     */
    public Login(User selectUser) {
        initComponents();
        setIconImage(icon);
        this.userDAO = new UserDAO();
        List<User> listUser = new ArrayList<>();
        setLocationRelativeTo(null);
        setPlaceholder(txtsdt, "Enter your phone number...");
        setPlaceholder(txtpass, "Enter your password...");
        setPlaceholder(txtName, "Enter your name...");
        setPlaceholder(txtSDT, "Enter your phone number...");
        setPlaceholder(txtPassword, "Enter your password...");
        setPlaceholder(txtConfirmPass, "Enter your password...");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void time1() {
        Timer time1 = new Timer(10, (e) -> {
            x = signupform.getX() - 4;
            if (x > -signupform.getWidth()) {
                signupform.setLocation(x, signupform.getY());
                Login.setEnabled(false);
            } else {
                ((Timer) e.getSource()).stop();
                System.out.println("Timer stopped");
                Login.setEnabled(true);
            }
        });
        time1.start();
    }

    public void time2() {

        Timer time2 = new Timer(10, (e) -> {
            x = signupform.getX() + 4;
            System.out.println(x); // In giá trị của x để theo dõi
            if (x < 0) {
                signupform.setLocation(x, signupform.getY());
            } else {
                ((Timer) e.getSource()).stop();
                System.out.println("Timer stopped");
            }
        });
        time2.start();

    }

    public void setPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
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
        loginform = new javax.swing.JPanel();
        signupform = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblForgetpass = new javax.swing.JLabel();
        Signup = new javax.swing.JLabel();
        btnSignIn = new javax.swing.JButton();
        txtsdt = new javax.swing.JTextField();
        txtpass = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Login = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        txtConfirmPass = new javax.swing.JTextField();
        btnSignup = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 362, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");

        loginform.setBackground(new java.awt.Color(51, 255, 255));
        loginform.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        signupform.setBackground(new java.awt.Color(153, 255, 255));
        signupform.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Login");
        signupform.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 130, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo-dien-tu-dep-01-removebg-preview.png"))); // NOI18N
        signupform.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-50, 0, 220, 110));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("SDT");
        signupform.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(47, 141, 37, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Password");
        signupform.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 60, -1));

        lblForgetpass.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblForgetpass.setText("Forget password ?");
        lblForgetpass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblForgetpassMouseClicked(evt);
            }
        });
        signupform.add(lblForgetpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 110, -1));

        Signup.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        Signup.setText("Signup");
        Signup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SignupMouseClicked(evt);
            }
        });
        signupform.add(Signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 60, -1));

        btnSignIn.setBackground(new java.awt.Color(51, 153, 255));
        btnSignIn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSignIn.setForeground(new java.awt.Color(255, 255, 255));
        btnSignIn.setText("Sumit");
        btnSignIn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignInActionPerformed(evt);
            }
        });
        signupform.add(btnSignIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 100, 40));

        txtsdt.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        signupform.add(txtsdt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 200, -1));

        txtpass.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        signupform.add(txtpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 200, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Create account ?");
        signupform.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 100, -1));

        loginform.add(signupform, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 380));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo-dien-tu-dep-01-removebg-preview.png"))); // NOI18N
        jLabel10.setText("jLabel2");
        loginform.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(-60, -30, 200, -1));

        jLabel11.setText("Name");
        loginform.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 37, -1));

        jLabel12.setText("SDT");
        loginform.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 37, -1));

        jLabel7.setText("Password");
        loginform.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 60, -1));

        jLabel8.setText("Enter the password");
        loginform.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 255, 110, -1));

        Login.setBackground(new java.awt.Color(51, 255, 255));
        Login.setFont(new java.awt.Font("Segoe UI Semibold", 3, 14)); // NOI18N
        Login.setText("Login");
        Login.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });
        loginform.add(Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, 50, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("Signup");
        loginform.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 130, -1));

        txtName.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        loginform.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 200, -1));

        txtSDT.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        loginform.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 200, -1));

        txtPassword.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        loginform.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 200, -1));

        txtConfirmPass.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        txtConfirmPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConfirmPassActionPerformed(evt);
            }
        });
        loginform.add(txtConfirmPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 200, -1));

        btnSignup.setBackground(new java.awt.Color(51, 153, 255));
        btnSignup.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSignup.setForeground(new java.awt.Color(255, 255, 255));
        btnSignup.setText("Sumit");
        btnSignup.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });
        loginform.add(btnSignup, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 100, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginform, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(loginform, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SignupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SignupMouseClicked
        time1();
        // TODO add your handling code here:
    }//GEN-LAST:event_SignupMouseClicked

    private void btnSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignInActionPerformed

      try {
    String sdt = txtsdt.getText().trim();
    String pass = txtpass.getText().trim();

    // Kiểm tra trước đầu vào
    if (sdt.isEmpty() || pass.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại và mật khẩu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Kiểm tra user theo SDT
     selectUser = userDAO.selectUserBySDT(sdt);
    if (selectUser == null) {
        JOptionPane.showMessageDialog(this, "Số điện thoại không tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Kiểm tra mật khẩu
   
    if (selectUser.getPassword() == null) {
        JOptionPane.showMessageDialog(this, "Mật khẩu không chính xác.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

  
    // Đăng nhập thành công, mở MainForm
    new Mainform(selectUser).setVisible(true);
    dispose();

} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi đăng nhập.", "Lỗi", JOptionPane.ERROR_MESSAGE);
}


        // Check if the user exists and the password is correct
// TODO add your handling code here:
    }//GEN-LAST:event_btnSignInActionPerformed

    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignupActionPerformed
        try {
    String name = txtName.getText().trim();
    String sdt = txtSDT.getText().trim();
    String password = txtPassword.getText();
    String confirmPass = txtConfirmPass.getText();
System.out.println("Password: " + password);
System.out.println("Confirm Password: " + confirmPass);
    // Kiểm tra đầu vào
    if (name.isEmpty() || sdt.isEmpty() || password.isEmpty() || confirmPass.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!password.equalsIgnoreCase(confirmPass)) {
        JOptionPane.showMessageDialog(this, "Mật khẩu và xác nhận mật khẩu không khớp.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }
System.out.println("Password: " + password);
System.out.println("Confirm Password: " + confirmPass);

    // Tạo đối tượng User mới
    User newUser = new User(name, false, sdt, password);

    // Thêm mới người dùng
    userDAO.insert(newUser);

    // Hiển thị thông báo thành công
    JOptionPane.showMessageDialog(this, "Đã thêm người dùng thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

    // Cập nhật bảng trong form chính nếu cần
    // ...

} catch (Exception e) {
    // Xử lý các loại lỗi cụ thể
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi thêm người dùng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
}
       // TODO add your handling code here:
    }//GEN-LAST:event_btnSignupActionPerformed

    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
        time2();    // TODO add your handling code here:
    }//GEN-LAST:event_LoginActionPerformed

    private void txtConfirmPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConfirmPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConfirmPassActionPerformed

    private void lblForgetpassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForgetpassMouseClicked
Forgetpassform fg= new Forgetpassform(this, true);
fg.setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_lblForgetpassMouseClicked

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                User selectUser = null;
                new Login(selectUser).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Login;
    private javax.swing.JLabel Signup;
    private javax.swing.JButton btnSignIn;
    private javax.swing.JButton btnSignup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblForgetpass;
    private javax.swing.JPanel loginform;
    private javax.swing.JPanel signupform;
    private javax.swing.JTextField txtConfirmPass;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtpass;
    private javax.swing.JTextField txtsdt;
    // End of variables declaration//GEN-END:variables
}