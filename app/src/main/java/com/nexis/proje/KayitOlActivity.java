package com.nexis.proje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class KayitOlActivity extends AppCompatActivity {

    private ProgressDialog mProgress;
    private static final String TAG="KayitOlActivity";
    private EditText edtAd,edtSoyad,edtEmail,edtSifre;
    private String txtAd,txtSoyad,txtEmail,txtSifre,txtFoto;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;
    private Kullanici mKullanici;
    private Button btnKayit;
    private LinearLayout cL_KayitOl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        btnKayit=(Button)findViewById(R.id.btnKayitOl);
        edtAd = (EditText) findViewById(R.id.edtAd);
        edtSoyad = (EditText) findViewById(R.id.edtSoyad);
        edtEmail = (EditText) findViewById(R.id.edtMail);
        edtSifre = (EditText) findViewById(R.id.edtSifre);
        cL_KayitOl=(LinearLayout) findViewById(R.id.ConstraintLayout_kayitOl);
        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();

        btnKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAd= edtAd.getText().toString();
                txtSoyad=edtSoyad.getText().toString();
                txtEmail=edtEmail.getText().toString();
                txtSifre= edtSifre.getText().toString();
                txtFoto="Kullanici profil";
                if ( ! TextUtils.isEmpty(txtAd) && ! TextUtils.isEmpty(txtSoyad) && ! TextUtils.isEmpty(txtEmail) && ! TextUtils.isEmpty(txtSifre)){


                    mProgress =new ProgressDialog(KayitOlActivity.this);
                    mProgress.setTitle("Kayıt olunuyor...");
                    mProgress.show();
                    mAuth.createUserWithEmailAndPassword(txtEmail,txtSifre).addOnCompleteListener(KayitOlActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                mUser=mAuth.getCurrentUser();
                                if (mUser !=null){

                                    mKullanici=new Kullanici(mUser.getUid(),txtEmail,txtSifre, txtAd, txtSoyad,txtFoto);

                                    mFirestore.collection("kullanicilar").document(mUser.getUid())
                                            .set(mKullanici).addOnCompleteListener(KayitOlActivity.this, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                progressAyar();
                                                Toast.makeText(KayitOlActivity.this,"Başarıyla kayıt olundu", Toast.LENGTH_SHORT).show();
                                                finish();
                                                startActivity(new Intent(KayitOlActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                                            }else{
                                                Snackbar.make(cL_KayitOl,task.getException().getMessage(),Snackbar.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }


                            }
                            else{
                                progressAyar();
                                Toast.makeText(KayitOlActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                System.out.println(task.getException().getMessage());
                            }
                        }
                    });


                }else{
                    Toast.makeText(KayitOlActivity.this, "Alanlar boş geçilemez",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void progressAyar(){
        if (mProgress.isShowing()){
            mProgress.dismiss();
        }
    }
}