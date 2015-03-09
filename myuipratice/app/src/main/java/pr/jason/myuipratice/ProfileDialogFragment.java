package pr.jason.myuipratice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Created by Jaesin on 2015-03-09.
 */
public class ProfileDialogFragment extends DialogFragment{
    private static final String POINT_X_KEY = "POINT_X";
    private static final String POINT_Y_KEY = "POINT_Y";
    private float pointX;
    private float pointY;
    private ImageView profile_photo_bg_img;
    private ImageView profile_photo_img;
    public static ProfileDialogFragment newInstance(float pointX, float pointY){
        ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
        Bundle args = new Bundle();
        args.putFloat(POINT_X_KEY, pointX);
        args.putFloat(POINT_Y_KEY,pointY);
        profileDialogFragment.setArguments(args);
        return profileDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pointX = getArguments().getFloat(POINT_X_KEY,0f);
        pointY = getArguments().getFloat(POINT_Y_KEY,0f);
        View v = inflater.inflate(R.layout.profile_dialog_fragment,container,false);
        profile_photo_bg_img = (ImageView)v.findViewById(R.id.profile_photo_bg_img);
        profile_photo_img = (ImageView)v.findViewById(R.id.profile_photo_img);

        return  v;

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if()
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


}
