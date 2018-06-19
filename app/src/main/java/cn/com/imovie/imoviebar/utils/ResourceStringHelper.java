package cn.com.imovie.imoviebar.utils;

import android.content.res.Resources;

/**
 * Created by 锟节斤拷平 on 2015/4/1.
 */
public class ResourceStringHelper {
    Resources resources;
    public ResourceStringHelper(Resources resources){
        this.resources = resources;
    }
    public String format(int res,Object... args){
        String string = resources.getString(res);
        if(string==null)
            return "";
        return String.format(string,args);
    }

    public String getString(int res){
        return resources.getString(res);
    }
    public Resources getResources(){
        return resources;
    }
}
