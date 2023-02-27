package org.example.Admin.Student;

import org.example.DBconn.DBconn;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;


public class StuCourseSelectionManage extends JPanel{
    JLabel title = new JLabel("学生选课管理");
    JPanel pane1 = new JPanel();
    JLabel studentID = new JLabel("学号ID");
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
    String sql,sql2; //sql语句
    ResultSet rs = null;
    //连接数据库
    DBconn db = new DBconn();
    JScrollPane jScrollPane1;
    int selectedRow = -1;
    Object[] rowData = null;
    String sID;

    JButton add=new JButton("添加");
    JButton modify=new JButton("修改");
    JButton delete=new JButton("删除");
    public class AddCourseSelectionFrame extends JFrame {

        private JTextField sidField;
        private JTextField cidField;
        private JTextField semesterField;

        private JTextField tidField;
        private JPanel course;
        public AddCourseSelectionFrame() throws SQLException {
            // 设置标题
            setTitle("添加学生选课记录界面");

            // 设置窗口大小和位置
            setSize(775, 290);
            setLocationRelativeTo(null);


            // 创建GridBagConstraints对象
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间隔

            // 创建面板并设置布局为GridBagLayout
            Container c = this.getContentPane();
            c.setLayout(new GridBagLayout());

            // 添加学生ID组件
            JLabel idLabel = new JLabel("学生ID：");
            sidField = new JTextField(10);
            sidField.setText(rowData[0].toString());
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            gbc.anchor = GridBagConstraints.WEST;
            c.add(idLabel, gbc);
            gbc.gridx = 5;
            gbc.gridy = 2;
            gbc.gridwidth=10;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            c.add(sidField, gbc);

            // 添加课程号组件
            JLabel cidLabel = new JLabel("课程号：");
            cidField = new JTextField(10);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            gbc.fill = GridBagConstraints.NONE;
            c.add(cidLabel, gbc);
            gbc.gridx = 5;
            gbc.gridy = 3;
            gbc.gridwidth=10;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            c.add(cidField, gbc);

            // 添加学期组件
            JLabel semesterLabel = new JLabel("学期：");
            semesterField = new JTextField(10);
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            c.add(semesterLabel, gbc);
            gbc.gridx = 5;
            gbc.gridy = 4;
            gbc.gridwidth=10;
            c.add(semesterField, gbc);

            // 添加教师号组件
            JLabel tidLabel = new JLabel("教师号：");
            tidField = new JTextField(10);
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            gbc.fill = GridBagConstraints.NONE;
            c.add(tidLabel, gbc);
            gbc.gridx = 5;
            gbc.gridy = 5;
            gbc.gridwidth=10;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            c.add(tidField, gbc);

            //添加课程表面板
            course=new JPanel();
            //设置课程表标题
            JLabel subTitle=new JLabel("课程表：");
            subTitle.setFont(new Font("宋体", Font.BOLD, 18));
            gbc.gridx=15;
            gbc.gridy=0;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            //c.add(subTitle,gbc);
            //创建表格
            int j = model.getRowCount();//删除表格中原有的数据
            if (j > 0) {
                for (int i = 0; i < j; i++) {
                    model.removeRow(0);
                }
            }
            DefaultTableModel cmodel=new DefaultTableModel();
            String[] cheaders={"课程号","课程名","学期","教师号"};
            cmodel.setColumnIdentifiers(cheaders);  //设置表头
            JTable ctable=new JTable(cmodel);//创建ctable
            sql="select courseID,courseName,semester,teacherID from tb_course";
            ResultSet crs = db.Query(sql);  //查询
            while(crs.next()) {
                Vector tempvector = new Vector(1, 1);
                tempvector.add(crs.getString("courseID"));
                tempvector.add(crs.getString("courseName"));
                tempvector.add(crs.getString("semester"));
                tempvector.add(crs.getString("teacherID"));
                cmodel.addRow(tempvector);
            }

            //设置表头样式
            JTableHeader cheader = ctable.getTableHeader();
            cheader.setFont(new Font("宋体", Font.PLAIN, 16));
            DefaultTableCellRenderer chr = (DefaultTableCellRenderer) cheader.getDefaultRenderer();
            chr.setHorizontalAlignment(JLabel.CENTER);
            //使用自定义单元格渲染器定义单元格字体大小
            for(int k=0;k<4;k++)
            {
                ctable.getColumnModel().getColumn(k).setCellRenderer(new CustomTableCellRenderer());
            }
            // 设置行高为20像素
            ctable.setRowHeight(20);
            JScrollPane cscrollPane=new JScrollPane(ctable);//装进滚动面板
            cscrollPane.setPreferredSize(new Dimension(400, 200));
            course.add(cscrollPane);
            gbc.gridx=15;
            gbc.gridy=2;
            gbc.gridwidth=10;
            gbc.gridheight=5;
            c.add(course, gbc);
            // 添加取消按钮
            JButton cancelButton = new JButton("取消");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 点击取消按钮时，关闭窗口
                    dispose();
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets= new Insets(0, 15, 0, 0);
            c.add(cancelButton, gbc);
            // 添加保存按钮
            JButton saveButton = new JButton("保存");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        //检查新添加的选课记录是否重复
                        sql="select studentID from tb_course_selection where courseID='"+cidField.getText()+"' and semester='"+semesterField.getText()+"' and teacherID='"+tidField.getText()+"'";
                        rs=db.Query(sql);
                        if(rs.next()){
                            JOptionPane.showMessageDialog(null, "选课记录重复，不能添加！");
                        }
                        else {//检查新添加的课程是否存在
                            sql = "select courseID from tb_course where courseID='" + cidField.getText() + "' and semester='" + semesterField.getText() + "' and teacherID='" + tidField.getText() + "'";
                            rs = db.Query(sql);
                            if (!rs.next()) {
                                JOptionPane.showMessageDialog(null, "该课程信息不存在，不能添加！");
                            } else {//ResultSet结果集为空表示该学号不存在，可以添加
                                sql2 = "insert into tb_course_selection(studentID,courseID,semester,teacherID)" +
                                        "values('" + sidField.getText() + "','" + cidField.getText() + "','" + semesterField.getText() + "','" + tidField.getText() + "')";

                                int i = db.Update(sql2);
                                JOptionPane.showMessageDialog(null, "添加成功！");
                            }
                        }
                    }catch (Exception e2) {
                        System.out.println(e2.toString());
                    }
                    dispose();
                }
            });
            gbc.gridx = 5;
            gbc.gridy = 6;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets= new Insets(0, 30, 0, 0);
            c.add(saveButton, gbc);

            // 设置窗口可见
            setVisible(true);
        }
    }
    public class ModifyCourseSelectionFrame extends JFrame {

        private JTextField sidField;
        private JTextField cidField;
        private JTextField semesterField;

        private JTextField tidField;
        private JPanel course;
        private JPanel course_selection;
        public ModifyCourseSelectionFrame() throws SQLException {
            // 设置标题
            setTitle("修改学生选课记录界面");

            // 设置窗口大小和位置
            setSize(1275, 300);
            setLocationRelativeTo(null);


            // 创建GridBagConstraints对象
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间隔

            // 创建面板并设置布局为GridBagLayout
            Container c = this.getContentPane();
            c.setLayout(new GridBagLayout());

            // 添加学生ID组件
            JLabel idLabel = new JLabel("学生ID：");
            sidField = new JTextField(10);
            sidField.setText(rowData[0].toString());
            sidField.setEditable(false);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            gbc.anchor = GridBagConstraints.WEST;
            c.add(idLabel, gbc);
            gbc.gridx = 5;
            gbc.gridy = 1;
            gbc.gridwidth=10;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            c.add(sidField, gbc);

            // 添加课程号组件
            JLabel cidLabel = new JLabel("课程号：");
            cidField = new JTextField(10);
            cidField.setText(rowData[1].toString());
            cidField.setEditable(false);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            gbc.fill = GridBagConstraints.NONE;
            c.add(cidLabel, gbc);
            gbc.gridx = 5;
            gbc.gridy = 2;
            gbc.gridwidth=10;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            c.add(cidField, gbc);

            // 添加学期组件
            JLabel semesterLabel = new JLabel("学期：");
            semesterField = new JTextField(10);
            semesterField.setText(rowData[2].toString());
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            c.add(semesterLabel, gbc);
            gbc.gridx = 5;
            gbc.gridy = 3;
            gbc.gridwidth=10;
            c.add(semesterField, gbc);

            // 添加教师号组件
            JLabel tidLabel = new JLabel("教师号：");
            tidField = new JTextField(10);
            tidField = new JTextField(10);
            tidField.setText(rowData[3].toString());
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            gbc.fill = GridBagConstraints.NONE;
            c.add(tidLabel, gbc);
            gbc.gridx = 5;
            gbc.gridy = 4;
            gbc.gridwidth=10;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            c.add(tidField, gbc);

            //添加课程表面板
            course=new JPanel();
            //设置课程表标题
            JLabel subTitle=new JLabel("课程表：");
            subTitle.setFont(new Font("宋体", Font.BOLD, 18));
            gbc.gridx=15;
            gbc.gridy=0;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            c.add(subTitle,gbc);
            DefaultTableModel cmodel=new DefaultTableModel();
            String[] cheaders={"课程号","课程名","学期","教师号"};
            cmodel.setColumnIdentifiers(cheaders);  //设置表头
            JTable ctable=new JTable(cmodel);//创建ctable
            sql="select courseID,courseName,semester,teacherID from tb_course";
            ResultSet crs = db.Query(sql);  //查询
            while(crs.next()) {
                Vector tempvector = new Vector(1, 1);
                tempvector.add(crs.getString("courseID"));
                tempvector.add(crs.getString("courseName"));
                tempvector.add(crs.getString("semester"));
                tempvector.add(crs.getString("teacherID"));
                cmodel.addRow(tempvector);
            }

            //设置表头样式
            JTableHeader cheader = ctable.getTableHeader();
            cheader.setFont(new Font("宋体", Font.PLAIN, 16));
            DefaultTableCellRenderer chr = (DefaultTableCellRenderer) cheader.getDefaultRenderer();
            chr.setHorizontalAlignment(JLabel.CENTER);
            //使用自定义单元格渲染器定义单元格字体大小
            for(int k=0;k<4;k++)
            {
                ctable.getColumnModel().getColumn(k).setCellRenderer(new CustomTableCellRenderer());
            }
            // 设置行高为20像素
            ctable.setRowHeight(20);
            JScrollPane cscrollPane=new JScrollPane(ctable);//装进滚动面板
            cscrollPane.setPreferredSize(new Dimension(400, 200));
            course.add(cscrollPane);
            gbc.gridx=15;
            gbc.gridy=1;
            gbc.gridwidth=10;
            gbc.gridheight=5;
            c.add(course, gbc);

            //添加选课表面板
            course_selection=new JPanel();
            //设置课程表标题
            JLabel subTitle2=new JLabel("选课表：");
            subTitle2.setFont(new Font("宋体", Font.BOLD, 18));
            gbc.gridx=25;
            gbc.gridy=0;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            c.add(subTitle2,gbc);
            //创建表格
            DefaultTableModel csmodel=new DefaultTableModel();
            String[] csheaders={"学生ID","课程号","学期","教师号"};
            csmodel.setColumnIdentifiers(csheaders);  //设置表头
            JTable cstable=new JTable(csmodel);//创建cstable
            sql="select * from tb_course_selection";
            ResultSet csrs = db.Query(sql);  //查询
            while(csrs.next()) {
                Vector tempvector = new Vector(1, 1);
                tempvector.add(csrs.getString("studentID"));
                tempvector.add(csrs.getString("courseID"));
                tempvector.add(csrs.getString("semester"));
                tempvector.add(csrs.getString("teacherID"));
                csmodel.addRow(tempvector);
            }

            //设置表头样式
            JTableHeader csheader = cstable.getTableHeader();
            csheader.setFont(new Font("宋体", Font.PLAIN, 16));
            DefaultTableCellRenderer cshr = (DefaultTableCellRenderer) csheader.getDefaultRenderer();
            cshr.setHorizontalAlignment(JLabel.CENTER);
            //使用自定义单元格渲染器定义单元格字体大小
            for(int k=0;k<4;k++)
            {
                cstable.getColumnModel().getColumn(k).setCellRenderer(new CustomTableCellRenderer());
            }
            // 设置行高为20像素
            cstable.setRowHeight(20);
            JScrollPane csscrollPane=new JScrollPane(cstable);//装进滚动面板
            csscrollPane.setPreferredSize(new Dimension(400, 200));
            course_selection.add(csscrollPane);
            gbc.gridx=25;
            gbc.gridy=1;
            gbc.gridwidth=10;
            gbc.gridheight=5;
            c.add(course_selection, gbc);
            // 添加取消按钮
            JButton cancelButton = new JButton("取消");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 点击取消按钮时，关闭窗口
                    dispose();
                }
            });
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth=5;
            gbc.gridheight=1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets= new Insets(0, 15, 0, 0);
            c.add(cancelButton, gbc);
            // 添加保存按钮
            JButton saveButton = new JButton("保存");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        //检查该课程是否存在,修改的时候不能修改课程号，否则不如直接删除再添加
                        sql="select courseID,semester,teacherID from tb_course where courseID='"+cidField.getText()+"' and semester='"+semesterField.getText()+"' and teacherID='"+tidField.getText()+"'";
                        rs=db.Query(sql);
                        if(rs.next()){
                            sql2 = "update tb_course_selection set semester='"+semesterField.getText()+"',teacherID='"+tidField.getText()+
                                    "' where studentID='"+sidField.getText()+"' and courseID='"+cidField.getText()+"'";
                            int i=db.Update(sql2);
                            JOptionPane.showMessageDialog(null, "修改成功！");
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "该课程不存在，不能修改！");
                        }
                    }catch (Exception e2) {
                        System.out.println(e2.toString());
                    }
                    dispose();
                }
            });
            gbc.gridx = 5;
            gbc.gridy = 5;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets= new Insets(0, 30, 0, 0);
            c.add(saveButton, gbc);

            // 设置窗口可见
            setVisible(true);
        }

}
    public StuCourseSelectionManage() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
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
        //通过网格包布局控制添加进的组件的显示位置
        GridBagConstraints s1 = new GridBagConstraints();
        s1.fill = GridBagConstraints.NONE;
        s1.anchor=GridBagConstraints.WEST;
        s1.gridx=0;
        s1.gridy=0;
        layout1.setConstraints(studentID, s1);//设置组件
        s1.gridx=4;
        s1.gridy=0;
        layout1.setConstraints(studentNAME, s1);
        s1.gridx=0;
        s1.gridy = 1;
        layout1.setConstraints(TstudentID, s1);
        s1.gridx=4;
        s1.gridy=1;
        layout1.setConstraints(TstudentNAME, s1);
        s1.gridx =8;
        s1.gridy = 0;
        s1.gridwidth = 1;
        s1.gridheight = 2;
        s1.insets = new Insets(0, 260, 0, 0);
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
                    // 设置"选择"列宽
                    table.getColumnModel().getColumn(0).setPreferredWidth(1);
                    //设置第一列为选择框
                    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());
                    table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());

                    //设置表头样式
                    JTableHeader header = table.getTableHeader();
                    header.setFont(font2);
                    DefaultTableCellRenderer hr = (DefaultTableCellRenderer) header.getDefaultRenderer();
                    hr.setHorizontalAlignment(JLabel.CENTER);
                    //使用自定义单元格渲染器定义单元格字体大小
                    for(int j=1;j<5;j++)
                    {
                        table.getColumnModel().getColumn(j).setCellRenderer(new CustomTableCellRenderer());
                    }
                    // 设置行高为20像素
                    table.setRowHeight(20);
                    jScrollPane1= new JScrollPane(table);
                    jScrollPane1.setPreferredSize(new Dimension(700, 150));
                    pane2.remove(jScrollPane1);
                    GridBagConstraints c3=new GridBagConstraints();
                    c3.gridx=0;
                    c3.gridy=1;
                    c3.gridwidth=10;
                    c3.gridheight=3;
                    //c3.anchor=GridBagConstraints.CENTER;
                    pane2.add(jScrollPane1,c3);    //添加滚动面板
                    pane2.revalidate();
                    pane2.repaint();
                    pane2.setVisible(true);
                } catch (Exception e2) {
                    System.out.println(e2.toString());
                }
            }
        });

        //设置可视
        pane1.setVisible(true);

        //pane2的模块设计------------------------------------
        pane2.setLayout(new GridBagLayout());
        result.setFont(new Font("宋体", Font.BOLD, 20));
        GridBagConstraints s2=new GridBagConstraints();
        s2.gridwidth=10;
        s2.gridheight=1;
        s2.gridx=0;
        s2.gridy=0;
        s2.anchor=GridBagConstraints.WEST;
        pane2.add(result,s2);
        String[] headers={"选择","学生ID","课程号","学期","教师号"};
        model.setColumnIdentifiers(headers);
        //创建table
        table = new JTable(model);
        // 设置"选择"列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(1);
        //为表的第一列添加渲染器和编辑器
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());
        table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());
        //设置表头样式
        JTableHeader header = table.getTableHeader();
        header.setFont(font2);
        DefaultTableCellRenderer hr = (DefaultTableCellRenderer) header.getDefaultRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        //使用自定义单元格渲染器定义单元格字体大小
        for(int j=1;j<5;j++)
        {
            table.getColumnModel().getColumn(j).setCellRenderer(new CustomTableCellRenderer());
        }
        // 设置行高为20像素
        table.setRowHeight(20);
        //把table装进滚动面板
        jScrollPane1= new JScrollPane(table);
        jScrollPane1.setPreferredSize(new Dimension(700, 150));
        s2.gridy=1;
        s2.gridheight=3;
        s2.anchor=GridBagConstraints.CENTER;
        pane2.add(jScrollPane1,s2);    //添加滚动面板
        s2.gridx=0;
        s2.gridy=4;
        s2.gridwidth=1;
        s2.gridheight=1;
        s2.insets=new Insets(0, 71, 0, 71);
        pane2.add(add,s2);
        s2.gridx=1;
        s2.gridy=4;
        pane2.add(modify,s2);
        s2.gridx=2;
        s2.gridy=4;
        pane2.add(delete,s2);
        //为添加按钮添加ActionListener
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AddCourseSelectionFrame SmallJFrame1=new AddCourseSelectionFrame();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }});
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ModifyCourseSelectionFrame SmallJFrame2=new ModifyCourseSelectionFrame();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }});
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //检查该学生选的这门课是否已有成绩
                    sql="select studentID from tb_score where studentID='"+rowData[0]+"' and courseID='"+rowData[1]+"'";
                    rs=db.Query(sql);
                    if(rs.next()){
                        JOptionPane.showMessageDialog(null, "该同学选的这门课已有成绩，不能删除！");
                    }
                    else{
                        sql2 = "delete from tb_course_selection where studentID='"+rowData[0]+"' and courseID='"+rowData[1]+"'";
                        db.Update(sql2);
                        JOptionPane.showMessageDialog(null, "删除成功！");
                        UpdateTable();
                        }
                }catch (Exception e2) {
                    System.out.println(e2.toString());
                }
            }});
        //设置主面板大小
        this.setPreferredSize(new Dimension(800, 400));
        this.setLayout(new GridBagLayout());
        GridBagConstraints cs=new GridBagConstraints();
        cs.gridx=0;
        cs.gridy=0;
        cs.gridwidth=10;
        cs.gridheight=1;
//        cs.weightx = 0;
//        cs.weighty = 0;
//        cs.anchor=GridBagConstraints.CENTER;
        this.add(title,cs);
        cs.gridx=0;
        cs.gridy=1;
        cs.gridheight=2;
        this.add(pane1,cs);
        cs.gridx=0;
        cs.gridy=3;
        cs.gridheight=4;
        this.add(pane2,cs);
        //设置边框
        Border border3 = BorderFactory.createLineBorder(Color.BLACK, 2, true);
        this.setBorder(border3);
    }
    //设置sql语句
    String SetSql(String[] Inputs){
        String sql;
        if (Inputs[0].equals("") == true && Inputs[1].equals("") == true ) {
            sql = "select studentID,courseID,semester,teacherID from tb_course_selection";
        }
        else if ((Inputs[0].equals("") == false && Inputs[1].equals("") == true)) {
            sql="select studentID,courseID,semester,teacherID from tb_course_selection where studentID='" + Inputs[0] + "'";
        }
        else if ((Inputs[0].equals("") == true && Inputs[1].equals("") == false)) {
            sql="select tb_course_selection.studentID,tb_course_selection.courseID,tb_course_selection.semester,tb_course_selection.teacherID from tb_course_selection,tb_student where tb_course_selection.studentID=tb_student.studentID and studentName='" + Inputs[1] + "'";
        }
        else {
            sql="select tb_course_selection.studentID,tb_course_selection.courseID,tb_course_selection.semester,tb_course_selection.teacherID from tb_course_selection,tb_student where tb_course_selection.studentID=tb_student.studentID and studentID='"+Inputs[0]+"' and studentName='" + Inputs[1] + "'";
        }
        return sql;
    }
    //创建表模型model
    DefaultTableModel CreateModel() throws SQLException {
        int j = model.getRowCount();//删除表格中原有的数据
        if (j > 0) {
            for (int i = 0; i < j; i++) {
                model.removeRow(0);
            }
        }
        ResultSet rs1 = db.Query(sql);
        if(!rs1.next()){
            JOptionPane.showMessageDialog(null, "查询不到此学生！");
            return model;
        }
        rs = db.Query(sql);
        // 内循环获取每个结果集的记录
        while(rs.next()){
            Vector tempvector = new Vector(1, 1);
            tempvector.add(false);
            tempvector.add(rs.getString(1));
            tempvector.add(rs.getString(2));
            tempvector.add(rs.getString(3));
            tempvector.add(rs.getString(4));

            model.addRow(tempvector);
        }
        return model;
    }
    //渲染器
    private class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
        public CheckBoxRenderer() {

        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (row == selectedRow) {
                setSelected(true);
            } else {
                setSelected(false);
            }
            return this;
        }
    }

    //编辑器
    private class CheckBoxEditor extends DefaultCellEditor {
        public CheckBoxEditor() {
            super(new JCheckBox());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            selectedRow = row;
            rowData = null;
            for (int i = 0; i < table.getRowCount(); i++) {
                if (i != row) {
                    table.setValueAt(false, i, 0);
                }
            }
            return super.getTableCellEditorComponent(table, value, isSelected, row, column);
        }

        @Override
        public Object getCellEditorValue() {
            if (selectedRow != -1) {
                ArrayList<Object> list = new ArrayList<>();
                for (int i = 1; i < table.getColumnCount(); i++) {
                    list.add(table.getValueAt(selectedRow, i));
                }
                rowData = list.toArray();
            }
            return super.getCellEditorValue();
        }

        @Override
        public boolean stopCellEditing() {
            boolean result = super.stopCellEditing();
            if (selectedRow != -1) {
                table.repaint(table.getCellRect(selectedRow, 0, true));
            }
            return result;
        }
    }
    // 自定义TableCellRenderer用来更改字体大小
    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // 获取标准的JLabel，用于呈现单元格内容
            JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // 使单元格中字体居中
            cell.setHorizontalAlignment(SwingConstants.CENTER);

            return cell;
        }
    }

    //更新查询的表格
    void UpdateTable() throws SQLException {
        //更新表格
        Inputs = new String[]{TstudentID.getText(), TstudentNAME.getText()};
        //设置SQL语句
        sql = SetSql(Inputs);
        System.out.println(sql);
        model=CreateModel();
        table = new JTable(model);
        // 设置"选择"列宽
        table.getColumnModel().getColumn(0).setPreferredWidth(1);
        //设置第一列为选择框
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());
        table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());

        //设置表头样式
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("宋体", Font.PLAIN, 16));
        DefaultTableCellRenderer hr = (DefaultTableCellRenderer) header.getDefaultRenderer();
        hr.setHorizontalAlignment(JLabel.CENTER);
        //使用自定义单元格渲染器定义单元格字体大小
        for(int j=1;j<5;j++)
        {
            table.getColumnModel().getColumn(j).setCellRenderer(new CustomTableCellRenderer());
        }
        // 设置行高为20像素
        table.setRowHeight(20);
        jScrollPane1= new JScrollPane(table);
        jScrollPane1.setPreferredSize(new Dimension(660, 150));
        pane2.remove(jScrollPane1);
        GridBagConstraints c3=new GridBagConstraints();
        c3.gridx=0;
        c3.gridy=1;
        c3.gridwidth=10;
        c3.gridheight=3;
        //c3.anchor=GridBagConstraints.CENTER;
        pane2.add(jScrollPane1,c3);    //添加滚动面板
        pane2.revalidate();
        pane2.repaint();
        pane2.setVisible(true);
    }

}
