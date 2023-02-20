package org.example.Login;




import org.example.DBconn.DBconn;
import org.example.MainUI.AdminMainUI;
import org.example.MainUI.StudentMainUI;
import org.example.MainUI.TeacherMainUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;


public class Login extends JFrame {  //构造各组件
    JPanel contentPane;
    JLabel jLabel1 = new JLabel("用户名：");
    public JTextField uname = new JTextField();
    JLabel jLabel2 = new JLabel("密码：");
    public JPasswordField upsd = new JPasswordField();
    JButton submit = new JButton("登录");
    JButton cancel = new JButton("取消");
    JLabel jLabel3 = new JLabel("学生成绩管理系统");
    JLabel jLabel4 = new JLabel("身份：");
    JComboBox identity = new JComboBox();

    public Login() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);//设置右上角关闭
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {       //组件初始化
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        setSize(new Dimension(280, 300));  //设置尺寸大小
        setResizable(false);  //设置是否可改变大小
        setTitle("学生成绩管理系统");  //设置标题
        jLabel1.setBounds(new Rectangle(49, 69, 61, 32));  //设置各组件的初始位置
        uname.setBounds(new Rectangle(122, 77, 102, 27));
        jLabel2.setBounds(new Rectangle(49, 128, 45, 31));
        upsd.setBounds(new Rectangle(122, 130, 102, 28));
        submit.setBounds(new Rectangle(49, 229, 69, 27));
        cancel.setBounds(new Rectangle(160, 229, 64, 27));
        jLabel3.setFont(new Font("宋体-方正超大字符集", Font.BOLD, 21));
        jLabel3.setBounds(new Rectangle(49, 10, 231, 41));
        jLabel4.setBounds(new Rectangle(49, 182, 55, 26));
        identity.setBounds(new Rectangle(122, 179, 102, 30));
        identity.addItem(new String("学生"));
        identity.addItem(new String("教师"));
        identity.addItem(new String("管理员"));
        contentPane.add(jLabel2);
        contentPane.add(jLabel4);
        contentPane.add(identity);
        contentPane.add(upsd);
        contentPane.add(uname);
        contentPane.add(jLabel1);
        contentPane.add(submit);
        contentPane.add(cancel);
        contentPane.add(jLabel3);


        // Key bindings:
        // 1. ESC: quit;
        // 2. ENTER: login

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {
                // if press ENTER, then verify
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    // Manually call the click event of the submit button
                    submit.doClick();
                }
            }
        };

        // @10-Kirito
        // Key binding:
        // Bind the keyboard key ESC, if the ESC key is pressed, and
        // if the current interface is the main interface, the current
        // interface will be closed.

        this.getRootPane().registerKeyboardAction(
                (ActionEvent e) -> {
                    setVisible(true);

                    upsd.setText("");
                    uname.setText("");
                    dispose();
                },
                KeyStroke.getKeyStroke("ESCAPE"),
                JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        submit.addActionListener(new LoginListener(this));
        cancel.addActionListener(new LoginListener(this));

        // Bind to monitor key input when typing
        uname.addKeyListener(keyListener);
        upsd.addKeyListener(keyListener);
        identity.addKeyListener(keyListener);
    }

    class LoginListener implements ActionListener {//验证
        Login login_frame;
        public LoginListener(Login login_){ login_frame = login_;}

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submit) {
                String ID = uname.getText();
                String psd = new String(upsd.getPassword());
                int authority = 0;
                String ident = (String) identity.getSelectedItem();
                DBconn db = new DBconn();//调用自定义类DBconn来连接数据库，并查找输入的账号密码是否正确
                ResultSet rs = null;
                String sql = "";
                boolean login = false;
                if (ident.equals("学生")) {//数据库语句查询
                    sql = "select * from tb_student where studentID='" + ID + "' and password='" + psd + "'";
                } else if (ident.equals("教师")) {
                    sql = "select * from tb_teacher where teacherID='" + ID + "' and password='" + psd + "'";
                } else {
                    sql = "select * from logon where userID='" + ID + "' and password='" + psd + "'";
                }
                try {
                    rs = db.Query(sql);
                    if (rs.next()) {
                        login = true;
                    } else {//设置弹窗
                        JOptionPane.showMessageDialog(null, "用户/密码错误，请重新输入！");
                    }
                    if (login == true) {
                        if (ident.equals("学生")) {
                            StudentMainUI mui = new StudentMainUI(ID, psd, ident, login_frame);

                            setVisible(false);
                            mui.setVisible(true);
                        } else if (ident.equals("管理员")) {
                            AdminMainUI admin = new AdminMainUI(ID, psd, ident);//调用自定义类MAINUI加载下面的程序
                            setVisible(false);
                            admin.setVisible(true);
                        } else if (ident.equals("教师")) {
                            TeacherMainUI teac = new TeacherMainUI(ID, psd, ident);
                            setVisible(false);
                            teac.setVisible(true);
                        }
                    }
                } catch (Exception er) {
                    System.out.println(er.toString());
                }
                db.close();
            }else{
                System.exit(0);
            }
        }
    }
}
