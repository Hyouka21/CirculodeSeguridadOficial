package com.sosa.circulodeseguridadoficial.ui.grupo;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.databinding.CrearGrupoFragmentBinding;
import com.sosa.circulodeseguridadoficial.databinding.GrupoFragmentBinding;
import com.sosa.circulodeseguridadoficial.entidades.dto.CrearGrupos;

import java.io.ByteArrayOutputStream;

public class CrearGrupo extends Fragment {

    private CrearGrupoFragmentBinding binding;
    private CrearGrupoViewModel mViewModel;
    private ImageView imagen1;
    private CrearGrupos crearGrupos=  new CrearGrupos();
    public static CrearGrupo newInstance() {
        return new CrearGrupo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CrearGrupoFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
    imagen1 = binding.imageView2;
        mViewModel = new ViewModelProvider(this).get(CrearGrupoViewModel.class);
        binding.BTFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });
        mViewModel.getFoto().observe(getViewLifecycleOwner(), new Observer<byte[]>() {
            @Override
            public void onChanged(byte[] bytes) {
                crearGrupos.setAvatarGrupo(Base64.encodeToString(bytes, Base64.DEFAULT));
                binding.TVImagen.setVisibility(View.VISIBLE);
            }
        });
        binding.BTCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            crearGrupos.setDescripcion(binding.ETDescripcion.getText().toString());
            crearGrupos.setNombre(binding.ETNombre.getText().toString());
            mViewModel.crearGrupo(crearGrupos);
            }
        });
        mViewModel.getCrear().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                imagen1=null;
                binding.TVImagen.setVisibility(View.INVISIBLE);
                binding.ETDescripcion.setText("");
                binding.ETNombre.setText("");

            }
        });
        return root;
    }


    private void cargarImagen(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion..."),10);

    }

    private void convertirImagen(){
        BitmapDrawable drawable = (BitmapDrawable) imagen1.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        mViewModel.guardaFoto(b);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            Uri path=data.getData();
            imagen1.setImageURI(path);
            convertirImagen();
        }
    }
}