package com.example.e_event;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetailActivity extends AppCompatActivity {

    TextView tv_title, tv_org, tv_desc, tv_date, tv_tick;
    Button btn_book;
    ImageView iv_poster;

    private int eventID;
    private User user;
    private Event event;
    private DataUser db_user;
    private DataEvent db_event;

    private String e_title, e_org, e_date, e_desc;
    private int e_ticket, e_active, e_ownerID;
    private Bitmap e_poster;
    private byte[] e_bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Intent intent = getIntent();
        eventID = intent.getIntExtra("eventID",0);
        db_user = new DataUser(this);
        user = db_user.getLoggedInUser();
        db_event = new DataEvent(this);
        event = db_event.getOneEvent(eventID);
        Log.d("Etitle", event.getTitle());

        init();
        fill();
        getSupportActionBar().setTitle(e_title);

    }

    void init(){
        tv_title = findViewById(R.id.det_tv_tit);
        tv_org = findViewById(R.id.det_tv_org);
        tv_date = findViewById(R.id.det_tv_date);
        tv_tick = findViewById(R.id.det_tv_tick);
        tv_desc = findViewById(R.id.det_tv_desc);
        iv_poster = findViewById(R.id.det_img);
        btn_book = findViewById(R.id.det_btn_submit);

    }

    void fill(){
        e_title = event.getTitle();
        e_org = event.getOrganizer();
        e_date = event.getDate_started();
        e_ticket = event.getTicket();
        if(e_ticket < 1){
            btn_book.setEnabled(false);
        }
        e_desc = event.getDesc();
        e_active = event.getActive();
        e_ownerID = event.getOwnerID();
        e_bytes = event.getPoster();
        e_poster = BitmapFactory.decodeByteArray(e_bytes, 0, e_bytes.length);

        tv_title.setText(e_title);
        tv_org.setText("Penyelenggara : "+e_org);
        tv_date.setText(e_date);
        tv_tick.setText(e_ticket +" Tiket tersedia");
        tv_desc.setText(e_desc);
        iv_poster.setImageBitmap(e_poster);
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = askOption();
                alert.show();
            }
        });


    }

    public void submit(){
        int current_ticket = event.getTicket()-1;
        db_event.updateOne(eventID,e_title,e_org,e_date,current_ticket, e_desc, e_bytes ,e_active,e_ownerID);
        Intent i = new Intent(this, TicketActivity.class);
        i.putExtra("id", eventID);
        startActivity(i);
        finish();
    }

    private AlertDialog askOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Ikuti Event")
                .setMessage("Dengan melanjutkan maka Anda akan terdaftar pada event ini")
                .setIcon(R.drawable.ticket)

                .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        submit();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }
}