package pr.jason.myuipratice;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

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
    private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();
    DisplayImageOptions options;
    private float mRowHeight;

    public ContactAdapter(Context context, int resource, ArrayList<ContactsClass> contactsArray, DisplayImageOptions options){
        //super(context,resource);
        this.contactsArray = contactsArray;
        mContext = context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int size = contactsArray.size();
        mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";
        this.options = options;
        mRowHeight = MainActivity.mDisHeight/9;
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
            v = inflater.inflate(R.layout.contacts_row, parent, false);
            viewHolder.linearLayout = (LinearLayout)v.findViewById(R.id.contacts_row_layout);
            viewHolder.imageView = (ImageView)v.findViewById(R.id.picture_iv);
            viewHolder.textView = (TextView)v.findViewById(R.id.name_tv);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        final ViewGroup.LayoutParams params = v.getLayoutParams();
        if(params == null){
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)mRowHeight));
        }else{
            params.height = (int)mRowHeight;
        }
        viewHolder.linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        viewHolder.textView.setTextAppearance(mContext,R.style.myContactsListRowText);
        viewHolder.textView.setText(contactsArray.get(position).friendName);

        if(contactsArray.get(position).friendPictureUrl != null && !contactsArray.get(position).friendPictureUrl.equals("")) {
            ImageLoader.getInstance().displayImage(contactsArray.get(position).friendPictureUrl, viewHolder.imageView, options, animateFirstListener);
        }else{

        }
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
                        if (contactsArray.get(j).friendName!=null&&StringMatcher.match(String.valueOf(contactsArray.get(j).friendName.charAt(0)), String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (contactsArray.get(j).friendName!=null&&StringMatcher.match(String.valueOf(contactsArray.get(j).friendName.charAt(0)), String.valueOf(mSections.charAt(i))))
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
        LinearLayout linearLayout;
    }


}
