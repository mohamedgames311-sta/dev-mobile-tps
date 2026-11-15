package com.dsi3.isetkb.mapremiereapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * TP1 - Ma première application Android.
 * L'activité principale affiche simplement le layout activity_principale.xml.
 */
public class Principale extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principale);
    }
}
