package cn.com.imovie.imoviebar.bean;

import java.util.ArrayList;
import java.util.List;

public class MovieQueryCondition {

	private List<Moviecat> moviecatList = new ArrayList<Moviecat>();
	
	private List<Times> timesList=new ArrayList<Times>();
	
	private List<Area> areaList=new ArrayList<Area>();

	public List<Moviecat> getMoviecatList() {
		return moviecatList;
	}

	public void setMoviecatList(List<Moviecat> moviecatList) {
		this.moviecatList = moviecatList;
	}

	public List<Times> getTimesList() {
		return timesList;
	}

	public void setTimesList(List<Times> timesList) {
		this.timesList = timesList;
	}

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}
	
}
