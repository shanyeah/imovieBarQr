package cn.com.imovie.imoviebar.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import cn.com.imovie.imoviebar.event.Notify;

/**
 * Created by 锟节斤拷平 on 2015/3/26.
 */
public class PlayService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new NotifyBinder();
    }

    private class NotifyBinder extends Binder implements Notify{

        @Override
        public void onNotify(String tag, Bundle bundle) {

        }
    }
}
