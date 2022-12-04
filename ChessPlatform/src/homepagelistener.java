import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class homepagelistener implements ActionListener, ItemListener {
    private JFrame jf;
    private int row=8;
    private boolean isGo=false;
    private Chess chess;
    private ChessBoard cb;
    public homepagelistener(JFrame jf){
        this.jf=jf;
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("开始游戏")) {
            if (isGo)
                chess=new Go();
            else
                chess =new GoBang();
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
            if (str=="围棋")
                isGo=true;
            else if (str=="五子棋")
                isGo=false;
            else
                row=Integer.valueOf(str.substring(0, str.indexOf("*")));
        }
    }
}
