package controlador;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import objeto.DatoContacto;

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
}
