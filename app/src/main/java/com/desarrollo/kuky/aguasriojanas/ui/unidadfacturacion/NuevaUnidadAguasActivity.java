package com.desarrollo.kuky.aguasriojanas.ui.unidadfacturacion;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.controlador.InicioControlador;
import com.desarrollo.kuky.aguasriojanas.controlador.unidadfacturacion.UnidadControlador;
import com.desarrollo.kuky.aguasriojanas.objeto.unidadfacturacion.RelacionInmueble;
import com.desarrollo.kuky.aguasriojanas.util.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static com.desarrollo.kuky.aguasriojanas.util.Util.EXITOSO;
import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;
import static com.desarrollo.kuky.aguasriojanas.util.Util.setPrimaryFontBold;
import static com.desarrollo.kuky.aguasriojanas.util.Util.validarCampos;

public class NuevaUnidadAguasActivity extends AppCompatActivity {

    public static ArrayList<RelacionInmueble> relacionesInmuebles;
    private ProgressBar progressBar;
    private TextView tvProgressBar;
    TextView tvInstrucciones;
    EditText etNumeroUnidad, etNumeroComprobante;
    Spinner sRelacionInmueble;
    Button bAltaUnidad;
    public List<String> labelsRelacionInmueble = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_unidad_aguas);
        progressBar = findViewById(R.id.progressBar);
        tvProgressBar = findViewById(R.id.tvProgressBar);
        tvInstrucciones = findViewById(R.id.tv_instrucciones);
        etNumeroUnidad = findViewById(R.id.et_numero_unidad);
        etNumeroComprobante = findViewById(R.id.et_numero_comprobante);
        sRelacionInmueble = findViewById(R.id.sp_relacion_inmueble);
        bAltaUnidad = findViewById(R.id.b_alta_unidad);
        ///////////////////////////////////////////////////////////////
        setPrimaryFontBold(this, tvInstrucciones);
        setPrimaryFontBold(this, etNumeroUnidad);
        setPrimaryFontBold(this, etNumeroComprobante);
        setPrimaryFontBold(this, bAltaUnidad);
        ///////////////////////////////////////////////////////////////
        for (int i = 0; i < relacionesInmuebles.size(); i++) {
            labelsRelacionInmueble.add(relacionesInmuebles.get(i).getNombre());
        }
        /** CARGAMOS LOS SPINNERS ************************************/
        Util.cargarSpinner(sRelacionInmueble,
                NuevaUnidadAguasActivity.this,
                0,
                labelsRelacionInmueble,
                () -> {

                    return null;
                });
        /**************************************************************/
        bAltaUnidad.setOnClickListener(v -> {
            if (validarCampos(this, etNumeroUnidad, etNumeroComprobante) == EXITOSO) {
                UnidadControlador unidadControlador = new UnidadControlador();
                unidadControlador.buscarUnidadAguas(this, progressBar, tvProgressBar,
                        Integer.parseInt(etNumeroUnidad.getText().toString()),
                        Integer.parseInt(etNumeroComprobante.getText().toString()), () -> {
                            InicioControlador inicioControlador = new InicioControlador();
                            inicioControlador.abrirUnidadFacturacionActivity(this, progressBar, tvProgressBar);
                            return null;
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        abrirActivity(this, UnidadFacturacionActivity.class);
    }
}
