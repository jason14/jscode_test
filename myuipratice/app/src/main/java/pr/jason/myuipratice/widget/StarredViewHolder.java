package pr.jason.myuipratice.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pr.jason.myuipratice.R;

/**
 * Created by Jaesin on 2015-02-13.
 */
public class StarredViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public ImageView imageView;
    public StarredViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.starred_name_tv);
        imageView = (ImageView) itemView.findViewById(R.id.starred_photo_iv);
    }
}