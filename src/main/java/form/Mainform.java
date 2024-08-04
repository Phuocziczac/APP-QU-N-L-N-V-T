/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package form;

import DAO.CategoryDAO;
import DAO.OderDAO;
import DAO.OrderDetailDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import helper.ExcelExporter;
import helper.Xlmage;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import model.Categories;
import model.Order;
import model.Orderdetail;
import model.PanelInfo;
import model.Products;
import model.User;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import   com.itextpdf.layout.Document;
import java.io.FileNotFoundException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ASUS
 */
public class Mainform extends javax.swing.JFrame {

    private int x = 0;
    List<User> listUser = new ArrayList<>();
    UserDAO UserDAO;
    List<Categories> listCategory = new ArrayList<>();
    CategoryDAO categoryDAO;
    int panelCount = 0;
    int gapX = 254;
    int gapY = 247;
    List<JPanel> panels;
    ProductDAO productDAO;
    List<PanelInfo> panelInfos = new ArrayList<>();
    List<Order> listoder = new ArrayList<>();
    OderDAO oderDAO;
    List<Orderdetail> listOrderID = new ArrayList<>();
    OrderDetailDAO oderdetailDAO;
    User selectUser;

    // Hoặc false tùy thuộc vào trạng thái mong muốn
    /**
     * Creates new form Mainform
     *
     * @param selectUser
     */
    public Mainform(User selectUser) {
        initComponents();
        setLocationRelativeTo(null);
        this.UserDAO = new UserDAO();
        this.selectUser = selectUser;
        // Initialize User
        this.categoryDAO = new CategoryDAO();
        this.productDAO = new ProductDAO();
        this.oderDAO = new OderDAO();
        panels = new ArrayList<>();  // Khởi tạo biến panels
        this.listoder = new ArrayList<>();
        panelInfos = new ArrayList<>();
        this.listOrderID = new ArrayList<>();
        this.oderdetailDAO = new OrderDetailDAO();

        loadPanelInfoFromDatabase();
        fillToCbxReport();
        applySavedPanelSizeFood();
        applySavedPanelSizeDrink();

//      updatePanelInfos();
    }

    private static void setEnabledAll(Container container, boolean enabled) {
        for (Component component : container.getComponents()) {
            component.setEnabled(enabled);
            if (component instanceof Container) {
                setEnabledAll((Container) component, enabled);
            }
        }
    }

// private boolean areAllCheckBoxesSelected(JPanel mainPanel) {
//    // Lấy danh sách các component trong JPanel chứa nhiều JPanel con
// 
//}
    public void fillToTableUser() {
        DefaultTableModel model = (DefaultTableModel) UserTable.getModel();

        model.setRowCount(0);

        try {
            listUser = UserDAO.selectAll();

            for (User u : listUser) {
                Object[] row = new Object[]{u.getUserID(), u.getName(), u.getSDT(), u.getEmail(), u.getAdress(), u.getAge(), u.isRole(), u.getWage(), u.getPassword(), u.getImg()};
                model.addRow(row);
            }

            System.out.println("Data loaded successfully"); // In ra console để kiểm tra
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getSelectedUserData() {
        int selectedRowIndex = UserTable.getSelectedRow();

        if (selectedRowIndex >= 0) {

            int userID = (int) UserTable.getValueAt(selectedRowIndex, 0);
            String name = UserTable.getValueAt(selectedRowIndex, 1).toString();
            String sdt = UserTable.getValueAt(selectedRowIndex, 2).toString();
            String email = UserTable.getValueAt(selectedRowIndex, 3).toString();
            String address = UserTable.getValueAt(selectedRowIndex, 4).toString();
            String age = UserTable.getValueAt(selectedRowIndex, 5).toString();
            boolean role = (boolean) UserTable.getValueAt(selectedRowIndex, 6);
            float wage = (float) UserTable.getValueAt(selectedRowIndex, 7);
            String password = UserTable.getValueAt(selectedRowIndex, 8).toString();
            String Img = UserTable.getValueAt(selectedRowIndex, 9).toString();

            return new User(userID, name, role, age, sdt, email, password, address, wage, Img);
        }

        return null;
    }

    public void refreshUserData() {
        fillToTableUser();
        // Gọi lại hàm fillToTableUser hoặc làm bất kỳ điều gì khác để cập nhật dữ liệu trên bảng
    }

// When you want to open the EditUser form
    private void openEditUserForm() {
        User selectedUser = getSelectedUserData();

        if (selectedUser != null) {
            EditUser editUserForm = new EditUser(this, true, selectedUser);
            editUserForm.setVisible(true);
        }
    }

    private void openEditUserForm(User foundUser) {
        if (foundUser != null) {
            EditUser editUserForm = new EditUser(this, true, foundUser);
            editUserForm.setVisible(true);
        }
    }

    public void fillToTableCategory() {
        DefaultTableModel model = (DefaultTableModel) tblCategory.getModel();
        model.setRowCount(0);
        listCategory = categoryDAO.selectAll();
        try {
            for (Categories x : listCategory) {
                Object[] row = new Object[]{x.getCategoriesID(), x.getName()};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
   

    public void fillToCbxReport() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) TimeOrder.getModel();
        List<Order> dateOrder = oderDAO.selectAllDates();
        for (Order order : dateOrder) {
            model.addElement(order.getOrderDate());
        }
        TimeOrder.setModel(model);
    }

    public Categories getSelectedDataCategory() {
        int selectedindex = tblCategory.getSelectedRow();
        if (selectedindex >= 0) {
            int categoryID = (int) tblCategory.getValueAt(selectedindex, 0);
            String Name = tblCategory.getValueAt(selectedindex, 1).toString();
            return new Categories(categoryID, Name);
        }
        return null;
    }

    public void openEditCategoryform() {
        Categories selectedcategory = getSelectedDataCategory();
        if (selectedcategory != null) {
            EditCategory editcategory = new EditCategory(this, true, selectedcategory);
            editcategory.setVisible(true);

        }
    }

    private static final String PANEL_HEIGHT_KEY = "panel_height";
    private static final int DEFAULT_HEIGHT = 500; // Giá trị mặc định nếu không có giá trị được lưu trữ

    private void savePanelHeight(int height) {
        Preferences prefs = Preferences.userNodeForPackage(Mainform.class);
        prefs.putInt(PANEL_HEIGHT_KEY, height);
    }

    private int loadPanelHeight() {
        Preferences prefs = Preferences.userNodeForPackage(Mainform.class);
        return prefs.getInt(PANEL_HEIGHT_KEY, DEFAULT_HEIGHT);
    }

//    private void loadPanelInfoFromFile() {
//        File file = new File("panel_info.ser");
//        if (file.exists()) {
//            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
//                List<PanelInfo> loadedPanelInfos = (List<PanelInfo>) ois.readObject();
//                panelInfos.addAll(loadedPanelInfos);  // Thêm tất cả các panelInfos từ tệp vào danh sách
//                for (PanelInfo panelInfo : loadedPanelInfos) {
//                    JPanel loadedPanel = createPanelFromInfoFood(panelInfo);
//                    panels.add(loadedPanel);
//                    food.add(loadedPanel);
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("File not found. Creating a new one.");
//            savePanelInfoToFile();  // Tạo một tệp mới nếu nó không tồn tại
//        }
//        food.revalidate();
//        food.repaint();
//    }
    private JPanel createPanelFromInfoFood(PanelInfo panelInfo) {
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
        int x = (countPanelsInJPanelFood() % 3) * gapX;
        int y = (countPanelsInJPanelFood() / 3) * gapY;
        panel.setBounds(x, y, 192, 187);

        return panel;
    }

    private JPanel createPanelFromInfoDrink(PanelInfo panelInfo) {
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
        int x = (countPanelsInJPanelDrink() % 3) * gapX;
        int y = (countPanelsInJPanelDrink() / 3) * gapY;
        panel.setBounds(x, y, 192, 187);

        return panel;
    }

    private int countPanelsInJPanelFood() {
        int count = 0;

        for (Component component : food.getComponents()) {
            System.out.println("Component class: " + component.getClass().getName());

            if (component instanceof JPanel && component != food) {
                count++;
            }
        }

        System.out.println("Number of panels: " + count);

        return count;
    }

    private int countPanelsInJPanelDrink() {
        int count = 0;

        for (Component component : drink.getComponents()) {
            System.out.println("Component class drink: " + component.getClass().getName());

            if (component instanceof JPanel && component != drink) {
                count++;
            }
        }

        System.out.println("Number of panels: " + count);

        return count;
    }

    private JPanel createPanelFood() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.red);
        JCheckBox cbx = new JCheckBox();
        JLabel label = new JLabel("Tên sản phẩm");
        JLabel label2 = new JLabel("\n IMG");
        JLabel label3 = new JLabel("\n 0");// Thiết lập văn bản cho label2

        panel.add(cbx);
        panel.add(label);
        panel.add(label2);
        panel.add(label3);

        // Sử dụng layout manager hoặc phương thức để cài đặt vị trí
        // Ví dụ sử dụng FlowLayout
        // Tính toán vị trí x và y cho panel mới
        int x = (countPanelsInJPanelFood() % 3) * gapX;
        int y = (countPanelsInJPanelFood() / 3) * gapY;
        System.out.println("x: " + x + ", y: " + y);

        // Set the bounds for the panel
        panel.setBounds(x, y, 192, 187);
        if (y > food.getHeight()) {
            int currentHeight = food.getHeight();
            int newHeight = currentHeight + 250;
            savePanelHeight(newHeight);
            food.setPreferredSize(new Dimension(food.getWidth(), newHeight));
            food.revalidate();
        }

        // Thêm panelInfo vào danh sách panelInfos
        panelInfos.add(new PanelInfo(cbx.isSelected(), label.getText(), label2.getText(), Float.parseFloat(label3.getText())));

        return panel;
    }

    private JPanel createPanelDrink() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.red);
        JCheckBox cbx = new JCheckBox();
        JLabel label = new JLabel("Tên sản phẩm");
        JLabel label2 = new JLabel("\n IMG");
        JLabel label3 = new JLabel("\n 0");// Thiết lập văn bản cho label2

        panel.add(cbx);
        panel.add(label);
        panel.add(label2);
        panel.add(label3);

        // Sử dụng layout manager hoặc phương thức để cài đặt vị trí
        // Ví dụ sử dụng FlowLayout
        // Tính toán vị trí x và y cho panel mới
        int x = (countPanelsInJPanelDrink() % 3) * gapX;
        int y = (countPanelsInJPanelDrink() / 3) * gapY;
        System.out.println("x: " + x + ", y: " + y);

        // Set the bounds for the panel
        panel.setBounds(x, y, 192, 187);
        if (y > drink.getHeight()) {
            int currentHeight = drink.getHeight();
            int newHeight = currentHeight + 250;
            savePanelHeight(newHeight);
            drink.setPreferredSize(new Dimension(drink.getWidth(), newHeight));
            drink.revalidate();
        }

        // Thêm panelInfo vào danh sách panelInfos
        panelInfos.add(new PanelInfo(cbx.isSelected(), label.getText(), label2.getText(), Float.parseFloat(label3.getText())));

        return panel;
    }

    private boolean checkCollisionFood(JPanel newPanel) {
        if (panels == null) {
            System.err.println("panels is null");
            return false; // Hoặc có thể xử lý nếu panels là null ở đây
        }

        Rectangle newBounds = newPanel.getBounds();

        // Kiểm tra va chạm với các panel con của jPanel14
        for (Component component : food.getComponents()) {
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

    private boolean checkCollisionDrink(JPanel newPanel) {
        if (panels == null) {
            System.err.println("panels is null");
            return false; // Hoặc có thể xử lý nếu panels là null ở đây
        }

        Rectangle newBounds = newPanel.getBounds();

        // Kiểm tra va chạm với các panel con của jPanel14
        for (Component component : drink.getComponents()) {
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

    private void removeSelectedPanelFood() {
        try {
            // Tạo một danh sách tạm thời để lưu trữ các sản phẩm cần xóa
            List<Products> productNamesToRemove = new ArrayList<>();

            // Xác định các sản phẩm được chọn để xóa
            for (Component component : food.getComponents()) {
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

    private void removeSelectedPanelDrink() {
        try {
            // Tạo một danh sách tạm thời để lưu trữ các sản phẩm cần xóa
            List<Products> productNamesToRemove = new ArrayList<>();

            // Xác định các sản phẩm được chọn để xóa
            for (Component component : drink.getComponents()) {
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
    private void applySavedPanelSizeFood() {
        int savedHeight = loadPanelHeight();
        food.setPreferredSize(new Dimension(food.getWidth(), savedHeight));
        food.revalidate();
    }

    private void applySavedPanelSizeDrink() {
        int savedHeight = loadPanelHeight();
        drink.setPreferredSize(new Dimension(drink.getWidth(), savedHeight));
        drink.revalidate();
    }

    public void editInfoFood() {
        for (Component component : food.getComponents()) {
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

    public void editInfoDrink() {
        for (Component component : drink.getComponents()) {
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

    public void loadPanelInfoFromDatabase() {
        panelInfos.clear();

        // Thực hiện tải dữ liệu từ cơ sở dữ liệu và cập nhật danh sách panelInfos
        // Đoạn mã xử lý việc tải dữ liệu từ cơ sở dữ liệu ở đây
        // Sau khi tải dữ liệu, cần cập nhật lại giao diện người dùng
        food.removeAll();
        drink.removeAll();
        List<PanelInfo> panelInfoList = PanelInfo.createPanelInfoListFromDatabase();

        for (PanelInfo panelInfo : panelInfoList) {

            if (panelInfo.getCategory().contains("Drink")) {
                JPanel newPanel = createPanelFromInfoDrink(panelInfo);
                drink.add(newPanel);
                panels.add(newPanel);

            } else {
                JPanel newPanel = createPanelFromInfoFood(panelInfo);
                panels.add(newPanel);
                food.add(newPanel);
            }

        }

        food.revalidate();
        food.repaint();
        drink.revalidate();
        drink.repaint();
    }

    public void fillToTableOder() {
        try {
            DefaultTableModel model = (DefaultTableModel) tblOder.getModel();
            model.setRowCount(0);
            listoder = oderDAO.selectAll();

            for (Order x : listoder) {
                String statusText = x.isStatus() ? "Thành công" : "Thất bại";
                Object[] row = new Object[]{x.getOrderID(), x.getCustomerID(), x.getTotalAmount(), x.getOrderDate(), statusText};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void fillToAccount(User selectUser) {
        txtUserName.setText(selectUser.getName());
        txtAge.setText(selectUser.getAge());
        txtAdress.setText(selectUser.getAdress());
        txtEmail.setText(selectUser.getEmail());
        txtPassword.setText(selectUser.getPassword());
        txtPhoneNumber.setText(selectUser.getSDT());
        if (cbxRole.getItemCount() == 0) {  // Kiểm tra xem đã có mục nào chưa
            cbxRole.addItem("Admin");
            cbxRole.addItem("Nhân viên");
        }

// Sau đó thiết lập giá trị được chọn
        cbxRole.setSelectedItem(selectUser.isRole() ? "Admin" : "Nhân viên");
        String imagePath = selectUser.getImg();
        ImageIcon userImageIcon = new ImageIcon(imagePath);
        Image image = userImageIcon.getImage().getScaledInstance(imgLogin.getWidth(), imgLogin.getHeight(), Image.SCALE_SMOOTH);
        imgLogin.setIcon(new ImageIcon(image));
        imgLogin.setToolTipText(imagePath);

    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel3 = new javax.swing.JPanel();
        pnmenu = new javax.swing.JPanel();
        lblexitmenu = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        User = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        Order = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        Reports = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        Product = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        Category = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        Account = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        Logout = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblmenu = new javax.swing.JLabel();
        Menu = new javax.swing.JTabbedPane();
        pnUser = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        UserTable = new javax.swing.JTable();
        txtFindUser = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        pnOder = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblOder = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnReloadOder = new javax.swing.JButton();
        pnReport = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        NumberOrder = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        NumberTotalAmount = new javax.swing.JLabel();
        btnExcel = new javax.swing.JButton();
        TimeOrder = new javax.swing.JComboBox<>();
        pnSetting = new javax.swing.JPanel();
        pnAccount = new javax.swing.JPanel();
        imgLogin = new javax.swing.JLabel();
        UserName = new javax.swing.JLabel();
        Email = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Adress = new javax.swing.JLabel();
        Role = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPhoneNumber = new javax.swing.JTextField();
        txtAdress = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        txtAge = new javax.swing.JTextField();
        cbxRole = new javax.swing.JComboBox<>();
        btnEdit = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        pnProduct = new javax.swing.JPanel();
        jTabbedPaned = new javax.swing.JTabbedPane();
        pnFood = new javax.swing.JPanel();
        jcrollpaneFood = new javax.swing.JScrollPane();
        food = new javax.swing.JPanel();
        pnDrink = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        drink = new javax.swing.JPanel();
        btnPay = new javax.swing.JButton();
        btnAddProducts = new javax.swing.JButton();
        btnEditProduct = new javax.swing.JButton();
        btnRemoveProduct = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        pnCategory = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCategory = new javax.swing.JTable();
        btnAddCategory = new javax.swing.JButton();
        btnEditCategory = new javax.swing.JButton();
        btnRemoveCategory = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(51, 153, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnmenu.setBackground(new java.awt.Color(255, 255, 255));
        pnmenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblexitmenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/unnamed-removebg-preview.png"))); // NOI18N
        lblexitmenu.setText("jLabel2");
        lblexitmenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblexitmenuMouseClicked(evt);
            }
        });
        pnmenu.add(lblexitmenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 23, 66, 59));

        jSeparator1.setBackground(new java.awt.Color(51, 51, 51));
        pnmenu.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 113, 162, -1));

        User.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        User.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        User.setText("USER");
        User.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserMouseClicked(evt);
            }
        });
        pnmenu.add(User, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 128, 162, -1));

        jSeparator2.setBackground(new java.awt.Color(51, 51, 51));
        pnmenu.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 159, 162, -1));

        Order.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Order.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Order.setText("ODER");
        Order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OrderMouseClicked(evt);
            }
        });
        pnmenu.add(Order, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 174, 162, -1));

        jSeparator3.setBackground(new java.awt.Color(51, 51, 51));
        pnmenu.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 211, 150, -1));

        Reports.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Reports.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Reports.setText("REPORTS");
        Reports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReportsMouseClicked(evt);
            }
        });
        pnmenu.add(Reports, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 232, 150, -1));

        jSeparator4.setBackground(new java.awt.Color(51, 51, 51));
        pnmenu.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 263, 150, -1));

        Product.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Product.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Product.setText("PRODUCT");
        Product.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProductMouseClicked(evt);
            }
        });
        pnmenu.add(Product, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 284, 162, -1));

        jSeparator5.setBackground(new java.awt.Color(51, 51, 51));
        pnmenu.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 315, 156, -1));

        Category.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Category.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Category.setText("CATEGORY");
        Category.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CategoryMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CategoryMouseEntered(evt);
            }
        });
        pnmenu.add(Category, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 162, -1));

        jSeparator6.setBackground(new java.awt.Color(51, 51, 51));
        pnmenu.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 156, -1));

        Account.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Account.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Account.setText("ACCOUNT");
        Account.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AccountMouseClicked(evt);
            }
        });
        pnmenu.add(Account, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 162, -1));

        jSeparator8.setBackground(new java.awt.Color(51, 51, 51));
        pnmenu.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 156, -1));

        Logout.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Logout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Logout.setText("LOGOUT");
        Logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutMouseClicked(evt);
            }
        });
        pnmenu.add(Logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 162, -1));

        jSeparator7.setBackground(new java.awt.Color(51, 51, 51));
        pnmenu.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 156, -1));

        jPanel3.add(pnmenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 610));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/1024px-OOjs_UI_icon_userAvatar-progressive.svg-removebg-preview.png"))); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, 80, -1));

        lblmenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/unnamed-removebg-preview (1).png"))); // NOI18N
        lblmenu.setText("jLabel1");
        lblmenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblmenuMouseClicked(evt);
            }
        });
        jPanel2.add(lblmenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 69, -1));

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 80));

        UserTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "UserID", "Name", "SDT", "Email", "Adress", "Age", "Role", "Wage", "Password", "Img"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        UserTable.setFocusable(true);
        UserTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(UserTable);
        if (UserTable.getColumnModel().getColumnCount() > 0) {
            UserTable.getColumnModel().getColumn(0).setResizable(false);
            UserTable.getColumnModel().getColumn(1).setResizable(false);
            UserTable.getColumnModel().getColumn(2).setResizable(false);
            UserTable.getColumnModel().getColumn(3).setResizable(false);
            UserTable.getColumnModel().getColumn(4).setResizable(false);
            UserTable.getColumnModel().getColumn(5).setResizable(false);
            UserTable.getColumnModel().getColumn(6).setResizable(false);
            UserTable.getColumnModel().getColumn(7).setResizable(false);
            UserTable.getColumnModel().getColumn(8).setResizable(false);
            UserTable.getColumnModel().getColumn(9).setResizable(false);
        }

        btnFind.setText("Find");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnUserLayout = new javax.swing.GroupLayout(pnUser);
        pnUser.setLayout(pnUserLayout);
        pnUserLayout.setHorizontalGroup(
            pnUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnUserLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(pnUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 764, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnUserLayout.createSequentialGroup()
                        .addComponent(txtFindUser, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFind)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        pnUserLayout.setVerticalGroup(
            pnUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnUserLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pnUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFindUser, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1121, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", pnUser);

        tblOder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "OderID", "CustomerID", "Total amount", " Oder Date", "Status"
            }
        ));
        tblOder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOderMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblOder);

        jLabel1.setBackground(new java.awt.Color(255, 51, 51));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BILL OF SALE");
        jLabel1.setToolTipText("");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnReloadOder.setText("Reload");
        btnReloadOder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadOderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnOderLayout = new javax.swing.GroupLayout(pnOder);
        pnOder.setLayout(pnOderLayout);
        pnOderLayout.setHorizontalGroup(
            pnOderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnOderLayout.createSequentialGroup()
                .addGroup(pnOderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
                    .addGroup(pnOderLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pnOderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnReloadOder)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnOderLayout.setVerticalGroup(
            pnOderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnOderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReloadOder)
                .addContainerGap(1116, Short.MAX_VALUE))
        );

        Menu.addTab("tab3", pnOder);

        jPanel1.setBackground(new java.awt.Color(255, 102, 51));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/production_quantity_limits_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        NumberOrder.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        NumberOrder.setForeground(new java.awt.Color(255, 255, 255));
        NumberOrder.setText("0");

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Arial Rounded MT Bold", 3, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("ORDER");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NumberOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(NumberOrder)))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/category_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Arial Rounded MT Bold", 3, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TOTAL AMOUNT");

        NumberTotalAmount.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        NumberTotalAmount.setForeground(new java.awt.Color(255, 255, 255));
        NumberTotalAmount.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NumberTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addGap(44, 44, 44)
                        .addComponent(NumberTotalAmount))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        btnExcel.setText("XUẤT EXCEL");
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        TimeOrder.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TimeOrderItemStateChanged(evt);
            }
        });
        TimeOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TimeOrderMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnReportLayout = new javax.swing.GroupLayout(pnReport);
        pnReport.setLayout(pnReportLayout);
        pnReportLayout.setHorizontalGroup(
            pnReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnReportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnReportLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(TimeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExcel)
                .addGap(17, 17, 17))
        );
        pnReportLayout.setVerticalGroup(
            pnReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnReportLayout.createSequentialGroup()
                .addGroup(pnReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnReportLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnExcel))
                    .addGroup(pnReportLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(TimeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(77, 77, 77)
                .addGroup(pnReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1325, Short.MAX_VALUE))
        );

        Menu.addTab("tab4", pnReport);

        pnSetting.setPreferredSize(new java.awt.Dimension(800, 419));

        javax.swing.GroupLayout pnSettingLayout = new javax.swing.GroupLayout(pnSetting);
        pnSetting.setLayout(pnSettingLayout);
        pnSettingLayout.setHorizontalGroup(
            pnSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 810, Short.MAX_VALUE)
        );
        pnSettingLayout.setVerticalGroup(
            pnSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1640, Short.MAX_VALUE)
        );

        Menu.addTab("tab5", pnSetting);

        imgLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgLoginMouseClicked(evt);
            }
        });

        UserName.setText("User Name :");

        Email.setText("Email:");

        jLabel10.setText("Phone Number:");

        Adress.setText("Adress :");

        Role.setText("Role :");

        jLabel13.setText("Password :");

        jLabel14.setText("Age :");

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.setEnabled(false);
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnAccountLayout = new javax.swing.GroupLayout(pnAccount);
        pnAccount.setLayout(pnAccountLayout);
        pnAccountLayout.setHorizontalGroup(
            pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAccountLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnAccountLayout.createSequentialGroup()
                        .addComponent(imgLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnAccountLayout.createSequentialGroup()
                                .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnAccountLayout.createSequentialGroup()
                            .addComponent(Role, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50)
                            .addComponent(cbxRole, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnAccountLayout.createSequentialGroup()
                            .addComponent(Adress, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50)
                            .addComponent(txtAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnAccountLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                        .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnAccountLayout.createSequentialGroup()
                                .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPassword)
                                    .addComponent(txtAge, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)))
                            .addGroup(pnAccountLayout.createSequentialGroup()
                                .addComponent(btnEdit)
                                .addGap(85, 85, 85)
                                .addComponent(btnUpdate)))
                        .addGap(58, 58, 58))
                    .addGroup(pnAccountLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail)
                            .addComponent(txtUserName)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnAccountLayout.setVerticalGroup(
            pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAccountLayout.createSequentialGroup()
                .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnAccountLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UserName)
                            .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnAccountLayout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(Email))
                            .addGroup(pnAccountLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(52, 52, 52)
                        .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAccountLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(imgLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(73, 73, 73)
                .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Adress)
                    .addComponent(jLabel13)
                    .addComponent(txtAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(91, 91, 91)
                .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Role)
                    .addComponent(jLabel14)
                    .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(pnAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnUpdate))
                .addContainerGap(1133, Short.MAX_VALUE))
        );

        Menu.addTab("tab6", pnAccount);

        pnProduct.setBackground(new java.awt.Color(204, 204, 204));

        jTabbedPaned.setBackground(new java.awt.Color(51, 51, 255));
        jTabbedPaned.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPaned.setTabPlacement(javax.swing.JTabbedPane.RIGHT);

        food.setBackground(new java.awt.Color(102, 255, 255));

        javax.swing.GroupLayout foodLayout = new javax.swing.GroupLayout(food);
        food.setLayout(foodLayout);
        foodLayout.setHorizontalGroup(
            foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 738, Short.MAX_VALUE)
        );
        foodLayout.setVerticalGroup(
            foodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 881, Short.MAX_VALUE)
        );

        jcrollpaneFood.setViewportView(food);

        javax.swing.GroupLayout pnFoodLayout = new javax.swing.GroupLayout(pnFood);
        pnFood.setLayout(pnFoodLayout);
        pnFoodLayout.setHorizontalGroup(
            pnFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcrollpaneFood, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE)
        );
        pnFoodLayout.setVerticalGroup(
            pnFoodLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jcrollpaneFood, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
        );

        jTabbedPaned.addTab("FOOD", pnFood);

        drink.setBackground(new java.awt.Color(0, 255, 255));

        javax.swing.GroupLayout drinkLayout = new javax.swing.GroupLayout(drink);
        drink.setLayout(drinkLayout);
        drinkLayout.setHorizontalGroup(
            drinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 808, Short.MAX_VALUE)
        );
        drinkLayout.setVerticalGroup(
            drinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 717, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(drink);

        javax.swing.GroupLayout pnDrinkLayout = new javax.swing.GroupLayout(pnDrink);
        pnDrink.setLayout(pnDrinkLayout);
        pnDrinkLayout.setHorizontalGroup(
            pnDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDrinkLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnDrinkLayout.setVerticalGroup(
            pnDrinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
        );

        jTabbedPaned.addTab("DRINK", pnDrink);

        btnPay.setText("PAY");
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        btnAddProducts.setText("ADD");
        btnAddProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductsActionPerformed(evt);
            }
        });

        btnEditProduct.setText("EDIT");
        btnEditProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProductActionPerformed(evt);
            }
        });

        btnRemoveProduct.setText("REMOVE");
        btnRemoveProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveProductActionPerformed(evt);
            }
        });

        btnReload.setText("RELOAD");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnProductLayout = new javax.swing.GroupLayout(pnProduct);
        pnProduct.setLayout(pnProductLayout);
        pnProductLayout.setHorizontalGroup(
            pnProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaned, javax.swing.GroupLayout.PREFERRED_SIZE, 810, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnProductLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnAddProducts)
                .addGap(18, 18, 18)
                .addComponent(btnEditProduct)
                .addGap(18, 18, 18)
                .addComponent(btnRemoveProduct)
                .addGap(18, 18, 18)
                .addComponent(btnReload)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPay)
                .addGap(95, 95, 95))
        );
        pnProductLayout.setVerticalGroup(
            pnProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProductLayout.createSequentialGroup()
                .addComponent(jTabbedPaned, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(pnProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddProducts)
                    .addComponent(btnEditProduct)
                    .addComponent(btnRemoveProduct)
                    .addComponent(btnPay)
                    .addComponent(btnReload))
                .addGap(0, 1122, Short.MAX_VALUE))
        );

        Menu.addTab("tab2", pnProduct);

        tblCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "CategoryID", "Name"
            }
        ));
        jScrollPane2.setViewportView(tblCategory);

        btnAddCategory.setText("ADD");
        btnAddCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCategoryActionPerformed(evt);
            }
        });

        btnEditCategory.setText("EDIT");
        btnEditCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCategoryActionPerformed(evt);
            }
        });

        btnRemoveCategory.setText("REMOVE");
        btnRemoveCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveCategoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnCategoryLayout = new javax.swing.GroupLayout(pnCategory);
        pnCategory.setLayout(pnCategoryLayout);
        pnCategoryLayout.setHorizontalGroup(
            pnCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCategoryLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCategoryLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddCategory)
                .addGap(34, 34, 34)
                .addComponent(btnEditCategory)
                .addGap(33, 33, 33)
                .addComponent(btnRemoveCategory)
                .addGap(104, 104, 104))
        );
        pnCategoryLayout.setVerticalGroup(
            pnCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCategoryLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddCategory)
                    .addComponent(btnEditCategory)
                    .addComponent(btnRemoveCategory))
                .addGap(50, 50, 50)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1122, Short.MAX_VALUE))
        );

        Menu.addTab("tab7", pnCategory);

        jPanel3.add(Menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 810, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblmenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblmenuMouseClicked
        Timer time1 = new Timer(10, (e) -> {
            x = pnmenu.getWidth() + 4;
            if (pnmenu.getWidth() < 191) {
                pnmenu.setSize(x, pnmenu.getHeight());
                lblexitmenu.setEnabled(false);
            } else {
                ((Timer) e.getSource()).stop();
                lblexitmenu.setEnabled(true);
            }
        });
        time1.start();
        setEnabledAll(pnUser, false);
// TODO add your handling code here:
    }//GEN-LAST:event_lblmenuMouseClicked

    private void lblexitmenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblexitmenuMouseClicked
        Timer time2 = new Timer(10, (e) -> {
            x = pnmenu.getWidth() - 4;
            if (pnmenu.getWidth() > 0) {
                pnmenu.setSize(x, pnmenu.getHeight());

            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        time2.start();
        setEnabledAll(pnUser, true);
        // TODO add your handling code here:
    }//GEN-LAST:event_lblexitmenuMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        fillToTableUser();
        fillToTableCategory();
        fillToTableOder();
        fillToAccount(selectUser);
// TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void UserTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserTableMouseClicked
        getSelectedUserData();
        openEditUserForm();
        refreshUserData();

        // Now you can use these values to populate the "Edit User" section

    }//GEN-LAST:event_UserTableMouseClicked

    private void UserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserMouseClicked
        Menu.setSelectedComponent(pnUser);     // TODO add your handling code here:
    }//GEN-LAST:event_UserMouseClicked

    private void OrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OrderMouseClicked
        Menu.setSelectedComponent(pnOder);// TODO add your handling code here:
    }//GEN-LAST:event_OrderMouseClicked

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        String inputUserID = txtFindUser.getText().trim();

        // Kiểm tra xem người dùng đã nhập một giá trị hợp lệ chưa
        if (inputUserID.isEmpty()) {
            // Hiển thị thông báo hoặc thực hiện xử lý khi người dùng không nhập gì đó
            JOptionPane.showMessageDialog(this, "Vui lòng nhập UserID để tìm kiếm.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Chuyển đổi giá trị UserID từ chuỗi sang số nguyên

            // Gọi phương thức selectById từ UserDAO để tìm kiếm người dùng
            User foundUser = UserDAO.selectById(inputUserID);

            // Kiểm tra xem người dùng có tồn tại hay không
            if (foundUser != null) {

                // Hiển thị thông tin người dùng hoặc thực hiện xử lý cần thiết
                JOptionPane.showMessageDialog(this, "Người dùng được tìm thấy:\n", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                openEditUserForm(foundUser);

                refreshUserData();
            } else {
                // Hiển thị thông báo nếu không tìm thấy người dùng
                JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng với UserID: " + inputUserID, "Thông báo", JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException e) {
            // Xử lý nếu người dùng nhập không phải là một số nguyên
            JOptionPane.showMessageDialog(this, "Vui lòng nhập một số nguyên hợp lệ cho UserID.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFindActionPerformed

    private void ProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductMouseClicked
        Menu.setSelectedComponent(pnProduct);    // TODO add your handling code here:
    }//GEN-LAST:event_ProductMouseClicked

    private void ReportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReportsMouseClicked
        Menu.setSelectedComponent(pnReport);        // TODO add your handling code here:
    }//GEN-LAST:event_ReportsMouseClicked

    private void CategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CategoryMouseClicked
        Menu.setSelectedComponent(pnCategory);     // TODO add your handling code here:
    }//GEN-LAST:event_CategoryMouseClicked

    private void LogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseClicked
        dispose();
        new Login(selectUser).setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_LogoutMouseClicked

    private void btnAddCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCategoryActionPerformed
        Categories selectedcategory = null;

        new EditCategory(this, true, selectedcategory).setVisible(true);

// TODO add your handling code here:
    }//GEN-LAST:event_btnAddCategoryActionPerformed

    private void btnEditCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCategoryActionPerformed
        openEditCategoryform();
// TODO add your handling code here:
    }//GEN-LAST:event_btnEditCategoryActionPerformed

    private void btnRemoveCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveCategoryActionPerformed
        Categories selectedcategory = getSelectedDataCategory();
        try {
            if (selectedcategory != null) {
                categoryDAO.delete(selectedcategory);
                // Update the table in the main form
                fillToTableCategory();
                JOptionPane.showMessageDialog(this, "ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting user");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveCategoryActionPerformed

    private void btnAddProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductsActionPerformed

        openEditProduct();
// TODO add your handling code here:
    }//GEN-LAST:event_btnAddProductsActionPerformed

    private void btnEditProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProductActionPerformed
        if (jTabbedPaned.getSelectedIndex() == 1) {
            editInfoDrink();
        } else {
            editInfoFood();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditProductActionPerformed

    private void btnRemoveProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveProductActionPerformed
        if (jTabbedPaned.getSelectedIndex() == 1) {
            removeSelectedPanelDrink();
        } else {
            removeSelectedPanelFood();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveProductActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        loadPanelInfoFromDatabase();        // TODO add your handling code here:
    }//GEN-LAST:event_btnReloadActionPerformed

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        List<Products> selectedProductNames = new ArrayList<>();
// Lấy danh sách các tên sản phẩm được chọn từ các JCheckBox

        for (Component component : drink.getComponents()) {
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
                                Products productList = productDAO.selectById(productName);
                                int productID = productList.getProductID();

                                selectedProductNames.add(new Products(productID, productName, price));
                                System.out.println("" + selectedProductNames);
                                // Tạo đối tượng Products mới và thêm vào danh sách

                            } catch (NumberFormatException e) {
                                // Xử lý nếu giá không hợp lệ
                                System.err.println("Giá không hợp lệ: " + priceStr);
                            }
                        }
                    }
                }
            }
        }

        for (Component component : food.getComponents()) {
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
                                Products productList = productDAO.selectById(productName);
                                int productID = productList.getProductID();

                                selectedProductNames.add(new Products(productID, productName, price));
                                System.out.println("" + selectedProductNames);
                                // Tạo đối tượng Products mới và thêm vào danh sách

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
    }//GEN-LAST:event_btnPayActionPerformed

    private void btnReloadOderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadOderActionPerformed
        fillToTableOder();  // TODO add your handling code here:
    }//GEN-LAST:event_btnReloadOderActionPerformed

    private void tblOderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOderMouseClicked
        int selectedtblOder = tblOder.getSelectedRow();
        System.out.println("" + selectedtblOder);
        if (selectedtblOder >= 0) {
            int OrderID = (int) tblOder.getValueAt(selectedtblOder, 0);
            System.out.println("" + OrderID);

            Oderdetailform oderdetailform = new Oderdetailform(OrderID);
            oderdetailform.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "vui lòng chọn đơn hàng để hiển thị hóa đơn chi tiết");
        }

// TODO add your handling code here:
    }//GEN-LAST:event_tblOderMouseClicked

    private void AccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AccountMouseClicked
        Menu.setSelectedComponent(pnAccount);        // TODO add your handling code here:
    }//GEN-LAST:event_AccountMouseClicked

    private void TimeOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TimeOrderMouseClicked

// TODO add your handling code here:
    }//GEN-LAST:event_TimeOrderMouseClicked

    private void TimeOrderItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TimeOrderItemStateChanged
        // Gọi phương thức reportOrder và lưu kết quả vào danh sách reportData
        List<Object[]> reportData = oderDAO.reportOrder(TimeOrder.getSelectedItem().toString());

// Duyệt qua từng phần tử trong danh sách reportData
        for (Object[] rowData : reportData) {
            // Lấy giá trị của cột 1 (COUNT(OrderID))
            int numberOfOrders = (int) rowData[0];
            // Lấy giá trị của cột 2 (SUM(TotalAmount))
            float totalAmount = (float) rowData[1];
            NumberOrder.setText(String.valueOf(numberOfOrders));
            NumberTotalAmount.setText(String.valueOf(totalAmount));

            // In ra giá trị của hai cột
            System.out.println("Number of Orders: " + numberOfOrders);
            System.out.println("Total Amount: " + totalAmount);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_TimeOrderItemStateChanged

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void CategoryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CategoryMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_CategoryMouseEntered

    private void imgLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgLoginMouseClicked
        JFileChooser jfile = new JFileChooser();
        int returnValue = jfile.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                File file = jfile.getSelectedFile();
                String imagePath = file.getAbsolutePath();
                Xlmage.save(file);
                ImageIcon userImageIcon = new ImageIcon(imagePath);
                Image image = userImageIcon.getImage().getScaledInstance(imgLogin.getWidth(), imgLogin.getHeight(), Image.SCALE_SMOOTH);
                imgLogin.setIcon(new ImageIcon(image));
                imgLogin.setToolTipText(imagePath);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

// TODO add your handling code here:
    }//GEN-LAST:event_imgLoginMouseClicked

    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
        String roleString = (String) cbxRole.getSelectedItem();

        User updateUser = new User(selectUser.getUserID(), txtUserName.getText(), Boolean.parseBoolean(roleString), txtAge.getText(), txtPhoneNumber.getText(), txtEmail.getText(), txtPassword.getText(), txtAdress.getText(), selectUser.getWage(), imgLogin.getToolTipText());
        // Cập nhật thông tin user

        UserDAO.update(updateUser);        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        boolean i = btnUpdate.isEnabled();
        i = !i;
        btnUpdate.setEnabled(i);

// TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
   String[] headers = {"Time","Oders","TOTAL AMOUNT"};      
     String[][] data = {
       { TimeOrder.getSelectedItem().toString(), NumberOrder.getText(), NumberTotalAmount.getText()}
        };    
        ExcelExporter excel = new ExcelExporter();
excel.exportDataToExcel(headers, data, "output.xlsx");
// TODO add your handling code here:
    }//GEN-LAST:event_btnExcelActionPerformed

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
            java.util.logging.Logger.getLogger(Mainform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mainform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mainform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mainform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                User selectUser = null;

                new Mainform(selectUser).setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Account;
    private javax.swing.JLabel Adress;
    private javax.swing.JLabel Category;
    private javax.swing.JLabel Email;
    private javax.swing.JLabel Logout;
    private javax.swing.JTabbedPane Menu;
    private javax.swing.JLabel NumberOrder;
    private javax.swing.JLabel NumberTotalAmount;
    private javax.swing.JLabel Order;
    private javax.swing.JLabel Product;
    private javax.swing.JLabel Reports;
    private javax.swing.JLabel Role;
    private javax.swing.JComboBox<String> TimeOrder;
    private javax.swing.JLabel User;
    private javax.swing.JLabel UserName;
    private javax.swing.JTable UserTable;
    private javax.swing.JButton btnAddCategory;
    private javax.swing.JButton btnAddProducts;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEditCategory;
    private javax.swing.JButton btnEditProduct;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnReloadOder;
    private javax.swing.JButton btnRemoveCategory;
    private javax.swing.JButton btnRemoveProduct;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbxRole;
    private javax.swing.JPanel drink;
    private javax.swing.JPanel food;
    private javax.swing.JLabel imgLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTabbedPane jTabbedPaned;
    private javax.swing.JScrollPane jcrollpaneFood;
    private javax.swing.JLabel lblexitmenu;
    private javax.swing.JLabel lblmenu;
    private javax.swing.JPanel pnAccount;
    private javax.swing.JPanel pnCategory;
    private javax.swing.JPanel pnDrink;
    private javax.swing.JPanel pnFood;
    private javax.swing.JPanel pnOder;
    private javax.swing.JPanel pnProduct;
    private javax.swing.JPanel pnReport;
    private javax.swing.JPanel pnSetting;
    private javax.swing.JPanel pnUser;
    private javax.swing.JPanel pnmenu;
    private javax.swing.JTable tblCategory;
    private javax.swing.JTable tblOder;
    private javax.swing.JTextField txtAdress;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFindUser;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtPhoneNumber;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
