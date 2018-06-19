package cn.com.imovie.imoviebar.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.activity.HtMainActivity;
import cn.com.imovie.imoviebar.adapter.FooterAdapter;
import cn.com.imovie.imoviebar.bean.Footer;
import cn.com.imovie.imoviebar.notify.OnFooterItemClick;
import cn.com.imovie.imoviebar.notify.ReloadNotify;

/**
 * Created by yujinping on 2015/2/2.
 */
public class FooterFragment extends Fragment implements ReloadNotify {

    public final static String TAG="FooterFragment";

    private static final String SELECTED="SELECTED";
    private static final String LAYOUT_TYPE="LAYOUT_TYPE";
    GridView mFooterGrid;
    FooterAdapter mFooterAdapter;
    OnFooterItemClick mOnFooterItemClick;
    Boolean mIsVerticalLayout=false;
    private HtMainActivity mActivity;
    private List<Footer> footerList = new ArrayList<Footer>();

    int selected=0;
    public static final FooterFragment createInstance(int selected){
        FooterFragment footerFragment = new FooterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SELECTED,selected);
        footerFragment.setArguments(bundle);
        return footerFragment;
    }

    public static final FooterFragment createInstance(int selected,boolean isVertical){
        FooterFragment footerFragment = new FooterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SELECTED,selected);
        bundle.putBoolean(LAYOUT_TYPE,isVertical);
        footerFragment.setArguments(bundle);
        return footerFragment;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (HtMainActivity) activity;
        if(activity instanceof OnFooterItemClick){
            mOnFooterItemClick = (OnFooterItemClick)activity;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reload();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_footer, container, false);
        mFooterGrid = (GridView) view.findViewById(R.id.footer_grid);
        Bundle bundle = getArguments();

        if(bundle.getBoolean(LAYOUT_TYPE,false)==true) {
            int V_SPACE = 40;
            int H_SPACE = 20;
            mFooterGrid.setNumColumns(1);
            mIsVerticalLayout=true;
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.footer_layout);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
            mFooterGrid.setVerticalSpacing(V_SPACE);
            mFooterGrid.setHorizontalSpacing(H_SPACE);
            mFooterGrid.setBackgroundColor(getActivity().getResources().getColor(R.color.default_background));
            mFooterGrid.setSelector(getActivity().getResources().getDrawable(android.R.drawable.screen_background_dark_transparent));
        } else {
            mIsVerticalLayout=false;
        }
        mFooterAdapter = new FooterAdapter(getActivity(),footerList,mIsVerticalLayout);
        mFooterGrid.setFocusableInTouchMode(true);
        mFooterGrid.setFocusable(true);
        mFooterGrid.setSelector(R.drawable.footer_item);
        mFooterGrid.setAdapter(mFooterAdapter);
        mFooterAdapter.select(MyApplication.getInstance().getSelectedFooterMenu());
        mFooterGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mFooterAdapter.select(position);
                //TODO::
                if(mOnFooterItemClick!=null){
                    mOnFooterItemClick.onFooterItemClick(position);
                }
            }
        });
        return view;

    }

    public void select(int position){
        mFooterAdapter.select(position);
        //TODO::
        if(mOnFooterItemClick!=null){
            mOnFooterItemClick.onFooterItemClick(position);
        }
    }

    @Override
    public void reload() {
        footerList.clear();
        int[] YELLOW={R.drawable.tab_1_y, R.drawable.tab_4_y};
        int[] WHITE={R.drawable.tab_1,R.drawable.tab_4};

        if(MyApplication.getInstance().mPref.getInt("BoxMode", 0)==2){
            YELLOW = new int[]{R.drawable.tab_1_y,R.drawable.tab_6_y};
            WHITE = new int[]{R.drawable.tab_1,R.drawable.tab_6};
        }


        //int[] GRAY ={R.drawable.tab_1_g,R.drawable.tab_2_g,R.drawable.tab_3_g,R.drawable.tab_5_g,R.drawable.tab_4_g};


        for(int i = 0 ; i < YELLOW.length ; i++){
            Footer f = new Footer();
            f.setWhite(WHITE[i]);
            f.setYellow(YELLOW[i]);
            footerList.add(f);
        }
        if(mFooterAdapter!=null) mFooterAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPageNo(int page){

    }

}
