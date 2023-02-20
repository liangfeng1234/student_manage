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
        String[] headers={"选择","课程号","课程名","学期","任课教师"};
        model.setColumnIdentifiers(headers);
        //设置SQL语句
        sql = "select courseID,courseName,teacherName,semester from tb_course_selection where studentID='" + studentID + "'";
        //System.out.println(sql);
        try {
            model=CreateModel();
            //System.out.println("已查询");
            table = new JTable(model);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());
            table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());

            table.getTableHeader().setFont(font2);
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
                    //设置SQL语句
                    sql2="delete from tb_course_selection where studentID='"+studentID+"' and courseID='"+rowData[0]+"' and teacherName='"
                            +rowData[3]+"' and semester='"+rowData[2]+"'";
//                    sql = "delete from tb_course_selection(studentID, studentName,courseID,courseName,teacherName,semester)"+
//                            "values('"+studentID+"','"+studentName+"','"+rowData[0]+"','"+rowData[1]+"','"+rowData[3]+"','"+rowData[2]+"')";
                    int i=db.Update(sql2);
                    //System.out.println("i: "+i);
                    //刷新显示表格
                    model=CreateModel();
                    //System.out.println("已查询");
                    table = new JTable(model);
                    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());
                    table.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor());

                    table.getTableHeader().setFont(font2);
                    TableColumn firstColumn = table.getColumnModel().getColumn(0);  //设置第一列列宽
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
            //System.out.println("查询到了数据");
            tempvector.add(false);
            tempvector.add(rs.getString("courseID"));
            tempvector.add(rs.getString("courseName"));
            tempvector.add(rs.getString("semester"));
            tempvector.add(rs.getString("teacherName"));
            model.addRow(tempvector);
        }
        //System.out.println("已返回model");
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
                //System.out.println("Selected row: " + selectedRow + ", Data: " + java.util.Arrays.toString(rowData));
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
}

