package com.sosa.circulodeseguridadoficial.ui.administrar;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sosa.circulodeseguridadoficial.entidades.Grupo;
import com.sosa.circulodeseguridadoficial.entidades.Subscripcion;
import com.sosa.circulodeseguridadoficial.entidades.dto.EditarSubscripcionDto;
import com.sosa.circulodeseguridadoficial.entidades.dto.IdentificadorDto;
import com.sosa.circulodeseguridadoficial.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdministrarSubscripcionesViewModel extends AndroidViewModel {
    private  MutableLiveData<Integer> estado;
    private MutableLiveData<IdentificadorDto> identificadorDto;
    private MutableLiveData<List<Subscripcion>> subs;
    private Context context;
    private IdentificadorDto identifo;

    public AdministrarSubscripcionesViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<Integer> getEstado(){
        if(estado==null){
            estado=new MutableLiveData<>();
        }
        return estado;
    }
    public LiveData<IdentificadorDto> getIdentificador(){
        if(identificadorDto==null){
            identificadorDto=new MutableLiveData<>();
        }
        return identificadorDto;
    }

    public LiveData<List<Subscripcion>> getSubs() {
        if(subs==null){
            subs = new MutableLiveData<>();
        }
        return subs;
    }
    public void setIdentificador(Bundle bundle){
        Grupo g = (Grupo) bundle.getSerializable("grupo");
        identifo=new IdentificadorDto(g.getIdentificador());
        identificadorDto.setValue(identifo);
    }
    public void setSubs(IdentificadorDto identificadorDto){
        SharedPreferences sp = context.getSharedPreferences("datos",0);
        String token = sp.getString("token","-1");
        Call<List<Subscripcion>> inm = ApiClient.getMyApiClient().obtenerSubscripciones(token,identificadorDto);
        inm.enqueue(new Callback<List<Subscripcion>>() {
            @Override
            public void onResponse(Call<List<Subscripcion>> call, Response<List<Subscripcion>> response) {
                if(response.isSuccessful()){
                    subs.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Subscripcion>> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error inesperado"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void setEstado(Subscripcion subscripcion) {
        SharedPreferences sp = context.getSharedPreferences("datos",0);
        String token = sp.getString("token","-1");
        EditarSubscripcionDto editarSubscripcionDto = new EditarSubscripcionDto(subscripcion.getEmail(),identifo.getIdentificador(),!subscripcion.isEstado());
        Call<Integer> inm = ApiClient.getMyApiClient().editarSubscripcion(token,editarSubscripcionDto);
        inm.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
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