package com.sample.marmu.tvstask.retrofit;

import com.sample.marmu.tvstask.model.TableModel;
import com.sample.marmu.tvstask.model.TableRequest;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("gettabledata.php")
    Call<TableModel> getTableData(@Body TableRequest tableRequest);

}
