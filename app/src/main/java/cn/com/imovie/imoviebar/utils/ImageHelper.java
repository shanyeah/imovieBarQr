package cn.com.imovie.imoviebar.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImageHelper {
	
	// drawable 转锟斤拷锟斤拷bitmap
    static public Bitmap drawableToBitmap(Drawable drawable) {
    	int width = drawable.getIntrinsicWidth();   // 取drawable锟侥筹拷锟斤拷
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;         // 取drawable锟斤拷锟斤拷色锟斤拷式
	    Bitmap bitmap = Bitmap.createBitmap(width, height, config);     // 锟斤拷锟斤拷锟斤拷应bitmap
    	Canvas canvas = new Canvas(bitmap);         // 锟斤拷锟斤拷锟斤拷应bitmap锟侥伙拷锟斤拷
    	drawable.setBounds(0, 0, width, height);
    	drawable.draw(canvas);      // 锟斤拷drawable锟斤拷锟捷伙拷锟斤拷锟斤拷锟斤拷锟斤拷
    	return bitmap;
    }

    static public Drawable zoomDrawable(Drawable drawable, int w, int h) {
    	int width = drawable.getIntrinsicWidth();
        int height= drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable); // drawable转锟斤拷锟斤拷bitmap
        Matrix matrix = new Matrix();   // 锟斤拷锟斤拷锟斤拷锟斤拷图片锟矫碉拷Matrix锟斤拷锟斤拷
        float scaleWidth = ((float)w / width);   // 锟斤拷锟斤拷锟斤拷锟脚憋拷锟斤拷
        float scaleHeight = ((float)h / height);
        matrix.postScale(scaleWidth, scaleHeight);         // 锟斤拷锟斤拷锟斤拷锟脚憋拷锟斤拷
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);       // 锟斤拷锟斤拷锟铰碉拷bitmap锟斤拷锟斤拷锟斤拷锟斤拷锟角讹拷原bitmap锟斤拷锟斤拷锟脚猴拷锟酵�
        return new BitmapDrawable(newbmp);       // 锟斤拷bitmap转锟斤拷锟斤拷drawable锟斤拷锟斤拷锟斤拷
    }

}
