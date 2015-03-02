package pr.jason.myuipratice;

import android.content.Context;
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

/**
 * Created by Jaesin on 2015-02-24.
 */
public class DialAdapter extends BaseAdapter {
    private ArrayList<ContactsClass> resultArrays;
    private Context mContext;
    private LayoutInflater inflater;
    DisplayImageOptions options;
    private ViewHolder viewHolder;
    private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();

    public DialAdapter(Context context,ArrayList<ContactsClass> resultArrays, DisplayImageOptions options){
        this.mContext = context;
        this.resultArrays = resultArrays;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.options = options;
    }
    @Override
    public int getCount() {
       return resultArrays.size();
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
            v = inflater.inflate(R.layout.dial_contacts_row,null);
            viewHolder.imageView = (ImageView)v.findViewById(R.id.picture_iv);
            viewHolder.textView = (TextView)v.findViewById(R.id.name_tv);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.textView.setTextAppearance(mContext,R.style.myListRowText);
        viewHolder.textView.setText(resultArrays.get(position).friendName);

        if(resultArrays.get(position).friendPictureUrl != null && !resultArrays.get(position).friendPictureUrl.equals("")) {
            ImageLoader.getInstance().displayImage(resultArrays.get(position).friendPictureUrl.toString(), viewHolder.imageView, options, animateFirstListener);
        }else{

        }
        return v;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;

    }
}
