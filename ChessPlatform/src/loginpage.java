
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class loginpage extends JPanel{
    private JFrame jf=new JFrame();
    private JPanel jp=new JPanel();


    public void showLoginPage() {

        jf.setTitle("登录");
        jf.setSize(280,350);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(3);

        jf.setLayout(new BorderLayout());

        Dimension dim1=new Dimension(280,0);



        jf.add(this,BorderLayout.CENTER);

        //实现右边的JPanel容器界面
        jp.setPreferredSize(dim1);
        jp.setBackground(Color.white);
        jf.add(jp,BorderLayout.EAST);//添加到框架布局的东边部分
        jp.setLayout(null);


        JLabel nameStr = new JLabel("账号:");
        nameStr.setBounds(50, 50, 100, 30);
        jp.add(nameStr);

        JLabel passwordStr = new JLabel("密码:");
        passwordStr.setBounds(50, 100, 100, 30);
        jp.add(passwordStr);

        JTextField userIDText = new JTextField();
        userIDText.setBounds(100, 50, 150, 30);
        jp.add(userIDText);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(100, 100, 150, 30);
        jp.add(passwordText);

        JLabel tip=new JLabel("");
        tip.setBounds(120,220,150,30);
        tip.setHorizontalTextPosition(JLabel.CENTER);
        jp.add(tip);


        LoginListener loginListener=new LoginListener(jf,tip,userIDText,passwordText);


        String butname1= "登录";
        JButton button1=new JButton(butname1);
        button1.setBounds(30,180,70,30);
        button1.addActionListener(loginListener);
        jp.add(button1);



        String butname2= "注册";
        JButton button2=new JButton(butname2);
        button2.setBounds(115,180,70,30);
        button2.addActionListener(loginListener);
        jp.add(button2);

        String butname3= "游客登录";
        JButton button3=new JButton(butname3);
        button3.setBounds(200,180,70,30);
        button3.addActionListener(loginListener);
        button3.setBorder(new EmptyBorder(0,10,0,10));
        jp.add(button3);


        jf.setVisible(true);
    }

}


class LoginListener implements ActionListener {
    private JTextField userIDText;
    private JPasswordField passwordText;
    private JFrame jf;
    private JLabel tip;

    public LoginListener(JFrame jf,JLabel tip,JTextField userIDText,JPasswordField passwordText) {
        this.jf=jf;
        this.tip=tip;
        this.userIDText=userIDText;
        this.passwordText=passwordText;
    }

    //当界面发生操作时进行处理
    public void actionPerformed(ActionEvent e) {
        User user = new User(userIDText.getText(),new String(passwordText.getPassword()));
        UserSet userSet=new UserSet();
        if (e.getActionCommand().equals("注册")) {
            if(userSet.addUser(user,true)) {
                tip.setText("注册成功！");
            }
            else {
                tip.setText("注册失败！");
            }

        } else if (e.getActionCommand().equals("登录")) {
            if(userSet.isLegalUser(user)) {
                System.out.println("登录成功");
                homepage gf = new homepage(userSet.getUser(user.getUserID()));
                gf.showHomepage();
                jf.setVisible(false);
            }
            else {
                tip.setText("账号或密码错误！");
            }
            }else {
            System.out.println("游客登录成功");
            user=new User("游客","0");
            homepage gf = new homepage(user);
            gf.showHomepage();
            jf.setVisible(false);
        }
    }

}
