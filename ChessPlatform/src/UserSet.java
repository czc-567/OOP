import java.io.*;
import java.util.ArrayList;

public class UserSet {
    private ArrayList<User> users=new ArrayList<User>();
    private String FileName="save/UserSet.txt";
    public UserSet(){
        loadUserSet();
    }
    public boolean isHaveUserID(User user){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUserID().equals(user.getUserID()))
                return true;
        }
        return false;
    }
    public User getUser(String ID){
        int i;
        for(i=0;i<users.size();i++){
            if(users.get(i).getUserID().equals(ID))
                break;
        }
        return users.get(i);
    }

    public boolean isLegalUser(User user){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUserID().equals(user.getUserID())&&users.get(i).getPassword().equals(user.getPassword()))
                return true;
        }
        return false;
    }
    public void loadUserSet(){
        try {
            users=new ArrayList<User>();
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(FileName)));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            int i=0;
            //按行读取
            while((line = bufferedReader.readLine() )!= null){
                if(line!=null){
                    //将按行读取的字符串按空格分割，得到一个string数组
                    String[] info = line.split("\\t");
                    User user=new User();
                    user.setInfo(info);
                    users.add(user);
                    //行数加1
                    i++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(User user,boolean append){
        loadUserSet();
        if(append)
            if(user.getUserID().equals("")||user.getPassword().equals("")||isHaveUserID(user))
                return false;
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(FileName, append);
            String[] info = user.outputInfo();
            for (int i=0;i<info.length;i++) {
                fwriter.write(info[i]);
                fwriter.write( "\t");
            }
            fwriter.write( "\r\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    public void updateUserSet(ArrayList<User> users){
        ArrayList<User> users2=new ArrayList<User>();
        for (int i=0;i<users.size();i++) {
            try {
                users2.add(users.get(i).clone());
            }
            catch (Exception e){
            }
        }
        addUser(users2.get(0),false);
        for(int i=1;i<users2.size();i++)
            addUser(users2.get(i),true);
    }

    public void updateUser(User user){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUserID().equals(user.getUserID())) {
                users.remove(i);
                users.add(user);
                break;
            }
        }
        updateUserSet(users);
    }

}
