package com.example.tasarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RelativeLayout panel;
    Button btnlogin,btnsign,btnregister;
    EditText txtusername,txtpassword,txtemail;
    SQLiteDatabase db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        panel = findViewById(R.id.panel);
        btnlogin = findViewById(R.id.btnlogin);
        btnsign = findViewById(R.id.btnsign);
        btnregister = findViewById(R.id.btnregister);
        txtusername = findViewById(R.id.txtusername);
        txtpassword = findViewById(R.id.txtpassword);
        txtemail = findViewById(R.id.txtemail);
        txtemail.setVisibility(View.INVISIBLE);
        btnregister.setVisibility(View.INVISIBLE);
        Animation anim1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim1);
        Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim2);
        panel.startAnimation(anim1);

        try {
            db = this.openOrCreateDatabase("login",MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS person (username VARCHAR, password VARCHAR, email VARCHAR)");
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (txtusername.getText().toString().equals("")||txtpassword.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this,"Kullanıcı Adı Veya Şifre Boş Bırakılamaz",Toast.LENGTH_SHORT).show();
                    }else {
                        Cursor cursor = db.rawQuery("SELECT * FROM person WHERE username = ?", new String[] {txtusername.getText().toString()});
                        if (cursor.getCount() > 0){
                            Cursor cursor2 = db.rawQuery("SELECT * FROM person WHERE password = ?", new String[] {txtpassword.getText().toString()});
                            if (cursor2.getCount() > 0){
                                Toast.makeText(MainActivity.this,"Giriş Başarılı",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                                startActivity(intent);
                                txtusername.setText("");
                                txtpassword.setText("");
                            }else {
                                Toast.makeText(MainActivity.this,"Şifre Yanlış",Toast.LENGTH_SHORT).show();
                                txtusername.setText("");
                                txtpassword.setText("");
                            }
                        }else {
                            Toast.makeText(MainActivity.this,"Kullanıcı Adı Yanlış",Toast.LENGTH_SHORT).show();
                            txtusername.setText("");
                            txtpassword.setText("");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panel.startAnimation(anim2);
                txtemail.setVisibility(View.VISIBLE);
                btnlogin.setVisibility(View.INVISIBLE);
                btnsign.setVisibility(View.INVISIBLE);
                btnregister.setVisibility(View.VISIBLE);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (txtusername.getText().toString().equals("")||txtpassword.getText().toString().equals("")||txtemail.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this,"Lütfen Tüm Alanları Doldurunuz",Toast.LENGTH_SHORT).show();
                    }else {
                        Cursor cursor = db.rawQuery("SELECT * FROM person WHERE username = ?", new String[] {txtusername.getText().toString()});
                        if (cursor.getCount() > 0){
                            Toast.makeText(MainActivity.this,"Bu Kullanıcı Adı Zaten Kullanılıyor",Toast.LENGTH_SHORT).show();
                        }else {
                            Cursor cursor2 = db.rawQuery("SELECT * FROM person WHERE email = ?", new String[] {txtemail.getText().toString()});
                            if (cursor2.getCount() > 0){
                                Toast.makeText(MainActivity.this,"Bu E-Mail Zaten Kullanılıyor",Toast.LENGTH_SHORT).show();
                            }else {
                                db.execSQL("INSERT INTO person(username,password,email) VALUES ('"+txtusername.getText().toString()+"','"+txtpassword.getText().toString()+"','"+txtemail.getText().toString()+"')");
                                Toast.makeText(MainActivity.this,"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
                                txtusername.setText("");
                                txtpassword.setText("");
                                txtemail.setText("");
                                panel.startAnimation(anim1);
                                txtemail.setVisibility(View.INVISIBLE);
                                btnregister.setVisibility(View.INVISIBLE);
                                btnlogin.setVisibility(View.VISIBLE);
                                btnsign.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
