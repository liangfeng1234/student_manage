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

public class CourseStuAdd extends JFrame{
    JFrame frame;
    JPanel panel;
    private JLabel courseID, semester, studentID, score;//标签
    private JTextField IDField, semesterField, studentIDField, scoreField;//文本框
    private JButton commitButton;

    private DBconn dbconn;

    public CourseStuAdd(){
        frame = new JFrame("添加学生成绩信息");
        dbconn = new DBconn();

        //设置各个标签的名称
        courseID = new JLabel("课程号");
        semester = new JLabel("课程开设学期");
        studentID = new JLabel("学生学号");
        score = new JLabel("学生成绩");

        //初始化各个TextField
        IDField = new JTextField(20);
        semesterField = new JTextField(20);
        studentIDField = new JTextField(20);
        scoreField = new JTextField(20);

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
        gridBox.addLayoutComponent(semester, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;//设置组件对齐方式
        gridBox.addLayoutComponent(semesterField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;//设置组件对齐方式
        gridBox.addLayoutComponent(studentID, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;//设置组件对齐方式
        gridBox.addLayoutComponent(studentIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;//设置组件对齐方式
        gridBox.addLayoutComponent(score, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;//设置组件对齐方式
        gridBox.addLayoutComponent(scoreField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gridBox.addLayoutComponent(commitButton, gbc);

        panel.add(courseID);
        panel.add(IDField);
        panel.add(semester);
        panel.add(semesterField);
        panel.add(studentID);
        panel.add(studentIDField);
        panel.add(score);
        panel.add(scoreField);
        panel.add(commitButton);

        frame.setContentPane(panel);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //点击提交按钮进行课程的添加
        commitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //4个 ?(占位符);来表示4个值的位置;
                String sql = "insert into tb_score values(?, ?, ?, ?)";
                if(IDField.getText().length() > 0 && semesterField.getText().length() > 0 && studentIDField.getText().length() > 0 && scoreField.getText().length() > 0)
                {
                    try{
                        PreparedStatement pStatement = dbconn.conn.prepareStatement(sql);
                        pStatement.setString(1, IDField.getText());
                        pStatement.setString(2, semesterField.getText());
                        pStatement.setString(3, studentIDField.getText());
                        pStatement.setString(4, scoreField.getText());
                        if(!pStatement.execute()){
                            System.out.println("add successfully!");
                            JOptionPane.showMessageDialog(null, "添加学生信息成功");
                            cleanField();//添加课程信息成功后清空Field
                        }
                        else{
                            System.out.println("add failed");
                        }
                    }catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "学生成绩添加失败！");
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
        semesterField.setText("");
        studentIDField.setText("");
        scoreField.setText("");
    }

    //main函数，自己测试使用
    public static void main(String[] args){
        CourseStuAdd courseStuAdd = new CourseStuAdd();
    }
}
