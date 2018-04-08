package com.easv.boldi.yuki.mapme.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easv.boldi.yuki.mapme.R;
import com.easv.boldi.yuki.mapme.activities.FriendActivityEditNew;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ChangePhotoDialog extends DialogFragment {
    private static final String TAG = "ChangePhotoDialog";
    private String mSelectedImagePath;
    private static final int CAMERA_REQUEST_CODE = 10;
    String mCurrentPhotoPath;
    private static final int IMAGE_REQUEST_CODE = 1;
    private static final String CAPTURE_IMAGE_FILE_PROVIDER = "com.easv.boldi.yuki.mapme.fileprovider";

    public static Bitmap rotateImage(Bitmap src, float degree) {
        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }

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
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAMERA_REQUEST_CODE);

                File path = new File(getActivity().getFilesDir(), "your/path");
                if (!path.exists()) path.mkdirs();
                File image = new File(path, "image.jpg");
                Uri imageUri = FileProvider.getUriForFile(getActivity(), CAPTURE_IMAGE_FILE_PROVIDER, image);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);

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

        if (requestCode == IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                File path = new File(getActivity().getFilesDir(), "your/path");
                if (!path.exists()) path.mkdirs();
                File imageFile = new File(path, "image.jpg");
                Log.d(TAG, "onActivityResult: imageFile: " + imageFile);
                // use imageFile to open your image
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(imageFile), options);
                Bitmap finalBitmap = rotateImage(bitmap, 90);
                Uri finalPath = getImageUri(finalBitmap);
                Log.d(TAG, "onActivityResult: finalPath " + finalPath);
                getBitmapImage(String.valueOf(finalPath));
                getDialog().dismiss();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
// D: onActivityResult: image: /data/user/0/com.easv.boldi.yuki.mapme/files/your/path/image.jpg <><><><><><><><><><><><><><><><><><><><>
//D: onActivityResult: bitmap: android.graphics.Bitmap@5d21825 <><><><><><><><><><><><><><><><><><><><>

//        D: onActivityResult: images: content:/com.android.providers.media.documents/document/image%3A18062 null
//        getImagePath: got the imagePathcontent:/com.android.providers.media.documents/document/image%3A18062

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

    public void getBitmapImage(String imagePath) {
        Log.d(TAG, "getImagePath: got the imagePath  " + imagePath);
        if (!imagePath.equals("")) {
            mSelectedImagePath = imagePath;
            FriendActivityEditNew.mSelectedImagePath = imagePath;
            UniversalImageLoader.setImage(imagePath, FriendActivityEditNew.mFriendImage, null, "");
        }
    }

    private Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}