package model;

import DAO.ProductDAO;
import form.NewJFrame;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents information needed to create a JPanel.
 */
public class PanelInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;
    List<Products> listproduct = new ArrayList<>();

    private boolean checkboxSelected;
    private String productName;
    private String imagePath;
    private float price;
    private int productID;
    private String category;

    public PanelInfo(boolean checkboxSelected, int productID, String category, String productName, String imagePath, float price) {
        this.checkboxSelected = checkboxSelected;
        this.productName = productName;
        this.category = category;
        this.productID = productID;
        this.imagePath = imagePath;
        this.price = price;
        this.productID = productID;
    }

    public PanelInfo(boolean checkboxSelected, String productName, String imagePath, float price) {
        this.checkboxSelected = checkboxSelected;
        this.productName = productName;
        this.imagePath = imagePath;
        this.price = price;

    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static List<PanelInfo> createPanelInfoListFromDatabase() {
        List<PanelInfo> panelInfoList = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO(); // Khởi tạo ProductDAO

        // Truy vấn dữ liệu từ CSDL và tạo PanelInfo cho mỗi sản phẩm
        List<Products> productList = productDAO.selectAll();
        for (Products product : productList) {
            PanelInfo panelInfo = new PanelInfo(false, product.getProductID(), product.getCategoriesName(), product.getProductName(), product.getImgProduct(), product.getPrice());
            panelInfoList.add(panelInfo);
        }

        return panelInfoList;
    }

// Lấy sản phẩm đầu tiên trong danh sách
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public boolean isCheckboxSelected() {
        return checkboxSelected;
    }

    public void setCheckboxSelected(boolean checkboxSelected) {
        this.checkboxSelected = checkboxSelected;
    }

    public String getProductName() {
        return productName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
