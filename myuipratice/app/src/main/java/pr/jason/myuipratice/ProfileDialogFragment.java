package pr.jason.myuipratice;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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
    private static final String CONTACTS_CLASS_KEY = "CONTACTS_CLASS_KEY";
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
    private ContactsClass contactsClass;
    private TextView profile_name_tv;
    public static ProfileDialogFragment newInstance(ContactsClass contactsClass, float listX, float listY, float listHeight, float photoX, float photoY, float listPhotoHeight){
        ProfileDialogFragment profileDialogFragment = new ProfileDialogFragment();
        Bundle args = new Bundle();
        args.putFloat(LIST_X_KEY, listX);
        args.putFloat(LIST_Y_KEY,listY);
        args.putFloat(LIST_HEIGHT_KEY,listHeight);
        args.putFloat(PHOTO_X_KEY, photoX);
        args.putFloat(PHOTO_Y_KEY,photoY);
        args.putFloat(LIST_PHOTO_HEIGHT_KEY,listPhotoHeight);
        args.putSerializable(CONTACTS_CLASS_KEY, contactsClass);
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
    private int topBtnsWidth;
    private int bottomBtnsWidth;
    private View setDialogViewsSizeAndPosition(View v){
        contactsClass = (ContactsClass)getArguments().getSerializable(CONTACTS_CLASS_KEY);
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
        profile_name_tv = (TextView)v.findViewById(R.id.profile_name_tv);
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

        topBtnsWidth =(int) (dialogWidth/2-DisplayConfig.convertDpToPixel(0.5f,getActivity().getApplicationContext()));
        bottomBtnsWidth =(int) (dialogWidth/3-DisplayConfig.convertDpToPixel(0.5f,getActivity().getApplicationContext()));

        int btnsHeight = btn_layout_height/7*3;

        LinearLayout.LayoutParams topBtnParams = new LinearLayout.LayoutParams(topBtnsWidth, btnsHeight);
        LinearLayout.LayoutParams bottomBtnParams = new LinearLayout.LayoutParams(bottomBtnsWidth,btnsHeight);

        topBtnParams.rightMargin = (int)DisplayConfig.convertDpToPixel(1,getActivity().getApplicationContext());
        star_add_btn.setLayoutParams(topBtnParams);
        call_log_btn.setLayoutParams(topBtnParams);
        bottomBtnParams.rightMargin = (int)DisplayConfig.convertDpToPixel(1,getActivity().getApplicationContext());
        call_btn.setLayoutParams(bottomBtnParams);
        text_message_btn.setLayoutParams(bottomBtnParams);
        video_call_btn.setLayoutParams(bottomBtnParams);
        setContents();
        v.setVisibility(View.GONE);
        return v;
    }
    private String mNumber;
    private String mName;
    private String mPeoplePhotoUri;
    private long mId;
    private int mStarred;
    private ImageLoadingListener animateFirstListener = new MainActivity.AnimateFirstDisplayListener();

    private void setContents(){
        mPeoplePhotoUri = contactsClass.friendPictureUrl;
        mNumber = contactsClass.friendNum;
        mId = contactsClass.friendId;
        mName = contactsClass.friendName;
        Log.e("Dialog", "mPeoplePhotoUri " + mPeoplePhotoUri);
        Log.e("Dialog", "mNumber " + mNumber);
        Log.e("Dialog", "mId " +mId );

        if (mId == 0) {
            if(contactsClass.cashed_photo_id != 0) {
                String id = fetchContactIdFromPhoneNumber(mNumber);
                if (id != null && !id.equals("")) {
                    mPeoplePhotoUri = getPhotoUri(Long.parseLong(id)).toString();
                }
            }
        }

        if(mPeoplePhotoUri != null && !mPeoplePhotoUri.equals("")) {


        }else{
            mPeoplePhotoUri = "drawable://"+R.drawable.ic_face_grey600_48dp;
        }
        ImageLoader.getInstance().displayImage(mPeoplePhotoUri, profile_photo_img, MainActivity.options, animateFirstListener);
        RelativeLayout.LayoutParams nameTvLayoutParams = (RelativeLayout.LayoutParams)new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, photo_layout_height/2 - photo_img_size/2);
        nameTvLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //nameTvLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        profile_name_tv.setLayoutParams(nameTvLayoutParams);
        profile_name_tv.setText(mName);
    }

    public Uri getPhotoUri(long contactId) {
        ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();
        Cursor cursor = null;
        try {
            cursor =
                    contentResolver.query(ContactsContract.Data.CONTENT_URI,null,ContactsContract.Data.CONTACT_ID+ "="+ contactId+ " AND "+ ContactsContract.Data.MIMETYPE+"='"+ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE+ "'", null, null);

            if (cursor != null) {
                if (!cursor.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        cursor.close();

        Uri person = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI, contactId);
        return person;
    }

    public String fetchContactIdFromPhoneNumber(String phoneNumber) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri,new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID },null, null, null);
        String contactId = "";

        if (cursor.moveToFirst()) {
            do {
                contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup._ID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactId;
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
                        ObjectAnimator.ofFloat(v, "scaleX", 1.0f,1.0f),
                        ObjectAnimator.ofFloat(v, "scaleY", listHeight/dialogHeight * 2, 1.0f),
                        ObjectAnimator.ofFloat(profile_photo_img, "scaleX", listHeight/dialogHeight * 2 * 10 / 9, 1.0f),
                        ObjectAnimator.ofFloat(v, "translationY", listY ,0),
                        alphaAni);

                set.setDuration(300);
                set.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        v.setVisibility(View.VISIBLE);

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

                        int topBtnsPadding = 0;
                        int bottomBtnsPadding = 0;
                        int btnsHeight = btn_layout_height/7*3;

                        if(topBtnsWidth> btnsHeight) {
                            topBtnsPadding = (int)( btnsHeight/2 - DisplayConfig.convertDpToPixel(18,getActivity().getApplicationContext()));
                        }else{
                            topBtnsPadding = (int)( topBtnsWidth/2 - DisplayConfig.convertDpToPixel(18,getActivity().getApplicationContext()));
                        }


                        star_add_btn.setPadding(topBtnsPadding, topBtnsPadding, topBtnsPadding, topBtnsPadding);
                        star_add_btn.setAdjustViewBounds(true);
                        star_add_btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        star_add_btn.setImageResource(R.drawable.ic_star_rate_white_48dp);

                        call_log_btn.setPadding(topBtnsPadding, topBtnsPadding, topBtnsPadding, topBtnsPadding);
                        call_log_btn.setAdjustViewBounds(true);
                        call_log_btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        call_log_btn.setImageResource(R.drawable.ic_star_rate_white_48dp);

                        if(bottomBtnsWidth > btnsHeight) {
                            bottomBtnsPadding = (int)( btnsHeight/2 - DisplayConfig.convertDpToPixel(18,getActivity().getApplicationContext()));
                        }else{
                            bottomBtnsPadding = (int)( bottomBtnsWidth/2 - DisplayConfig.convertDpToPixel(18,getActivity().getApplicationContext()));
                        }

                        call_btn.setPadding(bottomBtnsPadding, bottomBtnsPadding, bottomBtnsPadding, bottomBtnsPadding);
                        call_btn.setAdjustViewBounds(true);
                        call_btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        call_btn.setImageResource(R.drawable.ic_phone_white_48dp);

                        text_message_btn.setPadding(bottomBtnsPadding, bottomBtnsPadding, bottomBtnsPadding, bottomBtnsPadding);
                        text_message_btn.setAdjustViewBounds(true);
                        text_message_btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        text_message_btn.setImageResource(R.drawable.ic_messenger_white_48dp);

                        video_call_btn.setPadding(bottomBtnsPadding, bottomBtnsPadding, bottomBtnsPadding, bottomBtnsPadding);
                        video_call_btn.setAdjustViewBounds(true);
                        video_call_btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        video_call_btn.setImageResource(R.drawable.ic_messenger_white_48dp);

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
