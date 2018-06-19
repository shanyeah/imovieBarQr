package cn.com.imovie.imoviebar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.imovie.imoviebar.R;

/**
 * Created by yujinping on 2015/2/2.
 */
public class TestGridAdapter extends BaseAdapter {

    Context context;
    int selected=0;
    public TestGridAdapter(Context context){
        this.context = context;
    }

    public void select(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ArrayData.DATA.length;
    }

    @Override
    public String getItem(int position) {
        return ArrayData.DATA[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
            convertView = LayoutInflater.from(context).inflate(R.layout.text_item,null);
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(ArrayData.DATA[position]);
        textView.setBackgroundColor(context.getResources().getColor(R.color.default_background));
        textView.setTextColor(context.getResources().getColor(R.color.white));
        if(selected==position)
            textView.setBackground(context.getResources().getDrawable(R.drawable.item_selected));
        return convertView;
    }
}