package pr.jason.myuipratice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by Jaesin on 2015-02-05.
 */
public class RecentFragment extends Fragment {
    private String title;
    private int page;
    private ArrayList<ContactsClass> callsArray;
    DisplayImageOptions options;

    public static RecentFragment newInstance(int page,String title){
        RecentFragment recentFragment = new RecentFragment();
        Bundle args = new Bundle();
        args.putInt("somePage",page);
        args.putString("someTitle",title);
        recentFragment.setArguments(args);

        return recentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page= getArguments().getInt("somePage",0);
        title = getArguments().getString("someTitle");
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(1000))
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_fragment,container,false);
        callsArray = new ArrayList<ContactsClass>();
        callsArray = ((MainActivity)MainActivity.mContext).getCallsList();
        RecentAdapter recentAdapter = new RecentAdapter(getActivity().getApplicationContext(),callsArray,options);
        ListView listView =(ListView)view.findViewById(R.id.listview);
        listView.setAdapter(recentAdapter);

        return view;
    }

}
