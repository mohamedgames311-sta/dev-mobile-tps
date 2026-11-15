package com.dsi3.isetkb.tp4auth;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Point d'entrée unique pour appeler le serveur, avec deux implémentations :
 *  - approche 1 : AsyncTask (HttpPostTask)
 *  - approche 2 : Google Volley (StringRequest)
 */
public final class Api {

    public interface Callback {
        /** Réponse JSON valide reçue du serveur. */
        void onResponse(JSONObject json);

        /** Erreur réseau ou réponse illisible. */
        void onError(String message);
    }

    private Api() {
    }

    public static void post(Context context, boolean useVolley, String url,
                            JSONObject body, Callback callback) {
        if (useVolley) {
            postWithVolley(context, url, body, callback);
        } else {
            new HttpPostTask(body, callback).execute(url);
        }
    }

    /** Approche 2 : Volley. */
    private static void postWithVolley(Context context, String url, JSONObject body,
                                       final Callback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        final String requestBody = body.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                        dispatch(response, callback);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                        callback.onError("Impossible de contacter le serveur");
                    }
                }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);
    }

    /** Transforme la chaîne reçue en JSONObject et appelle le bon callback. */
    static void dispatch(String result, Callback callback) {
        if (result == null || result.isEmpty()) {
            callback.onError("Impossible de contacter le serveur");
            return;
        }
        try {
            callback.onResponse(new JSONObject(result));
        } catch (JSONException e) {
            e.printStackTrace();
            callback.onError("Réponse invalide du serveur");
        }
    }
}
