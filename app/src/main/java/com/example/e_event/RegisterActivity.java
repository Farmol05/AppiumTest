package com.example.e_event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private DataUser user;
    Button btn_register;
    TextView tv_login, p1,p2;
    EditText et_nama, et_email, et_hp, et_pwd, et_pwd2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initUser();
        init();
        register();
        login();
    }

    void init(){
        et_nama = findViewById(R.id.reg_et_nama);
        et_email = findViewById(R.id.reg_et_email);
        et_hp = findViewById(R.id.reg_et_hp);
        et_pwd = findViewById(R.id.reg_et_pwd);
        et_pwd2 = findViewById(R.id.reg_et_pwd2);
        btn_register = findViewById(R.id.reg_btn_daftar);
        tv_login = findViewById(R.id.reg_tv_masuk);
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
    }

    void register(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = et_nama.getText().toString().toLowerCase();
                String email = et_email.getText().toString().toLowerCase();
                String hp = et_hp.getText().toString();
                String pwd = et_pwd.getText().toString();
                String pwd2 = et_pwd2.getText().toString();
                p1.setText(pwd);
                p2.setText(pwd2);
                String a = p1.getText().toString();
                String b = p2.getText().toString();

                if(TextUtils.isEmpty(et_nama.getText())){
                    et_nama.requestFocus();
                    et_nama.setError("Tidak boleh kosong");
                }else if(TextUtils.isEmpty(et_email.getText())){
                    et_email.requestFocus();
                    et_email.setError("Tidak boleh kosong");
                }else if(TextUtils.isEmpty(et_hp.getText())){
                    et_hp.requestFocus();
                    et_hp.setError("Tidak boleh kosong");
                }else if(TextUtils.isEmpty(et_pwd.getText())){
                    et_pwd.requestFocus();
                    et_pwd.setError("Tidak boleh kosong");
                }else if(!passConfirmed(a, b)){
                    et_pwd2.requestFocus();
                    et_pwd2.setError("Kata sandi tidak sama");
                    Toast.makeText(RegisterActivity.this, a+":"+b, Toast.LENGTH_SHORT).show();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    et_email.requestFocus();
                    et_email.setError("Email tidak valid");
                }else if(emailIsUsed(email)){
                    et_email.requestFocus();
                    et_email.setError("Email ini sudah digunakan");
                }else{
                    user.addUser(new User(nama, email, hp, pwd));
                    Toast.makeText(RegisterActivity.this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }
        });
    }

    void login(){
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(login);

            }
        });
    }

    void initUser(){
        user = new DataUser(this);
        user.open();

    }

    boolean passConfirmed(String a, String b){
        if(a.equals(b)) return true;
        else return false;
    }

    boolean emailIsUsed(String email){
        List<User> users =  user.getAllUser();
        for(User user:users){
            String db_email = user.getEmail();
            if(email.equals(db_email)){
                return true;
            }
        }
                return false;
    }


}