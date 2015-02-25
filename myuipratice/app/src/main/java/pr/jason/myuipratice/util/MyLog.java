package pr.jason.myuipratice.util;

import android.util.Log;

/**
 * Created by Jaesin on 2015-02-25.
 */
public class MyLog {
    public static boolean logOn = true;
    public static void LogMessage(String tag,String log){
        if(logOn) {
            Log.e(tag, log);
        }
    }
}
