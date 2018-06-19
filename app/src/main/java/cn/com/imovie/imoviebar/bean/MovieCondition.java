package cn.com.imovie.imoviebar.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MovieCondition {
	private String search;
	private Integer moviecatId;
	private String times;
	private String area;
	private Integer pageNo;
	private Integer pageSize;
	private Integer orderByType;
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public Integer getMoviecatId() {
		return moviecatId;
	}
	public void setMoviecatId(Integer moviecatId) {
		this.moviecatId = moviecatId;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getOrderByType() {
		return orderByType;
	}
	public void setOrderByType(Integer orderByType) {
		this.orderByType = orderByType;
	}

	public String toUrlString() {
		StringBuffer url=new StringBuffer("page_no="+pageNo+"&page_size="+pageSize+"&order_by_type="+orderByType);
		if (search != null) {
            try {
                url.append("&search=").append(URLEncoder.encode(search, "GB18030"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
		}
		if (moviecatId != null) {
			url.append("&moviecat_id="+moviecatId);
		}
		if (times != null) {
			try {
				url.append("&times=").append(URLEncoder.encode(times, "GB18030"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (area != null) {
			try {
				url.append("&area=").append(URLEncoder.encode(area, "GB18030"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return url.toString();
	}
}
