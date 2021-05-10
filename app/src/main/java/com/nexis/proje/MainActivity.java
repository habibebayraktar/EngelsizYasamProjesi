package com.nexis.proje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button BtnKayitOl;
    private EditText edtGirisMail, edtGirisSifre;
    private String girisTxt, sifreTxt;
    private TextView sifremiUnuttum;
    private FirebaseAuth mAuth;
    private LinearLayout mConstraintLayout;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BtnKayitOl = (Button) findViewById(R.id.btnKayitOl);
        edtGirisMail = (EditText) findViewById(R.id.editGirisMail);
        edtGirisSifre = (EditText) findViewById(R.id.editGirisSifre);
        sifremiUnuttum=(TextView)findViewById(R.id.sifremiUnuttum);
        mAuth = FirebaseAuth.getInstance();
        mConstraintLayout = (LinearLayout) findViewById(R.id.ConstraintLayout_giris);


        BtnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, KayitOlActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });


        sifremiUnuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SifremiUnuttumActivity.class));
            }
        });
    }

    public  void girisYap(View v){
        girisTxt= edtGirisMail.getText().toString();
        sifreTxt= edtGirisSifre.getText().toString();
        System.out.println(sifreTxt);
        if (! TextUtils.isEmpty(girisTxt) && ! TextUtils.isEmpty(girisTxt)){


            mProgress=new ProgressDialog(MainActivity.this);
            mProgress.setTitle("Giriş yapılıyor...");
            mProgress.show();

            mAuth.signInWithEmailAndPassword(girisTxt,sifreTxt).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressAyar();
                        Toast.makeText(MainActivity.this,"Başarıyla giriş yaptınız",Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(MainActivity.this,AnasayfaActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }else{
                        progressAyar();
                        Snackbar.make(mConstraintLayout,task.getException().getMessage(),Snackbar.LENGTH_LONG).show();
                        System.out.println(task.getException().getMessage());
                    }
                }
            });

        }else{
            Toast.makeText(MainActivity.this,"Alanlar Boş Geçilemez",Toast.LENGTH_SHORT).show();
        }
    }
    private void progressAyar(){
        if(mProgress.isShowing()){
            mProgress.dismiss();
        }
    }
}
