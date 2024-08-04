/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import form.Oderdetailform;
import helper.Jdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Orderdetail;

/**
 *
 * @author ASUS
 */
public class OrderDetailDAO extends MainDAO<Orderdetail>{
    String  SELECT_ALL_ODERDETAIL="SELECT * FROM Oderdetail";
    String INSERT = "INSERT INTO Oderdetail (OrderID,ProductID,Quantity,Price) VALUES (?,?,?,?)";
    String UPDATE = "UPDATE Oderdetail SET OrderID = ? ,ProductName=?,Quantity=?,Price=? WHERE OrderdetailID=?";
    String DELETE = "DELETE  FROM Oderdetail WHERE OrderdetailID=? ";
    String SELECT_BY_ORDERID = "select * from Oderdetail where OrderID = ?";

    @Override
    public void insert(Orderdetail model) {
       Jdbc.executeUpdate(INSERT, model.getOrderID(),model.getProductID(),model.getQuantity(),model.getPrice());
    }

    @Override
    public void update(Orderdetail model) {
 Jdbc.executeUpdate(UPDATE, model.getOrderID(), model.getProductID(), model.getQuantity(), model.getPrice(), model.getOrderdetailID());
    }

    @Override
    public void delete(Orderdetail model) {
 Jdbc.executeUpdate(DELETE, model.getOrderdetailID());
    }

    
   public List<Orderdetail> selectById(int orderID) {
    List<Orderdetail> resultList = selectBySQL(SELECT_BY_ORDERID, orderID);
       System.out.println(""+resultList.size());
    if (!resultList.isEmpty()) {
      return resultList;
      
    } else {
        JOptionPane.showMessageDialog(new Oderdetailform(orderID), "không có hóa đơn chi tiết");
    }
    return null;
}


    @Override
    public List<Orderdetail> selectAll() {
return selectBySQL(SELECT_ALL_ODERDETAIL)  ;
    }

    @Override
    protected List<Orderdetail> selectBySQL(String sql, Object... args) {
      List<Orderdetail> list = new ArrayList<>();
        try {
            ResultSet rs = Jdbc.executeQuery(sql, args);
            while (rs.next()) {                
                Orderdetail model = new Orderdetail(rs.getInt("OrderdetailID"),rs.getInt("OrderID") , rs.getInt("ProductID"), rs.getInt("Quantity"), rs.getFloat("Price"));
                list.add(model);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
              throw new RuntimeException(e);
        }
    }

    @Override
    public Orderdetail selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
