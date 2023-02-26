package org.example.Student;

import org.example.DBconn.DBconn;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class CreateCoursePane extends JPanel {
    JLabel title = new JLabel("开设课程");
    JPanel pane1 = new JPanel();
    JLabel courseID = new JLabel("课程ID");
    JTextField TcourseID = new JTextField(10);
    JLabel courseNAME = new JLabel("课程名称");
    JTextField TcourseNAME = new JTextField(20);
    JLabel teacher = new JLabel("任课老师");
    JTextField Tteacher = new JTextField(10);
    JLabel semester = new JLabel("学期");
    JTextField Tsemester = new JTextField(15);
    JButton create = new JButton("开设");
    String[] Inputs=new String[]{"1","2","3","4"};


    JLabel result = new JLabel("查询结果：\n");
    JPanel pane2 = new JPanel();
    private JTable table;
    private DefaultTableModel model= new DefaultTableModel() {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 0) {
                return Object.class;
            }
            return super.getColumnClass(columnIndex);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }
    };
    String sql,sql2; //sql语句
    ResultSet rs = null;
    //连接数据库
    DBconn db = new DBconn();
    JScrollPane jScrollPane1;
    int selectedRow = -1;
    Object[] rowData = null;
    JButton select=new JButton("选课");
    String teacherID, cID;


    public CreateCoursePane(JPanel panel, String ID) {
        try {
            this.teacherID =ID;
            jbInit(panel);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit(JPanel panel) throws Exception {
        //新建字体样式
        Font font_title = new Font("宋体", Font.BOLD, 30);
        Font font2 = new Font("宋体", Font.PLAIN, 16);
        //设置标题样式
        title.setFont(font_title);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setHorizontalTextPosition(SwingConstants.CENTER);

        //查询面板pane1的界面设计--------------------

        //设置组件字体大小
        courseID.setFont(font2);
        TcourseID.setFont(font2);
        courseNAME.setFont(font2);
        TcourseNAME.setFont(font2);
        teacher.setFont(font2);
        Tteacher.setFont(font2);
        semester.setFont(font2);
        Tsemester.setFont(font2);
        create.setFont(font2);
        //将teacherID默认
        Tteacher.setText(teacherID);
        Tteacher.setEditable(false);//默认不可修改
        // 设置pane1的布局
        GridBagLayout layout1 = new GridBagLayout();
        pane1.setLayout(layout1);
        //增加边框
        Border border1 = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        pane1.setBorder(border1);
        // 将组件添加到面板Pane1上
        pane1.add(courseID);
        pane1.add(TcourseID);
        pane1.add(courseNAME);
        pane1.add(TcourseNAME);
        pane1.add(teacher);
        pane1.add(Tteacher);
        pane1.add(semester);
        pane1.add(Tsemester);
        pane1.add(create);
        //控制添加进的组件的显示位置
        GridBagConstraints s1 = new GridBagConstraints();
        s1.fill = GridBagConstraints.NONE;
        s1.anchor=GridBagConstraints.WEST;
        s1.gridwidth = 2;//该方法是设置组件水平所占用的格子数
        s1.gridheight = 1;
        s1.gridx=0;
        s1.gridy=0;
        s1.weightx = 1;//该方法设置组件水平的拉伸幅度
        s1.weighty = 1;//该方法设置组件垂直的拉伸幅度
        layout1.setConstraints(courseID, s1);//设置组件
        s1.gridx=2;
        s1.gridy=0;
        layout1.setConstraints(courseNAME, s1);
        s1.gridx=4;
        s1.gridy=0;
        layout1.setConstraints(teacher, s1);
        s1.gridx = 6;
        s1.gridy = 0;
        layout1.setConstraints(semester, s1);
        s1.gridx=0;
        s1.gridy = 1;
        layout1.setConstraints(TcourseID, s1);
        s1.gridx=2;
        s1.gridy=1;
        layout1.setConstraints(TcourseNAME, s1);
        s1.gridx=4;
        s1.gridy=1;
        layout1.setConstraints(Tteacher, s1);
        s1.gridx = 6;
        s1.gridy = 1;
        layout1.setConstraints(Tsemester, s1);
        s1.gridx =8;
        s1.gridy = 0;
        s1.gridwidth = 1;
        s1.gridheight = 2;
        s1.insets = new Insets(0, 100, 0, 0);
        layout1.setConstraints(create, s1);

        // 添加 ActionListener 到"开设课程"按钮
        //点击提交按钮进行课程的添加
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //4个 ?(占位符);来表示4个值的位置;
                String sql = "insert into tb_course values(?, ?, ?, ?)";
                if(TcourseID.getText().length() > 0 && TcourseNAME.getText().length() > 0 && Tsemester.getText().length() > 0 && Tteacher.getText().length() >0)
                {
                    try{
                        PreparedStatement pStatement = db.conn.prepareStatement(sql);
                        pStatement.setString(1, TcourseID.getText());
                        pStatement.setString(2, TcourseNAME.getText());
                        pStatement.setString(3, Tsemester.getText());
                        pStatement.setString(4, Tteacher.getText());
                        if(!pStatement.execute()){
                            System.out.println("add successfully!");
                            JOptionPane.showMessageDialog(null, "添加课程信息成功");
                            cleanField();//添加课程信息成功后清空Field
                        }
                        else{
                            System.out.println("add failed");
                        }
                    }catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "添加失败！");
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "请将信息填写完整！");
                }
            }
        });

        pane1.setVisible(true);

       //设置主面板
        this.setPreferredSize(new Dimension(800, 400));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        c.gridx=0;
        c.gridy=0;
        c.gridwidth=10;
        c.gridheight=1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.CENTER;
        this.setVisible(true);
        this.add(title,c);
        c.gridx=0;
        c.gridy=1;
        c.gridheight=2;
        c.fill = GridBagConstraints.NONE;
        this.add(pane1,c);
        Border border3 = BorderFactory.createLineBorder(Color.BLACK, 2, true);
        this.setBorder(border3);
    }

    void cleanField(){
        TcourseID.setText("");
        TcourseNAME.setText("");
        Tsemester.setText("");
    }

}

