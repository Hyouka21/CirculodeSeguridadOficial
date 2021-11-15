package com.sosa.circulodeseguridadoficial.ui.evento;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sosa.circulodeseguridadoficial.entidades.dto.CrearEventoDto;
import com.sosa.circulodeseguridadoficial.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearEventoViewModel extends AndroidViewModel {

 private Context context;
 private MutableLiveData<Integer> crear;
    public CrearEventoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<Integer> getCrear(){
        if(crear==null){
            crear = new MutableLiveData<>();
        }
        return crear;
    }

    public void crearEvento(CrearEventoDto crearEventoDto) {
        Log.d("exce", "crearGrupo: aqui");
        SharedPreferences sp = context.getSharedPreferences("datos", 0);
        String token = sp.getString("token", "-1");
        Call<Integer> inm = ApiClient.getMyApiClient().crearEvento(token, crearEventoDto);
        inm.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    crear.postValue(response.body());

                } else {
                    Log.d("paso", response.code() + " " + response.message() + " " + response.body());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context, "hubo un error inesperado" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}