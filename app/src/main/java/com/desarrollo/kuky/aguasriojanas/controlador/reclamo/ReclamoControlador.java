package com.desarrollo.kuky.aguasriojanas.controlador.reclamo;

import android.app.Activity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.desarrollo.kuky.aguasriojanas.controlador.VolleySingleton;
import com.desarrollo.kuky.aguasriojanas.objeto.reclamo.Reclamo;
import com.desarrollo.kuky.aguasriojanas.ui.ErrorActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensaje;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensajeLog;
import static com.desarrollo.kuky.aguasriojanas.util.Util.setPreference;

public class ReclamoControlador {

    public void buscarReclamos(Activity a, ProgressBar progressBar, TextView tvProgressBar,
                               ArrayList<Reclamo> reclamos, Callable<Void> method) {
        displayProgressBar(a, progressBar, tvProgressBar, "Buscando reclamos...");
        StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "reclamo_select.php", response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            Log.d("response", response);
            if (!response.equals("ERROR_ARRAY_VACIO")) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Reclamo reclamo = new Reclamo();
                        reclamo.setId(Integer.parseInt(jsonArray.getJSONObject(i).getString("id")));
                        reclamo.setIdTipoReclamo(Integer.parseInt(jsonArray.getJSONObject(i).getString("id_tipo_reclamo")));
                        reclamo.setIdUbicacionReclamo(Integer.parseInt(jsonArray.getJSONObject(i).getString("id_ubicacion_reclamo")));
                        reclamo.setDireccion(jsonArray.getJSONObject(i).getString("direccion"));
                        reclamo.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
//                        reclamo.setFoto(jsonArray.getJSONObject(i).getString("foto"));
                        reclamo.setFecha(Timestamp.valueOf(jsonArray.getJSONObject(i).getString("fecha")));
                        if (Integer.parseInt(jsonArray.getJSONObject(i).getString("estado")) == 1) {
                            reclamo.setFinalizado(true);
                        } else {
                            reclamo.setFinalizado(false);
                        }
                        reclamo.setUnidad(jsonArray.getJSONObject(i).getString("unidad"));
                        reclamo.setIdUsuario(jsonArray.getJSONObject(i).getString("id_usuario"));
                        reclamos.add(reclamo);
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

    public void insertReclamo(Activity a, ProgressBar progressBar, TextView tvProgressBar,
                              Reclamo reclamo, Callable<Void> method) {
        JSONArray reclamoInsert = new JSONArray();
        displayProgressBar(a, progressBar, tvProgressBar, "Enviando reclamo...");
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id_tipo_reclamo", reclamo.getIdTipoReclamo());
            jsonObject.put("id_ubicacion_reclamo", reclamo.getIdUbicacionReclamo());
            jsonObject.put("direccion", reclamo.getDireccion());
            jsonObject.put("descripcion", reclamo.getDescripcion());
            jsonObject.put("foto", reclamo.getFoto());
            jsonObject.put("unidad", reclamo.getUnidad());
            jsonObject.put("id_usuario", reclamo.getIdUsuario());
            reclamoInsert.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "reclamo_insert.php", reclamoInsert, response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            try {
                if (response.getJSONObject(0).getString("status").equals("SUCCESS")) {
                    Log.d("RESPUESTASERVER", "SUCCESS");
                    // SI SALE BIEN PASAMOS A LA SIGUIENTE REQUEST
                    try {
                        method.call();
                    } catch (Exception e) {
                        mostrarMensajeLog(a, e.toString());
                    }
                } else if (response.getJSONObject(0).getString("status").equals("FAIL")) {
                    Log.e("RESPUESTASERVER", "FAIL");
                    mostrarMensaje(a, "No se pudo insertar el reclamo");
                } else {
                    Log.e("RESPUESTASERVER", "ERROR");
                }
            } catch (JSONException e) {
                Log.e("RESPUESTASERVER", e.toString());
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
