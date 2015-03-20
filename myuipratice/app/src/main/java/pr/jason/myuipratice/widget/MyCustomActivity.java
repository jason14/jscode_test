package pr.jason.myuipratice.widget;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import pr.jason.myuipratice.util.SettingInfo;

/**
 * Created by Jaesin on 2015-03-20.
 */
public class MyCustomActivity extends ActionBarActivity{

    @Override
    protected void onResume() {
        super.onResume();
        checkSettingInfo();
    }

    private void checkSettingInfo(){ //preference setting 초기화되었는지 확인
        Log.e("main color", SettingInfo.mIsInit + " SettingInfo.mIsInit");
        if(SettingInfo.mIsInit){
           return;
        }else{
            SettingInfo.setCurrentSetting(this);
        }
    }
}
