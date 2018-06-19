package cn.com.imovie.imoviebar.http;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import cn.com.imovie.imoviebar.bean.Area;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.ErrorInfo;
import cn.com.imovie.imoviebar.bean.Ewatch;
import cn.com.imovie.imoviebar.bean.Guide;
import cn.com.imovie.imoviebar.bean.LspiStatus;
import cn.com.imovie.imoviebar.bean.Movie;
import cn.com.imovie.imoviebar.bean.MovieCategory;
import cn.com.imovie.imoviebar.bean.MovieDownload;
import cn.com.imovie.imoviebar.bean.MovieFavorite;
import cn.com.imovie.imoviebar.bean.MovieQueryCondition;
import cn.com.imovie.imoviebar.bean.Moviecat;
import cn.com.imovie.imoviebar.bean.NewVersion;
import cn.com.imovie.imoviebar.bean.Page;
import cn.com.imovie.imoviebar.bean.PlayTask;
import cn.com.imovie.imoviebar.bean.PlayTaskLog;
import cn.com.imovie.imoviebar.bean.PlayTaskLogCategory;
import cn.com.imovie.imoviebar.bean.QRData;
import cn.com.imovie.imoviebar.bean.Release;
import cn.com.imovie.imoviebar.bean.Roomcategory;
import cn.com.imovie.imoviebar.bean.SimpleMovie;
import cn.com.imovie.imoviebar.bean.StatInfo;
import cn.com.imovie.imoviebar.bean.Status;
import cn.com.imovie.imoviebar.bean.Stginfo;
import cn.com.imovie.imoviebar.bean.Times;
import cn.com.imovie.imoviebar.utils.DateHelper;
import cn.com.imovie.imoviebar.utils.ServerTimerTask;
import cn.com.imovie.imoviebar.utils.VV8Utils;
import cn.com.imovie.imoviebar.utils.XMLToBeanUtil;


/**
 * XML?????????
 * 
 * @author ?????? 2012-1-5????9:40:33
 */
public class XMLDataParser {
	private static final String TAG = "XMLDataGetter";
	private DocumentBuilder db;
	private DocumentBuilderFactory dbfactory;
	private static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static XMLDataParser xmlParser;
	private XMLDataParser() {
		try {
			this.dbfactory = DocumentBuilderFactory.newInstance();
			this.db = this.dbfactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	public static XMLDataParser getXmlParser(){
		if(xmlParser==null){
			xmlParser=new XMLDataParser();
		}
		return xmlParser;
	}
	public static class DataCache {
		private static NewVersion newVersion;
		private static List<SimpleMovie> BROWSEINFO_MOVIE_LIST;
		private static List<MovieCategory> MOVIE_CATEGORY_LIST;
		public static Date getServerTime() {
			return ServerTimerTask.getDate();
		}
		/**
		 * ???????
		 * @return
		 * @author ??????
		 * @return long
		 * 2012-3-7????1:58:44
		 */
		public static long getCurServerTime(){
			return ServerTimerTask.getDateInt();
		}
		public static NewVersion getNewVersion() {
			return newVersion;
		}


		public static List<SimpleMovie> getBrowseinfoMovie() {
			return BROWSEINFO_MOVIE_LIST;
		}
		public static List<MovieCategory> getMovieCategoryList() {
			return MOVIE_CATEGORY_LIST;
		}

	}	

	@SuppressWarnings("unused")
	private String getValueFromElement(Element element) {
		if (element == null || element.getFirstChild() == null)
			return null;
		return element.getFirstChild().getNodeValue();

	}

	private String getValueFromNode(Node node) {

		if (node == null || node.getFirstChild() == null)
			return null;
		return node.getFirstChild().getNodeValue();

	}

	/**
	 * 
	 * @param xml
	 * @throws org.xml.sax.SAXException
	 * @throws java.io.IOException
	 * @author ??????
	 * @return void 2012-1-5????10:07:29
	 */
	public void parserConnectserver(String xml) {
		Element rootElement = this.getRootElement(xml);
		Node timeNode = rootElement.getElementsByTagName("server_time").item(0);
		String serverTime = this.getValueFromNode(timeNode); 
		ServerTimerTask.startTimerTask(DateHelper.toDate(serverTime, DATETIME_FORMAT).getTime()/1000);
		NodeList versionNode = rootElement.getElementsByTagName("new_version")
				.item(0).getChildNodes();
		DataCache.newVersion = (NewVersion) XMLToBeanUtil.xmlToBean(
                NewVersion.class, versionNode);

	}
	
	public void parserGuide(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList nodes = rootElement.getChildNodes();
		baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(Guide.class, nodes));
	}	
	
	public void parserRelease(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList nodes = rootElement.getChildNodes();
		baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(Release.class, nodes));
	}

	public void parserLogin(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList nodes = rootElement.getChildNodes();
		baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(ErrorInfo.class, nodes));
	}

	public void parserConnect(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList nodes = rootElement.getChildNodes();
		baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(Guide.class, nodes));
	}	
	
	public void parserPage(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList nodes = rootElement.getChildNodes();
		baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(Page.class, nodes));
	}		
	
	public void parserMovieList(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList movieListNodes = rootElement.getElementsByTagName("simple_movie");
		@SuppressWarnings("unchecked")
		List<SimpleMovie> movieList = XMLToBeanUtil.xmlToBeanList(
				SimpleMovie.class, movieListNodes);
		baseReturn.setOtherObject(movieList);
	}
	public void parserEwatchList(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList ewatchListNodes = rootElement.getElementsByTagName("ewatch_status");
		@SuppressWarnings("unchecked")
		List<Ewatch> ewatchList = XMLToBeanUtil.xmlToBeanList(Ewatch.class, ewatchListNodes);
		baseReturn.setOtherObject(ewatchList);
	}
	public void parserMovie(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
//		NodeList nodes = rootElement.getChildNodes();
		NodeList nodes = rootElement.getElementsByTagName("movie").item(0).getChildNodes();
		baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(Movie.class, nodes));
	}		
	
	public void parserPlayTask(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList nodes = rootElement.getChildNodes();
		baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(PlayTask.class, nodes));
	}			

	
	public void parserMovieFavorite(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList nodes = rootElement.getChildNodes();
		baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(MovieFavorite.class, nodes));
	}
    public void parserStatus(BaseReturn baseReturn) {
        Element rootElement = this.getRootElement(baseReturn.getOtherText());
        NodeList nodes = rootElement.getChildNodes();
        baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(Status.class, nodes));
    }
	public void parserMovieFavoriteList(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList listNodes = rootElement.getElementsByTagName("movie_favorite");
		@SuppressWarnings("unchecked")
		List<MovieFavorite> list = XMLToBeanUtil.xmlToBeanList(
				MovieFavorite.class, listNodes);
		baseReturn.setOtherObject(list);
	}

    public void parserMovieDownloadList(BaseReturn baseReturn) {
        Element rootElement = this.getRootElement(baseReturn.getOtherText());
        NodeList listNodes = rootElement.getElementsByTagName("movie_download");
        @SuppressWarnings("unchecked")
        List<MovieDownload> list = XMLToBeanUtil.xmlToBeanList(
				MovieDownload.class, listNodes);
        baseReturn.setOtherObject(list);
    }

	public void parserStatInfo(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList nodes = rootElement.getChildNodes();
		baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(StatInfo.class, nodes));
	}	
	
	public void parserPlayTaskLogCategoryList(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList listNodes = rootElement.getElementsByTagName("play_task_log_category");
		@SuppressWarnings("unchecked")
		List<PlayTaskLogCategory> list = XMLToBeanUtil.xmlToBeanList(
				PlayTaskLogCategory.class, listNodes);
		baseReturn.setOtherObject(list);
	}
	
	public void parserPlayTaskLogList(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList listNodes = rootElement.getElementsByTagName("play_task_log");
		@SuppressWarnings("unchecked")
		List<PlayTaskLog> list = XMLToBeanUtil.xmlToBeanList(
				PlayTaskLog.class, listNodes);
		baseReturn.setOtherObject(list);
	}	
	
	@SuppressWarnings("unchecked")
	public void parserMovieQueryCondition(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		MovieQueryCondition movieQueryCondition = new MovieQueryCondition();
		movieQueryCondition.setMoviecatList(XMLToBeanUtil.xmlToBeanList(Moviecat.class, rootElement.getElementsByTagName("moviecat")));
		movieQueryCondition.setTimesList(XMLToBeanUtil.simpleXmlToBeanList(Times.class, rootElement.getElementsByTagName("times")));
		movieQueryCondition.setAreaList(XMLToBeanUtil.simpleXmlToBeanList(Area.class, rootElement.getElementsByTagName("area")));
		baseReturn.setOtherObject(movieQueryCondition);
	}	
	
	public LspiStatus parserLspiGetStatus(String lspiStatusXml) {
		Element rootElement = this.getRootElement(lspiStatusXml);
		NodeList nodes = rootElement.getChildNodes();
		return (LspiStatus)XMLToBeanUtil.xmlToBean(LspiStatus.class, nodes);
	}		
	
	public void parserErrorInfo(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList nodes = rootElement.getChildNodes();
		baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(ErrorInfo.class, nodes));
	}		


	public void parserBrowseinfo(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList movieListNodes = rootElement.getElementsByTagName("movie");
		@SuppressWarnings("unchecked")
		List<SimpleMovie> movieList = XMLToBeanUtil.xmlToBeanList(
				SimpleMovie.class, movieListNodes);
		DataCache.BROWSEINFO_MOVIE_LIST = movieList;
	}
	

	@SuppressWarnings("unchecked")
	public void parserMovieCategoryList(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList categoryNodes = rootElement.getElementsByTagName("movie_category");
		List<MovieCategory> categorys=XMLToBeanUtil.xmlToBeanList(
				MovieCategory.class, categoryNodes);
				DataCache.MOVIE_CATEGORY_LIST=categorys;;
		baseReturn.setOtherObject(categorys);
		
	}
	public void parserStgMovieList(BaseReturn baseReturn){
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList movieNodes = rootElement.getElementsByTagName("movie");
		@SuppressWarnings("unchecked")
		List<Movie> movieList=XMLToBeanUtil.xmlToBeanList(
				Movie.class, movieNodes);
		baseReturn.setOtherObject(movieList);
	}


//连接视频听馆类型
	public void parserStginfo(BaseReturn baseReturn){
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList nodes = rootElement.getElementsByTagName("stg").item(0).getChildNodes();
		baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(Stginfo.class, nodes));
	}


	public void parserQRCode(BaseReturn baseReturn){
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList nodes = rootElement.getChildNodes();
		baseReturn.setOtherObject(XMLToBeanUtil.xmlToBean(QRData.class, nodes));
	}


	//获取包房信息

	public List<Roomcategory> parserRoomList(BaseReturn baseReturn) {

		List<Roomcategory> list = new ArrayList<Roomcategory>();
		Element rootElement = this.getRootElement(baseReturn.getOtherText());

	     NodeList roomListNodes = rootElement.getElementsByTagName("room_category");



         for (int i=0; i<roomListNodes.getLength();i++){


			 Roomcategory roomcategory  = (Roomcategory)XMLToBeanUtil.xmlToBean(Roomcategory.class, roomListNodes.item(i).getChildNodes());




			 list.add(roomcategory);


		 }

		return list;


		/*List<Roomcategory> roomList = XMLToBeanUtil.xmlToBeanList(
				Roomcategory.class, roomListNodes);
		baseReturn.setOtherObject(roomList);*/



	}


	public void parserMovieDetail(BaseReturn baseReturn) {
		Element rootElement = this.getRootElement(baseReturn.getOtherText());
		NodeList movieNodes = rootElement.getElementsByTagName("movie");
		@SuppressWarnings("unchecked")
		List<Movie> movieList=XMLToBeanUtil.xmlToBeanList(
				Movie.class, movieNodes);
		if(movieList!=null && movieList.size()>0){
			baseReturn.setOtherObject(movieList.get(0));
		}
	}
	
	/**
	 * ???Root???
	 * 
	 * @param xml
	 * @return
	 * @author ??????
	 * @return Element 2012-1-5????10:18:43
	 */
	private Element getRootElement(String xml) {
		VV8Utils.printLog(TAG, "Parser xml=" + xml);
		Element rootElement = null;
		try {
			rootElement = db.parse(new InputSource(new StringReader(xml)))
					.getDocumentElement();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rootElement;
	}
}
