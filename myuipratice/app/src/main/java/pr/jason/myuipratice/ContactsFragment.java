package pr.jason.myuipratice;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import pr.jason.myuipratice.widget.IndexableListView;

/**
 * Created by Jaesin on 2015-02-05.
 */
public class ContactsFragment extends Fragment{

    private String title;
    private int page;
    private Context context;
    private ContactsClass contactsClass;
    private ArrayList<ContactsClass> contactsArray;
    DisplayImageOptions options;
    private MainActivity mainActivity;
    public static ContactsFragment newInstance(int page,String title){
        ContactsFragment contactsFragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putInt("somePage",page);
        args.putString("someTitle",title);
        contactsFragment.setArguments(args);
        return contactsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page= getArguments().getInt("somePage",0);
        title = getArguments().getString("someTitle");
        context = getActivity().getApplicationContext();

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(1000))
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment,container,false);
        //연락처 목록 받기
        contactsArray = new ArrayList<ContactsClass>();
        contactsArray = ((MainActivity)MainActivity.mContext).getPhoneBooKList();

        //getPhoneBoolList();
        //정렬
        setSortArrayList();
        IndexableListView listView = (IndexableListView)view.findViewById(R.id.listview);
        ContactAdapter contactAdapter = new ContactAdapter(context,R.layout.contacts_row,contactsArray,options);
        listView.setAdapter(contactAdapter);
        listView.setFastScrollEnabled(true);
        Log.e("연락처 배열 개수", "" + contactsArray.size());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AnimateFirstDisplayListener.displayedImages.clear();
    }

    public void setSortArrayList(){
        final Comparator<ContactsClass> comparator = new Comparator<ContactsClass>() {
            @Override
            public int compare(ContactsClass lhs, ContactsClass rhs) {
                return Collator.getInstance().compare(lhs.friendName,rhs.friendName);
            }
        };

        Collections.sort(contactsArray,comparator);

    }

   /* public void getPhoneBoolList(){

        contactsArray = new ArrayList<ContactsClass>();
        Uri contactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String disId = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String disName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String number = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Cursor cursor = context.getContentResolver().query(contactsUri, new String[]{disId,disName,number},null,null,null);

        int i = 0;
        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                long id = Long.parseLong(cursor.getString(0));
                String name = cursor.getString(1);
                String phone = cursor.getString(2);

                contactsClass = null;
                contactsClass = new ContactsClass();

                contactsClass.friendId = id;
                contactsClass.friendName = name;
                contactsClass.friendNum = phone;

                ContentResolver contentResolver = context.getContentResolver();
                Uri imageUrl = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,id);
                contactsClass.friendPictureUrl = imageUrl;
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
    }
*/
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

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
