package cn.com.imovie.imoviebar.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.Roomcategory;
import cn.com.imovie.imoviebar.bean.SelectedMov;

public class SelectedMovieActivity extends Activity implements View.OnClickListener {


    private ListView listView;
    private MoveListAdapter moveListAdapter;
    private Button btn_ok, btn_delall, btn_return;


    private static final int GET_ROOM_SUCCESS = 0x88687;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_ROOM_SUCCESS:   //成功取到房间列表
                    PopupMenu popupMenu = new PopupMenu(SelectedMovieActivity.this, btn_ok);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {


                            String s = menuItem.getTitle().toString();


                            for (Roomcategory room : MyApplication.getInstance().roomList) {

                                if (room.getName().equals(s)) {


                                    String invalidMov = "";

                                    for (SelectedMov mov : MyApplication.getInstance().selectedMovList) {

                                        if (mov.getPriceList().get(room.getId()) == null) {
                                            //没有设置价格的影片是不能播放的
                                            invalidMov = invalidMov + mov.getMovName() + " ";


                                        }


                                    }


                                    if (invalidMov.length() > 0) {


                                        Toast.makeText(SelectedMovieActivity.this, "影片：" + invalidMov + "不支持在当前包房播放", Toast.LENGTH_LONG).show();

                                        return true;
                                    }


                                    MyApplication.getInstance().selectedRoomID = room.getId();
                                    Intent intent = new Intent(SelectedMovieActivity.this, OrderMovieActivity.class);
                                    startActivity(intent);
                                    break;

                                }

                            }


                            return true;
                        }


                    });

                    Menu menu = popupMenu.getMenu();

                    // 通过代码添加菜单项

                    menu.clear();

                    int i = 0;

                    for (Roomcategory rv : MyApplication.getInstance().roomList) {
                        i++;
                        menu.add(Menu.NONE, Menu.FIRST + i + 1, i, rv.getName());


                    }

                    MenuInflater menuInflater = getMenuInflater();
                    menuInflater.inflate(R.menu.popup_roomtype, menu);
                     popupMenu.show();


                    break;


            }

            //  super.handleMessage(msg);
        }
    };


    private void addMovie(SelectedMov mov){

        for (SelectedMov mov1 : MyApplication.getInstance().selectedMovList) {
            if (mov1.getMovID() == mov.getMovID()) return;
        }

        MyApplication.getInstance().selectedMovList.add(mov);
    }


    @Override
    protected void onResume() {

        moveListAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_movie);
        MyApplication.getInstance().addActivityIntoTask(this);

        listView = (ListView) findViewById(R.id.mv_list);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_delall = (Button) findViewById(R.id.btn_delall);
        btn_return = (Button) findViewById(R.id.btn_return);


        btn_delall.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_return.setOnClickListener(this);

        Intent intent = getIntent();
        boolean isSameType = true;

       if (intent != null) {

            SelectedMov mov = (SelectedMov) intent.getSerializableExtra("MOV");

            if (mov != null) {

                for (SelectedMov mov1 : MyApplication.getInstance().selectedMovList) {
                    if (mov1.getMovType() != mov.getMovType()) {
                        isSameType = false;
                        break;
                    }

                }

               if (isSameType){
                   addMovie(mov);
               }
               else{
                    Toast.makeText(this,"3D影片和2D影片不能同时选择，请确认影片类型",Toast.LENGTH_LONG).show();
               }

            }
        }



        moveListAdapter = new MoveListAdapter(this);
        listView.setAdapter(moveListAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:


                if (MyApplication.getInstance().selectedMovList.size() == 0) {
                    Toast.makeText(SelectedMovieActivity.this, "没有选择影片", Toast.LENGTH_LONG).show();


                    return;

                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        BaseReturn baseReturn = MyApplication.getInstance().getRoomInfo();

                        if (baseReturn.getCode() == BaseReturn.SUCCESS) {


                            handler.sendEmptyMessage(GET_ROOM_SUCCESS);


                        } else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SelectedMovieActivity.this, "不能获取包房信息", Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                    }
                }).start();


                break;
            case R.id.btn_delall:
                MyApplication.getInstance().selectedMovList.clear();
                moveListAdapter.notifyDataSetChanged();

                MyApplication.getInstance().finishToActivity(HtMainActivity.class);
                break;

            case R.id.btn_return:
                  finish();

                break;


        }
    }


    static class ViewHolder {
        public TextView tv_name;
        public TextView tv_times;

    }


    class MoveListAdapter extends BaseAdapter {

        private Context context;

        private LayoutInflater inflater;

        private TextView mv_name, mv_times;
        private Button btn_del;

        @Override
        public int getCount() {
            return MyApplication.getInstance().selectedMovList.size();
        }


        public MoveListAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public Object getItem(int position) {
            return MyApplication.getInstance().selectedMovList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.movie_item, null);


            }

            mv_name = (TextView) convertView.findViewById(R.id.item_name);
            mv_times = (TextView) convertView.findViewById(R.id.item_times);

            btn_del = (Button) convertView.findViewById(R.id.btn_del);

            btn_del.setTag(position);

            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (Integer) v.getTag();

                    MyApplication.getInstance().selectedMovList.remove(pos);
                    moveListAdapter.notifyDataSetChanged();


                }
            });


            mv_name.setText(MyApplication.getInstance().selectedMovList.get(position).getMovName());
            mv_times.setText(MyApplication.getInstance().selectedMovList.get(position).getTimeLens() + "分钟");


            return convertView;
        }
    }
}
