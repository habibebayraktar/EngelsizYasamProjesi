package com.nexis.proje;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class TasitveTrafikActivity extends AppCompatActivity {

    private WebView gif;
    private Button bIleri, bGeri;
    private TextView txtHarf;
    private String[] kelimeler={"taşıt-trafik","araba","benzin","motor","demiryolu","direksiyon","dolmuş","durak","ehliyet","far","gaz","gemi","hava alanı","helikopter","istasyon","kamyon","kaptan","kavşak","kayık","kaza","korna","köprü","kriko","metro","otobüs","motor","pilot","plaka","silecek"
            ,"sinyal","şöför","taksi","tamir","tekerlek","tır","traktör","tren","tünel","uçak","viraj","yaya","yol","cadde","yolcu"};
    private int s=-1, h=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasitve_trafik);




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
                    harfGoster(h);
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
                    harfGoster(s);
                    filePath(s);
                }
                else if(s==0){
                    harfGoster(s);
                    filePath(s);
                    bGeri.setEnabled(false);

                }else{
                    bIleri.setEnabled(true);
                    bGeri.setEnabled(false);
                }
            }
        });
    }
    public void harfGoster(int indx){
        for (String h:kelimeler){
            if (h==kelimeler[indx]){
                txtHarf.setText(kelimeler[indx]);
            }
        }
    }
    public void filePath(int indx){
        for (int i=0; i<kelimeler.length;i++){
            if (i==indx){
                String file="file:android_asset/tasitvetrafik/"+kelimeler[i]+".gif";
                gif.loadUrl(file);
            }
        }
    }
}