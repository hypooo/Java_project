package Java_DB;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class loginframe extends JFrame{
    private JTextField username;
    private JPasswordField password;
    private JButton login,register;

    public loginframe() {
        super();
        this.setSize(300,200);
        this.setTitle("登录");
        this.setLocationRelativeTo(getOwner());

        //设置组建布局--三行两列
        Container cont = getContentPane();
        cont.setLayout(new GridLayout(3,2));

        //添加用户名、密码
        cont.add(new JLabel("用户名："));
        username = new JTextField(10);
        cont.add(username);
        cont.add(new JLabel("密码："));
        password = new JPasswordField(10);
        cont.add(password);

        //添加登录、注册按钮
        login = new JButton("登录");
        cont.add(login);
        register = new JButton("注册");
        cont.add(register);

        //注册监听器
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //login事件处理
                String pass = new String(password.getPassword());
                if(username.getText().equals("ding") &&
                        pass.equals("123456")){
                    HelloWorld hello = new HelloWorld();
                    hello.setVisible(true);
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null, "密码错误");
                }
            }
        });
    }
    public static void main(String[] args) {
        loginframe w = new loginframe();
        w.setVisible(true);
        w.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}