package com.minilook.minilook.util;

import android.Manifest;
import android.content.Context;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

public class PermissionUtil {

    public static void checkStoragePermission(Context context, PermissionListener listener) {
        TedPermission.with(context)
            .setPermissionListener(listener)
            .setDeniedMessage("권한을 거부하면 서비스를 사용할 수 없습니다.")
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .check();
    }

    public static void checkCameraPermission(Context context, PermissionListener listener) {
        TedPermission.with(context)
            .setPermissionListener(listener)
            .setDeniedMessage("권한을 거부하면 서비스를 사용할 수 없습니다.")
            .setPermissions(Manifest.permission.CAMERA)
            .check();
    }
}
