package com.sample.marmu.tvstask.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.sample.marmu.tvstask.R;
import com.sample.marmu.tvstask.model.UserModel;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Employee Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            UserModel userModel = (UserModel) bundle.get("userModel");

            if (userModel != null) {
                TextInputEditText etName = findViewById(R.id.etName);
                TextInputEditText etDesignation = findViewById(R.id.etDesignation);
                TextInputEditText etCity = findViewById(R.id.etCity);
                TextInputEditText etCode = findViewById(R.id.etCode);
                TextInputEditText etDateOfJoin = findViewById(R.id.etDateOfJoin);
                TextInputEditText etSalary = findViewById(R.id.etSalary);

                etName.setText(userModel.getName());
                etDesignation.setText(userModel.getDesignation());
                etCity.setText(userModel.getCity());
                etCode.setText(userModel.getCode());
                etDateOfJoin.setText(userModel.getDateOfJoin());
                etSalary.setText("$" + userModel.getSalary());
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
