public class Reversi extends Chess{
    public Reversi(User user){
        super.name="黑白棋";
        super.isAvail[3][3]=2;
        super.isAvail[4][4]=2;
        super.isAvail[3][4]=1;
        super.isAvail[4][3]=1;
        super.user=user;

    }
    public  void startGame(){
        for (int i = 0; i < isAvail.length; i++)
            for (int j = 0; j < isAvail.length; j++)
                isAvail[i][j]=0;
        for (int i = 0; i < ChessPositonList.length; i++) {
            ChessPositonList[i][0] = -1;
            ChessPositonList[i][1] = -1;
        }
        turn=1;
        numbers=0;
        winner=0;
        initRepentance();
        isAvail[3][3]=2;
        isAvail[4][4]=2;
        isAvail[3][4]=1;
        isAvail[4][3]=1;


    }

    public boolean canMove(int x,int y,int row) {
        if (isAvail[x][y] > 0) {
            return false;//已有棋子
        } else{
            int[][] direction={{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
            for(int i=0;i<direction.length;i++){
                //
                int dx=x+direction[i][0];
                int dy=y+direction[i][1];
                if(dx<8&&dx>=0&&dy<8&&dy>=0) {
                    if (turn + isAvail[dx][dy] == 3) {
                        do {
                            dx += direction[i][0];
                            dy += direction[i][1];
                        }
                        while (dx<8&&dx>=0&&dy<8&&dy>=0&&turn + isAvail[dx][dy] == 3);
                        if (dx<8&&dx>=0&&dy<8&&dy>=0&&isAvail[dx][dy] == turn) return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean move(int x,int y,int row){
        int[][] direction={{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
        for(int i=0;i<direction.length;i++){
            //
            int dx=x+direction[i][0];
            int dy=y+direction[i][1];
            if(dx<8&&dx>=0&&dy<8&&dy>=0) {
                if (turn + isAvail[dx][dy] == 3) {
                    do {
                        dx += direction[i][0];
                        dy += direction[i][1];
                    }
                    while (dx<8&&dx>=0&&dy<8&&dy>=0&&turn + isAvail[dx][dy] == 3);
                    //判断是否满足夹的条件

                    if (dx<8&&dx>=0&&dy<8&&dy>=0&&isAvail[dx][dy] == turn) {
                        dx -= direction[i][0];
                        dy -= direction[i][1];
                        while (dx != x || dy != y) {
                            isAvail[dx][dy] = turn;
                            dx -= direction[i][0];
                            dy -= direction[i][1];
                        }
                    }

                }
            }
        }
        isAvail[x][y]=turn;
        ChessPositonList[numbers][0]=x;
        ChessPositonList[numbers][1]=y;
        numbers++;
        if(turn==1) turn++;
        else turn--;
        judgeWinner();
        return true;
    }

    public boolean isHaveCanMove(){
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (canMove(i,j,8)) {
                    return true;
                }
            }
        return false;
    }

    public void judgeWinner(){
        //当前颜色的棋没有合法棋步可下
        if(!isHaveCanMove()){

            //轮到另一颜色棋
            if(turn==1) turn++;
            else turn--;
            //另一颜色棋也没有合法棋步可下，则判断胜负
            if(!isHaveCanMove()) {
                int whiteSum = 0, blackSum = 0;
                for (int i = 0; i < 8; i++)
                    for (int j = 0; j < 8; j++) {
                        if (isAvail[i][j] == 1) blackSum++;
                        else if (isAvail[i][j] == 2) whiteSum++;
                    }
                if (blackSum > whiteSum) {
                    winner = 1;
                } else if (blackSum < whiteSum) {
                    winner = 2;
                } else {
                    winner = -1;
                }
            }
        }


    }

}
