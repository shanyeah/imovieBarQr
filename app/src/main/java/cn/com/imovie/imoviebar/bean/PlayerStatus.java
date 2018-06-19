package cn.com.imovie.imoviebar.bean;

public class PlayerStatus {
	public int processStatus; //processStatus: 0 没锟叫达拷锟斤拷 1 准锟斤拷锟斤拷锟斤拷 2 锟斤拷始锟斤拷锟斤拷
	public int processReturn;
	public long startProcessTimeMillis;	
	public long putStatusTimeMillis;		
	
	private Integer id;
	private Integer status;
	private Integer playStatus;
	private String hash;
	private String path;
	private Integer timeLong;
	private Integer playPosition;
	private Long speed;
	private Long fileSize;
	
	
	
	//processType: 0 锟斤拷纱锟斤拷锟� 1 准锟斤拷锟斤拷锟斤拷 2 锟斤拷始锟斤拷锟斤拷
	public synchronized void process(int processType) {
		if (processType == 1) { //准锟斤拷锟斤拷锟斤拷
			startProcessTimeMillis = System.currentTimeMillis();
			processReturn = -1; //默锟较达拷锟斤拷失锟斤拷
			processStatus = 1;
		} else if (processType == 2) {
			if (processStatus == 1) {
				processStatus = 2;
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else { //锟斤拷纱锟斤拷锟�
			if (processStatus == 2) {
				this.notify();
			}
			processStatus = 0;
		}
	}
	
	public int getProcessReturn() {
		return processReturn;
	}
	public void setProcessReturn(int processReturn) {
		this.processReturn = processReturn;
	}



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
	public Long getSpeed() {
		return speed;
	}
	public void setSpeed(Long speed) {
		this.speed = speed;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
