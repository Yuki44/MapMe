package com.easv.boldi.yuki.mapme.utils;

import android.Manifest;

public class Init {

    public static final int CAMERA_REQUEST_CODE = 10;

    public static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    public static final String[] PHONE_PERMISSIONS = {Manifest.permission.CALL_PHONE};

    public Init() {

    }

    public static final int PICKFILE_REQUEST_CODE = 8352;


}

