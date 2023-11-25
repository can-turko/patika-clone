package com.path.View;


import com.path.Helper.Config;
import com.path.Helper.Helper;
import com.path.Model.Course;
import com.path.Model.CourseContent;
import com.path.Model.Item;
import com.path.Model.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EducatorGUI extends JFrame{
    private JPanel wrapper;
    private JLabel lbl_welcome;
    private JTable tbl_course_list;
    private JPanel pnl_course_list;
    private JScrollPane scrl_course_list;
    private JComboBox cmb_content_course;
    private JComboBox cmb_content_title;
    private JButton btn_content_add;
    private JEditorPane pane_content_quiz;
    private JButton btn_educator_logout;
    private final Users educator;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;
    private int course_id;


    public EducatorGUI(Users educator){
        this.educator = educator;
        add(wrapper);
        setSize(1000,500);
        int x = Helper.dimention("x",getSize());
        int y = Helper.dimention("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Educator Panel");
        setVisible(true);
        lbl_welcome.setText("Wellcome, " + educator.getName());


        mdl_course_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };

        mdl_course_list.setColumnIdentifiers(Config.EDUCATOR);
        row_course_list = new Object[Config.EDUCATOR.length];
        loadEducatorModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        loadContentCourseCombo();
        //String title = cmb_content_course.getSelectedItem().toString();
        loadContentTitleCombo();
        tbl_course_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Point point = e.getPoint();
                    int selected_row = tbl_course_list.rowAtPoint(point);
                    int selected_column = tbl_course_list.columnAtPoint(point);
                    tbl_course_list.setRowSelectionInterval(selected_column, selected_row);
                    dispose();
                    ContentGUI contentGUI = new ContentGUI((String) tbl_course_list.getValueAt(selected_row, 0));
                }catch (IllegalArgumentException exception){
                    exception.getStackTrace();
                }
            }
        });


        btn_educator_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginPage();
            }
        });
        btn_content_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = cmb_content_title.getSelectedItem().toString();
                String quizText = pane_content_quiz.getText();
                if(CourseContent.addQuiz(title,quizText)){
                    Helper.message("done","notification");
                    pane_content_quiz.setText(null);
                }else {
                    Helper.message("error","notification");
                    pane_content_quiz.setText(null);
                }

            }
        });
        cmb_content_course.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String title = cmb_content_course.getSelectedItem().toString();
                loadContentTitleCombo();
            }
        });
    }


    public void loadEducatorModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for(Course obj :  Course.getList()){
            i = 0;
            if (obj.getEducator().getName().equals(educator.getName())){
                row_course_list[i++] = obj.getName();
                row_course_list[i++] = obj.getPatika().getName();
                mdl_course_list.addRow(row_course_list);
            }

        }
    }

    public void loadContentCourseCombo(){
        cmb_content_course.removeAllItems();
        for(Course obj :  Course.getList()){
            if (obj.getEducator().getName().equals(educator.getName())){
                cmb_content_course.addItem(new Item(obj.getId(), obj.getName()));
            }
        }
    }

    public void loadContentTitleCombo(){
        cmb_content_title.removeAllItems();
        String title = cmb_content_course.getSelectedItem().toString();
        int id = CourseContent.getCourseID(title);
        for (CourseContent obj: CourseContent.getList(id)){
            cmb_content_title.addItem(new Item(obj.getId(),obj.getTitle()));

        }
    }


}
