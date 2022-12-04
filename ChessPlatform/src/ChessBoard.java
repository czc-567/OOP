import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class ChessBoard extends JPanel{
    private Chess chess;
    public JLabel tip1,tip2;

    public ChessBoard(Chess chess){
        this.chess=chess;
    }

    public void initUI() {
        ShapeConfig sc=ShapeConfig.getInstance();
        int size=sc.getSize();
        int row=sc.getRow();
        int col=sc.getCol();
        //初始化一个界面,并设置标题大小等属性
        JFrame jf=new JFrame();
        jf.setTitle(chess.name);
        jf.setSize(size*row+20+150,size*row+20+20);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(3);

        jf.setLayout(new BorderLayout());

        Dimension dim1=new Dimension(150,0);
        Dimension dim3=new Dimension(size*row+20,0);
        Dimension dim2=new Dimension(140,40);

        this.setPreferredSize(dim3);
        this.setBackground(Color.LIGHT_GRAY);
        jf.add(this,BorderLayout.CENTER);

        //实现右边的JPanel容器界面
        JPanel jp=new JPanel();
        jp.setPreferredSize(dim1);
        jp.setBackground(Color.white);
        jf.add(jp,BorderLayout.EAST);
        jp.setLayout(new FlowLayout());

        //设置按钮数组
        String[] butname= {"开始新游戏","悔棋","认输","保存对局","读取对局"};
        JButton[] button=new JButton[5];

        //依次把各个按钮组件加上去
        for(int i=0;i<butname.length;i++) {
            button[i]=new JButton(butname[i]);
            button[i].setPreferredSize(dim2);
            jp.add(button[i]);
        }


        //按钮监控类
        ConsoleListener butListen=new ConsoleListener(this,chess);
        //对每一个按钮都添加状态事件的监听处理机制
        for(int i=0;i<butname.length;i++) {
            button[i].addActionListener(butListen);//添加发生操作的监听方法
        }

        JLabel tip=new JLabel("                提示:                ");
        tip1=new JLabel("黑棋回合");
//        tip1.setPreferredSize(new Dimension(140,20));
//        tip1.setVerticalTextPosition(JLabel.TOP);//文字垂直对齐方式向上
//        tip1.setHorizontalTextPosition(JLabel.CENTER);

        tip2=new JLabel("");
        jp.add(tip);
        jp.add(tip1);
        jp.add(tip2);
        jf.setVisible(true);
    }


    //重写重绘方法,这里重写的是第一个大的JPanel的方法
    public void paint(Graphics g) {
        super.paint(g);//画出白框

        ShapeConfig sc=ShapeConfig.getInstance();
        int startX=sc.getX();
        int startY=sc.getY();
        int size=sc.getSize();
        int row=sc.getRow();
        int col=sc.getCol();

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
        if (chess.turn==1)
            tip1.setText("黑棋回合");
        else tip1.setText("白棋回合");
        tip2.setText("");
    }


}


class ConsoleListener implements ActionListener {
    private ChessBoard cb;
    private Chess chess;

    public ConsoleListener(ChessBoard cb, Chess chess) {
        this.cb = cb;
        this.chess = chess;
    }

    //当界面发生操作时进行处理
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("开始新游戏")) {
            chess.startGame();
            cb.repaint();
        }
        //判断当前点击的按钮是不是悔棋
        else if (e.getActionCommand().equals("悔棋")) {
            if (chess.repentance()) {
                cb.repaint();
            } else {
//                System.out.println("不能悔棋!");
                cb.tip2.setText("悔棋不能超过三次");
            }
        } else if (e.getActionCommand().equals("认输")) {
            if (chess.getSurrenderer() == 1) {
                cb.tip2.setText("白棋赢"); //System.out.println("白方赢");
                cb.tip1.setText("");
            }
            else {
                cb.tip2.setText("黑棋赢");//System.out.println("黑方赢");
                cb.tip1.setText("");

            }
        } else if (e.getActionCommand().equals("保存对局")) {
            chess.saveChess("save");//System.out.println("保存成功!");
            cb.tip2.setText("保存成功");
        } else if (e.getActionCommand().equals("读取对局")) {
            chess.loadChess("save");
            cb.repaint();
            cb.tip2.setText("读取成功");
        }

    }
}

class BoardListener implements MouseListener {
    private ChessBoard cb;
    private Chess chess;
    //public int turn;//判断当前轮到谁了，1表示黑方，2表示白方
    //动态数组对象的实例化
    //public ArrayList<ChessPosition>ChessPositonList=new ArrayList<ChessPosition>();

    public BoardListener(ChessBoard cb,Chess chess) {
        this.cb=cb;
        this.chess=chess;
    }

    public void mouseClicked(MouseEvent e) {
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

                if (chess.getWinner() == 1) {
//                    System.out.println("黑方赢");
                    cb.tip2.setText("黑方赢");
                    cb.tip1.setText("");
                }
                else if (chess.getWinner() == 2) {
//                    System.out.println("白方赢");
                    cb.tip2.setText("白方赢");
                    cb.tip1.setText("");
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
