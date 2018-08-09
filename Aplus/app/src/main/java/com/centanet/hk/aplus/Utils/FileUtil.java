package com.centanet.hk.aplus.Utils;

import com.centanet.hk.aplus.Utils.net.GsonUtil;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by yangzm4 on 2018/8/8.
 */

public class FileUtil {

    public static <T> T getData(Class<T> t, File file) {

        try {
            try {
                return GsonUtil.parseJson(new FileInputStream(file), t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFile(File file) {
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            inputStream = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            {
                if (reader != null) {
                    try {
                        inputStream.close();
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return content.toString();
    }

    public static void saveFile(String data, File file) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(data.getBytes());
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
