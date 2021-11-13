package com.sosa.circulodeseguridadoficial.ui.administrar;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.databinding.AdministrarSubscripcionesFragmentBinding;

public class AdministrarSubscripciones extends Fragment {

    private AdministrarSubscripcionesViewModel mViewModel;
    private AdministrarSubscripcionesFragmentBinding binding;
    public static AdministrarSubscripciones newInstance() {
        return new AdministrarSubscripciones();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(AdministrarSubscripcionesViewModel.class);

        return root;
    }



}