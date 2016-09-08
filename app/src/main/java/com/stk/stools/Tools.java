package com.stk.stools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by stk on 16/7/5.
 */
public class Tools extends Activity{

    private Button rebt,rerec,fmiui,ftw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tools);

        rebt=(Button)findViewById(R.id.rebt);
        rerec=(Button)findViewById(R.id.rerec);
        fmiui=(Button)findViewById(R.id.fmiui);
        ftw=(Button)findViewById(R.id.ftw);

        rebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Shell.SU.run("reboot");

            }
        });
        rerec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shell.SU.run("reboot recovery");
            }
        });

    }
    public void FlashMiuiRecovery(View view){
        String miui = "miui_recovery.img";
        FileUtils.copyAssetFileToFile(this.getApplication(), miui);
        CmdTools.RunRootCmd("dd if=/data/data/com.stk.stools/files/miui_recovery.img of=/dev/block/platform/msm_sdcc.1/by-name/recovery");
        Toast.makeText(Tools.this, "刷入Miui Recovery成功", Toast.LENGTH_LONG).show();
        FileUtils.deleteFile(new File(this.getApplicationContext().getFilesDir().getAbsolutePath()));
    }

    public void FlashTwRecovery(View view){
        String tw = "tw_recovery.img";
        FileUtils.copyAssetFileToFile(this.getApplication(), tw);
        CmdTools.RunRootCmd("dd if=/data/data/com.stk.stools/files/tw_recovery.img of=/dev/block/platform/msm_sdcc.1/by-name/recovery");
        Toast.makeText(Tools.this, "刷入Tw Recovery成功", Toast.LENGTH_LONG).show();
        FileUtils.deleteFile(new File(this.getApplicationContext().getFilesDir().getAbsolutePath()));
    }
}
