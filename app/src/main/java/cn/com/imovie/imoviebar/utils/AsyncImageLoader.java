package cn.com.imovie.imoviebar.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Pair;
import cn.com.imovie.imoviebar.bean.ImageClass;
import cn.com.imovie.imoviebar.http.ImageDataGet;

import java.util.List;

/**
 * 图片锟届步锟斤拷锟斤拷锟斤拷
 * 
 * @author 锟斤拷锟斤拷锟斤拷 2012-1-18锟斤拷锟斤拷2:10:55
 */
public class AsyncImageLoader extends
		AsyncTask<List<ImageClass>, ImageClass, Boolean>  implements
		android.os.Handler.Callback{
	// 锟斤拷锟矫革拷锟斤拷状态
	private boolean mAbort = false;
	
	
	
	public boolean getAbort() {
		return mAbort;
	}

	public void setAbort(boolean mAbort) {
		this.mAbort = mAbort;
	}
	@Override
	protected Boolean doInBackground(List<ImageClass>... params) {
		List<ImageClass> list = params[0];
		if (list.size() == 0)
			return true;
		for (ImageClass image:list) {
			if(this.mAbort)break;
			image.getImageView().setTag(new Pair<String,String>(image.getImageUrl(),image.getHash()));
			Bitmap bitmap = ImageDataGet.returnBitMap(image.getImageUrl(), image.getHash());
			image.setBitmap(bitmap);
			this.publishProgress(image);
		}
		return true;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		Message message = Message.obtain();
		if (result.booleanValue()) {//锟斤拷锟叫讹拷锟斤拷锟斤拷执锟斤拷锟疥，锟斤拷锟斤拷取锟斤拷
			message.what = 1;
			handleMessage(message);
		}
		this.mAbort=false;
	}

	@Override
	protected void onProgressUpdate(ImageClass... values) {
		ImageClass image = values[0];
		@SuppressWarnings("unchecked")
		Pair<String,String> tag=(Pair<String,String>)image.getImageView().getTag();
		if (image.getBitmap() != null && tag!=null && tag.first.equalsIgnoreCase(image.getImageUrl())) {
			image.getImageView().setTag(null);
			image.getImageView().setImageBitmap(image.getBitmap());
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}
	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	
}