/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import form.Customer;
import helper.Jdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Result;
import model.Customers;

/**
 *
 * @author ASUS
 */
public class CustomerDAO extends MainDAO<Customers> {

    String INSERT = "INSERT INTO Customers (Name, Adress, Email, SDT) VALUES (?, ?, ?, ?)";

    String UPDATE = "UPDATE Customers SET Name = ?, Adress = ?, Email = ?, SDT = ? WHERE CustomersID = ?";

    String DELETE = "DELETE FROM Customers WHERE Name = ?";

    String SELECT_BY_SDT = "SELECT * FROM Customers WHERE SDT = ?";

    String SELECT_ALL = "SELECT * FROM Customers";

    @Override
    public void insert(Customers model) {
        try {
            Jdbc.executeUpdate(INSERT, model.getName(), model.getAdress(), model.getEmail(), model.getSDT());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Customers model) {
        try {
            Jdbc.executeUpdate(UPDATE, model.getName(),model.getAdress(),model.getEmail(),model.getSDT());
        } catch (Exception e) {
              throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Customers model) {
        try {
            Jdbc.executeUpdate(DELETE, model.getName());
        } catch (Exception e) {
        }
    }


 
    public List<Customers> selectBySDT(String name) {
      
    return selectBySQL(SELECT_BY_SDT, name);
    }

    @Override
    public List<Customers> selectAll() {
 return selectBySQL(SELECT_ALL);
    }

    @Override
    protected List<Customers> selectBySQL(String sql, Object... args) {
        List<Customers> list = new ArrayList<>();
        try {
            ResultSet rs = Jdbc.executeQuery(sql, args);
            while (rs.next()) {
                Customers model = new Customers(rs.getInt("CustomersID"), rs.getString("Name"), rs.getString("Adress"), rs.getString("Email"), rs.getString("SDT"));
                list.add(model);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customers selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
