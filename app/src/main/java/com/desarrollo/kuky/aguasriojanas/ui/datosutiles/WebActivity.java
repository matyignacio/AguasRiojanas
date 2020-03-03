package com.desarrollo.kuky.aguasriojanas.ui.datosutiles;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.controlador.DatosUtilesControlador;
import com.desarrollo.kuky.aguasriojanas.objeto.datosutiles.DatoContacto;

import androidx.appcompat.app.AppCompatActivity;

import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;

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
        abrirActivity(this, DatosUtilesActivity.class);
    }
}
