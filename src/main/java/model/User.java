/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ASUS
 */
public class User {
    private  int UserID;
    private String Name;
    private boolean Role;
    private String Age;
    private String SDT;
    private String Email;
    private String Password;
    private String Adress;
    private float Wage;
    private String Img;

    public User(String Name, boolean Role, String SDT, String Password) {
        this.Name = Name;
        this.Role = Role;
        this.SDT = SDT;
        this.Password = Password;
    }

    public User(String Email, String Password) {
        this.Email = Email;
        this.Password = Password;
    }

  

    
    
    
    public User(String Name, boolean Role, String Age, String SDT, String Email, String Password, String Adress, float Wage, String Img) {
        this.Name = Name;
        this.Role = Role;
        this.Age = Age;
        this.SDT = SDT;
        this.Email = Email;
        this.Password = Password;
        this.Adress = Adress;
        this.Wage = Wage;
        this.Img = Img;
    }

    public User(int UserID, String Name, boolean Role, String Age, String SDT, String Email, String Password, String Adress, float Wage, String Img) {
        this.UserID = UserID;
        this.Name = Name;
        this.Role = Role;
        this.Age = Age;
        this.SDT = SDT;
        this.Email = Email;
        this.Password = Password;
        this.Adress = Adress;
        this.Wage = Wage;
        this.Img = Img;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public boolean isRole() {
        return Role;
    }

    public void setRole(boolean Role) {
        this.Role = Role;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public float getWage() {
        return Wage;
    }

    public void setWage(float Wage) {
        this.Wage = Wage;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String Img) {
        this.Img = Img;
    }

  
   
   

   
   
    
}
