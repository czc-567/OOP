import java.awt.*;

public class GoBang extends Chess{


    public GoBang(User user){
        super.name="五子棋";
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
    }
    public boolean canMove(int x,int y,int row) {
        if (isAvail[x][y] > 0) {
            return false;//已有棋子
        } else return true;
    }
    public boolean move(int x,int y,int row){
        if(canMove(x, y,row)){
            //计算棋盘上棋子在数组中相应的位置
            //黑
            if(turn==1) {
                //先获取要落的地方
//                g.setColor(Color.black);
                //落子
//                g.fillOval(countx-size/2, county-size/2, size, size);
                //设置当前位置已经有棋子了,棋子为黑子
                isAvail[x][y]=1;
                //把当前所下的棋子位置保存在动态数组中
//                gf.ChessPositonList.add(new ChessPosition(x,y));
                ChessPositonList[numbers][0]=x;
                ChessPositonList[numbers][1]=y;
                numbers++;
                turn++;

                //判断是否已经出现五科棋子了
                //列判断
                //首先界定数组范围，防止越界
                int imin=x-4,imax=x+4;
                if(imin<0) imin=0;
                if(imax>row-1) imax=row-1;
                int count1=0;//判断相连的棋子数
                for(int i=imin;i<=imax;i++) {
                    if(isAvail[i][y]==1) count1++;
                        //如果出现了其他棋子，或者是没有棋子时，就重新开始计数
                    else count1=0;
                    if(count1==5) {
                        winner=1;
                    }
                }
                //行判断
                //首先界定数组范围，防止越界
                int jmin=y-4,jmax=y+4;
                if(jmin<0) jmin=0;
                if(jmax>row-1) jmax=row-1;
                int count2=0;//判断相连的棋子数
                for(int j=jmin;j<=jmax;j++) {
                    if(isAvail[x][j]==1) count2++;
                    else count2=0;
                    if(count2==5) {
                        winner=1;
                    }
                    //如果出现了其他棋子，或者是没有棋子时，就重新开始计数

                }
                //135度判断
                //首先界定数组范围，防止越界
                int count3=0;//判断相连的棋子数
                for(int i=-4;i<=4;i++) {
                    if((x+i>=0)&&(y+i>=0)&&(x+i<=row-1)&&(y+i<=row-1)) {
                        if(isAvail[x+i][y+i]==1) count3++;
                            //如果出现了其他棋子，或者是没有棋子时，就重新开始计数
                        else count3=0;
                        if(count3==5) {
                            winner=1;
                        }
                    }
                }
                int count4=0;//判断相连的棋子数
                for(int i=-4;i<=4;i++) {
                    if((x+i>=0)&&(y-i>=0)&&(x+i<=row-1)&&(y-i<=row-1)) {
                        //System.out.print("count4:"+count4);
                        if(isAvail[x+i][y-i]==1) count4++;
                            //如果出现了其他棋子，或者是没有棋子时，就重新开始计数
                        else count4=0;
                        if(count4==5) {
                            winner=1;
                        }
                    }
                }
            }
            else {//白
//                g.setColor(Color.white);
//                g.fillOval(countx-size/2, county-size/2, size, size);
                //设置当前位置已经有棋子了，棋子为白子
//                gf.ChessPositonList.add(new ChessPosition(x,y));
                ChessPositonList[numbers][0]=x;
                ChessPositonList[numbers][1]=y;
                numbers++;
                isAvail[x][y]=2;
                turn--;

                //列判断
                //首先界定数组范围，防止越界
                int imin=x-4,imax=x+4;
                if(imin<0) imin=0;
                if(imax>row-1) imax=row-1;
                int count1=0;//判断相连的棋子数
                for(int i=imin;i<=imax;i++) {
                    if(isAvail[i][y]==2) count1++;
                        //如果出现了其他棋子，或者是没有棋子时，就重新开始计数
                    else count1=0;
                    if(count1==5) {
                        winner=2;
                    }
                }
                //行判断
                //首先界定数组范围，防止越界
                int jmin=y-4,jmax=y+4;
                if(jmin<0) jmin=0;
                if(jmax>row-1) jmax=row-1;
                int count2=0;//判断相连的棋子数
                for(int j=jmin;j<=jmax;j++) {
                    if(isAvail[x][j]==2) count2++;
                        //如果出现了其他棋子，或者是没有棋子时，就重新开始计数
                    else count2=0;
                    if(count2==5) {
                        winner=2;
                    }

                }
                //135度判断
                //首先界定数组范围，防止越界
                int count3=0;//判断相连的棋子数
                for(int i=-4;i<=4;i++) {
                    if((x+i>=0)&&(y+i>=0)&&(x+i<=row-1)&&(y+i<=row-1)) {
                        if(isAvail[x+i][y+i]==2) count3++;
                            //如果出现了其他棋子，或者是没有棋子时，就重新开始计数
                        else count3=0;
                        if(count3==5) {
                            winner=2;
                        }
                    }
                }
                int count4=0;//判断相连的棋子数
                for(int i=-4;i<=4;i++) {
                    if((x+i>=0)&&(y-i>=0)&&(x+i<=row-1)&&(y-i<=row-1)) {
                        if(isAvail[x+i][y-i]==2) count4++;
                            //如果出现了其他棋子，或者是没有棋子时，就重新开始计数
                        else count4=0;
                        if(count4==5) {
                            winner=2;
                        }
                    }
                }
            }

            if(numbers==ShapeConfig.getInstance().getRow()*ShapeConfig.getInstance().getCol())
                winner=-1;
        }
        return true;
    }
}
