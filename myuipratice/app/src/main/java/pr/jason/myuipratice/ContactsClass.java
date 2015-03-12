package pr.jason.myuipratice;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Jaesin on 2015-02-09.
 */
public class ContactsClass implements Serializable {

    public long friendId;
    public Bitmap friendPicture;
    public int friendCount;
    public String friendName;
    public String friendNum;
    public String friendPictureUrl;
    public int friendStarred;
    public long date;
    public long duration;
    public int type;
    public long cashed_photo_id;
    public int phone_type;
    public ContactsClass(){

    }
}
