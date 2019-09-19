package com.desarrollo.kuky.aguasriojanas.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.desarrollo.kuky.aguasriojanas.R;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import controlador.InicioControlador;

import static util.Util.abrirActivity;
import static util.Util.setPrimaryFontBold;

public class InicioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button bUnidadFacturacion, bEstadoCuenta, bAsistenciaTecnica, bNotificaciones, bDatosUtiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bUnidadFacturacion = findViewById(R.id.bUnidadesFacturacion);
        bEstadoCuenta = findViewById(R.id.bEstadoCuenta);
        bAsistenciaTecnica = findViewById(R.id.bAsistenciaTecnica);
        bNotificaciones = findViewById(R.id.bNotificaciones);
        bDatosUtiles = findViewById(R.id.bDatosUtiles);
        /*** SETEAMOS TYPEFACES ***********************************/
        setPrimaryFontBold(this, bUnidadFacturacion);
        setPrimaryFontBold(this, bEstadoCuenta);
        setPrimaryFontBold(this, bAsistenciaTecnica);
        setPrimaryFontBold(this, bNotificaciones);
        setPrimaryFontBold(this, bDatosUtiles);
        /**********************************************************/
        bDatosUtiles.setOnClickListener(v -> {
            InicioControlador inicioControlador = new InicioControlador();
            inicioControlador.abrirDatosUtiles(this);
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_close) {
            LoginActivity.mAuth.signOut();
            abrirActivity(this, LoginActivity.class);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
