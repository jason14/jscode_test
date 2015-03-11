package pr.jason.myuipratice;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import pr.jason.myuipratice.util.DisplayConfig;

/**
 * Created by Jaesin on 2015-03-09.
 */
public class ProfileDialogFragment extends DialogFragment{
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
    private LinearLayout profile_btn_layout;
    private float listHeight;
    private float listPhotoHeight;
    private ImageButton star_add_btn;
    private ImageButton call_log_btn;
    private ImageButton call_btn;
    private ImageButton video_call_btn;
    private ImageButton text_message_btn;
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
        Log.e("Dialog","photoX " + photoX);
        Log.e("Dialog","photoY " + photoY);
        dialogY = (MainActivity.mDisHeight/10)-listY;
        dialogPhotoPositionY = (int)((MainActivity.mDisHeight)/10) + photo_layout_height/2 - photo_img_size/2;
        listHeight = getArguments().getFloat(LIST_HEIGHT_KEY,0f);
        profile_photo_bg_img = (ImageView)v.findViewById(R.id.profile_photo_bg_img);
        profile_photo_img = (ImageView)v.findViewById(R.id.profile_photo_img);
        profile_photo_layout = (RelativeLayout)v.findViewById(R.id.profile_photo_layout);
        profile_btn_layout = (LinearLayout)v.findViewById(R.id.profile_btn_layout);

        LinearLayout.LayoutParams profile_photo_params = new LinearLayout.LayoutParams(dialogWidth,photo_layout_height);
        profile_photo_layout.setLayoutParams(profile_photo_params);
        LinearLayout.LayoutParams profile_btn_params = new LinearLayout.LayoutParams(dialogWidth,btn_layout_height);
        profile_btn_layout.setLayoutParams(profile_btn_params);
        RelativeLayout.LayoutParams profile_photo_img_params = new RelativeLayout.LayoutParams(photo_img_size,photo_img_size);
        profile_photo_img_params.addRule(RelativeLayout.CENTER_IN_PARENT);
        profile_photo_img.setLayoutParams(profile_photo_img_params);


        star_add_btn = (ImageButton)v.findViewById(R.id.star_add_btn);
        call_btn = (ImageButton)v.findViewById(R.id.call_btn);
        call_log_btn = (ImageButton)v.findViewById(R.id.call_log_btn);
        text_message_btn = (ImageButton)v.findViewById(R.id.text_message_btn);
        video_call_btn = (ImageButton)v.findViewById(R.id.video_call_btn);
        int topBtnsSize =(int) (dialogWidth/2-DisplayConfig.convertDpToPixel(0.5f,getActivity().getApplicationContext()));
        int bottomBtnsSize =(int) (dialogWidth/3-DisplayConfig.convertDpToPixel(0.5f,getActivity().getApplicationContext()));

        int btnsPadding = (int)DisplayConfig.convertDpToPixel(20,getActivity().getApplicationContext());
        LinearLayout.LayoutParams topBtnParams = new LinearLayout.LayoutParams(topBtnsSize, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams bottomBtnParams = new LinearLayout.LayoutParams(bottomBtnsSize,LinearLayout.LayoutParams.MATCH_PARENT);

        star_add_btn.setLayoutParams(topBtnParams);
        star_add_btn.setPadding(btnsPadding, 0, btnsPadding, 0);
        star_add_btn.setAdjustViewBounds(true);
        star_add_btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
        star_add_btn.setImageResource(R.drawable.ic_star_rate_white_48dp);

        call_log_btn.setLayoutParams(topBtnParams);
        call_log_btn.setPadding(btnsPadding, 0, btnsPadding, 0);
        call_log_btn.setAdjustViewBounds(true);
        call_log_btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
        call_log_btn.setImageResource(R.drawable.ic_star_rate_white_48dp);

        call_btn.setLayoutParams(bottomBtnParams);
        call_btn.setPadding(btnsPadding, 0, btnsPadding, 0);
        call_btn.setAdjustViewBounds(true);
        call_btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
        call_btn.setImageResource(R.drawable.ic_phone_white_48dp);

        text_message_btn.setLayoutParams(bottomBtnParams);
        text_message_btn.setPadding(btnsPadding, 0, btnsPadding, 0);
        text_message_btn.setAdjustViewBounds(true);
        text_message_btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
        text_message_btn.setImageResource(R.drawable.ic_messenger_white_48dp);

        video_call_btn.setLayoutParams(bottomBtnParams);
        video_call_btn.setPadding(btnsPadding, 0, btnsPadding, 0);
        video_call_btn.setAdjustViewBounds(true);
        video_call_btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
        video_call_btn.setImageResource(R.drawable.ic_messenger_white_48dp);

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
                ObjectAnimator alphaAni = ObjectAnimator.ofFloat(v, "alpha", 0, 0.5f);
                set.playTogether(
                        ObjectAnimator.ofFloat(profile_photo_img, "translationX", -(MainActivity.mDisWidth/2) + photoX ,0),
                        ObjectAnimator.ofFloat(v, "scaleX", 10/9,1),
                        ObjectAnimator.ofFloat(v, "scaleY", listHeight/dialogHeight * 2, 1),
                        ObjectAnimator.ofFloat(profile_photo_img, "scaleX", listHeight/dialogHeight * 2 * 10 / 9, 1),
                        ObjectAnimator.ofFloat(v, "translationY", listY ,MainActivity.mDisHeight/20),
                        alphaAni);

                set.setDuration(300);
                set.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        v.setVisibility(View.VISIBLE);
                        Log.e("Dialog","onAnimationStart");
                        star_add_btn.setVisibility(View.GONE);
                        call_btn.setVisibility(View.GONE);
                        call_log_btn.setVisibility(View.GONE);
                        text_message_btn.setVisibility(View.GONE);
                        video_call_btn.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        star_add_btn.setVisibility(View.VISIBLE);
                        call_btn.setVisibility(View.VISIBLE);
                        call_log_btn.setVisibility(View.VISIBLE);
                        text_message_btn.setVisibility(View.VISIBLE);
                        video_call_btn.setVisibility(View.VISIBLE);
                        Log.e("Dialog" , " v.getWidth() " + v.getWidth());
                        AnimatorSet set = new AnimatorSet();
                        ObjectAnimator alphaAni = ObjectAnimator.ofFloat(v, "alpha", 0.5f, 1.0f);

                        set.playTogether(
                                ObjectAnimator.ofFloat(star_add_btn, "alpha", 0f ,1f),
                                ObjectAnimator.ofFloat(call_btn, "alpha", 0f ,1f),
                                ObjectAnimator.ofFloat(call_log_btn, "alpha", 0f ,1f),
                                ObjectAnimator.ofFloat(text_message_btn, "alpha", 0f ,1f),
                                ObjectAnimator.ofFloat(video_call_btn, "alpha", 0f ,1f),
                                alphaAni);

                        set.setDuration(100);
                        set.start();
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
