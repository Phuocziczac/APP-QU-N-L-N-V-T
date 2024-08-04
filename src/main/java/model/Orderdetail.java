/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class Orderdetail {
    private int OrderdetailID;
    private int OrderID;
    private int ProductID;
    private int Quantity;
    private float Price;    

    public Orderdetail(int OrderdetailID, int OrderID, int ProductID, int Quantity, float Price) {
        this.OrderdetailID = OrderdetailID;
        this.OrderID = OrderID;
        this.ProductID = ProductID;
        this.Quantity = Quantity;
        this.Price = Price;
    }

    public Orderdetail(int OrderID, int ProductID, int Quantity, float Price) {
        this.OrderID = OrderID;
        this.ProductID = ProductID;
        this.Quantity = Quantity;
        this.Price = Price;
    }

    public int getOrderdetailID() {
        return OrderdetailID;
    }

    public void setOrderdetailID(int OrderdetailID) {
        this.OrderdetailID = OrderdetailID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

 

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

 

 
    

   
    
}
