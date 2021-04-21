package com.example.e_event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.SpanWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DataUser db_user;
    private User authenticatedUser;

    private boolean isAuthenticated;
    Button btn_login;
    TextView tv_register;
    EditText et_email, et_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isAnyUserAuthenticated()){
            startActivity(new Intent(this, EventActivity.class));
            finish();
        }else {
            setContentView(R.layout.activity_main);
            getSupportActionBar().hide();
            init();
            register();
            login();
        }
    }

    void init(){
        et_email = findViewById(R.id.reg_et_email);
        et_pwd = findViewById(R.id.reg_et_pwd);
        btn_login = findViewById(R.id.reg_btn_daftar);
        tv_register = findViewById(R.id.reg_tv_masuk);
    }

    void login(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString().toLowerCase();
                String pwd = et_pwd.getText().toString();
                isAuthenticated = false;
                db_user = new DataUser(MainActivity.this);
                db_user.open();
                List<User> users = db_user.getAllUser();
                if(users.isEmpty()){
                    Toast.makeText(MainActivity.this, "Database kosong, silakan buat akun baru", Toast.LENGTH_LONG).show();
                }else {
                    for (User user : users) {
                        int user_id = user.getId();
                        String user_name = user.getFullname();
                        String user_email = user.getEmail();
                        String user_phone = user.getPhone();
                        String user_pwd = user.getPassword();
                        if (user_email.equals(email) && user_pwd.equals(pwd)) {
                            isAuthenticated = true;
                            db_user.logOneUser(user_id,1);
//                            Toast.makeText(MainActivity.this, "Logged in:"+user_id, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, EventActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    }
                    if(!isAuthenticated) {
                        Toast.makeText(MainActivity.this, "Email atau password salah", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    void register(){
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent register = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }

    boolean isAnyUserAuthenticated(){
        db_user = new DataUser(this);
        authenticatedUser = db_user.getLoggedInUser();
        if(authenticatedUser != null){
            return true;
        }
        return false;
    }

}