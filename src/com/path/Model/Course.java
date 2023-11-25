package com.path.Model;

import com.path.Helper.Conner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String lang;

    private Patika patika;
    private Users educator;

    public Course(int id, int user_id, int patika_id, String name, String lang) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.lang = lang;
        this.patika = Patika.fetch(patika_id);
        this.educator = Users.fetch(user_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public Users getEducator() {
        return educator;
    }

    public void setEducator(Users educator) {
        this.educator = educator;
    }

    public static ArrayList<Course> getList(){
        ArrayList<Course> coursList = new ArrayList<>();
        Course obj;
        try {
            Statement st = Conner.getConnect().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM  lessons");
            while (rs.next()){
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Course(id,user_id,patika_id,name,lang);
                coursList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coursList;
    }

    public static ArrayList<Course> getListByUserId(int userId){
        ArrayList<Course> coursList = new ArrayList<>();
        Course obj;
        try {
            Statement st = Conner.getConnect().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM  lessons WHERE user_id = " + userId);
            while (rs.next()){
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                obj = new Course(id,user_id,patika_id,name,lang);
                coursList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coursList;
    }

    public static boolean add(int user_id, int patika_id, String name, String lang){
        String query = "INSERT INTO lessons ( user_id, patika_id, name, lang) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = Conner.getConnect().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,patika_id);
            pr.setString(3,name);
            pr.setString(4,lang);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean delete(int id){
        String query = "DELETE FROM lessons WHERE id = ?";
        try {
            PreparedStatement pr = Conner.getConnect().prepareCall(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
