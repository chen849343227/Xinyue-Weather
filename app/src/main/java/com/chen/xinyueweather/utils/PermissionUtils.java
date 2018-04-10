package com.chen.xinyueweather.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.tbruyelle.rxpermissions.RxPermissions;

/**
 * author long
 * date 17-11-3
 * desc
 */

public class PermissionUtils {

    private static final String TAG = "PermissionUtils";

    private static RxPermissions mRxPermissions;

    private PermissionUtils() {

    }

    public static void requestPermission(Activity activity) {
        if (activity == null) {
            return;
        }
        if (mRxPermissions == null) {
            mRxPermissions = new RxPermissions(activity);
        } else {
            mRxPermissions
                    .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe(permission -> {
                        if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            if (permission.granted) {
                                // 用户已经同意该权限
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框

                            } else {
                                // 用户拒绝了该权限，并且选中『不再询问』
                                Log.d(TAG, permission.name + " is denied.");
                            }
                        } else if (permission.name.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            if (permission.granted) {
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框

                            } else {
                                // 用户拒绝了该权限，并且选中『不再询问』
                                Log.d(TAG, permission.name + " is denied.");
                            }
                        } else if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            if (permission.granted) {

                            } else if (permission.shouldShowRequestPermissionRationale) {
                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框

                            } else {
                                // 用户拒绝了该权限，并且选中『不再询问』
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                builder.setTitle("设置")
                                        .setMessage("即将前往设置去打开位置设置")
                                        .setPositiveButton("设置", (dialog, which) -> {
                                            Intent intent = new Intent();
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                            intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                                            activity.startActivity(intent);
                                        })
                                        .setNegativeButton("取消", (dialog, which) -> {

                                        });
                                builder.setCancelable(true);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
        }
    }
}
