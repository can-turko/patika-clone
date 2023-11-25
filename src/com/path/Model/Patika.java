package com.path.Model;

import com.path.Helper.Conner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ArrayList<Patika> list(){
        ArrayList<Patika> list = new ArrayList<>();
        try {
            Statement st = Conner.getConnect().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patika;");
            while (rs.next()){
                Patika pa = new Patika(rs.getInt("id"),rs.getString("name"));
                list.add(pa);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static boolean add(String name){
        String query = "INSERT INTO patika (name) VALUES (?);";
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setString(1,name);
            return ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean update(int id, String name){
        String query = "UPDATE patika SET name =? WHERE id = ?;";
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setString(1,name);
            ps.setInt(2,id);
            return !ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Patika fetch(int id) {
        String query = "SELECT * FROM patika WHERE id = ?";
        Patika patika = null;
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                patika = new Patika(rs.getInt("id"),rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return patika;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM patika WHERE id = ?;";
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setInt(1,id);
            return !ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
}
