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

//进行课程的修改操作，借用CourseAdd的UI界面
//设定修改的时候课程号、学生学号是不允许改变的，可以改变学生的成绩和课程开设的学期
public class CourseStuModify extends JFrame{
    JFrame frame;
    JPanel panel;
    private JLabel courseID, semester, studentID, score;//标签
    private JTextField courseIDField, semesterField, studentIDField, scoreField;//文本框
    private JButton commitButton;

    private DBconn dbconn;

    public CourseStuModify(String courseid, String studentid) {
        frame = new JFrame("学生成绩修改");
        dbconn = new DBconn();

        //设置各个标签的名称
        courseID = new JLabel("课程号");
        semester = new JLabel("课程开设学期");
        studentID = new JLabel("学生学号");
        score = new JLabel("学生成绩");

        //初始化各个TextField，给课程号的Field直接进行赋值
        courseIDField = new JTextField(courseid);
        semesterField = new JTextField(20);
        studentIDField = new JTextField(studentid);
        scoreField = new JTextField(20);

        commitButton = new JButton("提交修改");

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
        gridBox.addLayoutComponent(courseIDField, gbc);

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
        panel.add(courseIDField);
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
                //String sql = "insert into tb_course values(?, ?, ?)";
                String sql = "update tb_score set semester = ?, score = ? where courseID = ? and studentID = ?";
                if(semesterField.getText().length() > 0 && scoreField.getText().length() > 0 && courseIDField.getText().length() > 0 && studentIDField.getText().length() > 0)
                {
                    try{
                        PreparedStatement pStatement = dbconn.conn.prepareStatement(sql);
                        pStatement.setString(1, semesterField.getText());
                        pStatement.setString(2, scoreField.getText());
                        pStatement.setString(3, courseIDField.getText());
                        pStatement.setString(4, studentIDField.getText());
                        if(!pStatement.execute()){
                            System.out.println("add successfully!");
                            JOptionPane.showMessageDialog(null, "修改学生成绩成功");
                            cleanField();//添加课程信息成功后清空Field
                        }
                        else{
                            System.out.println("add failed");
                        }
                    }catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "信息填写有误！");
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "请将信息填写完整！");
                }
            }
        });
    }

    public void cleanField(){
        //IDField.setText("");
        //semesterField.setText("");
        scoreField.setText("");
    }

}
