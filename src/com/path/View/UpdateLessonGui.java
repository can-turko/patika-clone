package com.path.View;

import com.path.Helper.Helper;
import com.path.Model.Item;
import com.path.Model.Lessons;
import com.path.Model.Patika;
import com.path.Model.Users;

import javax.swing.*;

public class UpdateLessonGui  extends JFrame{
    private JPanel wrapper;
    private JTextField fd_updateLesson_name;
    private JTextField fd_updateLesson_software;
    private JComboBox cmb_updateLesson_patika;
    private JComboBox cmb_updateLesson_educator;
    private JButton btn_updateLesson;

    public UpdateLessonGui(Lessons lesson){
        add(wrapper);
        setSize(700,170);
        setVisible(true);
        setTitle("Update lesson");
        fd_updateLesson_name.setText(lesson.getName());
        fd_updateLesson_software.setText(lesson.getLang());
        loadPatika();
        loadEducator();
        setLocation(Helper.dimention("x",getSize()),Helper.dimention("y",getSize()));

        btn_updateLesson.addActionListener(e -> {
            if(fd_updateLesson_name.getText().isEmpty() || fd_updateLesson_software.getText().isEmpty() || cmb_updateLesson_educator.getSelectedItem() == null || cmb_updateLesson_patika.getSelectedItem()==null ){
                Helper.message("Fill all areas","Notification");
            }
            else {
                int id = lesson.getId();
                String name=fd_updateLesson_name.getText();
                String lang = fd_updateLesson_software.getText();
                int patikaId = ((Item) cmb_updateLesson_patika.getSelectedItem()).getId();
                int educatorId= ((Item) cmb_updateLesson_educator.getSelectedItem()).getId();
                if(Lessons.update(id,name,lang,patikaId,educatorId)){
                    Helper.message("Updated","Notification");
                    dispose();
                }
                else Helper.message("Database error","Notification");
            }
        });
    }

    private void loadEducator() {
        cmb_updateLesson_educator.addItem(null);
        for(Users u: Users.getEducatorList()){
            Item item = new Item(u.getId(),u.getName());
            cmb_updateLesson_educator.addItem(item);
        }
    }

    private void loadPatika() {
        cmb_updateLesson_patika.addItem(null);
        for(Patika pa: Patika.list()){
            Item item = new Item(pa.getId(), pa.getName());
            cmb_updateLesson_patika.addItem(item);
        }
    }
}
