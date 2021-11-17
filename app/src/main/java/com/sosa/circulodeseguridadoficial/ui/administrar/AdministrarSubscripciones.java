package com.sosa.circulodeseguridadoficial.ui.administrar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.adapter.SubscripcionesAdapter;
import com.sosa.circulodeseguridadoficial.databinding.AdministrarSubscripcionesFragmentBinding;
import com.sosa.circulodeseguridadoficial.entidades.Subscripcion;
import com.sosa.circulodeseguridadoficial.entidades.dto.IdentificadorDto;
import com.sosa.circulodeseguridadoficial.utilidades.EnviarDatosSubs;

import java.util.List;

public class AdministrarSubscripciones extends Fragment  {

    private AdministrarSubscripcionesViewModel mViewModel;
    private AdministrarSubscripcionesFragmentBinding binding;
    private RecyclerView RVSubscripciones;
    private SubscripcionesAdapter subscripcionesAdapter;
    private IdentificadorDto iden ;
    public static AdministrarSubscripciones newInstance() {
        return new AdministrarSubscripciones();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AdministrarSubscripcionesFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(AdministrarSubscripcionesViewModel.class);
        RVSubscripciones = (RecyclerView) root.findViewById(R.id.RVSubsAdmin);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        RVSubscripciones.setLayoutManager(linearLayoutManager);
        mViewModel.getIdentificador().observe(getViewLifecycleOwner(), new Observer<IdentificadorDto>() {
            @Override
            public void onChanged(IdentificadorDto identificadorDto) {
                iden = identificadorDto;
                mViewModel.setSubs(identificadorDto);
            }
        });
        mViewModel.getSubs().observe(getViewLifecycleOwner(), new Observer<List<Subscripcion>>() {
            @Override
            public void onChanged(List<Subscripcion> subscripcions) {

                subscripcionesAdapter = new SubscripcionesAdapter(subscripcions, root, getLayoutInflater(), new EnviarDatosSubs() {
                    @Override
                    public void enviarInfo(Subscripcion subscripcion) {
                        mViewModel.setEstado(subscripcion);
                    }
                });

                RVSubscripciones.setAdapter(subscripcionesAdapter);
            }
        });
        mViewModel.getEstado().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Toast.makeText(getContext(), "Se modifico con exito", Toast.LENGTH_SHORT).show();
                RVSubscripciones.removeAllViewsInLayout();
                mViewModel.setSubs(iden);
            }
        });
        mViewModel.setIdentificador(getArguments());
        return root;
    }



}