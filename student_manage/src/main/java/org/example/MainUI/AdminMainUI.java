package org.example.MainUI;

import org.example.Admin.Course.CourseManage;
import org.example.Admin.Student.GradesManage;
import org.example.Admin.Student.StuCourseSelectionManage;
import org.example.Admin.Student.StuManage;

import org.example.Admin.Teacher.TeacherInformationUI;
import org.example.Login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMainUI extends JFrame {
    Login login_frame;
    Toolkit tk = Toolkit.getDefaultToolkit();  //用于设置窗口大小
    Dimension sSize = tk.getScreenSize();
    int sh = sSize.height;
    int sw = sSize.width;
    BorderLayout borderLayout1 = new BorderLayout();
    JMenuBar mb = new JMenuBar();  //菜单的初始化
    JMenu stu_sys = new JMenu("学生管理");
//    JMenuItem grades_manage = new JMenuItem("学生成绩管理" );
    JMenuItem stu_manage = new JMenuItem("学生信息管理");
    JMenuItem stu_course_manage = new JMenuItem("学生选课管理");
    JMenu teacher_sys = new JMenu("教师管理");
    JMenuItem teacher_manage = new JMenuItem("教师信息管理");

    JMenu course_system = new JMenu("课程管理");
    JMenuItem course_manage = new JMenuItem("课程信息管理");
    JPanel pane=new JPanel();//当作内容载体面板
    private String admin, psd, ident;

    public AdminMainUI(String ID, String psd, String ident,Login login_) {
        try {  //接收各项参数
            login_frame = login_;
            // This value is used to dispose of the current window when the user closes it.
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            admin = ID;
            this.psd = psd;
            this.ident = ident;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
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

        setTitle("学生成绩管理系统");  //设置主窗体的标题
        setSize(sw * 3 / 5, sh * 3 / 5);  //设置主窗体的尺寸大小
        setLocation(sw / 4, sh / 4);  //设置主窗体的位置
        setResizable(false);  //设置主窗体不可改变大小
        Container c = getContentPane();
        c.setLayout(null);  //设置空布局
        pane.setBounds((this.getWidth()-1000)/2,(this.getHeight()-500)/2-20,1000,500);
        pane.setLayout(new GridBagLayout());
        setJMenuBar(mb);  //添加菜单

        //学生信息管理事件处理
        stu_manage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StuManage Pane=new StuManage(pane);
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
        stu_course_manage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StuCourseSelectionManage Pane=new StuCourseSelectionManage();
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

//        grades_manage.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                GradesManage gradesManage = new GradesManage();
//            }
//        });

        // Menu bar click event binding, this function will be called after clicking teacher management
        teacher_manage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TeacherManage TeacherManage = new TeacherManage();
                TeacherInformationUI teacherInformationUI = new TeacherInformationUI();
            }
        });
        course_manage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CourseManage CourseManage = new CourseManage();
            }
        });

        mb.add(stu_sys);
        mb.add(teacher_sys);
        mb.add(course_system);
        stu_sys.add(stu_manage);
//        stu_sys.add(stu_course_manage);
        stu_sys.add(stu_course_manage);
        teacher_sys.add(teacher_manage);
        course_system.add(course_manage);

        add(pane);

        setLocationRelativeTo(null);
    }
}
