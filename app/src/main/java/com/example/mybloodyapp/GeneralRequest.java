package com.example.mybloodyapp;

import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.mybloodyapp.adapter.EmptySpinnerAdapter;
import com.example.mybloodyapp.data.model.city.City;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralRequest {
    public static void getSpinnerData(Call<City> call, final Spinner spinner, final EmptySpinnerAdapter adapter, final Integer selectedId,
                                final String hint, final AdapterView.OnItemSelectedListener listener) {

        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    adapter.setData(response.body().getData(),hint);
                    spinner.setAdapter(adapter);
                    for (int i = 0; i < adapter.generalResponseDataList.size(); i++) {
                        if (adapter.generalResponseDataList.get(i).getId()==selectedId){
                            spinner.setSelection(i);

                            break;
                        }
                    }
                    spinner.setOnItemSelectedListener(listener);
                }
                catch (Exception e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

            }
        });
    }

    public static void getSpinnerData(Call<City> call, final Spinner spinner, final EmptySpinnerAdapter adapter, final Integer selectedId,
                                final String hint) {

        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {
                    adapter.setData(response.body().getData(),hint);
                    spinner.setAdapter(adapter);
                    for (int i = 0; i < adapter.generalResponseDataList.size(); i++) {
                        if (adapter.generalResponseDataList.get(i).getId()==selectedId){
                            spinner.setSelection(i);

                            break;
                        }
                    }
                }
                catch (Exception e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

            }
        });
    }
}
