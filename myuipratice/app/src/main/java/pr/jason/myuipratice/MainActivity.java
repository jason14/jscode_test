package pr.jason.myuipratice;

import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.melnykov.fab.FloatingActionButton;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pr.jason.myuipratice.util.DisplayConfig;
import pr.jason.myuipratice.util.MyLog;


public class MainActivity extends ActionBarActivity {
    private PagerSlidingTabStrip mSlidingTabLayout;
    private ViewPager mViewPager;
    private View mToolbar;
    private FragmentManager fm;
    public static Context mContext;
    public static DisplayImageOptions options;
    public static float fabTransWidth;
    FloatingActionButton fab;
    Animation ani;
    public static int prePage = 0;
    public static boolean onDial = false;
    android.support.v4.app.FragmentTransaction transaction;
    public static float mDisWidth;
    public static float mDisHeight;
    public static boolean isOnfocusScrollView = false;
    public static LinearLayout main_layout;
    private float  maxScrollY = 0;
    private float minScrollY = 20f;
    private float minSlideDownStartY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(1000))
                .build();

       if(savedInstanceState == null){
           FragmentTransaction transaction = getFragmentManager().beginTransaction();
       }
        initImageLoader(this);
        //Display dis = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        DisplayConfig displayConfig = new DisplayConfig(mContext,MainActivity.this);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        mDisWidth = displayConfig.getDisplayWidth();
        mDisHeight = displayConfig.getDisplayHeight();
        float fabMarginRight = getResources().getDimension(R.dimen.item_margin);
        //float fabHalfWidth = fab.getWidth()/2;
        fabTransWidth = mDisWidth/2 - (fabMarginRight + displayConfig.convertDpToPixel(24,mContext));
        maxScrollY = mDisHeight/2;
        minSlideDownStartY = mDisHeight/8;

        fm = this.getSupportFragmentManager();
        main_layout = (LinearLayout)findViewById(R.id.main_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MainViewPagerAdpater(fm,3));
        mSlidingTabLayout = (PagerSlidingTabStrip) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDividerColor(getResources().getColor(R.color.colorTransparent));
        mSlidingTabLayout.setIndicatorColor(getResources().getColor(R.color.colorControlHighlight));
        mSlidingTabLayout.setTextColorResource(R.color.colorLightText);
        mSlidingTabLayout.setUnderlineHeight(0);
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(prePage==0&&position!=0) {

                    AnimatorSet set = new AnimatorSet();
                    set.playTogether( com.nineoldandroids.animation.ObjectAnimator.ofFloat(fab, "translationX", 0, fabTransWidth));
                    set.setDuration(300).start();
                    // Log.e("Trans Width value", "최근 또는 연락처 trans value: " + fabTransWidth);
                }else if(prePage!=0&&position==0){

                    AnimatorSet set = new AnimatorSet();
                    set.playTogether( com.nineoldandroids.animation.ObjectAnimator.ofFloat(fab, "translationX", fabTransWidth, 0));
                    set.setDuration(300).start();
                }
                prePage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Dial Fragment","Dial 프래그먼트 시작");
                DialFragment dialFragment = DialFragment.newInstance("Dial");
                transaction = getSupportFragmentManager().beginTransaction();

                transaction.setCustomAnimations(R.anim.slide_in_top,R.anim.slide_out_bottom);
                transaction.replace(android.R.id.content,dialFragment,"dialFragment");
                transaction.addToBackStack(null).commit();
                onDial = true;
            }
        });

        //main_layout.setOnTouchListener(mainLayoutTouchListener);


    }
    private float startY;
    private float downY;
    private float onMoveY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:

                downY = event.getY();
                startY = main_layout.getY();
                Log.e("dispatchTouchEvent 터치 시작점", ", y: " + downY);
                MyLog.LogMessage("dispatchTouchEvent ACTION_DOWN ", " Top: " + main_layout.getY());

                break;
            case MotionEvent.ACTION_MOVE:
                onMoveY = event.getY();
                int deltaY = (int)(onMoveY-downY);
                int newY = (int)(deltaY+startY);
                Log.e("dispatchTouchEvent 터치 무브", ", y: " + newY);
                Log.e("dispatchTouchEvent","isOnfocusScrollView: " + isOnfocusScrollView);

                    if (Math.abs(newY) < maxScrollY) {
                        MyLog.LogMessage("dispatchTouchEvent main_layout.getTop()", " Top: " + main_layout.getY());

                        if(main_layout.getY() >= 0) {
                            if (isOnfocusScrollView == true) {

                                if((startY<=0)&&(deltaY<minScrollY)){
                                    ViewHelper.setTranslationY(main_layout, 0);
                                }else {
                                    ViewHelper.setTranslationY(main_layout, newY);
                                }


                                MyLog.LogMessage("dispatchTouchEvent 터치이벤트", "" + newY);
                            }
                        }else{

                        }

                    }
                break;
            case MotionEvent.ACTION_UP:

                    float endY = event.getY();
                        MyLog.LogMessage("UP"," startY: " + startY +" ,endY: "+endY );
                    if ((downY > endY) && ((downY - endY) > minScrollY)) {
                        MyLog.LogMessage("UP","isOnfocusScrollView: " +isOnfocusScrollView);

                        if(isOnfocusScrollView) {
                            slideUpMainLayout(main_layout.getY());
                            //ViewHelper.setTranslationY(main_layout, 0);
                            isOnfocusScrollView = false;
                        }
                    }else if((downY < endY) && ((endY-downY) > minSlideDownStartY)){
                        if(isOnfocusScrollView) {
                            slideDownMainLayout(main_layout.getY());
                        }
                    }else if((downY > endY) && ((downY - endY) < minScrollY)){
                        if(isOnfocusScrollView) {
                            slideDownMainLayout(main_layout.getY());
                        }
                    }else if((downY < endY) && ((endY-downY) < minSlideDownStartY)){
                        if(isOnfocusScrollView) {
                            slideUpMainLayout(main_layout.getY());
                            //ViewHelper.setTranslationY(main_layout, 0);
                            isOnfocusScrollView = false;
                        }
                    }

                    if (main_layout.getY() < 0) {
                        ViewHelper.setTranslationY(main_layout, 0);
                        isOnfocusScrollView = false;
                    }

                Log.e("터치 옵", ", y: " + event.getY());
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private void slideDownMainLayout(float curY){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(main_layout, "translationY", curY, maxScrollY));
        set.setDuration(300).start();
    }

    private void slideUpMainLayout(float curY){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(main_layout, "translationY", curY, 0));
        set.setDuration(300).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }



    public ArrayList<ContactsClass> getPhoneBooKList(boolean isStarredList){

        ArrayList<ContactsClass> contactsArray = new ArrayList<ContactsClass>();
        ContactsClass contactsClass = new ContactsClass();
        Uri contactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String disId = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String disName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String number = ContactsContract.CommonDataKinds.Phone.NUMBER;
        String disStarred = ContactsContract.CommonDataKinds.Phone.STARRED;
        Cursor cursor = getContentResolver().query(contactsUri, new String[]{disId,disName,number,disStarred},null,null,null);

        int i = 0;
        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                long id = Long.parseLong(cursor.getString(0));

                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                int starred = Integer.parseInt(cursor.getString(3));

                contactsClass = new ContactsClass();

                contactsClass.friendId = id;
                contactsClass.friendName = name;
                contactsClass.friendNum = phone;
                contactsClass.friendStarred = starred;


                ContentResolver contentResolver = getContentResolver();
                Uri imageUrl = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);

                contactsClass.friendPictureUrl = imageUrl;
                if(isStarredList) {
                    if (contactsClass.friendStarred == 1) {
                        contactsArray.add(contactsClass);
                    }
                }else{
                    contactsArray.add(contactsClass);
                }
                i++;
                cursor.moveToNext();
            }
            cursor.close();
        }
        return contactsArray;
    }



    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

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
