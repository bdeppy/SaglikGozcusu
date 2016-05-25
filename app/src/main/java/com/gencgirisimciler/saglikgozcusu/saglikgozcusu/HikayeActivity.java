package com.gencgirisimciler.saglikgozcusu.saglikgozcusu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarSearchHandler;
import com.blunderer.materialdesignlibrary.listeners.OnSearchListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Bdeppy on 24.05.2016.
 */
public class HikayeActivity extends com.blunderer.materialdesignlibrary.activities.Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_hikaye);

        Intent ResultsActivityGelenIntent = getIntent();
        String toolbarTitleName =ResultsActivityGelenIntent.getStringExtra("MaddeAdi");

        final TextView sonucTextView = (TextView)findViewById(R.id.hikayeActivitySonucTextView);
        int indexOf = ResultsActivityGelenIntent.getIntExtra("MaddeIndex",-1);

        if(indexOf!=-1)
            sonucTextView.setText("\t"+ getMaddeIndex("maddeler.json",indexOf));
        else
            sonucTextView.setText("\tAradığınız madde bulunmamaktadır, internet üzerinden aratma yapmak için tıklayınız...");

        sonucTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sonucTextView.getText().toString().startsWith("Aradığınız")) {

                }
            }
        });

        getSupportActionBar().setTitle(toolbarTitleName);

    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarSearchHandler(this, new OnSearchListener() {

            @Override
            public void onSearched(String text) {
                Toast.makeText(getApplicationContext(),
                        "Searching \"" + text + "\"", Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_hikaye;
    }

    private String getMaddeIndex(String fileName,int position)
    {
        String sonuc="" ;
        JSONArray jsonArray = null;

        ArrayList<String> cList = new ArrayList<String>();

        try {

            InputStream is = getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();

            String json = new String(data,"UTF-8");
            jsonArray= new JSONArray(json);

            if(jsonArray!=null){

//                MainActivity.mMaddeListesi.get(position)

                sonuc= jsonArray.getJSONObject(position).getString("Turkce");
             /*   for(int i =0;i<jsonArray.length();i++)
                {
                    cList.add(jsonArray.getJSONObject(i).getString("Turkce"));
                }*/
            }
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return  sonuc;
    }
}
