package org.example.Admin.Teacher;

import org.example.DBconn.DBconn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class TeacherDeleteInfo extends JFrame{

    // 0. 界面的最上面应该显示教师的姓名和工号以及对应的搜索输入框
    private JLabel searchName;
    private JLabel searchNum;
    private JTextField nameField;
    private JTextField numField;

    // 1. 查询结果表格的显示:
    DefaultTableModel model = new DefaultTableModel();
    JTable jTable =new JTable(model);
    JScrollPane jScrollPane =new JScrollPane(jTable);

    // 2. 最下面应该显示的是两个按钮，一个是搜索按钮，另外的一个按钮应该是删除按钮:
    private JButton searchButton, deleteButton;


    // 3. 数据库Key:
    DBconn dBconn;
    Vector vector = new Vector(1,1);
    public TeacherDeleteInfo(){
        super("Delete the selected teacher");
    }
    public TeacherDeleteInfo(DBconn dBconn_){
        dBconn = dBconn_;
        new TeacherDeleteInfo();
        DisplayUI();
    }

    public void DisplayUI(){
        // 1. 最上面的框架:
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 5, 20, 10));

        searchName = new JLabel("Name: ");
        topPanel.add(searchName);
        nameField = new JTextField(20);
        topPanel.add(nameField);

        searchNum = new JLabel("ID: ");
        topPanel.add(searchNum);
        numField = new JTextField(20);
        topPanel.add(numField);

        // 2. 最中间的搜索结果:
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("gender");
        model.addColumn("birthday");
        model.addColumn("position");
        model.addColumn("apartment");
        jTable.setFillsViewportHeight(true);

        // 3. 最下面的两个按钮:
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3, 10, 10));

        searchButton = new JButton("Search");

        searchButton.addActionListener(e -> {
            int num = model.getRowCount();
            for(int i = 0; i < num; i++)
                model.removeRow(i);

            System.out.println("Press the search button");
            String sql = "select * from tb_teacher where teacherID = ? or teacherName = ?";
            try {
                // 传入参数:
                PreparedStatement preparedStatement = dBconn.conn.prepareStatement(sql);
                preparedStatement.setString(1, numField.getText());
                preparedStatement.setString(2, nameField.getText());
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    // 查询结果不为空
                    vector = new Vector(1,1);
                    vector.add(resultSet.getString("teacherID"));
                    vector.add(resultSet.getString("teacherName"));
                    vector.add(resultSet.getString("teacherSex"));
                    vector.add(resultSet.getString("teacherBirthday"));
                    vector.add(resultSet.getString("post"));
                    vector.add(resultSet.getString("department"));
                    model.addRow(vector);
                }else{
                    // 查询结果为空
                    JOptionPane.showMessageDialog(null,"未查询到相关老师信息，请检查输入内容!");
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        bottomPanel.add(searchButton);

        deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> {
            System.out.println("Press the delete button");
            String sql = "delete from tb_teacher where teacherID=? or teacherName=?;";
            try {
                PreparedStatement statement = dBconn.conn.prepareStatement(sql);
                statement.setString(1, numField.getText());
                statement.setString(2, nameField.getText());
                if(!statement.execute()){
                    JOptionPane.showMessageDialog(null,"删除教师信息成功!");
                }else{
                    JOptionPane.showMessageDialog(null,"删除失败，请检查输入信息!");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        bottomPanel.add(deleteButton);

        add(topPanel, BorderLayout.NORTH);
        add(jScrollPane, BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(400,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new TeacherDeleteInfo();
    }


}
