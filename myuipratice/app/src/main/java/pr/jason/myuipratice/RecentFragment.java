package pr.jason.myuipratice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jaesin on 2015-02-05.
 */
public class RecentFragment extends Fragment {
    private String title;
    private int page;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_fragment,container,false);

        return view;
    }

}
