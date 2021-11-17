package com.sosa.circulodeseguridadoficial.ui.evento;

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
import com.sosa.circulodeseguridadoficial.adapter.GrupoAdapter;
import com.sosa.circulodeseguridadoficial.databinding.CrearEventoFragmentBinding;
import com.sosa.circulodeseguridadoficial.databinding.EventosFragmentBinding;
import com.sosa.circulodeseguridadoficial.entidades.Evento;
import com.sosa.circulodeseguridadoficial.entidades.Grupo;
import com.sosa.circulodeseguridadoficial.entidades.dto.IdentificadorDto;

import java.util.List;

public class EventosFragment extends Fragment {

    private EventosViewModel mViewModel;
    private EventosFragmentBinding binding;
    private RecyclerView RVEventos;
    private EventoAdapter eventoAdapter;
    private IdentificadorDto identificadorDto;
    public static EventosFragment newInstance() {
        return new EventosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = EventosFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(EventosViewModel.class);
        RVEventos = (RecyclerView) root.findViewById(R.id.RVEvento);

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
        mViewModel.getEventos().observe(getViewLifecycleOwner(), new Observer<List<Evento>>() {
            @Override
            public void onChanged(List<Evento> eventos) {

                RVEventos.setLayoutManager(linearLayoutManager);
                eventoAdapter = new EventoAdapter(eventos,root,getLayoutInflater());

                RVEventos.setAdapter(eventoAdapter);
            }
        });
        mViewModel.setIdentificador(getArguments());
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.setEventos(identificadorDto);
    }
}