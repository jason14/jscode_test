package pr.jason.myuipratice.util;

import android.content.Context;
import android.graphics.Color;

/**
 * Created by Jaesin on 2015-02-25.
 */
public class SettingInfo {

    public static final String BUTTON_IMAGE_RESOURCE_URI = "BUTTON_IMAGE_RESOURCE_URI";
    public static final String BUTTON_IMAGE_URI = "BUTTON_IMAGE_URI";
    public static final String WALLPAPER_CATEGORY = "WALLPAPER_CATEGORY"; // Simple, Featured WallPaper, My photo  0,1,2
    public static final String WALLPAPER_URI = "WALLPAPER_URI";
    public static final String THEME_COLOR = "THEME_COLOR";
    public static final String MAIN_COLOR = "MAIN_COLOR";
    public static final String COUNT_OF_FAVORITE = "COUNT_OF_FAVORITE";
    public static final String SIZE_OF_FAVORITE = "SIZE_OF_FAVORITE";
    public static final String SIZE_OF_KEYPAD = "SIZE_OF_KEYPAD";
    public static final String KEYPAD_LANGUAGE = "KEYPAD_LANGUAGE";
    public static final String BEEP_SOUND = "BEEP_SOUND";
    public static final String INDEX = "INDEX";

    //public static final String
    public static String mButtonImageUri;
    public static String mButtonImageResourceUri;
    public static String mWallPaperUri;
    public static int mThemeColor;
    public static int mWallPaperCategory;
    public static int mMainColor;
    public static int mCountOfFavorite;
    public static int mSizeOfFavorite;
    public static int mSizeOfKeypad;
    public static int mKeypadLanguage;
    public static int mBeepSound;
    public static int mIndex;

    public static void setCurrentSetting(Context context){
        PreferenceManager preferenceManager = new PreferenceManager(context);
        mButtonImageUri = preferenceManager.getValue(BUTTON_IMAGE_URI,"");
        mButtonImageResourceUri = preferenceManager.getValue(BUTTON_IMAGE_RESOURCE_URI,"");
        mWallPaperCategory = preferenceManager.getValue(WALLPAPER_CATEGORY,0);
        mWallPaperUri = preferenceManager.getValue(WALLPAPER_URI,"");
        mThemeColor = preferenceManager.getValue(THEME_COLOR, Color.parseColor("#ffffff"));
        mMainColor = preferenceManager.getValue(MAIN_COLOR, Color.parseColor("#ffffff"));
        mCountOfFavorite = preferenceManager.getValue(COUNT_OF_FAVORITE, 0);
        mSizeOfFavorite =  preferenceManager.getValue(SIZE_OF_FAVORITE, 0);
        mSizeOfKeypad =  preferenceManager.getValue(SIZE_OF_KEYPAD, 0);
        mKeypadLanguage =  preferenceManager.getValue(KEYPAD_LANGUAGE, 0);
        mBeepSound =  preferenceManager.getValue(BEEP_SOUND, 0);
        mIndex =  preferenceManager.getValue(INDEX, 0);


    }



}
