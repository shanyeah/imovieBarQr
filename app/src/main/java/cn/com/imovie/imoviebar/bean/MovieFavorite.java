package cn.com.imovie.imoviebar.bean;

import java.util.Date;

public class MovieFavorite {

	private int id;

	private Integer movieId;	
	
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
	
}
