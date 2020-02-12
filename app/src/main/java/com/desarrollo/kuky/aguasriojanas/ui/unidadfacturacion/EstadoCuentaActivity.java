package com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.controlador.InicioControlador;
import com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.ui.estadocuenta.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class EstadoCuentaActivity extends AppCompatActivity {

    Button bImprimirBoleta;
    TextView title;
    private ProgressBar progressBar;
    private TextView tvProgressBar;
    int unidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_cuenta);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        progressBar = findViewById(R.id.progressBar);
        tvProgressBar = findViewById(R.id.tvProgressBar);
        title = findViewById(R.id.title);
        unidad = getIntent().getExtras().getInt("unidad");
        title.setText("Comprobantes de " + unidad);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InicioControlador inicioControlador = new InicioControlador();
        inicioControlador.abrirUnidadFacturacionActivity(this, progressBar, tvProgressBar);
    }
}