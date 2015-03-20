package pr.jason.myuipratice;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import pr.jason.myuipratice.util.DisplayConfig;
import pr.jason.myuipratice.util.SettingInfo;
import pr.jason.myuipratice.util.SoundSearcher;
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
    private ArrayList<ContactsClass> resultsArray;
    private RelativeLayout searchBar;
    private EditText searchEditText;
    private ImageView searchIconImg;
    float searchBarHeight;
    private static final int SCROLL_STOP = 0;
    private static  final int SCROLL_UP = 2;
    private static final int SCROLL_DOWN = 1;
    private boolean isVisibleSearchView = true;
    private boolean isOnAnimation = false;
    private boolean isDoneInitListView =false;
    private ContactAdapter contactAdapter;
    private int dummyItemHeight = 0;
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
        listView = (IndexableListView)view.findViewById(R.id.listview);

        //연락처 목록 받기
        if(resultsArray == null){
            contactsArray = new ArrayList<ContactsClass>();
            resultsArray = new ArrayList<ContactsClass>();
            contactsArray = MainActivity.getPhoneBooKList(false,getActivity().getApplicationContext());
            setSortArrayList();
            resultsArray = (ArrayList<ContactsClass>)contactsArray.clone();
            contactAdapter = new ContactAdapter(context,R.layout.contacts_row,resultsArray, MainActivity.options);
            listView.setAdapter(contactAdapter);
        }
        listView.setFastScrollEnabled(true);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int lastFirstVisibleItem;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int scrolly = getFirstChildViewScrollY();
                if(view.getFirstVisiblePosition() == 0&&scrolly==0){
                    isListViewScrollTop = true;
                }else{
                    isListViewScrollTop = false;
                }

                int scrollDirection = getScrollDirection(firstVisibleItem,lastFirstVisibleItem);
                if(!isOnAnimation) {
                    if(!listView.getmScroller().getIsIndexMove()){
                        startSearchBarAni(scrollDirection);
                    }else{
                        listView.getmScroller().setIsIndexMove(false);
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }
        });


        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(MainActivity.isOnfocusScrollView){
                    listView.smoothScrollToPosition(0);
                    listView.invalidate();
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

        searchBarHeight = 0f;
        ViewTreeObserver vto = listView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(listView.getChildAt(0)!=null){
                    if(!isDoneInitListView) {
                        dummyItemHeight = (int)DisplayConfig.convertDpToPixel(48,getActivity().getApplicationContext());
                        searchBarHeight = dummyItemHeight;
                        RelativeLayout.LayoutParams searchLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) searchBarHeight);
                        searchBar.setLayoutParams(searchLP);
                        listView.setCustomTopMargin((int) searchBarHeight);
                        isDoneInitListView = true;

                    }
                }
            }
        });
        searchEditText = (EditText)view.findViewById(R.id.search_et);
        searchEditText.addTextChangedListener(textWatcher);
        searchBar = (RelativeLayout)view.findViewById(R.id.search_bar_layout);
        searchBar.setBackgroundColor(SettingInfo.mMainColor);
        return view;
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            if(s.length()==0){
                resultsArray.clear();
                resultsArray = (ArrayList<ContactsClass>)contactsArray.clone();
                contactAdapter = new ContactAdapter(context,R.layout.contacts_row,resultsArray, MainActivity.options);
                listView.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();
            }else{
                resultsArray.clear();
                for(int i = 1; i < contactsArray.size(); i++){
                    String name = contactsArray.get(i).friendName;
                    String keyWord = s.toString();
                    boolean isRight = SoundSearcher.matchString(name,keyWord);
                    if(isRight){
                        resultsArray.add(contactsArray.get(i));
                    }
                }
                ContactsClass dummyClass = new ContactsClass();
                String dummyImgName = "drawable://"+ R.drawable.dummy_img;
                dummyClass.friendName = "0";
                dummyClass.friendPictureUrl = dummyImgName;
                resultsArray.add(0,dummyClass);
                contactAdapter = new ContactAdapter(context,R.layout.contacts_row,resultsArray, MainActivity.options);
                listView.setAdapter(contactAdapter);
                contactAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public ArrayList<ContactsClass> getResultsArray(String keyword){
        resultsArray.clear();

        return resultsArray;
    }

    @Override
    public void onResume() {
        checkScrollPostion();
        isDoneInitListView = false;

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

    ObjectAnimator searchViewAnim;
    private void startSearchBarAni(int scrollDirection){
        switch(scrollDirection){
            case SCROLL_UP:
                if(!isVisibleSearchView){
                    listView.setIsVisibleSearchEt(true);
                    isOnAnimation = true;
                    searchViewAnim = ObjectAnimator.ofFloat(searchBar,"translationY",-searchBarHeight,0);
                    searchViewAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isVisibleSearchView = true;
                            isOnAnimation = false;

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    searchViewAnim.setDuration(200);
                    searchViewAnim.start();

                }
                break;
            case SCROLL_DOWN:
                if(isVisibleSearchView){
                    isOnAnimation = true;
                    listView.setIsVisibleSearchEt(false);
                    searchViewAnim = ObjectAnimator.ofFloat(searchBar,"translationY",0,-searchBarHeight);
                    searchViewAnim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isVisibleSearchView = false;
                            isOnAnimation = false;
                            //listView.setCustomTopMargin(0);

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    searchViewAnim.setDuration(300);
                    searchViewAnim.start();

                }
                break;
        }
    }

    private int getScrollDirection(int firstVisibleItem, int lastFirstViisbleItem){

        if(lastFirstViisbleItem > firstVisibleItem){
            return SCROLL_UP;
        }else if(lastFirstViisbleItem < firstVisibleItem){
            return SCROLL_DOWN;
        }else{
            return SCROLL_STOP;
        }
    }

    private void checkScrollPostion(){
        int scrolly = getFirstChildViewScrollY();
        if(listView !=null) {
            if (listView.getFirstVisiblePosition() == 0 && scrolly < 5) {
                isListViewScrollTop = true;
            } else {
                isListViewScrollTop = false;
            }
        }
    }

    private int getFirstChildViewScrollY(){
        int scrolly = 0;
        View c = listView.getChildAt(0);
        try {
            scrolly = -c.getTop() + listView.getFirstVisiblePosition() * c.getHeight();
            return scrolly;
        }catch(NullPointerException e){
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

        Collections.sort(contactsArray, comparator);
        ContactsClass dummyClass = new ContactsClass();
        String dummyImgName = "drawable://"+ R.drawable.dummy_img;

        dummyClass.friendName = "0";
        dummyClass.friendPictureUrl = dummyImgName;
        contactsArray.add(0,dummyClass);

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
