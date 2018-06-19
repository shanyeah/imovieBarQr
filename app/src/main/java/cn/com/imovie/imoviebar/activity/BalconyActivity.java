package cn.com.imovie.imoviebar.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.adapter.BalconyAdapter;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.Ewatch;
import cn.com.imovie.imoviebar.http.XMLDataGetter;

/**
 * Created by zhouxinshan on 2016/08/27.
 */
public class BalconyActivity extends Activity {

    int pageNo =1;
    public List<Ewatch> list = new ArrayList<Ewatch>();
    public BalconyAdapter adapter;
    public ProgressBar mProgressBar;
    private boolean mClear=true;

    GridView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_balcony);
        mProgressBar = (ProgressBar)findViewById(R.id.progress);
        mProgressBar.setVisibility(View.INVISIBLE);
        listView = (GridView) findViewById(R.id.listView);
        listView.setNumColumns(4);
        listView.setVerticalSpacing(20);
        listView.setHorizontalSpacing(10);
        adapter = new BalconyAdapter(BalconyActivity.this,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    SharedPreferences.Editor editor = MyApplication.getInstance().mPref.edit();
                    editor.putInt("EwatchId", adapter.getItem(position).getId());
                    editor.putString("EwatchName", adapter.getItem(position).getName());
                    editor.commit();
                    Toast.makeText(BalconyActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        loadEwatchList();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void loadEwatchList(){
        mProgressBar.setVisibility(View.VISIBLE);
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
//                String url = MyApplication.getInstance().guide.getEwatchListUrl();
                String serverIP = MyApplication.getInstance().mPref.getString("serverIp","0.0.0.0");
                String url = "http://" + serverIP + "/pad/ewatchstatuslist.php";
                BaseReturn baseReturn = XMLDataGetter.doHttpRequest(url, null, XMLDataGetter.GET_REQUEST);
                if(baseReturn.getCode()==BaseReturn.SUCCESS){
                    MyApplication.getInstance().xmlParser.parserEwatchList(baseReturn);
                    List<Ewatch> ewatches = (List<Ewatch>)baseReturn.getOtherObject();
                    if(ewatches!=null){
                        list.clear();
                        list.addAll(ewatches);
                    }

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mProgressBar.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

}