package com.sample.marmu.tvstask.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.sample.marmu.tvstask.R;
import com.sample.marmu.tvstask.model.UserModel;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ChartActivity extends AppCompatActivity {

    List<UserModel> usersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Chart Screen");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            usersList = (List<UserModel>) bundle.get("barChart");


            ArrayList<BarEntry> salaries = new ArrayList<>();
            for (int i = usersList.size() - 1; i > usersList.size() - 11; i--) {
                UserModel userModel = usersList.get(i);

                float _val = Float.parseFloat(String.valueOf(userModel.getSalary()));
                salaries.add(new BarEntry(Float.parseFloat(String.valueOf(usersList.size() - i)), _val));
            }
            populateBar(salaries);
        }
    }

    private void populateBar(ArrayList<BarEntry> salaries) {
        BarChart barChart = findViewById(R.id.barChart);

        BarDataSet barDataSet;
        barDataSet = new BarDataSet(salaries, "salary");

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.2f);
        barChart.setData(barData);
        barChart.setFitBars(true);
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
