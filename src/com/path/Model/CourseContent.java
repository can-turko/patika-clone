package com.path.Model;

import com.path.Helper.Conner;
import com.path.Helper.Helper;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CourseContent {
    private int id;
    private String title;
    private String description;
    private String ytLink;
    private String quizQuestion;
    private int course_id;
    private Course course;

    public CourseContent(int id, String title, String description, String ytLink, String quizQuestn, int course_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ytLink = ytLink;
        this.quizQuestion = quizQuestn;
        this.course_id = course_id;
    }

    public static ArrayList<CourseContent> getList(int courseId) {
        ArrayList<CourseContent> contentList = new ArrayList<>();
        CourseContent obj;
        try {
            Statement st = Conner.getConnect().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course_content WHERE course_id = " + courseId);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String descrption = rs.getString("description");
                String ytLink = rs.getString("ytLink");
                String quiz_qstn = rs.getString("quizQuestion");
                int course_id = rs.getInt("course_id");
                obj = new CourseContent(id, title, descrption, ytLink, quiz_qstn, course_id);
                contentList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentList;
    }

    public static ArrayList<CourseContent> getList(String contentTitle) {
        ArrayList<CourseContent> contentList = new ArrayList<>();
        CourseContent obj;
        try {
            PreparedStatement pr = Conner.getConnect().prepareStatement("SELECT * FROM course_content WHERE title = ?");
            pr.setString(1, contentTitle);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String descrption = rs.getString("description");
                String ytLink = rs.getString("ytLink");
                String quiz_qstn = rs.getString("quizQuestion");
                int course_id = rs.getInt("course_id");
                obj = new CourseContent(id, title, descrption, ytLink, quiz_qstn, course_id);
                contentList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentList;
    }

    public static boolean add(String title, String dscrptn, String ytLink, int course_id) {
        String query = "INSERT INTO course_content (title,description,ytLink,course_id) VALUES (?,?,?,?)";
        CourseContent findContent = fetch(title);
        if (findContent != null) {
            Helper.message("Same course cannot have another title.", "Notification");
            return false;
        } else {
            try {
                PreparedStatement pr = Conner.getConnect().prepareStatement(query);
                pr.setString(1, title);
                pr.setString(2, dscrptn);
                pr.setString(3, ytLink);
                pr.setInt(4, course_id);
                return pr.executeUpdate() != -1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static CourseContent fetch(String title) {
        CourseContent obj = null;
        String query = "SELECT * FROM course_content WHERE title = ?";
        try {
            PreparedStatement pr = Conner.getConnect().prepareStatement(query);
            pr.setString(1, title);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = new CourseContent(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("ytLink"), rs.getString("quizQuestion"), rs.getInt("course_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static boolean delete(int content_id) {
        String query = "DELETE FROM course_content WHERE id = ?";
        try {
            PreparedStatement pr = Conner.getConnect().prepareStatement(query);
            pr.setInt(1, content_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<CourseContent> searchContentForTitle(String title, int id) {
        String query = "SELECT * FROM public.content WHERE title ILIKE '%{{title}}%' AND course_id = " + id;
        query = query.replace("{{title}}", title);
        ArrayList<CourseContent> contentList = new ArrayList<>();
        CourseContent obj;
        try {
            Statement st = Conner.getConnect().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                obj = new CourseContent(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getString("ytLink"), rs.getString("quizQuestion"), rs.getInt("course_id"));
                contentList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contentList;
    }

    public static int getCourseID(String name) {
        String query = "SELECT id FROM lessons WHERE name = ?";
        int id = 0;
        try {
            PreparedStatement pr = Conner.getConnect().prepareStatement(query);
            pr.setString(1, name);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
            return id;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean addQuiz(String title, String quizQuestn) {
        String query = "UPDATE course_content SET quizQuestion=? WHERE title =?";
        try {
            PreparedStatement pr = Conner.getConnect().prepareStatement(query);
            pr.setString(1, quizQuestn);
            pr.setString(2, title);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYtLink() {
        return ytLink;
    }

    public void setYtLink(String ytLink) {
        this.ytLink = ytLink;
    }

    public String getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(String quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
