package com.desarrollo.kuky.aguasriojanas.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.ui.DatosUtilesFragments.Contacto;

import util.Util;

import static util.Util.abrirActivity;

public class DatosUtiles extends AppCompatActivity {

    public static Contacto contactoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_utiles);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        contactoFragment = new Contacto();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        abrirActivity(this, Inicio.class);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.lugares_pago:

                    return true;
                case R.id.oficinas_comerciales:

                    return true;
                case R.id.datos_contacto:
                    if (!contactoFragment.isVisible()) {
                        Util.abrirFragmento(DatosUtiles.this, R.id.rlDatosUtiles, contactoFragment);
                    }
                    return true;
            }
            return false;
        }
    };
}
