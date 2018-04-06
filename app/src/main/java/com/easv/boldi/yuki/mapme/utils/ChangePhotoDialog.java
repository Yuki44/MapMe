package com.easv.boldi.yuki.mapme.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.activities.FriendActivityEditNew;

import java.io.File;

public class ChangePhotoDialog extends DialogFragment {
    private static final String TAG = "ChangePhotoDialog";
    //    OnPhotoReceivedListener mOnPhotoReceived;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    private String mSelectedImagePath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_changephoto, container, false);

        //initalize the textview for starting the camera
        TextView takePhoto = view.findViewById(R.id.dialogTakePhoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starting camera.");
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        //Initialize the textview for choosing an image from memory
        TextView selectPhoto = view.findViewById(R.id.dialogChoosePhoto);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: accessing phones memory.");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Init.PICKFILE_REQUEST_CODE);
            }
        });

        // Cancel button for closing the dialog
        TextView cancelDialog = view.findViewById(R.id.dialogCancel);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog.");
                getDialog().dismiss();
            }
        });

        mSelectedImagePath = null;

        return view;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            mOnPhotoReceived = (OnPhotoReceivedListener) getActivity();
//        } catch (ClassCastException e) {
//            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        /*
//        REsults when taking a new image with camera
//         */
//        if (requestCode == Init.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            Log.d(TAG, "onActivityResult: done taking a picture.");
//
//            //get the new image bitmap
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            Log.d(TAG, "onActivityResult: receieved bitmap: " + bitmap);
//
//            //send the bitmap and fragment to the interface
//            mOnPhotoReceived.getBitmapImage(bitmap);
//            getDialog().dismiss();
//        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Log.d(TAG, "onActivityResult: received bitmap: " + imageBitmap);
            FriendActivityEditNew.mFriendImage.setImageBitmap(imageBitmap);
            getDialog().dismiss();
        }


        /*
        Results when selecting new image from phone memory
         */

        // When an Image is picked
        if (requestCode == Init.PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK
                && null != data) {
//                    // Get the Image from data
//                    try {
//
//                        Uri selectedImageUri = data.getData();
//            File file = new File(selectedImageUri.toString());
////
//
//                        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
//                    // Set the Image in ImageView after decoding the String
//                    EditFriendActivity.mFriendImage.setImageBitmap(bitmap);
//                        getDialog().dismiss();
//                    } catch (Exception e) {
//                        Log.d(TAG, "onActivityResult: Something wrong happened");
//                    }
//                } else {
//                    Log.d(TAG, "onActivityResult: Something wrong happened");
//                }


            Uri selectedImageUri = data.getData();
            File file = new File(selectedImageUri.toString());
            Log.d(TAG, "onActivityResult: images: " + file.getPath());

            //send the bitmap and fragment to the interface
            getImagePath(file.getPath());
            getDialog().dismiss();
        }

//    public interface OnPhotoReceivedListener {
//        void getBitmapImage(Bitmap bitmap);
//
//        void getImagePath(String imagePath);
//    }
    }


    public void getImagePath(String imagePath) {
        Log.d(TAG, "getImagePath: got the imagePath" + imagePath);
        if (!imagePath.equals("")) {
            imagePath = imagePath.replace(":/", "://");
            mSelectedImagePath = imagePath;
            FriendActivityEditNew.mSelectedImagePath = imagePath;
            UniversalImageLoader.setImage(imagePath, FriendActivityEditNew.mFriendImage, null, "");
        }
}

}