/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import helper.XDate;
import java.util.Date;

/**
 *
 * @author ASUS
 */

public class Order {
   
    private int OrderID;
    private int CustomerID;
    private float TotalAmount;
    private Date OrderDate; 
    private boolean Status;

    public Order(int CustomerID, float TotalAmount, Date OrderDate, Boolean Status) {
        this.CustomerID = CustomerID;
        this.TotalAmount = TotalAmount;
        this.OrderDate = OrderDate;
        this.Status = Status;
    }

    public Order(int OrderID, int CustomerID, float TotalAmount, Date OrderDate, Boolean Status) {
        this.OrderID = OrderID;
        this.CustomerID = CustomerID;
        this.TotalAmount = TotalAmount;
        this.OrderDate = OrderDate;
        this.Status = Status;
    }

    public Order() {
    }

    public Order(Date OrderDate) {
        this.OrderDate = OrderDate;
    }


   

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int CustomerID) {
        this.CustomerID = CustomerID;
    }

    public float getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(float TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date OrderDate) {
        this.OrderDate = OrderDate;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

  
    
    
}
