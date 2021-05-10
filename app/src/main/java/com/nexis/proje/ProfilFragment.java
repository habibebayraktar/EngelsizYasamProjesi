package com.nexis.proje;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


public class ProfilFragment extends Fragment {

    private static final int IZIN_KODU=0;
    private static final int IZIN_ALINDI_KODU=1;

    private Intent galeriIntent;
    private EditText  profil_fragment_isim,profil_fragment_email;
    public EditText profil_fragment_soyad;
    private TextView txtSifremiDegistir, txtBilgiDegistir;
    private Button btnDegisikleriKaydet;
    private ImageView imgProfil, imgNewProfil;
    private  View v;

    private FirebaseFirestore mFireStore;
    private DocumentReference mRef;
    private FirebaseUser mUser;
    private Kullanici user;

    private Uri mUri;
    private Bitmap gelenResim;
    private ImageDecoder.Source imgSource;

    private ByteArrayOutputStream outputStream;
    private byte[] imgByte;

    private StorageReference mStorageReference, yeniRef,sRef;
    private String kayitYeri,indirmeLinki;
    private HashMap<String, Object> mData;
    private HashMap<String, Object> mGüncelData;

    private ProgressDialog mProgress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profil, container, false);
        profil_fragment_isim = v.findViewById(R.id.profil_fragment_edtIsim);
        profil_fragment_email = v.findViewById(R.id.profil_fragment_edtEmail);
        profil_fragment_soyad =v.findViewById(R.id.profil_fragment_edtSoyad);
        txtSifremiDegistir=v.findViewById(R.id.profil_fragment_sifreDegistir);
        txtBilgiDegistir=v.findViewById(R.id.profil_fragment_bilgileriDegistir);
        btnDegisikleriKaydet=v.findViewById(R.id.profil_fragment_bilgileriKaydet);
        btnDegisikleriKaydet.setVisibility(v.GONE);
        imgProfil = v.findViewById(R.id.fragment_imgUserProfil);
        imgNewProfil=v.findViewById(R.id.profil_fragment_imgYeniResim);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mFireStore = FirebaseFirestore.getInstance();
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mRef = mFireStore.collection("kullanicilar").document(mUser.getUid());

        mRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(v.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value != null && value.exists()) {
                    user = value.toObject(Kullanici.class);

                    if (user != null) {
                        profil_fragment_isim.setText(user.getKullaniciIsmi());
                        profil_fragment_email.setText(user.getKullaniciEmail());
                        profil_fragment_soyad.setText(user.getKullaniciSoyadi());
                        if (user.getKullaniciImg().equals("Kullanici profil")){
                            imgProfil.setImageResource(R.mipmap.profil);
                        }else{
                            Picasso.get().load(user.getKullaniciImg()).resize(156,156).into(imgProfil);
                        }

                    }
                }
            }
        });
        imgNewProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions((Activity) v.getContext(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, IZIN_KODU);
                }else galeriyeGit();
            }
        });
        txtBilgiDegistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profil_fragment_isim.setEnabled(true); profil_fragment_soyad.setEnabled(true); btnDegisikleriKaydet.setEnabled(true);
                btnDegisikleriKaydet.setVisibility(v.VISIBLE);

                btnDegisikleriKaydet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if ( !TextUtils.isEmpty(profil_fragment_isim.getText().toString()) && ! TextUtils.isEmpty(profil_fragment_soyad.getText().toString())){

                           profil_fragment_isim.setEnabled(false); profil_fragment_soyad.setEnabled(false);
                           btnDegisikleriKaydet.setVisibility(v.GONE);
                           String yeniIsım=profil_fragment_isim.getText().toString();
                           String yeniSoyad=profil_fragment_soyad.getText().toString();
                           profil_fragment_isim.setText(yeniIsım);
                           profil_fragment_soyad.setText(yeniSoyad);
                           mGüncelData=new HashMap<>();
                           mGüncelData.put("kullaniciIsmi",yeniIsım);
                           mGüncelData.put("kullaniciSoyadi",yeniSoyad);
                           mFireStore.collection("kullanicilar").document(user.getKullaniciId()).update(mGüncelData).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()){
                                       Toast.makeText(v.getContext(), "Güncelleme işlemi başarılı", Toast.LENGTH_SHORT).show();
                                   }else{

                                   }
                               }
                           });
                       }
                       else{
                           Toast.makeText(v.getContext(),"Alanlar boş geçilemez",Toast.LENGTH_SHORT).show();
                       }

                    }
                });

            }
        });


        txtSifremiDegistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SifremiDegistirActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
    public void galeriyeGit(){
        galeriIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galeriIntent,IZIN_ALINDI_KODU);
        mProgress =new ProgressDialog(v.getContext());
        mProgress.setTitle("yükleniyor...");
        mProgress.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode ==IZIN_KODU){
            if (grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                galeriyeGit();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode ==IZIN_ALINDI_KODU){
            if (resultCode== RESULT_OK && data != null && data.getData() != null){
                mUri= data.getData();

                try {
                    if (Build.VERSION.SDK_INT >=28){
                        imgSource=ImageDecoder.createSource(v.getContext().getContentResolver(),mUri);
                        gelenResim=ImageDecoder.decodeBitmap(imgSource);
                    }else{
                        gelenResim=MediaStore.Images.Media.getBitmap(v.getContext().getContentResolver(),mUri);
                    }

                    outputStream=new ByteArrayOutputStream();
                    gelenResim.compress(Bitmap.CompressFormat.PNG,75,outputStream);
                    imgByte=outputStream.toByteArray();

                    kayitYeri="kullanicilar/"+ user.getKullaniciEmail() +"/profil.png";
                    sRef=mStorageReference.child(kayitYeri);
                    sRef.putBytes(imgByte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            yeniRef= FirebaseStorage.getInstance().getReference(kayitYeri);
                            yeniRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    indirmeLinki= uri.toString();
                                    mData=new HashMap<>();
                                    mData.put("kullaniciImg",indirmeLinki);

                                    mFireStore.collection("kullanicilar").document(mUser.getUid()).update(mData)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        progressAyar();
                                                        Toast.makeText(v.getContext(),"Profil fotoğrafınız başarıyla güncellendi", Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        Toast.makeText(v.getContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void progressAyar(){
        if (mProgress.isShowing()){
            mProgress.dismiss();
        }
    }
}