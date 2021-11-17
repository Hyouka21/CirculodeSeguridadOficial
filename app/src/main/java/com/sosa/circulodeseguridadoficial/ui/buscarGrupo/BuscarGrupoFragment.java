package com.sosa.circulodeseguridadoficial.ui.buscarGrupo;

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
import com.sosa.circulodeseguridadoficial.adapter.BuscarGrupoAdapter;
import com.sosa.circulodeseguridadoficial.adapter.GrupoAdapter;
import com.sosa.circulodeseguridadoficial.databinding.BuscarGrupoFragmentBinding;
import com.sosa.circulodeseguridadoficial.databinding.NotificacionFragmentBinding;
import com.sosa.circulodeseguridadoficial.entidades.Grupo;
import com.sosa.circulodeseguridadoficial.utilidades.OpcionSubcripcion;

import java.util.List;

public class BuscarGrupoFragment extends Fragment {
    private RecyclerView RVBuscar;
    private BuscarGrupoAdapter buscarGrupoAdapter;
    private BuscarGrupoViewModel mViewModel;
    private BuscarGrupoFragmentBinding binding;
    public static BuscarGrupoFragment newInstance() {
        return new BuscarGrupoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = BuscarGrupoFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RVBuscar = (RecyclerView) root.findViewById(R.id.RVGrupoBuscar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        mViewModel = new ViewModelProvider(this).get(BuscarGrupoViewModel.class);
        binding.BTBuscarRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RVBuscar.removeAllViewsInLayout();
                mViewModel.setGrupos(binding.ETNombreGrupo.getText().toString());
            }
        });
        mViewModel.getEstado().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                RVBuscar.removeAllViewsInLayout();

                mViewModel.setGrupos(binding.ETNombreGrupo.getText().toString());
            }
        });

        mViewModel.getGrupos().observe(getViewLifecycleOwner(), new Observer<List<Grupo>>() {
            @Override
            public void onChanged(List<Grupo> grupos) {
                RVBuscar.setLayoutManager(linearLayoutManager);
                buscarGrupoAdapter = new BuscarGrupoAdapter(grupos, root, getLayoutInflater(), new OpcionSubcripcion() {
                    @Override
                    public void subscribirme(Grupo grupo) {
                    mViewModel.subscribirme(grupo.getIdentificador());
                    }

                    @Override
                    public void desubscribirme(Grupo grupo) {
                    mViewModel.desubscribirme(grupo.getIdentificador());
                    }
                });

                RVBuscar.setAdapter(buscarGrupoAdapter);
            }
        });
        return root;
    }



}