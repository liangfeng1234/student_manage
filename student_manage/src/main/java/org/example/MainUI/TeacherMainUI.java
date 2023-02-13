package org.example.MainUI;

import org.example.Admin.Course.CourseManage;
import org.example.Admin.Student.GradesManage;
import org.example.Admin.Student.StuCourseManage;
import org.example.Admin.Student.StuManage;
import org.example.Admin.Teacher.TeacherManage;
import org.example.Teacher.GradesQuery;
import org.example.Teacher.MyCourseManage;
import org.example.Teacher.StuInfoManage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherMainUI extends JFrame {
    Toolkit tk = Toolkit.getDefaultToolkit();  //用于设置窗口大小
    Dimension sSize = tk.getScreenSize();
    int sh = sSize.height;
    int sw = sSize.width;
    BorderLayout borderLayout1 = new BorderLayout();
    MenuBar mb = new MenuBar();  //菜单的初始化
    Menu stu_manage = new Menu("学生管理");
    MenuItem grades_query = new MenuItem("学生成绩查询" );
    MenuItem stu_info_manage = new MenuItem("学生信息管理");
    Menu teac_course_manage = new Menu("课程管理");
    MenuItem my_course = new MenuItem("我的课程");


    private String teacherID, psd, ident;

    public TeacherMainUI(String teacherID, String psd, String ident) {
        try {  //接收各项参数
            this.teacherID = teacherID;
            this.psd = psd;
            this.ident = ident;
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setTitle("学生成绩管理系统-教师端--------" + teacherID + "您好！");  //设置主窗体的标题
        setSize(sw * 3 / 5, sh * 3 / 5);  //设置主窗体的尺寸大小
        setLocation(sw / 4, sh / 4);  //设置主窗体的位置
        setResizable(false);  //设置主窗体不可改变大小
        Container c = getContentPane();
        setMenuBar(mb);  //添加菜单
        grades_query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradesQuery gradesQuery = new GradesQuery(teacherID);

            }
        });
        stu_info_manage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StuInfoManage stuInfoManage = new StuInfoManage(teacherID);

            }
        });

        my_course.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyCourseManage myCourseManage = new MyCourseManage(teacherID);
            }
        });


        mb.add(stu_manage);
        mb.add(teac_course_manage);
        stu_manage.add(grades_query);
        stu_manage.add(stu_info_manage);
        teac_course_manage.add(my_course);
    }
}
