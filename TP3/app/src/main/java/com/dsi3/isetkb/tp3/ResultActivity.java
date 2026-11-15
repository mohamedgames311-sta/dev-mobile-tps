package com.dsi3.isetkb.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Deuxième activité :
 *  - affiche le résultat reçu par Extra,
 *  - bouton "back" : renvoie le résultat à ConversionActivity (setResult + finish).
 */
public class ResultActivity extends AppCompatActivity {

    private String valeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        valeur = getIntent().getStringExtra(ConversionActivity.EXTRA_VALEUR);
        if (valeur == null) valeur = "";

        TextView tValeur = (TextView) findViewById(R.id.t_valeur);
        tValeur.setText(valeur);

        ImageButton btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                retour(v);
            }
        });
    }

    /** Retour à l'activité principale avec le résultat. */
    public void retour(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(ConversionActivity.EXTRA_RESULTAT, valeur);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}