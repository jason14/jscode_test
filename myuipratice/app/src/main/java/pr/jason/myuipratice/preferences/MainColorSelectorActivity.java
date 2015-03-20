package pr.jason.myuipratice.preferences;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import pr.jason.myuipratice.R;
import pr.jason.myuipratice.util.ColorCode;
import pr.jason.myuipratice.util.PreferenceManager;
import pr.jason.myuipratice.util.SettingInfo;
import pr.jason.myuipratice.widget.MyCustomActivity;

/**
 * Created by Jaesin on 2015-03-16.
 */

public class MainColorSelectorActivity extends MyCustomActivity {

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
    private PreferenceManager mPreferenceManager;
    private int colorType = 0;
    private int savedColorType = 0;
    private int mainColorPosition = 0;
    private int mSelectedMainColor = 0;
    private Button basic_type_btn, tone_down_type_btn, pastel_type_btn;
    private Button typeBtns[] = {basic_type_btn,tone_down_type_btn,pastel_type_btn};
    ListView colorList;
    SelectorAdapter selectorAdapter;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_color_selector_activity);
        mPreferenceManager = new PreferenceManager(this);
        savedColorType = SettingInfo.mMainColorType;
        mainColorPosition = SettingInfo.mMainColorPosition;
        colorList = (ListView)findViewById(R.id.color_list);
        typeBtns[0] = (Button)findViewById(R.id.basic_type_btn);
        typeBtns[1] = (Button)findViewById(R.id.tone_down_type_btn);
        typeBtns[2] = (Button)findViewById(R.id.pastel_type_btn);
        for(int i=0; i < typeBtns.length; i++){
            final int position = i;
            typeBtns[i].setOnClickListener(mTypeBtnsClickListener);
        }
        typeBtns[0].setTextColor(Color.parseColor("#333333"));
        typeBtns[1].setTextColor(Color.parseColor("#aaaaaa"));
        typeBtns[2].setTextColor(Color.parseColor("#aaaaaa"));

        selectorAdapter = new SelectorAdapter(this);
        colorList.setAdapter(selectorAdapter);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView)toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.setting_main_color).toString());
        mTitle.setTextColor(getResources().getColor(R.color.colorControlNormal));
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        colorList.setOnItemClickListener(mColorItemClickListener);
    }

    private AdapterView.OnItemClickListener mColorItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mainColorPosition = position;
            mPreferenceManager.put(SettingInfo.MAIN_COLOR_POSITION,position);
            mPreferenceManager.put(SettingInfo.MAIN_COLOR_TYPE,colorType);
            mSelectedMainColor = setBgColor(ColorCode.getColorValue(bgColors[position], 500));
            mPreferenceManager.put(SettingInfo.MAIN_COLOR,mSelectedMainColor);
            savedColorType = colorType;
            selectorAdapter.notifyDataSetChanged();
        }
    };

    private View.OnClickListener mTypeBtnsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i =0; i < typeBtns.length; i++){
                if(v.getId() == typeBtns[i].getId()){
                    typeBtns[i].setTextColor(Color.parseColor("#333333"));
                    colorType = i;
                }else{
                    typeBtns[i].setTextColor(Color.parseColor("#aaaaaa"));

                }
            }
            selectorAdapter.notifyDataSetChanged();

        }
    };

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
            Log.e("main color", bgColorsName[position] + " ");
            viewHolder.nameTV.setText(bgColorsName[position]);

            viewHolder.nameTV.setBackgroundColor(setBgColor(ColorCode.getColorValue(bgColors[position], 500)));
            if(colorType == SettingInfo.COLOR_TYPE_BASIC){
                viewHolder.nameTV.setTextColor(Color.parseColor("#ffffff"));

            }else if(colorType == SettingInfo.COLOR_TYPE_TONE_DOWN){
                viewHolder.nameTV.setTextColor(Color.parseColor("#30000000"));

            }else if(colorType == SettingInfo.COLOR_TYPE_PASTEL){
                viewHolder.nameTV.setTextColor(Color.parseColor("#80ffffff"));

            }
            if(position == mainColorPosition&&colorType==savedColorType) {
                viewHolder.bgLayoutView.setBackgroundColor(setBgColor(ColorCode.getColorValue(bgColors[position], 200)));
            }else{
                viewHolder.bgLayoutView.setBackgroundColor(setBgColor(ColorCode.getColorValue(bgColors[position], 500)));
            }
            return v;
        }


    }
    private int setBgColor(int color) {
        //int alpha = (color >>> 24) & 0xFF;


        int red = (color >>> 16) & 0xFF;
        int green = (color >>> 8) & 0xFF;
        int blue = (color >>> 0) & 0xFF;


        Log.e("main color red : ", red + "");
        Log.e("main color green : ", green + "");
        Log.e("main color blue : ", blue + "");

        int rgbColor = Color.rgb(red, green, blue);

        if (colorType == SettingInfo.COLOR_TYPE_BASIC) {
            return rgbColor;
        } else if(colorType == SettingInfo.COLOR_TYPE_TONE_DOWN){
            float[] hsv = new float[3];
            Color.RGBToHSV(red,green,blue,hsv);
            hsv[0] = hsv[0];
            hsv[1] = hsv[1] * 0.8f;
            hsv[2] = hsv[2];
            rgbColor = Color.HSVToColor(hsv);
            rgbColor = shadeColor(String.format("#%06X", 0xFFFFFF & rgbColor),-35);
            return rgbColor;
        }else if(colorType == SettingInfo.COLOR_TYPE_PASTEL){
            float[] hsv = new float[3];

            Color.RGBToHSV(red,green,blue,hsv);
            hsv[0] = hsv[0];
            hsv[1] = hsv[1] * 0.6f;
            hsv[2] = hsv[2];

            return Color.HSVToColor(hsv);
        }else{
            return Color.parseColor("#000000");
        }
    }

    private int shadeColor(String color, int percent){
        int R = Integer.parseInt(color.substring(1,3),16);
        int G = Integer.parseInt(color.substring(3,5),16);
        int B = Integer.parseInt(color.substring(5,7),16);

        R = (R * (100 + percent) / 100);
        G = (G * (100 + percent) / 100);
        B = (B * (100 + percent) / 100);

        R = (R<255)?R:255;
        G = (G<255)?G:255;
        B = (B<255)?B:255;

        String RR = ((Integer.toHexString(R).length()==1)?"0"+Integer.toHexString(R):Integer.toHexString(R));
        String GG = ((Integer.toHexString(G).length()==1)?"0"+Integer.toHexString(G):Integer.toHexString(G));
        String BB = ((Integer.toHexString(B).length()==1)?"0"+Integer.toHexString(B):Integer.toHexString(B));

        return Color.parseColor("#"+RR+GG+BB);
    }

}
