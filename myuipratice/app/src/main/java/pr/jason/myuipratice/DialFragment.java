package pr.jason.myuipratice;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

import pr.jason.myuipratice.util.DisplayConfig;
import pr.jason.myuipratice.util.KeyPadText;
import pr.jason.myuipratice.util.PreferenceManager;
import pr.jason.myuipratice.util.Utils;
import pr.jason.myuipratice.widget.TouchableLinearLayout;
import pr.jason.myuipratice.widget.TouchableListView;

/**
 * Created by Jaesin on 2015-02-05.
 */
public class DialFragment extends Fragment{

    private String title;
    private int page;
    private float content_height;
    private float bottom_height;
    private LinearLayout content_layout;
    private RelativeLayout bottom_layout;
    private TouchableListView result_list;
    private TouchableLinearLayout dial_layout;
    private float listview_width;
    private float dial_width;
    private static int RIGHT = 1;
    private static int LEFT = 0;
    private int initDialPosition;
    private int dialPosition = RIGHT;
    float downX = 0;
    float downY = 0;
    int upX =0;
    int upY = 0;
    private static float TOLERANCE;
    private String inputNum = "";
    PreferenceManager preferenceManager;
    private ArrayList<ContactsClass> resultArray;
    private ArrayList<ContactsClass> contactsArray=null;
    private EditText contact_search_et;
    private LinearLayout mBtn1,mBtn2,mBtn3,mBtn4,mBtn5,mBtn6,mBtn7,mBtn8,mBtn9,mBtn10,mBtn11,mBtn12,mBtn13,mBtn14,mBtn15;
    private String mCurKeyPad[][];
    DialAdapter dialAdapter;
    private Context mContext;
    public static boolean isOnPositionChanged = false;
    public static float listRowHeight;
    public static DialFragment newInstance(String title){
        DialFragment dialFragment = new DialFragment();
        Bundle args = new Bundle();
        args.putString("someTitle", title);
        dialFragment.setArguments(args);
        return dialFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();

        initUiResource();
        getContactsInfo();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(contactsArray == null){
            getContactsInfo();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dial_fragment,container,false);
        content_layout = (LinearLayout)view.findViewById(R.id.content_layout);
        bottom_layout = (RelativeLayout)view.findViewById(R.id.bottom_layout);
        dial_layout = (TouchableLinearLayout)view.findViewById(R.id.dial_layout);
        result_list =(TouchableListView) view.findViewById(R.id.result_list);
        contact_search_et = (EditText)view.findViewById(R.id.contact_search_et);
        contact_search_et.setInputType(0);
        contact_search_et.addTextChangedListener(mTextWatcher);

        RelativeLayout.LayoutParams content_layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int)content_height);
        content_layout.setLayoutParams(content_layoutParams);
        RelativeLayout.LayoutParams bottom_layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        bottom_layout.setLayoutParams(bottom_layoutParams);

        RelativeLayout.LayoutParams listview_layoutParams = new RelativeLayout.LayoutParams((int)listview_width,(int)bottom_height+(int)(bottom_height/5));
        listview_layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,bottom_layout.getId());
        RelativeLayout.LayoutParams dial_layoutParams = new RelativeLayout.LayoutParams((int)dial_width,(int)bottom_height);
        dial_layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,bottom_layout.getId());
        if(dialPosition==RIGHT){
            dial_layoutParams.addRule(RelativeLayout.RIGHT_OF,result_list.getId());
            dial_layout.setInit(mContext,RIGHT,result_list,preferenceManager);
            result_list.setInit(mContext,LEFT,dial_layout,preferenceManager);
        }else{
            listview_layoutParams.addRule(RelativeLayout.RIGHT_OF,dial_layout.getId());
            dial_layout.setInit(mContext,LEFT,result_list,preferenceManager);
            result_list.setInit(mContext,RIGHT,dial_layout,preferenceManager);

        }
        result_list.setLayoutParams(listview_layoutParams);

        dial_layout.setLayoutParams(dial_layoutParams);
        setDialBtns(view);

        bottom_layout.setFocusableInTouchMode(true);
        bottom_layout.setFocusable(true);

        resultArray = new ArrayList<ContactsClass>();
        result_list.setOnScrollListener(mScrollListener);
        ViewTreeObserver vto = result_list.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if(result_list.getChildAt(0)!=null){
                    listRowHeight = result_list.getChildAt(0).getHeight()-10; //listview alpha value adjust point
                }else{

                }
            }



        });
        return view;
    }



    public void getFadeOutView(ListView v, float rowHeight,int firstVisibleItem, int visibleItemCount){
        int firstWantedPosition = firstVisibleItem;
        int wantedPosition = firstVisibleItem+1; // Whatever position you're looking for
        int firstPosition = v.getFirstVisiblePosition() - v.getHeaderViewsCount(); // This is the same as child #0
        int wantedChild = wantedPosition - firstPosition;
        int firstWantedChild = firstWantedPosition - firstPosition;

        if (wantedChild < 0 || wantedChild >= v.getChildCount()) {
            ViewHelper.setAlpha(v.getChildAt(firstWantedChild),0f);
            return;
        }
        if (firstWantedChild < 0 || firstWantedChild >= v.getChildCount()) {
            return;
        }

        View wantedView = v.getChildAt(wantedChild);
        View firstWantedView = v.getChildAt(firstWantedChild);

        int visibleCount =v.getLastVisiblePosition() - v.getFirstVisiblePosition();


        for(int i = 0; i < visibleCount+1; i++){
            if(i==wantedChild){
                if(wantedView.getY() < listRowHeight){
                    float alphaRate = 0f;
                    float forHalfTransparentValue = (listRowHeight - (2*(listRowHeight-wantedView.getY())))/listRowHeight;
                    alphaRate = ((wantedView.getY())/listRowHeight)*forHalfTransparentValue;
                    ViewHelper.setAlpha(wantedView,alphaRate);

                }else{
                    ViewHelper.setAlpha(wantedView,1.0f);
                }
            }else if(firstWantedChild==i){
                ViewHelper.setAlpha(v.getChildAt(i),0f);
            }
            else{
                ViewHelper.setAlpha(v.getChildAt(i),1.0f);
            }
        }
    }

    private AbsListView.OnScrollListener mScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if(visibleItemCount!=0) {
                getFadeOutView((ListView)view, listRowHeight,firstVisibleItem,visibleItemCount);
            }
        }
    };

    private void initUiResource(){
        content_height = MainActivity.mDisHeight/2;
        bottom_height = MainActivity.mDisHeight-content_height;
        dial_width = MainActivity.mDisWidth/2;
        listview_width = MainActivity.mDisWidth - dial_width;
        TOLERANCE = DisplayConfig.convertDpToPixel(30,getActivity().getApplicationContext());
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        dialPosition = preferenceManager.getValue("DIAL_POSITION",RIGHT);
        initDialPosition = dialPosition;
        mCurKeyPad = KeyPadText.KoreaKeyPad;
    }

    private void getContactsInfo(){
        contactsArray = new ArrayList<ContactsClass>();
        contactsArray = MainActivity.getPhoneBooKList(false, getActivity().getApplicationContext());
    }

    private void setDialBtns(View v){
        mBtn1 = (LinearLayout)v.findViewById(R.id.dial_btn_1);
        mBtn2 = (LinearLayout)v.findViewById(R.id.dial_btn_2);
        mBtn3 = (LinearLayout)v.findViewById(R.id.dial_btn_3);
        mBtn4 = (LinearLayout)v.findViewById(R.id.dial_btn_4);
        mBtn5 = (LinearLayout)v.findViewById(R.id.dial_btn_5);
        mBtn6 = (LinearLayout)v.findViewById(R.id.dial_btn_6);
        mBtn7 = (LinearLayout)v.findViewById(R.id.dial_btn_7);
        mBtn8 = (LinearLayout)v.findViewById(R.id.dial_btn_8);
        mBtn9 = (LinearLayout)v.findViewById(R.id.dial_btn_9);
        mBtn10 = (LinearLayout)v.findViewById(R.id.dial_btn_asterisk);
        mBtn11 = (LinearLayout)v.findViewById(R.id.dial_btn_0);
        mBtn12 = (LinearLayout)v.findViewById(R.id.dial_btn_number);
        mBtn13 = (LinearLayout)v.findViewById(R.id.dial_btn_video_call);
        mBtn14 = (LinearLayout)v.findViewById(R.id.dial_btn_call);
        mBtn15 = (LinearLayout)v.findViewById(R.id.dial_btn_chat);

        mBtn1.setTag(1);
        mBtn2.setTag(2);
        mBtn3.setTag(3);
        mBtn4.setTag(4);
        mBtn5.setTag(5);
        mBtn6.setTag(6);
        mBtn7.setTag(7);
        mBtn8.setTag(8);
        mBtn9.setTag(9);
        mBtn11.setTag(0);

        mBtn1.setOnClickListener(mDialBtnClickListener);
        mBtn2.setOnClickListener(mDialBtnClickListener);
        mBtn3.setOnClickListener(mDialBtnClickListener);
        mBtn4.setOnClickListener(mDialBtnClickListener);
        mBtn5.setOnClickListener(mDialBtnClickListener);
        mBtn6.setOnClickListener(mDialBtnClickListener);
        mBtn7.setOnClickListener(mDialBtnClickListener);
        mBtn8.setOnClickListener(mDialBtnClickListener);
        mBtn9.setOnClickListener(mDialBtnClickListener);
        mBtn11.setOnClickListener(mDialBtnClickListener);
        mBtn12.setOnClickListener(mBackSpace);

    }

    private PhoneNumberFormattingTextWatcher mTextWatcher = new PhoneNumberFormattingTextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            dialAdapter = null;
            dialAdapter = new DialAdapter(getActivity().getApplicationContext(),resultArray,MainActivity.options);
            result_list.setAdapter(dialAdapter);
            if(s.length()==0){
                resultArray.clear();
            }else{
                setSearchResultList(contact_search_et.getText().toString());

            }
            dialAdapter.notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void setSearchResultList(String str){
        resultArray.clear();
        String inputStr = str.replace("-","");
        inputStr = inputStr.trim();
        for(int i=0; i < contactsArray.size(); i++){
            if(contactsArray.get(i).friendNum.contains(inputStr)){
                resultArray.add(contactsArray.get(i));
            }
        }
        ContactsClass dummyClass = new ContactsClass();
        resultArray.add(0,dummyClass);
    }

    private View.OnClickListener mDialBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int)v.getTag();
            String id = ""+tag;
            inputNum += id;
            inputNum = PhoneNumberUtils.formatNumber(inputNum);
            contact_search_et.setText(inputNum);
        }
    };

    private View.OnClickListener mBackSpace = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String inputStr = inputNum.replace("-","");
            inputStr = inputStr.trim();
            inputNum =  Utils.deleteLastCharacter(inputStr);
            inputNum = PhoneNumberUtils.formatNumber(inputNum);
            contact_search_et.setText(inputNum);
        }
    };
}
