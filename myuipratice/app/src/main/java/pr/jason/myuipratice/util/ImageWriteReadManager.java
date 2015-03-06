package pr.jason.myuipratice.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Jaesin on 2015-03-06.
 */
public class ImageWriteReadManager {
    Context mContext;
    private static final int PICK_FROM_CAMERA; //카메라
    private static final int PICK_FROM_GALLERY; //카메라
    private static final int AFTER_CROP; //카메라

    static{
        PICK_FROM_CAMERA = 0;
        PICK_FROM_GALLERY = 1;
        AFTER_CROP = 2;
    }
    private Uri captureUri;
    private Uri cropUri;
    private ImageView iv;
    private View bt;
    private int name;
    private File tempFile;
    private boolean cameraFlag = false;

    public ImageWriteReadManager(Context context, View view, ImageView iv){
        mContext = context;
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/division");
        if(!f.isDirectory()) f.mkdir();
        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse("file://"+Environment.getExternalStorageDirectory())));
        if(!initialFileRead()){
            name = 10000;
        }
        bt = view;

        bt.setOnClickListener(load);

    }
}
