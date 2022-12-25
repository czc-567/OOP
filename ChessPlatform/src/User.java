public class User {
    private String userID;
    private String password;
    private int chessNumb=0;
    private int winNumb=0;
    public User(){
    }
    public User(String userID,String password){
        this.userID=userID;
        this.password=password;
    }
    public String getUserID(){
        return userID;
    }
    public String getPassword(){
        return password;
    }
    public int getChessNumb(){return chessNumb;}
    public int getWinNumb(){return winNumb;}
    public void addChessNumb(){chessNumb++;}
    public void addWinNum(){winNumb++;}
    public String[] outputInfo(){
        String[] info=new String[4];
        info[0]=userID;
        info[1]=password;
        info[2]=chessNumb+"";
        info[3]=winNumb+"";
        return info;
    }

    public void setInfo(String[] info){
        userID=info[0];
        password=info[1];
        chessNumb=Integer.parseInt(info[2]);
        winNumb=Integer.parseInt(info[3]);
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        User user= new User();
        user.setInfo(this.outputInfo());
        return user;
    }
}
