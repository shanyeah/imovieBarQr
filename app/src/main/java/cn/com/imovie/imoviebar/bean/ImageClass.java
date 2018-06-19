package cn.com.imovie.imoviebar.bean;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageClass {
	
	public ImageClass(){
		
	}
	public ImageClass(String imageUrl,String hash,ImageView imageView){
		this.imageUrl=imageUrl;
		this.hash=hash;
		this.imageView=imageView;
	}
	public ImageClass(String imageUrl,String hash,ImageView imageView,int cacheType){
		this.imageUrl=imageUrl;
		this.hash=hash;
		this.cacheType=cacheType;
		this.imageView=imageView;
	}
	public final static int CACHE_TYPE_ZERO=0;
	public final static int CACHE_TYPE_ONE=1;
	private String imageUrl;
	private ImageView imageView;
	private Bitmap bitmap;
	private String hash;
	private int cacheType=CACHE_TYPE_ONE;
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public int getCacheType() {
		return cacheType;
	}
	public void setCacheType(int cacheType) {
		this.cacheType = cacheType;
	}
	
	
	
}
