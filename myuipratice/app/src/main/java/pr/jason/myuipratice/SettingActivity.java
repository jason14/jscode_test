package pr.jason.myuipratice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import pr.jason.myuipratice.util.ImageWriteReadManager;

/**
 * Created by Jaesin on 2015-03-06.
 */
public class SettingActivity extends PreferenceActivity {
    Context mContext;
    ImageWriteReadManager imageWriteReadManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_set_activity);
        //getActionBar().setTitle("설정");
        mContext = this;

        final Preference settingBg = (Preference)findPreference("keySettingBg");
        settingBg.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                imageWriteReadManager = new ImageWriteReadManager(SettingActivity.this,mContext);
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageWriteReadManager.onActivityResult( requestCode,  resultCode,  data);
    }
}
