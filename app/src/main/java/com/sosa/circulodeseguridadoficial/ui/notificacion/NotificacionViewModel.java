package com.sosa.circulodeseguridadoficial.ui.notificacion;

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

import com.sosa.circulodeseguridadoficial.entidades.Evento;
import com.sosa.circulodeseguridadoficial.entidades.Grupo;
import com.sosa.circulodeseguridadoficial.entidades.Notificacion;
import com.sosa.circulodeseguridadoficial.entidades.dto.IdentificadorDto;
import com.sosa.circulodeseguridadoficial.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionViewModel  extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Notificacion>> notificaciones;
    private MutableLiveData<IdentificadorDto> identificadorDto;
    public NotificacionViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }
    public LiveData<IdentificadorDto> getIdentificador(){
        if(identificadorDto==null){
            identificadorDto=new MutableLiveData<>();
        }
        return identificadorDto;
    }
    public MutableLiveData<List<Notificacion>> getNotificaciones() {
        if(notificaciones==null){
            notificaciones = new MutableLiveData<>();
        }
        return notificaciones;
    }
    public void setIdentificador(Bundle bundle){
        Grupo g = (Grupo) bundle.getSerializable("grupo");
        identificadorDto.setValue(new IdentificadorDto(g.getIdentificador()));
    }
    public void setNotificaciones(IdentificadorDto identificadorDto) {
        SharedPreferences sp = context.getSharedPreferences("datos", 0);
        String token = sp.getString("token", "-1");
        Call<List<Notificacion>> inm = ApiClient.getMyApiClient().obtenerNotificacion(token, identificadorDto);
        inm.enqueue(new Callback<List<Notificacion>>() {
            @Override
            public void onResponse(Call<List<Notificacion>> call, Response<List<Notificacion>> response) {
                if (response.isSuccessful()) {
                    notificaciones.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Notificacion>> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error inesperado" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    }
