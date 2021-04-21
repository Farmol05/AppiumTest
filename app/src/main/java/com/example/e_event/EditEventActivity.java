package com.example.e_event;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class EditEventActivity extends AppCompatActivity {

    private DataEvent event;
    private User authUser;
    private DataUser db_user;

    private int eventID;
    private int userID;
    EditText ettitle,etorg,etdate,etcap,etdesc;
    Button btn_submit, btn_gbr, btn_hps;
    Bitmap poster;
    byte[] poster_byte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        db_user = new DataUser(this);
        authUser = db_user.getLoggedInUser();
        userID = authUser.getId();
        init();
        initEvent();
        uploadGambar();
        submit();
        hapusEvent();

    }

    void init(){
        ettitle = findViewById(R.id.edev_et_judul);
        etorg = findViewById(R.id.edev_et_org);
        etdate = findViewById(R.id.edev_et_date);
        etcap = findViewById(R.id.edev_cap);
        etdesc = findViewById(R.id.edev_et_desc);
        btn_submit = findViewById(R.id.edev_btn_daftar);
        btn_gbr = findViewById(R.id.edev_btn_gbr);
        btn_hps = findViewById(R.id.edev_btn_hps);


    }

    void initEvent(){
        event = new DataEvent(this);
        Intent intent = getIntent();
        eventID = intent.getIntExtra("id",0);
        Event e = event.getOneEvent(eventID);
        ettitle.setText(e.getTitle());
        etorg.setText(e.getOrganizer());
        etcap.setText(Integer.toString(e.getTicket()));
        etdate.setText(e.getDate_started());
        etdesc.setText(e.getDesc());
        poster_byte = e.getPoster();
        poster = BitmapFactory.decodeByteArray(poster_byte,0,poster_byte.length);
    }

    void submit(){
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(ettitle.getText())){
                    ettitle.requestFocus();
                    ettitle.setError("Tidak boleh kosong");
                }else if(ettitle.getText().length() > 80){
                    ettitle.requestFocus();
                    ettitle.setError("Max 80 Karakter : ("+ettitle.getText().length()+")");
                }else if(TextUtils.isEmpty(etorg.getText())){
                    etorg.requestFocus();
                    etorg.setError("Tidak boleh kosong");
                }else if(TextUtils.isEmpty(etdate.getText())){
                    etdate.requestFocus();
                    etdate.setError("Tidak boleh kosong");
                }else if(TextUtils.isEmpty(etcap.getText())){
                    etcap.requestFocus();
                    etcap.setError("Tidak boleh kosong");
                }else if(poster == null){
                    Toast.makeText(EditEventActivity.this, "Masukkan poster", Toast.LENGTH_SHORT).show();
                }else{
                    String title = ettitle.getText().toString();
                    String organizer = etorg.getText().toString();
                    String date = etdate.getText().toString();
                    int capacity = Integer.parseInt(etcap.getText().toString());
                    String desc = etdesc.getText().toString();
                    event.updateOne(eventID, title, organizer, date, capacity, desc, poster_byte, 1, userID);
                    Toast.makeText(EditEventActivity.this, "Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(EditEventActivity.this, EventActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

    void uploadGambar(){
        btn_gbr.setOnClickListener(new View.OnClickListener() {
            private static final int RESULT_LOAD_IMG = 1;
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });
    }
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                int w = selectedImage.getWidth();
                int h = selectedImage.getHeight();

                Log.d("height", "H:"+h);
                Log.d("height", "W:"+w);

                if(w < 2000 && h < 2220) {
                    poster = selectedImage;
                    poster_byte = bitmapToByte(poster);
                    Toast.makeText(getApplicationContext(), "Berhasil",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Poster terlalu besar", Toast.LENGTH_LONG).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(EditEventActivity.this, "Ada kesalahan", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(EditEventActivity.this, "Anda belum memilih poster",Toast.LENGTH_LONG).show();
        }
    }

    public static byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public void hapusEvent(){
        btn_hps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = askOption();
                alert.show();
            }
        });
    }

    private AlertDialog askOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Hapus Event")
                .setMessage("Apakah Anda ingin menghapus event ini?")
                .setIcon(R.drawable.ic_del)

                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        event.deleteOne(eventID);
                        Toast.makeText(EditEventActivity.this, "Event ID:"+eventID+" dihapus", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                        startActivity(intent);
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