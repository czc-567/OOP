import java.util.Random;

public class AI_Level1 implements AI{
    private Chess chess;
    private Random random; ;
    //        System.out.println(random.nextInt(2));
    public AI_Level1(Chess chess){
        this.chess=chess;
        this.random=new Random();
    }

    public int[] AI_move(){
        int[] result={-1,-1};
        int row=ShapeConfig.getInstance().getRow();
        int col=ShapeConfig.getInstance().getCol();
        int x=random.nextInt(row);
        int y=random.nextInt(col);

        if(chess.getWinner()!=0)
            return result;

        for (int dy=y;dy<col;dy++)
            if(chess.canMove(x,dy,row)) {
                chess.move(x, dy, row);
                result[0]=x;
                result[1]=dy;
                return result;
            }
        for (int dx=x+1;dx<row;dx++)
            for (int dy=0;dy<col;dy++)
                if(chess.canMove(dx,dy,row)) {
                    chess.move(dx, dy, row);
                    result[0]=dx;
                    result[1]=dy;
                    return result;
                }
        for (int dy=0;dy<y;dy++)
            if(chess.canMove(x,dy,row)) {
                chess.move(x, dy, row);
                result[0]=x;
                result[1]=dy;
                return result;
            }
        return result;
    }

}
