package org.example;/*
 * 主程序入口，这里显示登录程序
 * */

import org.example.Login.Login;

import javax.swing.*;
import java.awt.*;

public class Welcome {
    boolean packFrame = false;

    public Welcome() {   //构造方法，并显示应用程序
        Login frame = new Login();//这里调用自定义类login 登录
        if (packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();//在窗口中心显示
        Dimension fSize = frame.getSize();
        if (fSize.height > sSize.height) {
            fSize.height = sSize.height;
        } else if (fSize.width > sSize.width) {
            fSize.width = sSize.width;
        }
        frame.setLocation((sSize.width - fSize.width) / 2, (sSize.height - fSize.height) / 2);
        frame.setVisible(true);
    }

    public static void main(String[] args) { //应用程序入口
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                new Welcome();
            }
        });
    }
}
