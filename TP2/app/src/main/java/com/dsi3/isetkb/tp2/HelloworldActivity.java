package com.dsi3.isetkb.tp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * TP2 - Activité de départ.
 *  - Partie 1 : chaque méthode du cycle de vie affiche son nom dans un Toast.
 *  - Partie 2 : un EditText + un Button (comportement défini sans android:onClick).
 *  - Partie 3 : le bouton lance ClicActivity via un Intent explicite avec un Extra.
 */
public class HelloworldActivity extends AppCompatActivity {

    public static final String EXTRA_TEXTE = "texte";

    private Button bAfficher;
    private EditText eSaisie;

    // ------------------------------------------------------------------
    // Cycle de vie
    // ------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        afficher("onCreate()");

        // Question 3 du TP : remplacer le layout XML par un TextView créé en Java.
        // Constat : l'interface devient uniquement le TextView "Hello, Android" ;
        // le champ de saisie et le bouton du layout XML disparaissent (et leurs
        // findViewById renverraient null). Décommentez pour tester :
        //
        // TextView tv = new TextView(this);
        // tv.setText("Hello, Android");
        // setContentView(tv);
        // return;

        setContentView(R.layout.activity_helloworld);

        // Initialisation des vues
        this.eSaisie = (EditText) this.findViewById(R.id.saisie);
        this.bAfficher = (Button) this.findViewById(R.id.boutonAfficher);

        // Comportement du bouton sans android:onClick
        this.bAfficher.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1) Toast "J'ai été cliqué !" (2 secondes)
                Toast helloToast = Toast.makeText(HelloworldActivity.this,
                        "J'ai été cliqué !", Toast.LENGTH_SHORT);
                helloToast.setGravity(Gravity.CENTER, 0, 0);
                helloToast.show();

                // 2) Passage à ClicActivity avec le texte saisi en Extra
                Intent myIntent = new Intent(HelloworldActivity.this, ClicActivity.class);
                myIntent.putExtra(EXTRA_TEXTE, eSaisie.getText().toString());
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        afficher("onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        afficher("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        afficher("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        afficher("onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        afficher("onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        afficher("onDestroy()");
    }

    private void afficher(String message) {
        Toast helloToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        helloToast.setGravity(Gravity.CENTER, 0, 0);
        helloToast.show();
    }
}
