package com.sosa.circulodeseguridadoficial;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.sosa.circulodeseguridadoficial.entidades.UsuarioDto;
import com.sosa.circulodeseguridadoficial.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends AndroidViewModel {
    private MutableLiveData<UsuarioDto> usuario ;
    private Context context;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<UsuarioDto> getUsuario() {
        if(usuario == null){
            usuario =  new MutableLiveData<>();
        }
        return usuario;
    }
    public void actualizarPerfil(){
        SharedPreferences sp = context.getSharedPreferences("datos",0);
        String token = sp.getString("token","-1");
        Log.d("exce" ,token);
        Call<UsuarioDto> prop = ApiClient.getMyApiClient().obtenerUsuario(token);
        prop.enqueue(new Callback<UsuarioDto>() {
            @Override
            public void onResponse(Call<UsuarioDto> call, Response<UsuarioDto> response) {
                if(response.isSuccessful()){
                    Log.d("exce3" ,response.code()+""+response.message());
                    usuario.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<UsuarioDto> call, Throwable t) {
                Log.d("exce1" ,t.getMessage());
            }
        });
    }
    public void actualizarPerfil(UsuarioDto u){

//      propietario.setValue(u);

    }


}
