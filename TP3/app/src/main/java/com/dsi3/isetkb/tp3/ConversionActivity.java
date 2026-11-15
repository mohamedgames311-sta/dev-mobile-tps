package com.dsi3.isetkb.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * TP3 - Partie 1 : convertisseur de devises (Méthode 3 : l'activité implémente OnClickListener).
 * Partie 2 : le résultat est affiché dans ResultActivity (intent explicite avec résultat).
 */
public class ConversionActivity extends AppCompatActivity implements OnClickListener {

    /** Taux utilisé dans le TP (10 Novembre 2016) : 1 € = 2.4711 TND */
    private static final double TAUX_EURO_DINAR = 2.4711;

    static final int REQUEST_CODE = 1;
    static final String EXTRA_VALEUR = "valeur";
    static final String EXTRA_RESULTAT = "resultat";

    private Button btnConvertir;
    private EditText eEntry;
    private TextView tResultat;
    private RadioButton rEuroDinar;
    private RadioButton rDinarEuro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        this.btnConvertir = (Button) this.findViewById(R.id.btn_convertir);
        this.eEntry = (EditText) this.findViewById(R.id.e_entry);
        this.tResultat = (TextView) this.findViewById(R.id.t_resultat);
        this.rEuroDinar = (RadioButton) this.findViewById(R.id.r_euro_dinar);
        this.rDinarEuro = (RadioButton) this.findViewById(R.id.r_dinar_euro);

        // L'activité courante est l'écouteur du clic
        this.btnConvertir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_convertir) {
            String saisie = eEntry.getText().toString().trim().replace(',', '.');
            if (saisie.isEmpty()) {
                eEntry.setError("Veuillez saisir un montant");
                return;
            }

            double montant;
            try {
                montant = Double.parseDouble(saisie);
            } catch (NumberFormatException e) {
                eEntry.setError("Montant invalide");
                return;
            }

            double resultat = rEuroDinar.isChecked()
                    ? montant * TAUX_EURO_DINAR
                    : montant / TAUX_EURO_DINAR;
            String texte = formater(resultat);

            // Intent explicite avec résultat : on ouvre ResultActivity
            Intent i = new Intent(ConversionActivity.this, ResultActivity.class);
            i.putExtra(EXTRA_VALEUR, texte);
            startActivityForResult(i, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                String res = data.getStringExtra(EXTRA_RESULTAT);
                tResultat.setText(res);
                Toast.makeText(this, "Resultat = " + res, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Pas de Resultat !", Toast.LENGTH_LONG).show();
            }
        }
    }

    /** Arrondi à 4 décimales, sans zéros inutiles (4 € -> 9.8844). */
    private String formater(double valeur) {
        return new BigDecimal(valeur)
                .setScale(4, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString();
    }
}
