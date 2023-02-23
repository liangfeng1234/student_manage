package org.example.Admin.Course;

import org.example.DBconn.DBconn;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CourseStuDelete {
    String courseID, studentID;
    //构造函数来接收号和名字
    DBconn dbconn;
    CourseStuDelete(String courseid, String studentid){
        courseID = courseid;
        studentID = studentid;
        System.out.println(courseID);
        //System.out.println(Name);会出现乱码
        deleteCourse();
    }

    void deleteCourse(){
        if(courseID != null && studentID != null){
            dbconn = new DBconn();
            if(dbconn.connect()){
                String sql = "delete from tb_score where courseID = ? and studentID = ?";
                try{
                    PreparedStatement pStatement = dbconn.conn.prepareStatement(sql);
                    pStatement.setString(1, courseID);
                    pStatement.setString(2, studentID);
                    if(!pStatement.execute()){
                        System.out.println("delete successfully!");
                        JOptionPane.showMessageDialog(null, "删除学生成绩成功");
                    }
                    else{
                        System.out.println("delete failed");
                    }
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }else{
                System.out.println("DATABASE connected failed!");
            }
        }
    }
}
