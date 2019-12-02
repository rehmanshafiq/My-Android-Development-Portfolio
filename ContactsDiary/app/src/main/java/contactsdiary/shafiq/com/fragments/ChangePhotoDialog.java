package contactsdiary.shafiq.com.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

import contactsdiary.shafiq.com.R;
import contactsdiary.shafiq.com.utils.InitPermission;

public class ChangePhotoDialog extends DialogFragment {

    private static final String TAG = "ChangePhotoDialog";

    // widgets
    TextView mTakePhoto, mSelectPhoto, mCancelDialog;

    // listener
    public interface OnPhotoReceivedListener{
        public void getBitmapImage(Bitmap bitmap);
        public void getImagePath(String imagePath);
    }

    OnPhotoReceivedListener mOnPhotoReceived;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_chanephoto, container, false);

        // initialize the textview for starting camera
        mTakePhoto = view.findViewById(R.id.dialogTakePhoto);
        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starting camera.");

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, InitPermission.CAMERA_REQUEST_CODE);
            }
        });

        // initialize the textview for choosing photo from memory
        mSelectPhoto = view.findViewById(R.id.dialogChoosePhoto);
        mSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: accessing phone memory.");

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, InitPermission.PICKFILE_REQUEST_CODE);
            }
        });

        // initialize the textview for cancel button for closing the dialog
        mCancelDialog = view.findViewById(R.id.dialogCancel);
        mCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog.");
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mOnPhotoReceived = (OnPhotoReceivedListener) getTargetFragment();
        } catch (ClassCastException e) {
            Log.d(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         * Results when taking a new photo from camera
         */
        if (requestCode == InitPermission.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult: done taking a picture.");

            // get the new image bitmap
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Log.d(TAG, "onActivityResult: receive bitmap: " + bitmap);

            // send the bitmap and fragment to the interface
            mOnPhotoReceived.getBitmapImage(bitmap);
            getDialog().dismiss();
        }

        /**
         * Results when selecting new image from phone memory
         */
        if (requestCode == InitPermission.PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri selectedImageUri = data.getData();
            File file = new File(selectedImageUri.toString());
            Log.d(TAG, "onActivityResult: images: " + file.getPath());

            // send the bitmap and fragment to interface
            mOnPhotoReceived.getImagePath(file.getPath());
            getDialog().dismiss();
        }
    }
}
