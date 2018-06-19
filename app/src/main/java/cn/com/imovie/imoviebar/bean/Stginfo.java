package cn.com.imovie.imoviebar.bean;

/**
 * Created by glin on 1/19/16.
 */
public class Stginfo {
    private int id ;
    private String name;
    private String simpleName ;
    //<!-- status 0锛�0 绂佺敤 1 鍚敤 2 闈炴硶-->
    private int status;
    private String serverTime ; //><![CDATA[ 2011-05-10 16:29:56]]></server_time>

    private int specialControl  ; //><![CDATA[0]]></special_control>
    private String  logoUrl;
    private String stgType ;

    public int getId() {
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

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public int getSpecialControl() {
        return specialControl;
    }

    public void setSpecialControl(Integer specialControl) {
        this.specialControl = specialControl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getStgType() {
        return stgType;
    }

    public void setStgType(String stgType) {
        this.stgType = stgType;
    }
}
