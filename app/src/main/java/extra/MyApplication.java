package extra;

import android.app.Application;
import android.content.Context;

/**
 * Created by nishant on 7/7/15.
 */
public class MyApplication extends Application {

    public static final String API_KEY="0202d13f1ba65cc7aa383b62c44adfc0";

    private static MyApplication sInstance;

    public static MyApplication getsInstance(){
        return sInstance;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }
}
