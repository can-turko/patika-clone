package com.path.Model;

import com.path.Helper.Conner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Lessons {
    private int id;
    private Users user;
    private Patika patika;
    private String name;
    private String lang;

    public Lessons(int id, int userId, int patikaId, String name, String lang) {
        this.id = id;
        this.user = Users.fetch(userId);
        this.patika = Patika.fetch(patikaId);
        this.name = name;
        this.lang = lang;
    }

    public Lessons() {
    }

    public static ArrayList<Lessons> list() {
        ArrayList<Lessons> list = new ArrayList<>();
        String query = "SELECT * FROM lessons;";
        try {
            Statement st = Conner.getConnect().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                int patika_id = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                Lessons l1 = new Lessons(id, user_id, patika_id, name, lang);
                list.add(l1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public static boolean add(int user_id, int patika_id, String name, String lang){
        String query = "INSERT INTO lessons (user_id,patika_id,name,lang) VALUES(?,?,?,?);";
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setInt(1,user_id);
            ps.setInt(2,patika_id);
            ps.setString(3,name);
            ps.setString(4,lang);
            return !ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM lessons WHERE id = ?;";
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setInt(1,id);
            return !ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean update(int id, String name, String lang, int patikaId, int educatorId) {
        String query = "UPDATE lessons SET name = ?, lang = ?, user_id = ?, patika_id = ? WHERE id = ?;";
        try {
            PreparedStatement ps = Conner.getConnect().prepareStatement(query);
            ps.setString(1,name);
            ps.setString(2,lang);
            ps.setInt(3,educatorId);
            ps.setInt(4,patikaId);
            ps.setInt(5,id);
            return !ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Lessons fetch(int id) {
        String query = "SELECT * FROM lessons WHERE id = "+id;
        try {
            Statement st = Conner.getConnect().createStatement();
            ResultSet rs = st.executeQuery(query);
            Lessons lesson = null;
            if(rs.next()){
                int lessonId = rs.getInt("id");
                int userId = rs.getInt("user_id");
                int patikaId = rs.getInt("patika_id");
                String name = rs.getString("name");
                String lang = rs.getString("lang");
                lesson = new Lessons(lessonId,userId,patikaId,name,lang);
            }
            return lesson;

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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
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
}
