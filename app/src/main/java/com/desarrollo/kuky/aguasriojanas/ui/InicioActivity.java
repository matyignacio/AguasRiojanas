package com.desarrollo.kuky.aguasriojanas.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.controlador.InicioControlador;
import com.desarrollo.kuky.aguasriojanas.ui.login.LoginActivity;
import com.desarrollo.kuky.aguasriojanas.util.Util;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import static com.desarrollo.kuky.aguasriojanas.ui.login.LoginActivity.mAuth;
import static com.desarrollo.kuky.aguasriojanas.ui.login.LoginActivity.mGoogleSignInClient;
import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensaje;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensajeLog;
import static com.desarrollo.kuky.aguasriojanas.util.Util.setPrimaryFontBold;

public class InicioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "InicioActivity";

    Button bUnidadFacturacion, bReclamo, bNotificaciones, bDatosUtiles;
    private ProgressBar progressBar;
    private TextView tvProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        progressBar = findViewById(R.id.progressBar);
        tvProgressBar = findViewById(R.id.tvProgressBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView subTitle = headerView.findViewById(R.id.tvUsuarioNavBar);
        subTitle.setText(Objects.requireNonNull(LoginActivity.mAuth.getCurrentUser()).getDisplayName());
        mostrarMensajeLog(this, mAuth.getCurrentUser().getUid());
        bUnidadFacturacion = findViewById(R.id.bUnidadesFacturacion);
        bReclamo = findViewById(R.id.bReclamos);
        bNotificaciones = findViewById(R.id.bNotificaciones);
        bDatosUtiles = findViewById(R.id.bDatosUtiles);
        /*** SETEAMOS TYPEFACES ***********************************/
        setPrimaryFontBold(this, bUnidadFacturacion);
        setPrimaryFontBold(this, bReclamo);
        setPrimaryFontBold(this, bNotificaciones);
        setPrimaryFontBold(this, bDatosUtiles);
        /**********************************************************/
        bUnidadFacturacion.setOnClickListener(v -> {
            InicioControlador inicioControlador = new InicioControlador();
            inicioControlador.abrirUnidadFacturacionActivity(this, progressBar, tvProgressBar);
        });
        bReclamo.setOnClickListener(v -> {
            InicioControlador inicioControlador = new InicioControlador();
            inicioControlador.abrirReclamoActivity(this, progressBar, tvProgressBar);
        });
        bNotificaciones.setOnClickListener(v -> {
            InicioControlador inicioControlador = new InicioControlador();
            inicioControlador.abrirNotificacionActivity(this, progressBar, tvProgressBar);
        });
        bDatosUtiles.setOnClickListener(v -> {
            InicioControlador inicioControlador = new InicioControlador();
            inicioControlador.abrirDatosUtilesActivity(this, progressBar, tvProgressBar);
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
            Util.createCustomDialog(this,
                    "¿Esta seguro que desea cerrar la sesión?",
                    "",
                    "SI, CERRAR",
                    "CANCELAR",
                    () -> {
                        mAuth.signOut();
                        try {
                            // Google revoke access
                            mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                                    task -> abrirActivity(InicioActivity.this, LoginActivity.class));
                        } catch (Exception e) {
                            mostrarMensaje(InicioActivity.this,
                                    "No se pudo cerrar. Error: " + e.toString());
                        }
                        return null;
                    },
                    () -> null).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
