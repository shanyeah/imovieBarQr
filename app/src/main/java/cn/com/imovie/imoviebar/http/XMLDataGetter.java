package cn.com.imovie.imoviebar.http;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.ErrorInfo;
import cn.com.imovie.imoviebar.http.XMLDataParser.DataCache;
import cn.com.imovie.imoviebar.utils.NetWorkTypeUtils;
import cn.com.imovie.imoviebar.utils.StringHelper;
import cn.com.imovie.imoviebar.utils.VV8Utils;

/**
 * Http请求类
 *
 * @author 李忠仁 2012-1-4上午10:58:55
 */
public class XMLDataGetter {
    public static final String GET_REQUEST = "GET";
    public static final String POST_REQUEST = "POST";
    public static final String PUT_REQUEST = "PUT";
    public static final String DELETE_REQUEST = "DELETE";

    private static final String TAG = "XMLDataGetter";
    private static final int CONNECT_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;
    public static int HTTP_CODE_1000 = 1000;// 系统错误
    public static int HTTP_CODE_1010 = 1010;// 无网络连接
    public static String HTTP_MSG_1010 = "无法连接网络或连接服务器异常";// 无网络连接

    private static XMLDataGetter instance;
    static {
        instance = new XMLDataGetter();
    }

    public static XMLDataGetter getInstance() {
        return instance;
    }

    private static URLConnection getGetURLConnection(String urlString) {
        int i = 0;
        HttpURLConnection con = null;
        while (i < 3) {
            try {
                URL url = new URL(urlString);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(CONNECT_TIMEOUT);
                con.setReadTimeout(READ_TIMEOUT);
                con.setDoInput(true);
                con.setDoOutput(false);
                con.setUseCaches(false);
                con.setRequestMethod("GET");
                int responsecode = con.getResponseCode();
                VV8Utils.printLog(TAG, "response code is " + responsecode);
                if (responsecode == 200)
                    return con;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                i++;
            }
        }
        return con;
    }

    private static URLConnection getGetURLConnection(String urlString,int connectTimeout,int readTimeout) {
        int i = 0;
        HttpURLConnection con = null;
        while (i < 3) {
            try {
                URL url = new URL(urlString);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(connectTimeout);
                con.setReadTimeout(readTimeout);
                con.setDoInput(true);
                con.setDoOutput(false);
                con.setUseCaches(false);
                con.setRequestMethod("GET");
                int responsecode = con.getResponseCode();
                VV8Utils.printLog(TAG, "response code is " + responsecode);
                if (responsecode == 200)
                    return con;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                i++;
            }
        }
        return con;
    }

    public static BaseReturn doGetHttpRequest(String urlString,int connectTimeout,int readTimeout ) {
        BaseReturn baseReturn = new BaseReturn();
        if(NetWorkTypeUtils.getAvailableNetWorkInfo(MyApplication.getInstance())==null){
            baseReturn.setCode(HTTP_CODE_1010);
            baseReturn.setMessage(HTTP_MSG_1010);
            return baseReturn;
        }
        StringBuffer urlSB=new StringBuffer(urlString);
        if(urlSB.indexOf("?")==-1){
            urlSB.append("?time=").append(DataCache.getCurServerTime());
        } else {
            urlSB.append("&time=").append(DataCache.getCurServerTime());
        }
        urlSB.append("&ts=").append(System.currentTimeMillis());

        Log.d(TAG, "doGetHttpRequest"+urlSB.toString());

        InputStream inputStream = null;
        try {


            Log.d(TAG, "doGetHttpRequest====="+urlSB.toString()+"==="+connectTimeout+"===="+readTimeout);

            URLConnection con = getGetURLConnection(urlSB.toString(),connectTimeout,readTimeout);
            if (con == null) {
                baseReturn.setCode(HTTP_CODE_1010);
                baseReturn.setMessage(HTTP_MSG_1010);
                return baseReturn;
            }else if(((HttpURLConnection)con).getResponseCode()!=200){
                baseReturn.setCode(HTTP_CODE_1010);
                baseReturn.setMessage("网络连接出错:"+((HttpURLConnection)con).getResponseCode());
                return baseReturn;
            }

            if (baseReturn.getCode()==BaseReturn.SUCCESS) {
                inputStream = con.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] b = new byte[4096];
                for (int n; (n = inputStream.read(b)) != -1;) {
                    outStream.write(b, 0, n);
                }
                b=null;
                baseReturn.setOtherText(new String(outStream.toByteArray(),"GBK"));
                outStream.close();
            }

        } catch (Exception e) {
            baseReturn.setCode(HTTP_CODE_1000);
            baseReturn.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return baseReturn;
    }

    public static BaseReturn doGetHttpRequest(String urlString) {
        BaseReturn baseReturn = new BaseReturn();
        if(NetWorkTypeUtils.getAvailableNetWorkInfo(MyApplication.getInstance())==null){
            baseReturn.setCode(HTTP_CODE_1010);
            baseReturn.setMessage(HTTP_MSG_1010);
            return baseReturn;
        }
        StringBuffer urlSB=new StringBuffer(urlString);
       /* if(urlSB.indexOf("?")==-1){
            urlSB.append("?time=").append(DataCache.getCurServerTime());
        } else {
            urlSB.append("&time=").append(DataCache.getCurServerTime());
        }
        urlSB.append("&ts=").append(System.currentTimeMillis());
*/

        Log.d(TAG, "urlSB== " + urlSB.toString());

        InputStream inputStream = null;
        try {
            URLConnection con = getGetURLConnection(urlSB.toString());
            if (con == null) {
                baseReturn.setCode(HTTP_CODE_1010);
                baseReturn.setMessage(HTTP_MSG_1010);
                return baseReturn;
            }else if(((HttpURLConnection)con).getResponseCode()!=200){
                baseReturn.setCode(HTTP_CODE_1010);
                baseReturn.setMessage("网络连接出错:"+((HttpURLConnection)con).getResponseCode());
                return baseReturn;
            }

            if (baseReturn.getCode()==BaseReturn.SUCCESS) {
                inputStream = con.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] b = new byte[4096];
                for (int n; (n = inputStream.read(b)) != -1;) {
                    outStream.write(b, 0, n);
                }
                b=null;
                baseReturn.setOtherText(new String(outStream.toByteArray(),"GBK"));
                outStream.close();
            }

        } catch (Exception e) {
            baseReturn.setCode(HTTP_CODE_1000);
            baseReturn.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return baseReturn;
    }


    private static URLConnection getPostURLConnection(String urlstring,
                                                      String requestData) {
        int i = 0;
        HttpURLConnection con = null;
        OutputStream outputStream = null;
        requestData = StringHelper.formatNullValue(requestData, "");
        String realurl = urlstring;
        //int index = urlstring.indexOf("?");
        //if (index != -1) {
        //	realurl = urlstring.substring(0, index);
        //	StringBuffer pam = new StringBuffer();
        //	pam.append(urlstring.substring(index).replaceFirst("\\?", "")
        //			.trim());
        //	if (!StringHelper.isEmpty(requestData)) {
        //		if (!requestData.trim().startsWith("&")) {
        //			pam.append("&");
        //		}
        //		pam.append(requestData);
        //	}
        //	requestData = pam.toString();
        //}
        VV8Utils.printLog(TAG, urlstring+" /requestData===" + requestData);
        while (i < 3) {
            try {
                URL url = new URL(realurl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(CONNECT_TIMEOUT);
                con.setReadTimeout(READ_TIMEOUT);
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setRequestMethod("POST");
                outputStream = con.getOutputStream();
                outputStream.write(requestData.getBytes("GBK"));
                outputStream.close();
                int responsecode = con.getResponseCode();
                VV8Utils.printLog(TAG, "response code is " + responsecode);
                //if (responsecode == 200 || responsecode == 201 || responsecode == 204)
                return con;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                i++;
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return con;
    }

    private static HttpURLConnection getURLConnection(String urlString,	String requestData, String requestMethod) {
        int i = 0;
        HttpURLConnection con = null;
        OutputStream outputStream = null;
        requestData = StringHelper.formatNullValue(requestData, "");
        while (i < 3) {
            try {
                URL url = new URL(urlString);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(CONNECT_TIMEOUT);
                con.setReadTimeout(READ_TIMEOUT);
                con.setUseCaches(false);
                con.setRequestMethod(requestMethod);
                if (requestData != null && !"".equals(requestData)) {
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    outputStream = con.getOutputStream();
                    outputStream.write(requestData.getBytes("GBK"));
                    outputStream.close();
                }
                return con;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                i++;
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return con;
    }

    /**
     * http请求
     * @param urlstring
     * @return
     * @author 李忠仁
     * @return BaseReturn
     * 2012-1-4下午3:48:23
     */
    public static BaseReturn doPostHttpRequest(String urlstring) {
        return doPostHttpRequest(urlstring, "");
    }

    /**
     * http请求
     * @param urlstring
     * @param requestData
     * @return
     * @author 李忠仁
     * @return BaseReturn
     * 2012-1-4下午3:48:30
     * @throws IOException
     */
    public static BaseReturn doPostHttpRequest(String urlstring, String requestData) {
        BaseReturn baseReturn = new BaseReturn();
        if(NetWorkTypeUtils.getAvailableNetWorkInfo(MyApplication.getInstance())==null){
            baseReturn.setCode(HTTP_CODE_1010);
            baseReturn.setMessage(HTTP_MSG_1010);
            return baseReturn;
        }
        StringBuffer data=new StringBuffer(requestData);
        if(data.indexOf("&time=")==-1){
            if(data.length()>0){
                data.append("&time=").append(DataCache.getCurServerTime());
            }else{
                data.append("time=").append(DataCache.getCurServerTime());
            }

        }
        InputStream inputStream = null;
        try {
            URLConnection con = getPostURLConnection(urlstring, data.toString());
            if (con == null) {
                baseReturn.setCode(HTTP_CODE_1010);
                baseReturn.setMessage(HTTP_MSG_1010);
                return baseReturn;
            }

            int responseCode = ((HttpURLConnection)con).getResponseCode();
            if (responseCode != 204) {
                inputStream = con.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] b = new byte[4096];
                for (int n; (n = inputStream.read(b)) != -1;) {
                    outStream.write(b, 0, n);
                }
                b=null;
                outStream.close();
                baseReturn.setOtherText(new String(outStream.toByteArray(),"GBK"));
            }
            if(responseCode != 200 && responseCode != 201){
                baseReturn.setCode(responseCode);
                baseReturn.setMessage("读取数据错误:"+responseCode);
            }
        } catch (Exception e) {
            baseReturn.setCode(HTTP_CODE_1000);
            baseReturn.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return baseReturn;
    }

    public static BaseReturn doHttpRequest(String urlString, String requestData, String requestMethod) {
        BaseReturn baseReturn = new BaseReturn();
        if(NetWorkTypeUtils.getAvailableNetWorkInfo(MyApplication.getInstance())==null){
            baseReturn.setCode(HTTP_CODE_1010);
            baseReturn.setMessage(HTTP_MSG_1010);
            return baseReturn;
        }
        StringBuffer urlSB=new StringBuffer(urlString);
        if(urlSB.indexOf("?")==-1){
            urlSB.append("?time=").append(DataCache.getCurServerTime());
        } else {
            urlSB.append("&time=").append(DataCache.getCurServerTime());
        }

        VV8Utils.printLog(TAG, "urlSB== " + urlSB.toString());

        InputStream inputStream = null;
        try {
            HttpURLConnection con = getURLConnection(urlSB.toString(), requestData, requestMethod);
            if (con == null) {
                baseReturn.setCode(HTTP_CODE_1010);
                baseReturn.setMessage(HTTP_MSG_1010);
                return baseReturn;
            }
            int responseCode = con.getResponseCode();
            if (responseCode != 204) {
                if (responseCode == 200 || responseCode == 201) {
                    inputStream = con.getInputStream();
                } else {
                    inputStream = con.getErrorStream();
                }
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] b = new byte[4096];
                for (int n; (n = inputStream.read(b)) != -1;) {
                    outStream.write(b, 0, n);
                }
                b=null;
                outStream.close();
                baseReturn.setOtherText(new String(outStream.toByteArray(),"GBK"));
            }
            if(responseCode != 200 && responseCode != 201 && responseCode != 204) {
                if (baseReturn.getOtherText() != null && !"".equals(baseReturn.getOtherText().trim())) {
                    XMLDataParser.getXmlParser().parserErrorInfo(baseReturn);
                    ErrorInfo errorInfo = (ErrorInfo)baseReturn.getOtherObject();
                    baseReturn.setCode(responseCode);
                    baseReturn.setMessage(errorInfo.getMessage());
                }
            }
        } catch (Exception e) {
            baseReturn.setCode(HTTP_CODE_1000);
            baseReturn.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return baseReturn;
    }

}
