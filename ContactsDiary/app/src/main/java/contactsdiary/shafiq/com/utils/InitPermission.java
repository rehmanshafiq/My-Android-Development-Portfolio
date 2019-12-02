package contactsdiary.shafiq.com.utils;

import android.Manifest;

public class InitPermission {

    public InitPermission() {
    }

    public static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public static final String[] PHONE_PERMISSIONS = {Manifest.permission.CALL_PHONE};

    public static final int CAMERA_REQUEST_CODE = 5;

    public static final int PICKFILE_REQUEST_CODE = 8325;
}
