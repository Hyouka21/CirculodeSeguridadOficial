package com.sosa.circulodeseguridadoficial.ui.grupo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sosa.circulodeseguridadoficial.entidades.Grupo;
import com.sosa.circulodeseguridadoficial.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisGruposViewModel extends AndroidViewModel {
private Context context;
private MutableLiveData<List<Grupo>> grupos;
    public MisGruposViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }
    public MutableLiveData<List<Grupo>> getGrupos() {
        if(grupos==null){
            grupos = new MutableLiveData<>();
        }
        return grupos;
    }
    public void setGrupos(){
        SharedPreferences sp = context.getSharedPreferences("datos",0);
        String token = sp.getString("token","-1");
        Call<List<Grupo>> inm = ApiClient.getMyApiClient().obtenerGrupos(token);
        inm.enqueue(new Callback<List<Grupo>>() {
            @Override
            public void onResponse(Call<List<Grupo>> call, Response<List<Grupo>> response) {
                if(response.isSuccessful()){
                    grupos.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Grupo>> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error inesperado"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}