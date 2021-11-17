package com.sosa.circulodeseguridadoficial.ui.buscarGrupo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sosa.circulodeseguridadoficial.entidades.Grupo;
import com.sosa.circulodeseguridadoficial.entidades.dto.BuscarGrupoDto;
import com.sosa.circulodeseguridadoficial.entidades.dto.IdentificadorDto;
import com.sosa.circulodeseguridadoficial.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarGrupoViewModel  extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Grupo>> grupos;
    private MutableLiveData<Integer> estado;

    public BuscarGrupoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<Grupo>> getGrupos() {
        if (grupos == null) {
            grupos = new MutableLiveData<>();
        }
        return grupos;
    }
    public MutableLiveData<Integer> getEstado() {
        if (estado == null) {
            estado = new MutableLiveData<>();
        }
        return estado;
    }
    public void setGrupos(String nombre){
        BuscarGrupoDto buscar = new BuscarGrupoDto(nombre);
        SharedPreferences sp = context.getSharedPreferences("datos",0);
        String token = sp.getString("token","-1");
        Log.d("Excepcion","por aqui");
        Call<List<Grupo>> inm = ApiClient.getMyApiClient().buscarGrupos(token,buscar);
        inm.enqueue(new Callback<List<Grupo>>() {
            @Override
            public void onResponse(Call<List<Grupo>> call, Response<List<Grupo>> response) {
                if(response.isSuccessful()){
                    grupos.postValue(response.body());
                    Log.d("Excepcion","por aqui1");
                }
                Log.d("Excepcion","por aqui2"+response.code());
            }

            @Override
            public void onFailure(Call<List<Grupo>> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error inesperado"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void subscribirme(String identificador){
        IdentificadorDto iden = new IdentificadorDto(identificador);
        SharedPreferences sp = context.getSharedPreferences("datos",0);
        String token = sp.getString("token","-1");
        Call<Integer> inm = ApiClient.getMyApiClient().subscribirse(token,iden);
        inm.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Te subscribiste con exito", Toast.LENGTH_SHORT).show();
                    estado.postValue(response.body());

                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error inesperado"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void desubscribirme(String identificador){
        IdentificadorDto iden = new IdentificadorDto(identificador);
        SharedPreferences sp = context.getSharedPreferences("datos",0);
        String token = sp.getString("token","-1");
        Call<Integer> inm = ApiClient.getMyApiClient().desubscribirse(token,iden);
        inm.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Te desubscribiste con exito", Toast.LENGTH_SHORT).show();
                    estado.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error inesperado"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}