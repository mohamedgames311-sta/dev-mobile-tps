package com.dsi3.isetkb.tp4auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/** Écran affiché après une connexion réussie. */
public class AcceuilAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        ((Button) findViewById(R.id.btnLogout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AcceuilAct.this, MainActivity.class));
                finish();
            }
        });
    }
}
