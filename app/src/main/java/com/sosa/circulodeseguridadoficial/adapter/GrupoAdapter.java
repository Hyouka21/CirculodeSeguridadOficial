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
import com.sosa.circulodeseguridadoficial.entidades.Grupo;

import java.util.List;

public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.MiViewHolder>{
    private List<Grupo> lista;
    private View root ;
    private LayoutInflater inflater;

    public GrupoAdapter(List<Grupo> lista, View root, LayoutInflater inflater) {
        this.lista = lista;
        this.root = root;
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public GrupoAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_grupo, parent, false);
        return new GrupoAdapter.MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrupoAdapter.MiViewHolder holder, int position) {

        Grupo i = lista.get(position);
        holder.TVNombre.setText(i.getNombre());
        holder.TVDetalle.setText(i.getDescripcion());
        Log.d("pas",i.getAvatarGrupo());
        Glide.with(root.getContext())//contexto
                .load(i.getAvatarGrupo())//url de la imagen
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
               .diskCacheStrategy(DiskCacheStrategy.ALL)// guarda en el cache
               .into(holder.imagen); // se encarga de setear la imagen
        if(!i.isEstado()){
            holder.imagen.setMaxHeight(700);
            holder.BTAdministrar.setVisibility(View.INVISIBLE);
        }
        holder.BTVerNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("grupo",i);
                Navigation.findNavController(root).navigate(R.id.notificacionFragment,bundle);
            }
        });
        holder.BTAdministrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("grupo",i);
                Navigation.findNavController(root).navigate(R.id.administrarSubscripciones,bundle);
            }
        });
        holder.BTVerEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("grupo",i);
                Navigation.findNavController(root).navigate(R.id.eventosFragment,bundle);
            }
        });
        holder.BTVerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("grupo",i);
                Navigation.findNavController(root).navigate(R.id.grupoMapaFragment,bundle);

            }
        });

    };


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen;
        private TextView TVDetalle ,TVNombre;
        private Button BTEmergencia , BTVerMapa,BTVerEvento,BTVerNotificacion,BTAdministrar;
        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.TVCVImagen);
            TVNombre = itemView.findViewById(R.id.TVCVNombre);
            TVDetalle = itemView.findViewById(R.id.TVCVDetalle);
            BTEmergencia= itemView.findViewById(R.id.BTEmergencia);
            BTVerMapa = itemView.findViewById(R.id.BTVerMapa);
            BTVerEvento = itemView.findViewById(R.id.BTVerEvento);
            BTVerNotificacion= itemView.findViewById(R.id.BTVerNotificacion);
            BTAdministrar = itemView.findViewById(R.id.BTAdministrar);
        }
    }
}