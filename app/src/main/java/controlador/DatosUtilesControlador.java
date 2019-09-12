package controlador;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import objeto.DatoContacto;
import objeto.OficinaComercial;

public class DatosUtilesControlador {

    public DatoContacto getDatosContacto(Activity a) {
        DatoContacto datoContacto = new DatoContacto();
        SQLiteDatabase db = BaseHelper.getInstance(a).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT telefono, web, correo " +
                " FROM datos_contacto ", null);
        while (c.moveToNext()) {
            datoContacto.setTelefono(c.getString(0));
            datoContacto.setWeb(c.getString(1));
            datoContacto.setCorreo(c.getString(2));
        }
        c.close();
        db.close();
        return datoContacto;
    }

    public ArrayList<OficinaComercial> getOficinasComerciales(Activity a) {
        ArrayList<OficinaComercial> oficinas = new ArrayList<>();
        SQLiteDatabase db = BaseHelper.getInstance(a).getReadableDatabase();
        Cursor c = db.rawQuery("SELECT localidad, direccion, horario_desde, horario_hasta" +
                " FROM oficinas_comerciales ", null);
        while (c.moveToNext()) {
            OficinaComercial oficinaComercial = new OficinaComercial();
            oficinaComercial.setLocalidad(c.getString(0));
            oficinaComercial.setDireccion(c.getString(1));
            oficinaComercial.setHorarioDesde(c.getString(2));
            oficinaComercial.setHorarioHasta(c.getString(3));
            oficinas.add(oficinaComercial);
        }
        c.close();
        db.close();
        return oficinas;
    }
}
