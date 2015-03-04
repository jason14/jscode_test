package pr.jason.myuipratice.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import pr.jason.myuipratice.DialFragment;
import pr.jason.myuipratice.util.DisplayConfig;
import pr.jason.myuipratice.util.PreferenceManager;

/**
 * Created by Jaesin on 2015-03-02.
 */
public class TouchableListView extends ListView {

    private float mStartX;
    private float mDownX;
    private float mDownY;
    private float mWidth;
    private int mDX;
    private int mDY;
    private static float TOLERANCE;
    private Context mContext;
    private int mStartXposition;
    private View mSideView;
    private static float MAX_VIEW_SCALE = 1.1f;
    private static float MIN_VIEW_SCALE = 1.0f/1.1f;
    private static float ORI_VIEW_SCALE = 1.0f;
    private static int DURATION = 200;
    private static int curPosition = 0;
    private static int RIGHT = 1;
    private static int LEFT = 0;
    public TouchableListView(Context context) {
        super(context);
    }
    public TouchableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public TouchableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            if(DialFragment.isOnPositionChanged){
                event.setAction(MotionEvent.ACTION_CANCEL);
                break;
            }

                setCurPosition(this.getX());
                mDownX = event.getX();
                mDownY = event.getY();

                mStartX = this.getX();
                mWidth = this.getWidth();

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                if(DialFragment.isOnPositionChanged==false) {
                    mDX = (int) (event.getX() - mDownX);
                    mDY = (int) (event.getY() - mDownY);


                    if (Math.abs(mDX)>Math.abs(mDY) && Math.abs(mDX) > TOLERANCE && curPosition == LEFT) {
                        DialFragment.isOnPositionChanged = true;
                        if (mDX > 0) {
                            slideRightForward();
                            preferenceManager.put("DIAL_POSITION", 0);

                        } else {
                            slideRightBackward();
                            preferenceManager.put("DIAL_POSITION", 0);

                        }

                    } else if (Math.abs(mDX)>Math.abs(mDY) && Math.abs(mDX) > TOLERANCE && curPosition != LEFT) {
                        DialFragment.isOnPositionChanged = true;
                        if (mDX > 0) {
                            slideLeftBackward();
                            preferenceManager.put("DIAL_POSITION", 1);

                        } else {
                            slideLeftForward();
                            preferenceManager.put("DIAL_POSITION", 1);

                        }

                    }
                    break;
                }
        }
        return super.dispatchTouchEvent(event);
    }

    public void setCurPosition(float curPosition){
        if(curPosition < mSideView.getWidth()){
           this.curPosition = LEFT;
        }else{
            this.curPosition = RIGHT;
        }
    }


    public AnimatorSet transScaleXStart(View v ,float startPosition,float endPostion, float startScale, float endScale){
        AnimatorSet transScaleX = new AnimatorSet();
        ObjectAnimator transAni = ObjectAnimator.ofFloat(v, "translationX", startPosition, endPostion);
        transAni.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator scaleXAni =  ObjectAnimator.ofFloat(v, "scaleX", startScale, endScale);
        scaleXAni.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator scaleYAni = ObjectAnimator.ofFloat(v, "scaleY", startScale, endScale);
        scaleYAni.setInterpolator(new DecelerateInterpolator());
        transScaleX.playTogether(transAni,scaleXAni,scaleYAni);

        return transScaleX;
    }

    public AnimatorSet transScaleXEnd(View v ,float startPosition,float endPostion, float startScale, float endScale){
        AnimatorSet transScaleX = new AnimatorSet();
        ObjectAnimator transAni = ObjectAnimator.ofFloat(v, "translationX", startPosition, endPostion);
        transAni.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator scaleXAni =  ObjectAnimator.ofFloat(v, "scaleX", startScale, endScale);
        scaleXAni.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator scaleYAni = ObjectAnimator.ofFloat(v, "scaleY", startScale, endScale);
        scaleYAni.setInterpolator(new AccelerateInterpolator());
        transScaleX.playTogether(transAni,scaleXAni,scaleYAni);

        return transScaleX;
    }

    public int getStartXposition(boolean isTouchedView){
        int touchedXposition = 0;
        if(isTouchedView){
            touchedXposition = mStartXposition;
        }else{
            if(mStartXposition==RIGHT){
                touchedXposition = LEFT;
            }else{
                touchedXposition = RIGHT;
            }
        }
        return touchedXposition;
    }

    public AnimatorSet rightForwardStart(View v,View sideView,boolean isTouchedView){
        AnimatorSet set = new AnimatorSet();
        v.bringToFront();
        v.invalidate();
        int touchedXposition = getStartXposition(isTouchedView);

        if(touchedXposition == 0) {

            set = transScaleXStart(v, 0, sideView.getWidth() / 2, ORI_VIEW_SCALE, MAX_VIEW_SCALE);

        }else{
            set = transScaleXStart(v, -sideView.getWidth(), -sideView.getWidth() / 2, ORI_VIEW_SCALE, MAX_VIEW_SCALE);
        }
        return set;
    }

    public AnimatorSet rightForwardEnd(View v,View sideView,boolean isTouchedView){
        AnimatorSet set = new AnimatorSet();
        int touchedXposition = getStartXposition(isTouchedView);

        if(touchedXposition == 0) {
            set = transScaleXEnd(v, sideView.getWidth() / 2, sideView.getWidth(), MAX_VIEW_SCALE, ORI_VIEW_SCALE);
        }else{
            set = transScaleXEnd(v, -sideView.getWidth() / 2, 0, MAX_VIEW_SCALE, ORI_VIEW_SCALE);
        }
        return set;
    }

    public AnimatorSet leftForwardStart(View v,View sideView,boolean isTouchedView){
        AnimatorSet set = new AnimatorSet();
        v.bringToFront();
        v.invalidate();
        int touchedXposition = getStartXposition(isTouchedView);

        if(touchedXposition == 0) {
            set = transScaleXStart(v, sideView.getWidth(), sideView.getWidth() / 2, ORI_VIEW_SCALE, MAX_VIEW_SCALE);
        }else{
            set = transScaleXStart(v, 0, -sideView.getWidth() / 2, ORI_VIEW_SCALE, MAX_VIEW_SCALE);
        }
        return set;
    }

    public AnimatorSet leftForwardEnd(View v,View sideView,boolean isTouchedView){
        AnimatorSet set = new AnimatorSet();
        int touchedXposition = getStartXposition(isTouchedView);

        if(touchedXposition == 0) {
            set = transScaleXEnd(v, sideView.getWidth() / 2, 0, MAX_VIEW_SCALE, ORI_VIEW_SCALE);
        }else{
            set = transScaleXEnd(v, -sideView.getWidth() / 2, -sideView.getWidth(), MAX_VIEW_SCALE, ORI_VIEW_SCALE);
        }
        return set;
    }

    public AnimatorSet rightBackwardStart(View v,View sideView,boolean isTouchedView){
        AnimatorSet set = new AnimatorSet();
        int touchedXposition = getStartXposition(isTouchedView);

        if(touchedXposition == 0) {
            set = transScaleXStart(v, 0, sideView.getWidth() / 2, ORI_VIEW_SCALE, MIN_VIEW_SCALE);
        }else{
            set = transScaleXStart(v, -sideView.getWidth(), -sideView.getWidth() / 2, ORI_VIEW_SCALE, MIN_VIEW_SCALE);
        }
        return set;
    }

    public AnimatorSet rightBackwardEnd(View v,View sideView,boolean isTouchedView){
        AnimatorSet set = new AnimatorSet();
        int touchedXposition = getStartXposition(isTouchedView);

        if(touchedXposition == 0) {
            set = transScaleXEnd(v, sideView.getWidth() / 2, sideView.getWidth(), MIN_VIEW_SCALE, ORI_VIEW_SCALE);
        }else{
            set = transScaleXEnd(v, -sideView.getWidth() / 2, 0, MIN_VIEW_SCALE, ORI_VIEW_SCALE);
        }
        return set;
    }

    public AnimatorSet leftBackwardStart(View v,View sideView,boolean isTouchedView){
        AnimatorSet set = new AnimatorSet();
        int touchedXposition = getStartXposition(isTouchedView);

        if(touchedXposition == 0) {
            set = transScaleXStart(v, sideView.getWidth(), sideView.getWidth() / 2, ORI_VIEW_SCALE, MIN_VIEW_SCALE);
        }else{
            set = transScaleXStart(v, 0, -sideView.getWidth() / 2, ORI_VIEW_SCALE, MIN_VIEW_SCALE);
        }
        return set;
    }

    public AnimatorSet leftBackwardEnd(View v,View sideView,boolean isTouchedView){
        AnimatorSet set = new AnimatorSet();
        int touchedXposition = getStartXposition(isTouchedView);

        if(touchedXposition == 0) {
            set = transScaleXEnd(v, sideView.getWidth() / 2, 0, MIN_VIEW_SCALE, ORI_VIEW_SCALE);
        }else{
            set = transScaleXEnd(v, -sideView.getWidth() / 2, -sideView.getWidth(), MIN_VIEW_SCALE, ORI_VIEW_SCALE);
        }
        return set;
    }

    public void slideAniStart(AnimatorSet thisViewStartSet,AnimatorSet thisViewEndSet, AnimatorSet sideViewStartSet, AnimatorSet sideViewEndSet){

        AnimatorSet start = new AnimatorSet();
        AnimatorSet end = new AnimatorSet();
        start.playTogether(thisViewStartSet,sideViewStartSet);
        end.playTogether(thisViewEndSet,sideViewEndSet);
        start.setDuration(DURATION);
        end.setDuration(DURATION);

        AnimatorSet slideActionSet = new AnimatorSet();
        slideActionSet.playSequentially(start,end);


        slideActionSet.start();
        slideActionSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                DialFragment.isOnPositionChanged = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void slideRightForward() {
        slideAniStart(rightForwardStart(this,mSideView,true),rightForwardEnd(this,mSideView,true),
                leftBackwardStart(mSideView, this,false),leftBackwardEnd(mSideView, this,false));
    }



    public void slideRightBackward(){
        slideAniStart(rightBackwardStart(this, mSideView,true),rightBackwardEnd(this, mSideView,true),
                leftForwardStart(mSideView, this,false),leftForwardEnd(mSideView, this,false));
    }

    public void slideLeftForward(){
        slideAniStart(leftForwardStart(this, mSideView,true),leftForwardEnd(this, mSideView,true),
                rightBackwardStart(mSideView, this,false),rightBackwardEnd(mSideView, this,false));

    }

    public void slideLeftBackward(){
        slideAniStart(leftBackwardStart(this,mSideView,true),leftBackwardEnd(this,mSideView,true),
                rightForwardStart(mSideView, this,false),rightForwardEnd(mSideView, this,false));

    }

    private PreferenceManager preferenceManager;

    public void setInit(Context context,int startXposition,View sideView,PreferenceManager preferenceManager){
        mContext = context;
        TOLERANCE = DisplayConfig.convertDpToPixel(80, mContext);
        mStartXposition = startXposition;
        mSideView = sideView;
        this.preferenceManager = preferenceManager;
        if(mStartXposition!=0){
            curPosition = 1;
        }else{
            curPosition = 0;
        }

    }
}
