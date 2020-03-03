package com.desarrollo.kuky.aguasriojanas.controlador.reclamo;

import android.app.Activity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.desarrollo.kuky.aguasriojanas.controlador.VolleySingleton;
import com.desarrollo.kuky.aguasriojanas.objeto.reclamo.TipoReclamo;
import com.desarrollo.kuky.aguasriojanas.ui.ErrorActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.desarrollo.kuky.aguasriojanas.util.Errores.ERROR_PREFERENCE;
import static com.desarrollo.kuky.aguasriojanas.util.Util.MODULO_AGUAS_RIOJANAS;
import static com.desarrollo.kuky.aguasriojanas.util.Util.MY_DEFAULT_TIMEOUT;
import static com.desarrollo.kuky.aguasriojanas.util.Util.VOLLEY_HOST;
import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;
import static com.desarrollo.kuky.aguasriojanas.util.Util.displayProgressBar;
import static com.desarrollo.kuky.aguasriojanas.util.Util.lockProgressBar;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensajeLog;
import static com.desarrollo.kuky.aguasriojanas.util.Util.setPreference;

public class TipoReclamoControlador {

    public void buscarTiposReclamo(Activity a, ProgressBar progressBar, TextView tvProgressBar,
                                   ArrayList<TipoReclamo> tipoReclamos, Callable<Void> method) {
        displayProgressBar(a, progressBar, tvProgressBar, "Cargando tipos de reclamos...");
        StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "reclamo_tipo_select.php", response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            Log.d("response", response);
            if (!response.equals("ERROR_ARRAY_VACIO")) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TipoReclamo tipoReclamo = new TipoReclamo();
                        tipoReclamo.setId(Integer.parseInt(jsonArray.getJSONObject(i).getString("id")));
                        tipoReclamo.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                        tipoReclamos.add(tipoReclamo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                mostrarMensajeLog(a, "Ud aun no asigno unidades");
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
        });
        // Establecer una política de reintentos en mi petición Volley mediante el método setRetryPolicy
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(a).addToRequestQueue(request);
    }
}
