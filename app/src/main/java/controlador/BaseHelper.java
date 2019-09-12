package controlador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kuky on 22/05/2019.
 */
public class BaseHelper extends SQLiteOpenHelper {
    private static BaseHelper sInstance;

    private static final String DATABASE_NAME = "AguasRiojanas";
    private static final int DATABASE_VERSION = 1;

    public static synchronized BaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new BaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static method "getInstance()" instead.
     */
    private BaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(sqlTablaDatosContacto);
        sqLiteDatabase.execSQL(sqlTablaOficinasComerciales);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String dropTable(String table) {
        return "DROP TABLE IF EXISTS " + table;
    }

    private String sqlTablaDatosContacto = "CREATE TABLE IF NOT EXISTS datos_contacto (" +
            "  id int(11) NOT NULL ," +
            "  telefono varchar(45) NOT NULL," +
            "  web varchar(45) NOT NULL," +
            "  correo varchar(45) NOT NULL," +
            "  PRIMARY KEY (id)" +
            ") ";

    private String sqlTablaOficinasComerciales = "CREATE TABLE IF NOT EXISTS oficinas_comerciales (" +
            "  id int(11) NOT NULL," +
            "  localidad varchar(30) NOT NULL," +
            "  direccion varchar(45) NOT NULL," +
            "  horario_desde varchar(15) NOT NULL," +
            "  horario_hasta varchar(15) NOT NULL," +
            "  PRIMARY KEY (id)" +
            ") ";

    public String getSqlTablaDatosContacto() {
        return sqlTablaDatosContacto;
    }

    public String getSqlTablaOficinasComerciales() {
        return sqlTablaOficinasComerciales;
    }
}