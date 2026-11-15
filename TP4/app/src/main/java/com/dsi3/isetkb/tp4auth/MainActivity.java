package com.dsi3.isetkb.tp4auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import org.json.JSONException;
import org.json.JSONObject;

/** Écran d'authentification (Figure 1 du TP). */
public class MainActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editPassword;
    private TextView tvMsg;
    private Button btnConnect;
    private SwitchCompat switchVolley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        btnConnect = (Button) findViewById(R.id.btnConnect);
        switchVolley = (SwitchCompat) findViewById(R.id.switchVolley);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seConnecter();
            }
        });

        ((Button) findViewById(R.id.btnCancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editName.setText("");
                editPassword.setText("");
                tvMsg.setText("");
            }
        });

        ((Button) findViewById(R.id.btnRegister)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }

    private void seConnecter() {
        String username = editName.getText().toString().trim();
        String password = editPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            tvMsg.setText("Veuillez saisir le nom d'utilisateur et le mot de passe");
            return;
        }

        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
            body.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        tvMsg.setText("");
        btnConnect.setEnabled(false);

        Api.post(this, switchVolley.isChecked(), ServerConfig.LOGIN_URL, body, new Api.Callback() {
            @Override
            public void onResponse(JSONObject json) {
                btnConnect.setEnabled(true);
                if (json.has("success")) {
                    if (json.optInt("success") == 1) {
                        startActivity(new Intent(MainActivity.this, AcceuilAct.class));
                        finish();
                    } else {
                        tvMsg.setText("Please verify username and password");
                    }
                } else {
                    tvMsg.setText("Cannot connect to database!");
                }
            }

            @Override
            public void onError(String message) {
                btnConnect.setEnabled(true);
                tvMsg.setText(message);
            }
        });
    }
}
