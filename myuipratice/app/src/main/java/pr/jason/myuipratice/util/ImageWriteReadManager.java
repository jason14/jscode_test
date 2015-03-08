package pr.jason.myuipratice.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import pr.jason.myuipratice.MainActivity;

/**
 * Created by Jaesin on 2015-03-06.
 */
public class ImageWriteReadManager {
    Context mContext;
    private static final int PICK_FROM_CAMERA = 0; //카메라
    private static final int PICK_FROM_GALLERY = 1; //카메라
    private static final int AFTER_CROP = 2; //카메라
    private Activity activity;

    private Uri captureUri;
    private Uri cropUri;
    // private ImageView iv;
    //private View bt;
    private int name;
    private File tempFile;
    private boolean cameraFlag = false;
    private int mDisplayWidth;
    private int mDisplayHeight;
    private PreferenceManager preferenceManager;
    public ImageWriteReadManager(Activity activity, Context context){
        mContext = context;
        this.activity = activity;
        mDisplayWidth = (int)MainActivity.mDisWidth;
        mDisplayHeight = (int)MainActivity.mDisHeight;
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/division");
        if(!f.isDirectory()) f.mkdir();
        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.parse("file://"+Environment.getExternalStorageDirectory())));
        preferenceManager = new PreferenceManager(mContext);
        new AlertDialog.Builder(mContext).setIcon(0).setTitle("Choose Image From... ")
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getCameraPhoto();
                    }
                }).setNeutralButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getGalleryPhoto();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

    }
    //카메라에서 이미지 획득
    private void getCameraPhoto(){
        cameraFlag = true;
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        tempFile = new File(Environment.getExternalStorageDirectory(),"tempo1234.jpg");
        captureUri = Uri.fromFile(tempFile);
        cameraIntent.putExtra("output",captureUri);
        activity.startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
    }
    //갤러리에서 이미지 획득
    private void getGalleryPhoto(){
        cameraFlag = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(Environment.getExternalStorageDirectory()); //out is your output file
            mediaScanIntent.setData(contentUri);
            mContext.sendBroadcast(mediaScanIntent);
        } else {
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }


        Intent galleryIntent = new Intent("android.intent.action.PICK");
        galleryIntent.setType("vnd.android.cursor.dir/image");
        activity.startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
        Log.e("Image Crop","requestCode " + PICK_FROM_GALLERY);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        Log.e("Image Crop","requestCode " + requestCode);
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://"+Environment.getExternalStorageDirectory())));
        if(resultCode != activity.RESULT_OK){
            if(resultCode == activity.RESULT_CANCELED){
                if(cameraFlag){
                    if(tempFile.exists()){
                        tempFile.delete();
                    }
                }
                return;
            }

            switch(requestCode){
                case PICK_FROM_GALLERY:
                    captureUri = intent.getData();
                    crop();
                    break;
                case PICK_FROM_CAMERA:
                    crop();
                    break;
                case AFTER_CROP:
                    try{
                        Bitmap cropPhoto = BitmapFactory.decodeFile(cropUri.getPath());
                        /*iv.setImageBitmap(cropPhoto);
                        iv.setScaleType(ImageView.ScaleType.FIT_XY);*/
                    }catch(Exception e){
                        return;
                    }
                    if(cameraFlag){
                        if(tempFile.exists()) tempFile.delete();
                    }
                    ++name;
                    break;
            }
        }
    }

    public void crop(){
        if(cropUri == null) {
            Log.e("Image Crop", " NULL!!!!");
        }else{
            Log.e("Image Crop", " NOT NULL!!!!");
        }
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(captureUri,"image/*");
        cropIntent.putExtra("outputX", mDisplayWidth);
        cropIntent.putExtra("outputY", mDisplayHeight);
        cropIntent.putExtra("aspectX", mDisplayWidth);
        cropIntent.putExtra("aspectY", mDisplayHeight / mDisplayWidth);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("noFaceDetection", true);
        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

        cropUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/division","c"+System.currentTimeMillis()+".png"));
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT,cropUri);
        preferenceManager.put(SettingInfo.APP_BACKGROUND_IMAGE,cropUri.toString());
        activity.startActivityForResult(cropIntent, AFTER_CROP);
    }

    private View.OnClickListener load = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(mContext).setIcon(0).setTitle("Choose Image From... ")
                    .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getCameraPhoto();
                        }
                    }).setNeutralButton("Gallery", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    };
}
