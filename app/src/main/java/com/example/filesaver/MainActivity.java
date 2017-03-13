package com.example.filesaver;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewMessage;
    private EditText mMessage;
    private Button mButtonSave;
    private Button mButtonRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessage = (EditText)findViewById(R.id.editTextMessage);
        mButtonSave = (Button)findViewById(R.id.buttonWrite);
        mButtonRead = (Button)findViewById(R.id.buttonRead);
        mTextViewMessage = (TextView)findViewById(R.id.textViewMessage) ;

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeExternalStorage();
            }
        });

        mButtonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readExternalStorage();
            }
        });
    }


    private void writeExternalStorage(){

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            File root = Environment.getExternalStorageDirectory();
            File Dir = new File(root.getAbsolutePath() + "/MyAppFile");
            if (!Dir.exists()){
                Dir.mkdir();
            }
            File file = new File(Dir, "Mymessage.txt");
            String message = mMessage.getText().toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                mMessage.setText("");
                Toast.makeText(this, "Message Saved!", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this, "SDCARD Not Found!", Toast.LENGTH_SHORT).show();
        }

    }

    private void readExternalStorage(){
    File root = Environment.getExternalStorageDirectory();
    File Dir = new File(root.getAbsolutePath() + "/MyAppFile");
    File file = new File(Dir, "Mymessage.txt");
    String message;

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            InputStreamReader inputStream = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStream);
            StringBuffer stringBuffer = new StringBuffer();

            while((message=bufferedReader.readLine())!= null){
                stringBuffer.append(message + "/n");
            }
            mTextViewMessage.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
