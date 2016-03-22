package com.craterzone.fileexperiments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileOperations extends AppCompatActivity {

    private static final String SPACE = " ";
    private static final String NEXT_LINE = "\n";
    private static final String SLASH = "/";
    private static final String TAG = FileOperations.class.getSimpleName();
    private EditText firstName;
    private EditText lastName;
    private Button showFromFile;
    private TextView mainText;
    private Button saveToFile;
    private Button showFromDifferentThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_operations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews() {
        firstName= (EditText) findViewById(R.id.first_name);
        lastName= (EditText) findViewById(R.id.last_name);
        saveToFile = (Button) findViewById(R.id.save_to_file);
        saveToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeAndStoreInFile();
            }
        });
        showFromDifferentThread = (Button) findViewById(R.id.show_data_from_file_from_different_thread);
        showFromDifferentThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = ProgressDialog.show(FileOperations.this,"wait","loading...");
                progressBar.show();
                new WriteToFileThread(FileOperations.this,handler).start();
            }
        });
        showFromFile= (Button) findViewById(R.id.show_from_file);
        showFromFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromFile();
            }
        });
        View clearDataFromFile = findViewById(R.id.clear_data_from_file);
        clearDataFromFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File readingFile = new File(Environment.getExternalStorageDirectory()+SLASH+getString(R.string.test_folder)+SLASH+getString(R.string.file_name));
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(readingFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                pw.close();
            }
        });
        mainText= (TextView) findViewById(R.id.main_text);
    }

    private void readFromFile() {
        File readingFile = new File(Environment.getExternalStorageDirectory()+SLASH+getString(R.string.test_folder)+SLASH+getString(R.string.file_name));
        try {
            if (!readingFile.exists()){
                readingFile.createNewFile();
            }
            FileReader fileReader = new FileReader(readingFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            int lineNumber = 1;
            while (line != null){
                stringBuilder.append(line);
                if (lineNumber == 1){
                    Log.d(TAG,"line number "+lineNumber+" content "+line);
                }else if (lineNumber== 2){
                    Log.d(TAG,"line number "+lineNumber+" content "+line);
                }else if (lineNumber == 3){
                    Log.d(TAG,"line number "+lineNumber+" content "+line);
                }
                line = bufferedReader.readLine();
                lineNumber++;
            }
            mainText.setText(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAndStoreInFile() {
        File file = new File(Environment.getExternalStorageDirectory(),getString(R.string.test_folder));
        if (!file.exists()){
            file.mkdir();
        }
        File newTextFile = new File(file,getString(R.string.file_name));
        try {
            if (!newTextFile.exists()){
                newTextFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(newTextFile,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(firstName.getText().toString() + SPACE + lastName.getText().toString() + NEXT_LINE);
            bufferedWriter.close();
            firstName.setText("");
            lastName.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_operations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ProgressDialog progressBar;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.dismiss();
        }
    };
}
