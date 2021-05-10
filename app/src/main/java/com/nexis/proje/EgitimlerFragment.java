package com.nexis.proje;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EgitimlerFragment extends Fragment {

    private ListView mList;
    private ArrayAdapter<String> adapter;
    private String[] eğitimler={"Alfabe", "Aile ve Akrabalar","Hayvanlar","Taşıt ve Trafik","Renkler","Meslekler","Fiiller",
    "Spor"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_egitimler, container, false);
        mList=(ListView) v.findViewById(R.id.egitimlerList);
        ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, eğitimler);
        mList.setAdapter(veriAdaptoru);


        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Intent intent0=new Intent(getContext(),AlfabeActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1=new Intent(getContext(),AileveAkrabalarActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2=new Intent(getContext(),HayvanlarActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3=new Intent(getContext(),TasitveTrafikActivity.class);
                        startActivity(intent3);;
                        break;
                    case 4:
                        Intent intent4=new Intent(getContext(),RenklerActivity.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5=new Intent(getContext(),MesleklerActivity.class);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6=new Intent(getContext(),FiillerActivity.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7=new Intent(getContext(),SporActivity.class);
                        startActivity(intent7);
                        break;


                }
            }
        });
        return v;

    }
}