/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;

/**
 *
 * @author ASUS
 */

public abstract class MainDAO<T> {
    
    abstract public void insert(T model);
     abstract public void update(T model);
      abstract public void delete(T model);
        abstract public T selectById(String id);

    abstract public List<T> selectAll();

    abstract protected List<T> selectBySQL(String sql, Object... args);
    
    
}
