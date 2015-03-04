package pr.jason.myuipratice.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by Jaesin on 2015-03-04.
 */
public class ResoursesManager {
    public static String getStringResource(String resName,Context context){
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(resName,"string",packageName);
        if(resId != 0) {
            return context.getResources().getString(resId);
        }else{
            return resName;
        }
    }
    public static float getDimensionResource(String resName,Context context){
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(resName,"dimen",packageName);

        return context.getResources().getDimension(resId);
    }

    public static int getColorResource(String resName,Context context){
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(resName,"color",packageName);

        return context.getResources().getColor(resId);
    }

    public static Drawable getDrawableResource(String resName,Context context){
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(resName,"drawable",packageName);

        return context.getResources().getDrawable(resId);
    }

}
