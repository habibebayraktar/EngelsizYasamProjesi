package com.nexis.proje;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class SifremiDegistirActivity extends AppCompatActivity {

    private EditText edtSifre,edtSifreTekrar;
    private FirebaseFirestore mFireStore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Kullanici user;
    private DocumentReference mRef;
    private TextInputLayout inputSifre, inputSifreTekrar;
    private Button sifreDegistir;
    private HashMap<String, Object> mData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_degistir);

        edtSifre=(EditText) findViewById(R.id.sifre_degistir);
        edtSifreTekrar=(EditText) findViewById(R.id.sifre_degistir_tekrar);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mFireStore = FirebaseFirestore.getInstance();
        sifreDegistir=(Button) findViewById(R.id.btn_sifreDegistir);
        inputSifre=(TextInputLayout) findViewById(R.id.inputSifre);
        inputSifreTekrar=(TextInputLayout) findViewById(R.id.inputSifreTekrar);

        mRef = mFireStore.collection("kullanicilar").document(mUser.getUid());

        mRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(SifremiDegistirActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null && value.exists()) {
                    user = value.toObject(Kullanici.class);

                }
            }
        });

        sifreDegistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtSifre.getText().toString()) && ! TextUtils.isEmpty(edtSifre.getText().toString())){

                    if (edtSifre.getText().toString().equals(edtSifreTekrar.getText().toString())){
                            //String sifreKayit=MD5Sifreleme.md5Sifreleme(edtSifre.getText().toString());
                            String sifreKayit=edtSifre.getText().toString();
                            mData=new HashMap<>();
                            mData.put("kullaniciSifre",sifreKayit);

                            mUser.updatePassword(sifreKayit).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent=new Intent(SifremiDegistirActivity.this, AnasayfaActivity.class);
                                        finish();
                                        startActivity(intent);
                                        Toast.makeText(SifremiDegistirActivity.this, "Şifreniz başarıyla güncellenmiştir.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    }else{
                        inputSifre.setError("Sifreler aynı değil");
                        inputSifreTekrar.setError("Sifreler aynı değil");
                    }

                }else{
                    Toast.makeText(SifremiDegistirActivity.this,"Alanlar boş geçilemez", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}