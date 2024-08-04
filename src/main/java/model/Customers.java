/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class Customers {

    private int CustomersID;
    private String Name;
    private String Adress;
    private String Email;
    private String SDT;

    public Customers(int CustomersID, String Name, String Adress, String Email, String SDT) {
        this.CustomersID = CustomersID;
        this.Name = Name;
        this.Adress = Adress;
        this.Email = Email;
        this.SDT = SDT;
    }

    public Customers(String Name, String Adress, String Email, String SDT) {
        this.Name = Name;
        this.Adress = Adress;
        this.Email = Email;
        this.SDT = SDT;
    }
    

    public int getCustomersID() {
        return CustomersID;
    }

    public void setCustomersID(int CustomersID) {
        this.CustomersID = CustomersID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
    
    
    
}
