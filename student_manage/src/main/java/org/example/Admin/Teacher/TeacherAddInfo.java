package org.example.Admin.Teacher;

import org.example.DBconn.DBconn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  The purpose of this class is to create a new form when the user clicks the add button
 */
public class TeacherAddInfo extends JFrame{

    private JLabel teacherID, teacherName, teacherSex, teacherBirthday, post, department, pwd;
    private JTextField IDField, nameField, sexField, birthdayField, postField, departmentField,pwdField;
    private JButton submitButton;

    private DBconn dBconn;
    // constructor:

    public TeacherAddInfo(DBconn dBconn_) {
        dBconn = dBconn_;
        setTitle("Add Teacher");
        setSize(400, 300);

        // This value is used to dispose of the current window when the user closes it.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // 指定的布局
        setLayout(new GridBagLayout());

        teacherID = new JLabel("teacherID:");
        teacherName = new JLabel("teacherName:");
        teacherSex = new JLabel("teacherSex:");
        teacherBirthday = new JLabel("Birthday:");
        post = new JLabel("post:");
        department = new JLabel("department:");
        pwd = new JLabel("password:");

        IDField = new JTextField(20);
        nameField = new JTextField(20);
        sexField = new JTextField(20);
        birthdayField = new JTextField(20);
        postField = new JTextField(20);
        departmentField = new JTextField(20);
        pwdField = new JTextField(20);

        submitButton = new JButton("Submit");

        // 为上面添加的布局: GridBagLayout 添加约束
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(5, 5, 5, 5);
        add(teacherID, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        add(IDField, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        add(teacherName, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(nameField, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        add(teacherSex, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        add(sexField, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.LINE_END;
        add(teacherBirthday, gc);

        gc.gridx = 1;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.LINE_START;
        add(birthdayField, gc);

        gc.gridx = 0;
        gc.gridy = 4;
        gc.anchor = GridBagConstraints.LINE_END;
        add(post, gc);

        gc.gridx = 1;
        gc.gridy = 4;
        gc.anchor = GridBagConstraints.LINE_START;
        add(postField, gc);

        gc.gridx = 0;
        gc.gridy = 5;
        gc.anchor = GridBagConstraints.LINE_END;
        add(department, gc);

        gc.gridx = 1;
        gc.gridy = 5;
        gc.anchor = GridBagConstraints.LINE_START;
        add(departmentField, gc);

        gc.gridx = 0;
        gc.gridy = 6;
        gc.anchor = GridBagConstraints.LINE_END;
        add(pwd, gc);

        gc.gridx = 1;
        gc.gridy = 6;
        gc.anchor = GridBagConstraints.LINE_START;
        add(pwdField, gc);


        gc.gridx = 1;
        gc.gridy = 7;
        gc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gc);

        // Submit button function implementation
        // private JTextField IDField, nameField, sexField, birthdayField, postField, departmentField,pwdField;
        submitButton.addActionListener(e -> {
            String sql = "CALL teacher_add(?,?,?,?,?,?,?)";
            try {
                CallableStatement statement = dBconn.conn.prepareCall(sql);
                statement.setString(1,IDField.getText());
                statement.setString(2,nameField.getText());
                statement.setString(3,sexField.getText());
                statement.setString(4,birthdayField.getText());
                statement.setString(5,postField.getText());
                statement.setString(6,departmentField.getText());
                statement.setString(7,pwdField.getText());

                if(!statement.execute()){
                    System.out.println("Add the new teacher successfully");
                    JOptionPane.showMessageDialog(null, "添加教师信息成功!");
                    CleanField();

                }else {
                    System.out.println("Add the new teacher failed");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


        setVisible(true);
    }

    // private JTextField IDField, nameField, sexField, birthdayField, postField, departmentField,pwdField;
    public void CleanField(){
        IDField.setText("");
        nameField.setText("");
        sexField.setText("");
        birthdayField.setText("");
        postField.setText("");
        departmentField.setText("");
        pwdField.setText("");
    }


    public static void main(String[] args) {
        DBconn dBconn1 = new DBconn();
        TeacherAddInfo addTeacherWindow = new TeacherAddInfo(dBconn1);
    }
}