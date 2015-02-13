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
import android.view.View;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
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


public class MainActivity extends ActionBarActivity {
    private PagerSlidingTabStrip mSlidingTabLayout;
    private ViewPager mViewPager;
    private View mToolbar;
    private FragmentManager fm;
    public static Context mContext;
    public static DisplayImageOptions options;

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
        fm = this.getSupportFragmentManager();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MainViewPagerAdpater(fm,3));
        mSlidingTabLayout = (PagerSlidingTabStrip) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);

        Log.e("시작","시작");
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
