package pr.jason.myuipratice;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;

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
    private View v;
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
        v.setVisibility(View.GONE);
        this.v = v;
        return  v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(getDialog() == null){
            return;
        }

       /* if(pointY < MainActivity.mDisHeight/5) {
            getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_0);
            Log.e("Scale Ani", "Number 0: " + pointY);
            return;
        }else if(pointY < MainActivity.mDisHeight/5*2){
            getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_1);
            Log.e("Scale Ani", "Number 1: " + pointY);
            return;
        }else if(pointY < MainActivity.mDisHeight/5*3){
            getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_2);
            Log.e("Scale Ani", "Number 2: " + pointY);
            return;
        }else if(pointY < MainActivity.mDisHeight/5*4){
            getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_3);
            Log.e("Scale Ani", "Number 3: " + pointY);
            return;
        }else if(pointY < MainActivity.mDisHeight){
            getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_4);
            Log.e("Scale Ani", "Number 4: " + pointY);
            return;
        }else{
            getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_2);
            Log.e("Scale Ani", "Number default: " + pointY);
        }*/
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Log.e("Dialog show","show");
                AnimatorSet set = new AnimatorSet();
                set.playTogether(
                        ObjectAnimator.ofFloat(v, "scaleX", 0, 1),
                        ObjectAnimator.ofFloat(v, "scaleY", 0, 1),
                        ObjectAnimator.ofFloat(v, "alpha", 0, 1));
                AnimatorProxy.wrap(v).setPivotY(pointY/MainActivity.mDisHeight);
                Log.e("Dialog","pointY/MainActivity.mDisHeight: " + pointY/MainActivity.mDisHeight);

                set.setDuration(300);
                set.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        v.setVisibility(View.VISIBLE);
                        Log.e("Dialog","onAnimationStart");
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                set.start();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.e("Dialog dismiss","dismiss");

            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.e("Dialog cancel","cancel");
                dialog.dismiss();
            }
        });

        return dialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.e("Dialog onCancel","cancel");
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation scaleAni = new ScaleAnimation(0f,1f,0f,1f,0.5f,pointY/MainActivity.mDisHeight);
        scaleAni.setDuration(200);
        Log.e("onCreateAnimation","onCreateAnimation enter " + enter);
        if(enter){
            return scaleAni;
        }

        return super.onCreateAnimation(transit, enter, nextAnim);
    }


}
