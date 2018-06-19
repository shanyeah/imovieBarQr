package cn.com.imovie.imoviebar.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.bean.Ewatch;
import cn.com.imovie.imoviebar.utils.StringHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by fwh on 15-9-16.
 */
public class EwatchAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    DisplayImageOptions options = null;
    private List<Ewatch> mList;
    GridView.LayoutParams itemLayoutParams;

    public EwatchAdapter(LayoutInflater inflater,List<Ewatch> list,GridView.LayoutParams layoutParams) {
        mInflater = inflater;
        mList = list;
        itemLayoutParams = layoutParams;
        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading()
                .cacheInMemory()
                .cacheOnDisc()
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    public Ewatch getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return getItem(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_movie_item, null);
            convertView.setLayoutParams(itemLayoutParams);
        }
        Ewatch item = getItem(position);

        ImageView movieImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
        TextView ewatchName = (TextView) convertView.findViewById(R.id.tvEwatchName);
        TextView movieName = (TextView) convertView.findViewById(R.id.tvMovieName);

        ewatchName.setText((item.getName()+"@@浠锋牸: "+(StringHelper.isEmpty(item.getPrice())?"---":item.getPrice())).replace("@@","\n"));
        movieName.setText(item.isFree()?"绌洪棽":("姝ｅ湪鎾斁: "+item.getMovieName()));
        ImageLoader.getInstance().displayImage(item.getBigPoster(), movieImage,options);
        return convertView;
    }
}