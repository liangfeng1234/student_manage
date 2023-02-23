package org.example.Admin.Course;

import org.example.DBconn.DBconn;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//添加课程信息，涉及课程的添加

public class CourseAdd extends JFrame{
    JFrame frame;
    JPanel panel;
    private JLabel courseID, courseName, semester, teacherID;//标签
    private JTextField IDField, nameField, semesterField, tIDField;//文本框
    private JButton commitButton;

    private DBconn dbconn;

    public CourseAdd(){
        frame = new JFrame("添加课程信息");
        dbconn = new DBconn();

        //设置各个标签的名称
        courseID = new JLabel("课程号");
        courseName = new JLabel("课程名称");
        semester = new JLabel("课程开设学期");
        teacherID = new JLabel("课程教师号");

        //初始化各个TextField
        IDField = new JTextField(20);
        nameField = new JTextField(20);
        semesterField = new JTextField(20);
        tIDField = new JTextField(20);

        commitButton = new JButton("提交添加");

        //使用布局网格袋进行布局
        GridBagLayout gridBox = new GridBagLayout();//布局控制器
        GridBagConstraints gbc = new GridBagConstraints();//约束

        panel = new JPanel(gridBox);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;//设置组件对齐方式
        gbc.insets = new Insets(5, 5, 5, 5);//设置组件的外部填充，设置组件之间的间隔，上下左右
        gridBox.addLayoutComponent(courseID, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;//设置组件的对齐方式
        gridBox.addLayoutComponent(IDField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;//设置组件对齐方式
        gridBox.addLayoutComponent(courseName, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;//设置组件对齐方式
        gridBox.addLayoutComponent(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;//设置组件对齐方式
        gridBox.addLayoutComponent(semester, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;//设置组件对齐方式
        gridBox.addLayoutComponent(semesterField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;//设置组件对齐方式
        gridBox.addLayoutComponent(teacherID, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;//设置组件对齐方式
        gridBox.addLayoutComponent(tIDField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gridBox.addLayoutComponent(commitButton, gbc);

        panel.add(courseID);
        panel.add(IDField);
        panel.add(courseName);
        panel.add(nameField);
        panel.add(semester);
        panel.add(semesterField);
        panel.add(teacherID);
        panel.add(tIDField);
        panel.add(commitButton);

        frame.setContentPane(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //点击提交按钮进行课程的添加
        commitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //4个 ?(占位符);来表示4个值的位置;
                String sql = "insert into tb_course values(?, ?, ?, ?)";
                if(IDField.getText().length() > 0 && nameField.getText().length() > 0 && semesterField.getText().length() > 0 && tIDField.getText().length() > 0)
                {
                    try{
                        PreparedStatement pStatement = dbconn.conn.prepareStatement(sql);
                        pStatement.setString(1, IDField.getText());
                        pStatement.setString(2, nameField.getText());
                        pStatement.setString(3, semesterField.getText());
                        pStatement.setString(4, tIDField.getText());
                        if(!pStatement.execute()){
                            System.out.println("add successfully!");
                            JOptionPane.showMessageDialog(null, "添加课程信息成功");
                            cleanField();//添加课程信息成功后清空Field
                        }
                        else{
                            System.out.println("add failed");
                        }
                    }catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "添加失败！");
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "请将信息填写完整！");
                }
            }
        });
    }

    public void cleanField(){
        IDField.setText("");
        nameField.setText("");
        semesterField.setText("");
        tIDField.setText("");
    }

    //main函数，自己测试使用
    public static void main(String[] args){
        CourseAdd courseAdd = new CourseAdd();
    }
}
