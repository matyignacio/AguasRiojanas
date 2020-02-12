package com.desarrollo.kuky.aguasriojanas.controlador.unidadfacturacion;

import android.app.Activity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.desarrollo.kuky.aguasriojanas.controlador.VolleySingleton;
import com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion.Unidad;
import com.desarrollo.kuky.aguasriojanas.ui.ErrorActivity;

import org.json.JSONArray;
import org.json.JSONException;

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

public class UnidadControlador {

    public void buscarUnidades(Activity a, ProgressBar progressBar, TextView tvProgressBar,
                               ArrayList<Unidad> unidades, Callable<Void> method) {
        displayProgressBar(a, progressBar, tvProgressBar, "Buscando unidades...");
        StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "unidad_select.php", response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            Log.d("response", response);
            if (!response.equals("ERROR_ARRAY_VACIO")) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Unidad unidad = new Unidad();
                        unidad.setUnidad(Integer.parseInt(jsonArray.getJSONObject(i).getString("unidad")));
                        unidad.setNombre(jsonArray.getJSONObject(i).getString("razon"));
                        unidad.setCalle(jsonArray.getJSONObject(i).getString("calle"));
                        unidad.setNumeroCasa(Integer.parseInt(jsonArray.getJSONObject(i).getString("numero")));
                        unidades.add(unidad);
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

    public void buscarUnidadAguas(Activity a, ProgressBar progressBar, TextView tvProgressBar,
                                  int unidad, int numeroComprobante, Callable<Void> method) {
        displayProgressBar(a, progressBar, tvProgressBar, "Buscando unidad...");
        StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "dar_alta_unidad_aguas.php", response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            Log.d("response", response);
            if (!response.equals("ERROR_ARRAY_VACIO")) {
                // Y AL FINAL EJECUTAMOS LA SIGUIENTE REQUEST
                try {
                    method.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                mostrarMensajeLog(a, "No se encontro ninguna unidad");
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
                params.put("unidad", String.valueOf(unidad));
                params.put("num_com", String.valueOf(numeroComprobante));
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

    public void buscarUnidadEdelar(Activity a, ProgressBar progressBar, TextView tvProgressBar,
                                   int nis, int usuario, Callable<Void> method) {
        displayProgressBar(a, progressBar, tvProgressBar, "Buscando unidad...");
        StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "dar_alta_unidad_edelar.php", response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            Log.d("response", response);
            if (!response.equals("ERROR_ARRAY_VACIO")) {
                // Y AL FINAL EJECUTAMOS LA SIGUIENTE REQUEST
                try {
                    method.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                mostrarMensajeLog(a, "No se encontro ninguna unidad");
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
                params.put("nis", String.valueOf(nis));
                params.put("usuario", String.valueOf(usuario));
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

    public void eliminarUnidad(Activity a, ProgressBar progressBar, TextView tvProgressBar,
                               int unidad, Callable<Void> method) {
        displayProgressBar(a, progressBar, tvProgressBar, "Eliminando unidad...");
        StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "baja_unidad.php", response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            if (!response.equals("ERROR_ARRAY_VACIO")) {
                // Y AL FINAL EJECUTAMOS LA SIGUIENTE REQUEST
                try {
                    method.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                mostrarMensaje(a, "No se pudo eliminar la unidad");
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
                params.put("unidad", String.valueOf(unidad));
                return params;
            }
        };
        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(a).addToRequestQueue(request);
    }

}
