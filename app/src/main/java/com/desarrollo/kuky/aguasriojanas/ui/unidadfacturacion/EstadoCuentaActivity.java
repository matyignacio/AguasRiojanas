package com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion.Comprobante;
import com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion.ui.estadocuenta.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import static android.text.TextUtils.concat;
import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;

public class EstadoCuentaActivity extends AppCompatActivity {

    TextView title;
    private ProgressBar progressBar;
    private TextView tvProgressBar;
    int unidad;
    public static ArrayList<Comprobante> comprobantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_cuenta);
        unidad = Objects.requireNonNull(getIntent().getExtras()).getInt("unidad");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        progressBar = findViewById(R.id.progressBar);
        tvProgressBar = findViewById(R.id.tvProgressBar);
        title = findViewById(R.id.title);
        title.setText(concat("Comprobantes de ", String.valueOf(unidad)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        abrirActivity(this, UnidadFacturacionActivity.class);
    }
}