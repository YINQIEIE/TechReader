package com.yq.eie.media;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.MainThread;
import android.support.annotation.UiThread;
import android.util.Log;

import com.yq.eie.IMediaAidlInterface;

/**
 * Created by Administrator on 2017/12/25.
 */

public class MusicPlayerManager {

    public static IMediaAidlInterface musicService;

    public static final ServiceToken bindService(final Context context, final ServiceConnection connectionCallback) {
        Activity realActivity = ((Activity) context).getParent();
        if (realActivity == null)
            realActivity = (Activity) context;
        final ContextWrapper contextWrapper = new ContextWrapper(realActivity);
        contextWrapper.startService(new Intent(contextWrapper, MusicService.class));
        final ServiceBinder binder = new ServiceBinder(context, connectionCallback);
        if (contextWrapper.bindService(new Intent().setClass(contextWrapper, MusicService.class), binder, 0))
            Log.i("service", "bind success");
        return new ServiceToken(contextWrapper);
    }


    @UiThread
    @MainThread
    public static final class ServiceBinder implements ServiceConnection {

        private Context context;
        private ServiceConnection callback;

        public ServiceBinder(Context context, ServiceConnection callback) {
            this.context = context;
            this.callback = callback;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = IMediaAidlInterface.Stub.asInterface(service);
            if (callback != null)
                callback.onServiceConnected(name, service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (callback != null)
                callback.onServiceDisconnected(name);
            musicService = null;
        }

    }

    /**
     * 记录绑定的 service 信息，解绑时使用
     */
    public static final class ServiceToken {

        public ContextWrapper mWrappedContext;

        public ServiceToken(final ContextWrapper context) {
            mWrappedContext = context;
        }
    }
}


