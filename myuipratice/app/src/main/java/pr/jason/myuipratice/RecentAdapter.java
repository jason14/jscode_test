package pr.jason.myuipratice;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import pr.jason.myuipratice.util.TimeConvert;

/**
 * Created by Jaesin on 2015-02-12.
 */
public class RecentAdapter extends BaseAdapter{

    private ArrayList<ContactsClass> callArrays;
    private Context mContext;
    private LayoutInflater inflater;
    DisplayImageOptions options;
    private ViewHolder viewHolder;
    private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();

    public RecentAdapter(Context context,ArrayList<ContactsClass> callArrays, DisplayImageOptions options){
        this.mContext = context;
        this.callArrays = callArrays;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.options = options;
    }

    @Override
    public int getCount() {
        return callArrays.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView==null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.recent_row,parent, false);
            viewHolder.picture_iv = (ImageView)v.findViewById(R.id.picture_iv);
            viewHolder.name_tv = (TextView)v.findViewById(R.id.name_tv);
            viewHolder.number_tv = (TextView)v.findViewById(R.id.number_tv);
            viewHolder.date_tv = (TextView)v.findViewById(R.id.date_tv);
            viewHolder.type_duration_tv = (TextView)v.findViewById(R.id.type_duration_tv);
            viewHolder.type_iv = (ImageView)v.findViewById(R.id.type_iv);

            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }


        if(callArrays.get(position).friendName == null||callArrays.get(position).friendName.equals("")){
            viewHolder.name_tv.setVisibility(View.GONE);
        }else{
            viewHolder.name_tv.setVisibility(View.VISIBLE);
            viewHolder.name_tv.setText(callArrays.get(position).friendName);
            viewHolder.name_tv.setTextAppearance(mContext,R.style.myContactsListRowText);
        //    Log.e("friendName"," friendName : " + callArrays.get(position).friendName+ " ");

        }

        viewHolder.number_tv.setText(callArrays.get(position).friendNum);

        String imageUri = "";
        String type_duration = "";
        if(callArrays.get(position).type == CallLog.Calls.MISSED_TYPE){
             imageUri = "drawable://"+R.drawable.ic_call_missed_grey600_48dp;
             type_duration = mContext.getString(R.string.call_missed);
        }else if(callArrays.get(position).type == CallLog.Calls.INCOMING_TYPE){
             imageUri = "drawable://"+R.drawable.ic_call_received_grey600_48dp;
             type_duration = mContext.getString(R.string.call_received) + " " + TimeConvert.parseTime(callArrays.get(position).duration*1000);

        }else if(callArrays.get(position).type == CallLog.Calls.OUTGOING_TYPE){
             imageUri = "drawable://"+R.drawable.ic_call_made_grey600_48dp;
            if(callArrays.get(position).duration <= 0){
                type_duration = mContext.getString(R.string.call_not_connected);
            }else{
                type_duration = mContext.getString(R.string.call_made) + " " + TimeConvert.parseTime(callArrays.get(position).duration *1000);
            }

        }else{
            imageUri = null;
            type_duration = mContext.getString(R.string.call_rejected);
        }

        viewHolder.type_duration_tv.setText(type_duration);



        ImageLoader.getInstance().displayImage(imageUri, viewHolder.type_iv, options, animateFirstListener);

        String peoplePhotoUri = "";
        String number = callArrays.get(position).friendNum;
        if(callArrays.get(position).cashed_photo_id != 0) {
            String id = fetchContactIdFromPhoneNumber(number);
            Log.e("id", "아이디 " + id);
            if (id != null && !id.equals("")) {
                peoplePhotoUri = getPhotoUri(Long.parseLong(id)).toString();
            }
        }
        if(peoplePhotoUri != null && !peoplePhotoUri.equals("")) {


        }else{
            peoplePhotoUri = "drawable://"+R.drawable.ic_face_grey600_48dp;
        }

        ImageLoader.getInstance().displayImage(peoplePhotoUri, viewHolder.picture_iv, options, animateFirstListener);


        viewHolder.date_tv.setText(TimeConvert.formatTimeString(callArrays.get(position).date));

        return v;
    }

    class ViewHolder{
        ImageView picture_iv;
        TextView name_tv;
        TextView number_tv;
        TextView date_tv;
        TextView type_duration_tv;
        ImageView type_iv;
    }


    public Uri getPhotoUri(long contactId) {
        ContentResolver contentResolver = mContext.getContentResolver();
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
        Cursor cursor = mContext.getContentResolver().query(uri,new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID },null, null, null);

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
