package com.path.View;

import com.path.Helper.Helper;
import com.path.Model.Patika;

import javax.swing.*;

public class UpdatePatikaGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fd_patika;
    private JButton btn_add;

    UpdatePatikaGUI(Patika patika){
        add(wrapper);
        setTitle("Update");
        setLocation(Helper.dimention("x", getSize()), Helper.dimention("y", getSize()));
        setSize(400,160);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        fd_patika.setText(patika.getName());

        //update button
        btn_add.addActionListener(e -> {
            if(Patika.update(patika.getId(),fd_patika.getText())){
                Helper.message("Updated","Notification");
                dispose();
            }
        });
        //
    }
}
