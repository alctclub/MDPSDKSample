package com.alct.mdpsdksample.ui;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.alct.mdpsdksample.constant.BaseConstant;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    private static final String NFC_DATA_TYPE = "*/*";

    private PendingIntent nfcIntent;
    private IntentFilter[] nfcFilters;
    private String[][] nfcTechLists;
    private NfcAdapter nfcAdapter;

    protected boolean hasNfcAdapter() {
        return nfcAdapter != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        nfcIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(
                NfcAdapter.ACTION_NDEF_DISCOVERED);

        try {
            ndef.addDataType(NFC_DATA_TYPE);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            Log.e(BaseConstant.TAG, e.getMessage());
        }

        nfcFilters = new IntentFilter[]{ndef};
        nfcTechLists = new String[][]{
                new String[]{MifareClassic.class.getName()},
                new String[]{NfcA.class.getName()}
        };
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter == null) {
            return;
        }

        nfcAdapter.enableForegroundDispatch(this, nfcIntent, nfcFilters, nfcTechLists);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter == null) return;
        nfcAdapter.disableForegroundDispatch(this);
    }
}