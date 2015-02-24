package pr.jason.myuipratice.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Jaesin on 2015-02-23.
 */
public class DisplayConfig {

    Context mContext;
    Activity activity;
    DisplayMetrics outMetrics;
    Display dis;
    public DisplayConfig(Context mContext,Activity activity){
        this.mContext = mContext;
        this.activity = activity;
        outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        dis =  ((WindowManager) mContext.getSystemService(mContext.WINDOW_SERVICE)).getDefaultDisplay();
    }

    public final int getDisplayWidth(){
        int width = 0;
        if (  Integer.valueOf(android.os.Build.VERSION.SDK_INT) < 13 ) {
            width = dis.getWidth();
        } else {
            Point size = new Point();
            dis.getSize(size);
            width = size.x;
        }
        return  width;

    }

    public final int getDisplayHeight(){
        int height = 0;
        if (  Integer.valueOf(android.os.Build.VERSION.SDK_INT) < 13 ) {
            height = dis.getHeight();
        } else {
            Point size = new Point();
            dis.getSize(size);
            height = size.y;
        }
        return  height;

    }

    public static float convertDpToPixel(float dp,Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

}
