import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Chess {
    protected int[][] isAvail=new int [19][19];//记录棋盘
    protected int[][] ChessPositonList=new int [19*19][2];//记录每一步的落子情况
    protected int numbers=0;//记录总落子次数
    protected int turn=1;//1表示轮到黑方,2表示白
    protected int winner=0;
    protected String name;
    private int blackRepentance=0;//黑棋悔棋次数
    private int whiteRepentance=0;//白棋悔棋次数
    private int maxRepentance=3;//最多悔棋次数
    protected User user;
    private String filenama="save//";

    public User getUser(){
        return user;
    }
    public int  getWinner(){return winner;}
    public int getTurn(){
        return turn;
    }

    public void initRepentance(){
        blackRepentance=0;
        whiteRepentance=0;
    }
    public Chess() {
        for (int i = 0; i < ChessPositonList.length; i++) {
            ChessPositonList[i][0] = -1;
            ChessPositonList[i][1] = -1;

        }
    }
    //重新开始游戏


    //悔棋
    public boolean repentance(){
        if(numbers>0&&((turn==1&&whiteRepentance<maxRepentance)||(turn==2&&blackRepentance<maxRepentance))) {

            //所有信息还原至上一步
            numbers--;
            isAvail[ChessPositonList[numbers][0]][ChessPositonList[numbers][1]]=0;
            ChessPositonList[numbers][0]=-1;
            ChessPositonList[numbers][1]=-1;
            if(turn==1) {
                turn++;
                whiteRepentance++;
            }
            else {
                turn--;
                blackRepentance++;
            }


            return true;//正常悔棋

        } else {
            return false;//悔棋失败
        }
    }

    //获取认输者
    public int getSurrenderer(){
        return turn;
    }
    public void saveChess(){
        saveArray(isAvail, filenama+user.getUserID()+"_isAvail.txt");
        saveArray(ChessPositonList,filenama+user.getUserID()+"_ChessPositonList.txt");
    }

    public void loadChess(){
        isAvail=readArray(filenama+user.getUserID()+"_isAvail.txt");
        ChessPositonList=readArray(filenama+user.getUserID()+"_ChessPositonList.txt");
        numbers=0;
        for (int i=0;i<ChessPositonList.length;i++)
            if (ChessPositonList[i][0]!=-1)
                numbers++;
        if (numbers%2==0) turn=1;
        else turn=2;
    }
    public int[][] playBack(){
        loadChess();
        int[][] result=new int[numbers][2];
        for(int i=0;i<numbers;i++) {
            result[i][0]=ChessPositonList[i][0];
            result[i][1]=ChessPositonList[i][1];

        }
        return result;
    }

    public boolean isHaveChess(int x,int y){
        if (isAvail[x][y]>0) return true;
        else return false;
    }

    public void saveArray(int[][] array,String filename){
        //1.创建字符输出流
        FileWriter writeFile = null;
        try {
            //2.数据想写入的路径及文件
            File file = new File(filename);
            //3.如果该文件不存在，就创建
            if(!file.exists()) {
                file.createNewFile();
            }
            //4.给字节输出流赋予实例
            writeFile = new FileWriter(file);
            //5.通过循环将数组写入txt文件中
            for(int i = 0; i < array.length; i++) {
                //6.数据前n - 1列尾部加入","
                for(int j = 0; j < array[0].length - 1; j++) {
                    writeFile.write(array[i][j] + ",");
                }
                //7.数组最后一列后面不加","
                writeFile.write(array[i][array[0].length - 1] + "");
                //8.加上换行符
                writeFile.write("\n");
            }
            //9.把writeFile里的数据全部刷新一次，全部写入文件中
            writeFile.flush();
        } catch (Exception e) {//10.异常捕获
            e.printStackTrace();
        } finally {
            try {
                //11.如果writeFile不为空，就将其关闭
                if(writeFile != null)
                    writeFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public int[][] readArray(String filename) {
        //1.声明一个字符输入流
        FileReader reader = null;
        //2.声明一个字符输入缓冲流
        BufferedReader readerBuf = null;
        //3.声明一个二维数组
        int[][] array = null;
        try {
            //4.指定reader的读取路径
            reader = new FileReader(filename);
            //5.通过BufferedReader包装字符输入流
            readerBuf = new BufferedReader(reader);
            //6.创建一个集合，用来存放读取的文件的数据
            List<String> strList = new ArrayList<>();
            //7.用来存放一行的数据
            String lineStr;
            //8.逐行读取txt文件中的内容
            while((lineStr = readerBuf.readLine()) != null) {
                //9.把读取的行添加到list中
                strList.add(lineStr);
            }
            //10.获取文件有多少行
            int lineNum = strList.size();
            //11.获取数组有多少列
            String s =  strList.get(0);
            int columnNum = s.split("\\,").length;
            //12.根据文件行数创建对应的数组
            array = new int[strList.size()][columnNum];
            //13.记录输出当前行
            int count = 0;
            //14.循环遍历集合，将集合中的数据放入数组中
            for(String str : strList) {
                //15.将读取的str按照","分割，用字符串数组来接收
                String[] strs = str.split("\\,");
                for(int i = 0; i < columnNum; i++) {
                    array[count][i] = Integer.valueOf(strs[i]);
                }
                //16.将行数 + 1
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //17.关闭字符输入流
            try {
                if(reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //18.关闭字符输入缓冲流
            try {
                if(readerBuf != null)
                    readerBuf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //19.返回稀疏数组
        return array;
    }

    public abstract boolean canMove(int x,int y,int row);
    public abstract boolean move(int x,int y,int row);

    public abstract void startGame();
}
