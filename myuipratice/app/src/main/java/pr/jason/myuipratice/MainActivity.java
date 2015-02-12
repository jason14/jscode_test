package pr.jason.myuipratice;

import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private PagerSlidingTabStrip mSlidingTabLayout;
    private ViewPager mViewPager;
    private View mToolbar;
    private FragmentManager fm;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
       if(savedInstanceState == null){
           FragmentTransaction transaction = getFragmentManager().beginTransaction();
       }
        initImageLoader(this);
        fm = this.getSupportFragmentManager();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MainViewPagerAdpater(fm,3));
        mSlidingTabLayout = (PagerSlidingTabStrip) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);

        getCallsList();
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

    public ArrayList<ContactsClass> getCallsList(){
        ArrayList<ContactsClass> callsArray = new ArrayList<ContactsClass>();
        Uri callUri = CallLog.Calls.CONTENT_URI;
        String callNumber = CallLog.Calls.NUMBER;
        String callName = CallLog.Calls.CACHED_NAME;
        String callType = CallLog.Calls.TYPE;
        String callDuration = CallLog.Calls.DURATION;
        String callDate = CallLog.Calls.DATE;
        Cursor cursor = getContentResolver().query(callUri, new String[]{callNumber,callName,callType,callDuration,callDuration},null,null,null);

        while(cursor.moveToNext()){
            String number = cursor.getString(0);
            String name = cursor.getString(1);

            int type = Integer.parseInt(cursor.getString(2));
            Long duration = Long.parseLong(cursor.getString(3));
            Long date = Long.parseLong(cursor.getString(4));

            String id = fetchContactIdFromPhoneNumber(number);
            if (id != null && !id.equals("")) {
                Uri imageUrl = getPhotoUri(Long.parseLong(id));
               // Log.w("Calls Data", " name: " + name);

                      // Log.w("Calls Data", "사진 URL Calls List: " + imageUrl.toString() + " ");
            }
            ContactsClass contactsClass = new ContactsClass();

            contactsClass.friendName = name;
            contactsClass.friendNum = number;
            contactsClass.type = type;
            contactsClass.duration = duration;
            contactsClass.date = date;

            callsArray.add(contactsClass);
        }
        cursor.close();
        return callsArray;
    }

    public Uri getPhotoUri(long contactId) {
        ContentResolver contentResolver = getContentResolver();

        try {
            Cursor cursor =
                    contentResolver.query(ContactsContract.Data.CONTENT_URI,null,ContactsContract.Data.CONTACT_ID+ "="+ contactId+ " AND "+ ContactsContract.Data.MIMETYPE+"='"+ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE+ "'", null, null);

            if (cursor != null) {
                if (!cursor.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Uri person = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI, contactId);
        return Uri.withAppendedPath(person,ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }

    public String fetchContactIdFromPhoneNumber(String phoneNumber) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));
        Cursor cursor = this.getContentResolver().query(uri,new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID },null, null, null);

        String contactId = "";

        if (cursor.moveToFirst()) {
            do {
                contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup._ID));
            } while (cursor.moveToNext());
        }

        return contactId;
    }

    public ArrayList<ContactsClass> getPhoneBooKList(){

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
                contactsClass = null;
                contactsClass = new ContactsClass();

                contactsClass.friendId = id;
                contactsClass.friendName = name;
                contactsClass.friendNum = phone;
                contactsClass.friendStarred = starred;


                ContentResolver contentResolver = getContentResolver();
                Uri imageUrl = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);

                contactsClass.friendPictureUrl = imageUrl;

                if(contactsClass.friendStarred == 1){


                }

                InputStream io = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver,imageUrl);
                if(io != null){
                    Bitmap photo = BitmapFactory.decodeStream(io);
                    contactsClass.friendPicture = photo;

                }else{
                    contactsClass.friendPicture = null;
                }
                contactsArray.add(contactsClass);
                i++;
                cursor.moveToNext();
            }
            cursor.close();
        }

        return contactsArray;
    }
}
