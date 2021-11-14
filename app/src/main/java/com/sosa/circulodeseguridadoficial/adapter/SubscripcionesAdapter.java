package com.sosa.circulodeseguridadoficial.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.entidades.Subscripcion;
import com.sosa.circulodeseguridadoficial.ui.administrar.AdministrarViewModel;
import com.sosa.circulodeseguridadoficial.utilidades.EnviarDatosSubs;

import java.util.List;

public class SubscripcionesAdapter extends RecyclerView.Adapter<SubscripcionesAdapter.MiViewHolder>{
    private List<Subscripcion> lista;
    private View root ;
    private LayoutInflater inflater;
    private AdministrarViewModel mviewModel;
    private EnviarDatosSubs listen;
    public SubscripcionesAdapter(List<Subscripcion> lista, View root, LayoutInflater inflater, EnviarDatosSubs listener) {
        this.lista = lista;
        this.root = root;
        this.inflater = inflater;
        listen = listener;
    }


    @NonNull
    @Override
    public SubscripcionesAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_subscripcion, parent, false);
        return new SubscripcionesAdapter.MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscripcionesAdapter.MiViewHolder holder, int position) {


        Subscripcion i = lista.get(position);
        Log.d("ex",i.toString());
        holder.TVNombre.setText(i.getNickName());
        holder.TVEmail.setText(i.getEmail());
        String texto = null;
        if(i.isEstado()){
            holder.botonCambiar.setText("Quitar");

        }else{
            texto="No esta en el grupo";
            holder.botonCambiar.setText("Agregar");
        }
        holder.TVEstado.setText(texto);
        Glide.with(root.getContext())//contexto
                .load(i.getAvatar())//url de la imagen
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
               .diskCacheStrategy(DiskCacheStrategy.ALL)// guarda en el cache
               .into(holder.imagen); // se encarga de setear la imagen

        holder.botonCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listen.enviarInfo(i);

            }
        });

    };


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen;
        private Button botonCambiar;
        private TextView TVEmail ,TVNombre,TVEstado;
        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            botonCambiar = itemView.findViewById(R.id.BTUsuSub);
            imagen = itemView.findViewById(R.id.AvatarUsuSubs);
            TVNombre = itemView.findViewById(R.id.TVNombreUsuSub);
            TVEmail = itemView.findViewById(R.id.EmailUsuSub);
            TVEstado=itemView.findViewById(R.id.TVEstadoUsuSub);
        }
    }
}