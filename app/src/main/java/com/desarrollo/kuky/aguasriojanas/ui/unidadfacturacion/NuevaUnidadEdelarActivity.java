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

public class NuevaUnidadEdelarActivity extends AppCompatActivity {
    public static ArrayList<RelacionInmueble> relacionesInmuebles;
    private ArrayList<EditText> inputs = new ArrayList<>();
    private ProgressBar progressBar;
    private TextView tvProgressBar;
    TextView tvInstrucciones;
    EditText etNis, etNumeroCliente;
    Spinner sRelacionInmueble;
    Button bAltaUnidad;
    public List<String> labelsRelacionInmueble = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_unidad_edelar);
        progressBar = findViewById(R.id.progressBar);
        tvProgressBar = findViewById(R.id.tvProgressBar);
        tvInstrucciones = findViewById(R.id.tv_instrucciones);
        etNis = findViewById(R.id.et_numero_unidad);
        etNumeroCliente = findViewById(R.id.et_numero_comprobante);
        inputs.add(etNis);
        inputs.add(etNumeroCliente);
        sRelacionInmueble = findViewById(R.id.sp_relacion_inmueble);
        bAltaUnidad = findViewById(R.id.b_alta_unidad);
        ///////////////////////////////////////////////////////////////
        setPrimaryFontBold(this, tvInstrucciones);
        setPrimaryFontBold(this, etNis);
        setPrimaryFontBold(this, etNumeroCliente);
        setPrimaryFontBold(this, bAltaUnidad);
        ///////////////////////////////////////////////////////////////
        for (int i = 0; i < relacionesInmuebles.size(); i++) {
            labelsRelacionInmueble.add(relacionesInmuebles.get(i).getNombre());
        }
        /* CARGAMOS LOS SPINNERS ************************************/
        Util.cargarSpinner(sRelacionInmueble,
                NuevaUnidadEdelarActivity.this,
                0,
                labelsRelacionInmueble,
                () -> {

                    return null;
                },
                () -> {
                    return null;
                });
        /* *************************************************************/
        bAltaUnidad.setOnClickListener(v -> {
            if (validarCampos(this, inputs) == EXITOSO) {
                UnidadControlador unidadControlador = new UnidadControlador();
                unidadControlador.buscarUnidadEdelar(this, progressBar, tvProgressBar,
                        Integer.parseInt(etNis.getText().toString()),
                        Integer.parseInt(etNumeroCliente.getText().toString()), () -> {
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
