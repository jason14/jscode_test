package pr.jason.myuipratice;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import pr.jason.myuipratice.widget.IndexableListView;

/**
 * Created by Jaesin on 2015-02-05.
 */
public class ContactsFragment extends Fragment{

    private String title;
    private int page;
    private Context context;
    private ContactsClass contactsClass;
    private ArrayList<ContactsClass> contactsArray;
    private MainActivity mainActivity;
    private boolean isListViewScrollTop = true;
    IndexableListView listView;

    public static ContactsFragment newInstance(int page,String title){
        ContactsFragment contactsFragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putInt("somePage",page);
        args.putString("someTitle",title);
        contactsFragment.setArguments(args);
        return contactsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page= getArguments().getInt("somePage",0);
        title = getArguments().getString("someTitle");
        context = getActivity().getApplicationContext();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment,container,false);
        //연락처 목록 받기
        contactsArray = new ArrayList<ContactsClass>();
        contactsArray = ((MainActivity)MainActivity.mContext).getPhoneBooKList(false);

        //getPhoneBoolList();
        //정렬
        setSortArrayList();
        listView = (IndexableListView)view.findViewById(R.id.listview);
        ContactAdapter contactAdapter = new ContactAdapter(context,R.layout.contacts_row,contactsArray, MainActivity.options);
        listView.setAdapter(contactAdapter);
        listView.setFastScrollEnabled(true);
        Log.e("연락처 배열 개수", "" + contactsArray.size());
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int scrolly = getScrollY();

                if(scrolly==0&&scrollState == 0){
                    isListViewScrollTop = true;
                }else{
                    isListViewScrollTop = false;
                }
                if(MainActivity.isOnfocusScrollView == true){
                    view.scrollTo(0,0);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(MainActivity.isOnfocusScrollView){

                }else{

                    if(isListViewScrollTop){
                        if(isDragDown(event)){
                            MainActivity.isOnReadyScrollView = true;
                            MainActivity.main_layout.requestDisallowInterceptTouchEvent(true);
                        }
                    }else{
                        MainActivity.isOnReadyScrollView = false;
                    }
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        checkScrollPostion();
        super.onResume();
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



    private void checkScrollPostion(){
        int scrolly = getScrollY();
        Log.e("onResume scroll 위치",""+scrolly);
        if(scrolly==0){
            isListViewScrollTop = true;
        }else {
            isListViewScrollTop = false;
        }
    }

    private int getScrollY(){
        int scrolly = 0;
        View c = listView.getChildAt(0);
        try {
            scrolly = -c.getTop() + listView.getFirstVisiblePosition() * c.getHeight();
            return scrolly;
        }catch(NullPointerException e){
            Log.e("listView.getFirstVisiblePosition()", ""+ e);
            return 0;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AnimateFirstDisplayListener.displayedImages.clear();
    }

    public void setSortArrayList(){
        final Comparator<ContactsClass> comparator = new Comparator<ContactsClass>() {
            @Override
            public int compare(ContactsClass lhs, ContactsClass rhs) {
                return Collator.getInstance().compare(lhs.friendName,rhs.friendName);
            }
        };

        Collections.sort(contactsArray,comparator);

    }


    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
