package com.alct.mdpsdksample.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.alct.mdpsdksample.R;

import permissions.dispatcher.PermissionUtils;

public class BootstrapActivity extends Activity {
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootstrap);

        requestPermissions();
    }

    private void jumpToStartActivity() {
        Intent intent = new Intent(BootstrapActivity.this, RegisterActivity.class);
        BootstrapActivity.this.startActivity(intent);
        finish();
    }

    private void requestPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            String[] permissions = new String[3];
            permissions[0] = Manifest.permission.READ_PHONE_STATE;
            permissions[1] = Manifest.permission.ACCESS_FINE_LOCATION;
            permissions[2] = Manifest.permission.NFC;

            if (!PermissionUtils.hasSelfPermissions(
                    this,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.NFC
            )) {
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            } else {
                jumpToStartActivity();
            }
        } else {
            jumpToStartActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        jumpToStartActivity();
    }
}
