import java.util.HashMap;
import java.util.Random;

public class AI_Level2 implements AI{
    private Chess chess;
    private int[][] weightArray=new int[ShapeConfig.getInstance().getRow()][ShapeConfig.getInstance().getCol()];
    public AI_Level2(Chess chess){
        this.chess=chess;
    }

    public int[] AI_move(){
        int[] result={-1,-1};
        int row=ShapeConfig.getInstance().getRow();
        int col=ShapeConfig.getInstance().getCol();

        if(chess.getWinner()!=0)
            return result;
        if(chess.numbers==0){
            result[0]=row/2;
            result[1]=col/2;
            chess.move(result[0],result[1],row);
            return result;
        }

        for(int i=0;i<row;i++)
            for(int j=0;j<col;j++)
                weightArray[i][j]=0;

        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                //首先判断当前位置是否为空
                if(chess.isAvail[i][j]==0) {
                    //往左延伸
                    String ConnectType="0";
                    int jmin=Math.max(0, j-4);
                    for(int positionj=j-1;positionj>=jmin;positionj--) {
                        //依次加上前面的棋子
                        ConnectType=ConnectType+chess.isAvail[i][positionj];
                    }
                    //从数组中取出相应的权值，加到权值数组的当前位置中
                    Integer valueleft=map.get(ConnectType);
                    if(valueleft!=null) weightArray[i][j]+=valueleft;

                    //往右延伸
                    ConnectType="0";
                    int jmax=Math.min(14, j+4);
                    for(int positionj=j+1;positionj<=jmax;positionj++) {
                        //依次加上前面的棋子
                        ConnectType=ConnectType+chess.isAvail[i][positionj];
                    }
                    //从数组中取出相应的权值，加到权值数组的当前位置中
                    Integer valueright=map.get(ConnectType);
                    if(valueright!=null) weightArray[i][j]+=valueright;

                    //联合判断，判断行
                    weightArray[i][j]+=unionWeight(valueleft,valueright);

                    //往上延伸
                    ConnectType="0";
                    int imin=Math.max(0, i-4);
                    for(int positioni=i-1;positioni>=imin;positioni--) {
                        //依次加上前面的棋子
                        ConnectType=ConnectType+chess.isAvail[positioni][j];
                    }
                    //从数组中取出相应的权值，加到权值数组的当前位置中
                    Integer valueup=map.get(ConnectType);
                    if(valueup!=null) weightArray[i][j]+=valueup;

                    //往下延伸
                    ConnectType="0";
                    int imax=Math.min(14, i+4);
                    for(int positioni=i+1;positioni<=imax;positioni++) {
                        //依次加上前面的棋子
                        ConnectType=ConnectType+chess.isAvail[positioni][j];
                    }
                    //从数组中取出相应的权值，加到权值数组的当前位置中
                    Integer valuedown=map.get(ConnectType);
                    if(valuedown!=null) weightArray[i][j]+=valuedown;

                    //联合判断，判断列
                    weightArray[i][j]+=unionWeight(valueup,valuedown);

                    //往左上方延伸,i,j,都减去相同的数
                    ConnectType="0";
                    for(int position=-1;position>=-4;position--) {
                        if((i+position>=0)&&(i+position<=14)&&(j+position>=0)&&(j+position<=14))
                            ConnectType=ConnectType+chess.isAvail[i+position][j+position];
                    }
                    //从数组中取出相应的权值，加到权值数组的当前位置
                    Integer valueLeftUp=map.get(ConnectType);
                    if(valueLeftUp!=null) weightArray[i][j]+=valueLeftUp;

                    //往右下方延伸,i,j,都加上相同的数
                    ConnectType="0";
                    for(int position=1;position<=4;position++) {
                        if((i+position>=0)&&(i+position<=14)&&(j+position>=0)&&(j+position<=14))
                            ConnectType=ConnectType+chess.isAvail[i+position][j+position];
                    }
                    //从数组中取出相应的权值，加到权值数组的当前位置
                    Integer valueRightDown=map.get(ConnectType);
                    if(valueRightDown!=null) weightArray[i][j]+=valueRightDown;

                    //联合判断，判断行
                    weightArray[i][j]+=unionWeight(valueLeftUp,valueRightDown);

                    //往左下方延伸,i加,j减
                    ConnectType="0";
                    for(int position=1;position<=4;position++) {
                        if((i+position>=0)&&(i+position<=14)&&(j-position>=0)&&(j-position<=14))
                            ConnectType=ConnectType+chess.isAvail[i+position][j-position];
                    }
                    //从数组中取出相应的权值，加到权值数组的当前位置
                    Integer valueLeftDown=map.get(ConnectType);
                    if(valueLeftDown!=null) weightArray[i][j]+=valueLeftDown;

                    //往右上方延伸,i减,j加
                    ConnectType="0";
                    for(int position=1;position<=4;position++) {
                        if((i-position>=0)&&(i-position<=14)&&(j+position>=0)&&(j+position<=14))
                            ConnectType=ConnectType+chess.isAvail[i-position][j+position];
                    }
                    //从数组中取出相应的权值，加到权值数组的当前位置
                    Integer valueRightUp=map.get(ConnectType);
                    if(valueRightUp!=null) weightArray[i][j]+=valueRightUp;

                    //联合判断，判断行
                    weightArray[i][j]+=unionWeight(valueLeftDown,valueRightUp);
                }
            }
        }

        //取出最大的权值
        int AIi=-1,AIj=-1;
        int weightmax=-1;
        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                if(weightmax<weightArray[i][j]&&chess.canMove(i,j,row)) {
                    weightmax=weightArray[i][j];
                    AIi=i;
                    AIj=j;
                }
            }
        }
        chess.move(AIi,AIj,row);
        result[0]=AIi;
        result[1]=AIj;
        return result;


    }
    //棋子相连情况的划分

    public Integer unionWeight(Integer a,Integer b ) {
        //必须要先判断a,b两个数值是不是null
        if ((a == null) || (b == null)) return 0;
            //一一
        else if ((a >= 10) && (a <= 25) && (b >= 10) && (b <= 25)) return 60;
            //一二、二一
        else if (((a >= 10) && (a <= 25) && (b >= 60) && (b <= 80)) || ((a >= 60) && (a <= 80) && (b >= 10) && (b <= 25)))
            return 800;
            //一三、三一、二二
        else if (((a >= 10) && (a <= 25) && (b >= 140) && (b <= 1000)) || ((a >= 140) && (a <= 1000) && (b >= 10) && (b <= 25)) || ((a >= 60) && (a <= 80) && (b >= 60) && (b <= 80)))
            return 3000;
            //二三、三二
        else if (((a >= 60) && (a <= 80) && (b >= 140) && (b <= 1000)) || ((a >= 140) && (a <= 1000) && (b >= 60) && (b <= 80)))
            return 3000;
        else return 0;
    }

    public static HashMap<String,Integer> map = new HashMap<String,Integer>();//设置不同落子情况和相应权值的数组
    static {

        //被堵住
        map.put("01", 17);//眠1连
        map.put("02", 12);//眠1连
        map.put("001", 17);//眠1连
        map.put("002", 12);//眠1连
        map.put("0001", 17);//眠1连
        map.put("0002", 12);//眠1连

        map.put("0102",17);//眠1连，15
        map.put("0201",12);//眠1连，10
        map.put("0012",15);//眠1连，15
        map.put("0021",10);//眠1连，10
        map.put("01002",19);//眠1连，15
        map.put("02001",14);//眠1连，10
        map.put("00102",17);//眠1连，15
        map.put("00201",12);//眠1连，10
        map.put("00012",15);//眠1连，15
        map.put("00021",10);//眠1连，10

        map.put("01000",21);//活1连，15
        map.put("02000",16);//活1连，10
        map.put("00100",19);//活1连，15
        map.put("00200",14);//活1连，10
        map.put("00010",17);//活1连，15
        map.put("00020",12);//活1连，10
        map.put("00001",15);//活1连，15
        map.put("00002",10);//活1连，10

        //被堵住
        map.put("0101",65);//眠2连，40
        map.put("0202",60);//眠2连，30
        map.put("0110",65);//眠2连，40
        map.put("0220",60);//眠2连，30
        map.put("011",65);//眠2连，40
        map.put("022",60);//眠2连，30
        map.put("0011",65);//眠2连，40
        map.put("0022",60);//眠2连，30

        map.put("01012",65);//眠2连，40
        map.put("02021",60);//眠2连，30
        map.put("01102",65);//眠2连，40
        map.put("02201",60);//眠2连，30
        map.put("00112",65);//眠2连，40
        map.put("00221",60);//眠2连，30

        map.put("01010",75);//活2连，40
        map.put("02020",70);//活2连，30
        map.put("01100",75);//活2连，40
        map.put("02200",70);//活2连，30
        map.put("00110",75);//活2连，40
        map.put("00220",70);//活2连，30
        map.put("00011",75);//活2连，40
        map.put("00022",70);//活2连，30

        //被堵住
        map.put("0111",150);//眠3连，100
        map.put("0222",140);//眠3连，80

        map.put("01112",150);//眠3连，100
        map.put("02221",140);//眠3连，80

        map.put("01101",1000);//活3连，130
        map.put("02202",800);//活3连，110
        map.put("01011",1000);//活3连，130
        map.put("02022",800);//活3连，110
        map.put("01110", 1000);//活3连
        map.put("02220", 800);//活3连

        map.put("01111",3000);//4连，300
        map.put("02222",3500);//4连，280
    }
}

