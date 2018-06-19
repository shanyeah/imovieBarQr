package cn.com.imovie.imoviebar.bean;

import java.util.Date;
import java.util.List;

public class Movie {
	
	private Integer id;	
	
	private String name;
	
	private String enName;
	
	private String aliasName;
	
	private Integer timeLong;	
	
	private String area;
	
	private String lang;	

	private String director;
	
	private String actor;
	
	private Date pubDate;	
	
	private String times;
	
	private String shortDesc;
	
	private String movieDesc;
	
	private String category;
	
	private String poster;
	
	private String posterHash;	
	
	private String bigPoster;
	
	private String bigPosterHash;	
	
	private Date downloadDate;
	
	private Integer movieFavoriteId;


	public Integer getServiceCategoryId() {
		return serviceCategoryId;
	}

	public void setServiceCategoryId(Integer serviceCategoryId) {
		this.serviceCategoryId = serviceCategoryId;
	}

	public String getServiceCategoryName() {
		return serviceCategoryName;
	}

	public void setServiceCategoryName(String serviceCategoryName) {
		this.serviceCategoryName = serviceCategoryName;
	}

	private Integer serviceCategoryId;
	private String serviceCategoryName;


	private List<MoviePrice> moviePriceList;

	public List<MoviePrice> getMoviePriceList() {
		return moviePriceList;
	}

	public void setMoviePriceList(List<MoviePrice> moviePriceList) {
		this.moviePriceList = moviePriceList;
	}

	public Integer getMovieFavoriteId() {
		return movieFavoriteId;
	}

	public void setMovieFavoriteId(Integer movieFavoriteId) {
		this.movieFavoriteId = movieFavoriteId;
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

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
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

	public String getMovieDesc() {
		return movieDesc;
	}

	public void setMovieDesc(String movieDesc) {
		this.movieDesc = movieDesc;
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

	public Date getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}	
	

}
