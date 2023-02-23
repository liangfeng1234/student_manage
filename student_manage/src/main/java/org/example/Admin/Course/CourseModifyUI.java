package org.example.Admin.Course;

import org.example.Admin.Teacher.TeacherAddInfo;
import org.example.Admin.Teacher.TeacherInformationUI;
import org.example.DBconn.DBconn;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class CourseModifyUI extends JFrame {
    //声明窗体
    JFrame jFrame;
    // 一共两个搜索区域:
    // 1. 输入姓名;
    // 2. 输入ID;
    private JTextField searchField1;//用于输入课程的名称
    private String name;
    private JTextField searchField2;//用于输入课程号
    private String ID;

    //两个label，分别来放置课程名称和课程号以及搜索的按钮
    private JLabel searchLabel1;
    private JLabel searchLabel2;
    // 一共个按钮:
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
    JTable table = new JTable(model);
    JScrollPane jScrollPane = new JScrollPane(table);

    private Vector vector;
    // 与数据库查询相关的变量
    DBconn dBconn;
    ResultSet resultSet;

    //用来存储按钮选中行的信息
    static String courseID_tem;
    static String teacherID_tem;

    //构造函数
    CourseModifyUI() {
        jFrame = new JFrame("课程信息管理");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(800, 600);
        //相关界面的显示，主要是为了更新Pane
        DisplayUI();
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
        // This value is used to dispose of the current window when the user closes it.
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //SQL查询语句
    public ResultSet courseSearch(String name_, String ID_) throws SQLException {
        // System.out.println("Press the search button");
        String sql = "select * from tb_course where courseName = ? or courseID = ?";
        PreparedStatement preparedStatement = dBconn.conn.prepareStatement(sql);
        preparedStatement.setString(1, name_);
        preparedStatement.setString(2, ID_);
        return preparedStatement.executeQuery();
    }

    void DisplayUI() {
        // 1. 数据库对象的创建:
        dBconn = new DBconn();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 5, 20, 10));

        // 2. 第一个查询区域:
        searchLabel1 = new JLabel("课程名称: ");
        topPanel.add(searchLabel1);
        searchField1 = new JTextField(20);
        topPanel.add(searchField1);

        // 3. 第二个查询区域:
        searchLabel2 = new JLabel("课程号: ");
        topPanel.add(searchLabel2);
        searchField2 = new JTextField(20);
        topPanel.add(searchField2);

        searchButton = new JButton("搜索");
        topPanel.add(searchButton);
        // 4. 中间部分查询结果部分显示
        model.addColumn("是否选择");
        model.addColumn("课程号");
        model.addColumn("课程名称");
        model.addColumn("开课学期");
        model.addColumn("开课教师号");

        // 5. search button function:
        searchButton.addActionListener(e -> {
            // Code to search for teacher information based on the entered name or ID
            // and update the query result display
            if (dBconn.connect()) {
                try {
                    System.out.println("Press the search button");
                    resultSet = courseSearch(searchField1.getText(), searchField2.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                        System.out.println("SUCCESSFULLY");
                        DisplayTable(resultSet);//将查询到的语句插入到model中
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.out.println("DATABASE connected failed!");
            }

        });

        //添加Table的按钮添加
        // Add a new column for the option buttons
        TableColumn optionColumn = new TableColumn();
        optionColumn.setHeaderValue("选择");
        table.addColumn(optionColumn);

        // Create a custom cell renderer for the option buttons
        TableCellRenderer renderer = new ButtonRenderer();
        optionColumn.setCellRenderer(renderer);

        // Create a custom cell editor for the option buttons
        TableCellEditor editor = new ButtonEditor(table);
        optionColumn.setCellEditor(editor);

        table.setFillsViewportHeight(true);
        //在查询完SQL之后，将table加入jScrollPane中
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3, 10, 10));

        addButton = new JButton("添加");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to add a new teacher to the database
                System.out.println("Press the add button");
                CourseAdd courseAdd = new CourseAdd();
                //new CourseAdd(dBconn);
            }
        });
        bottomPanel.add(addButton);

        deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Code to delete a teacher from the database
                System.out.println("Press the delete button");
                CourseDelete courseDelete = new CourseDelete(courseID_tem, teacherID_tem);
            }
        });

        bottomPanel.add(deleteButton);

        modifyButton = new JButton("修改");
        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(courseID_tem != null){
                    // Code to modify a teacher's information in the database
                    System.out.println("Press the modify button");
                    CourseModify courseModify = new CourseModify(courseID_tem);
                }
            }
        });
        bottomPanel.add(modifyButton);

        // 将上面所有的布局添加到主界面中:
        jFrame.add(topPanel, BorderLayout.NORTH);
        jFrame.add(jScrollPane, BorderLayout.CENTER);
        jFrame.add(bottomPanel, BorderLayout.SOUTH);
    }

    //用来存储选中的行的信息
    static void temp(String cID, String sID){
        courseID_tem = cID;
        teacherID_tem = sID;
    }

    // 参数resultSet在传进来的时候必须保证不是空的
    void DisplayTable(ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            vector = new Vector(1, 1);
            vector.add("否");
            vector.add(resultSet.getString("courseID"));
            vector.add(resultSet.getString("courseName"));
            vector.add(resultSet.getString("semester"));
            vector.add(resultSet.getString("teacherID"));
            System.out.println("Select successfully");
            model.addRow(vector);
        }
    }


    //设计JTable的一些类，保证每次只能选择一行数据，来进行课程的删和改正
    // Custom cell renderer for the option buttons
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent
                (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
                //setText("选择");
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
                //setText("否");
            }
            setText("选择");
            return this;
        }
    }

    // Custom cell editor for the option buttons
    static class ButtonEditor extends DefaultCellEditor {
        protected JButton button;

        public ButtonEditor(JTable table) {
            super(new JCheckBox());
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                fireEditingStopped();
                int row = table.getSelectedRow();
                System.out.println("Data content of row " + row + ": " + table.getValueAt(row, 1).toString());
                System.out.println("Option!");
                temp(table.getValueAt(row, 1).toString(), table.getValueAt(row, 4).toString());
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
                //button.setText("否");
            }
            button.setText("选择");
            return button;
        }

        public Object getCellEditorValue() {
            return button.getText();
        }

        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }
    }
}
