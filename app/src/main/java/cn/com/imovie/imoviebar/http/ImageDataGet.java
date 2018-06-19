package cn.com.imovie.imoviebar.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.utils.ImageUtil;
import cn.com.imovie.imoviebar.utils.VV8Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片的加载
 * @author 李忠仁
 * 2012-2-3下午2:16:28
 */
public class ImageDataGet {
    private static final String TAG="ImageDataGet";

    public static Bitmap returnBitMap(String imageUrl,String hash){
        Bitmap bitMap = null;
        //先判断外置的SDCard是否存在图片
        bitMap=loadBitmapByLocal(hash);
        if(bitMap==null){//再走网络下载图片
            InputStream inputstream = null;
            try {
                VV8Utils.printLog(TAG, "请求服务器加载："+imageUrl);
                inputstream = ImageUtil.getRequest(imageUrl);
                if(inputstream==null) return null;
                File diskFile = saveBitmapToDisk(imageUrl, hash, inputstream, new File(MyApplication.getInstance().imageDir));
                if(diskFile==null) return null;
                bitMap=BitmapFactory.decodeFile(diskFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(inputstream!=null){
                    try {
                        inputstream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bitMap;
    }

    public static final String getFirstChar(String s){
        return s.substring(0, 1);
    }

    public static File saveBitmapToDisk(String imageUrl, String savePath, InputStream inputstream, File fileDir){
        FileOutputStream fileoutputstream=null;
        try{
            String dir=getFirstChar(savePath);
            File saveDiskFile= new File(fileDir, "/"+dir+"/");
            if(!saveDiskFile.exists()){
                saveDiskFile.mkdirs();
            }
            saveDiskFile= new File(saveDiskFile, savePath);
            byte abyte[]=new byte[32768];
            fileoutputstream = new FileOutputStream(saveDiskFile);
            int seek=0;
            while( true ){
                seek = inputstream.read(abyte);
                if(seek ==-1)break;
                fileoutputstream.write(abyte,0,seek);
            }
            fileoutputstream.flush();
            return saveDiskFile;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(fileoutputstream!=null){
                try {
                    fileoutputstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取本地图片
     * @param imageUrl
     * @param sharedpreferences
     * @param file
     * @param options
     * @return
     * @author 李忠仁
     * @return Bitmap
     * 2012-2-3上午11:18:03
     */
    public static Bitmap loadBitmapByLocal(String hash) {
        Bitmap bitmap = null;
        File imageFile = new File(MyApplication.getInstance().imageDir+"/"+getFirstChar(hash)+"/"+hash);
        if (imageFile.exists()){
            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), null);
        }
        return bitmap;
    }
}
