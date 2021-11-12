package com.sosa.circulodeseguridadoficial.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.sosa.circulodeseguridadoficial.entidades.UsuarioDto;
import com.sosa.circulodeseguridadoficial.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<UsuarioDto> usuario;
    private MutableLiveData<String> tokenMD;
    private MutableLiveData<Boolean> mensaje;

    private MutableLiveData<Boolean> llamar;
    private Context context;
    private boolean estado = false;
    private int contador = 0;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        usuario = new MutableLiveData<>();
        mensaje = new MutableLiveData<>();

        llamar = new MutableLiveData<>();
        tokenMD = new MutableLiveData<>();

    }
    public MutableLiveData<String> getTokenMD() {
        return tokenMD;
    }
    public MutableLiveData<UsuarioDto> getUsuario() {
        return usuario;
    }

    public MutableLiveData<Boolean> getMensaje() {
        return mensaje;
    }

    public void token(String token){

        Call<UsuarioDto> callProp = ApiClient.getMyApiClient().obtenerUsuario("Bearer "+token);
        callProp.enqueue(new Callback<UsuarioDto>() {
            @Override
            public void onResponse(Call<UsuarioDto> call, Response<UsuarioDto> response) {
                if (response.isSuccessful()) {

                    UsuarioDto p = response.body();
                    if (p != null) {

                        usuario.postValue(p);

                    } else {

                    }
                }else{
                    mensaje.postValue(true);
                }
            }

            @Override
            public void onFailure(Call<UsuarioDto> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error inesperado"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void iniciar(String email , String clave){
        Call<String> callTok = ApiClient.getMyApiClient().login(email,clave);

        callTok.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {



                    SharedPreferences sp = context.getSharedPreferences("datos",0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("token", "Bearer " + response.body());
                    editor.commit();
                    tokenMD.postValue(response.body());

                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Ocurrio un error inesperado"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void inicioAutomatico() {
        SharedPreferences sp = context.getSharedPreferences("datos",0);
        String token = sp.getString("token","-1");
        if(token!=null) {
            Call<UsuarioDto> callProp = ApiClient.getMyApiClient().obtenerUsuario(token);
            callProp.enqueue(new Callback<UsuarioDto>() {
                @Override
                public void onResponse(Call<UsuarioDto> call, Response<UsuarioDto> response) {
                    if (response.isSuccessful()) {

                        UsuarioDto p = (UsuarioDto)response.body();

                        if (p != null) {

                            usuario.postValue(p);

                        } else {

                        }
                    }else {

                    }
                }

                @Override
                public void onFailure(Call<UsuarioDto> call, Throwable t) {
                    Toast.makeText(context, "Ocurrio un error inesperado"+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
