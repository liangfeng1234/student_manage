package org.example.Student;

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

public class CourseQueryPane extends JPanel {
    JLabel title = new JLabel("课程查询");
    JPanel pane1 = new JPanel();
    JLabel courseID = new JLabel("课程ID");
    JTextField TcourseID = new JTextField(10);
    JLabel courseNAME = new JLabel("课程名称");
    JTextField TcourseNAME = new JTextField(10);
    JLabel teacher = new JLabel("任课老师");
    JTextField Tteacher = new JTextField(10);
    JButton query = new JButton("查询");
    String[] Inputs=new String[]{"1","2","3"};


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
    String studentID,cID;


    public CourseQueryPane(JPanel panel, String ID) {
        try {
            this.studentID=ID;
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
        query.setFont(font2);
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
        pane1.add(query);
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
        s1.gridx=0;
        s1.gridy = 1;
        layout1.setConstraints(TcourseID, s1);
        s1.gridx=2;
        s1.gridy=1;
        layout1.setConstraints(TcourseNAME, s1);
        s1.gridx=4;
        s1.gridy=1;
        layout1.setConstraints(Tteacher, s1);
        s1.gridx =8;
        s1.gridy = 0;
        s1.gridwidth = 1;
        s1.gridheight = 2;
        s1.insets = new Insets(0, 100, 0, 0);
        layout1.setConstraints(query, s1);

        // 添加 ActionListener 到"查询"按钮
        query.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inputs = new String[]{TcourseID.getText(), TcourseNAME.getText(), Tteacher.getText()};
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
                    for(int j=1;j<6;j++)
                    {
                        table.getColumnModel().getColumn(j).setCellRenderer(new CustomTableCellRenderer());
                    }
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
        String[] headers={"选择","课程号","课程名","学期","任课教师号","任课教师名"};
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
        for(int j=1;j<6;j++)
        {
            table.getColumnModel().getColumn(j).setCellRenderer(new CustomTableCellRenderer());
        }
        // 设置行高为30像素
        table.setRowHeight(20);
        //把table装进滚动面板
        jScrollPane1= new JScrollPane(table);
        jScrollPane1.setPreferredSize(new Dimension(660, 150));
        c2.gridy=1;
        c2.gridheight=3;
        pane2.add(jScrollPane1,c2);    //添加滚动面板
        c2.gridx=5;
        c2.gridy=4;
        c2.gridheight=1;
        c2.anchor=GridBagConstraints.EAST;
        pane2.add(select,c2);
        //设置选课按钮的大小和位置
        select.setFont(font2);

        //为选课按钮添加ActionListener
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    //检查是否已选过此课程
                    sql="select courseID from tb_course_selection where courseID='"+rowData[0]+"' and studentID='"+studentID+"'";
                    rs=db.Query(sql);
                    if(rs.next()){//ResultSet结果集为空表示未选过这门课
                        JOptionPane.showMessageDialog(null, "此课程已选！");
                    }
                    else {
                        sql2 = "insert into tb_course_selection(studentID,courseID,semester,teacherID)"+
                                "values('"+studentID+"','"+rowData[0]+"','"+rowData[2]+"','"+rowData[3]+"')";
                        int i=db.Update(sql2);
                        JOptionPane.showMessageDialog(null, "选课成功！");
                    }
                }catch (Exception e2) {
                    System.out.println(e2.toString());
                }
            }});
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
        if (Inputs[0].equals("") == true && Inputs[1].equals("") == true && Inputs[2].equals("") == true) {
            sql = "";
        }
        else if ((Inputs[0].equals("") == false && Inputs[1].equals("") == true && Inputs[2].equals("") == true)) {
            sql="select courseID,courseName,semester,tb_course.teacherID,tb_teacher.teacherName from tb_course,tb_teacher where tb_course.teacherID=tb_teacher.teacherID and courseID='" + Inputs[0] + "'";
        }
        else if ((Inputs[0].equals("") == true && Inputs[1].equals("") == false && Inputs[2].equals("") == true)) {
            sql="select courseID,courseName,semester,tb_course.teacherID,tb_teacher.teacherName from tb_course,tb_teacher where tb_course.teacherID=tb_teacher.teacherID and courseName='" + Inputs[1] + "'";
        }
        else if ((Inputs[0].equals("") == true && Inputs[1].equals("") == true && Inputs[2].equals("") == false)) {
            sql="select courseID,courseName,semester,tb_course.teacherID,tb_teacher.teacherName from tb_course,tb_teacher where tb_course.teacherID=tb_teacher.teacherID and teacherName='" + Inputs[2] + "'";
        }
        else if ((Inputs[0].equals("") == false && Inputs[1].equals("") == false && Inputs[2].equals("") == true)) {
            sql="select courseID,courseName,semester,tb_course.teacherID,tb_teacher.teacherName from tb_course,tb_teacher where tb_course.teacherID=tb_teacher.teacherID and courseID='" + Inputs[0] + "' and courseName='"+Inputs[1]+"'";
        }
        else if ((Inputs[0].equals("") == false && Inputs[1].equals("") == true && Inputs[2].equals("") == false)) {
            sql="select courseID,courseName,semester,tb_course.teacherID,tb_teacher.teacherName from tb_course,tb_teacher where tb_course.teacherID=tb_teacher.teacherID and courseID='" + Inputs[0] + "' and teacherName='"+Inputs[2]+"'";
        }
        else if ((Inputs[0].equals("") == true && Inputs[1].equals("") == false && Inputs[2].equals("") ==false)) {
            sql="select courseID,courseName,semester,tb_course.teacherID,tb_teacher.teacherName from tb_course,tb_teacher where tb_course.teacherID=tb_teacher.teacherID and courseName='" + Inputs[1] + "' and teacherName='"+Inputs[2]+"'";
        }
        else{
            sql="select courseID,courseName,semester,tb_course.teacherID,tb_teacher.teacherName from tb_course,tb_teacher where tb_course.teacherID=tb_teacher.teacherID and courseID='" + Inputs[0] + "' and courseName='"+Inputs[1]+"' and teacherName='"+Inputs[2]+"'";
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
            tempvector.add(false);
            tempvector.add(rs.getString(1));
            tempvector.add(rs.getString(2));
            tempvector.add(rs.getString(3));
            tempvector.add(rs.getString(4));
            tempvector.add(rs.getString(5));

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

            // 更改字体大小
            cell.setFont(new Font("宋体", Font.PLAIN, 14));
            cell.setHorizontalAlignment(SwingConstants.CENTER);

            return cell;
        }
    }
}

