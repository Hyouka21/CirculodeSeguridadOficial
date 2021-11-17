package com.sosa.circulodeseguridadoficial.ui.notificacion;

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

import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.adapter.EventoAdapter;
import com.sosa.circulodeseguridadoficial.adapter.NotificacionAdapter;
import com.sosa.circulodeseguridadoficial.databinding.EventosFragmentBinding;
import com.sosa.circulodeseguridadoficial.databinding.NotificacionFragmentBinding;
import com.sosa.circulodeseguridadoficial.entidades.Evento;
import com.sosa.circulodeseguridadoficial.entidades.Notificacion;
import com.sosa.circulodeseguridadoficial.entidades.dto.IdentificadorDto;

import java.util.List;

public class NotificacionFragment extends Fragment {

    private NotificacionViewModel mViewModel;
    private NotificacionFragmentBinding binding;
    private RecyclerView RVNotificacion;
    private NotificacionAdapter notificacionAdapter;
    private IdentificadorDto identificadorDto;
    public static NotificacionFragment newInstance() {
        return new NotificacionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = NotificacionFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(NotificacionViewModel.class);
        RVNotificacion = (RecyclerView) root.findViewById(R.id.RVNotificacion);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        mViewModel.getIdentificador().observe(getViewLifecycleOwner(), new Observer<IdentificadorDto>() {
            @Override
            public void onChanged(IdentificadorDto identifi) {
                identificadorDto = identifi;

            }
        });
        mViewModel.getNotificaciones().observe(getViewLifecycleOwner(), new Observer<List<Notificacion>>() {
            @Override
            public void onChanged(List<Notificacion> notificacions) {

                RVNotificacion.setLayoutManager(linearLayoutManager);
                notificacionAdapter = new NotificacionAdapter(notificacions,root,getLayoutInflater());

                RVNotificacion.setAdapter(notificacionAdapter);
            }
        });
        mViewModel.setIdentificador(getArguments());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.setNotificaciones(identificadorDto);
    }
}