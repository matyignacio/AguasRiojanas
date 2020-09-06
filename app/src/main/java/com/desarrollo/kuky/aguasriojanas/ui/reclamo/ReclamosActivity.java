package com.desarrollo.kuky.aguasriojanas.ui.reclamo;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.objeto.reclamo.Reclamo;
import com.desarrollo.kuky.aguasriojanas.objeto.reclamo.TipoReclamo;
import com.desarrollo.kuky.aguasriojanas.objeto.reclamo.UbicacionReclamo;
import com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion.Unidad;
import com.desarrollo.kuky.aguasriojanas.ui.InicioActivity;
import com.desarrollo.kuky.aguasriojanas.ui.reclamo.ui.reclamo.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;

public class ReclamosActivity extends AppCompatActivity {
    public static ArrayList<Unidad> unidades;
    public static ArrayList<Reclamo> reclamos;
    public static ArrayList<TipoReclamo> tiposReclamos;
    public static ArrayList<UbicacionReclamo> ubicacionReclamos;
    private ProgressBar progressBar;
    private TextView tvProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamos);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        progressBar = findViewById(R.id.progressBar);
        tvProgressBar = findViewById(R.id.tvProgressBar);
        TextView title = findViewById(R.id.title);
        title.setText(R.string.title_activity_reclamos);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            abrirActivity(this, NuevoReclamoActivity.class);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        abrirActivity(this, InicioActivity.class);
    }
}