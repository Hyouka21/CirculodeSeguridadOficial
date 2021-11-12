package com.sosa.circulodeseguridadoficial.ui.localizacionGrupo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.entidades.LocalizacionUsuario;
import com.sosa.circulodeseguridadoficial.entidades.dto.IdentificadorDto;
import com.sosa.circulodeseguridadoficial.ui.login.LoginViewModel;

import java.util.List;

public class GrupoMapaFragment extends Fragment {
    private GrupoMapaFragmentViewModel fragmentViewModel;
    private OnMapReadyCallback callback;
    private Thread hilo ;
    private boolean suspender=false;
    SupportMapFragment mapFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        fragmentViewModel = new ViewModelProvider(this).get(GrupoMapaFragmentViewModel.class);
        fragmentViewModel.getIdentificador().observe(getViewLifecycleOwner(), new Observer<IdentificadorDto>() {
            @Override
            public void onChanged(IdentificadorDto identificadorDto) {
                hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while(true) {

                                fragmentViewModel.obtenerLocalizaciones(identificadorDto);
                                if(suspender){
                                    wait();
                                }
                                Thread.sleep(20000);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                });
                hilo.start();

            }
        });
        fragmentViewModel.getLocalizacion().observe(getViewLifecycleOwner(), new Observer<List<LocalizacionUsuario>>() {
            @Override
            public void onChanged(List<LocalizacionUsuario> localizacionUsuarios) {
                callback= new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng latLng=null;
                        googleMap.clear();
                        for(LocalizacionUsuario locUsu : localizacionUsuarios)
                        {

                            latLng= new LatLng(locUsu.getCoordenadaX(), locUsu.getCoordenadaY());
                            googleMap.addMarker(new MarkerOptions().position(latLng)
                                    .title(locUsu.getNickName()));
                        }


//                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                        googleMap.setMinZoomPreference(15.0f);
//                        googleMap.setMaxZoomPreference(20.0f);
                    }
                };
                mapFragment.getMapAsync(callback);
            }
        });

        fragmentViewModel.setIdentificador(getArguments());
        return inflater.inflate(R.layout.fragment_grupo_mapa, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        suspender=false;
    }

    @Override
    public void onStop() {
        super.onStop();
        suspender=true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hilo.interrupt();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}