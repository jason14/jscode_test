package pr.jason.myuipratice;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

/**
 * Created by Jaesin on 2015-02-05.
 */
public class StarredFragment extends Fragment{

    private String title;
    private int page;
    public  DisplayImageOptions options;
    private boolean isListViewScrollTop = true;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
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
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_face_grey600_48dp)
                .showImageForEmptyUri(R.drawable.ic_face_grey600_48dp)
                .showImageOnFail(R.drawable.ic_face_grey600_48dp)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(1000))
                .build();
    }
    int scrolly = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorite_fragment,container,false);
        ArrayList<ContactsClass> contactsArray = new ArrayList<ContactsClass>();
        contactsArray = MainActivity.getPhoneBooKList(true,getActivity().getApplicationContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_margin);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new StarredAdapter(contactsArray,options));

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                scrolly = getScrollY();

                if(gridLayoutManager.findFirstVisibleItemPosition() == 0 &&scrolly<=0){
                    isListViewScrollTop = true;
                }else{
                    isListViewScrollTop = false;
                }
                if(MainActivity.isOnfocusScrollView == true){
                    recyclerView.scrollToPosition(0);
                }


            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MainActivity.isOnfocusScrollView){

                }else{
                    MainActivity.main_layout.requestDisallowInterceptTouchEvent(true);
                    if(isListViewScrollTop){
                        if(isDragDown(event)){
                            MainActivity.isOnReadyScrollView = true;
                            MainActivity.main_layout.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                return false;
            }
        });
        return view;
    }

    private int getScrollY(){
        int scrolly = 0;
        View c = recyclerView.getChildAt(0);
        try {
            scrolly = -c.getTop() + gridLayoutManager.findFirstVisibleItemPosition() * c.getHeight();
            return scrolly;
        }catch(NullPointerException e){
            Log.e("getFirstVisiblePosition", "" + e);
            return 0;
        }
    }

    private void checkScrollPostion(){
        int scrolly = getScrollY();
        Log.e("onResume scroll 위치",""+scrolly);
        if(recyclerView !=null) {
            if (gridLayoutManager.findFirstVisibleItemPosition() == 0 && scrolly == 0) {
                isListViewScrollTop = true;
            } else {
                isListViewScrollTop = false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkScrollPostion();
    }

    private boolean isDragDown(MotionEvent event){
        boolean result = false;
        float downX=0;
        float downY=0;
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            downX  = event.getX();
            downY = event.getY();
        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            int x = (int)(event.getX()-downX);
            int y = (int)(event.getY()-downY);
            if(y>0) {
                result = true;
            }
        }
        return result;
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration{
        private int space;

        public SpacesItemDecoration(int space){
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            /*if(parent.getChildPosition(view)==0){
                outRect.top = space;
            }*/
        }
    }

}
