package com.sosa.circulodeseguridadoficial.servicios;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.sosa.circulodeseguridadoficial.entidades.Grupo;
import com.sosa.circulodeseguridadoficial.entidades.dto.CrearLocalizacion;
import com.sosa.circulodeseguridadoficial.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioLocalizacion extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private GoogleApiClient apiClient;
    private String token;
    private Thread hilo;
    private Location loca;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Guardado", "Con exito1");



        token = intent.getStringExtra("token");
        Log.d("Guardado", token);
        hili();
        obtenerLocalizacion();
        hilo.start();
        apiClient.connect();
        return super.onStartCommand(intent, flags, startId);
    }
    public void hili(){
        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Log.d("Guardado", "Con exito7");
                        if (loca != null) {
                            float x =(float) loca.getLatitude();
                            float y = (float)loca.getLongitude();
                            CrearLocalizacion crear = new CrearLocalizacion(x,y);
                            Call<Integer> inm = ApiClient.getMyApiClient().enviarLocalizacion(token,crear);
                            inm.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if (response.isSuccessful()) {
                                        Log.d("Guardado", "Con exito");

                                    }

                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Ocurrio un error inesperado" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        apiClient.disconnect();
                        obtenerLocalizacion();
                        apiClient.connect();
                        Thread.sleep(40000);


                    }

                } catch(InterruptedException e){
                    e.printStackTrace();
                }


            }
        });

    }


    @Override
    public void onDestroy() {

        super.onDestroy();
        hilo.interrupt();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    private void obtenerLocalizacion() {
        Log.d("Guardado", "Con exito5");
        apiClient = new GoogleApiClient.Builder(this)

                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        FusedLocationProviderClient cliente = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> tarea = cliente.getLastLocation();
        tarea.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    Log.d("Guardado", "Con exito2");
                    loca =location;
                  //  Toast.makeText(MainActivity.this, "Latitud " + location.getLatitude() + " Longitud " + location.getLongitude(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Guardado", "Con exito3");
       // Toast.makeText(MainActivity.this,"Servicio Suspendido",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Guardado", "Con exito4");
     //   Toast.makeText(MainActivity.this,"Servicio Fallido",Toast.LENGTH_LONG).show();

    }
}
