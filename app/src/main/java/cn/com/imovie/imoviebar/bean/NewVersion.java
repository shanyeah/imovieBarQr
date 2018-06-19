package cn.com.imovie.imoviebar.bean;

import cn.com.imovie.imoviebar.utils.DateHelper;

import java.util.Date;

public class NewVersion {
	
	private int upgradeType;
	
	private String versionName;
	
	private Date releaseTime;
	
	private String downloadUrl;
	
	private Long fileSize;
	
	private String remark;

	public int getUpgradeType() {
		return upgradeType;
	}

	public void setUpgradeType(int upgradeType) {
		this.upgradeType = upgradeType;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer f=new StringBuffer();
		f.append("upgradeType:"+upgradeType).append("|");
		f.append("versionName:"+versionName).append("|");
		f.append("releaseTime:"+ DateHelper.toString(releaseTime, "yyyy-MM-dd HH:mm:ss")).append("|");
		f.append("fileSize:"+fileSize).append("|");
		f.append("downloadUrl:"+downloadUrl).append("|");
		f.append("remark:"+remark).append("|");
		return f.toString();
	}
	

}
