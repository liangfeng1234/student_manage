package org.example.MainUI;

import org.example.Admin.Course.CourseManage;
import org.example.Admin.Student.GradesManage;
import org.example.Admin.Student.StuCourseManage;
import org.example.Admin.Student.StuManage;
import org.example.Admin.Teacher.TeacherManage;
import org.example.Login.Login;
import org.example.Student.CourseSelectManage;
import org.example.Student.GradesQuery;
import org.example.Welcome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentMainUI extends JFrame {
    Login login_frame;
    Toolkit tk = Toolkit.getDefaultToolkit();  //用于设置窗口大小
    Dimension sSize = tk.getScreenSize();
    int sh = sSize.height;
    int sw = sSize.width;
    BorderLayout borderLayout1 = new BorderLayout();
    Button grades_query = new Button("成绩查询");
    Button course_select_manage = new Button("选课管理");
    private String studentID, psd, ident;

    public StudentMainUI(String studentID, String psd, String ident, Login login_) {
        try {  //接收各项参数
            login_frame = login_;
            this.studentID = studentID;
            this.psd = psd;
            this.ident = ident;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("学生成绩管理系统-学生端-----" + studentID + "你好！");  //设置主窗体的标题
        setSize(sw * 3 / 5, sh * 3 / 5);  //设置主窗体的尺寸大小
        setLocation(sw / 4, sh / 4);  //设置主窗体的位置
        setResizable(false);  //设置主窗体不可改变大小
        Container c = getContentPane();

        // @10-Kirito
        // Key bindings:
        // 1. ESC : Exit the program to the login interface
        this.getRootPane().registerKeyboardAction(
                (ActionEvent e) -> {
                    login_frame.setVisible(true);

                    login_frame.upsd.setText("");
                    login_frame.uname.setText("");
                    dispose();
                },
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );



        grades_query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradesQuery gradesQuery = new GradesQuery(studentID);
            }
        });
        course_select_manage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CourseSelectManage courseSelectManage = new CourseSelectManage(studentID);
            }
        });
        grades_query.setPreferredSize(new Dimension(50, 50));
        c.add(grades_query, BorderLayout.NORTH);

        /*course_select_manage.setPreferredSize(new Dimension(50, 50));
        c.add(course_select_manage, BorderLayout.NORTH);*/

    }
}
