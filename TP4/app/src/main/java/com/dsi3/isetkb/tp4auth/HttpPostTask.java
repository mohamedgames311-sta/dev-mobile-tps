package com.dsi3.isetkb.tp4auth;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Première approche : AsyncTask + HttpURLConnection.
 * La requête HTTP POST (corps JSON) est lancée en arrière-plan pour ne pas bloquer
 * le thread d'interface ; le résultat est rendu dans onPostExecute (thread UI).
 *
 * Remarque : on n'appelle PAS .get() après execute() (contrairement au sujet),
 * car get() bloque le thread UI et annule l'intérêt de l'AsyncTask.
 */
@SuppressWarnings("deprecation")
public class HttpPostTask extends AsyncTask<String, Void, String> {

    private final JSONObject body;
    private final Api.Callback callback;

    public HttpPostTask(JSONObject body, Api.Callback callback) {
        this.body = body;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... urls) {
        Log.i("Info", "launched");
        try {
            return httpPost(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // serveur injoignable / URL invalide
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.i("info", String.valueOf(result));
        Api.dispatch(result, callback);
    }

    private String httpPost(String myUrl) throws IOException {
        String result = "";
        URL url = new URL(myUrl);

        // 1. créer la HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(8000);
        conn.setDoOutput(true);

        // 2-3. ajouter le contenu JSON au corps de la requête POST
        setPostRequestContent(conn, body);

        // 4. exécuter la requête et lire la réponse
        InputStream is;
        try {
            is = conn.getInputStream();
        } catch (IOException e) {
            is = conn.getErrorStream(); // peut être null
        }
        Log.i("result code", Integer.toString(conn.getResponseCode()));

        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            result = sb.toString();
            Log.i("result", result);
        } else {
            Log.i("result", "Null");
        }
        conn.disconnect();

        // 5. retourner le message de réponse
        return result;
    }

    private void setPostRequestContent(HttpURLConnection conn, JSONObject jsonObject)
            throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }
}
