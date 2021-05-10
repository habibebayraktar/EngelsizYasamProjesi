package com.nexis.proje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SifremiUnuttumActivity extends AppCompatActivity {

    private EditText edtMail;
    private Button btnIleri;
    private TextInputLayout txtMail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);

        edtMail=(EditText) findViewById(R.id.sifremiUnuttumEdt);
        btnIleri=(Button) findViewById(R.id.btnIleri);
        txtMail=(TextInputLayout) findViewById(R.id.sifremiUnuttum);
        mAuth=FirebaseAuth.getInstance();


        btnIleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=edtMail.getText().toString().trim();
                if (!TextUtils.isEmpty(mail)){

                   mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                                startActivity(new Intent(SifremiUnuttumActivity.this,MainActivity.class));
                                finish();
                                Toast.makeText(SifremiUnuttumActivity.this,"Yeni parola için gerekli bağlantı adresinize gönderildi",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SifremiUnuttumActivity.this,"Mail gönderme hatası",Toast.LENGTH_SHORT).show();
                            }
                        }
                   });

                }else{
                    txtMail.setError("E-mail alanı boş geçilemez");
                }
            }
        });




    }
}