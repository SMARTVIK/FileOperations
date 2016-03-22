package com.craterzone.fileexperiments;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by VIK on 22-03-2016.
 */
public class WriteToFileThread extends Thread {
    private static final String TAG = WriteToFileThread.class.getSimpleName();
    private static final String SLASH = "/";
    private Context context;
    private Handler handler;

    public WriteToFileThread(Context context,Handler handler) {
        this.context = context;
        this.handler=handler;
    }

    @Override
    public void run() {
        super.run();
        readFromFile();
    }


    private void readFromFile() {
        File folder = new File(Environment.getExternalStorageDirectory(),context.getString(R.string.ac_details));
        File file = new File(folder,context.getString(R.string.file_name));
//        File readingFile = new File(Environment.getExternalStorageDirectory() + SLASH + context.getString(R.string.test_folder) + SLASH + context.getString(R.string.file_name));
        try {
//            if (!readingFile.exists()) {
//                readingFile.createNewFile();
//            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            int lineNumber = 1;
            while (line != null) {
                stringBuilder.append(line);
                if (lineNumber == 1) {
                    Log.d(TAG, "line number " + lineNumber + " content " + line);
                } else if (lineNumber == 2) {
                    Log.d(TAG, "line number " + lineNumber + " content " + line);
                } else if (lineNumber == 3) {
                    Log.d(TAG, "line number " + lineNumber + " content " + line);
                }
                line = bufferedReader.readLine();
                lineNumber++;
            }
            Thread.sleep(5000);
            Log.d(TAG, stringBuilder.toString());
            handler.sendEmptyMessage(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
