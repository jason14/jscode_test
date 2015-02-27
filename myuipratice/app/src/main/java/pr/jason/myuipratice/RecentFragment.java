package pr.jason.myuipratice;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;

import pr.jason.myuipratice.util.MyLog;

/**
 * Created by Jaesin on 2015-02-05.
 */
public class RecentFragment extends Fragment {
    private String title;
    private int page;
    private ArrayList<ContactsClass> callsArray;
    private boolean isListViewScrollTop = true;
    ListView listView;
    public static RecentFragment newInstance(int page,String title){
        RecentFragment recentFragment = new RecentFragment();
        Bundle args = new Bundle();
        args.putInt("somePage",page);
        args.putString("someTitle",title);
        recentFragment.setArguments(args);

        return recentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page= getArguments().getInt("somePage",0);
        title = getArguments().getString("someTitle");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_fragment,container,false);
        MainActivity.isOnfocusScrollView = false;
        callsArray = new ArrayList<ContactsClass>();
        callsArray = getCallsList();
       // callsArray = addPhotoUri(callsArray);
        RecentAdapter recentAdapter = new RecentAdapter(getActivity().getApplicationContext(),callsArray,MainActivity.options);
        listView =(ListView)view.findViewById(R.id.listview);
        listView.setAdapter(recentAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int scrolly = getScrollY();

                if(scrolly==0&&scrollState == 0){
                    isListViewScrollTop = true;
                }else{
                    isListViewScrollTop = false;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


            }
        });

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(MainActivity.isOnfocusScrollView){
                   // MainActivity.main_layout.requestDisallowInterceptTouchEvent(false);
                    MyLog.LogMessage("MOVE 터치",MainActivity.isOnfocusScrollView+" RecentFragment isOnfocusScrollView");
                    event.setAction(MotionEvent.ACTION_CANCEL);
                }else{
                    MyLog.LogMessage("MOVE isListViewScrollTop",isListViewScrollTop+" isListViewScrollTop");

                    if(isListViewScrollTop){
                        if(isDragDown(event)){
                            MyLog.LogMessage("UP 터치",MainActivity.isOnfocusScrollView+"");

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

        return view;
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

    @Override
    public void onResume() {
        Log.e("onResume ",""+"resume");

            int scrolly = getScrollY();
            Log.e("onResume scroll 위치",""+scrolly);
            if(scrolly==0){
                isListViewScrollTop = true;
            }else {
                isListViewScrollTop = false;
            }
        super.onResume();
    }

    private int getScrollY(){
        int scrolly = 0;
        View c = listView.getChildAt(0);
        try {
            scrolly = -c.getTop() + listView.getFirstVisiblePosition() * c.getHeight();
           return scrolly;
        }catch(NullPointerException e){
            Log.e("listView.getFirstVisiblePosition()", ""+ e);
            return 0;
        }
    }

    public ArrayList<ContactsClass> getCallsList(){
        ArrayList<ContactsClass> callsArray = new ArrayList<ContactsClass>();
        Uri callUri = CallLog.Calls.CONTENT_URI;
        String callNumber = CallLog.Calls.NUMBER;
        String callName = CallLog.Calls.CACHED_NAME;
        String callType = CallLog.Calls.TYPE;
        String callDuration = CallLog.Calls.DURATION;
        String callDate = CallLog.Calls.DATE;
        String cashedPhoto = CallLog.Calls.CACHED_PHOTO_ID;
        Cursor cursor = getActivity().getContentResolver().query(callUri, new String[]{callNumber,callName,callType,callDuration,callDate,cashedPhoto},null,null,null);

        while(cursor.moveToNext()){
            String number = cursor.getString(0);
            String name = null;
            name = cursor.getString(1);

            int type = Integer.parseInt(cursor.getString(2));
            Long duration = Long.parseLong(cursor.getString(3));
            Long date = Long.parseLong(cursor.getString(4));
            Long photo = Long.parseLong(cursor.getString(5));

            ContactsClass contactsClass = new ContactsClass();

            contactsClass.friendName = name;
            contactsClass.friendNum = number;
            contactsClass.type = type;
            contactsClass.duration = duration;
            contactsClass.date = date;
            contactsClass.cashed_photo_id = photo;
            callsArray.add(contactsClass);
        }
        cursor.close();
        return callsArray;
    }

    public ArrayList<ContactsClass> addPhotoUri(ArrayList<ContactsClass> callsArray){

        for(int i = 0; i < callsArray.size(); i++){
            Uri imageUrl = null;
            if(callsArray.get(i).cashed_photo_id!=0){
                Log.e( " Count ", "이미지 있는 count:  "+ i);
                String number = callsArray.get(i).friendNum;
                String id = fetchContactIdFromPhoneNumber(number);

                if (id != null && !id.equals("")) {
                    imageUrl = getPhotoUri(Long.parseLong(id));
                }

            }
            callsArray.get(i).friendPictureUrl = imageUrl;
        }
        return callsArray;
    }

    public Uri getPhotoUri(long contactId) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor cursor = null;
        try {
             cursor =
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
        cursor.close();

        Uri person = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI, contactId);
        return person;
    }

    public String fetchContactIdFromPhoneNumber(String phoneNumber) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));
        Cursor cursor = getActivity().getContentResolver().query(uri,new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID },null, null, null);

        String contactId = "";

        if (cursor.moveToFirst()) {
            do {
                contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup._ID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactId;
    }



}
