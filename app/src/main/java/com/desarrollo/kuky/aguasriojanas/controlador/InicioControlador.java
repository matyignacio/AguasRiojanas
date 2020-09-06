package com.desarrollo.kuky.aguasriojanas.controlador;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.desarrollo.kuky.aguasriojanas.controlador.datosutiles.DatosUtilesControlador;
import com.desarrollo.kuky.aguasriojanas.controlador.notificaciones.NotificacionControlador;
import com.desarrollo.kuky.aguasriojanas.controlador.reclamo.ReclamoControlador;
import com.desarrollo.kuky.aguasriojanas.controlador.reclamo.TipoReclamoControlador;
import com.desarrollo.kuky.aguasriojanas.controlador.reclamo.UbicacionReclamoControlador;
import com.desarrollo.kuky.aguasriojanas.controlador.unidadfacturacion.UnidadControlador;
import com.desarrollo.kuky.aguasriojanas.ui.ErrorActivity;
import com.desarrollo.kuky.aguasriojanas.ui.InicioActivity;
import com.desarrollo.kuky.aguasriojanas.ui.datosutiles.DatosUtilesActivity;
import com.desarrollo.kuky.aguasriojanas.ui.notificacion.NotificacionActivity;
import com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity;
import com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.UnidadFacturacionActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.desarrollo.kuky.aguasriojanas.ui.login.LoginActivity.mAuth;
import static com.desarrollo.kuky.aguasriojanas.ui.notificacion.NotificacionActivity.notificaciones;
import static com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity.reclamos;
import static com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity.tiposReclamos;
import static com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity.ubicacionReclamos;
import static com.desarrollo.kuky.aguasriojanas.util.Errores.ERROR_PREFERENCE;
import static com.desarrollo.kuky.aguasriojanas.util.Util.MODULO_AGUAS_RIOJANAS;
import static com.desarrollo.kuky.aguasriojanas.util.Util.VOLLEY_HOST;
import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;
import static com.desarrollo.kuky.aguasriojanas.util.Util.displayProgressBar;
import static com.desarrollo.kuky.aguasriojanas.util.Util.lockProgressBar;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensaje;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensajeLog;
import static com.desarrollo.kuky.aguasriojanas.util.Util.setPreference;

public class InicioControlador {
    public String token = "";
    private static final String TAG = "InicioControlador";
    private DatosUtilesControlador datosUtilesControlador = new DatosUtilesControlador();

    public void abrirInicioActivity(Activity a) {
        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }
                    // Get new Instance ID token
                    token = Objects.requireNonNull(task.getResult()).getToken();
                    StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "fcm_insert.php", response -> {
                        Log.e(TAG, response);
                        try {
                            if (response.equals("[\"OK\"]")) {
                                Log.d("RESPUESTASERVER", "SUCCESS");
                                // SI SALE BIEN PASAMOS A LA SIGUIENTE REQUEST
                                try {
                                    abrirActivity(a, InicioActivity.class);
                                } catch (Exception e) {
                                    mostrarMensajeLog(a, e.toString());
                                }
                            } else if (response.equals("[\"FAIL\"]")) {
                                Log.e("RESPUESTASERVER", "FAIL");
                                mostrarMensaje(a, "Hubo un problema con el token de sesión.");
                            } else {
                                Log.e("RESPUESTASERVER", "[\"ERROR\"]");
                            }
                        } catch (Exception e) {
                            Log.e("RESPUESTASERVER", e.toString());
                        }
                    }, error -> {
                        String problema = error.toString() + " en " + this.getClass().getSimpleName();
                        setPreference(a, ERROR_PREFERENCE, problema);
                        mostrarMensajeLog(a, problema);
                        abrirActivity(a, ErrorActivity.class);
                    }) {
                        //Pass Your Parameters here
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("fcm_token", token);
                            params.put("id_usuario", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                            return params;
                        }
                    };
                    // Access the RequestQueue through your singleton class.
                    VolleySingleton.getInstance(a).addToRequestQueue(request);
                });
        // [END retrieve_current_token]
    }

    public void abrirInicioActivity(Activity a, ProgressBar progressBar, TextView tvProgressBar) {
        displayProgressBar(a, progressBar, tvProgressBar, "Cargando...");
        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "getInstanceId failed", task.getException());
                        return;
                    }
                    // Get new Instance ID token
                    token = Objects.requireNonNull(task.getResult()).getToken();
                    StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "fcm_insert.php", response -> {
                        lockProgressBar(a, progressBar, tvProgressBar);
                        Log.e(TAG, response);
                        try {
                            if (response.equals("[\"OK\"]")) {
                                Log.d("RESPUESTASERVER", "SUCCESS");
                                // SI SALE BIEN PASAMOS A LA SIGUIENTE REQUEST
                                try {
                                    abrirActivity(a, InicioActivity.class);
                                } catch (Exception e) {
                                    mostrarMensajeLog(a, e.toString());
                                }
                            } else if (response.equals("[\"FAIL\"]")) {
                                Log.e("RESPUESTASERVER", "FAIL");
                                mostrarMensaje(a, "Hubo un problema con el token de sesión.");
                            } else {
                                Log.e("RESPUESTASERVER", "[\"ERROR\"]");
                            }
                        } catch (Exception e) {
                            Log.e("RESPUESTASERVER", e.toString());
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
                            params.put("fcm_token", token);
                            params.put("id_usuario", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                            return params;
                        }
                    };
                    // Access the RequestQueue through your singleton class.
                    VolleySingleton.getInstance(a).addToRequestQueue(request);
                });
        // [END retrieve_current_token]
    }

    public void abrirDatosUtilesActivity(Activity a, ProgressBar progressBar, TextView tvProgressBar) {
        datosUtilesControlador.syncDatosContacto(a, progressBar, tvProgressBar, () -> {
            datosUtilesControlador.syncOficinasComerciales(a, progressBar, tvProgressBar, () -> {
                datosUtilesControlador.syncLugaresPago(a, progressBar, tvProgressBar, () -> {
                    abrirActivity(a, DatosUtilesActivity.class);
                    return null;
                });
                return null;
            });
            return null;
        });
    }

    public void abrirUnidadFacturacionActivity(Activity a, ProgressBar progressBar, TextView tvProgressBar) {
        UnidadControlador unidadControlador = new UnidadControlador();
        UnidadFacturacionActivity.unidades = new ArrayList<>();
        unidadControlador.buscarUnidades(a, progressBar, tvProgressBar,
                UnidadFacturacionActivity.unidades, () -> {
                    abrirActivity(a, UnidadFacturacionActivity.class);
                    return null;
                });
    }

    public void abrirNotificacionActivity(Activity a, ProgressBar progressBar, TextView tvProgressBar) {
        notificaciones = new ArrayList<>();
        NotificacionControlador notificacionControlador = new NotificacionControlador();
        notificacionControlador.buscarNotificaciones(a, progressBar, tvProgressBar,
                notificaciones, () -> {
                    if (notificaciones.size() > 0) {
                        abrirActivity(a, NotificacionActivity.class);
                    } else {
                        mostrarMensaje(a, "Ud no tiene notificaciones para ver");
                    }
                    return null;
                });
    }

    public void abrirReclamoActivity(Activity a, ProgressBar progressBar, TextView tvProgressBar) {
        ReclamosActivity.unidades = new ArrayList<>();
        tiposReclamos = new ArrayList<>();
        ubicacionReclamos = new ArrayList<>();
        reclamos = new ArrayList<>();
        UnidadControlador unidadControlador = new UnidadControlador();
        ReclamoControlador reclamoControlador = new ReclamoControlador();
        TipoReclamoControlador tipoReclamoControlador = new TipoReclamoControlador();
        UbicacionReclamoControlador ubicacionReclamoControlador = new UbicacionReclamoControlador();
        unidadControlador.buscarUnidades(a, progressBar, tvProgressBar,
                ReclamosActivity.unidades, () -> {
                    if (ReclamosActivity.unidades.size() > 0) {
                        tipoReclamoControlador.buscarTiposReclamo(a, progressBar, tvProgressBar, tiposReclamos, () -> {
                            if (tiposReclamos.size() > 0) {
                                ubicacionReclamoControlador.buscarUbicacionesReclamo(a, progressBar, tvProgressBar,
                                        ubicacionReclamos, () -> {
                                            if (ubicacionReclamos.size() > 0) {
                                                reclamoControlador.buscarReclamos(a, progressBar, tvProgressBar,
                                                        reclamos, () -> {
                                                            abrirActivity(a, ReclamosActivity.class);
                                                            return null;
                                                        });
                                            }
                                            return null;
                                        });
                            }
                            return null;
                        });
                    } else {
                        mostrarMensaje(a, "Para poder realizar reclamos, debe asignar al menos una unidad.");
                    }
                    return null;
                });
    }

    public void actualizarTablas(Activity a) {
        try {
            SQLiteDatabase db = BaseHelper.getInstance(a).getWritableDatabase();
            /** DROPEAMOS PARA QUE SE CREEN */
            db.execSQL(BaseHelper.getInstance(a).dropTable("lugares_pago"));
            db.execSQL(BaseHelper.getInstance(a).dropTable("lugares_pago"));
            db.execSQL(BaseHelper.getInstance(a).dropTable("datos_contacto"));
            /** Y AHORA LAS VOLVEMOS A CREAR CON FORMATO DEFINITIVO */
            db.execSQL(BaseHelper.getInstance(a).getSqlTablaDatosContacto());
            db.execSQL(BaseHelper.getInstance(a).getSqlTablaLugaresPago());
            db.execSQL(BaseHelper.getInstance(a).getSqlTablaOficinasComerciales());
            db.close();
        } catch (Exception e) {
            mostrarMensaje(a, e.toString());
        }
    }
}
