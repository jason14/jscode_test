package pr.jason.myuipratice.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Jaesin on 2015-02-10.
 */
public class IndexableListView extends ListView {

    private boolean mIsFastScrollEnabled = false;
    private IndexScroller mScroller = null;
    private GestureDetector mGestureDetector = null;
    public int customTopMargin;
    public IndexableListView(Context context) {
        super(context);
    }

    public IndexableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFastScrollEnabled() {
        return mIsFastScrollEnabled;
    }

    @Override
    public void setFastScrollEnabled(boolean enabled) {
        mIsFastScrollEnabled = enabled;
        if (mIsFastScrollEnabled) {
            if (mScroller == null)
                mScroller = new IndexScroller(getContext(), this);
        } else {
            if (mScroller != null) {
                mScroller.hide();
                Log.e("Draw","setFastScrollEnabled mScroller.hide()" );

                mScroller = null;
            }
        }
    }
    private Canvas mCanvas;
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Overlay index bar
        if (mScroller != null) {
            mScroller.draw(canvas);
            mCanvas = canvas;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Intercept ListView's touch event
        if (mScroller != null && mScroller.onTouchEvent(ev))
            return true;

        if (mGestureDetector == null) {
            mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2,
                                       float velocityX, float velocityY) {
                    // If fling happens, index bar shows
                    if (mScroller != null)
                        mScroller.show();
                    Log.e("Draw","mGestureDetector mScroller.show()" );

                    return super.onFling(e1, e2, velocityX, velocityY);
                }

            });
        }
        mGestureDetector.onTouchEvent(ev);

        return super.onTouchEvent(ev);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        if (mScroller != null)
            mScroller.setAdapter(adapter);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mScroller != null)
            mScroller.onSizeChanged(w, h, customTopMargin, oldh);
        tmp_w =w;
        tmp_h= h;
        tmp_oldh = oldh;

    }
    int tmp_w;
    int tmp_h;
    int tmp_oldh;
    public void setCustomTopMargin(int topMargin){
        customTopMargin = topMargin;
        if (mScroller != null) {
            mScroller.hide();
            mScroller.onSizeChanged(tmp_w, tmp_h, customTopMargin, tmp_oldh);
            mScroller.draw(mCanvas);
            mScroller.show();

            Log.e("Draw","onSizeChanged: " + customTopMargin);
        }
    }

    public void setIsVisibleSearchEt(boolean value){
        if(mScroller != null){
            mScroller.setIsVisibleSearchEt(value);
        }
    }

    public IndexScroller getmScroller(){
        IndexScroller scroller = null;
        if(mScroller != null) {
            scroller = mScroller;
        }
        return scroller;
    }

}
