package com.stk.stools.Utils;

import android.util.Log;

import java.io.DataOutputStream;

/**
 * Created by builder on 16-5-28.
 */
public class RootCmd {

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
