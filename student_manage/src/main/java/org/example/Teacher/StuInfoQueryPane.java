package org.example.Teacher;

import org.example.DBconn.DBconn;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

//学生信息查询，可用来查询指定学生的姓名、学号、性别、出生日期、专业及班级
public class StuInfoQueryPane extends JPanel {
    JLabel title = new JLabel("学生信息查询");
    JPanel pane1 = new JPanel();
    JLabel studentID = new JLabel("学生学号");
    JTextField TstudentID = new JTextField(10);
    JLabel studentNAME = new JLabel("学生姓名");
    JTextField TstudentNAME = new JTextField(10);
    JButton query = new JButton("查询");
    String[] Inputs=new String[]{"1","2"};


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
    String sql; //sql语句
    ResultSet rs = null;
    //连接数据库
    DBconn db = new DBconn();
    JScrollPane jScrollPane1;
    int selectedRow = -1;
    Object[] rowData = null;
    String teacherID, teacherName;


    public StuInfoQueryPane(JPanel panel, String ID) {
        try {
            this.teacherID=ID;
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
        studentID.setFont(font2);
        TstudentID.setFont(font2);
        studentNAME.setFont(font2);
        TstudentNAME.setFont(font2);
        query.setFont(font2);
        // 设置pane1的布局
        GridBagLayout layout1 = new GridBagLayout();
        pane1.setLayout(layout1);
        //增加边框
        Border border1 = BorderFactory.createLineBorder(Color.BLACK, 1, true);
        pane1.setBorder(border1);
        // 将组件添加到面板Pane1上
        pane1.add(studentID);
        pane1.add(TstudentID);
        pane1.add(studentNAME);
        pane1.add(TstudentNAME);
        pane1.add(query);
        //控制添加进的组件的显示位置
        GridBagConstraints s1 = new GridBagConstraints();
        s1.fill = GridBagConstraints.NONE;
        s1.anchor=GridBagConstraints.WEST;
        s1.gridwidth = 2;//该方法是设置组件水平所占用的格子数
        s1.gridheight = 1;
        s1.gridx=1;
        s1.gridy=1;
        s1.weightx = 1;//该方法设置组件水平的拉伸幅度
        s1.weighty = 1;//该方法设置组件垂直的拉伸幅度
        layout1.setConstraints(studentID, s1);//设置组件
        s1.gridx=1;
        s1.gridy=2;
        layout1.setConstraints(studentNAME, s1);
        s1.gridx=4;
        s1.gridy=1;
        layout1.setConstraints(TstudentID, s1);
        s1.gridx=4;
        s1.gridy=2;
        layout1.setConstraints(TstudentNAME, s1);
        s1.gridx=4;
        s1.gridy=1;
        s1.gridwidth = 1;
        s1.gridheight = 2;
        s1.insets = new Insets(0, 100, 0, 0);
        layout1.setConstraints(query, s1);

        // 添加 ActionListener 到"查询"按钮
        query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inputs = new String[]{TstudentID.getText(), TstudentNAME.getText()};
                //设置SQL语句
                sql = SetSql(Inputs);
                try {
                    model=CreateModel();
                    table = new JTable(model);
                    //设置表头样式
                    JTableHeader header = table.getTableHeader();
                    header.setFont(font2);
                    DefaultTableCellRenderer hr = (DefaultTableCellRenderer) header.getDefaultRenderer();
                    hr.setHorizontalAlignment(JLabel.CENTER);
                    // 设置行高为30像素
                    table.setRowHeight(20);
                    jScrollPane1= new JScrollPane(table);
                    jScrollPane1.setPreferredSize(new Dimension(660, 150));
                    pane2.remove(jScrollPane1);
                    GridBagConstraints c3=new GridBagConstraints();
                    c3.gridx=1;
                    c3.gridy=1;
                    c3.gridwidth=7;
                    c3.gridheight=3;
                    pane2.add(jScrollPane1,c3);    //添加滚动面板
                    pane2.revalidate();
                    pane2.repaint();
                    pane2.setVisible(true);
                } catch (Exception e2) {
                    System.out.println(e2.toString());
                }
            }
        });
        pane1.setVisible(true);

        //pane2的模块设计------------------------------------

        pane2.setLayout(new GridBagLayout());
        result.setFont(new Font("宋体", Font.BOLD, 20));
        GridBagConstraints c2=new GridBagConstraints();
        c2.gridwidth=7;
        c2.gridheight=1;
        c2.weightx=0;
        c2.weighty=0;
        c2.anchor=GridBagConstraints.WEST;
        pane2.add(result,c2);
        String[] headers={"学号","姓名","性别","出生年月","专业及班级"};
        model.setColumnIdentifiers(headers);
        //创建table
        table = new JTable(model);
        table.setEnabled(false);
        //设置表头样式
        JTableHeader header = table.getTableHeader();
        header.setFont(font2);
        DefaultTableCellRenderer hr = (DefaultTableCellRenderer) header.getDefaultRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        // 设置行高为30像素
        table.setRowHeight(20);
        //把table装进滚动面板
        jScrollPane1= new JScrollPane(table);
        jScrollPane1.setPreferredSize(new Dimension(750, 150));
        c2.gridy=1;
        c2.gridheight=3;
        pane2.add(jScrollPane1,c2);    //添加滚动面板
        c2.gridx=5;
        c2.gridy=4;
        c2.gridheight=1;
        c2.anchor=GridBagConstraints.EAST;
        pane2.setVisible(true);
        //设置主面板大小
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
        c.gridx=0;
        c.gridy=3;
        c.gridheight=3;
        this.add(pane2,c);
        Border border3 = BorderFactory.createLineBorder(Color.BLACK, 2, true);
        this.setBorder(border3);
    }
    String SetSql(String[] Inputs){
        String sql;
        if (Inputs[0].equals("") == true && Inputs[1].equals("") == true) {
            sql = "";
        }
        else if ((Inputs[0].equals("") == false && Inputs[1].equals("") == true)) {
            sql = "select studentID, studentName, studentSex, studentBirthday, clas from tb_student where studentID = '" + Inputs[0] + "'";
        }
        else if ((Inputs[0].equals("") == true && Inputs[1].equals("") == false)) {
            sql = "select studentID, studentName, studentSex, studentBirthday, clas from tb_student where studentName = '" + Inputs[1] + "'";
        }
        else{
            sql = "select studentID, studentName, studentSex, studentBirthday, clas from tb_student where studentID = '" + Inputs[0] + "' and studentName = '" + Inputs[1] +"'";
        }
        return sql;
    }
    DefaultTableModel CreateModel() throws SQLException {
        int j = model.getRowCount();//删除表格中原有的数据
        if (j > 0) {
            for (int i = 0; i < j; i++) {
                model.removeRow(0);
            }
        }
        ResultSet rs1 = db.Query(sql);
        if(!rs1.next()){
            JOptionPane.showMessageDialog(null, "查询不到课程信息！");
            return model;
        }
        rs = db.Query(sql);
        // 内循环获取每个结果集的记录
        while(rs.next()){
            Vector tempvector = new Vector(1, 1);
            tempvector.add(rs.getString(1));
            tempvector.add(rs.getString(2));
            tempvector.add(rs.getString(3));
            tempvector.add(rs.getString(4));
            tempvector.add(rs.getString(5));
            model.addRow(tempvector);
        }
        return model;
    }
}

