package com.desarrollo.kuky.aguasriojanas.controlador.unidadfacturacion;

import android.app.Activity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.desarrollo.kuky.aguasriojanas.controlador.VolleySingleton;
import com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion.RelacionInmueble;
import com.desarrollo.kuky.aguasriojanas.ui.ErrorActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import static com.desarrollo.kuky.aguasriojanas.util.Errores.ERROR_PREFERENCE;
import static com.desarrollo.kuky.aguasriojanas.util.Util.MODULO_AGUAS_RIOJANAS;
import static com.desarrollo.kuky.aguasriojanas.util.Util.VOLLEY_HOST;
import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;
import static com.desarrollo.kuky.aguasriojanas.util.Util.displayProgressBar;
import static com.desarrollo.kuky.aguasriojanas.util.Util.lockProgressBar;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensajeLog;
import static com.desarrollo.kuky.aguasriojanas.util.Util.setPreference;

public class RelacionInmuebleControlador {

    void syncRelacionesInmuebles(Activity a, ProgressBar progressBar, TextView tvProgressBar, final ArrayList<RelacionInmueble> relacionesInmueblesGenerico, Callable<Void> method) {
        displayProgressBar(a, progressBar, tvProgressBar, "Obteniendo relaciones...");
        StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "relacion_inmueble_select.php", response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            Log.d("response", response);
            if (!response.equals("ERROR_ARRAY_VACIO")) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        RelacionInmueble relacionInmueble = new RelacionInmueble();
                        relacionInmueble.setId(jsonArray.getJSONObject(i).getInt("id"));
                        relacionInmueble.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                        relacionesInmueblesGenerico.add(relacionInmueble);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Y LLAMAMOS AL SIGUIENTE METODO
                try {
                    method.call();
                } catch (Exception e) {
                    mostrarMensajeLog(a, e.toString());
                }
            } else {
                Toast.makeText(a, "No existen relaciones de inmuebles", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            String problema = error.toString() + " en " + this.getClass().getSimpleName();
            setPreference(a, ERROR_PREFERENCE, problema);
            mostrarMensajeLog(a, problema);
            abrirActivity(a, ErrorActivity.class);
        });
        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(a).addToRequestQueue(request);
    }
}
