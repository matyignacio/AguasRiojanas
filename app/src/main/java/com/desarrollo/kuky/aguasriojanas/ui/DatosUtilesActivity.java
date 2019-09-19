package com.desarrollo.kuky.aguasriojanas.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.ui.DatosUtilesFragments.Contacto;
import com.desarrollo.kuky.aguasriojanas.ui.DatosUtilesFragments.LugaresPago;
import com.desarrollo.kuky.aguasriojanas.ui.DatosUtilesFragments.OficinasComerciales;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import util.Util;

import static util.Util.abrirActivity;

public class DatosUtilesActivity extends AppCompatActivity {

    Contacto contactoFragment;
    OficinasComerciales oficinasComerciales;
    LugaresPago lugaresPago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_utiles);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        contactoFragment = new Contacto();
        oficinasComerciales = new OficinasComerciales();
        lugaresPago = new LugaresPago();
        Util.abrirFragmento(DatosUtilesActivity.this, R.id.container, lugaresPago);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        abrirActivity(this, InicioActivity.class);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.lugares_pago:
                    Util.cerrarFragmento(DatosUtilesActivity.this, contactoFragment);
                    Util.cerrarFragmento(DatosUtilesActivity.this, oficinasComerciales);
                    if (!lugaresPago.isVisible()) {
                        Util.abrirFragmento(DatosUtilesActivity.this, R.id.container, lugaresPago);
                    }
                    return true;
                case R.id.oficinas_comerciales:
                    Util.cerrarFragmento(DatosUtilesActivity.this, contactoFragment);
                    Util.cerrarFragmento(DatosUtilesActivity.this, lugaresPago);
                    if (!oficinasComerciales.isVisible()) {
                        Util.abrirFragmento(DatosUtilesActivity.this, R.id.container, oficinasComerciales);
                    }
                    return true;
                case R.id.datos_contacto:
                    Util.cerrarFragmento(DatosUtilesActivity.this, oficinasComerciales);
                    Util.cerrarFragmento(DatosUtilesActivity.this, lugaresPago);
                    if (!contactoFragment.isVisible()) {
                        Util.abrirFragmento(DatosUtilesActivity.this, R.id.container, contactoFragment);
                    }
                    return true;
            }
            return false;
        }
    };
}
