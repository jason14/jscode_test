package pr.jason.myuipratice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.AnimatorSet;

import java.util.ArrayList;

import pr.jason.myuipratice.util.DisplayConfig;
import pr.jason.myuipratice.util.KeyPadText;
import pr.jason.myuipratice.util.MyLog;
import pr.jason.myuipratice.util.PreferenceManager;
import pr.jason.myuipratice.util.Utils;

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
    private ListView result_list;
    private LinearLayout dial_layout;
    private float listview_width;
    private float dial_width;
    private static int RIGHT = 0;
    private static int LEFT = 1;
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

    public static DialFragment newInstance(String title){
        DialFragment dialFragment = new DialFragment();
        Bundle args = new Bundle();
        args.putString("someTitle",title);
        dialFragment.setArguments(args);
        return dialFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        dial_layout = (LinearLayout)view.findViewById(R.id.dial_layout);
        result_list =(ListView) view.findViewById(R.id.result_list);
        contact_search_et = (EditText)view.findViewById(R.id.contact_search_et);
        contact_search_et.setInputType(0);
        contact_search_et.addTextChangedListener(mTextWatcher);

        LinearLayout.LayoutParams content_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)content_height);
        content_layout.setLayoutParams(content_layoutParams);
        LinearLayout.LayoutParams bottom_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        bottom_layout.setLayoutParams(bottom_layoutParams);

        RelativeLayout.LayoutParams listview_layoutParams = new RelativeLayout.LayoutParams((int)listview_width,RelativeLayout.LayoutParams.MATCH_PARENT);

        RelativeLayout.LayoutParams dial_layoutParams = new RelativeLayout.LayoutParams((int)dial_width,RelativeLayout.LayoutParams.MATCH_PARENT);
        if(dialPosition==RIGHT){
            dial_layoutParams.addRule(RelativeLayout.RIGHT_OF,result_list.getId());
        }else{
            listview_layoutParams.addRule(RelativeLayout.RIGHT_OF,dial_layout.getId());
        }
        result_list.setLayoutParams(listview_layoutParams);
        dial_layout.setLayoutParams(dial_layoutParams);
        setDialBtns(view);

        bottom_layout.setFocusableInTouchMode(true);
        bottom_layout.setFocusable(true);
        bottom_layout.setOnTouchListener(dialPositionChangeListener);

        resultArray = new ArrayList<ContactsClass>();

        return view;
    }



    private View.OnTouchListener dialPositionChangeListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean returnValue = false;
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        Log.e("터치 시작점","x: "+downX + ", y: " + downY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x = (int)(event.getX()-downX);
                        int y = (int)(event.getY()-downY);
                        Log.e("터치 무브","x: "+x + ", y: " + y);

                        if(Math.abs(x) > TOLERANCE && Math.abs(y) < TOLERANCE){

                            AnimatorSet set = new AnimatorSet();

                            if(initDialPosition == RIGHT) {
                                if (x > 0 && dialPosition == LEFT) {
                                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(dial_layout, "translationX", -listview_width, 0),
                                            com.nineoldandroids.animation.ObjectAnimator.ofFloat(result_list, "translationX", 0, dial_width));
                                    dialPosition = RIGHT;
                                    preferenceManager.put("DIAL_POSITION", dialPosition);
                                    returnValue = true;
                                } else if (x < 0 && dialPosition == RIGHT) {
                                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(dial_layout, "translationX", 0, -listview_width),
                                            com.nineoldandroids.animation.ObjectAnimator.ofFloat(result_list, "translationX", dial_width, 0));
                                    dialPosition = LEFT;
                                    preferenceManager.put("DIAL_POSITION", dialPosition);
                                    returnValue = true;
                                } else {
                                    returnValue =  false;
                                }
                            }else{
                                if (x > 0 && dialPosition == LEFT) {
                                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(dial_layout, "translationX", 0, listview_width),
                                            com.nineoldandroids.animation.ObjectAnimator.ofFloat(result_list, "translationX", 0, 0));
                                    dialPosition = RIGHT;
                                    preferenceManager.put("DIAL_POSITION", dialPosition);
                                    returnValue = true;
                                } else if (x < 0 && dialPosition == RIGHT) {
                                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(dial_layout, "translationX", listview_width, 0),
                                            com.nineoldandroids.animation.ObjectAnimator.ofFloat(result_list, "translationX", 0, dial_width));
                                    dialPosition = LEFT;
                                    preferenceManager.put("DIAL_POSITION", dialPosition);
                                    returnValue = true;
                                } else {
                                    returnValue =  false;
                                }
                            }

                            set.setDuration(300).start();

                        }else{
                            returnValue =  false;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("터치 옵","x: "+event.getX() + ", y: " + event.getY());
                        break;
                }
            MyLog.LogMessage("리턴 값", "returnValue: "+returnValue);
            return returnValue;
        }
    };

    private void initUiResource(){
        content_height = MainActivity.mDisHeight/2;
        bottom_height = MainActivity.mDisHeight/2;
        dial_width = MainActivity.mDisWidth/2;
        listview_width = MainActivity.mDisWidth - dial_width;
        TOLERANCE = DisplayConfig.convertDpToPixel(100,getActivity().getApplicationContext());
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        dialPosition = preferenceManager.getValue("DIAL_POSITION",RIGHT);
        initDialPosition = dialPosition;
        mCurKeyPad = KeyPadText.KoreaKeyPad;
    }

    private void getContactsInfo(){
        contactsArray = new ArrayList<ContactsClass>();
        contactsArray = ((MainActivity)MainActivity.mContext).getPhoneBooKList(false);
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

        mBtn1.setFocusable(false);
        mBtn2.setFocusable(false);
        mBtn3.setFocusable(false);
        mBtn4.setFocusable(false);
        mBtn5.setFocusable(false);
        mBtn6.setFocusable(false);
        mBtn7.setFocusable(false);
        mBtn8.setFocusable(false);
        mBtn9.setFocusable(false);
        mBtn11.setFocusable(false);
        mBtn1.setFocusableInTouchMode(false);
        mBtn2.setFocusableInTouchMode(false);
        mBtn3.setFocusableInTouchMode(false);
        mBtn4.setFocusableInTouchMode(false);
        mBtn5.setFocusableInTouchMode(false);
        mBtn6.setFocusableInTouchMode(false);
        mBtn7.setFocusableInTouchMode(false);
        mBtn8.setFocusableInTouchMode(false);
        mBtn9.setFocusableInTouchMode(false);
        mBtn11.setFocusableInTouchMode(false);

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

        mBtn1.setOnTouchListener(dialPositionChangeListener);
        mBtn2.setOnTouchListener(dialPositionChangeListener);
        mBtn3.setOnTouchListener(dialPositionChangeListener);
        mBtn4.setOnTouchListener(dialPositionChangeListener);
        mBtn5.setOnTouchListener(dialPositionChangeListener);
        mBtn6.setOnTouchListener(dialPositionChangeListener);
        mBtn7.setOnTouchListener(dialPositionChangeListener);
        mBtn8.setOnTouchListener(dialPositionChangeListener);
        mBtn9.setOnTouchListener(dialPositionChangeListener);
        mBtn11.setOnTouchListener(dialPositionChangeListener);
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

    }

    private View.OnClickListener mDialBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int)v.getTag();
            String id = ""+tag;
            inputNum += id;
            contact_search_et.setText(inputNum);
            MyLog.LogMessage("클릭 버튼",""+inputNum);
        }
    };

    private View.OnClickListener mBackSpace = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            inputNum =  Utils.deleteLastCharacter(inputNum);
            contact_search_et.setText(inputNum);
        }
    };



}
