package com.desarrollo.kuky.aguasriojanas.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollo.kuky.aguasriojanas.R;
import com.google.android.gms.common.SignInButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import androidx.appcompat.app.AlertDialog;

/**
 * EN ESTA CLASE UTIL VAMOS A IR CREANDO LAS CONSTANTES O FUNCIONES QUE NOS SIRVAN PARA
 * HACER CODIGO LIMPIO DURANTE EL DESARROLLO DE NUESTRA APP
 */
public class Util {
    /**
     * CONEXION
     ********************************************/
//    public static final String VOLLEY_HOST = "http://volley.aguasriojanas.com.ar/presionaguas/";
    public static final String VOLLEY_HOST = "http://volley.aguasriojanas.com.ar/presionaguas-debug/";
    public static final String MODULO_AGUAS_RIOJANAS = "aguasriojanas/";
    public static final int EXITOSO = 1;
    public static final String EXITO = "EXITO";
    private static final int ERROR = 0;
    public static final int MY_DEFAULT_TIMEOUT = 60000;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String font_primary_path = "font/font_primary.ttf";
    private static final String font_primary_bold_path = "font/font_primary_bold.ttf";
    private static final String POSICION_SELECCIONADA = "posicion_seleccionada_spinner";

    public static void abrirActivity(Activity a, Class destino) {
        Intent intent = new Intent(a, destino);
        a.startActivity(intent);
        a.finish();
    }

    public static void abrirActivityWithBundle(Activity a, Class destino, Bundle mBundle) {
        /** COMO SE USA
         *             Bundle mBundle = new Bundle();
         *             mBundle.putInt("key", value); (Pueden ser ints, Strings, etc)
         *             abrirActivityWithBundle(this, ActivityDestino.class, mBundle);
         * */
        Intent intent = new Intent(a, destino);
        intent.putExtras(mBundle);
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
        String fecha;
        fecha = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
        return fecha;
    }

    public static int validarCampos(Activity a, EditText... inputs) {
        int i;
        for (i = 0; i < inputs.length; i++) {
            if (inputs[i].getText().toString().equals("")) {
                mostrarMensaje(a, "Debe llenar el campo " + inputs[i].getHint().toString());
                inputs[i].requestFocus();
                return ERROR;
            }
        }
        return EXITOSO;
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
        bmp.compress(Bitmap.CompressFormat.PNG, 20, stream);
        bytes = stream.toByteArray();
        return bytes;
    }

    public static String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bytes = stream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static Bitmap rotarBitMap(Bitmap bmp, int angulo) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angulo);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    public static void customizeGooglePlusButton(SignInButton signInButton, String mensaje, Context c) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(mensaje);
                tv.setTextColor(c.getResources().getColor(R.color.colorPrimaryDark));
                tv.setBackgroundResource(R.color.textColorPrimaryAppBar);
                tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_google, 0, 0, 0);
                tv.setAllCaps(true); //mayusculiza el texto
                return;
            }
        }
    }

    public static void customizeGooglePlusButton(SignInButton signInButton, String mensaje) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(mensaje);
                tv.setBackgroundResource(R.color.colorAccent);
                tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_email_white_24dp, 0, 0, 0);
                tv.setAllCaps(true); //mayusculiza el texto
                return;
            }
        }
    }

    private static void setEnabledActivity(Activity a, Boolean estado) {
        if (estado) {
            a.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            a.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    private static void progressBarVisibility(ProgressBar progressBar, TextView
            tvProgressBar, Boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
            tvProgressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            tvProgressBar.setVisibility(View.GONE);
        }
    }

    private static void ocultarTeclado(Activity a, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            mostrarMensajeLog(a, e.toString());
        }
    }

    public static void displayProgressBar(Activity a, ProgressBar progressBar, TextView
            tvProgressBar, String mensajeTV) {
        setEnabledActivity(a, false);
        ocultarTeclado(a, progressBar);
        progressBarVisibility(progressBar, tvProgressBar, true);
        tvProgressBar.setText(mensajeTV);
    }

    public static void lockProgressBar(Activity a, ProgressBar progressBar, TextView
            tvProgressBar) {
        setEnabledActivity(a, true);
        progressBarVisibility(progressBar, tvProgressBar, false);
    }

    public static AlertDialog createCustomDialog(Activity a,
                                                 String titulo, String cuerpo,
                                                 String mensajeSi, String mensajeNo,
                                                 Callable<Void> methodAcept, Callable<Void> methodCancel) {
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(a);
        // Get the layout inflater
        LayoutInflater inflater = a.getLayoutInflater();
        // Inflar y establecer el layout para el dialogo
        // Pasar nulo como vista principal porque va en el diseño del diálogo
        View v = inflater.inflate(R.layout.custom_dialog, null);
        //builder.setView(inflater.inflate(R.layout.dialog_signin, null))
        TextView tvTitulo = v.findViewById(R.id.tvTitulo);
        TextView tvCuerpo = v.findViewById(R.id.tvCuerpo);
        Button bAceptar = v.findViewById(R.id.bAceptar);
        Button bCancelar = v.findViewById(R.id.bCancelar);
        tvTitulo.setText(titulo);
        tvCuerpo.setText(cuerpo);
        bAceptar.setText(mensajeSi);
        bCancelar.setText(mensajeNo);
        builder.setView(v);
        alertDialog = builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Add action buttons
        bAceptar.setOnClickListener(
                v12 -> {
                    // Aceptar
                    try {
                        methodAcept.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                }
        );
        bCancelar.setOnClickListener(
                v1 -> {
                    try {
                        methodCancel.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    alertDialog.dismiss();
                }
        );
        return alertDialog;
    }

    public static void cargarSpinner(Spinner spinner,
                                     Activity a,
                                     int dato,
                                     List<String> labels,
                                     Callable<Void> methodAcept) {
        spinner.setBackgroundResource(R.drawable.sp_redondo);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(a,
                R.layout.spinner_item, labels);
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(dato);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setPreference(a, POSICION_SELECCIONADA, i);
                try {
                    methodAcept.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // NO HACE NADA
            }
        });
    }

    public static String formatearFechaString(String fecha) {
        return fecha.substring(8, 10) + "/" +
                fecha.substring(5, 7) + "/" +
                fecha.substring(0, 4);
    }

    public static void intentar(Callable<Void> method) {
        try {
            method.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}