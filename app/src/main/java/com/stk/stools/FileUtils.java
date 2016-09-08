package com.stk.stools;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stk on 16/7/19.
 */
public class FileUtils {

    public static void copyAssetDirToFiles(Context context , String string) throws IOException {
        new File((Object)context.getFilesDir() + "/" + string).mkdir();
        AssetManager assetManager = context.getAssets();
        String[] arrstring = assetManager.list(string);
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String string2 = arrstring[n2];
            String string3 = string + '/' + string2;
            if (assetManager.list(string3).length == 0){
                FileUtils.copyAssetFileToFile(context , string3);
            } else {
                FileUtils.copyAssetDirToFiles(context , string3);
            }
            ++n2;
        }
    }

    public static void copyAssetFileToFile(Context context , String string){
        try {
            InputStream inputStream = context.getAssets().open(string);
            byte[] arrby = new byte[inputStream.available()];
            inputStream.read(arrby);
            inputStream.close();
            File file = new File((Object)context.getFilesDir() + "/" + string);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(arrby);
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(File file) {
        if (!file.exists()) {
            Log.i((String) "stk", (String) "stk");
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] arrfile = file.listFiles();
            for (int i = 0; i < arrfile.length; ++i) {
                FileUtils.deleteFile(arrfile[i]);
            }
        }
        file.delete();
    }

    public static void doCopyFile(String string1, String string2) throws IOException {
        File file = new File(string1);
        File file2 = new File(string2);
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        try {
            byte[] s = new byte[4096];
            int t;
            while (-1 != (t = fileInputStream.read(s))) {
                fileOutputStream.write(s, 0, t);
            }

        }
        catch (Throwable e) {
            if (fileOutputStream == null)

                try {
                    fileOutputStream.close();
                    throw e;
                }
                catch (Throwable e1) {
                    if (fileInputStream == null)
                        try {
                            fileInputStream.close();
                        }
                        catch (IOException e2) {
                            throw e2;
                        }



                    if (fileInputStream == null) return;
                    try {
                        fileInputStream.close();
                        return;
                    }
                    catch (IOException e3) {
                        return;
                    }

                }

            if (fileOutputStream != null) {
                fileOutputStream.close();
            }

        }
    }
    public static boolean fileExists(String string2) {
        return new File(string2).exists();
    }
    public static List<String> readFile(File file) throws IOException {
        String string2;
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader((Reader)fileReader);
        ArrayList arrayList = new ArrayList();
        while ((string2 = bufferedReader.readLine()) != null) {
            arrayList.add((Object)string2);
        }
        bufferedReader.close();
        fileReader.close();
        return arrayList;
    }

    public static List<String> readFile(String string2) throws IOException {
        return FileUtils.readFile(new File(string2));
    }
}
