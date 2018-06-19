package cn.com.imovie.imoviebar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.bean.Footer;

/**
 * Created by yujinping on 2015/1/28.
 */
public class FooterAdapter extends BaseAdapter {
    public final static String TAG = "FooterAdapter";

    public static final int MAX_SIZE=2;
    private int selection=0;
    private Context context;
    private List<Footer> footerList = new ArrayList<Footer>();
    private boolean isVerticalLayout =false;
    public FooterAdapter(Context context){
        this.context = context;
    }
    public FooterAdapter(Context context,List<Footer> footers,boolean isVerticalLayout){
        this.context = context;
        this.footerList = footers;
        this.isVerticalLayout = isVerticalLayout;
        selection = MyApplication.getInstance().getSelectedFooterMenu();
    }


    @Override
    public int getCount() {
        return MAX_SIZE;
    }
    public void setSelection(int selection){
        this.selection = selection;
        MyApplication.getInstance().setSelectedFooterMenu(selection);
    }
    public void select(int selection){
        setSelection(selection);
        notifyDataSetChanged();
    }
    @Override
    public Footer getItem(int position) {
        return footerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.footer_item,null);
        }

        Footer f =getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView mBadgeView = (TextView) convertView.findViewById(R.id.txt_right_tips);
        if(position==3){
            if(f.getMsgCount()>0){
                mBadgeView.setVisibility(View.VISIBLE);
                mBadgeView.setText(f.getMsgCount()+"");
            }else{
                mBadgeView.setVisibility(View.INVISIBLE);
            }

        }
        if(position==selection){
            imageView.setImageResource(f.getYellow());
        } else {
            imageView.setImageResource(f.getWhite());
        }
        return convertView;
    }
}
