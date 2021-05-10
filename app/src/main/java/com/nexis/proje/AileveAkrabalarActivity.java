package com.nexis.proje;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;


public class AileveAkrabalarActivity extends AppCompatActivity {

    private WebView gif;
    private Button bIleri, bGeri;
    private TextView txtHarf;
    private String[] kelimeler={"aile-akraba","abi","abla","aile","baba","anne","kardeş","bebek","hala","dede","nine","amca","dayi","teyze","enişte","yenge","kuzen","akraba","torun","yeğen","erkek","kız","oğul","kızı","ikiz","arkadaş","düğün","nikah","damat","gelin","nişan","kına","bekar","evli","boşanmış","üvey","kayınvalide"};
    private int s=-1, h=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aileve_akrabalar);

        txtHarf=(TextView)findViewById(R.id.txtHarf);
        bGeri=(Button)findViewById(R.id.geri);
        bIleri=(Button)findViewById(R.id.ileri);
        gif=(WebView) findViewById(R.id.gifAlani);
        gif.getSettings().setLoadWithOverviewMode(true);
        gif.getSettings().setUseWideViewPort(true);
        WebSettings webSet=gif.getSettings();
        webSet.setJavaScriptEnabled(true);


        bIleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bGeri.setEnabled(true);
                s++;
                if (s<kelimeler.length)
                {
                    bIleri.setEnabled(true);
                    kelimeGoster(h);
                    filePath(s);
                    h++;
                }
                else if(s==kelimeler.length || s>kelimeler.length){
                    bIleri.setEnabled(false);
                }
            }
        });
        bGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bIleri.setEnabled(true);
                s--;
                h--;
                if (s>0){
                    kelimeGoster(s);
                    filePath(s);
                }
                else if(s==0){
                    kelimeGoster(s);
                    filePath(s);
                    bGeri.setEnabled(false);

                }else{
                    bIleri.setEnabled(true);
                    bGeri.setEnabled(false);
                }
            }
        });
    }
    public void kelimeGoster(int indx){
        for (String h:kelimeler){
            if (h==kelimeler[indx]){
                txtHarf.setText(kelimeler[indx]);
            }
        }
    }
    public void filePath(int indx){
        for (int i=0; i<kelimeler.length;i++){
            if (i==indx){
                String file="file:android_asset/aileveakrabalar/"+kelimeler[i]+".gif";
                gif.loadUrl(file);
            }
        }
    }
}