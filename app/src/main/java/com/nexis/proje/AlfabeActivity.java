package com.nexis.proje;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.DateTimePatternGenerator;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class AlfabeActivity extends AppCompatActivity {

    private WebView gif;
    private Button bIleri, bGeri;
    private TextView txtHarf;
    private String[] harfler={"ALFABE","A","B","C","Ç","D","E","F","G","Ğ","H","I","İ-i","J","K","L","M","N","O","Ö","P","R","S","Ş","T","U","Ü","V","Y","Z"};
    private int s=-1, h=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alfabe);

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
                if (s<harfler.length)
                {
                    bIleri.setEnabled(true);
                    harfGoster(h);
                    filePath(s);
                    h++;
                }
                else if(s==harfler.length || s>harfler.length){
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
        for (String h:harfler){
            if (h==harfler[indx]){
                txtHarf.setText(harfler[indx]);
            }
        }
    }
    public void filePath(int indx){
        for (int i=0; i<harfler.length;i++){
            if (i==indx){
                String file="file:android_asset/alfabe/"+harfler[i]+".gif";
                gif.loadUrl(file);
            }
        }
    }
}