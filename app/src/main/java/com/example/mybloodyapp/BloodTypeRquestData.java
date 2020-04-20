package com.example.mybloodyapp;

import android.content.Context;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mybloodyapp.adapter.EmptySpinnerAdapter;
import com.example.mybloodyapp.data.model.city.City;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mybloodyapp.data.api.RetrofitClient.getClient;

public class BloodTypeRquestData {
    public static void getSpinnerBloodTypeData(final Spinner spinner, final EmptySpinnerAdapter spinnerAdapter, final String hint, final Context context){
        getClient().getBloodType().enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    if (response.body().getStatus()==1){
                        spinnerAdapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(spinnerAdapter);
                    }
                }
                catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(context, "check your internet connection", Toast.LENGTH_LONG).show();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
