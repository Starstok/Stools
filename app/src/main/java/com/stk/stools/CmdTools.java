package com.stk.stools;

import android.util.Log;

import java.io.DataOutputStream;

/**
 * Created by stk on 16/7/5.
 */
public class CmdTools {

    private static final String LOG_TAG = "STKTOOLS";
    public static int RunRootCmd(String paramString){
        try{

            Log.i(LOG_TAG,paramString);
            Process localProcess = Runtime.getRuntime().exec("su");
            DataOutputStream localDataOutputStream = new DataOutputStream(localProcess.getOutputStream());
            String str = paramString + "\n";
            localDataOutputStream.writeBytes(str);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            localProcess.waitFor();
            return localProcess.exitValue();
        }
        catch (Exception localExcption){
            localExcption.printStackTrace();
        }
        return 1;
    }




}
