package com.desarrollo.kuky.aguasriojanas.controlador.unidadfacturacion;

import android.app.Activity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.desarrollo.kuky.aguasriojanas.controlador.VolleySingleton;
import com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion.Comprobante;
import com.desarrollo.kuky.aguasriojanas.ui.ErrorActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.desarrollo.kuky.aguasriojanas.util.Errores.ERROR_PREFERENCE;
import static com.desarrollo.kuky.aguasriojanas.util.Util.MODULO_AGUAS_RIOJANAS;
import static com.desarrollo.kuky.aguasriojanas.util.Util.MY_DEFAULT_TIMEOUT;
import static com.desarrollo.kuky.aguasriojanas.util.Util.VOLLEY_HOST;
import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;
import static com.desarrollo.kuky.aguasriojanas.util.Util.displayProgressBar;
import static com.desarrollo.kuky.aguasriojanas.util.Util.intentar;
import static com.desarrollo.kuky.aguasriojanas.util.Util.lockProgressBar;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensajeLog;
import static com.desarrollo.kuky.aguasriojanas.util.Util.setPreference;

class ComprobanteControlador {

    void buscarComprobantes(Activity a, ProgressBar progressBar, TextView tvProgressBar,
                            int unidad, final ArrayList<Comprobante> comprobantes,
                            Callable<Void> method) {
        displayProgressBar(a, progressBar, tvProgressBar, "Buscando comprobantes...");
        StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "comprobantes_select.php", response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            Log.d("response", response);
            if (!response.equals("ERROR_ARRAY_VACIO")) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Comprobante comprobante = new Comprobante();
                        comprobante.setTipoComprobante(jsonArray.getJSONObject(i).getString("tpo_com"));
                        comprobante.setPrenumeracion(jsonArray.getJSONObject(i).getString("pre_com"));
                        comprobante.setNumeroComprobante(jsonArray.getJSONObject(i).getInt("num_com"));
                        comprobante.setPeriodo(jsonArray.getJSONObject(i).getString("per_has"));
                        comprobante.setImporte(jsonArray.getJSONObject(i).getDouble("imp_tot"));
                        comprobante.setEstado(jsonArray.getJSONObject(i).getString("estado"));
                        comprobante.setVencimiento1(jsonArray.getJSONObject(i).getString("primer_vencimiento"));
                        int finalI = i;
                        intentar(() -> {
                            if (!jsonArray.getJSONObject(finalI).getString("segundo_vencimiento").equals("null")) {
                                comprobante.setVencimiento2(jsonArray.getJSONObject(finalI).getString("segundo_vencimiento"));
                            }
                            return null;
                        });
                        intentar(() -> {
                            comprobante.setRecargo(jsonArray.getJSONObject(finalI).getDouble("recargo"));
                            return null;
                        });
                        intentar(() -> {
                            comprobante.setRecargoIva(jsonArray.getJSONObject(finalI).getDouble("rec_iva"));
                            return null;
                        });
                        if (jsonArray.getJSONObject(finalI).getInt("imprimible") == 1) {
                            comprobante.setImprimible(true);
                        } else {
                            comprobante.setImprimible(false);
                        }
                        comprobantes.add(comprobante);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
