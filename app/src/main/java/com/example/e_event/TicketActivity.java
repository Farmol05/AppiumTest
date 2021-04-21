package com.example.e_event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class TicketActivity extends AppCompatActivity {
    private static final String DIRECTORY_DOWNLOADS = "a";
    private int eventID;
    private User user;
    private Event event;
    private DataUser db_user;
    private DataEvent db_event;

    private String title, email;

    ImageView ivE, ivB;
    byte[] e_bytes;
    Bitmap poster, ss;
    Button bt, btn_ss;
    TextView tvTitle, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(TicketActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        eventID = intent.getIntExtra("id",0);
        db_user = new DataUser(this);
        user = db_user.getLoggedInUser();
        db_event = new DataEvent(this);
        event = db_event.getOneEvent(eventID);

        init();
        fill();

    }

    void init(){
        ivE = findViewById(R.id.ticket_iv_event);
        ivB = findViewById(R.id.iv_barcode);
        tvEmail = findViewById(R.id.ticket_userEmail);
        tvTitle = findViewById(R.id.ticket_eventName);
        bt = findViewById(R.id.tick_btn);
        btn_ss = findViewById(R.id.btn_ss);
    }

    void fill(){
        title = event.getTitle();
        email = user.getEmail();
        e_bytes = event.getPoster();
        poster = BitmapFactory.decodeByteArray(e_bytes, 0, e_bytes.length);
//        poster = Bitmap.createScaledBitmap(poster, 70, 100, true);

        tvTitle.setText(title);
        tvEmail.setText(email);
//        ivE.setImageBitmap(poster);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHome();
            }
        });

        btn_ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
            btn_ss.setVisibility(View.INVISIBLE);
            ss = getScreenShot(rootView);
            String filename = "ticket-"+event.getId()+".png";
            store(ss, filename);
            startActivity(new Intent(TicketActivity.this, EventActivity.class));
            }
        });

    }

    void backToHome(){
        Intent i = new Intent(this, EventActivity.class);
        startActivity(i);
    }


    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;

    }
    public void store(Bitmap bm, String fileName){
//        String /**/dirPath ="/storage/emulated/0/";
        String dirPath = Environment.getExternalStorageDirectory().toString()+ "/Download/";
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            Toast.makeText(TicketActivity.this, "Tesimpan di Download/"+fileName, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}