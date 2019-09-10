package com.desarrollo.kuky.aguasriojanas.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.desarrollo.kuky.aguasriojanas.R;

import static util.Util.abrirActivity;
import static util.Util.setPrimaryFontBold;

public class Inicio extends AppCompatActivity {

    Button bUnidadFacturacion, bEstadoCuenta, bAsistenciaTecnica, bNotificaciones, bDatosUtiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
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
            abrirActivity(this, DatosUtiles.class);
        });
    }
}
