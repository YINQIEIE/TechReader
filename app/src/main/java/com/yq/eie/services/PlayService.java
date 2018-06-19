package com.yq.eie.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by yinqi on 2017/9/19.
 */

public class PlayService extends Service {

    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class MyBinder extends Binder {

    }

    /**
     * 用户操作发出的广播
     */
    public class ActionReceiver extends BroadcastReceiver {

        public static final String ACTION_PLAY_ALL_IN_ORDER = "PLAY_IN_ORDER";//按顺序播放全部


        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {

                case ACTION_PLAY_ALL_IN_ORDER:

                    break;
            }

        }


    }


}
