package org.example.Student;

import org.example.DBconn.DBconn;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.ResultSet;
import java.util.Vector;

public class GradesQueryPane extends JPanel{
    JLabel jLabel1 = new JLabel("学生成绩查询");
    ResultSet rs = null;
    Vector tempvector = new Vector(1, 1);
    DefaultTableModel model = new DefaultTableModel();
    JTable dbtable = new JTable(model);
    JScrollPane jScrollPane1 = new JScrollPane(dbtable);
    String sql, studentID;

    public GradesQueryPane(JPanel panel, String studentID) {
        try {
            this.studentID = studentID;
            jbInit(panel);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit(JPanel panel) throws Exception {
        //新建字体样式
        Font font_title=new Font("宋体", Font.BOLD, 30);
        Font font2=new Font("宋体", Font.PLAIN, 18);
        //设置标题样式
        jLabel1.setFont(font_title);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        //设置当前面板大小和布局
        this.setPreferredSize(new Dimension(800, 400));
        //this.setLocation((panel.getWidth()-800)/2,(panel.getHeight()-400)/2);
        this.setLayout(new BorderLayout());
        this.add(jLabel1, BorderLayout.NORTH);
        //给Jpanel设置边框
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2, true);
        this.setBorder(border);
        //设置JTable的列名
        String[] headers={"课程号","课程名","学期","成绩"};
        model.setColumnIdentifiers(headers);
        this.add(jScrollPane1, BorderLayout.CENTER);    //添加滚动面板
        //this.add(dbtable);
        //this.setBounds((frame.getWidth()-700)/2,(frame.getHeight()-400)/2, 700, 400);
        this.setVisible(true);  //设置课件
        //连接数据库
        DBconn db = new DBconn();
//        sql = "select tb_score.courseID,tb_course.courseName,tb_score.semester,score " +
//                "from tb_score,tb_course " +
//                "where tb_score.courseID=tb_course.courseID and studentID='" + studentID + "'";
        sql = "select tb_score.courseID,tb_course.courseName,tb_score.semester,score from tb_score,tb_course where tb_score.courseID=tb_course.courseID and tb_score.semester=tb_course.semester and studentID='" + studentID + "'";
        try {
            int j = model.getRowCount();//删除表格中原有的数据
            if (j > 0) {
                for (int i = 0; i < j; i++) {
                    model.removeRow(0);
                }
            }
            rs = db.Query(sql); //从数据库中查询相应的数据

            while (rs.next()) {
                tempvector = new Vector(1, 1);
                tempvector.add(rs.getString("courseID"));
                tempvector.add(rs.getString("courseName"));
                tempvector.add(rs.getString("semester"));
                tempvector.add(rs.getString("score"));
                model.addRow(tempvector);
            }
            dbtable.setEnabled(false); //表格中的数据不能修改
            dbtable.getTableHeader().setFont(font2);
            dbtable.setFont(new Font("宋体", Font.PLAIN, 16));    //设置表格中的文字大小
            //dbtable.setGridColor(Color.gray);  //设置单元格框线颜色
            dbtable.setRowHeight(25);   //设置行宽
            //设置单元格居中显示
            DefaultTableCellRenderer dc=new DefaultTableCellRenderer();
            dc.setHorizontalAlignment(SwingConstants.CENTER);
            dbtable.setDefaultRenderer(Object.class, dc);
            //设置表头居中显示
            JTableHeader header = dbtable.getTableHeader();
            DefaultTableCellRenderer hr = (DefaultTableCellRenderer) header.getDefaultRenderer();
            hr.setHorizontalAlignment(JLabel.CENTER);

        } catch (Exception e2) {
            System.out.println(e2.toString());
        }

    }
}
