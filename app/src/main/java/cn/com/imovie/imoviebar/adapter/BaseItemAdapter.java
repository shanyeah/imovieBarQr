package cn.com.imovie.imoviebar.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 浜庨噾骞� on 2015/3/30.
 */
public abstract class BaseItemAdapter<T> extends BaseAdapter {
    protected LayoutInflater inflater;
    protected int resourceId = 0;
    protected List<T> list;
    protected Resources resources;
    public BaseItemAdapter(Context context, List<T> objects) {
        this(context, 0, objects);
    }

    public BaseItemAdapter(Context context, int resourceId, List<T> objects) {
        this.inflater = LayoutInflater.from(context);
        this.list = objects;
        this.resourceId = resourceId;
        this.resources = context.getResources();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(resourceId, parent, false);
        }
        T item = getItem(position);
        initView(position, convertView, item);
        return convertView;
    }

    public abstract void initView(int position, View convertView, T item);
}
