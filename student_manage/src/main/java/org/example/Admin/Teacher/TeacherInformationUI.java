package org.example.Admin.Teacher;

import org.example.DBconn.DBconn;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class TeacherInformationUI {
    private JFrame frame;

    // 一共两个搜索区域:
    // 1. 输入姓名;
    // 2. 输入ID;
    private JTextField searchField;
    private String name;
    private JTextField searchField2;
    private String ID;

    // 一共两个搜索标签:
    // 1. Name标签;
    // 2. ID标签;

    private JLabel searchLabel;
    private JLabel searchLabel2;
    // 一共4个按钮:
    // 1. 查找按钮;
    // 2. 添加按钮;
    // 3. 删除按钮;
    // 4. 修改按钮;
    private JButton searchButton, addButton, deleteButton, modifyButton;
    // 相关的查询结果组件
    private JTable queryResultTable;
    // 查询结果显示:
    // 目的是显示一个表格!
    DefaultTableModel model = new DefaultTableModel();
    JTable jTable =new JTable(model);
    JScrollPane jScrollPane =new JScrollPane(jTable);

    private Vector vector;
    // 与数据库查询相关的变量
    DBconn dBconn;
    ResultSet resultSet;

    public TeacherInformationUI() {
        // 相关界面的显示
        DisplayUI();
    }

    /**
     * @autor 10-Kirito
     * @param name_
     * @param ID_
     * @return the result of Query
     * @throws SQLException
     */
    public ResultSet TeaSearch(String name_, String ID_) throws SQLException {
        // System.out.println("Press the search button");
        String sql = "select * from tb_teacher where teacherID = ? or teacherName = ?";
        PreparedStatement preparedStatement = dBconn.conn.prepareStatement(sql);
        preparedStatement.setString(1, ID_);
        preparedStatement.setString(2,name_);
        return preparedStatement.executeQuery();
    }

    // The function of this part of the function is to display the corresponding interface
    public void DisplayUI(){
        // 1. 数据库对象的创建:
        dBconn = new DBconn();

        frame = new JFrame("Teacher Information Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 5, 20, 10));

        // 2. 第一个查询区域:
        searchLabel = new JLabel("Name: ");
        topPanel.add(searchLabel);
        searchField = new JTextField(20);
        topPanel.add(searchField);

        // 3. 第二个查询区域:
        searchLabel2 = new JLabel("ID: ");
        topPanel.add(searchLabel2);
        searchField2 = new JTextField(20);
        topPanel.add(searchField2);

        searchButton = new JButton("Search");
        // 4. 中间部分查询结果部分显示
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("gender");
        model.addColumn("birthday");
        model.addColumn("position");
        model.addColumn("apartment");

        jTable.setFillsViewportHeight(true);

        // 5. search button function:
        searchButton.addActionListener(e -> {
            // Code to search for teacher information based on the entered name or ID
            // and update the query result display
            if(dBconn.connect()){
                try {
                    System.out.println("Press the search button");
                    resultSet = TeaSearch(searchField.getText(), searchField2.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    if(resultSet.next()){
                        System.out.println("SUCCESSFULLY");
                        DisplayTable(resultSet);
                    }
                    else{
                        System.out.println("the resultSet is empty");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }else{
                System.out.println("DATABASE connected failed!");
            }

        });
        topPanel.add(searchButton);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3, 10, 10));

        // add button function:

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to add a new teacher to the database
                System.out.println("Press the add button");
                new TeacherAddInfo(dBconn);
            }
        });
        bottomPanel.add(addButton);

        // delete button function:

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to delete a teacher from the database
                System.out.println("Press the delete button");
                new TeacherDeleteInfo(dBconn);
            }
        });
        bottomPanel.add(deleteButton);

        // modify button function:

        modifyButton = new JButton("Modify");
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to modify a teacher's information in the database
                System.out.println("Press the modify button");
            }
        });
        bottomPanel.add(modifyButton);

        // 将上面所有的布局添加到主界面中:
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(jScrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        // This value is used to dispose of the current window when the user closes it.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // 参数resultSet在传进来的时候必须保证不是空的
    public void DisplayTable(ResultSet resultSet) throws SQLException {
        vector = new Vector(1,1);
        vector.add(resultSet.getString("teacherID"));
        vector.add(resultSet.getString("teacherName"));
        vector.add(resultSet.getString("teacherSex"));
        vector.add(resultSet.getString("teacherBirthday"));
        vector.add(resultSet.getString("post"));
        vector.add(resultSet.getString("department"));
        model.addRow(vector);
    }

    public static void main(String[] args) {
        new TeacherInformationUI();
    }
}