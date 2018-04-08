package com.easv.boldi.yuki.mapme.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.activities.FriendActivityEditNew;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangePhotoDialog extends DialogFragment {
    private static final String TAG = "ChangePhotoDialog";
    private String mSelectedImagePath;
    private static final int CAMERA_REQUEST_CODE = 10;
    String mCurrentPhotoPath;

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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, CAMERA_REQUEST_CODE);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult: done taking a picture." + "  <><><><><><><><><><><><><><><><><><><><>");
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Log.d(TAG, "onActivityResult: receieved bitmap: " + bitmap);
            if (bitmap != null) {
                Bitmap compressedBitmap = compressBitmap(bitmap, 70);
                FriendActivityEditNew.mFriendImage.setImageBitmap(compressedBitmap);
                getDialog().dismiss();
            } else {
                Toast.makeText(this.getContext(), "Something went wrong...", Toast.LENGTH_LONG).show();

            }
        }

        // When an Image is picked
        if (requestCode == Init.PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            File file = new File(selectedImageUri.toString());
            Log.d(TAG, "onActivityResult: images: " + file.getPath() + " " + mSelectedImagePath);
            getImagePath(file.getPath());
            getDialog().dismiss();
        }
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

    public Bitmap compressBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return bitmap;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}