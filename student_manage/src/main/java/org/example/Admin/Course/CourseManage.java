package org.example.Admin.Course;

import org.example.DBconn.DBconn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.Vector;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class CourseManage extends JFrame{
    JFrame jFrame;
    //声明组件
    JPanel jpanel_1;
    JLabel jlabel_1;
    JButton jbutton_1, jbutton_2;
    DefaultTableModel model1;
    JTable jtable_1;//表格
    JScrollPane jscrollpane_1;//滚动条

    //字符串储存各个课程的信息
    String courseID;//课程号
    String courseName;//课程名称
    String courseSemester;//课程所在的学期
    String courseTeacher;//课程的代课老师

    //字符串存储SQL语句
    String sql;

    //MenuBar和JMenuItem的设置
    JMenuBar courseBar = new JMenuBar();
    //JMenu courseMenu = new JMenu("课程管理");
    //JMenu courseInfo = new JMenu("课程信息");
    JMenuItem courseModify = new JMenuItem("课程信息管理");
    JMenuItem courseStuUI = new JMenuItem("学生成绩管理");

    public CourseManage() {
        jFrame = new JFrame("课程详细信息");
        jFrame.setSize(800, 600);
        setJMenuBar(courseBar);
        //courseInfo.setBackground(Color.RED);
        //courseBar.add(courseInfo);
        //courseModify.setBackground(Color.CYAN);
        courseBar.add(courseModify);
        //courseStuUI.setBackground(Color.GREEN);
        courseBar.add(courseStuUI);
        jFrame.setJMenuBar(courseBar);
        DBconn db = new DBconn();//声明并创建数据库
        ResultSet res = null;//储存SQL语句查询返回的数据
        Vector tempVector;//存储一行的数据
        String[] column1 = {"课程号", "课程名称", "课程所在的学期", "课程的代课老师"};
        jpanel_1 = new JPanel();//面板
        jlabel_1 = new JLabel();//标签
        model1 = new DefaultTableModel(column1, 1);
        jtable_1 = new JTable(model1);
        jscrollpane_1 = new JScrollPane(jtable_1);
        jFrame.add(jscrollpane_1);
        jFrame.setLocationRelativeTo(null);//设置窗体的位置居中
        //设置窗体标题
        jFrame.setTitle("课程信息");
        jFrame.setVisible(true);
        jFrame.setResizable(true);
        //jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);关闭窗口同时关闭程序
        sql = "select * from tb_course";
        res = db.Query(sql);//从数据库中查询数据
        try{
            //删除model_1原有的数据
            int j = model1.getRowCount();
            if (j > 0) {
                for (int i = 0; i < j; i++) {
                    model1.removeRow(0);
                }
            }
            while(res.next()){
                tempVector = new Vector(1, 1);
                tempVector.add(res.getString("courseID"));
                tempVector.add(res.getString("courseName"));
                tempVector.add(res.getString("semester"));
                tempVector.add(res.getString("teacherID"));
                model1.addRow(tempVector);
            }
            jtable_1.setEnabled(false);//设置表格的内容不可以更改，默认是可以更改数据的
        } catch(Exception e){
            System.out.println(e.toString());
        }

        //courseInfo.addMenuListener(new MyMenuListener());
        //跳转到CourseModifyUI课程信息管理模块
        courseModify.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ CourseModifyUI coursemodifyUI = new CourseModifyUI();}
        });
        //跳转到CourseStu课程信息管理模块
        courseStuUI.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ CourseStuUI courseStuUI = new CourseStuUI();}
        });
    }

}

//class MyMenuListener implements MenuListener {
//    public void menuSelected(MenuEvent e) {
//        // 当菜单被选中时执行的代码
//        System.out.println("Selected");
//        CourseInfo courseInfo = new CourseInfo();
//    }
//
//    public void menuDeselected(MenuEvent e) {
//        // 当菜单被取消选中时执行的代码
//        System.out.println("Deselected");
//    }
//
//    public void menuCanceled(MenuEvent e) {
//        // 当菜单被取消时执行的代码
//        System.out.println("Cancel");
//    }
//}
