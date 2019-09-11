package controlador;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.desarrollo.kuky.aguasriojanas.ui.DatosUtiles;
import com.desarrollo.kuky.aguasriojanas.ui.ErrorActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import objeto.DatoContacto;

import static util.Errores.ERROR_DATOS_UTILES;
import static util.Errores.ERROR_PREFERENCE;
import static util.Util.abrirActivity;
import static util.Util.checkConnection;
import static util.Util.mostrarMensaje;
import static util.Util.mostrarMensajeLog;
import static util.Util.setPreference;

public class InicioControlador {
    private static final String TAG = InicioControlador.class.getSimpleName();
    private ProgressDialog pDialog;

    @SuppressLint("StaticFieldLeak")
    private class AbrirDatosUtilesTask extends AsyncTask<String, Float, String> {
        String RETURN = "ERROR " + TAG;
        Activity a;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(a);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Cargando datos utiles...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        AbrirDatosUtilesTask(Activity a) {
            this.a = a;
        }

        @Override
        protected String doInBackground(String... strings) {
            Connection conn;
            PreparedStatement ps;
            ResultSet rs;
            try {
                conn = Conexion.GetConnection();
                String consultaSql = "SELECT * FROM datos_contacto";
                ps = conn.prepareStatement(consultaSql);
                ps.execute();
                rs = ps.getResultSet();
                if (rs.next()) {
                    SQLiteDatabase db = BaseHelper.getInstance(a).getWritableDatabase();
                    db.delete("datos_contacto", null, null);
                    ContentValues values = new ContentValues();
                    values.put("id", rs.getInt(1));
                    values.put("telefono", rs.getString(2));
                    values.put("web", rs.getString(3));
                    values.put("correo", rs.getString(4));
                    if (db.insert("datos_contacto", null, values) > 0) {
                        db.close();
                        RETURN = "";
                    }
                }
                rs.close();
                ps.close();
                conn.close();
                return RETURN;
            } catch (SQLException e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            if (s.equals("")) {
                abrirActivity(a, DatosUtiles.class);
            } else {
                setPreference(a, ERROR_PREFERENCE, ERROR_DATOS_UTILES);
                mostrarMensajeLog(a, ERROR_DATOS_UTILES);
                abrirActivity(a, ErrorActivity.class);
            }
        }
    }

    public void abrirDatosUtiles(Activity a) {
        checkConnection(a, () -> {
            try {
                AbrirDatosUtilesTask abrirDatosUtilesTask = new AbrirDatosUtilesTask(a);
                abrirDatosUtilesTask.execute();
            } catch (Exception e) {
                mostrarMensaje(a, e.toString());
            }
            return null;
        });
    }
}
