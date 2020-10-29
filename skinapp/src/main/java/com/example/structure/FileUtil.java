package com.example.structure;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by hongqian.better@outlook.com
 * on 2020/10/28
 */
public class FileUtil {

    public static void copy(Context mContext) {
        try {
            InputStream open = mContext.getAssets().open("skinapk-debug.apk");
//            File storageDirectory = Environment.getStorageDirectory();

            String path = Environment.getExternalStorageDirectory()+ "/skinapk-debug.apk";
            File file = new File(path);
            file.setWritable(true);
            file.setReadable(true);
            if(file.exists()) {
                file.delete();
            }
           file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(path);

            byte[] arr = new byte[1024];
            int len=0;
            while ((len=open.read(arr)) != -1) {
                outputStream.write(arr,0,len);
            }

            outputStream.flush();
            open.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
