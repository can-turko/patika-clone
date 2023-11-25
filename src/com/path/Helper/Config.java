package com.path.Helper;

public class Config {
    public static final String DB_URL="jdbc:mysql://localhost:3306/hb-01-one-to-one-uni";
    public static final String DB_USERNAME="root";
    public static final String DB_PASSWORD="canname12";
    public static final Object[] ORDER = {"ID","name","uname","pass","type"};
    public static final Object[] PATIKA_ORDER = {"ID","name"};
    public static final Object[] LESSON = {"ID", "Name", "Software", "Patika", "Educator"};
    public static final Object[] COURSE_CONTENT = {"ID", "Title", "Description", "Youtebe link", "Quiz Questions","Course_id"};
    public static final Object[] EDUCATOR = {"Lesson","Path"};
    public static final Object[] STUDENT_PATH ={"lessons",""};
}
