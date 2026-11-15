package com.dsi3.isetkb.tp2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Seconde activité : affiche dans le TextView clicText la chaîne reçue
 * de HelloworldActivity (ou "Bonjour !" si rien n'a été saisi).
 */
public class ClicActivity extends AppCompatActivity {

    private TextView clicText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clic);

        clicText = (TextView) findViewById(R.id.clicText);

        String recu = getIntent().getStringExtra(HelloworldActivity.EXTRA_TEXTE);
        if (recu != null && !recu.trim().isEmpty()) {
            clicText.setText(recu);
        } else {
            clicText.setText("Bonjour !");
        }
    }
}
