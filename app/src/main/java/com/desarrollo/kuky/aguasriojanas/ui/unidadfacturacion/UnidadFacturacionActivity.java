package com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.controlador.unidadfacturacion.UnidadFacturacionControlador;
import com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion.Unidad;
import com.desarrollo.kuky.aguasriojanas.ui.InicioActivity;
import com.desarrollo.kuky.aguasriojanas.ui.adapter.lvaUnidades;
import com.desarrollo.kuky.aguasriojanas.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;

public class UnidadFacturacionActivity extends AppCompatActivity {

    public static ArrayList<Unidad> unidades;
    private ProgressBar progressBar;
    private TextView tvProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidad_facturacion);
        progressBar = findViewById(R.id.progressBar);
        tvProgressBar = findViewById(R.id.tvProgressBar);
        ListView lvUnidades = findViewById(R.id.lvUnidades);
        lvaUnidades adaptador = new lvaUnidades(this, unidades, progressBar, tvProgressBar);
        lvUnidades.setAdapter(adaptador);
        lvUnidades.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lvUnidades.setOnItemClickListener((parent, view, position, id) -> {
            UnidadFacturacionControlador unidadFacturacionControlador = new UnidadFacturacionControlador();
            unidadFacturacionControlador.abrirEstadoCuenta(
                    UnidadFacturacionActivity.this, progressBar, tvProgressBar, unidades.get(position).getUnidad()
            );
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Util.createCustomDialog(UnidadFacturacionActivity.this, "Nueva unidad",
                "Elija una opcion por la cual cargar una nueva unidad.",
                "EDELAR",
                "Aguas Riojanas",
                // mensajeSi
                () -> {
                    UnidadFacturacionControlador unidadFacturacionControlador = new UnidadFacturacionControlador();
                    unidadFacturacionControlador.abrirNuevaUnidadEdelar(
                            UnidadFacturacionActivity.this, progressBar, tvProgressBar
                    );
                    return null;
                },
                // mensajeNo
                () -> {
                    UnidadFacturacionControlador unidadFacturacionControlador = new UnidadFacturacionControlador();
                    unidadFacturacionControlador.abrirNuevaUnidadAguas(
                            UnidadFacturacionActivity.this, progressBar, tvProgressBar
                    );
                    return null;
                }).show());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        abrirActivity(this, InicioActivity.class);
    }
}
