package com.desarrollo.kuky.aguasriojanas.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.desarrollo.kuky.aguasriojanas.R;

import controlador.DatosUtilesControlador;
import objeto.DatoContacto;

import static util.Util.abrirActivity;

public class WebActivity extends AppCompatActivity {

    WebView miVisorWeb;
    DatoContacto datoContacto = new DatoContacto();
    DatosUtilesControlador datosUtilesControlador = new DatosUtilesControlador();

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        datoContacto = datosUtilesControlador.getDatosContacto(this);
        miVisorWeb = findViewById(R.id.miWebView);

        final WebSettings ajustesVisorWeb = miVisorWeb.getSettings();
        ajustesVisorWeb.setJavaScriptEnabled(true);

        miVisorWeb.loadUrl("http://" + datoContacto.getWeb() + "/");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        abrirActivity(this, DatosUtiles.class);
    }
}
