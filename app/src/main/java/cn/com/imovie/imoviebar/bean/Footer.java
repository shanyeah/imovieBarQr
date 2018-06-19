package cn.com.imovie.imoviebar.bean;

/**
 * Created by fwh on 15-8-11.
 */
public class Footer {
    private int id;
    private int yellow;
    private int white;
    private int msgCount=0;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setYellow(int yellow){
        this.yellow = yellow;
    }

    public int getYellow(){
        return yellow;
    }

    public void setWhite(int white){
        this.white = white;
    }

    public int getWhite(){
        return white;
    }

    public void setMsgCount(int msgCount){
        this.msgCount = msgCount;
    }

    public int getMsgCount(){
        return msgCount;
    }
}
