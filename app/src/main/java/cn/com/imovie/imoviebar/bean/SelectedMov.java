package cn.com.imovie.imoviebar.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by glin on 1/25/16.
 */


//选择的影片信息
public class SelectedMov implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;
    private int movID;
    private String movName;
    private int timeLens;

    public int getMovType() {
        return movType;
    }

    public void setMovType(int movType) {
        this.movType = movType;
    }

    private int movType;

    public HashMap<Integer,Float> getPriceList() {
        return priceList;
    }

    public void setPriceList(HashMap<Integer,Float> priceList) {
        this.priceList = priceList;
    }

    private HashMap<Integer,Float> priceList;






    public int getMovID() {
        return movID;
    }

    public void setMovID(int movID) {
        this.movID = movID;
    }

    public String getMovName() {
        return movName;
    }

    public void setMovName(String movName) {
        this.movName = movName;
    }

    public int getTimeLens() {
        return timeLens;
    }

    public void setTimeLens(int timeLens) {
        this.timeLens = timeLens;
    }
    //    private double price;

    public SelectedMov(int movID, String movName, int timeLens) {
        this.movID = movID;
        this.movName = movName;
        this.timeLens = timeLens;
    }

    public SelectedMov(int movID, String movName, int timeLens, int movType, HashMap<Integer,Float> priceList) {
        this.movID = movID;
        this.movName = movName;
        this.timeLens = timeLens;
        this.movType = movType;
        this.priceList = priceList;

    }

    public SelectedMov() {
    }
}
