package pr.jason.myuipratice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import pr.jason.myuipratice.widget.StarredViewHolder;

/**
 * Created by Jaesin on 2015-02-13.
 */
public class StarredAdapter extends RecyclerView.Adapter<StarredViewHolder>{
    private ArrayList<ContactsClass> contactsArray;
    DisplayImageOptions options;
    private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();

    public StarredAdapter(ArrayList<ContactsClass> contactsArray,DisplayImageOptions options){
        this.contactsArray = contactsArray;
        this.options = options;
    }

    @Override
    public StarredViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.starred_card,viewGroup,false);

        return new StarredViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StarredViewHolder starredViewHolder, int i) {
        final String name = contactsArray.get(i).friendName;
        starredViewHolder.textView.setText(name);
        if(contactsArray.get(i).friendPictureUrl != null && !contactsArray.get(i).friendPictureUrl.equals("")) {
            ImageLoader.getInstance().displayImage(contactsArray.get(i).friendPictureUrl.toString(), starredViewHolder.imageView, options, animateFirstListener);
        }else{

        }
    }

    @Override
    public int getItemCount() {
        return contactsArray.size();
    }
}
