package com.layse.restful;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {

    private EditText txt;
    private Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.editTxt);

        Thread t = new Thread(new MyServerthread());
        t.start();

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MessageSender().execute(txt.getText().toString());

            }
        });

    }

    class MyServerthread implements Runnable{

        Socket s;
        ServerSocket ss;
        InputStreamReader isr;
        BufferedReader bufferedReader;
        Handler h = new Handler();
        String msg;

        @Override
        public void run() {

            try {

                ss = new ServerSocket(7801);
                while(true){

                    s = ss.accept();
                    isr = new InputStreamReader(s.getInputStream());
                    bufferedReader = new BufferedReader(isr);
                    msg = bufferedReader.readLine();

                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            } catch (Exception e){
                e.printStackTrace();
            }

        }

    }

}
