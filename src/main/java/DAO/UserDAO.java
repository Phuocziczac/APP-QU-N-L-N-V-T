/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.microsoft.sqlserver.jdbc.SQLServerResultSet;
import helper.Jdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author ASUS
 */
public class UserDAO extends MainDAO<User> {

    String INSERT_USER = "INSERT INTO [User] (Name, Role, Age, SDT, Email, Password, Adress,Wage, Img) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
    String UPDATE_USER = "UPDATE [User] SET Name=?, Role=?, Age=?, SDT=?, Email=?, Password=?, Adress=?,Wage = ? ,Img=? WHERE UserID=?";
    String UPDATE_PASS_BY_EMAIL = "UPDATE [User] SET  Password=? WHERE Email=?";
    String DELETE_USER = "DELETE  [User] WHERE UserID=?";
    String SELECT_USER_BY_ID = "SELECT * FROM [User] WHERE UserID=?";
    String SELECT_ALL_USERS = "SELECT * FROM [User]";
    String SELECT_ALL_BY_KEYWORD = "SELECT * FROM [User] where Role = 0 and Name like ?";
    String SELECT_USER_BY_SDT = "SELECT * FROM [User] WHERE SDT = ? ";
    String SELECT_USER_BY_PASSWORD = "SELECT * FROM [User] WHERE Password = ? ";

    @Override
    public void insert(User model) {
        String name = (model.getName() != null && !model.getName().isEmpty()) ? model.getName() : "chưa cập nhật";
        String sdt = (model.getSDT() != null && !model.getSDT().isEmpty()) ? model.getSDT() : "chưa cập nhật";
        String age = (model.getAge() != null && !model.getAge().isEmpty()) ? model.getAge() : "chưa cập nhật";
        String email = (model.getEmail() != null && !model.getEmail().isEmpty()) ? model.getEmail() : "chưa cập nhật";
        String address = (model.getAdress() != null && !model.getAdress().isEmpty()) ? model.getAdress() : "chưa cập nhật";
        String wage = (String.valueOf(model.getWage()) != null && !String.valueOf(model.getWage()).isEmpty()) ? String.valueOf(model.getWage()) : "chưa cập nhật";
        String password = (model.getPassword() != null && !model.getPassword().isEmpty()) ? model.getPassword() : "chưa cập nhật";
        String img = (model.getImg() != null && !model.getImg().isEmpty()) ? model.getImg() : "chưa cập nhật";
        Jdbc.executeUpdate(INSERT_USER,
                name,
                model.isRole(),
                age,
                sdt,
                email,
                password,
                address,
                wage,
                img
        );
    }

    @Override
    public void update(User model) {
        Jdbc.executeUpdate(UPDATE_USER,
                model.getName(),
                model.isRole(),
                model.getAge(),
                model.getSDT(),
                model.getEmail(),
                model.getPassword(),
                model.getAdress(),
                model.getWage(),
                model.getImg(),
                model.getUserID());

    }

    public void updatepass(User model) {
        Jdbc.executeUpdate(UPDATE_PASS_BY_EMAIL, model.getPassword(), model.getEmail());

    }

    @Override
    public void delete(User model) {
        Jdbc.executeUpdate(DELETE_USER,
                model.getUserID());
    }

    @Override
    public User selectById(String id) {
        SQLServerResultSet resultSet = (SQLServerResultSet) Jdbc.executeQuery(SELECT_USER_BY_ID, id);
        try {
            if (resultSet != null && resultSet.next()) {
                User user = new User(
                        resultSet.getInt("UserID"),
                        resultSet.getString("Name"),
                        resultSet.getBoolean("Role"),
                        resultSet.getString("Age"),
                        resultSet.getString("SDT"),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        resultSet.getString("Adress"),
                        resultSet.getFloat("Wage"),
                        resultSet.getString("Img")
                );
                return user;
            } else {
                return null;
            }
        } catch (SQLServerException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public User selectUserBySDT(String key) throws SQLServerException {
        SQLServerResultSet resultSet = (SQLServerResultSet) Jdbc.executeQuery(SELECT_USER_BY_SDT, key);
        if (resultSet != null && resultSet.next()) {
            User user = new User(
                    resultSet.getInt("UserID"),
                    resultSet.getString("Name"),
                    resultSet.getBoolean("Role"),
                    resultSet.getString("Age"),
                    resultSet.getString("SDT"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("Adress"),
                    resultSet.getFloat("Wage"),
                    resultSet.getString("Img")
            );
            return user;
        } else {
            return null;
        }
    }

    public User selectUserByPassWord(String key) throws SQLServerException {
        SQLServerResultSet resultSet = (SQLServerResultSet) Jdbc.executeQuery(SELECT_USER_BY_PASSWORD, key);
        if (resultSet != null && resultSet.next()) {
            User user = new User(
                    resultSet.getInt("UserID"),
                    resultSet.getString("Name"),
                    resultSet.getBoolean("Role"),
                    resultSet.getString("Age"),
                    resultSet.getString("SDT"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("Adress"),
                    resultSet.getFloat("Wage"),
                    resultSet.getString("Img")
            );
            return user;
        } else {
            return null;
        }
    }

    @Override
    public List<User> selectAll() {
        return this.selectBySQL(SELECT_ALL_USERS);
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<User> SelectAllByKeyWord(String key) {
        return this.selectBySQL(SELECT_ALL_BY_KEYWORD, key);
    }

    @Override
    protected List<User> selectBySQL(String sql, Object... args) {
        List<User> list = new ArrayList<>();
        try {
            ResultSet rs = Jdbc.executeQuery(sql, args);
            while (rs.next()) {
                User model = new User(rs.getInt("UserID"), rs.getString("Name"), rs.getBoolean("Role"), rs.getString("Age"), rs.getString("SDT"), rs.getString("Email"), rs.getString("Password"), rs.getString("Adress"), rs.getFloat("Wage"), rs.getString("Img"));
                list.add(model);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
