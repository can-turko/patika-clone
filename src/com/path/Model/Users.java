package com.path.Model;

import com.path.Helper.Conner;
import com.path.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Users {
    private int id;
    private String name;
    private String uname;
    private String pass;
    private String type;

    public Users(){

    }
    public static String searchQuery(String name, String uname, String type){
        String query = "SELECT * FROM users WHERE name LIKE '%{{name}}%' AND uname LIKE '%{{uname}}%'";
        query= query.replace("{{name}}",name);
        query= query.replace("{{uname}}",uname);
        if(!type.isEmpty()){
            query+=(" AND type LIKE '%{{type}}%';");
            query=query.replace("{{type}}",type);
        }
        return query;
    }

    public Users(int id, String name, String uname, String pass, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.pass = pass;
        this.type = type;
    }

    public static ArrayList<Users> getList(){
        ArrayList<Users> allList = new ArrayList<>();
        try {
            Statement st = Conner.getConnect().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while(rs.next()){
                Users user = new Users(rs.getInt("id"),rs.getString("name"),rs.getString("uname"),rs.getString("pass"),rs.getString("type"));
                allList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allList;
    }
    public static ArrayList<Users> getEducatorList(){
        ArrayList<Users> allList = new ArrayList<>();
        try {
            Statement st = Conner.getConnect().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users WHERE type = 'educator'");
            while(rs.next()){
                Users user = new Users(rs.getInt("id"),rs.getString("name"),rs.getString("uname"),rs.getString("pass"),rs.getString("type"));
                allList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allList;
    }

    public static void add(String name, String uname, String pass, String type){
        String query = "INSERT INTO users (name,uname,pass,type) VALUES (?,?,?,?);";
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setString(1,name);
            ps.setString(2,uname);
            ps.setString(3,pass);
            ps.setString(4,type);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Users fetch(String uname){
        String query = "SELECT * FROM users WHERE uname=?;";
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setString(1,uname);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Users(rs.getInt("id"),rs.getString("name"),rs.getString("uname"),rs.getString("pass"),rs.getString("type"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Users fetch(String uname, String pass){
        String query = "SELECT * FROM users WHERE uname=? AND pass =?;";
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setString(1,uname);
            ps.setString(2,pass);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Users(rs.getInt("id"),rs.getString("name"),rs.getString("uname"),rs.getString("pass"),rs.getString("type"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static int getPatikaID(String patikaName){
        String query = "SELECT id FROM patika WHERE name = ?";
        int id = 0;
        try {
            PreparedStatement pr = Conner.getConnect().prepareStatement(query);
            pr.setString(1,patikaName);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public static Users fetch(int id){
        String query = "SELECT * FROM users WHERE id=?;";
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new Users(rs.getInt("id"),rs.getString("name"),rs.getString("uname"),rs.getString("pass"),rs.getString("type"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static boolean deleteUser(int id){
        String query = "DELETE FROM users WHERE id = ?;";
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setInt(1,id);
            boolean result = ps.execute();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(int id, String name, String uname, String pass, String type){
        String query = "UPDATE users SET name = ?, uname = ?, pass = ?, type = ? WHERE id = ?;";
        Users findUser = Users.fetch(uname);
        if(findUser != null && findUser.getId() != id){
            Helper.message("This username is already taken","Notification");
            return false;
        }
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setString(1,name);
            ps.setString(2,uname);
            ps.setString(3,pass);
            ps.setString(4,type);
            ps.setInt(5,id);
            boolean result = ps.execute();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Users> searchByQuery(String query){
        ArrayList<Users> allList = new ArrayList<>();
        try {
            Statement st = Conner.getConnect().createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                Users user = new Users(rs.getInt("id"),rs.getString("name"),rs.getString("uname"),rs.getString("pass"),rs.getString("type"));
                allList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allList;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
