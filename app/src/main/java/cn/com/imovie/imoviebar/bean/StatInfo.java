package cn.com.imovie.imoviebar.bean;

import java.util.Date;

public class StatInfo {
	private Integer totalMovieQuantity;
	private Integer favoriteMovieQuantity;	
	private Float totalSpace;
	private Float usedSpace;
	private Float favoriteSpace;
	private Float freeSpace;	
	private Float favoriteSpacePercent;
	private String sn;
	private String releaseVersion;
	private Date expiredTime;
	
	
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getReleaseVersion() {
		return releaseVersion;
	}
	public void setReleaseVersion(String releaseVersion) {
		this.releaseVersion = releaseVersion;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public Integer getTotalMovieQuantity() {
		return totalMovieQuantity;
	}
	public void setTotalMovieQuantity(Integer totalMovieQuantity) {
		this.totalMovieQuantity = totalMovieQuantity;
	}
	public Integer getFavoriteMovieQuantity() {
		return favoriteMovieQuantity;
	}
	public void setFavoriteMovieQuantity(Integer favoriteMovieQuantity) {
		this.favoriteMovieQuantity = favoriteMovieQuantity;
	}
	public Float getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(Float totalSpace) {
		this.totalSpace = totalSpace;
	}
	public Float getUsedSpace() {
		return usedSpace;
	}
	public void setUsedSpace(Float usedSpace) {
		this.usedSpace = usedSpace;
	}
	public Float getFavoriteSpace() {
		return favoriteSpace;
	}
	public void setFavoriteSpace(Float favoriteSpace) {
		this.favoriteSpace = favoriteSpace;
	}
	public Float getFreeSpace() {
		return freeSpace;
	}
	public void setFreeSpace(Float freeSpace) {
		this.freeSpace = freeSpace;
	}
	public Float getFavoriteSpacePercent() {
		return favoriteSpacePercent;
	}
	public void setFavoriteSpacePercent(Float favoriteSpacePercent) {
		this.favoriteSpacePercent = favoriteSpacePercent;
	}	
	
	
}
