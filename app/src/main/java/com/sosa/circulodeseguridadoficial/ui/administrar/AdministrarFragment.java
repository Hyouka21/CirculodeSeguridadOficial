package com.sosa.circulodeseguridadoficial.ui.administrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.adapter.GrupoAdapter;
import com.sosa.circulodeseguridadoficial.adapter.GrupoAdminAdapter;
import com.sosa.circulodeseguridadoficial.databinding.FragmentAdministrarBinding;
import com.sosa.circulodeseguridadoficial.entidades.Grupo;

import java.util.List;


public class AdministrarFragment extends Fragment {

    private AdministrarViewModel mViewModel;
    private FragmentAdministrarBinding binding;
    private RecyclerView RVAdminGrupos;
    private GrupoAdminAdapter grupoAdminAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(AdministrarViewModel.class);

        binding = FragmentAdministrarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RVAdminGrupos = (RecyclerView) root.findViewById(R.id.RVAdmin);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        mViewModel.getGrupos().observe(getViewLifecycleOwner(), new Observer<List<Grupo>>() {
            @Override
            public void onChanged(List<Grupo> grupos) {

                RVAdminGrupos.setLayoutManager(linearLayoutManager);
                grupoAdminAdapter = new GrupoAdminAdapter(grupos,root,getLayoutInflater());

                RVAdminGrupos.setAdapter(grupoAdminAdapter);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.setGrupos();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}