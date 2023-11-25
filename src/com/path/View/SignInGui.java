package com.path.View;


import com.path.Helper.Conner;
import com.path.Helper.Helper;
import com.path.Model.Users;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignInGui extends JFrame{
    private JTextField fld_e_mail;
    private JTextField fld_uname;
    private JTextField fld_firstname;
    private JTextField fld_surname;
    private JTextField fld_pass;
    private JTextField fld_pass_again;
    private JPanel wrapper;
    private JButton btn_signin;

    public SignInGui(){
        add(wrapper);
        setSize(600,300);
        setLocation(Helper.dimention("x",getSize()),Helper.dimention("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Sign in");
        setResizable(false);
        setVisible(true);
        btn_signin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = fld_e_mail.getText();
                String uname = fld_uname.getText();
                String firstname = fld_firstname.getText();
                String surname = fld_surname.getText();
                String pass = fld_pass.getText();
                String pass_again = fld_pass_again.getText();

                String name = firstname + " " + surname;
                if (Helper.isFieldEmpty(fld_e_mail) || Helper.isFieldEmpty(fld_uname) || Helper.isFieldEmpty(fld_firstname) || Helper.isFieldEmpty(fld_surname) || Helper.isFieldEmpty(fld_pass) || Helper.isFieldEmpty(fld_pass_again)){
                    Helper.message("fill","Notification");
                }else{
                    if (isEmailValid(email) && isPasswordsMatch(pass,pass_again)){
                        addNewStudent(name,uname,pass);
                        new LoginPage();
                        dispose();
                    }
                }


            }
        });
    }

    private boolean isEmailValid(String mail){
        if (mail.contains("@gmail.com") || mail.contains("@hotmail.com") || mail.contains("@icloud.com") || mail.contains("@yahoo.com") || mail.contains("@yandex.com") || mail.contains("@outlook.com")){
            return true;
        }else {
            Helper.message("Please enter a valid e-mail","Notification");
            return false;
        }
    }
    private boolean isPasswordsMatch(String pass, String pass_again){
        if (!(pass.equals(pass_again))){
            Helper.message("Password isn't same","Notification");
            return false;
        }
        return true;
    }

    private boolean addNewStudent(String name, String uname, String pass){
        String query = "INSERT INTO users (name,uname,pass,type) VALUES (?,?,?,'student')";
        Users findUser = Users.fetch(uname);
        if (findUser != null){
            Helper.message("This username is already taken, please try another username.","Notification");
            return false;
        }
        try {
            PreparedStatement pr = Conner.getConnect().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,pass);
            //int response = pr.executeUpdate();

            return pr.executeUpdate() != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;

    }


}
