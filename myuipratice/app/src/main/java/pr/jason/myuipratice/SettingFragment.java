package pr.jason.myuipratice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
                //getActionBar().setTitle("설정");
        mContext = getActivity().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.setting_fragment,container,false);
        listView = (ListView)v.findViewById(R.id.listview);
        SettingAdapter settingAdapter = new SettingAdapter();
        listView.setAdapter(settingAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        imageWriteReadManager = new ImageWriteReadManager(getActivity(),mContext);
                        break;
                }
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageWriteReadManager.onActivityResult( requestCode,  resultCode,  data);

    }

    public class SettingAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private Context mContext;
        private ViewHolder viewHolder;


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
