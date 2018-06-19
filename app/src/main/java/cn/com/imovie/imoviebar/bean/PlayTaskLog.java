package cn.com.imovie.imoviebar.bean;

import java.util.Date;

public class PlayTaskLog {
	
	private int id;
	
	private Integer movieId;	
	
	private Integer playTimeLong;	

	private Integer existStatus;  //0 锟斤拷删锟斤拷 1 锟斤拷锟斤拷锟斤拷 2 锟斤拷锟秸诧拷	
	
	private Date createTime;
	
	private String name;
	
	private Integer timeLong;	
	
	private String area;
	
	private String lang;	

	private String directors;
	
	private String actors;	
	
	private Date pubDate;	
	
	private String times;
	
	private String shortDesc;
	
	private String category;
	
	private String poster;
	
	private String posterHash;	
	
	private String bigPoster;
	
	private String bigPosterHash;

    private String ewatchSn;

    private String ewatchName;

    private Date startTime;

    private String price;

    private Integer printStatus;//鎵撳嵃鐘舵�侊細0 涓嶆墦鍗� 1 鏈墦鍗� 2 宸叉墦鍗�

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public Integer getPlayTimeLong() {
		return playTimeLong;
	}

	public void setPlayTimeLong(Integer playTimeLong) {
		this.playTimeLong = playTimeLong;
	}

	public Integer getExistStatus() {
		return existStatus;
	}

	public void setExistStatus(Integer existStatus) {
		this.existStatus = existStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTimeLong() {
		return timeLong;
	}

	public void setTimeLong(Integer timeLong) {
		this.timeLong = timeLong;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getDirectors() {
		return directors;
	}

	public void setDirectors(String directors) {
		this.directors = directors;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getPosterHash() {
		return posterHash;
	}

	public void setPosterHash(String posterHash) {
		this.posterHash = posterHash;
	}

	public String getBigPoster() {
		return bigPoster;
	}

	public void setBigPoster(String bigPoster) {
		this.bigPoster = bigPoster;
	}

	public String getBigPosterHash() {
		return bigPosterHash;
	}

	public void setBigPosterHash(String bigPosterHash) {
		this.bigPosterHash = bigPosterHash;
	}

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public void setPrintStatus(Integer priceStatus){
        this.printStatus = priceStatus;
    }

    public Integer getPrintStatus(){
        return printStatus;
    }

    public void setEwatchSn(String ewatchSn){
        this.ewatchSn = ewatchSn;
    }

    public String getEwatchSn(){
        return ewatchSn;
    }

    public void setEwatchName(String ewatchName){
        this.ewatchName = ewatchName;
    }

    public String getEwatchName(){
        return ewatchName;
    }

    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    public Date getStartTime(){
        return startTime;
    }
}
