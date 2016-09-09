package com.stk.stools.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stk.stools.R;
import com.stk.stools.Utils.RootCmd;

/**
 * Created by starstok on 16/5/25.
 */
public class Tools extends Activity {

    private Button rebt,recbt,fcwm,ftw,iplog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tools);

        rebt=(Button)findViewById(R.id.rebt);
        recbt=(Button)findViewById(R.id.recbt);
        fcwm=(Button)findViewById(R.id.fcwm);
        ftw=(Button)findViewById(R.id.ftw);
        iplog=(Button)findViewById(R.id.iplog);

        rebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RootCmd.RunRootCmd("reboot");

            }
        });
        recbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RootCmd.RunRootCmd("reboot recovery");
            }
        });
        fcwm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RootCmd.RunRootCmd("dd if=/sdcard/Android/data/com.stk.stools/files/cwm_recovery.img of=/dev/block/platform/msm_sdcc.1/by-name/recovery");
                Toast.makeText(Tools.this, "刷入Cwm Recovery成功",Toast.LENGTH_LONG).show();
            }
        });
        ftw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RootCmd.RunRootCmd("dd if=/sdcard/Android/data/com.stk.stools/files/tw_recovery.img of=/dev/block/platform/msm_sdcc.1/by-name/recovery");
                Toast.makeText(Tools.this, "刷入TW Recovery成功", Toast.LENGTH_LONG).show();
            }
        });
        iplog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mkdir /sdcard/Stools
                RootCmd.RunRootCmd("logcat *:sdcard/Stools >log.txt");
                Toast.makeText(Tools.this, "抓取成功", Toast.LENGTH_LONG).show();
            }
        });
    }
}