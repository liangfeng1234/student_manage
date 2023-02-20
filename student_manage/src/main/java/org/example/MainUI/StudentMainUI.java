package org.example.MainUI;

import org.example.Student.*;
import org.example.Login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLOutput;

public class StudentMainUI extends JFrame {
    Login login_frame;
    Toolkit tk = Toolkit.getDefaultToolkit();  //用于设置窗口大小
    Dimension sSize = tk.getScreenSize();
    int sh = sSize.height;
    int sw = sSize.width;

    JMenuBar menuBar=new JMenuBar();
    JMenu function=new JMenu("功能");
    JMenuItem info=new JMenuItem("个人信息");
    JMenuItem grades_query = new JMenuItem("成绩查询");
    JMenu course_select_manage = new JMenu("选课管理");
    JMenuItem course_query=new JMenuItem("课程查询");
    JMenuItem selected_course=new JMenuItem("已选课程");
    JPanel pane=new JPanel();
    //MyListener listener=new MyListener();

    private String studentID, psd, ident;

    public StudentMainUI(String ID, String psd, String ident, Login login_) {
        try {  //接收各项参数
            login_frame = login_;
            this.studentID = ID;
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
        c.setLayout(null);  //设置空布局
//        System.out.println(this.getWidth());
//        System.out.println(this.getHeight());
        pane.setBounds((this.getWidth()-1000)/2,(this.getHeight()-500)/2-20,1000,500);
        pane.setLayout(new GridBagLayout());
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

        //事件处理
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StuInfoPane Pane=new StuInfoPane(pane,studentID);
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
        grades_query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradesQueryPane Pane=new GradesQueryPane(pane,studentID);
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
        course_query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CourseQueryPane Pane=new CourseQueryPane(pane,studentID);
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
        selected_course.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectedCoursePane Pane=new SelectedCoursePane(pane,studentID);
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
        course_select_manage.add(course_query);
        course_select_manage.addSeparator();
        course_select_manage.add(selected_course);

        function.add(info);
        function.add(grades_query);
        function.add(course_select_manage);

        menuBar.add(function);
        this.setJMenuBar(menuBar);
        //添加主面板
        add(pane);

    }
}
