/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import helper.Jdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import model.Order;

/**
 *
 * @author ASUS
 */
public class OderDAO extends MainDAO<Order> {

    String INSERT_ORDER = "INSERT INTO [Order](CustomerID, TotalAmount, OderDate, Status) VALUES (?, ?, ?, ?)";
    String UPDATE_ORDER = "UPDATE [Order] SET CustomerID=?, TotalAmount=?, OderDate=?, Status=? WHERE OrderID=?";
    String DELETE_ORDER = "DELETE FROM [Order] WHERE OrderID=?";
    String SELECT_ORDER_BY_NAME = "SELECT * FROM [Order] WHERE OrderName=?";
    String SELECT_ALL_ORDERS = "SELECT * FROM [Order]";
    String SELECT_DATE="SELECT OderDate FROM [Order] GROUP BY OderDate";


    String SELECT_REPORT = "SELECT COUNT(OrderID) , SUM(TotalAmount) \n"
            + "FROM [Order]\n"
            + "WHERE OderDate = ?\n"
            + "GROUP BY OderDate;";

    @Override
    public void insert(Order model) {
        try {
            Jdbc.executeUpdate(INSERT_ORDER, model.getCustomerID(), model.getTotalAmount(), model.getOrderDate(), model.isStatus());
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void update(Order model) {
        Jdbc.executeUpdate(UPDATE_ORDER, model.getCustomerID(), model.getTotalAmount(), model.getOrderDate(), model.isStatus(), model.getOrderID());
    }

    @Override
    public void delete(Order model) {
        Jdbc.executeUpdate(DELETE_ORDER, model.getOrderID());
    }

    public Order selectByNAME(String id) {
        return (Order) selectBySQL(SELECT_ORDER_BY_NAME, id);
    }
   
    public List<Object[]> reportOrder(String date) {
    List<Object[]> reportData = new ArrayList<>();
    
    try {
        ResultSet rs = Jdbc.executeQuery(SELECT_REPORT, date);
        while (rs.next()) {
            Object[] rowData = new Object[2]; // Số lượng trường trong mỗi hàng là 2 (COUNT(OrderID) và SUM(TotalAmount))
            rowData[0] = rs.getInt(1); // Lấy giá trị của cột 1 (COUNT(OrderID))
            rowData[1] = rs.getFloat(2); // Lấy giá trị của cột 2 (SUM(TotalAmount))
            reportData.add(rowData);
        }
        rs.getStatement().getConnection().close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return reportData;
}


    @Override
    public List<Order> selectAll() {

        return selectBySQL(SELECT_ALL_ORDERS);
    }
   public List<Order> selectAllDates() {
    List<Order> dates = new ArrayList<>();
   
    try (
         ResultSet rs = Jdbc.executeQuery(SELECT_DATE)) {
        while (rs.next()) {
            Order model = new Order(rs.getDate("OderDate"));
           
            dates.add(model);
        }
         rs.getStatement().getConnection().close();
            
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return dates;
}


    @Override
    protected List<Order> selectBySQL(String sql, Object... args) {
        List<Order> list = new ArrayList<>();
        try {
            ResultSet rs = Jdbc.executeQuery(sql, args);
            while (rs.next()) {
                Order model = new Order(rs.getInt("OrderID"), rs.getInt("CustomerID"), rs.getFloat("TotalAmount"), rs.getDate("OderDate"), rs.getBoolean("Status"));
                list.add(model);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
