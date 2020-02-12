package com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.controlador.InicioControlador;

import androidx.appcompat.app.AppCompatActivity;

import static com.desarrollo.kuky.aguasriojanas.util.Util.setPrimaryFontBold;

public class ComprobanteUnidadActivity extends AppCompatActivity {

    Button bImprimirBoleta;
    private ProgressBar progressBar;
    private TextView tvProgressBar;
    int unidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprobante_unidad);
        progressBar = findViewById(R.id.progressBar);
        tvProgressBar = findViewById(R.id.tvProgressBar);
        bImprimirBoleta = findViewById(R.id.bImprimirBoleta);
        unidad = getIntent().getExtras().getInt("unidad");
        this.setTitle("Unidad nº " + unidad);
        setPrimaryFontBold(this, bImprimirBoleta);
        bImprimirBoleta.setOnClickListener(v -> {
            Uri uriUrl = Uri.parse("http://volley.aguasriojanas.com.ar/presionaguas/aguasriojanas/imprimir/imprimirfactura.php?unidad=");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse((uriUrl + String.valueOf(unidad))));
            this.startActivity(launchBrowser);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InicioControlador inicioControlador = new InicioControlador();
        inicioControlador.abrirUnidadFacturacionActivity(this, progressBar, tvProgressBar);
    }
}
