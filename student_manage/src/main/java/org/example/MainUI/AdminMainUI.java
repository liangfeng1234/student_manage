package org.example.MainUI;

import org.example.Admin.Course.CourseManage;
import org.example.Admin.Student.GradesManage;
import org.example.Admin.Student.StuCourseManage;
import org.example.Admin.Student.StuManage;
import org.example.Admin.Teacher.TeacherManage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMainUI extends JFrame {
    Toolkit tk = Toolkit.getDefaultToolkit();  //用于设置窗口大小
    Dimension sSize = tk.getScreenSize();
    int sh = sSize.height;
    int sw = sSize.width;
    BorderLayout borderLayout1 = new BorderLayout();
    MenuBar mb = new MenuBar();  //菜单的初始化
    Menu stu_sys = new Menu("学生管理");
    MenuItem grades_manage = new MenuItem("学生成绩管理" );
    MenuItem stu_manage = new MenuItem("学生信息管理");
    MenuItem stu_course_manage = new MenuItem("学生选课管理");
    Menu teacher_sys = new Menu("教师管理");
    MenuItem teacher_manage = new MenuItem("教师信息管理");

    Menu course_system = new Menu("课程管理");
    MenuItem course_manage = new MenuItem("课程信息管理");
    private String admin, psd, ident;

    public AdminMainUI(String ID, String psd, String ident) {
        try {  //接收各项参数
            admin = ID;
            this.psd = psd;
            this.ident = ident;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setTitle("学生成绩管理系统");  //设置主窗体的标题
        setSize(sw * 3 / 5, sh * 3 / 5);  //设置主窗体的尺寸大小
        setLocation(sw / 4, sh / 4);  //设置主窗体的位置
        setResizable(false);  //设置主窗体不可改变大小
        Container c = getContentPane();
        setMenuBar(mb);  //添加菜单
        stu_manage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StuManage stuManage = new StuManage();
            }
        });
        stu_course_manage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StuCourseManage stuCourseManage = new StuCourseManage();
            }
        });

        grades_manage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradesManage gradesManage = new GradesManage();
            }
        });
        teacher_manage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TeacherManage TeacherManage = new TeacherManage();
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
        stu_sys.add(stu_course_manage);
        stu_sys.add(grades_manage);
        teacher_sys.add(teacher_manage);
        course_system.add(course_manage);
    }
}
