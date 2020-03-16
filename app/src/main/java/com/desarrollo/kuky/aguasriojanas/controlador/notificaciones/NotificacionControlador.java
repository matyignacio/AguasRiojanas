package com.desarrollo.kuky.aguasriojanas.controlador.notificaciones;

import android.app.Activity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.desarrollo.kuky.aguasriojanas.controlador.VolleySingleton;
import com.desarrollo.kuky.aguasriojanas.objeto.notificaciones.Notificacion;
import com.desarrollo.kuky.aguasriojanas.ui.ErrorActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import static com.desarrollo.kuky.aguasriojanas.ui.login.LoginActivity.mAuth;
import static com.desarrollo.kuky.aguasriojanas.util.Errores.ERROR_PREFERENCE;
import static com.desarrollo.kuky.aguasriojanas.util.Util.MODULO_AGUAS_RIOJANAS;
import static com.desarrollo.kuky.aguasriojanas.util.Util.MY_DEFAULT_TIMEOUT;
import static com.desarrollo.kuky.aguasriojanas.util.Util.VOLLEY_HOST;
import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;
import static com.desarrollo.kuky.aguasriojanas.util.Util.displayProgressBar;
import static com.desarrollo.kuky.aguasriojanas.util.Util.lockProgressBar;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensajeLog;
import static com.desarrollo.kuky.aguasriojanas.util.Util.setPreference;

public class NotificacionControlador {

    public void buscarNotificaciones(Activity a, ProgressBar progressBar, TextView tvProgressBar,
                                     ArrayList<Notificacion> notificaciones, Callable<Void> method) {
        displayProgressBar(a, progressBar, tvProgressBar, "Buscando notificaciones...");
        StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "notificacion_select.php", response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            Log.d("response", response);
            if (!response.equals("ERROR_ARRAY_VACIO")) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Notificacion notificacion = new Notificacion();
                        notificacion.setResumen(jsonArray.getJSONObject(i).getString("resumen"));
                        notificacion.setFecha(Timestamp.valueOf(jsonArray.getJSONObject(i).getString("fecha")));
                        notificaciones.add(notificacion);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // Y AL FINAL EJECUTAMOS LA SIGUIENTE REQUEST
            try {
                method.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            String problema = error.toString() + " en " + this.getClass().getSimpleName();
            setPreference(a, ERROR_PREFERENCE, problema);
            mostrarMensajeLog(a, problema);
            abrirActivity(a, ErrorActivity.class);
        }) {
            //Pass Your Parameters here
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_usuario", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                return params;
            }
        };
        // Establecer una política de reintentos en mi petición Volley mediante el método setRetryPolicy
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(a).addToRequestQueue(request);
    }
}
