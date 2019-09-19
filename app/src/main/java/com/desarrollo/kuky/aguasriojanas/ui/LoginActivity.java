package com.desarrollo.kuky.aguasriojanas.ui;

import android.os.Bundle;
import android.view.View;

import com.desarrollo.kuky.aguasriojanas.BaseActivity;
import com.desarrollo.kuky.aguasriojanas.R;
import com.desarrollo.kuky.aguasriojanas.ui.login.EmailPasswordActivity;
import com.desarrollo.kuky.aguasriojanas.ui.login.GoogleSignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static util.Util.abrirActivity;
import static util.Util.customizeGooglePlusButton;

public class LoginActivity extends BaseActivity implements
        View.OnClickListener {

    // [START declare_auth]
    public static FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        // Button listeners
        findViewById(R.id.signInGoogle).setOnClickListener(this);
        findViewById(R.id.signInEmail).setOnClickListener(this);
        /** CUSTOMIZAMOS **************************************/
        customizeGooglePlusButton(findViewById(R.id.signInGoogle), "Acceder con Google", this);
        customizeGooglePlusButton(findViewById(R.id.signInEmail), "Acceder con Email");
        /******************************************************/

    }

    @Override
    protected void onStart() {
        super.onStart();
        hideProgressDialog();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            abrirActivity(this, InicioActivity.class);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signInGoogle) {
            abrirActivity(this, GoogleSignInActivity.class);
        }
        if (i == R.id.signInEmail) {
            abrirActivity(this, EmailPasswordActivity.class);
        }
    }

}
