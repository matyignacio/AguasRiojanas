package com.desarrollo.kuky.aguasriojanas.controlador;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.desarrollo.kuky.aguasriojanas.objeto.datosutiles.DatoContacto;
import com.desarrollo.kuky.aguasriojanas.objeto.datosutiles.LugarPago;
import com.desarrollo.kuky.aguasriojanas.objeto.datosutiles.OficinaComercial;
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

public class DatosUtilesControlador {
    private static final String TAG = DatosUtilesControlador.class.getSimpleName();

    void syncDatosContacto(Activity a, ProgressBar progressBar, TextView tvProgressBar, Callable<Void> method) {
        displayProgressBar(a, progressBar, tvProgressBar, "Obteniendo datos de contacto...");
        StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "datos_contacto_select.php", response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            Log.d("response", response);
            if (!response.equals("ERROR_ARRAY_VACIO")) {
                SQLiteDatabase db = BaseHelper.getInstance(a).getWritableDatabase();
                /* LIMPIAMOS LA TABLA */
                db.execSQL("DELETE FROM datos_contacto");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String sql = "INSERT INTO `datos_contacto` " +
                                " VALUES " +
                                "('" + jsonArray.getJSONObject(i).getInt("id") + "','" + // id
                                jsonArray.getJSONObject(i).getString("telefono") + "','" +
                                jsonArray.getJSONObject(i).getString("web") + "','" +
                                jsonArray.getJSONObject(i).getString("correo") + "');";
                        db.execSQL(sql);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                db.close();
                // Y PASAMOS A LA SIGUIENTE REQUEST
                try {
                    method.call();
                } catch (Exception e) {
                    mostrarMensajeLog(a, e.toString());
                }
            } else {
                Toast.makeText(a, "No existe datos de contacto", Toast.LENGTH_SHORT).show();
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

    void syncOficinasComerciales(Activity a, ProgressBar progressBar, TextView tvProgressBar, Callable<Void> method) {
        displayProgressBar(a, progressBar, tvProgressBar, "Obteniendo oficinas comerciales...");
        StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "oficinas_comerciales_select.php", response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            Log.d("response", response);
            if (!response.equals("ERROR_ARRAY_VACIO")) {
                SQLiteDatabase db = BaseHelper.getInstance(a).getWritableDatabase();
                /* LIMPIAMOS LA TABLA */
                db.execSQL("DELETE FROM oficinas_comerciales");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String sql = "INSERT INTO `oficinas_comerciales` " +
                                " VALUES " +
                                "('" + jsonArray.getJSONObject(i).getInt("id") + "','" + // id
                                jsonArray.getJSONObject(i).getString("localidad") + "','" +
                                jsonArray.getJSONObject(i).getString("direccion") + "','" +
                                jsonArray.getJSONObject(i).getString("horario_desde") + "','" +
                                jsonArray.getJSONObject(i).getString("horario_hasta") + "');";
                        db.execSQL(sql);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                db.close();
                // Y PASAMOS A LA SIGUIENTE REQUEST
                try {
                    method.call();
                } catch (Exception e) {
                    mostrarMensajeLog(a, e.toString());
                }
            } else {
                Toast.makeText(a, "No hay oficinas comerciales", Toast.LENGTH_SHORT).show();
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

    void syncLugaresPago(Activity a, ProgressBar progressBar, TextView tvProgressBar, Callable<Void> method) {
        displayProgressBar(a, progressBar, tvProgressBar, "Obteniendo lugares de pago...");
        StringRequest request = new StringRequest(Request.Method.POST, VOLLEY_HOST + MODULO_AGUAS_RIOJANAS + "lugares_pago_select.php", response -> {
            lockProgressBar(a, progressBar, tvProgressBar);
            Log.d("response", response);
            if (!response.equals("ERROR_ARRAY_VACIO")) {
                SQLiteDatabase db = BaseHelper.getInstance(a).getWritableDatabase();
                /* LIMPIAMOS LA TABLA */
                db.execSQL("DELETE FROM lugares_pago");
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String sql = "INSERT INTO `lugares_pago` " +
                                " VALUES " +
                                "('" + jsonArray.getJSONObject(i).getInt("id") + "','" + // id
                                jsonArray.getJSONObject(i).getString("titulo") + "','" +
                                jsonArray.getJSONObject(i).getString("descripcion") + "');";
                        db.execSQL(sql);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                db.close();
                // Y PASAMOS A LA SIGUIENTE REQUEST
                try {
                    method.call();
                } catch (Exception e) {
                    mostrarMensajeLog(a, e.toString());
                }
            } else {
                Toast.makeText(a, "No hay lugares de pago", Toast.LENGTH_SHORT).show();
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

    public DatoContacto getDatosContacto(Activity a) {
        DatoContacto datoContacto = new DatoContacto();
        SQLiteDatabase db = BaseHelper.getInstance(a).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT telefono, web, correo " +
                " FROM datos_contacto ", null);
        while (c.moveToNext()) {
            datoContacto.setTelefono(c.getString(0));
            datoContacto.setWeb(c.getString(1));
            datoContacto.setCorreo(c.getString(2));
        }
        c.close();
        db.close();
        return datoContacto;
    }

    public ArrayList<OficinaComercial> getOficinasComerciales(Activity a) {
        ArrayList<OficinaComercial> oficinas = new ArrayList<>();
        SQLiteDatabase db = BaseHelper.getInstance(a).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT localidad, direccion, horario_desde, horario_hasta" +
                " FROM oficinas_comerciales ", null);
        while (c.moveToNext()) {
            OficinaComercial oficinaComercial = new OficinaComercial();
            oficinaComercial.setLocalidad(c.getString(0));
            oficinaComercial.setDireccion(c.getString(1));
            oficinaComercial.setHorarioDesde(c.getString(2));
            oficinaComercial.setHorarioHasta(c.getString(3));
            oficinas.add(oficinaComercial);
        }
        c.close();
        db.close();
        return oficinas;
    }

    public ArrayList<LugarPago> getLugaresPago(Activity a) {
        ArrayList<LugarPago> lugares = new ArrayList<>();
        SQLiteDatabase db = BaseHelper.getInstance(a).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT titulo, descripcion" +
                " FROM lugares_pago ", null);
        while (c.moveToNext()) {
            LugarPago lugarPago = new LugarPago();
            lugarPago.setTitulo(c.getString(0));
            lugarPago.setDescripcion(c.getString(1));
            lugares.add(lugarPago);
        }
        c.close();
        db.close();
        return lugares;
    }
}
