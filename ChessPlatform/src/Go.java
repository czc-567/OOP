public class Go extends Chess {

    private int[][] visit=new int[19][19];
    public Go(){
        super.name="围棋";
    }
    //提子
    public boolean take(int x, int y) {
        cleanVisit();
        if(isAvail[x][y]<=0)
            return  false;//该位置没有棋子
        else if(haveLiberty(x,y))
            return  false;//无法提还有气的棋子
        else {
            isAvail[x][y]=-isAvail[x][y];//-1代表黑子不能下，白字能下
        }
        return true;
    }

    public boolean canMove(int x,int y,int row) {
        if(isAvail[x][y]+turn==0) {
            return false;//棋子无法下到无气的地方
        }else if(isAvail[x][y]>0) {
            return false;//此处已经有棋子了
        } else return true;
    }
    //下子
    public boolean move(int x, int y, int row){
        if(canMove(x,y,row)){
            isAvail[x][y]=turn;
            //把当前所下的棋子位置保存在动态数组中
            ChessPositonList[numbers][0]=x;
            ChessPositonList[numbers][1]=y;
            numbers++;
            if(turn==1) turn++;
            else turn--;

        }
        return true;
    }


    public void cleanVisit(){
        for (int i=0;i<19;i++)
            for (int j=0;j<19;j++)
                visit[i][j]=0;
    }
    public boolean haveLiberty(int x,int y){
        boolean isalive_flag=false;
        visit[x][y]=1;
        int[][] direction={{x+1,y},{x,y+1},{x-1,y},{x,y-1}};
        for(int i=0;i<4;i++){
            int dx=direction[i][0];
            int dy=direction[i][1];
            if ((dx>=0)&&(dx<ShapeConfig.getInstance().getRow())&&(dy>=0)&&(dy<ShapeConfig.getInstance().getRow())&&(visit[dx][dy]==0)){
                if(isAvail[dx][dy]==0)
                    return true;
                else if(isAvail[dx][dy]==isAvail[x][y])
                    isalive_flag=(isalive_flag||haveLiberty(dx,dy));
                else haveLiberty(dx,dy);
            }

        }
        return isalive_flag;
    }
}
