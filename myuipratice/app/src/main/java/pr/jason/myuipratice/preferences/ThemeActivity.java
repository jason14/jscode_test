package pr.jason.myuipratice.preferences;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import pr.jason.myuipratice.R;
import pr.jason.myuipratice.util.PreferenceManager;
import pr.jason.myuipratice.util.SettingInfo;
import pr.jason.myuipratice.widget.MyCustomActivity;

/**
 * Created by Jaesin on 2015-03-20.
 */
public class ThemeActivity extends MyCustomActivity {
    ListView mThemeListView;
    private PreferenceManager mPreferenceManager;
    private String mThemes[];
    private int savedThemePosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_activity);
        mPreferenceManager = new PreferenceManager(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView mTitle = (TextView)toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(getResources().getString(R.string.setting_theme).toString());
        mTitle.setTextColor(getResources().getColor(R.color.colorControlNormal));
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        mThemeListView = (ListView)findViewById(R.id.listview);
        String tmpThemes[] = {getResources().getString(R.string.basic_white),getResources().getString(R.string.basic_color),getResources().getString(R.string.basic_white_color),
                getResources().getString(R.string.basic_deep_color),getResources().getString(R.string.premium_black),getResources().getString(R.string.premium_white_blur_back),
                getResources().getString(R.string.premium_black_blur_back)};
        mThemes = tmpThemes;
        ThemeListAdapter themeListAdapter = new ThemeListAdapter(this);
        mThemeListView.setAdapter(themeListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        savedThemePosition = SettingInfo.mThemePosition;
    }

    private class ThemeListAdapter extends BaseAdapter{

        private Context mContext;
        private LayoutInflater inflater;
        private ViewHolder viewHolder;
        private class ViewHolder{
            TextView titleTV;
            ImageView checkedIV;
        }
        public ThemeListAdapter(Context context){
            mContext = context;
            inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);        }

        @Override
        public int getCount() {
            return mThemes.length;
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
                v = inflater.inflate(R.layout.theme_row, parent, false);
                viewHolder.checkedIV = (ImageView)v.findViewById(R.id.checked_img);
                viewHolder.titleTV =(TextView)v.findViewById(R.id.title);
                v.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)v.getTag();
            }
            viewHolder.titleTV.setText(mThemes[position]);
            if(position == savedThemePosition){
                viewHolder.checkedIV.setVisibility(View.VISIBLE);
            }else{
                viewHolder.checkedIV.setVisibility(View.GONE);
            }

            return v;
        }
    }

}
