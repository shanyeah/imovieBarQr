package cn.com.imovie.imoviebar.bean;

import java.io.Serializable;

import cn.com.imovie.imoviebar.utils.NumberHelper;

/**
 * Created by 锟节斤拷平 on 2015/4/2.
 */
public class Ewatch implements Serializable{
    Integer id;
    String name;
    String sn;
    String ip;
    Integer port;
    Integer status;
    Integer playStatus;
    String movieName;
    String bigPoster;
    String price;


    public static int PLAY_STATUS_PLAY = 1;
    public static int PLAY_STATUS_FREE = 0;

    public boolean isFree(){
        return NumberHelper.getIntValue(this.playStatus,-1) == PLAY_STATUS_FREE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMovieName(String movieName){
        this.movieName = movieName;
    }

    public String getMovieName(){
        return this.movieName;
    }

    public void setBigPoster(String bigPoster){
        this.bigPoster = bigPoster;
    }

    public String getBigPoster(){
        return this.bigPoster;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return this.price;
    }

    public void setPlayStatus(Integer playStatus){
        this.playStatus = playStatus;
    }

    public Integer getPlayStatus(){
        return this.playStatus;
    }
}
