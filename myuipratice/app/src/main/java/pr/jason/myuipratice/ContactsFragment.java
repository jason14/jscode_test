package pr.jason.myuipratice;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.InputStream;

/**
 * Created by Jaesin on 2015-02-05.
 */
public class ContactsFragment extends Fragment{

    private String title;
    private int page;
    private Context context;
    private ContactsClass contactsClass;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment,container,false);
        contactsClass = new ContactsClass();
        getPhoneBoolList();
        ListView listView = (ListView)view.findViewById(R.id.listview);

        return view;
    }

    public void getPhoneBoolList(){
        Uri contactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String disId = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String disName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String number = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Cursor cursor = context.getContentResolver().query(contactsUri, new String[]{disId,disName,number},null,null,null);
        contactsClass.friendId = new long[cursor.getCount()];
        contactsClass.friendPicture = new Bitmap[cursor.getCount()];
        contactsClass.friendCount = cursor.getCount();
        contactsClass.friendName = new String[cursor.getCount()];
        contactsClass.friendName = new String[cursor.getCount()];
        int i = 0;
        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                long id = Long.parseLong(cursor.getString(0));
                String name = cursor.getString(1);
                String phone = cursor.getString(2);

                contactsClass.friendId[i] = id;
                contactsClass.friendName[i] = name;
                contactsClass.friendNum[i] = phone;

                ContentResolver contentResolver = context.getContentResolver();
                Uri imageUrl = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,id);
                InputStream io = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver,imageUrl);
                if(io != null){
                    Bitmap photo = BitmapFactory.decodeStream(io);
                    contactsClass.friendPicture[i] = photo;

                }else{
                    contactsClass.friendPicture[i] = null;
                }
                i++;
                cursor.moveToNext();
            }
            cursor.close();
        }
    }
}
