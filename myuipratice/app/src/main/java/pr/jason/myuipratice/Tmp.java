/*
package pr.jason.myuipratice;

*/
/**
 * Created by Jaesin on 2015-03-11.
 *//*


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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

*/
/**
 * Created by Jaesin on 2015-03-09.
 *//*

public class ProfileDialogFragment extends DialogFragment {
    private static final String LIST_X_KEY = "POINT_X";
    private static final String LIST_Y_KEY = "POINT_Y";
    private static final String LIST_HEIGHT_KEY = "LIST_HEIGHT";
    private static final String PHOTO_X_KEY = "PHOTO_X";
    private static final String PHOTO_Y_KEY = "PHOTO_Y";
    private static final String LIST_PHOTO_HEIGHT_KEY = "LIST_PHOTO_HEIGHT_KEY";

    private float listX;
    private float listY;
    private float photoX;
    private float photoY;
    private ImageView profile_photo_bg_img;
    private ImageView profile_photo_img;
    private View v;
    private RelativeLayout profile_photo_layout;
    private RelativeLayout profile_btn_layout;
    private float listHeight;
    private float listPhotoHeight;
    public static ProfileDialogFragment newInstance(float listX, float listY, float listHeight, float photoX, float photoY, float listPhotoHeight){
        ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
        Bundle args = new Bundle();
        args.putFloat(LIST_X_KEY, listX);
        args.putFloat(LIST_Y_KEY,listY);
        args.putFloat(LIST_HEIGHT_KEY,listHeight);
        args.putFloat(PHOTO_X_KEY, photoX);
        args.putFloat(PHOTO_Y_KEY,photoY);
        args.putFloat(LIST_PHOTO_HEIGHT_KEY,listPhotoHeight);

        profileDialogFragment.setArguments(args);

        return profileDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private int dialogWidth;
    private int dialogHeight;
    private int photo_layout_height;
    private int btn_layout_height;
    private int photo_img_size;
    private float dialogY;

    private int dialogPhotoPositionY;
    private View setDialogViewsSizeAndPosition(View v){
        dialogWidth = (int)(MainActivity.mDisWidth/10*9);
        dialogHeight = (int)(MainActivity.mDisHeight/10*9);
        photo_layout_height = (int)(dialogHeight/2);
        btn_layout_height = dialogHeight - photo_layout_height;
        photo_img_size = (int)(dialogWidth/3);
        listX = getArguments().getFloat(LIST_X_KEY,0f);
        listY = getArguments().getFloat(LIST_Y_KEY,0f);
        photoX = getArguments().getFloat(PHOTO_X_KEY,0f);
        photoY = getArguments().getFloat(PHOTO_Y_KEY,0f) + listY;
        Log.e("Dialog", "photoX " + photoX);
        Log.e("Dialog","photoY " + photoY);
        dialogY = (MainActivity.mDisHeight/10)-listY;
        dialogPhotoPositionY = (int)((MainActivity.mDisHeight)/10) + photo_layout_height/2 - photo_img_size/2;
        listHeight = getArguments().getFloat(LIST_HEIGHT_KEY,0f);
        profile_photo_bg_img = (ImageView)v.findViewById(R.id.profile_photo_bg_img);
        profile_photo_img = (ImageView)v.findViewById(R.id.profile_photo_img);
        profile_photo_layout = (RelativeLayout)v.findViewById(R.id.profile_photo_layout);
        profile_btn_layout = (RelativeLayout)v.findViewById(R.id.profile_btn_layout);

        LinearLayout.LayoutParams profile_photo_params = new LinearLayout.LayoutParams(dialogWidth,photo_layout_height);
        profile_photo_layout.setLayoutParams(profile_photo_params);
        LinearLayout.LayoutParams profile_btn_params = new LinearLayout.LayoutParams(dialogWidth,btn_layout_height);
        profile_btn_layout.setLayoutParams(profile_btn_params);
        RelativeLayout.LayoutParams profile_photo_img_params = new RelativeLayout.LayoutParams(photo_img_size,photo_img_size);
        profile_photo_img_params.addRule(RelativeLayout.CENTER_IN_PARENT);
        profile_photo_img.setLayoutParams(profile_photo_img_params);
        RelativeLayout.LayoutParams profile_photo_bg_img_params = new RelativeLayout.LayoutParams(dialogWidth,photo_layout_height);
        profile_photo_img_params.addRule(RelativeLayout.CENTER_IN_PARENT);
        profile_photo_bg_img.setLayoutParams(profile_photo_bg_img_params);
        v.setVisibility(View.GONE);
        return v;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_dialog_fragment,container,false);
        setDialogViewsSizeAndPosition(v);
        this.v = v;
        return  v;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(getDialog() == null){
            return;
        }


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) MainActivity.mDisWidth;
        params.height =(int)MainActivity.mDisHeight;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams)params);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Log.e("Dialog show","show");
                v.setPivotY(0);
                v.setPivotX(MainActivity.mDisWidth / 2);

                AnimatorSet set = new AnimatorSet();
                ObjectAnimator alphaAni = ObjectAnimator.ofFloat(v, "alpha", 0, 1);
                alphaAni.setDuration(4000);
                set.playTogether(
                        ObjectAnimator.ofFloat(profile_photo_img, "translationX", -MainActivity.mDisWidth/2 + photoX , 0),
                        ObjectAnimator.ofFloat(profile_photo_img, "translationY",   - photoY , 0),
                        ObjectAnimator.ofFloat(profile_photo_bg_img, "scaleX", 10/9,1),
                        ObjectAnimator.ofFloat(profile_photo_bg_img, "scaleY", listHeight/dialogHeight * 2, 1),
                        ObjectAnimator.ofFloat(profile_photo_img, "scaleX", listPhotoHeight/photo_img_size , 1),
                        ObjectAnimator.ofFloat(profile_photo_img, "scaleY", listPhotoHeight/photo_img_size, 1),

                        ObjectAnimator.ofFloat(profile_photo_bg_img, "translationY", listY ,0)
                );

                set.setDuration(4000);
                set.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        v.setVisibility(View.VISIBLE);
                        Log.e("Dialog","onAnimationStart");

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        float widthRate = MainActivity.mDisWidth/v.getWidth();
                        Log.e("Dialog" , " v.getWidth() " + v.getWidth());
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
    public void dismiss() {
        Log.e("Dialog dismiss","dismiss");
        //dialog.dismiss();


        // super.dismiss();
    }

    public void parentDismiss(DialogInterface dialog){
        super.onCancel(dialog);
    }

    @Override
    public void onCancel(final DialogInterface dialog) {
        //super.onCancel(dialog);
        //dialog.dismiss();

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(v, "pivotY", listY *(listY /MainActivity.mDisHeight)),
                ObjectAnimator.ofFloat(v, "pivotX", MainActivity.mDisHeight/2),
                ObjectAnimator.ofFloat(v, "scaleX", 1f, 1),
                ObjectAnimator.ofFloat(v, "scaleY", 1f, 0),
                ObjectAnimator.ofFloat(v, "alpha", 1, 0));

        set.setDuration(300);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {


            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("Dialog onCancel" , " v.getWidth() " + v.getWidth());
                //v.setVisibility(View.GONE);
                parentDismiss(dialog);
                Log.e("Dialog onCancel onAnimationEnd","cancel");

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




}
*/
