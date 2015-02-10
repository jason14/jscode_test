package pr.jason.myuipratice;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pr.jason.myuipratice.util.StringMatcher;

/**
 * Created by Jaesin on 2015-02-09.
 */
public class ContactAdapter extends BaseAdapter implements SectionIndexer {
    private ArrayList<ContactsClass> contactsArray;
    private LayoutInflater inflater;
    private Context mContext;
    private String mSections;
    private ViewHolder viewHolder;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    DisplayImageOptions options;
    /*public ContactArrayAdapter(Context context, int resource) {
        super(context, resource);
    }*/

    public ContactAdapter(Context context, int resource, ArrayList<ContactsClass> contactsArray, DisplayImageOptions options){
        //super(context,resource);
        this.contactsArray = contactsArray;
        mContext = context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int size = contactsArray.size();
        mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";
        this.options = options;

    }

    @Override
    public int getCount() {
        return contactsArray.size();
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
            v = inflater.inflate(R.layout.contacts_row,null);
            viewHolder.imageView = (ImageView)v.findViewById(R.id.picture_iv);
            viewHolder.textView = (TextView)v.findViewById(R.id.name_tv);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        Log.e("getView name", contactsArray.get(position).friendName+" " + contactsArray.get(position).friendPictureUrl.toString());
        viewHolder.textView.setText(contactsArray.get(position).friendName);
        ImageLoader.getInstance().displayImage(contactsArray.get(position).friendPictureUrl.toString(), viewHolder.imageView, options, animateFirstListener);
        return v;
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = sectionIndex; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (StringMatcher.match(String.valueOf(contactsArray.get(j).friendName.charAt(0)), String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(contactsArray.get(j).friendName.charAt(0)), String.valueOf(mSections.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return position;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;

    }

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
