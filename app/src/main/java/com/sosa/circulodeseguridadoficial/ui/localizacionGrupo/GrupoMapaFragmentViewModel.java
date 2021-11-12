package com.sosa.circulodeseguridadoficial.ui.localizacionGrupo;

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

import com.sosa.circulodeseguridadoficial.entidades.Grupo;
import com.sosa.circulodeseguridadoficial.entidades.LocalizacionUsuario;
import com.sosa.circulodeseguridadoficial.entidades.dto.IdentificadorDto;
import com.sosa.circulodeseguridadoficial.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrupoMapaFragmentViewModel extends AndroidViewModel {
    private MutableLiveData<IdentificadorDto> identificadorDto;
    private MutableLiveData<List<LocalizacionUsuario>> localizacionUsuario;
    private Context context;

    public GrupoMapaFragmentViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public LiveData<IdentificadorDto> getIdentificador(){
        if(identificadorDto==null){
            identificadorDto=new MutableLiveData<>();
        }
        return identificadorDto;
    }
    public LiveData<List<LocalizacionUsuario>> getLocalizacion(){
        if(localizacionUsuario==null){
            localizacionUsuario=new MutableLiveData<>();
        }
        return localizacionUsuario;
    }
    public void setIdentificador(Bundle bundle){
        Grupo g = (Grupo) bundle.getSerializable("grupo");
        identificadorDto.setValue(new IdentificadorDto(g.getIdentificador()));
    }
    public void obtenerLocalizaciones(IdentificadorDto identificadorDto){
        SharedPreferences sp = context.getSharedPreferences("datos", 0);
        String token = sp.getString("token", "-1");
        Log.d("exp",identificadorDto.getIdentificador()+"por aqui");
        Call<List<LocalizacionUsuario>> inm = ApiClient.getMyApiClient().obtenerLocalizaciones(token, identificadorDto);
        inm.enqueue(new Callback<List<LocalizacionUsuario>>() {
            @Override
            public void onResponse(Call<List<LocalizacionUsuario>> call, Response<List<LocalizacionUsuario>> response) {
                if (response.isSuccessful()) {
                    localizacionUsuario.postValue(response.body());
                    Toast.makeText(context, "Se obtuvo las localizaciones con exito", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("paso", response.code() + " " + response.message() + " " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<LocalizacionUsuario>> call, Throwable t) {
                Toast.makeText(context, "hubo un error inesperado" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
