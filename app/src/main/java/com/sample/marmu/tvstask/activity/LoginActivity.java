package com.sample.marmu.tvstask.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sample.marmu.tvstask.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Login Screen");
        }
    }

    public void authenticate(View view) {
        TextInputEditText etUserName = findViewById(R.id.etUserName);
        TextInputEditText etPassword = findViewById(R.id.etPassword);

        String userName = Objects.requireNonNull(etUserName.getText()).toString().trim();
        String password = Objects.requireNonNull(etPassword.getText()).toString().trim();

        if (userName.equalsIgnoreCase(getString(R.string.test)) && password.equalsIgnoreCase(getString(R.string._123456))) {
            Snackbar.make(view, "Successfully logged In", Snackbar.LENGTH_SHORT).show();
            startMainActivity();
        } else {
            Snackbar.make(view, "User name and password doesn't match", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void startMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainActivity);
        finish();
    }
}
