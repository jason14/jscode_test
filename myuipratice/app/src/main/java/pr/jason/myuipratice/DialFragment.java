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
public class DialFragment extends Fragment{

    private String title;
    private int page;

    public static DialFragment newInstance(int page,String title){
        DialFragment dialFragment = new DialFragment();
        Bundle args = new Bundle();
        args.putInt("somePage",page);
        args.putString("someTitle",title);
        dialFragment.setArguments(args);
        return dialFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page= getArguments().getInt("somePage",0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dial_fragment,container,false);

        return view;
    }

}
