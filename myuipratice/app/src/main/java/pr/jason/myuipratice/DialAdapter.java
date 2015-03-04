package pr.jason.myuipratice;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
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
            viewHolder.name_tv = (TextView)v.findViewById(R.id.name_tv);
            viewHolder.what_number_tv = (TextView)v.findViewById(R.id.what_number_tv);
            viewHolder.number_tv = (TextView)v.findViewById(R.id.number_tv);
            v.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.name_tv.setTextAppearance(mContext,R.style.myDialListRowText);

        viewHolder.name_tv.setText(resultArrays.get(position).friendName);

        viewHolder.what_number_tv.setText(MainActivity.getPhoneType(mContext, resultArrays.get(position).phone_type));

        String formattedPhonNumber = "";
        String resultPhoneNum = resultArrays.get(position).friendNum;
        if(resultPhoneNum!=null&&!resultPhoneNum.equals("")){
            resultPhoneNum = resultPhoneNum.replace("-","");
            resultPhoneNum = resultPhoneNum.trim();
            formattedPhonNumber = PhoneNumberUtils.formatNumber(resultPhoneNum);
        }
        viewHolder.number_tv.setText(formattedPhonNumber);

        return v;
    }

    class ViewHolder{
        TextView name_tv;
        TextView what_number_tv;
        TextView number_tv;

    }
}
