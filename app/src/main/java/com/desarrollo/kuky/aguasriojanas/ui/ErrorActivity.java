package com.desarrollo.kuky.aguasriojanas.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.desarrollo.kuky.aguasriojanas.R;

import androidx.appcompat.app.AppCompatActivity;

import static com.desarrollo.kuky.aguasriojanas.util.Errores.ERROR_PREFERENCE;
import static com.desarrollo.kuky.aguasriojanas.util.Errores.MENSAJE_ERROR;
import static com.desarrollo.kuky.aguasriojanas.util.Util.abrirActivity;
import static com.desarrollo.kuky.aguasriojanas.util.Util.getPreference;

public class ErrorActivity extends AppCompatActivity {
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        tvError = findViewById(R.id.tvError);
        tvError.setText(getPreference(this, ERROR_PREFERENCE, MENSAJE_ERROR));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        abrirActivity(this, InicioActivity.class);
    }
}
