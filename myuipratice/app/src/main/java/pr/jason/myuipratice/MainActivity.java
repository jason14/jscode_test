package pr.jason.myuipratice;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.melnykov.fab.FloatingActionButton;
import com.nineoldandroids.animation.Animator;
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
import pr.jason.myuipratice.util.PreferenceManager;
import pr.jason.myuipratice.util.ResoursesManager;
import pr.jason.myuipratice.util.SettingInfo;
import pr.jason.myuipratice.widget.CustomViewPager;


public class MainActivity extends ActionBarActivity {
    private PagerSlidingTabStrip mSlidingTabLayout;
    private CustomViewPager mViewPager;
    private View mToolbar;
    private FragmentManager fm;
    public static Context mContext;
    public static DisplayImageOptions options;
    public static DisplayImageOptions optionsBg;

    public static float fabTransWidth;
    FloatingActionButton fab;
    public static int prePage = 0;
    public static boolean onDial = false;
    android.support.v4.app.FragmentTransaction transaction;
    public static float mDisWidth;
    public static float mDisHeight;
    public static float mStatusBarHeight;
    public static boolean isOnfocusScrollView = false;
    public static RelativeLayout main_layout;
    private float  maxScrollY = 0;
    private float minScrollY = 20f;
    private float minSlideDownStartY;
    private static boolean isOnPageViewScroll = false;
    private VelocityTracker mVelocityTracker;
    private int MAX_UP_DOWN_DURATION = 300;
    public static boolean isOnReadyScrollView = false;
    private ImageButton settingButton;
    public static int PREFERENCE_SETTING = 1000;
    private ImageView app_bg_img;
    private PreferenceManager preferenceManager;
    private int currentPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        preferenceManager = new PreferenceManager(mContext);
        SettingInfo.setCurrentSetting(mContext);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_face_grey600_48dp)
                .showImageForEmptyUri(R.drawable.ic_face_grey600_48dp)
                .showImageOnFail(R.drawable.ic_face_grey600_48dp)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(1000))
                .build();

        optionsBg = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.dummy_img)
                .showImageForEmptyUri(R.drawable.dummy_img)
                .showImageOnFail(R.drawable.dummy_img)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        if(savedInstanceState == null){
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
        }
        initImageLoader(this);
        initUpDownScroll();
        main_layout = (RelativeLayout)findViewById(R.id.main_layout);

        displayConfig();

        fm = this.getSupportFragmentManager();
        main_layout = (RelativeLayout)findViewById(R.id.main_layout);
        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MainViewPagerAdpater(fm,4));
        mSlidingTabLayout = (PagerSlidingTabStrip) findViewById(R.id.sliding_tabs);

        mSlidingTabLayout.setDividerColor(ResoursesManager.getColorResource("colorTransparent",mContext));
        mSlidingTabLayout.setIndicatorColor(ResoursesManager.getColorResource("colorControlHighlight",mContext));
        mSlidingTabLayout.setTextColor(ResoursesManager.getColorResource("colorLightText",mContext));
        mSlidingTabLayout.setUnderlineHeight(0);

        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (prePage == 0 && position != 0) {

                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(fab, "translationX", 0, fabTransWidth));
                    set.setDuration(300).start();

                } else if (prePage != 0 && position == 0) {

                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(fab, "translationX", fabTransWidth, 0));
                    set.setDuration(300).start();
                }
                prePage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state!=0){
                    isOnPageViewScroll = true;
                }else{
                    isOnPageViewScroll = false;
                }


            }
        });

        mSlidingTabLayout.setViewPager(mViewPager);
        app_bg_img = (ImageView)findViewById(R.id.app_bg_img);
        String appBgUri = preferenceManager.getValue(SettingInfo.WALLPAPER_URI,null);
        Log.e("App Bg Uri", appBgUri + "  ");
        if(appBgUri!=null) {
            //appBgUri = "file://"+appBgUri;
            ImageLoader.getInstance().displayImage(appBgUri, app_bg_img, optionsBg);
        }else{
            app_bg_img.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialFragment dialFragment = DialFragment.newInstance("Dial");
                transaction = getSupportFragmentManager().beginTransaction();

                transaction.setCustomAnimations(R.anim.slide_in_top,R.anim.slide_out_bottom,R.anim.slide_in_top,R.anim.slide_out_bottom);
                transaction.replace(android.R.id.content,dialFragment,"dialFragment");
                transaction.addToBackStack(null).commit();
                onDial = true;
            }
        });

        RelativeLayout hide_layout = (RelativeLayout)findViewById(R.id.hide_layout);
        RelativeLayout.LayoutParams hide_layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,(int)maxScrollY);
        hide_layout.setLayoutParams(hide_layoutParams);
        settingButton = (ImageButton)findViewById(R.id.app_setting);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(intent, PREFERENCE_SETTING);
            }
        });
        //currentPosition = mViewPager.getCurrentItem();
    }
    private float startY;
    private float downY;
    private float onMoveY;
    private float startX;
    private float downX;
    private float onMoveX;
    private float velocity = 0;
    private float mBaseLineFlingVelocity;
    private float mFlingVelocityInfluence;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mTouchSlop;

    void initUpDownScroll(){
        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        float density = mContext.getResources().getDisplayMetrics().density;
        mBaseLineFlingVelocity = 2500.0f * density;
        mFlingVelocityInfluence = 0.4f;
    }
    int deltaX = 0;
    int deltaY = 0;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:

                downY = event.getY();
                downX = event.getX();
                startY = main_layout.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.computeCurrentVelocity(1);
                velocity = mVelocityTracker.getYVelocity();
                onMoveY = event.getY();
                onMoveX = event.getX();
                deltaY = (int)(onMoveY-downY);
                deltaX = (int)(onMoveX-downX);

                int newY = (int)(deltaY+startY);

                if (Math.abs(newY) < maxScrollY) {
                    if(Math.abs(velocity) > mMinimumVelocity){
                        break;
                    }
                    Log.e("Main Scroll Down", "isOnReadyScrollView " + isOnReadyScrollView);

                    if((main_layout.getY() >= 0) && (isOnPageViewScroll ==false)) {
                        if (isOnReadyScrollView == true) {
                            if(Math.abs(deltaY)>(3*Math.abs(deltaX))) {
                                if ((startY <= 0) && (deltaY < minScrollY)) {

                                } else {
                                    if(startY<downY) {
                                        mViewPager.setPagingDisabled();
                                        isOnfocusScrollView = true;
                                    }
                                }
                            }
                        }
                    }else{

                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                final float main_layoutDelta = (float)Math.abs(deltaY)/(maxScrollY);
                int duration = (int)(main_layoutDelta*100);
                velocity = Math.abs(velocity);
                if (velocity > 0) {
                    duration += (duration / (velocity / mBaseLineFlingVelocity)) * mFlingVelocityInfluence;
                } else {
                    duration += 100;
                }
                duration = Math.min(duration, MAX_UP_DOWN_DURATION);
                float upY = event.getY();
                deltaY = Math.abs(deltaY);


                    if (isOnfocusScrollView && (downY > upY) && (deltaY > minScrollY)) {

                        slideUpMainLayout(startY,main_layout.getY(),duration);

                    } else if (isOnfocusScrollView &&(downY < upY) && (deltaY > minSlideDownStartY)) {

                        slideDownMainLayout(main_layout.getY(),duration);

                    } else if (isOnfocusScrollView &&(downY > upY) && (deltaY < minScrollY)) {

                        slideDownMainLayout(main_layout.getY(),duration);

                    } else if (isOnfocusScrollView &&(downY < upY) && (deltaY < minSlideDownStartY)) {

                        if(startY==0) {
                            slideUpMainLayout(startY, main_layout.getY(), duration);
                        }
                    }


                if (main_layout.getY() < 0) {
                    ViewHelper.setTranslationY(main_layout, 0);
                    isOnfocusScrollView = false;

                }
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onResume() {
        if(SettingInfo.WALLPAPER_URI == null){
            SettingInfo.setCurrentSetting(mContext);
        }
        super.onResume();

    }

    private void slideDownMainLayout(float curY,int duration){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(main_layout, "translationY", curY, maxScrollY));
        if(isOnPageViewScroll == false) {
            set.setDuration(duration).start();
        }
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void slideUpMainLayout(float startY, float curY, int duration){

        if(startY<downY) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(com.nineoldandroids.animation.ObjectAnimator.ofFloat(main_layout, "translationY", curY, 0));
            set.setDuration(duration).start();
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isOnfocusScrollView = false;
                    isOnReadyScrollView = false;
                    mViewPager.setPagingEnabled();

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
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



    public static ArrayList<ContactsClass> getPhoneBooKList(boolean isStarredList,Context context){

        ArrayList<ContactsClass> contactsArray = new ArrayList<ContactsClass>();
        ContactsClass contactsClass = new ContactsClass();
        Uri contactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String disId = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String disName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String number = ContactsContract.CommonDataKinds.Phone.NUMBER;
        String disStarred = ContactsContract.CommonDataKinds.Phone.STARRED;
        String phone_type_key = ContactsContract.CommonDataKinds.Phone.TYPE;
        Cursor cursor = context.getContentResolver().query(contactsUri, new String[]{disId,disName,number,disStarred,phone_type_key},null,null,null);

        int i = 0;
        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                long id = Long.parseLong(cursor.getString(0));

                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                int starred = Integer.parseInt(cursor.getString(3));
                int phone_type = Integer.parseInt(cursor.getString(4));
                contactsClass = new ContactsClass();

                contactsClass.friendId = id;
                contactsClass.friendName = name;
                contactsClass.friendNum = phone;
                contactsClass.friendStarred = starred;
                contactsClass.phone_type = phone_type;

                ContentResolver contentResolver = context.getContentResolver();
                Uri imageUrl = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);

                contactsClass.friendPictureUrl = imageUrl.toString();
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

    public static String getPhoneType(Context context,int phone_type){
        String phoneType = "";
        switch(phone_type){
            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                phoneType = ResoursesManager.getStringResource("phone_type_home",context);
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                phoneType = ResoursesManager.getStringResource("phone_type_work",context);
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                phoneType = ResoursesManager.getStringResource("phone_type_mobile",context);

                break;

            case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN:
                phoneType = ResoursesManager.getStringResource("phone_type_main",context);
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK:
                phoneType = ResoursesManager.getStringResource("phone_type_fax_work",context);
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME:
                phoneType = ResoursesManager.getStringResource("phone_type_fax_home",context);
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_ISDN:
                phoneType = ResoursesManager.getStringResource("phone_type_isdn",context);
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                phoneType = ResoursesManager.getStringResource("phone_type_other",context);
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM:
                phoneType = ResoursesManager.getStringResource("phone_type_custom",context);
                break;

        }
        return phoneType;
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

    public void displayConfig(){
        DisplayConfig displayConfig = new DisplayConfig(mContext,MainActivity.this);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        mDisWidth = displayConfig.getDisplayWidth();
        mDisHeight = displayConfig.getDisplayHeight();
        float fabMarginRight = ResoursesManager.getDimensionResource("item_margin",mContext);
        fabTransWidth = mDisWidth/2 - (fabMarginRight + displayConfig.convertDpToPixel(24,mContext));
        maxScrollY = mDisHeight/2;
        minSlideDownStartY = mDisHeight/16;
        main_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Activity activity = MainActivity.this;
                Rect rectangle = new Rect();
                Window window = activity.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
                int statusBarTop = rectangle.top;
                int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
                mStatusBarHeight = Math.abs(statusBarTop - contentViewTop);
                Log.e("mDisplayStatusBarHeight",""+ mStatusBarHeight);
            }
        });

    }

   /* // onCreate()에서 StatusBar 구하는 메서드 ( Density 이용 )
    private void getStatusBarSizeOnCreate(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);

        int statusBarHeight;

        switch (displayMetrics.densityDpi) {
            case DisplayMetrics.DENSITY_HIGH:
                mStatusBarHeight = HIGH_DPI_STATUS_BAR_HEIGHT;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                mStatusBarHeight = MEDIUM_DPI_STATUS_BAR_HEIGHT;
                break;
            case DisplayMetrics.DENSITY_LOW:
                mStatusBarHeight = LOW_DPI_STATUS_BAR_HEIGHT;
                break;
            default:
                mStatusBarHeight = MEDIUM_DPI_STATUS_BAR_HEIGHT;
        }
        Log.i("StatusBarTest" , "onCreate StatusBar Height= " + mStatusBarHeight);
    }*/
}
