package com.sosa.circulodeseguridadoficial.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sosa.circulodeseguridadoficial.entidades.dto.CrearGrupos;
import com.sosa.circulodeseguridadoficial.entidades.dto.RegistrarUsuarioDto;
import com.sosa.circulodeseguridadoficial.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarViewModel extends AndroidViewModel {

    private MutableLiveData<byte[]> foto;
    private MutableLiveData<Integer> crear;
    private Context context;

    public RegistrarViewModel(@NonNull Application application) {
        super(application);
        this.context = application.getApplicationContext();

    }


    public LiveData<Integer> getCrear() {
        if (crear == null) {
            crear = new MutableLiveData<>();
        }
        return crear;
    }

    public LiveData<byte[]> getFoto() {
        if (foto == null) {
            foto = new MutableLiveData<>();
        }
        return foto;
    }

    public void guardaFoto(byte[] b) {
        foto.setValue(b);
    }


    public void crearRegistro(RegistrarUsuarioDto registrarUsuarioDto) {
        if (foto.getValue() != null) {

            SharedPreferences sp = context.getSharedPreferences("datos", 0);
            String token = sp.getString("token", "-1");
            Call<Integer> inm = ApiClient.getMyApiClient().registrarUsuario(token, registrarUsuarioDto);
            inm.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        crear.postValue(response.body());
                        Toast.makeText(context, "Se guardo con exito", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("paso", response.code() + " " + response.message() + " " + response.body());
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(context, "hubo un error inesperado" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "debe elegir una foto", Toast.LENGTH_SHORT).show();
        }
    }
}