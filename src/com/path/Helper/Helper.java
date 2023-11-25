package com.path.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static int dimention(String axis, Dimension size){

        return switch (axis) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.getSize().width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.getSize().height) / 2;
            default -> 0;
        };
    }
    public static void theme(){
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }
    public static boolean isFieldEmpty(JEditorPane pane){
        return pane.getText().trim().isEmpty();
    }

    public static void message(String msg, String title){
        JOptionPane.showInternalMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirmMessage(String message) {
        int result = JOptionPane.showConfirmDialog(null,message,"Notification",JOptionPane.YES_NO_OPTION);
        return result == 0;

    }
}
