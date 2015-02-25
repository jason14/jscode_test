package pr.jason.myuipratice.util;

import android.content.Context;

/**
 * Created by Jaesin on 2015-02-25.
 */
public class Setting {

    public static final String DIAL_BUTTON_IMAGE_RESOURCE = "DIAL_BUTTON_IMAGE_RESOURCE";
    public static final String DIAL_BUTTON_IMAGE_COLOR = "DIAL_BUTTON_IMAGE_COLOR";
    public static final String APP_BACKGROUND_IMAGE = "APP_BACKGROUND_IMAGE";

    public static int dialButtonImage;
    public static int dialButtonImageResource;
    public static int appBackgroundImage;

    public static void setCurrentSetting(Context context){
        PreferenceManager preferenceManager = new PreferenceManager(context);
        dialButtonImage = preferenceManager.getValue(DIAL_BUTTON_IMAGE_RESOURCE,0);
        dialButtonImageResource = preferenceManager.getValue(DIAL_BUTTON_IMAGE_COLOR,0);
        appBackgroundImage = preferenceManager.getValue(APP_BACKGROUND_IMAGE,0);

    }


}
