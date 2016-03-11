package cn.hadcn.firememo;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * FireMemo
 * Created by 90Chris on 2016/3/11.
 */
public class FireMemo extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
