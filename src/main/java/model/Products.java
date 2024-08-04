    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import form.Mainform;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


/**
 *
 * @author ASUS
 */
public class Products {
     
    private int  ProductID;
    private String ProductName;
    private String Description;
    private float Price;
    private int QuantityInStock;
    private String CategoriesName;
    private String ImgProduct;
    private int totalPay;
 

    public Products(String ProductName, String Description, float Price, int QuantityInStock, String CategoriesName, String ImgProduct) {
        this.ProductName = ProductName;
        this.Description = Description;
        this.Price = Price;
        this.QuantityInStock = QuantityInStock;
        this.CategoriesName = CategoriesName;
        this.ImgProduct = ImgProduct;
    }

    public Products(int ProductID, String ProductName, String Description, float Price, int QuantityInStock, String CategoriesName, String ImgProduct) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Description = Description;
        this.Price = Price;
        this.QuantityInStock = QuantityInStock;
        this.CategoriesName = CategoriesName;
        this.ImgProduct = ImgProduct;
    }

    public Products(int ProductID,String ProductName, String Description, float Price, int totalPay) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Description = Description;
        this.Price = Price;
        this.totalPay = totalPay;
    }

    public Products(String ProductName, float Price) {
        this.ProductName = ProductName;
        this.Price = Price;
    }

    public Products(String ProductName) {
        this.ProductName = ProductName;
    }

    public Products(int ProductID, String ProductName, float Price) {
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Price = Price;
    }

   

    
 

    public int getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(int totalPay) {
        this.totalPay = totalPay;
    }

   
    
    


  



 


    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

    public int getQuantityInStock() {
        return QuantityInStock;
    }

    public void setQuantityInStock(int QuantityInStock) {
        this.QuantityInStock = QuantityInStock;
    }

    public String getCategoriesName() {
        return CategoriesName;
    }

    public void setCategoriesName(String CategoriesName) {
        this.CategoriesName = CategoriesName;
    }



    public String getImgProduct() {
        return ImgProduct;
    }

    public void setImgProduct(String ImgProduct) {
        this.ImgProduct = ImgProduct;
    }

   
    
}
