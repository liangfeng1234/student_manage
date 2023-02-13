package org.example.Admin.Teacher;

import org.example.DBconn.DBconn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.Vector;


public class TeacherManage extends JFrame{
    JLabel jLabel1 = new JLabel("教师信息管理");
    ResultSet rs = null;
    Vector tempvector = new Vector(1, 1);
    DefaultTableModel model = new DefaultTableModel();
    JTable dbtable = new JTable(model);
    JScrollPane jScrollPane1 = new JScrollPane(dbtable);
    String sql, sxm, sxh;

    public TeacherManage() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        Container container = this.getContentPane();
        jLabel1.setFont(new Font("宋体", Font.PLAIN, 18));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        container.add(jLabel1, "North");
        model.addColumn("工号");
        model.addColumn("姓名");
        model.addColumn("性别");
        model.addColumn("出生日期");
        model.addColumn("职称");
        model.addColumn("部门");
        container.add(jScrollPane1, "Center");
        container.add(dbtable);
        this.setBounds(277, 277, 900, 500);
        this.setVisible(true);
        DBconn db = new DBconn();
        sql = "select * from tb_teacher";
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
                tempvector.add(rs.getString("teacherID"));
                tempvector.add(rs.getString("teacherName"));
                tempvector.add(rs.getString("teacherSex"));
                tempvector.add(rs.getString("teacherBirthday"));
                tempvector.add(rs.getString("post"));
                tempvector.add(rs.getString("department"));
                model.addRow(tempvector);
            }
            dbtable.setEnabled(false); //表格中的数据不能修改
        } catch (Exception e2) {
            System.out.println(e2.toString());
        }

    }
}
