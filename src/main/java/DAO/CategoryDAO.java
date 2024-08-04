/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import helper.Jdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Categories;
import model.Products;

/**
 *
 * @author ASUS
 */
public class CategoryDAO extends MainDAO<Categories> {

    String INSERT_CATEGORY = "INSERT INTO Categories (Name) values (?)";
    String UPDATE_CATEGORY = "UPDATE Categories set Name = ? where CategoriesID = ? ";
    String DELETE_CATEGORY = "DELETE from Categories where CategoriesID = ?";
    String SELECT_CATEGORY_BY_ID = "SELECT * FROM Categories where CategoriesID = ? ";
    String SELECT_ALL = "SELECT  * FROM Categories ";
     String SELECT_CATETORYID = "SELECT CategoriesID FROM Categories";

    @Override
    public void insert(Categories model) {
        Jdbc.executeUpdate(INSERT_CATEGORY, model.getName());
    }

    @Override
  
public void update(Categories model) {
    Jdbc.executeUpdate(UPDATE_CATEGORY, model.getName(), model.getCategoriesID());
}


    @Override
    public void delete(Categories model) {
        Jdbc.executeUpdate(DELETE_CATEGORY, model.getCategoriesID());
        
    }

    @Override
    public Categories selectById(String id) {
        try {
          ResultSet rs =  Jdbc.prepareStatement(SELECT_CATEGORY_BY_ID, id).executeQuery();
            while (rs.next()) {                
               return new Categories(rs.getInt("CategoriesID"), rs.getString("Name"));
            }
        } catch (SQLException ex) {
             Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    @Override
    public List<Categories> selectAll() {
        return selectBySQL(SELECT_ALL);
    }
       public List<Categories> selectCategoryID() {
       return selectBySQL(SELECT_CATETORYID);
    }

    @Override
    protected List<Categories> selectBySQL(String sql, Object... args) {
        List<Categories> list = new ArrayList<>();
        try {
            ResultSet rs = Jdbc.executeQuery(sql, args);
            while (rs.next()) {
                Categories category = new Categories(rs.getInt("CategoriesID"), rs.getString("Name"));

                list.add(category);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
