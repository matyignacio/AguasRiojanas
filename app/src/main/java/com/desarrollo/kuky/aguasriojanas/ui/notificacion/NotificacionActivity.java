package com.desarrollo.kuky.aguasriojanas.ui.notificacion;

import android.os.Bundle;
import android.widget.ListView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.objeto.notificaciones.Notificacion;
import com.desarrollo.kuky.aguasriojanas.ui.InicioActivity;
import com.desarrollo.kuky.aguasriojanas.ui.adapter.lvaNotificaciones;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;

public class NotificacionActivity extends AppCompatActivity {

    public static ArrayList<Notificacion> notificaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);
        ListView lvNotificaciones = findViewById(R.id.lvNotificaciones);
        lvaNotificaciones adaptador = new lvaNotificaciones(this, notificaciones);
        lvNotificaciones.setAdapter(adaptador);
        lvNotificaciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        abrirActivity(this, InicioActivity.class);
    }
}
