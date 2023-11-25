package com.path.View;

import com.path.Helper.Config;
import com.path.Helper.Helper;
import com.path.Model.Item;
import com.path.Model.Lessons;
import com.path.Model.Patika;
import com.path.Model.Users;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Operator extends JFrame {
    private JPanel wrapper;
    private JButton btn_exit;
    private JTabbedPane tabbedPane1;
    private JTable tbl_list;
    private JLabel txt_wellcome;
    private JTextField fd_name;
    private JTextField fd_uname;
    private JPasswordField fd_pass;
    private JButton btn_add;
    private JComboBox cmb_type;
    private JTextField fd_delete;
    private JButton btn_delete;
    private JTextField fd_sh_uname;
    private JComboBox cmd_sh_type;
    private JButton btn_sh;
    private JTextField fd_sh_name;
    private JTable tbl_patika;
    private JTextField fd_add_patika;
    private JButton btn_patika_add;
    private JTable tbl_lessons;
    private JTextField fd_lesson_name;
    private JTextField fd_lesson_lang;
    private JComboBox cmb_patika;
    private JComboBox cmb_educator;
    private JButton btn_lesson_add;
    private JTextField fd_lesson_delete;
    private JButton btn_lesson_delete;
    private DefaultTableModel mdl_tbl_list;

    private DefaultTableModel mdl_patika_list;
    private JPopupMenu patika_right_click;

    private DefaultTableModel mdl_lesson;
    private JPopupMenu lesson_right_click;

    public Operator(Users user) {
        add(wrapper);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 490);
        setLocation(Helper.dimention("x", getSize()), Helper.dimention("y", getSize()));
        txt_wellcome.setText("Wellcome: " + user.getName());



        //list model
        mdl_tbl_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) return false;
                return super.isCellEditable(row, column);
            }
        };
        mdl_tbl_list.setColumnIdentifiers(Config.ORDER);
        tbl_list.setModel(mdl_tbl_list);
        tbl_list.getTableHeader().setReorderingAllowed(false);
        loadAll();
        //--------


        //table listener
        tbl_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String str = tbl_list.getValueAt(tbl_list.getSelectedRow(), 0).toString();
                fd_delete.setText(str);
            } catch (Exception e1) {
            }
        });
        //----------


        //Exit button
        btn_exit.addActionListener(e -> {
            dispose();
        });
        //-------------


        //panel add button
        btn_add.addActionListener(e -> {
            if (fd_name.getText().isEmpty() || fd_pass.getText().isEmpty() || fd_uname.getText().isEmpty()) {
                Helper.message("Please fill all fields", "Notification");
            } else if (Users.fetch(fd_uname.getText()) != null) {
                Helper.message("this Username is already taken.", "Warning");
            } else {
                Users.add(fd_name.getText(), fd_uname.getText(), fd_pass.getText(), cmb_type.getSelectedItem().toString());
                Helper.message("New user is added", "Notification");

                fd_uname.setText(null);
                fd_pass.setText(null);
                fd_name.setText(null);
            }
            loadAll();
            loadCmbEducator();
        });
        //-------------

        //delete button
        btn_delete.addActionListener(e -> {
            Users.deleteUser(Integer.parseInt(fd_delete.getText()));
            Helper.message("Deleted", "Notification");
            fd_delete.setText(null);
            loadAll();
            loadCmbEducator();
        });
        //----------------

        //update
        tbl_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int id = Integer.parseInt(tbl_list.getValueAt(tbl_list.getSelectedRow(), 0).toString());
                String name = tbl_list.getValueAt(tbl_list.getSelectedRow(), 1).toString();
                String uname = tbl_list.getValueAt(tbl_list.getSelectedRow(), 2).toString();
                String pass = tbl_list.getValueAt(tbl_list.getSelectedRow(), 3).toString();
                String type = tbl_list.getValueAt(tbl_list.getSelectedRow(), 4).toString();
                if (((type.equals("student") || type.equals("educator") || type.equals("operator")))) {
                    if (Users.update(id, name, uname, pass, type)) {
                        Helper.message("Succesfull", "Update");

                    }
                    loadAll();
                    loadCmbEducator();
                } else {
                    Helper.message("Type should be: 'student' or 'educator' or 'operator'", "Type Error");
                }

            }
        });

        //search button
        btn_sh.addActionListener(e -> {
            String name = fd_sh_name.getText();
            String uname = fd_sh_uname.getText();
            String type = cmd_sh_type.getSelectedItem().toString();
            loadSearch(name, uname, type);
        });
        //-----------------


        //patika table
        mdl_patika_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        mdl_patika_list.setColumnIdentifiers(Config.PATIKA_ORDER);
        tbl_patika.setModel(mdl_patika_list);
        loadPatika();
        tbl_patika.getTableHeader().setReorderingAllowed(false);
        tbl_patika.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(70);
        //----------------------------

        //Patika add
        btn_patika_add.addActionListener(e -> {
            if (!Patika.add(fd_add_patika.getText())) {
                Helper.message("Added", "Notification");
                loadPatika();
                loadCmbPatika();
            }
        });
        //--------------

        //patika list right click
        patika_right_click = new JPopupMenu();
        JMenuItem updatePatika = new JMenuItem("Update");
        JMenuItem deletePatika = new JMenuItem("Delete");
        patika_right_click.add(updatePatika);
        patika_right_click.add(deletePatika);
        tbl_patika.setComponentPopupMenu(patika_right_click);

        //

        //patika update right click
        updatePatika.addActionListener(e -> {
            int id = Integer.parseInt(tbl_patika.getValueAt(tbl_patika.getSelectedRow(), 0).toString());
            Patika patika = Patika.fetch(id);
            UpdatePatikaGUI updatePatikaGUI = new UpdatePatikaGUI(patika);
            updatePatikaGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    loadPatika();
                    loadCmbPatika();
                }
            });
        });
        //-------------

        //patika delete right click
        deletePatika.addActionListener(e -> {
            int id = Integer.parseInt(tbl_patika.getValueAt(tbl_patika.getSelectedRow(), 0).toString());
            if (Helper.confirmMessage("Do you want to delete?")) {
                if (Patika.delete(id)) {
                    Helper.message("Deleted", "Notification");
                    loadPatika();
                    loadCmbPatika();
                } else {
                    Helper.message("Couldn't deleted", "Error");
                }
            }

        });
        //--------------------

        //right click patika selection
        tbl_patika.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int i = tbl_patika.rowAtPoint(point);
                tbl_patika.setRowSelectionInterval(i, i);
            }
        });
        //---------------


        //righ click lesson selection
        tbl_lessons.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int i = tbl_lessons.rowAtPoint(point);
                tbl_lessons.setRowSelectionInterval(i,i);
            }
        });

        //lesson table
        mdl_lesson = new DefaultTableModel();
        mdl_lesson.setColumnIdentifiers(Config.LESSON);
        tbl_lessons.setModel(mdl_lesson);
        loadLessons();
        //_______

        loadCmbPatika();
        loadCmbEducator();

        //Add Lesson
        btn_lesson_add.addActionListener(e -> {
            int user_id = ((Item) cmb_educator.getSelectedItem()).getId();
            int patika_id = ((Item) cmb_patika.getSelectedItem()).getId();
            String name = fd_lesson_name.getText();
            String lang = fd_lesson_lang.getText();
            if (Lessons.add(user_id, patika_id, name, lang)) {
                Helper.message("Added", "Notification");

                loadLessons();

            } else Helper.message("Couldn't added", "Error");
        });
        //---------------

        //lesson table listener
        tbl_lessons.getSelectionModel().addListSelectionListener(e -> {
            try {
                fd_lesson_delete.setText(tbl_lessons.getValueAt(tbl_lessons.getSelectedRow(), 0).toString());
            }catch (Exception exp){

            }
        });
        //-------------


        //Delete lesson
        btn_lesson_delete.addActionListener(e -> {
            int id = Integer.parseInt(fd_lesson_delete.getText().toString());
            if(Helper.confirmMessage("Are you sure?")){
                if (Lessons.delete(id)){
                    Helper.message("Deleted","Notification");
                    loadLessons();
                }
                else {
                    Helper.message("Couldn't deleted","Error");
                }
            }
        });
        //----------

        //lesson righ click
        lesson_right_click = new JPopupMenu();
        JMenuItem updateLesson = new JMenuItem("update");
        lesson_right_click.add(updateLesson);
        tbl_lessons.setComponentPopupMenu(lesson_right_click);
        //-----------


        //lesson table update
        updateLesson.addActionListener(e -> {
            int id = Integer.parseInt(tbl_lessons.getValueAt(tbl_lessons.getSelectedRow(),0).toString());
            Lessons l = Lessons.fetch(id);
            UpdateLessonGui lesson = new UpdateLessonGui(l);
            lesson.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadLessons();
                }
            });
        });

    }

    private void loadCmbEducator() {
        cmb_educator.removeAllItems();
        for (Users user : Users.getEducatorList()) {
            Item item = new Item(user.getId(), user.getName());
            cmb_educator.addItem(item);
        }
    }




    private void loadCmbPatika() {
        cmb_patika.removeAllItems();
        for (Patika pa : Patika.list()) {
            Item item = new Item(pa.getId(), pa.getName());
            cmb_patika.addItem(item);
        }

    }

    private void loadLessons() {
        DefaultTableModel q = (DefaultTableModel) tbl_lessons.getModel();
        q.setRowCount(0);
        for (Lessons l : Lessons.list()) {
            Object[] obj = {l.getId(), l.getName(), l.getLang(), l.getPatika().getName(), l.getUser().getName()};
            mdl_lesson.addRow(obj);
        }
    }

    private void loadPatika() {
        DefaultTableModel ro = (DefaultTableModel) tbl_patika.getModel();
        ro.setRowCount(0);
        ArrayList<Patika> list = Patika.list();
        for (Patika pa : list) {
            Object[] obje = {pa.getId(), pa.getName()};
            mdl_patika_list.addRow(obje);
        }
    }

    public void loadAll() {
        ArrayList<Users> users = Users.getList();
        DefaultTableModel obj = (DefaultTableModel) tbl_list.getModel();
        obj.setRowCount(0);
        for (Users user : users) {
            Object[] obje = {user.getId(), user.getName(), user.getUname(), user.getPass(), user.getType()};
            mdl_tbl_list.addRow(obje);
        }
    }

    public void loadSearch(String name, String uname, String type) {
        ArrayList<Users> list = Users.searchByQuery(Users.searchQuery(name, uname, type));
        DefaultTableModel model = (DefaultTableModel) (tbl_list.getModel());
        model.setRowCount(0);
        list.stream().forEach(e -> {
            Object[] obje = {e.getId(), e.getName(), e.getUname(), e.getPass(), e.getType()};
            mdl_tbl_list.addRow(obje);
        });
    }
}
