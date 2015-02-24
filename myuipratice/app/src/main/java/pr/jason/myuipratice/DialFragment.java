package pr.jason.myuipratice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.AnimatorSet;

import pr.jason.myuipratice.util.DisplayConfig;
import pr.jason.myuipratice.util.PreferenceManager;

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
    PreferenceManager preferenceManager;
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
        page= getArguments().getInt("somePage",0);
        title = getArguments().getString("someTitle");
        content_height = MainActivity.mDisHeight/2;
        bottom_height = MainActivity.mDisHeight/2;
        dial_width = MainActivity.mDisWidth/2;
        listview_width = MainActivity.mDisWidth - dial_width;
        TOLERANCE = DisplayConfig.convertDpToPixel(100,getActivity().getApplicationContext());
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        dialPosition = preferenceManager.getValue("DIAL_POSITION",RIGHT);
        initDialPosition = dialPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dial_fragment,container,false);
        content_layout = (LinearLayout)view.findViewById(R.id.content_layout);
        bottom_layout = (RelativeLayout)view.findViewById(R.id.bottom_layout);
        dial_layout = (LinearLayout)view.findViewById(R.id.dial_layout);
        result_list =(ListView) view.findViewById(R.id.result_list);

        LinearLayout.LayoutParams content_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)content_height);
        content_layout.setLayoutParams(content_layoutParams);
        LinearLayout.LayoutParams bottom_layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)bottom_height);
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

        bottom_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

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
                            // Log.e("터치 무브","x: "+downX + ", y: " + downY);

                                AnimatorSet set = new AnimatorSet();

                            if(initDialPosition == RIGHT) {
                                if (x > 0 && dialPosition == LEFT) {
                                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(dial_layout, "translationX", -listview_width, 0),
                                            com.nineoldandroids.animation.ObjectAnimator.ofFloat(result_list, "translationX", 0, -dial_width));
                                    dialPosition = RIGHT;
                                    preferenceManager.put("DIAL_POSITION", dialPosition);
                                } else if (x < 0 && dialPosition == RIGHT) {
                                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(dial_layout, "translationX", 0, -listview_width),
                                            com.nineoldandroids.animation.ObjectAnimator.ofFloat(result_list, "translationX", -dial_width, 0));
                                    dialPosition = LEFT;
                                    preferenceManager.put("DIAL_POSITION", dialPosition);
                                } else {

                                }
                            }else{
                                if (x > 0 && dialPosition == LEFT) {
                                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(dial_layout, "translationX", 0, listview_width),
                                            com.nineoldandroids.animation.ObjectAnimator.ofFloat(result_list, "translationX", dial_width, 0));
                                    dialPosition = RIGHT;
                                    preferenceManager.put("DIAL_POSITION", dialPosition);
                                } else if (x < 0 && dialPosition == RIGHT) {
                                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(dial_layout, "translationX", listview_width, 0),
                                            com.nineoldandroids.animation.ObjectAnimator.ofFloat(result_list, "translationX", 0, dial_width));
                                    dialPosition = LEFT;
                                    preferenceManager.put("DIAL_POSITION", dialPosition);
                                } else {

                                }
                            }

                            set.setDuration(300).start();

                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("터치 옵","x: "+event.getX() + ", y: " + event.getY());
                        break;
                }

                return true;
            }
        });
        return view;
    }

}
