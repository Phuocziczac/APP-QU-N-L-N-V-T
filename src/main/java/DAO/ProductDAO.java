/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import com.sun.jdi.connect.spi.Connection;
import helper.Jdbc;
import java.beans.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Products;

/**
 *
 * @author ASUS
 */
public class ProductDAO extends MainDAO<Products> {

    String INSERT_PRODUCT = "INSERT INTO Products (ProductName, Description, Price, QuantityInStock, CategoriesName, ImgProduct) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    String UPDATE_PRODUCT = "UPDATE Products "
            + "SET Description = ? , Price = ?, QuantityInStock = ?,CategoriesName=? ,ImgProduct = ? "
            + "WHERE ProductName = ?";
     String DELETE_PRODUCT = "DELETE FROM Products WHERE ProductName = ?";
       String SELECT_PRODUCT_BY_NAME = "SELECT * FROM Products WHERE ProductName = ?";
        String SELECT_ALL_PRODUCTS = "SELECT * FROM Products";
        String SELECTNAME = "SELECT ProductName FROM Products";
        String SELECTIMG = "SELECT ImgProduct FROM Products";
       
    

    @Override
    public void insert(Products model) {
      
          if (model.getProductName() == null || model.getProductName().isEmpty()) {
        model.setProductName("chưa cập nhật");
    }

    if (model.getDescription() == null || model.getDescription().isEmpty()) {
        model.setDescription("chưa cập nhật");
    }
        if (String.valueOf(model.getPrice()) == null ||String.valueOf(model.getPrice()).isEmpty()) {
        model.setPrice(0);
    }
         if (String.valueOf(model.getQuantityInStock()) == null ||String.valueOf(model.getQuantityInStock()).isEmpty()) {
        model.setQuantityInStock(0);
    }
            if (String.valueOf(model.getCategoriesName()) == null ||String.valueOf(model.getCategoriesName()).isEmpty()) {
        model.setCategoriesName("Chua cap nhat");
    }
           if (model.getImgProduct() == null || model.getImgProduct().isEmpty()) {
        model.setImgProduct("chưa cập nhật");
    }  
         
        Jdbc.executeUpdate(INSERT_PRODUCT,
                model.getProductName(),
                model.getDescription(),
                model.getPrice(),
                model.getQuantityInStock(),
                model.getCategoriesName(),
                model.getImgProduct());
                
    }

    @Override
    public void update(Products model) {
       Jdbc.executeUpdate(UPDATE_PRODUCT, model.getDescription(),
       model.getPrice(),
       model.getQuantityInStock(),
       model.getCategoriesName(),
       model.getImgProduct(),
       model.getProductName());
      
    }
  

    @Override
    public void delete(Products model) {
        Jdbc.executeUpdate(DELETE_PRODUCT, model.getProductName());
    }
    
    

    @Override
    public Products selectById(String id) {
        try {
            // Thực hiện truy vấn SELECT
            ResultSet rs = Jdbc.prepareStatement(SELECT_PRODUCT_BY_NAME, id).executeQuery();

            // Xử lý kết quả SELECT
            if (rs.next()) {
                return new Products(
                        rs.getInt("ProductsID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getFloat("Price"),
                        rs.getInt("QuantityInStock"),
                        rs.getString("CategoriesName"),
                        rs.getString("ImgProduct")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

    @Override
    public List<Products> selectAll() {
       return selectBySQL(SELECT_ALL_PRODUCTS);
    }
   
    public List<Products> selectName() {
       return selectBySQL(SELECTNAME);
    }
     public List<Products> selectImg() {
       return selectBySQL(SELECTIMG);
    }


    
  
    @Override
    protected List<Products> selectBySQL(String sql, Object... args) {
        List<Products> listProduct = new ArrayList<>();
        try {
            ResultSet rs = Jdbc.executeQuery(sql, args);
            while (rs.next()) {
                Products product = new Products(rs.getInt("ProductsID"), rs.getString("ProductName"), rs.getString("Description"), rs.getFloat("Price"), rs.getInt("QuantityInStock"), rs.getString("CategoriesName"), rs.getString("ImgProduct"));
                listProduct.add(product);
            }
            rs.getStatement().getConnection().close();
            return listProduct;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
