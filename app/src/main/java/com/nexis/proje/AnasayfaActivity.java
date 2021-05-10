package com.nexis.proje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AnasayfaActivity extends AppCompatActivity {


    private DrawerLayout mDrawer;
    private NavigationView mNavigation;
    private Toolbar mToolBar;
    private ActionBarDrawerToggle mToggle;
    private ProfilFragment profil;
    private EgitimlerFragment egitim;
    private TextView txtBilgi;
    //private CikisFragment cikis;
    //private IletisimFragment iletisim;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        profil=new ProfilFragment();
        egitim=new EgitimlerFragment();
        mDrawer=(DrawerLayout) findViewById(R.id.Anasayfa_Activity_DrawerLayout);
        mNavigation=(NavigationView) findViewById(R.id.Anasayfa_Activity_NavigationView);
        mToolBar=(Toolbar)findViewById(R.id.Anasayfa_Activity_Toolbar);
        setSupportActionBar(mToolBar);
        mToggle=new ActionBarDrawerToggle(AnasayfaActivity.this,mDrawer,mToolBar,R.string.nav_Open,R.string.nav_Close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        mAuth=FirebaseAuth.getInstance();


        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.profil:
                        setFragment(profil);
                        mDrawer.closeDrawer(GravityCompat.START);
                        return true;

                    case R.id.eğitimler:
                        setFragment(egitim);
                        mDrawer.closeDrawer(GravityCompat.START);
                        return  true;

                    case R.id.cikis:
                        cikisYasp();
                        mDrawer.closeDrawer(GravityCompat.START);
                        return  true;

                    default:
                        return false;
                }

            }
        });

    }
    private void cikisYasp()
    {
        mAuth.signOut();
        Intent intent=new Intent(AnasayfaActivity.this,MainActivity.class);
        finish();
        startActivity(intent);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction t=getSupportFragmentManager().beginTransaction();
        t.replace(R.id.Anasayfa_Activity_FrameLayout,fragment);
        t.commit();

    }
    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START))
        {
            mDrawer.closeDrawer(GravityCompat.START);
        }
    }
}