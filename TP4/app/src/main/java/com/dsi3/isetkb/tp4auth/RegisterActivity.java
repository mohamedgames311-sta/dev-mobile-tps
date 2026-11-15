package com.dsi3.isetkb.tp4auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import org.json.JSONException;
import org.json.JSONObject;

/** Activité 1 : création de compte (nom, prénom, email, téléphone, username, mot de passe). */
public class RegisterActivity extends AppCompatActivity {

    private EditText editNom, editPrenom, editEmail, editTelephone, editUsername, editPassword;
    private TextView tvMsg;
    private Button btnRegister;
    private SwitchCompat switchVolley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editNom = (EditText) findViewById(R.id.editNom);
        editPrenom = (EditText) findViewById(R.id.editPrenom);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editTelephone = (EditText) findViewById(R.id.editTelephone);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        switchVolley = (SwitchCompat) findViewById(R.id.switchVolley);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sInscrire();
            }
        });
    }

    private void sInscrire() {
        String nom = editNom.getText().toString().trim();
        String prenom = editPrenom.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String telephone = editTelephone.getText().toString().trim();
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty()
                || username.isEmpty() || password.isEmpty()) {
            tvMsg.setText("Veuillez remplir tous les champs obligatoires");
            return;
        }

        JSONObject body = new JSONObject();
        try {
            body.put("nom", nom);
            body.put("prenom", prenom);
            body.put("email", email);
            body.put("telephone", telephone);
            body.put("username", username);
            body.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        tvMsg.setText("");
        btnRegister.setEnabled(false);

        Api.post(this, switchVolley.isChecked(), ServerConfig.REGISTER_URL, body, new Api.Callback() {
            @Override
            public void onResponse(JSONObject json) {
                btnRegister.setEnabled(true);
                if (json.optInt("success") == 1) {
                    Toast.makeText(RegisterActivity.this,
                            "Compte créé avec succès", Toast.LENGTH_LONG).show();
                    finish(); // retour à l'écran de connexion
                } else {
                    tvMsg.setText(json.optString("message", "Erreur lors de l'inscription"));
                }
            }

            @Override
            public void onError(String message) {
                btnRegister.setEnabled(true);
                tvMsg.setText(message);
            }
        });
    }
}
