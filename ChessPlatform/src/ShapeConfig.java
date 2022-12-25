//单例模式设置各种形状
public class ShapeConfig {
    private static ShapeConfig instance = null;
    private int startX,startY,size,row,col;
    private ShapeConfig(int startX,int startY,int size,int row,int col){
        this.startX=startX;
        this.startY=startY;
        this.size=size;
        this.row=row;
        this.col=col;
    }
    public int getX(){
        return startX;
    }
    public int getY(){
        return startY;
    }
    public int getSize(){
        return size;
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }

    synchronized public static ShapeConfig getInstance() {
        return instance;
    }

    //只有第一次能设置
    synchronized public static void setShape(int startX,int startY,int size,int row,int col) {
//        if(instance == null){
//            instance = new ShapeConfig(startX,startY,size,row,col);
//        }
//        else {
//            System.out.println("单例模式类ShapeConfig已被初始化，无法重新初始化！");
//        }
        instance = new ShapeConfig(startX,startY,size,row,col);

    }
}

