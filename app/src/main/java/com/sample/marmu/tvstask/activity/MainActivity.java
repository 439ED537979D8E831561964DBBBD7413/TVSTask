package com.sample.marmu.tvstask.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.sample.marmu.tvstask.R;
import com.sample.marmu.tvstask.adapter.MyUsersAdapter;
import com.sample.marmu.tvstask.model.TableModel;
import com.sample.marmu.tvstask.model.TableRequest;
import com.sample.marmu.tvstask.model.UserModel;
import com.sample.marmu.tvstask.retrofit.ApiClient;
import com.sample.marmu.tvstask.retrofit.ApiService;
import com.sample.marmu.tvstask.utils.DialogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

@SuppressWarnings("unchecked")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTableData();
    }

    List<UserModel> usersList = new ArrayList<>();

    private void getTableData() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<TableModel> call = apiService.getTableData(new TableRequest(getString(R.string.test), getString(R.string._123456)));
        call.enqueue(new Callback<TableModel>() {
            @Override
            public void onResponse(@NonNull Call<TableModel> call, @NonNull retrofit2.Response<TableModel> response) {
                if (response.isSuccessful()) {
                    setContentView(R.layout.activity_main);
                    TableModel tableModel = response.body();
                    if (tableModel != null) {
                        try {
                            String tableData = tableModel.getTABLEDATA();
                            JSONObject jsonObject = new JSONObject(tableData);
                            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
                            usersList = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONArray list = (JSONArray) jsonArray.get(i);

                                String name = String.valueOf(list.get(0));
                                String designation = String.valueOf(list.get(1));
                                String city = String.valueOf(list.get(2));
                                String code = String.valueOf(list.get(3));
                                String dateOfJoin = String.valueOf(list.get(4));
                                int salary = Integer.parseInt(String.valueOf(list.get(5)).substring(1).replace(",", ""));

                                usersList.add(new UserModel(name, designation, city, code, dateOfJoin, salary));
                            }
                            populateView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TableModel> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateView() {
        RecyclerView.Adapter adapter = new MyUsersAdapter(this, usersList);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.removeAllViews();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(0);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Employee List (" + usersList.size() + ")");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_chart:
                Intent chartActivity = new Intent(this, ChartActivity.class);
                List<UserModel> _usersList = usersList;
                Collections.sort(_usersList);
                chartActivity.putExtra("barChart", (Serializable) _usersList);
                startActivity(chartActivity);
                return true;
            case R.id.action_map:
                DialogUtils.showProgressDialog(MainActivity.this, "Loading");
                new MapLoadTask(MainActivity.this).execute(usersList);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        appExitDialog();
    }

    private void appExitDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(R.string.app_name));
        dialog.setMessage("Do you want to exit App ?");
        dialog.setCancelable(true);

        //positive button
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
                dialogInterface.cancel();
            }
        });

        //negative button
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        dialog.show();
    }

    private static class MapLoadTask extends AsyncTask<List<UserModel>, Void, HashMap<String, LatLng>> {
        private WeakReference<MainActivity> activityReference;

        MapLoadTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected HashMap<String, LatLng> doInBackground(List<UserModel>... usersLists) {
            MainActivity activity = activityReference.get();
            HashMap<String, LatLng> ll = new HashMap<>();
            if (Geocoder.isPresent()) {

                List<UserModel> usersList = usersLists[0];
                for (int i = 0; i < usersList.size(); i++) {
                    try {
                        UserModel userModel = usersList.get(i);
                        String location = userModel.getCity();
                        Geocoder gc = new Geocoder(activity);
                        List<Address> addresses = gc.getFromLocationName(location, 1); // get the found Address Objects

                        // A list to save the coordinates if they are available
                        for (Address a : addresses) {
                            if (a.hasLatitude() && a.hasLongitude()) {
                                ll.put(location, new LatLng(a.getLatitude(), a.getLongitude()));
                            }
                        }
                    } catch (IOException e) {
                        Log.e("Error", e.getMessage());
                    }
                }

            }
            return ll;
        }

        @Override
        protected void onPostExecute(HashMap<String, LatLng> result) {
            MainActivity activity = activityReference.get();
            Intent mapActivity = new Intent(activity, MapsActivity.class);
            mapActivity.putExtra("map", result);
            activity.startActivity(mapActivity);
        }
    }
}
