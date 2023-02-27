package org.example.Student;

import org.example.DBconn.DBconn;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class SelectedCoursePane extends JPanel {
    JLabel title = new JLabel("已选课程");
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
    JButton withdrawal=new JButton("退课");
    String studentID,studentName;


    public SelectedCoursePane(JPanel panel, String ID) {
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
        //设置布局
        pane2.setLayout(new GridBagLayout());
        result.setFont(new Font("宋体", Font.BOLD, 20));
        GridBagConstraints c2=new GridBagConstraints();
        c2.gridwidth=7;
        c2.gridheight=1;
        c2.weightx=0;
        c2.weighty=0;
        c2.anchor=GridBagConstraints.WEST;
        pane2.add(result,c2);
        //{"选择","课程号","学期","任课教师"};
        String[] headers={"","课程号","课程名","学期","任课教师号","任课教师名"};
        model.setColumnIdentifiers(headers);
        //设置SQL语句
        //sql = "select courseID,coursesemester,teacherID from tb_course_selection where studentID='" + studentID + "'";
        //sql="select tb_course_selection.courseID,tb_course.courseName,tb_course_selection.semester,tb_course_selection.teacherID,tb_teacher.teacherName from tb_course,tb_teacher,tb_course_selection where tb_course_selection.teacherID=tb_teacher.teacherID and tb_course.courseID=tb_course_selection.courseID and studentID='"+studentID+"'";
        sql="select tb_course_selection.courseID,tb_course.courseName,tb_course_selection.semester,tb_course_selection.teacherID,tb_teacher.teacherName from tb_course,tb_teacher,tb_course_selection where tb_course_selection.teacherID=tb_teacher.teacherID and tb_course.courseID=tb_course_selection.courseID and tb_course.semester=tb_course_selection.semester and studentID='"+studentID+"'";
        try {
            model=CreateModel();
            table = new JTable(model);
            // 设置"选择"列宽
            table.getColumnModel().getColumn(0).setPreferredWidth(1);
            //设置"第一列"为选择框
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());
            table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());
            // 设置行高为30像素
            table.setRowHeight(20);
            //设置表头样式
            JTableHeader header = table.getTableHeader();
            header.setFont(font2);
            DefaultTableCellRenderer hr = (DefaultTableCellRenderer) header.getDefaultRenderer();
            hr.setHorizontalAlignment(JLabel.CENTER);
            //使用自定义单元格渲染器定义单元格字体大小
            for(int i=1;i<6;i++)
            {
                table.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
            }
            jScrollPane1= new JScrollPane(table);
            jScrollPane1.setPreferredSize(new Dimension(660, 150));
            c2.gridy=1;
            c2.gridheight=3;
            pane2.add(jScrollPane1,c2);//添加滚动面板
            c2.gridy=4;
            c2.gridheight=1;
            c2.anchor=GridBagConstraints.EAST;
            pane2.add(withdrawal,c2);
            pane2.revalidate();
            pane2.repaint();
            pane2.setVisible(true);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        //为退课课按钮添加ActionListener
        withdrawal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //检查此课程是否已登分
                    sql2="select courseID from tb_score where courseID='"+rowData[0]+"' and studentID='"+studentID+"'";
                    rs=db.Query(sql2);
                    if(rs.next()){//ResultSet结果集为空表示此课程未登分
                        JOptionPane.showMessageDialog(null, "此课程已登分！");
                    }
                    else {
                        sql2="delete from tb_course_selection where studentID='"+studentID+"' and courseID='"+rowData[0]+"' and semester='"+rowData[2]+"'and teacherID='"
                                +rowData[3]+"'";
                        int i=db.Update(sql2);
                        JOptionPane.showMessageDialog(null, "退课成功！");
                    }

                    //刷新显示表格
                    model=CreateModel();

                    table = new JTable(model);
                    // 设置"选择"列宽
                    table.getColumnModel().getColumn(0).setPreferredWidth(1);
                    //设置表格第一列为选择框
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
                }catch (Exception e2) {
                    System.out.println(e2.toString());
                }
            }});
        pane2.setVisible(true);
        //设置主面板大小
        this.setPreferredSize(new Dimension(800, 400));
        this.setLayout(new BorderLayout()); //设置布局
        this.add(title,BorderLayout.NORTH);
        this.add(pane2,BorderLayout.CENTER);
        this.setVisible(true);  //设置可见
        //设置边框
        Border border3 = BorderFactory.createLineBorder(Color.BLACK, 2, true);
        this.setBorder(border3);
    }
    DefaultTableModel CreateModel() throws SQLException {
        int j = model.getRowCount();//删除表格中原有的数据
        if (j > 0) {
            for (int i = 0; i < j; i++) {
                model.removeRow(0);
            }
        }
        rs = db.Query(sql);
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
            //setHorizontalAlignment(JCheckBox.CENTER);
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

