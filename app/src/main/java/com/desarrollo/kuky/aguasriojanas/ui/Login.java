package com.desarrollo.kuky.aguasriojanas.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.desarrollo.kuky.aguasriojanas.BaseActivity;
import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.ui.login.EmailPasswordActivity;
import com.desarrollo.kuky.aguasriojanas.ui.login.GoogleSignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static util.Util.abrirActivity;
import static util.Util.setPrimaryFontBold;

public class Login extends BaseActivity implements
        View.OnClickListener {

    Button bSignInEmail;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bSignInEmail = findViewById(R.id.signInEmail);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        // Button listeners
        findViewById(R.id.signInGoogle).setOnClickListener(this);
        /*** SETEAMOS TYPEFACES ***********************************/
        setPrimaryFontBold(this, bSignInEmail);
        /**********************************************************/
        bSignInEmail.setOnClickListener(v -> {
            abrirActivity(this, EmailPasswordActivity.class);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        hideProgressDialog();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            abrirActivity(this, Inicio.class);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signInGoogle) {
            abrirActivity(this, GoogleSignInActivity.class);
        }
    }
}
