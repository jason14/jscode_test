package pr.jason.myuipratice;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

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
            v = inflater.inflate(R.layout.recent_row,null);
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
        }

        viewHolder.number_tv.setText(callArrays.get(position).friendNum);

        String imageUri = "";
        String type_duration = "";
        if(callArrays.get(position).type == CallLog.Calls.MISSED_TYPE){
             imageUri = "drawable://"+R.drawable.ic_call_missed_grey600_48dp;
             type_duration = mContext.getString(R.string.call_missed);
        }else if(callArrays.get(position).type == CallLog.Calls.INCOMING_TYPE){
             imageUri = "drawable://"+R.drawable.ic_call_received_grey600_48dp;
             type_duration = mContext.getString(R.string.call_received) + " " + TimeConvert.parseTime(callArrays.get(position).duration);

        }else if(callArrays.get(position).type == CallLog.Calls.OUTGOING_TYPE){
             imageUri = "drawable://"+R.drawable.ic_call_made_grey600_48dp;
            if(callArrays.get(position).duration <= 0){
                type_duration = mContext.getString(R.string.call_not_connected);
            }else{
                type_duration = mContext.getString(R.string.call_made) + " " + TimeConvert.parseTime(callArrays.get(position).duration);
            }

        }
        viewHolder.type_duration_tv.setText(type_duration);
        ImageLoader.getInstance().displayImage(imageUri, viewHolder.type_iv, options, animateFirstListener);
        if(callArrays.get(position).friendPictureUrl != null && !callArrays.get(position).friendPictureUrl.equals("")) {
            ImageLoader.getInstance().displayImage(callArrays.get(position).friendPictureUrl.toString(), viewHolder.picture_iv, options, animateFirstListener);
        }else{

        }
        viewHolder.date_tv.setText(TimeConvert.parseCompareNowTime(callArrays.get(position).date));

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
