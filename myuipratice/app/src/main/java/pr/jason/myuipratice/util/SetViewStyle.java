package pr.jason.myuipratice.util;

import android.widget.TextView;

/**
 * Created by Jaesin on 2015-02-24.
 */
public class SetViewStyle {

    public static TextView setTextViewStyle(TextView tv,int textColor, int bgResource, int textSize, String text){
        tv.setTextColor(textColor);
        tv.setBackgroundResource(bgResource);
        tv.setTextSize(textSize);
        tv.setText(text);
        return tv;
    }
}
