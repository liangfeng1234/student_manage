package org.example.Admin.Course;

import org.example.DBconn.DBconn;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class CourseDelete {
    String courseID, teacherID;
    DBconn dbconn;
    //构造函数来接收课程号cid和教师号sid
    CourseDelete(String cid, String sid){
        courseID = cid;
        teacherID = sid;
        System.out.println(courseID);
        //System.out.println(Name);会出现乱码
        deleteCourse();
    }

    void deleteCourse(){
        if(courseID != null && teacherID != null){
            dbconn = new DBconn();
            if(dbconn.connect()){
                String sql = "delete from tb_course where courseID = ? and teacherID = ?";
                try{
                    PreparedStatement pStatement = dbconn.conn.prepareStatement(sql);
                    pStatement.setString(1, courseID);
                    pStatement.setString(2, teacherID);
                    if(!pStatement.execute()){
                        System.out.println("delete successfully!");
                        JOptionPane.showMessageDialog(null, "删除课程成功");
                    }
                    else{
                        System.out.println("delete failed");
                    }
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }else{
                System.out.println("DATABASE connected failed!");
                JOptionPane.showMessageDialog(null, "删除课程信息失败！");
            }
        }
    }
}
