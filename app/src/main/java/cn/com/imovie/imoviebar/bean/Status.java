package cn.com.imovie.imoviebar.bean;

import java.io.Serializable;

/**
 * Created by yujinping on 2015/3/11.
 */
public class Status implements Serializable{
    Integer id;
    Integer status;
    Integer playStatus;
    String hash;
    Integer timeLong;
    Integer playPosition;
    Integer playTaskId;
    Integer speed;
    Integer movieId;
    String movieName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(Integer playStatus) {
        this.playStatus = playStatus;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(Integer timeLong) {
        this.timeLong = timeLong;
    }

    public Integer getPlayPosition() {
        return playPosition;
    }

    public void setPlayPosition(Integer playPosition) {
        this.playPosition = playPosition;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getPlayTaskId() {
        return playTaskId;
    }

    public void setPlayTaskId(Integer playTaskId) {
        this.playTaskId = playTaskId;
    }
}
