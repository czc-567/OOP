import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ChessBoard extends JPanel{
    private Chess chess;
    public JLabel tip,tip1,tip2;
    public JLabel userInfoBlack,userInfoWhite;
    private boolean startState=false;
    private String[] userType={"玩家","玩家"};
    public JFrame jf=new JFrame();
    public void setUserType(String str,int i){
        this.userType[i]=str;
    }
    public String[] getUserType(){
        return userType;
    }
    public ChessBoard(Chess chess){
        this.chess=chess;
    }

    public void initUI() {
        ShapeConfig sc=ShapeConfig.getInstance();
        int size=sc.getSize();
        int row=sc.getRow();
        int col=sc.getCol();

        //初始化一个界面,并设置标题大小等属性
        jf.setTitle(chess.name);
        jf.setSize(size*row+20+150,size*row+20+20);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(3);

        jf.setLayout(new BorderLayout());

        Dimension dim1=new Dimension(150,0);
        Dimension dim3=new Dimension(size*row+20,0);
        Dimension dim2=new Dimension(140,40);
        Dimension dim4=new Dimension(70,40);


        this.setPreferredSize(dim3);
        this.setBackground(Color.LIGHT_GRAY);
        jf.add(this,BorderLayout.CENTER);

        //实现右边的JPanel容器界面
        JPanel jp=new JPanel();
        jp.setPreferredSize(dim1);
        jp.setBackground(Color.white);
        jf.add(jp,BorderLayout.EAST);
        jp.setLayout(new FlowLayout());
        

        //按钮监控类
        ConsoleListener consoleListener=new ConsoleListener(this,chess);
        
        //设置按钮数组
        String[] butname= {"开始","返回首页","悔棋","认输","保存局面","录像","读取局面","回放"};
        JButton[] button=new JButton[8];

        //依次把各个按钮组件加上去
        for(int i=0;i<butname.length;i++) {
            button[i]=new JButton(butname[i]);
            button[i].setPreferredSize(dim4);
            button[i].setBorder(new EmptyBorder(0,10,0,10));
            jp.add(button[i]);
        }


        //对每一个按钮都添加状态事件的监听处理机制
        for(int i=0;i<butname.length;i++) {
            button[i].addActionListener(consoleListener);//添加发生操作的监听方法
        }

        
        
        
        Dimension dim5=new Dimension(60,20);
        Dimension dim6=new Dimension(15,20);

        String[] boxname= {"玩家","AI(L1)","AI(L2)"};

        ConsoleBoxListener consoleBoxListener1=new ConsoleBoxListener(this,1);//控制黑棋
        JComboBox box=new JComboBox(boxname);
        box.setPreferredSize(dim5);
        box.addItemListener(consoleBoxListener1);
        jp.add(box);

        JLabel vsLabel=new JLabel("vs");
        vsLabel.setPreferredSize(dim6);
        jp.add(vsLabel);

        ConsoleBoxListener consoleBoxListener2=new ConsoleBoxListener(this,2);//控制白棋
        JComboBox box2=new JComboBox(boxname);
        box2.setPreferredSize(dim5);
        box2.addItemListener(consoleBoxListener2);
        jp.add(box2);
//
//        for(int i=0;i<userInfo.length;i++)
//            for (int j=0;j<userInfo[0].length;j++){
////                userInfo[i][j].setSize(dim5);
//                userInfo[i][j].setText("1");
//                jp.add(userInfo[i][j]);
//            }

        userInfoBlack=new JLabel("");
        userInfoWhite=new JLabel("");
        userInfoBlack.setFont(new Font("宋体",Font.BOLD, 10));
        userInfoWhite.setFont(new Font("宋体",Font.BOLD, 10));

        jp.add(userInfoBlack);
        jp.add(userInfoWhite);
//        cb.tip=new JLabel("");
//        cb.tip1=new JLabel("黑棋回合");
        tip=new JLabel("               提示:                 ");
        tip1=new JLabel("");
//        tip1.setPreferredSize(new Dimension(140,20));
//        tip1.setVerticalTextPosition(JLabel.TOP);//文字垂直对齐方式向上
//        tip1.setHorizontalTextPosition(JLabel.CENTER);

        tip2=new JLabel("");
        jp.add(tip);
        jp.add(tip1);
        jp.add(tip2);
        jf.setVisible(true);

    }

    public void setStartState(boolean startState){
        this.startState=startState;
    }
    public boolean getStartState(){
        return startState;
    }

    public void paint(Graphics g) {
        super.paint(g);//画出白框

        ShapeConfig sc=ShapeConfig.getInstance();
        int startX=sc.getX();
        int startY=sc.getY();
        int size=sc.getSize();
        int row=sc.getRow();
        int col=sc.getCol();
        UserSet userSet=new UserSet();
        //重绘出棋盘
        g.setColor(Color.black);
        for(int i=0;i<row;i++) {
            g.drawLine(startX, startY+size*i, startX+size*(col-1), startY+size*i);
        }
        for(int j=0;j<col;j++) {
            g.drawLine(startX+size*j, startY, startX+size*j, startY+size*(row-1));
        }

        //重绘出棋子
        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                if(chess.isAvail[i][j]==1) {
                    int countx=size*i+20;
                    int county=size*j+20;
                    g.setColor(Color.black);
                    g.fillOval(countx-size/2, county-size/2, size, size);
                }
                else if(chess.isAvail[i][j]==2) {
                    int countx=size*i+20;
                    int county=size*j+20;
                    g.setColor(Color.white);
                    g.fillOval(countx-size/2, county-size/2, size, size);
                }
            }
        }
        if (chess.getWinner() == 1) {
//                    System.out.println("黑方赢");
            tip2.setText("黑方赢");
            tip1.setText("");
            setStartState(false);
            if(userType[0]=="玩家"&&userType[1]!="玩家"){
                chess.user.addChessNumb();
                chess.user.addWinNum();
                userSet.updateUser(chess.user);
            } else if (userType[0]!="玩家"&&userType[1]=="玩家") {
                chess.user.addChessNumb();
                userSet.updateUser(chess.user);
            }

        }
        else if (chess.getWinner() == 2) {
//                    System.out.println("白方赢");
            tip2.setText("白方赢");
            tip1.setText("");
            setStartState(false);
            if(userType[0]!="玩家"&&userType[1]=="玩家"){
                chess.user.addChessNumb();
                chess.user.addWinNum();
                userSet.updateUser(chess.user);
            } else if (userType[0]=="玩家"&&userType[1]!="玩家") {
                chess.user.addChessNumb();
                userSet.updateUser(chess.user);
            }


        }
        else if (chess.getWinner()==-1) {
            tip2.setText("平局");
            tip1.setText("");
            setStartState(false);
            if(userType[0]!="玩家"&&userType[1]=="玩家"||userType[0]=="玩家"&&userType[1]!="玩家") {
                chess.user.addChessNumb();
                userSet.updateUser(chess.user);
            }
        }
        else if (chess.turn==1) {
            tip1.setText("黑棋回合");
            tip2.setText("");
        }
        else {
            tip1.setText("白棋回合");
            tip2.setText("");
        }
    }


}

class ConsoleBoxListener implements ItemListener{
    private ChessBoard cb;
    private int chessColor;//1代表黑，2代表白
    public ConsoleBoxListener(ChessBoard cb,int chessColor){
        this.cb=cb;
        this.chessColor=chessColor;
    }
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange()==ItemEvent.SELECTED) {
            cb.setUserType((e.getItem()).toString(),chessColor-1);
        }
        String[] userType=cb.getUserType();
        if(userType[0]!="玩家"&&userType[1]!="玩家")
            cb.tip1.setText("AI对战AI");
            cb.tip2.setText("");

    }
}

class ConsoleListener implements ActionListener {
    private ChessBoard cb;
    private Chess chess;
    private AI ai1;
    private AI ai2;

    public ConsoleListener(ChessBoard cb, Chess chess) {
        this.cb = cb;
        this.chess = chess;
    }

    //当界面发生操作时进行处理
    public void actionPerformed(ActionEvent e) {
        Graphics g=cb.getGraphics();
        int startX = ShapeConfig.getInstance().getX();
        int startY = ShapeConfig.getInstance().getY();
        int size = ShapeConfig.getInstance().getSize();
        int row=ShapeConfig.getInstance().getRow();
        int col=ShapeConfig.getInstance().getCol();
        if (e.getActionCommand().equals("开始")) {
            chess.startGame();
            //重绘出棋盘
            cb.paint(g);
            g.setColor(Color.black);
            for(int i=0;i<row;i++) {
                g.drawLine(startX, startY+size*i, startX+size*(col-1), startY+size*i);
            }
            for(int j=0;j<col;j++) {
                g.drawLine(startX+size*j, startY, startX+size*j, startY+size*(row-1));
            }
            cb.repaint();
            cb.setStartState(true);
            String[] userType=cb.getUserType();
            if(userType[0]!="玩家"&&userType[1]!="玩家") {
                if(userType[0]=="AI(L1)") {
                    ai1 = new AI_Level1(chess);
                    cb.userInfoBlack.setText("黑棋：AI(Level 1）");
                }
                else if(userType[0]=="AI(L2)") {
                    ai1 = new AI_Level2(chess);
                    cb.userInfoBlack.setText("黑棋：AI(Level 2）");

                }
                if(userType[1]=="AI(L1)") {
                    ai2 = new AI_Level1(chess);
                    cb.userInfoWhite.setText("白棋：AI(Level 1）");
                }
                else if(userType[1]=="AI(L2)") {
                    ai2 = new AI_Level2(chess);
                    cb.userInfoWhite.setText("白棋：AI(Level 2）");

                }
                while (true) {
                    int[] location1 = ai1.AI_move();
                    int[] location2 = ai2.AI_move();

                    if (location1[0] != -1) {
                        g.setColor(Color.black);
                        g.fillOval(location1[0]*size+startX-size/2, location1[1]*size+startY-size/2, size, size);
                        try {
                            TimeUnit.MILLISECONDS.sleep(300);
                        } catch (InterruptedException exception) {
                            exception.printStackTrace();
                        }
                    } else break;
                    if (location2[0] != -1) {
                        g.setColor(Color.white);
                        g.fillOval(location2[0]*size+startX-size/2, location2[1]*size+startY-size/2, size, size);
                        try {
                            TimeUnit.MILLISECONDS.sleep(300);
                        } catch (InterruptedException exception) {
                            exception.printStackTrace();
                        }
                    } else break;
                }
            } else if (userType[0]!="玩家"&&userType[1]=="玩家") {
                if(userType[0]=="AI(L1)")
                    ai1=new AI_Level1(chess);
                else ai1=new AI_Level2(chess);
                    g.setColor(Color.black);
                int[] location1 = ai1.AI_move();
                g.fillOval(location1[0]*size+startX-size/2, location1[1]*size+startY-size/2, size, size);

                String userInfoStr="白棋:"+chess.user.getUserID()+" 总场次:"+chess.user.getChessNumb()+" 胜场:"+chess.user.getWinNumb();
                cb.userInfoWhite.setText(userInfoStr);
                if(userType[0]=="AI(L1)") {
                    cb.userInfoBlack.setText("黑棋：AI(Level 1）");
                }
                else if(userType[0]=="AI(L2)") {
                    cb.userInfoBlack.setText("黑棋：AI(Level 2）");

                }
            } else if (userType[0]=="玩家"&&userType[1]!="玩家") {
                String userInfoStr="黑棋:"+chess.user.getUserID()+" 总场次:"+chess.user.getChessNumb()+" 胜场:"+chess.user.getWinNumb();
                cb.userInfoBlack.setText(userInfoStr);
                if(userType[1]=="AI(L1)") {
                    ai2 = new AI_Level1(chess);
                    cb.userInfoWhite.setText("白棋：AI(Level 1）");
                }
                else if(userType[1]=="AI(L2)") {
                    ai2 = new AI_Level2(chess);
                    cb.userInfoWhite.setText("白棋：AI(Level 2）");

                }

            }else {
                String userInfoStr1="黑棋:"+chess.user.getUserID()+" 总场次:"+chess.user.getChessNumb()+" 胜场:"+chess.user.getWinNumb();
                cb.userInfoBlack.setText(userInfoStr1);
                String userInfoStr2="白棋:"+chess.user.getUserID()+" 总场次:"+chess.user.getChessNumb()+" 胜场:"+chess.user.getWinNumb();
                cb.userInfoWhite.setText(userInfoStr2);
            }
        } else if (e.getActionCommand().equals("返回首页")) {
            cb.jf.setVisible(false);
            homepage gf = new homepage(chess.getUser());
            gf.showHomepage();

        }
        //判断当前点击的按钮是不是悔棋
        else if (e.getActionCommand().equals("悔棋")) {
            if (chess.repentance()) {
                String[] userType=cb.getUserType();
                //玩家vs AI悔棋要退两步
                if(userType[0]!="玩家"&&userType[1]=="玩家"||userType[0]=="玩家"&&userType[1]!="玩家")
                    chess.repentance();
                cb.repaint();
            } else {
//                System.out.println("不能悔棋!");
                cb.tip2.setText("悔棋不能超过三次");
            }
        } else if (e.getActionCommand().equals("认输")) {
            if (chess.getSurrenderer() == 1) {
                cb.tip2.setText("白棋赢"); //System.out.println("白方赢");
                cb.tip1.setText("");
                cb.setStartState(false);

            }
            else {
                cb.tip2.setText("黑棋赢");//System.out.println("黑方赢");
                cb.tip1.setText("");
                cb.setStartState(false);

            }
        } else if (e.getActionCommand().equals("保存局面")) {
            chess.saveChess();//System.out.println("保存成功!");
            cb.tip2.setText("保存局面成功");
        } else if (e.getActionCommand().equals("读取局面")) {
            chess.loadChess();
            cb.repaint();
            cb.tip2.setText("读取局面成功");
        } else if (e.getActionCommand().equals("录像")) {
            chess.saveChess();//System.out.println("保存成功!");
            cb.tip2.setText("保存录像成功");
        } else if (e.getActionCommand().equals("回放")) {

            int[][]cpl=chess.playBack();
            for (int i=0;i<cpl.length;i++){
                if(i%2==0)
                    g.setColor(Color.black);
                else g.setColor(Color.white);
                g.fillOval(cpl[i][0]*size+startX-size/2, cpl[i][1]*size+startY-size/2, size, size);
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
            cb.repaint();
            cb.tip2.setText("读取局面成功");
        }

    }

}

class BoardListener implements MouseListener {
    private ChessBoard cb;
    private Chess chess;
    private AI ai;
    //public int turn;//判断当前轮到谁了，1表示黑方，2表示白方
    //动态数组对象的实例化
    //public ArrayList<ChessPosition>ChessPositonList=new ArrayList<ChessPosition>();

    public BoardListener(ChessBoard cb,Chess chess) {
        this.cb=cb;
        this.chess=chess;
    }

    public void mouseClicked(MouseEvent e) {
        if(!cb.getStartState())
            return;
        ShapeConfig sc=ShapeConfig.getInstance();
        int startX=sc.getX();
        int startY=sc.getY();
        int size=sc.getSize();
        int row=sc.getRow();
        int col=sc.getCol();

        int x=e.getX();
        int y=e.getY();
        //计算棋子要落在棋盘的哪个交叉点上
        int countx=(x/size)*size+startX;
        int county=(y/size)*size+startY;
        Graphics g=cb.getGraphics();

        if(e.isMetaDown()) {
            if (chess.name == "围棋")
                if (((Go) chess).take((countx-startX)/size,(county-startY)/size)) {
//                    System.out.println("提子成功");
                    cb.repaint();
                    cb.tip2.setText("提子成功");

                }
                else cb.tip2.setText("无法提子");//System.out.println("无法提子");


        }
        else {
            if ((countx - startX) / size > row - 1 || (county - startY) / size > col - 1)
//                System.out.println("此处无法下棋子");
                cb.tip2.setText("此处无法下棋子！");
            else if (chess.canMove((countx - startX) / size, (county - startY) / size, row)) {
                if (chess.getTurn() == 1) {
                    g.setColor(Color.black);
                    cb.tip1.setText("白棋回合");
                    cb.tip2.setText("");
                } else {
                    g.setColor(Color.white);
                    cb.tip1.setText("黑棋回合");
                    cb.tip2.setText("");
                }
                g.fillOval(countx - size / 2, county - size / 2, size, size);
                chess.move((countx - startX) / size, (county - startY) / size, row);
                cb.repaint();

                String[] userType=cb.getUserType();
                if(userType[0]!="玩家"&&userType[1]=="玩家"||userType[0]=="玩家"&&userType[1]!="玩家") {
                    if(userType[0]=="AI(L1)"||userType[1]=="AI(L1)")
                        ai=new AI_Level1(chess);
                    else ai=new AI_Level2(chess);
                    if (chess.turn==1)
                        g.setColor(Color.black);
                    else g.setColor(Color.white);
                    int[] location1 = ai.AI_move();
                    if (location1[0] != -1) {
                        g.fillOval(location1[0]*size+startX-size/2, location1[1]*size+startY-size/2, size, size);
                        try {
                            TimeUnit.MILLISECONDS.sleep(1000);
                        } catch (InterruptedException exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        cb.tip2.setText("平局"); //System.out.println("白方赢");
                        cb.tip1.setText("");
                    }
                }


            }else {
                cb.tip2.setText("此处无法下棋子！");
            }
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
