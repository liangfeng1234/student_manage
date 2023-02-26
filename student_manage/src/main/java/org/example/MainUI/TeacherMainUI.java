package org.example.MainUI;

import org.example.Student.CreateCoursePane;
import org.example.Student.ManageGradePane;
import org.example.Teacher.*;
import org.example.Login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class TeacherMainUI extends JFrame {
    Login login_frame;
    Toolkit tk = Toolkit.getDefaultToolkit();  //用于设置窗口大小
    Dimension sSize = tk.getScreenSize();
    int sh = sSize.height;
    int sw = sSize.width;

    JMenuBar menuBar=new JMenuBar();
    JMenu function=new JMenu("功能");
    JMenuItem tea_info=new JMenuItem("个人信息");
    JMenuItem stu_info_query = new JMenuItem("学生信息查询");
    JMenu my_course_manage = new JMenu("课程信息管理");
    JMenuItem create_my_course = new JMenuItem("开设课程");
    JMenuItem manage_my_course = new JMenuItem("已开设课程管理");
    JMenuItem manage_my_student =new JMenuItem("学生成绩管理");

    JPanel pane=new JPanel();

    private String teacherID, psd, ident;

    public TeacherMainUI(String ID, String psd, String ident, Login login_) {
        try {  //接收各项参数
            login_frame = login_;
            this.teacherID = ID;
            this.psd = psd;
            this.ident = ident;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("学生成绩管理系统-教师端-----" + teacherID + "您好！");  //设置主窗体的标题
        setSize(sw * 3 / 5, sh * 3 / 5);  //设置主窗体的尺寸大小
        setLocation(sw / 4, sh / 4);  //设置主窗体的位置
        setResizable(false);  //设置主窗体不可改变大小
        Container c = getContentPane();
        c.setLayout(null);  //设置空布局
        pane.setBounds((this.getWidth()-1000)/2,(this.getHeight()-500)/2-20,1000,500);
        pane.setLayout(new GridBagLayout());
        //pane.setBackground(Color.BLUE);
        // 1. ESC : Exit the program to the login interface，按ESC键退出到登录界面
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

        //事件处理，教师信息展示
        tea_info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TeaInfoPane Pane=new TeaInfoPane(pane, teacherID);
                pane.removeAll();
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.weightx = 1;
                c.weighty = 1;
                c.fill = GridBagConstraints.CENTER;
                pane.add(Pane, c);
                pane.revalidate();
                pane.repaint();
            }
        });
        stu_info_query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StuInfoQueryPane Pane=new StuInfoQueryPane(pane,teacherID);
                pane.removeAll();
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.weightx = 1;
                c.weighty = 1;
                c.fill = GridBagConstraints.CENTER;
                pane.add(Pane, c);
                pane.revalidate();
                pane.repaint();
            }
        });
        //已开设课程信息事件
        manage_my_course.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageCoursePane Pane=new ManageCoursePane(pane, teacherID);
                pane.removeAll();
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.weightx = 1;
                c.weighty = 1;
                c.fill = GridBagConstraints.CENTER;
                pane.add(Pane, c);
                pane.revalidate();
                pane.repaint();
            }
        });

        //管理学生课程成绩事件
        manage_my_student.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManageGradePane Pane=new ManageGradePane(pane, teacherID);
                pane.removeAll();
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.weightx = 1;
                c.weighty = 1;
                c.fill = GridBagConstraints.CENTER;
                pane.add(Pane, c);
                pane.revalidate();
                pane.repaint();
            }
        });

        //开设课程，只能开设自己的课程，所以teacherID默认当前界面的teacherID
        create_my_course.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateCoursePane Pane = new CreateCoursePane(pane, teacherID);
                pane.removeAll();
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.weightx = 1;
                c.weighty = 1;
                c.fill = GridBagConstraints.CENTER;
                pane.add(Pane, c);
                pane.revalidate();
                pane.repaint();
            }
        });

        //设置菜单布局
        my_course_manage.add(create_my_course);
        my_course_manage.add(manage_my_course);
        my_course_manage.add(manage_my_student);
        function.add(tea_info);
        function.add(stu_info_query);
        function.add(my_course_manage);
        //function.add(course_select_manage);

        menuBar.add(function);
        this.setJMenuBar(menuBar);
        //添加主面板
        add(pane);

    }
}





//package org.example.MainUI;
//
//import org.example.Teacher.GradesQuery;
//import org.example.Teacher.MyCourseManage;
//import org.example.Teacher.StuInfoManage;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class TeacherMainUI extends JFrame {
//    Toolkit tk = Toolkit.getDefaultToolkit();  //用于设置窗口大小
//    Dimension sSize = tk.getScreenSize();
//    int sh = sSize.height;
//    int sw = sSize.width;
//    BorderLayout borderLayout1 = new BorderLayout();
//    MenuBar mb = new MenuBar();  //菜单的初始化
//    Menu stu_manage = new Menu("学生管理");
//    MenuItem grades_query = new MenuItem("学生成绩查询" );
//    MenuItem stu_info_manage = new MenuItem("学生信息管理");
//    Menu teac_course_manage = new Menu("课程管理");
//    MenuItem my_course = new MenuItem("我的课程");
//
//
//    private String teacherID, psd, ident;
//
//    public TeacherMainUI(String teacherID, String psd, String ident) {
//        try {  //接收各项参数
//            this.teacherID = teacherID;
//            this.psd = psd;
//            this.ident = ident;
//            jbInit();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }
//
//    private void jbInit() throws Exception {
//        setTitle("学生成绩管理系统-教师端--------" + teacherID + "您好！");  //设置主窗体的标题
//        setSize(sw * 3 / 5, sh * 3 / 5);  //设置主窗体的尺寸大小
//        setLocation(sw / 4, sh / 4);  //设置主窗体的位置
//        setResizable(false);  //设置主窗体不可改变大小
//        Container c = getContentPane();
//        setMenuBar(mb);  //添加菜单
//        grades_query.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                GradesQuery gradesQuery = new GradesQuery(teacherID);
//
//            }
//        });
//        stu_info_manage.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                StuInfoManage stuInfoManage = new StuInfoManage(teacherID);
//
//            }
//        });
//
//        my_course.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                MyCourseManage myCourseManage = new MyCourseManage(teacherID);
//            }
//        });
//
//
//        mb.add(stu_manage);
//        mb.add(teac_course_manage);
//        stu_manage.add(grades_query);
//        stu_manage.add(stu_info_manage);
//        teac_course_manage.add(my_course);
//    }
//}
