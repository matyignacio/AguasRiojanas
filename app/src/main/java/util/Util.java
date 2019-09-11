package util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * EN ESTA CLASE UTIL VAMOS A IR CREANDO LAS CONSTANTES O FUNCIONES QUE NOS SIRVAN PARA
 * HACER CODIGO LIMPIO DURANTE EL DESARROLLO DE NUESTRA APP
 */
public class Util {
    public static final String DATA_BASE = "u101901458_presi";
    public static final String HOST = "sql200.main-hosting.eu";
    public static final String USER = "u101901458_matia";
    public static final String CLAVE = "Miseignacio11";
    //    public static final String DATA_BASE = "c1370466_aguas_out";
//    public static final String HOST = "66.97.39.77";
//    public static final String USER = "c1370466_consu";
//    public static final String CLAVE = "Consulta963";
    public static final String PUERTO = "3306";
    public static final int EXITOSO = 1;
    public static final int ERROR = 0;
    private static final int MAXIMA_MEDICION = 20;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String font_primary_path = "font/font_primary.ttf";
    private static final String font_primary_bold_path = "font/font_primary_bold.ttf";

    public static void abrirActivity(Activity a, Class destino) {
        Intent intent = new Intent(a, destino);
        a.startActivity(intent);
        a.finish();
    }


    public static void abrirFragmento(Activity a, int layout, Fragment fragment) {
        //Paso 1: Obtener la instancia del administrador de fragmentos
        FragmentManager fragmentManager = a.getFragmentManager();

        //Paso 2: Crear una nueva transacción
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //Paso 3: Crear un nuevo fragmento y añadirlo
        transaction.add(layout, fragment);

        //Paso 4: Confirmar el cambio
        transaction.commit();
    }

    public static void siguienteFragmento(Activity a, int layout, Fragment fragmentActual, Fragment fragmentSiguiente) {
        a.getFragmentManager().beginTransaction().remove(fragmentActual).commit();
        FragmentManager fragmentManager = a.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(layout, fragmentSiguiente);
        transaction.commit();
    }

    public static void cerrarFragmento(Activity a, Fragment fragment) {
        // SON LOS MISMOS PASOS QUE PARA AGREGAR UN FRAGMENT
        // SOLAMENTE CAMBIA EL ADD POR EL REMOVE
        // EN ESTE CODIGO ESTA SIMPLIFICADO EN UNA SOLA INSTRUCCION.
        a.getFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public static void mostrarMensaje(Context c, String mensaje) {
        Toast.makeText(c, mensaje, Toast.LENGTH_SHORT).show();
        Log.e("MOSTRARMENSAJE:::", mensaje);
    }

    public static void mostrarMensajeLog(Context c, String mensaje) {
        Log.e("MOSTRARMENSAJE:::", mensaje);
    }

    public static String convertirFecha(Date date) {
        String fecha, hora;
        fecha = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
        hora = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        return fecha + "\na las " + hora;
    }

    public static int validarCampos(Activity a, ArrayList<EditText> inputs) {
        int i;
        for (i = 0; i < inputs.size(); i++) {
            if (inputs.get(i).getText().toString().equals("")) {
                mostrarMensaje(a, "Debe llenar el campo " + inputs.get(i).getHint().toString());
                inputs.get(i).requestFocus();
                return ERROR;
            }
        }
        return EXITOSO;
    }

    public static int validarPresion(Activity a, EditText etPresion) {
        if (Float.parseFloat(etPresion.getText().toString()) <= MAXIMA_MEDICION &&
                Float.parseFloat(etPresion.getText().toString()) > 0) {
            return EXITOSO;
        } else {
            mostrarMensaje(a, "La presion debe ser entre 0 mca y 20 mca");
            return ERROR;
        }
    }

    public static void setPrimaryFont(Context a, TextView tv) {
        Typeface TF = Typeface.createFromAsset(a.getAssets(), font_primary_path);
        tv.setTypeface(TF);
    }

    public static void setPrimaryFont(Context a, EditText et) {
        Typeface TF = Typeface.createFromAsset(a.getAssets(), font_primary_path);
        et.setTypeface(TF);
    }

    public static void setPrimaryFont(Context a, Button bt) {
        Typeface TF = Typeface.createFromAsset(a.getAssets(), font_primary_path);
        bt.setTypeface(TF);
    }

    public static void setPrimaryFontBold(Context a, TextView tv) {
        Typeface TF = Typeface.createFromAsset(a.getAssets(), font_primary_bold_path);
        tv.setTypeface(TF);
    }

    public static void setPrimaryFontBold(Context a, EditText et) {
        Typeface TF = Typeface.createFromAsset(a.getAssets(), font_primary_bold_path);
        et.setTypeface(TF);
    }

    public static void setPrimaryFontBold(Context a, Button bt) {
        Typeface TF = Typeface.createFromAsset(a.getAssets(), font_primary_bold_path);
        bt.setTypeface(TF);
    }

    public static void setPrimaryFontBold(Context a, CheckBox bt) {
        Typeface TF = Typeface.createFromAsset(a.getAssets(), font_primary_bold_path);
        bt.setTypeface(TF);
    }


    public static void setPreference(Context c, String nombreDato, int dato) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(nombreDato, dato);
        editor.apply();
    }

    public static void setPreference(Context c, String nombreDato, String dato) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(nombreDato, dato);
        editor.apply();
    }

    public static int getPreference(Context c, String nombreDato, int defaultValue) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(nombreDato, defaultValue);
    }

    public static String getPreference(Context c, String nombreDato, String defaultValue) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(nombreDato, defaultValue);
    }

    public static byte[] comprimirImagen(byte[] bytes) {
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, stream);
        bytes = stream.toByteArray();
        return bytes;
    }

    public static Bitmap rotarBitMap(Bitmap bmp, int angulo) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angulo);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    public static void checkConnection(Activity a, Callable<Void> method) {
        InternetDetector internetDetector;
        internetDetector = new InternetDetector(a);
        if (!internetDetector.checkMobileInternetConn()) {
            mostrarMensaje(a, "No hay conexion de red disponible.");
        } else {
            try {
                method.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}