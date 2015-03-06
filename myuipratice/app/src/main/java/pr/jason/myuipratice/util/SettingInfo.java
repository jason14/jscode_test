package pr.jason.myuipratice.util;

import android.content.Context;

/**
 * Created by Jaesin on 2015-02-25.
 */
public class SettingInfo {

    public static final String DIAL_BUTTON_IMAGE_RESOURCE = "DIAL_BUTTON_IMAGE_RESOURCE";
    public static final String DIAL_BUTTON_IMAGE_COLOR = "DIAL_BUTTON_IMAGE_COLOR";
    public static final String APP_BACKGROUND_IMAGE = "APP_BACKGROUND_IMAGE";

    public static String dialButtonImageUri;
    public static String dialButtonImageResourceUri;
    public static String appBackgroundImageUri;

    public static void setCurrentSetting(Context context){
        PreferenceManager preferenceManager = new PreferenceManager(context);
        dialButtonImageUri = preferenceManager.getValue(DIAL_BUTTON_IMAGE_RESOURCE,"");
        dialButtonImageResourceUri = preferenceManager.getValue(DIAL_BUTTON_IMAGE_COLOR,"");
        appBackgroundImageUri = preferenceManager.getValue(APP_BACKGROUND_IMAGE,"");
    }



}
