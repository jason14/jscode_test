package pr.jason.myuipratice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import pr.jason.myuipratice.preferences.MainColorSelectorActivity;
import pr.jason.myuipratice.preferences.ThemeActivity;
import pr.jason.myuipratice.util.ImageWriteReadManager;
import pr.jason.myuipratice.util.SettingInfo;

/**
 * Created by Jaesin on 2015-03-12.
 */
public class SettingFragment extends Fragment {
    Context mContext;
    ImageWriteReadManager imageWriteReadManager;
    ListView listView;
    SettingFragment mSettingFragment;
    public static SettingFragment newInstance(int page,String title){
        SettingFragment settingFragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putInt("somePage",page);
        args.putString("someTitle",title);
        settingFragment.setArguments(args);
        return settingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        mSettingFragment = this;
    }
    public  String MAIN_COLOR;
    public  String GENERAL;
    public  String THEME;
    public  String BUTTON ;
    public  String WALLPAPER ;

    public  String FAVORITE;
    public  String COUNT_OF_FAVORITE;
    public  String SIZE_OF_FAVORITE;

    public  String KEYPAD;
    public  String SIZE_OF_KEYPAD;
    public  String KEYPAD_LANGUAGE;
    public  String BEEP_SOUND;

    public  String CONTACTS;
    public  String INDEX;
    public  String PREMIUM;
    public  String IN_APP_STORE;
    public  String WRITE_A_REVIEW;

    private  int  MAIN_COLOR_REQUEST_CODE = 1001;
    private int THEME_REQUEST_CODE = 1002;
    View generalHeadView;
    TextView generalHeadTV;
    View mainColorChildView;
    TextView mainColorChildTV;
    View themeChildView;
    TextView themeChildTV;
    View buttonChildView;
    TextView buttonChildTV;
    View wallPaperChildView;
    TextView wallPaperChildTV;
    View favoriteHeadView;
    TextView favoriteHeadTV;
    View countOfFavoriteChildView;
    TextView countOfFavoriteChildTV;
    View sizeOfFavoriteChildView;
    TextView sizeOfFavoriteChildTV;
    View keypadHeadView;
    TextView keypadHeadTV;
    View sizeOfKeypadChildView;
    TextView sizeOfKeypadChildTV;
    View keypadLanguageChildView;
    TextView keypadLanguageChildTV;
    View beepSoundChildView;
    TextView beepSoundChildTV;
    View contactsHeadView;
    TextView contactsHeadTV;
    View indexChildView;
    TextView indexChildTV;
    View premiumHeadView;
    TextView premiumHeadTV;
    View inAppStoreChildView;
    TextView inAppStoreChildTV;
    View writeAReviewChildView;
    TextView writeAReviewChildTV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.setting_fragment,container,false);
        initStringValue();
        generalHeadView = v.findViewById(R.id.general_head);

        generalHeadTV =  (TextView)generalHeadView.findViewById(R.id.head_title);
        mainColorChildView = v.findViewById(R.id.main_color_child);
        mainColorChildTV =  (TextView)mainColorChildView.findViewById(R.id.child_title);
        themeChildView = v.findViewById(R.id.theme_child);
        themeChildTV =  (TextView)themeChildView.findViewById(R.id.child_title);
        buttonChildView = v.findViewById(R.id.button_child);
        buttonChildTV =  (TextView)buttonChildView.findViewById(R.id.child_title);
        wallPaperChildView = v.findViewById(R.id.wallpaper_child);
        wallPaperChildTV =  (TextView)wallPaperChildView.findViewById(R.id.child_title);
        generalHeadTV.setText(GENERAL);

        mainColorChildTV.setText(MAIN_COLOR);
        themeChildTV.setText(THEME);
        buttonChildTV.setText(BUTTON);
        wallPaperChildTV.setText(WALLPAPER);
        wallPaperChildView.findViewById(R.id.child_preference_view_end_line).setVisibility(View.GONE);
        mainColorChildView.setOnClickListener(mClickListener);
        themeChildView.setOnClickListener(mClickListener);

        favoriteHeadView = v.findViewById(R.id.favorite_head);
        favoriteHeadTV =  (TextView)favoriteHeadView.findViewById(R.id.head_title);
        countOfFavoriteChildView = v.findViewById(R.id.count_of_favorite_child);
        countOfFavoriteChildTV =  (TextView)countOfFavoriteChildView.findViewById(R.id.child_title);
        sizeOfFavoriteChildView = v.findViewById(R.id.size_of_favorite_child);
        sizeOfFavoriteChildTV =  (TextView)sizeOfFavoriteChildView.findViewById(R.id.child_title);
        favoriteHeadTV.setText(FAVORITE);
        countOfFavoriteChildTV.setText(COUNT_OF_FAVORITE);
        sizeOfFavoriteChildTV.setText(SIZE_OF_FAVORITE);
        sizeOfFavoriteChildView.findViewById(R.id.child_preference_view_end_line).setVisibility(View.GONE);

        keypadHeadView = v.findViewById(R.id.keypad_head);
        keypadHeadTV =  (TextView)keypadHeadView.findViewById(R.id.head_title);
        sizeOfKeypadChildView = v.findViewById(R.id.size_of_keypad_child);
        sizeOfKeypadChildTV =  (TextView)sizeOfKeypadChildView.findViewById(R.id.child_title);
        keypadLanguageChildView = v.findViewById(R.id.keypad_language_child);
        keypadLanguageChildTV =  (TextView)keypadLanguageChildView.findViewById(R.id.child_title);
        beepSoundChildView = v.findViewById(R.id.beep_sound_child);
        beepSoundChildTV =  (TextView)beepSoundChildView.findViewById(R.id.child_title);
        keypadHeadTV.setText(KEYPAD);
        sizeOfKeypadChildTV.setText(SIZE_OF_KEYPAD);
        keypadLanguageChildTV.setText(KEYPAD_LANGUAGE);
        beepSoundChildTV.setText(BEEP_SOUND);
        beepSoundChildView.findViewById(R.id.child_preference_view_end_line).setVisibility(View.GONE);

        contactsHeadView = v.findViewById(R.id.contacts_head);
        contactsHeadTV =  (TextView)contactsHeadView.findViewById(R.id.head_title);
        indexChildView = v.findViewById(R.id.index_child);
        indexChildTV =  (TextView)indexChildView.findViewById(R.id.child_title);
        contactsHeadTV.setText(CONTACTS);
        indexChildTV.setText(INDEX);
        indexChildView.findViewById(R.id.child_preference_view_end_line).setVisibility(View.GONE);

        premiumHeadView = v.findViewById(R.id.premium_head);
        premiumHeadTV =  (TextView)premiumHeadView.findViewById(R.id.head_title);
        inAppStoreChildView = v.findViewById(R.id.in_app_store_child);
        inAppStoreChildTV =  (TextView)inAppStoreChildView.findViewById(R.id.child_title);
        writeAReviewChildView = v.findViewById(R.id.write_a_review_child);
        writeAReviewChildTV =  (TextView)writeAReviewChildView.findViewById(R.id.child_title);

        premiumHeadTV.setText(PREMIUM);
        inAppStoreChildTV.setText(IN_APP_STORE);
        writeAReviewChildTV.setText(WRITE_A_REVIEW);
        writeAReviewChildView.findViewById(R.id.child_preference_view_end_line).setVisibility(View.GONE);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setViewSetting();
    }

    private void setViewSetting(){
        generalHeadTV.setTextColor(SettingInfo.mMainColor);
        favoriteHeadTV.setTextColor(SettingInfo.mMainColor);
        keypadHeadTV.setTextColor(SettingInfo.mMainColor);
        contactsHeadTV.setTextColor(SettingInfo.mMainColor);
        premiumHeadTV.setTextColor(SettingInfo.mMainColor);

    }

    private void initStringValue(){
        MAIN_COLOR = getActivity().getResources().getString(R.string.setting_main_color);
        GENERAL = getActivity().getResources().getString(R.string.setting_general);
        THEME = getActivity().getResources().getString(R.string.setting_theme);
        BUTTON = getActivity().getResources().getString(R.string.setting_button);
        WALLPAPER = getActivity().getResources().getString(R.string.setting_wallpaper);

        FAVORITE = getActivity().getResources().getString(R.string.setting_favorite);
        COUNT_OF_FAVORITE = getActivity().getResources().getString(R.string.setting_count_of_favorite);
        SIZE_OF_FAVORITE = getActivity().getResources().getString(R.string.setting_size_of_favorite);

        KEYPAD = getActivity().getResources().getString(R.string.setting_keypad);
        SIZE_OF_KEYPAD = getActivity().getResources().getString(R.string.setting_size_of_keypad);
        KEYPAD_LANGUAGE = getActivity().getResources().getString(R.string.setting_keypad_language);
        BEEP_SOUND = getActivity().getResources().getString(R.string.setting_beep_sound);

        CONTACTS = getActivity().getResources().getString(R.string.setting_contacts);
        INDEX = getActivity().getResources().getString(R.string.setting_Index);

        PREMIUM = getActivity().getResources().getString(R.string.setting_premium);
        IN_APP_STORE = getActivity().getResources().getString(R.string.setting_in_app_store);
        WRITE_A_REVIEW = getActivity().getResources().getString(R.string.setting_write_a_review);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            switch(v.getId()){
                case R.id.main_color_child:
                    intent = new Intent(getActivity(),MainColorSelectorActivity.class);
                    mSettingFragment.startActivityForResult(intent, MAIN_COLOR_REQUEST_CODE);
                    break;
                case R.id.theme_child:
                    intent = new Intent(getActivity(),ThemeActivity.class);
                    mSettingFragment.startActivityForResult(intent, THEME_REQUEST_CODE);
                    break;
                case R.id.button_child:

                    break;
                case R.id.wallpaper_child:

                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //imageWriteReadManager.onActivityResult( requestCode,  resultCode,  data);

    }


}
