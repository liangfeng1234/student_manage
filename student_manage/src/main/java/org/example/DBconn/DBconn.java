package org.example.DBconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBconn {
    public Connection conn;
    public Statement stmt;
    public ResultSet rs = null;

    public DBconn() {
        this.connect();
    }

    public boolean connect() {  //进行数据库的连接
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/stu_management?useUnicode=true&characterEncoding=UTF-8";
            String user = "root";
            String password = "123456";
            Class.forName("com.mysql.jdbc.Driver");//加载数据库的驱动
            conn = DriverManager.getConnection(url, user, password);  //连接数据库
            stmt = conn.createStatement(); //创建操作数据库对象
        } catch (Exception ee) {
            System.out.println("connect db error:" + ee.getMessage());
            return false;//捕获异常，数据库没有连接成功，提示错误信息，返回错误
        }
        return true;//返回成功
    }

    public ResultSet Query(String sql) {  //从数据库中查询数据
        try {
            rs = stmt.executeQuery(sql);//查询
        } catch (Exception e) {
            System.out.println(e.toString());  //捕获异常，未查找成功
        }
        return rs;  //返回查询的数据
    }

    public int Update(String sql) {  //将数据库中的数据进行更新
        int i = 0;
        try {
            i = stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return i;//返回数据库中的位置
    }

    public void close() {  //关闭数据库
        try {
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
