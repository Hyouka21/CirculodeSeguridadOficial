package com.sosa.circulodeseguridadoficial.ui.grupo;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.adapter.GrupoAdapter;
import com.sosa.circulodeseguridadoficial.databinding.GrupoFragmentBinding;
import com.sosa.circulodeseguridadoficial.entidades.Grupo;
import com.sosa.circulodeseguridadoficial.entidades.dto.IdentificadorDto;
import com.sosa.circulodeseguridadoficial.utilidades.EmergenciaInterfaz;

import java.util.List;

public class MisGrupos extends Fragment {
    private RecyclerView RVMisGrupos;
    private GrupoAdapter grupoAdapter;

    private MisGruposViewModel mViewModel;
    private GrupoFragmentBinding binding;
    public static MisGrupos newInstance() {
        return new MisGrupos();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = GrupoFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(MisGruposViewModel.class);
        binding.BTNCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.crearGrupo);
            }
        });
        binding.BTNBuscarG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.buscarGrupoFragment);
            }
        });
        RVMisGrupos = (RecyclerView) root.findViewById(R.id.RVMisGrupos);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        mViewModel.getGrupos().observe(getViewLifecycleOwner(), new Observer<List<Grupo>>() {
            @Override
            public void onChanged(List<Grupo> grupos) {

                RVMisGrupos.setLayoutManager(linearLayoutManager);
                grupoAdapter = new GrupoAdapter(grupos, root, getLayoutInflater(), new EmergenciaInterfaz() {
                    @Override
                    public void emergenciaAccion(Grupo grupo) {
                        mViewModel.botonEmergencia(new IdentificadorDto(grupo.getIdentificador()));
                    }
                });

                RVMisGrupos.setAdapter(grupoAdapter);
            }
        });
        //mViewModel.setGrupos();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.setGrupos();
    }
}