package com.stk.stools.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by builder on 16-5-28.
 */
public class HttpTools {

    public static String getcontent(String path)throws Exception{

        //自动生成的方法存根
        URL url = new URL(path);
        HttpURLConnection coon = (HttpURLConnection)url.openConnection();
        coon.setConnectTimeout(5000);
        coon.setRequestMethod("GET");
        if(coon.getResponseCode() == 200){

            InputStream inputStream = coon.getInputStream();
            return HttpTools.getstring(inputStream);
        }
        return null;
    }

    public static String getstring(InputStream inputStream) throws Exception{

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String result = "";
        String line = "";
        while ((line = reader.readLine()) !=null){

            result = result + line;
        }
        inputStream.close();

        return result;
    }
}
