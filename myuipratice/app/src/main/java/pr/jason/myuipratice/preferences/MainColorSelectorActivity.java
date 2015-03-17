package pr.jason.myuipratice.preferences;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pr.jason.myuipratice.R;
import pr.jason.myuipratice.util.ColorCode;
import pr.jason.myuipratice.util.SettingInfo;

/**
 * Created by Jaesin on 2015-03-16.
 */

public class MainColorSelectorActivity extends ActionBarActivity{

    //Tone-down 각 ColorCode 배경색 500 글자색 900 Pastel CorlorCode 배경색 500 글자색 200

    private int bgColors[] = {ColorCode.BLUE_GREY, ColorCode.GREY ,ColorCode.BROWN, ColorCode.DEEP_ORANGE,
            ColorCode.ORANGE, ColorCode.AMBER ,ColorCode.YELLOW, ColorCode.LIME,
            ColorCode.LIGHT_GREEN, ColorCode.GREEN ,ColorCode.TEAL, ColorCode.CYAN,
            ColorCode.LIGHT_BLUE, ColorCode.BLUE ,ColorCode.INDIGO, ColorCode.DEEP_PURPLE,
            ColorCode.PURPLE, ColorCode.PINK , ColorCode.RED };

    private int bgColorsName[] = {R.string.blue_grey,R.string.grey,R.string.brwon,R.string.deep_orange,
            R.string.orange,R.string.amber,R.string.yellow,R.string.lime,
            R.string.light_green,R.string.green,R.string.teal,R.string.cyan,
            R.string.light_blue,R.string.blue,R.string.indigo,R.string.deep_purple,
            R.string.purple,R.string.pink,R.string.red };

    private int colorType = 0;

    private void checkSettingInfo(){ //preference setting 초기화되었는지 확인
        Log.e("main color", SettingInfo.mIsInit + " SettingInfo.mIsInit");
        if(SettingInfo.mIsInit){
            colorType = SettingInfo.mMainColorType;
        }else{
            SettingInfo.setCurrentSetting(this);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_color_selector_activity);
        checkSettingInfo();
        ListView colorList = (ListView)findViewById(R.id.color_list);
        SelectorAdapter selectorAdapter = new SelectorAdapter(this);
        colorList.setAdapter(selectorAdapter);
    }

    private class SelectorAdapter extends BaseAdapter{
        private LayoutInflater inflater;

        private ViewHolder viewHolder;
        private Context mContext;
        private class ViewHolder{
            TextView nameTV;
            LinearLayout bgLayoutView;
        }

        public SelectorAdapter(Context context){
            mContext = context;
            inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return bgColorsName.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null){
                viewHolder = new ViewHolder();
                v = inflater.inflate(R.layout.main_color_seletor_row, parent, false);
                viewHolder.bgLayoutView = (LinearLayout)v.findViewById(R.id.color_list_row_layout);
                viewHolder.nameTV =(TextView)v.findViewById(R.id.color_name_tv);
                v.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)v.getTag();
            }
            Log.e("main color",bgColorsName[position]+" ");
            viewHolder.nameTV.setText(bgColorsName[position]);
            viewHolder.nameTV.setTextColor(Color.parseColor("#ffffff"));
            viewHolder.bgLayoutView.setBackgroundColor(setBgColor(bgColors[position]));
            //viewHolder.bgLayoutView.setBackgroundColor();

            //viewHolder.bgLayoutView.setBackground(setBgColor(bgColors[position]));
            return v;
        }
        static final String hexaDecimalPattern = "^0x([\\da-fA-F]{1,8})$";

        private int setBgColor(int color) {
            //int alpha = (color >>> 24) & 0xFF;

            String hexForRGBConversion =
                   "" + color;
            String rgbValue = "";

            Pattern hexPattern = Pattern.compile(hexaDecimalPattern);
            Matcher hexMatcher = hexPattern.matcher(hexForRGBConversion);

            int red = (color >>> 16) & 0xFF0000;
            int green = (color >>> 8) & 0xFF00;
            int blue = (color >>> 0) & 0xFF;

            Log.e("main ", "hexForRGBConversion: "+ hexForRGBConversion + "");

            Log.e("main color red : ", red + "");
            Log.e("main color green : ", green + "");
            Log.e("main color blue : ", blue + "");

            int rgbColor = Color.rgb(red, green, blue);
            if (colorType == SettingInfo.COLOR_TYPE_BASIC) {
                return rgbColor;
            } else {
                return Color.parseColor("#000000");
            }
        }
    }


}
