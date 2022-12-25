
import javax.swing.*;
import java.awt.*;

public class homepage  extends JPanel{
    private JFrame jf=new JFrame();
    private JPanel jp=new JPanel();;
    private User user;

    public homepage(User user){
        this.user=user;
    }
    public void showHomepage() {
        //初始化一个界面,并设置标题大小等属性
        homepagelistener listener=new homepagelistener(jf,jp,user);

        jf.setTitle("首页");
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

        //设置选项按钮
        JLabel bq1=new JLabel("棋的类型:");
        bq1.setBounds(70,50,80,30);
        jp.add(bq1);

        String[] boxname1= {"五子棋","围棋","黑白棋"};
        JComboBox box1=new JComboBox(boxname1);
        box1.setBounds(150,50,80,30);
        box1.addItemListener(listener);
        jp.add(box1);

        JLabel bq2=new JLabel("棋盘大小:");
        bq2.setBounds(70,120,80,30);
        jp.add(bq2);
        String[] boxname2= new String[12];
        for(int checkerboardSize=8;checkerboardSize<=19;checkerboardSize++)
            boxname2[checkerboardSize-8]=checkerboardSize+"*"+checkerboardSize;
        JComboBox box2=new JComboBox(boxname2);
        box2.setBounds(150,120,80,30);
        box2.addItemListener(listener);
        jp.add(box2);

        String[] boxname3= {"8*8"};
        JComboBox box3=new JComboBox(boxname3);
        box3.setBounds(150,120,80,30);
        box3.addItemListener(listener);
        jp.add(box3);
        jp.getComponent(4).setVisible(false);




        String butname= "开始游戏";
        JButton button=new JButton(butname);
        button.setBounds(80,200,140,40);
        jp.add(button);

        button.addActionListener(listener);//添加发生操作的监听方法

        jf.setVisible(true);
    }

}
