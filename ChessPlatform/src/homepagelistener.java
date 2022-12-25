import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class homepagelistener implements ActionListener, ItemListener {
    private JFrame jf;
    private JPanel jp;
    private int row=8;
    private String chessType="五子棋";
    private Chess chess;
    private ChessBoard cb;
    private User user;
    public homepagelistener(JFrame jf,JPanel jp,User user){
        this.jf=jf;
        this.jp=jp;
        this.user=user;
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("开始游戏")) {
            if (chessType=="围棋")
                chess=new Go(user);
            else if(chessType=="五子棋")
                chess =new GoBang(user);
            else
                chess=new Reversi(user);
            ShapeConfig.setShape(20, 20, 40, row, row);
            cb = new ChessBoard(chess);
            jf.setVisible(false);
            cb.initUI();
            BoardListener bl=new BoardListener(cb,chess);
//            bl.setGraphics(cb);//获取画笔对象
            cb.addMouseListener(bl);

        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange()==ItemEvent.SELECTED) {
            String str=(e.getItem()).toString();
            //判断是选择棋的类型还是选择棋盘东西
            if (str=="围棋"||str=="五子棋") {
                chessType = str;
                 jp.getComponent(3).setVisible(true);
                 jp.getComponent(4).setVisible(false);

            }
            else if (str=="黑白棋") {
                chessType=str;
                String[] boxname1= {"五子棋","围棋","黑白棋"};
                jp.getComponent(3).setVisible(false);
                jp.getComponent(4).setVisible(true);
            } else
                row=Integer.valueOf(str.substring(0, str.indexOf("*")));
        }
    }
}

