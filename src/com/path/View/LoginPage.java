package com.path.View;

import com.path.Helper.Helper;
import com.path.Model.Users;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    private JPanel wrapper;
    private JTextField fd_username;
    private JPasswordField fd_password;
    private JButton btn_confirm;
    private JButton btn_sign;
    private JLabel iconLabel;

    public LoginPage() {

        add(wrapper);
        setVisible(true);
        setSize(400, 355);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2;

        setLocation(x, y);
        Helper.theme();

        btn_confirm.addActionListener(e -> {
            if (fd_username.getText().isEmpty() || fd_password.getText().isEmpty()) {
                Helper.message("Fill all areas", "Notification");
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            } else {
                Users u = Users.fetch(fd_username.getText(),fd_password.getText());
                if(u == null){
                    Helper.message("Username or password is wrong", "Notification");
                }
                else {
                    switch (u.getType()){
                        case "operator":
                            new Operator(u);
                            dispose();
                            break;
                        case "educator":
                            new EducatorGUI(u);
                            dispose();
                            break;
                        case "student":
                            new StudentGUI(u);
                            dispose();
                            break;
                    }
                }
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        btn_sign.addActionListener(e -> {
            new SignInGui();
        });
    }

}
