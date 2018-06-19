package cn.com.imovie.imoviebar.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.internal.Utils;
import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.QRData;
import cn.com.imovie.imoviebar.bean.Roomcategory;
import cn.com.imovie.imoviebar.bean.SelectedMov;
import cn.com.imovie.imoviebar.http.XMLDataGetter;
import cn.com.imovie.imoviebar.http.XMLDataParser;
import cn.com.imovie.imoviebar.utils.QRCodeUtil;

public class OrderMovieActivity extends Activity {


    private ListView listView;
    private OrderMoveListAdapter orderListAdapter;


    private TextView tv_movnum, tv_movtoaltime, tv_totalprice, tv_roomtype;
    private TextView tv_qr_code;
    private TextView tv_code_state;
    private TextView tv_paytips;


    private ImageView imgQR;

    private Button btn_return;

    private String selectRoomType;
    private int selectedRoomTypeID;

    private float tempPrice;
    private static final int GET_QRCODE_SUCCESS = 0x88688;
    private static final int GET_NOCODE_SUCCESS = 9001;

    private String qrCode="";
    private QRData qrData;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case GET_NOCODE_SUCCESS:
                    break;
                case GET_QRCODE_SUCCESS:

//                    Toast.makeText(OrderMovieActivity.this,"---:"+qrCode,Toast.LENGTH_LONG);
                    break;
            }

        }

        //  super.handleMessage(msg);
    };


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_movie);
        WindowManager.LayoutParams lp = getWindow().getAttributes();


        lp.screenBrightness = 1.0f;

        getWindow().setAttributes(lp);


        tempPrice = 0;


        tv_movnum = (TextView) findViewById(R.id.tv_movcount);

        tv_movtoaltime = (TextView) findViewById(R.id.tv_totaltime);
        tv_totalprice = (TextView) findViewById(R.id.tv_totalPrice);
        tv_roomtype = (TextView) findViewById(R.id.tv_roomtype);
        tv_qr_code = (TextView) findViewById(R.id.tv_qr_code);
        tv_code_state = (TextView) findViewById(R.id.tv_code_state);
        tv_paytips = (TextView) findViewById(R.id.tv_paytips);
        imgQR = (ImageView) findViewById(R.id.img_qr);
        btn_return = (Button) findViewById(R.id.btn_return);

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApplication.getInstance().selectedMovList.clear();
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.mvorder_list);

        orderListAdapter = new OrderMoveListAdapter(this);
        listView.setAdapter(orderListAdapter);


        selectedRoomTypeID = MyApplication.getInstance().selectedRoomID;

        if (selectedRoomTypeID > -1) {

            for (Roomcategory room : MyApplication.getInstance().roomList) {
                if (room.getId() == selectedRoomTypeID) {
                    selectRoomType = room.getName();
                }


            }


            tv_roomtype.setText(selectRoomType);
            tv_movnum.setText(orderListAdapter.getCount() + "部");
            tv_totalprice.setText(String.format("%.0f", getTotalPrice()) + "元");
        }

        int totalTime = 0;

        for (SelectedMov mov : MyApplication.getInstance().selectedMovList) {
            totalTime = totalTime + mov.getTimeLens();

        }


        tv_movtoaltime.setText(totalTime / 60 + "小时" + (totalTime % 60) + "分钟");


        sendOrderList();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }


    static class ViewHolder {
        public TextView tv_name;
        public TextView tv_times;

    }


    private void showQRcode(String qrData) {

//        Bitmap bitmap  = QRCodeUtil.createImage(qrData, 600, 600, Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
        Bitmap bitmap = QRCodeUtil.createImage2(qrData, 1600, 400);

        imgQR.setImageBitmap(bitmap);



    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){

            MyApplication.getInstance().selectedMovList.clear();
        }


        return super.onKeyDown(keyCode,event);
    }

    private void sendOrderList() {
        new QueryQrCodeTask().execute();
    }


    private float getTotalPrice() {


        float totoalPrice = 0;
        for (SelectedMov mov : MyApplication.getInstance().selectedMovList) {
            float tempPrice = (mov.getPriceList().get(selectedRoomTypeID) == null) ? 0 : mov.getPriceList().get(selectedRoomTypeID);

            totoalPrice = totoalPrice + tempPrice;
        }

        return totoalPrice;
    }


    private String requestQRcommand(int roomtypeid, List<SelectedMov> mvlist) {


        StringBuffer data = new StringBuffer();
        data.append("xml=<?xml version=\"1.0\" encoding=\"gbk\" ?>");
        data.append("<order_info>");

        data.append("<room_category_id>" + roomtypeid + "</room_category_id>");


        data.append("<movie_list>");

        for (SelectedMov mov : mvlist) {

            data.append("<movie>");

            data.append("<movie_id>" + mov.getMovID() + "</movie_id>");

            float price = (mov.getPriceList().get(roomtypeid) == null) ? 0 : mov.getPriceList().get(roomtypeid);


            data.append("<price>" + price + "</price>");

            data.append("</movie>");
        }


        data.append("</movie_list>");

        data.append("</order_info>");

        return data.toString();
    }


    class OrderMoveListAdapter extends BaseAdapter {

        private Context context;

        private LayoutInflater inflater;

        private TextView mv_name, mv_times, mv_price;


        @Override
        public int getCount() {
            return MyApplication.getInstance().selectedMovList.size();
        }


        public OrderMoveListAdapter(Context context) {
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


                convertView = inflater.inflate(R.layout.ordermovie_item, null);


            }

            mv_name = (TextView) convertView.findViewById(R.id.item_name2);
            mv_times = (TextView) convertView.findViewById(R.id.item_times2);
            mv_price = (TextView) convertView.findViewById(R.id.item_price2);


            mv_name.setText(MyApplication.getInstance().selectedMovList.get(position).getMovName());
            mv_times.setText(MyApplication.getInstance().selectedMovList.get(position).getTimeLens() + "分钟");

            int typeid = MyApplication.getInstance().selectedRoomID;

            float price = (MyApplication.getInstance().selectedMovList.get(position).getPriceList().get(typeid) == null) ? 0 :
                    MyApplication.getInstance().selectedMovList.get(position).getPriceList().get(typeid);

            mv_price.setText(String.format("%.0f", price) + "元");



            /*for (MoviePrice mp : MyApplication.getInstance().selectedMovList.get(position).getPriceList())
            {



                  if (String.valueOf(typeid).equals(mp.getRoomCategoryId()))
                  {

                      mv_price.setText(mp.getPrice()+"元");
                      break;

                  }

            }*/


            return convertView;
        }
    }

    class QueryQrCodeTask extends AsyncTask<Void, Void, BaseReturn> {
        @SuppressWarnings("unchecked")
        @Override
        protected BaseReturn doInBackground(Void... params) {

            BaseReturn baseReturn = new BaseReturn();
            baseReturn.setCode(BaseReturn.ERROR);
            StringBuffer url = new StringBuffer("http://" + MyApplication.getInstance().serverIp + "/stg/addselectbill.php");
            try {
                baseReturn = XMLDataGetter.doHttpRequest(url.toString(), requestQRcommand(selectedRoomTypeID, MyApplication.getInstance().selectedMovList), XMLDataGetter.POST_REQUEST);
                if (baseReturn.getCode() == BaseReturn.SUCCESS) {
                    XMLDataParser.getXmlParser().parserQRCode(baseReturn);
                    qrData = (QRData) baseReturn.getOtherObject();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


                return baseReturn;

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(BaseReturn result) {
            int stgId = MyApplication.getInstance().stginfo.getId();
//            Toast.makeText(OrderMovieActivity.this,":"+stgId,Toast.LENGTH_SHORT).show();
            if(stgId>6000 && stgId<8000){
                tv_code_state.setVisibility(View.VISIBLE);
                tv_qr_code.setVisibility(View.GONE);
                tv_paytips.setVisibility(View.GONE);
                imgQR.setVisibility(View.GONE);
            }else{
                tv_code_state.setVisibility(View.GONE);
                imgQR.setVisibility(View.VISIBLE);
                tv_qr_code.setVisibility(View.VISIBLE);
                tv_paytips.setVisibility(View.VISIBLE);
                if (qrData != null) {
                    qrCode = qrData.getOrderCode();
                    try {
                        if (qrCode != null) {
                            showQRcode(qrCode);
                            tv_qr_code.setText(qrCode);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    handler.sendEmptyMessage(GET_QRCODE_SUCCESS);
                }
            }
        }
    }
}
