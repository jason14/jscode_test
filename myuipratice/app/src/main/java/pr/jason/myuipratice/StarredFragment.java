package pr.jason.myuipratice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Jaesin on 2015-02-05.
 */
public class StarredFragment extends Fragment{

    private String title;
    private int page;

    public static StarredFragment newInstance(int page,String title){
        StarredFragment starredFragment = new StarredFragment();
        Bundle args = new Bundle();
        args.putInt("somePage",page);
        args.putString("someTitle",title);
        starredFragment.setArguments(args);
        return starredFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page= getArguments().getInt("somePage",0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment,container,false);
        ArrayList<ContactsClass> contactsArray = new ArrayList<ContactsClass>();
        contactsArray = ((MainActivity)MainActivity.mContext).getPhoneBooKList(true);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new StarredAdapter(contactsArray));
        return view;
    }

}
