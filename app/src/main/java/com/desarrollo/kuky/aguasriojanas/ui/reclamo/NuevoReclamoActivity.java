package com.desarrollo.kuky.aguasriojanas.ui.reclamo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.controlador.InicioControlador;
import com.desarrollo.kuky.aguasriojanas.controlador.reclamo.ReclamoControlador;
import com.desarrollo.kuky.aguasriojanas.objeto.reclamo.Reclamo;
import com.desarrollo.kuky.aguasriojanas.ui.InicioActivity;
import com.desarrollo.kuky.aguasriojanas.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

import static android.text.TextUtils.concat;
import static com.desarrollo.kuky.aguasriojanas.ui.login.LoginActivity.mAuth;
import static com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity.tiposReclamos;
import static com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity.ubicacionReclamos;
import static com.desarrollo.kuky.aguasriojanas.ui.reclamo.ReclamosActivity.unidades;
import static com.desarrollo.kuky.aguasriojanas.util.Util.EXITO;
import static com.desarrollo.kuky.aguasriojanas.util.Util.EXITOSO;
import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;
import static com.desarrollo.kuky.aguasriojanas.util.Util.imageToString;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensaje;
import static com.desarrollo.kuky.aguasriojanas.util.Util.mostrarMensajeLog;
import static com.desarrollo.kuky.aguasriojanas.util.Util.setPrimaryFontBold;
import static com.desarrollo.kuky.aguasriojanas.util.Util.validarCampos;

public class NuevoReclamoActivity extends AppCompatActivity
        implements FormFoto.OnItemSelectedListener {
    public static final int SELECT_PHOTO = 10;
    public static Reclamo reclamo;
    public static FormFoto formFoto = new FormFoto();
    private ProgressBar progressBar;
    private TextView tvProgressBar;
    public List<String> labelsTiposReclamos = new ArrayList<>();
    public List<String> labelsUbicacionReclamos = new ArrayList<>();
    public List<String> labelsUnidades = new ArrayList<>();
    Spinner sTiposReclamos;
    Spinner sUbicacionReclamos;
    Spinner sUnidades;
    EditText etDireccion, etDescripcion;
    Button bEnviarReclamo, bCapturarFoto;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_reclamo);
        reclamo = new Reclamo();
        progressBar = findViewById(R.id.progressBar);
        tvProgressBar = findViewById(R.id.tvProgressBar);
        sTiposReclamos = findViewById(R.id.sp_tipo_reclamo);
        sUbicacionReclamos = findViewById(R.id.sp_ubicacion_reclamo);
        sUnidades = findViewById(R.id.sp_unidades);
        etDireccion = findViewById(R.id.et_direccion_reclamo);
        etDescripcion = findViewById(R.id.et_descripcion_reclamo);
        bEnviarReclamo = findViewById(R.id.bEnviarReclamo);
        bCapturarFoto = findViewById(R.id.bCapturarFoto);
        ////////////////////////////////////////////////
        setPrimaryFontBold(this, etDireccion);
        setPrimaryFontBold(this, etDescripcion);
        setPrimaryFontBold(this, bCapturarFoto);
        setPrimaryFontBold(this, bEnviarReclamo);
        for (int i = 0; i < tiposReclamos.size(); i++) {
            labelsTiposReclamos.add(tiposReclamos.get(i).getNombre());
        }
        for (int i = 0; i < ubicacionReclamos.size(); i++) {
            labelsUbicacionReclamos.add(ubicacionReclamos.get(i).getNombre());
        }
        labelsUnidades.add("SIN ESPECIFICAR");
        for (int i = 0; i < unidades.size(); i++) {
            labelsUnidades.add(String.valueOf(unidades.get(i).getUnidad()));
        }
        /* CARGAMOS LOS SPINNERS ************************************/
        Util.cargarSpinner(sTiposReclamos,
                this,
                0,
                labelsTiposReclamos,
                () -> {
                    if (sTiposReclamos.getSelectedItemPosition() == 1) {
                        // SI ELIGE LA OPCION DE CONSUMO ELEVADO
                        sUbicacionReclamos.setSelection(0); // olbigamos
                        sUbicacionReclamos.setEnabled(false);
                    } else {
                        sUbicacionReclamos.setSelection(0);
                        sUbicacionReclamos.setEnabled(true);
                    }
                    return null;
                });
        Util.cargarSpinner(sUbicacionReclamos,
                this,
                0,
                labelsUbicacionReclamos,
                () -> null);
        Util.cargarSpinner(sUnidades,
                this,
                0,
                labelsUnidades,
                () -> {
                    if (sUnidades.getSelectedItemPosition() > 0) {
                        // SI ELIGE ALGUNA UNIDAD
                        etDireccion.setText(concat(unidades.get(sUnidades.getSelectedItemPosition() - 1).getCalle(),
                                " ",
                                String.valueOf(unidades.get(sUnidades.getSelectedItemPosition() - 1).getNumeroCasa())));
                    } else {
                        etDireccion.setText("");
                    }
                    return null;
                });
        /* *************************************************************/
        bEnviarReclamo.setOnClickListener(v -> {
            if (validarCampos(this, etDireccion) == EXITOSO) {
                if (bCapturarFoto.getText().equals(getResources().getString(R.string.b_capturar_foto_lista))) {
                    Util.createCustomDialog(this,
                            "CONFIRME",
                            "Esta seguro de enviar este reclamo?",
                            "ACEPTAR",
                            "CANCELAR",
                            () -> {
                                reclamo.setIdTipoReclamo(tiposReclamos.get(sTiposReclamos.getSelectedItemPosition()).getId());
                                reclamo.setIdUbicacionReclamo(ubicacionReclamos.get(sUbicacionReclamos.getSelectedItemPosition()).getId());
                                reclamo.setDireccion(etDireccion.getText().toString());
                                reclamo.setDescripcion(etDescripcion.getText().toString());
                                reclamo.setUnidad(labelsUnidades.get(sUnidades.getSelectedItemPosition()));
                                reclamo.setIdUsuario(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                                ReclamoControlador reclamoControlador = new ReclamoControlador();
                                reclamoControlador.insertReclamo(this,
                                        progressBar,
                                        tvProgressBar,
                                        reclamo, () -> {
                                            Util.createCustomDialog(this,
                                                    "MUCHAS GRACIAS!",
                                                    "Su reclamo ha sido registrado. Nos comunicaremos con ud a la brevedad.",
                                                    "ACEPTAR",
                                                    "IR AL INICIO",
                                                    () -> {
                                                        InicioControlador inicioControlador = new InicioControlador();
                                                        inicioControlador.abrirReclamoActivity(this, progressBar, tvProgressBar);
                                                        return null;
                                                    },
                                                    () -> {
                                                        abrirActivity(this, InicioActivity.class);
                                                        return null;
                                                    }).show();
                                            return null;
                                        });
                                return null;
                            },
                            () -> null).show();
                } else {
                    mostrarMensaje(this, "Debe adjuntar alguna imagen por foto o galeria.");
                }
            }
        });
        restaurarBoton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PHOTO) {
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    reclamo.setFoto(imageToString(bmp));
                    onGuardarSelected(EXITO);
                } catch (IOException e) {
                    mostrarMensaje(this, e.toString());
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (formFoto.isVisible()) {
            getSupportFragmentManager().beginTransaction().
                    remove(formFoto).
                    commit();
        } else {
            abrirActivity(this, ReclamosActivity.class);
        }
    }

    @Override
    public void onGuardarSelected(String resultado) {
        // Si el listener nos devuelve un EXITO, le mostramos al usuario un icono
        // para que entienda que ya se cargo la foto.
        // Y habilitamos el LongClickListener
        if (resultado.equals(EXITO)) {
            asignarImagenBoton();
        } else {
            // Sino, restauramos los botones originales y traemos el mensaje del error
            restaurarBoton();
            mostrarMensajeLog(this, resultado);
        }
    }

    private void asignarImagenBoton() {
        bCapturarFoto.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_check_white), null);
        bCapturarFoto.setText(R.string.b_capturar_foto_lista);
        bCapturarFoto.setOnLongClickListener(v -> {
            try {
                reclamo.setFoto(null);
                restaurarBoton();
            } catch (Exception e) {
                mostrarMensaje(this, e.toString());
            }
            return false;
        });
        bCapturarFoto.setOnClickListener(v -> mostrarMensaje(this, "Para eliminar la foto y tomar otra, mantenga apretado este boton."));
    }

    private void restaurarBoton() {
        bCapturarFoto.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_photo_camera_white_24dp), null);
        bCapturarFoto.setText(R.string.b_capturar_foto);
        bCapturarFoto.setOnLongClickListener(v -> false);
        bCapturarFoto.setOnClickListener(v -> Util.createCustomDialog(this,
                "Seleccionar metodo",
                "",
                "Tomar foto",
                "Cargar de la galeria",
                () -> {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.llNuevoReclamo, formFoto)
                            .commit();
                    return null;
                }, () -> {
                    abrirGaleria();
                    return null;
                }).show());
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Seleccione la Aplicación"), SELECT_PHOTO);
    }
}
