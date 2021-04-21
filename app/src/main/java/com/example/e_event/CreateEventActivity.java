package com.example.e_event;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateEventActivity extends AppCompatActivity {
    private DataEvent event;
    private User authUser;
    private DataUser db_user;
    private EditText ettitle,etorg,etdate,etcap,etdesc;
    private Button btn_submit, btn_gbr;
    private Bitmap poster;
    private byte[] poster_byte = null;
    private TextView tv_posterName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        db_user = new DataUser(this);
        authUser = db_user.getLoggedInUser();
        initEvent();
        init();
        uploadGambar();
        submit();

    }

    void init(){
        ettitle = findViewById(R.id.crev_et_judul);
        etorg = findViewById(R.id.crev_et_org);
        etdate = findViewById(R.id.crev_et_date);
        etcap = findViewById(R.id.crev_cap);
        etdesc = findViewById(R.id.crev_et_desc);
        btn_submit = findViewById(R.id.crev_btn_daftar);
        btn_gbr = findViewById(R.id.crev_btn_gbr);
        tv_posterName = findViewById(R.id.crev_tv_postername);
    }

    void initEvent(){
        event = new DataEvent(this);
        event.open();
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
                    AlertDialog alert = askOption();
                    alert.show();
                }else{
                    create();
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

                if(w < 2000 && h < 2000) {
                    poster = selectedImage;
                    Toast.makeText(CreateEventActivity.this, "Berhasil",Toast.LENGTH_LONG).show();
                    tv_posterName.setText("poster.jpg");
                }else{
                    Toast.makeText(this, "Poster terlalu besar", Toast.LENGTH_LONG).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(CreateEventActivity.this, "Ada kesalahan", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(CreateEventActivity.this, "Anda belum memilih poster",Toast.LENGTH_LONG).show();
        }
    }

    public static byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    private AlertDialog askOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Belum ada poster")
                .setMessage("Proses tidak bisa dilanjutkan")
                .setIcon(R.drawable.ic_no_img)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }

    public void create() {
        String title = ettitle.getText().toString();
        String organizer = etorg.getText().toString();
        String date = etdate.getText().toString();
        int capacity = Integer.parseInt(etcap.getText().toString());
        String desc = etdesc.getText().toString();
        int owner_id = authUser.getId();
        poster_byte = bitmapToByte(poster);
        event.addEvent(new Event(title, organizer, capacity, date, desc, poster_byte, 1, owner_id));
        Toast.makeText(CreateEventActivity.this, "Berhasil membuat event", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(CreateEventActivity.this, EventActivity.class);
        startActivity(i);
        finish();
    }
}