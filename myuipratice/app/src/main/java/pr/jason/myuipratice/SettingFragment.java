package pr.jason.myuipratice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import pr.jason.myuipratice.util.ImageWriteReadManager;

/**
 * Created by Jaesin on 2015-03-12.
 */
public class SettingFragment extends Fragment {
    Context mContext;
    ImageWriteReadManager imageWriteReadManager;
    ListView listView;
    private String[] settingList = {"Main Color"};
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
    public static final String MAIN_COLOR = "Main Color";
    public static final String GENERAL = "General";
    public static final String THEME = "Theme";
    public static final String BUTTON = "Button";
    public static final String Wallpaper = "Wallpaper";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.setting_fragment,container,false);
        View generalHeadView = v.findViewById(R.id.general_head);

        TextView generalHeadTV =  (TextView)generalHeadView.findViewById(R.id.head_title);
        View mainColorChildView = v.findViewById(R.id.main_color_child);
        TextView mainColorChildTV =  (TextView)mainColorChildView.findViewById(R.id.child_title);
        View themeChildView = v.findViewById(R.id.theme_child);
        TextView themeChildTV =  (TextView)themeChildView.findViewById(R.id.child_title);
        View buttonChildView = v.findViewById(R.id.button_child);
        TextView buttonChildTV =  (TextView)buttonChildView.findViewById(R.id.child_title);
        View wallPaperChildView = v.findViewById(R.id.wallpaper_child);
        TextView wallPaperChildTV =  (TextView)wallPaperChildView.findViewById(R.id.child_title);
        generalHeadTV.setText(GENERAL);
        mainColorChildTV.setText(MAIN_COLOR);
        themeChildTV.setText(THEME);
        buttonChildTV.setText(BUTTON);
        wallPaperChildTV.setText(Wallpaper);
        wallPaperChildView.findViewById(R.id.child_preference_view_end_line).setVisibility(View.GONE);
        mainColorChildView.setOnClickListener(mClickListener);
        return v;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.main_color_child:

                    break;
                case R.id.theme_child:

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
        imageWriteReadManager.onActivityResult( requestCode,  resultCode,  data);

    }

    public class SettingAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private Context mContext;
        private ViewHolder viewHolder;

        public SettingAdapter(Context context){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        class ViewHolder{

            TextView textView;

        }

        @Override
        public int getCount() {
            return settingList.length;
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

            if(convertView==null){
                viewHolder = new ViewHolder();
                v = inflater.inflate(R.layout.setting_row, parent, false);
                viewHolder.textView =(TextView)v.findViewById(R.id.textview);
                v.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)v.getTag();
            }
            viewHolder.textView.setText(settingList[position]);
            return v;
        }
    }
}
