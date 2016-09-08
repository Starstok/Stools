package com.stk.stools;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by stk on 16/7/5.
 */
public class InstallXp extends Activity {

    private static final String TAG = "xposed installer";
    String CPU_ABI;
    private ProgressDialog m_ProgressDialog = null;
    private Runnable viewOrders;
    private String xposed_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.installxp);
        try
        {
            this.CPU_ABI = DeviceUtils.getDeviceInfo().getRoProductCpuAbi();
        }
        catch (IOException e)
        {}
        this.xposed_type = getfolder();

    }

    private void doinstall(String string2) {
        try {
            if (!string2.equals((Object)"")) {
                FileUtils.copyAssetDirToFiles(this.getApplicationContext(), string2);
                String[] arrstring = new String[]{"mount -o remount,rw /system", "cd /data/data/com.stk.stools/files/" + string2, "sh install-script.sh", "mount -o remount,ro /system"};
                Shell.SU.run(arrstring);
                FileUtils.deleteFile(new File(this.getApplicationContext().getFilesDir().getAbsolutePath()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.runOnUiThread(new Runnable(){

            public void run() {
                InstallXp.this.m_ProgressDialog.dismiss();
                Toast.makeText((Context)InstallXp.this.getApplicationContext(), getResources().getString(R.string.done), 1).show();
                Shell.SU.run("reboot");
            }
        });
    }

    public void doInstallXposed(View view) {
        try {
            this.viewOrders = new Runnable(){

                public void run() {
                    InstallXp.this.doinstall(InstallXp.this.xposed_type);
                }
            };
            new Thread(null, this.viewOrders, "Background").start();
            this.m_ProgressDialog = ProgressDialog.show((Context)this, (CharSequence)this.getResources().getString(R.string.install), (CharSequence)(this.getResources().getString(R.string.install) + this.xposed_type), (boolean)true);
            return;
        }
        catch (Exception e) {
            return;
        }
    }

    private void douninstall(String string2) {
        try {
            if (!string2.equals((Object)"")) {
                FileUtils.copyAssetDirToFiles(this.getApplicationContext(), string2);
                String[] arrstring = new String[]{"mount -o remount,rw /system", "cd /data/data/com.stk.stools/files/" + string2, "sh uninstall-script.sh", "mount -o remount,ro /system"};
                Shell.SU.run(arrstring);
                FileUtils.deleteFile(new File(this.getApplicationContext().getFilesDir().getAbsolutePath()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.runOnUiThread(new Runnable(){

            public void run() {
                InstallXp.this.m_ProgressDialog.dismiss();
                Toast.makeText((Context)InstallXp.this.getApplicationContext(), getResources().getString(R.string.done), 1).show();
                Shell.SU.run("reboot");
            }
        });
    }

    public void doUnInstallXposed(View view) {
        try {
            this.viewOrders = new Runnable(){

                public void run() {
                    InstallXp.this.douninstall(InstallXp.this.xposed_type);
                }
            };
            new Thread(null, this.viewOrders, "Background").start();
            this.m_ProgressDialog = ProgressDialog.show((Context)this, (CharSequence)this.getResources().getString(R.string.uninstall), (CharSequence)(this.getResources().getString(R.string.uninstall) + this.xposed_type), (boolean)true);
            return;
        }
        catch (Exception e) {
            return;
        }
    }


    private String getfolder() {
        if (this.CPU_ABI.equals((Object)"arm64-v8a")) {
            if (Integer.parseInt((String)Build.VERSION.SDK) == 23) {
                return "xposed-v86-sdk23-arm64";
            }
            if (Integer.parseInt((String)Build.VERSION.SDK) == 22) {
                return "xposed-v86-sdk22-arm64";
            }
            if (Integer.parseInt((String)Build.VERSION.SDK) == 21) {
                return "xposed-v86-sdk21-arm64";
            }
        } else if (this.CPU_ABI.equals((Object)"armeabi-v7a")) {
            if (Integer.parseInt((String)Build.VERSION.SDK) == 23) {
                return "xposed-v86-sdk23-arm";
            }
            if (Integer.parseInt((String)Build.VERSION.SDK) == 22) {
                return "xposed-v86-sdk22-arm";
            }
            if (Integer.parseInt((String)Build.VERSION.SDK) == 21) {
                return "xposed-v86-sdk21-arm";
            }
        } else if (this.CPU_ABI.equals((Object)"x86")) {
            if (Integer.parseInt((String)Build.VERSION.SDK) == 23) {
                return "xposed-v86-sdk23-x86";
            }
            if (Integer.parseInt((String)Build.VERSION.SDK) == 22) {
                return "xposed-v86-sdk22-x86";
            }
            if (Integer.parseInt((String)Build.VERSION.SDK) == 21) {
                return "xposed-v86-sdk21-x86";
            }
        }
        return "";
    }

    public static boolean isAppInstalled(Context context, String s) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(s, 1);
            return true;

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    public void doInstallXpApp(View view) {
        String xp = "xp.apk";
        try {
            FileUtils.copyAssetFileToFile(this.getApplicationContext(), xp);
            Runtime.getRuntime().exec("chmod 777 " + (Object)this.getApplicationContext().getFilesDir() + "/" + xp);
            Intent intent = new Intent();
            intent.addFlags(1);
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse((String) ("file://" + (Object)this.getApplicationContext().getFilesDir() + "/" + xp)), "application/vnd.android.package-archive");
            this.startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void doUnInstallXpApp(View view){
        Uri uri = Uri.parse((String)"package:de.robv.android.xposed.installer");
        Intent intent = new Intent();
        intent.setAction("android.intent.action.UNINSTALL_PACKAGE");
        intent.setData(uri);
        this.startActivityForResult(intent,0);
    }

    private boolean hasXposed() {
        return FileUtils.fileExists("/system/framework/XposedBridge.jar");
    }

    /*private boolean hasSu() {

        return FileUtils.fileExists("/system/xbin/su");
    }*/

    public void onStart(){
        super.onStart();

        Log.v((String)"Xposed installer",(String)"hangling onStart");

        TextView textView = (TextView)this.findViewById(R.id.modelID);
        TextView textView2 = (TextView)this.findViewById(R.id.deviceID);
        TextView textView3 = (TextView)this.findViewById(R.id.systemID);
        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.MainStatusLayout);
        try {
            textView.setText((CharSequence)(Build.BRAND + " " + Build.MODEL));
            textView2.setText((CharSequence)Build.DEVICE);
            textView3.setText((CharSequence)("Android " + Build.VERSION.RELEASE + "(" + this.CPU_ABI + ")"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        TextView tvapkstatus = (TextView)findViewById(R.id.tvapkstatus);

            if (InstallXp.isAppInstalled((Context)this,"de.robv.android.xposed.installer")){
                tvapkstatus.setText(getResources().getString(R.string.apk_status) + getResources().getString(R.string.installed));
            }
            else {
                tvapkstatus.setText(getResources().getString(R.string.apk_status) + getResources().getString(R.string.uninstalled));
            }
        if (Integer.parseInt((String)Build.VERSION.SDK) < 21) {
            linearLayout.setVisibility(8);
            return;
        }
        linearLayout.setVisibility(0);
        if (!Shell.SU.available()) {
            this.findViewById(R.id.installframeworkButton).setEnabled(false);
            this.findViewById(R.id.uninstallframeworkButton).setEnabled(false);
            return;
        }

        TextView xpstatus = (TextView)findViewById(R.id.xpstatus);
        if (this.hasXposed()){
            xpstatus.setText(getResources().getString(R.string.xposed_status) + getResources().getString(R.string.installed));
        }
        else {
            xpstatus.setText(getResources().getString(R.string.xposed_status) + getString(R.string.uninstalled));
        }
    }




}