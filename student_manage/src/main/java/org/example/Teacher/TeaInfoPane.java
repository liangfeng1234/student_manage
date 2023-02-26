package org.example.Teacher;

import org.example.DBconn.DBconn;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.ResultSet;

//教师信息面板，展示的信息:工号、姓名、性别、出生年月、职称、学院
public class TeaInfoPane extends JPanel {
    JLabel title=new JLabel("教师个人信息");
    JLabel ID=new JLabel("工号：");
    JTextField tID=new JTextField();
    JLabel Name=new JLabel("姓名：");
    JTextField tName=new JTextField();
    JLabel Sex=new JLabel("性别：");
    JTextField tSex=new JTextField();
    //JLabel Birth=new JLabel("出生日期：");
    //JTextField tBirth=new JTextField();
    JLabel Title = new JLabel("职称");
    JTextField tTitle = new JTextField();
    JLabel School=new JLabel("学院：");
    JTextField tSchool=new JTextField();

    String sql, teacherID;
    ResultSet rs=null;

    public TeaInfoPane(JPanel panel, String teacherID){
        try {
            this.teacherID=teacherID;
            jbInit(panel);
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    private void jbInit(JPanel panel) throws Exception{
        //新建字体样式
        Font font_title=new Font("宋体", Font.BOLD, 30);
        Font font2=new Font("宋体", Font.PLAIN, 20);;
        //设置JPanel面板的大小
        this.setPreferredSize(new Dimension(537, 336));
        //设置JPanel面板的位置
        //this.setLocation((panel.getWidth()-537)/2,(panel.getHeight()-336)/2);
        //给Jpanel设置边框
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2, true);
        this.setBorder(border);
        //设置布局
        this.setLayout(null);
        //设置标题格式
        title.setFont(font_title);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        //设置位置和大小
        title.setBounds(128,5,280,40);
        ID.setBounds(135,75,65,25);ID.setFont(font2);
        Name.setBounds(135,125,65,25);Name.setFont(font2);
        Sex.setBounds(135,175,65,25);Sex.setFont(font2);
        //Birth.setBounds(135,225,100,25);Birth.setFont(font2);
        Title.setBounds(135,225,65,25);Title.setFont(font2);
        School.setBounds(135, 275, 65, 25);School.setFont(font2);

        tID.setBounds(185,68,215,40);tID.setFont(font2);
        tID.setHorizontalAlignment(SwingConstants.CENTER);//工号
        tName.setBounds(185,118,215,40);tName.setFont(font2);
        tName.setHorizontalAlignment(SwingConstants.CENTER);//姓名
        tSex.setBounds(185,168,215,40);tSex.setFont(font2);
        tSex.setHorizontalAlignment(SwingConstants.CENTER);//性别
        //tBirth.setBounds(225,218,175,40);tBirth.setFont(font2);
        //tBirth.setHorizontalAlignment(SwingConstants.CENTER);//出生年月
        tTitle.setBounds(185,218,215,40);tTitle.setFont(font2);
        tTitle.setHorizontalAlignment(SwingConstants.CENTER);//职称
        tSchool.setBounds(185,268,215,40);tSchool.setFont(font2);
        tSchool.setHorizontalAlignment(SwingConstants.CENTER);//学院

        //设置文本框不可编辑
        tID.setEditable(false);
        tName.setEditable(false);
        tSex.setEditable(false);
        //tBirth.setEditable(false);
        tTitle.setEditable(false);
        tSchool.setEditable(false);
        //新建数据库连接
        DBconn db = new DBconn();
        sql = "select * from tb_teacher where teacherID = '"+teacherID+"'";
        try{
            rs=db.Query(sql);   //从数据库中查询相应的数据
            tID.setText(teacherID);
            while (rs.next()) {
                tName.setText(rs.getString("teacherName"));
                tSex.setText(rs.getString("teacherSex"));
                //tBirth.setText(rs.getString("teacherBirthday"));
                tTitle.setText(rs.getString("post"));
                tSchool.setText(rs.getString("department"));
            }
            this.add(title);
            this.add(ID);    this.add(tID);
            this.add(Name);  this.add(tName);
            this.add(Sex);   this.add(tSex);
            //this.add(Birth); this.add(tBirth);
            this.add(Title); this.add(tTitle);
            this.add(School); this.add(tSchool);
            this.setVisible(true);

        }catch (Exception e2) {
            System.out.println(e2.toString());
        }
    }
}
