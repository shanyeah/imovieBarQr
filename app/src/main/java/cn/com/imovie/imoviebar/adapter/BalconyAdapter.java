package cn.com.imovie.imoviebar.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.bean.Ewatch;

/**
 * Created by zhouxinshan on 2016/3/23.
 */
public class BalconyAdapter extends BaseAdapter {
    private Context context;
    public List<Ewatch> mList;
    DisplayImageOptions options = null;
    public BalconyAdapter(Context context, List<Ewatch> mList){
        this.context = context;
        this.mList = mList;
        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading()
                .cacheInMemory()
                .cacheOnDisc()
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Ewatch getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_movie_item, null);
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            int width = (screenWidth-50)*135/640;
            convertView.setLayoutParams(new GridView.LayoutParams(width, width*3/2));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
        holder.tvEwatchName = (TextView) convertView.findViewById(R.id.tvEwatchName);
        holder.tvMovieName = (TextView) convertView.findViewById(R.id.tvMovieName);
        holder.tvEwatchName.setText(getItem(position).getName());
        holder.tvMovieName.setText(getItem(position).getMovieName());
        ImageLoader.getInstance().displayImage(getItem(position).getBigPoster(), holder.itemImage,options);
        return convertView;
    }
    class ViewHolder {
        public ImageView itemImage = null;
        public TextView tvEwatchName = null;
        public TextView tvMovieName = null;
    }
}
