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
import com.sosa.circulodeseguridadoficial.utilidades.OpcionSubcripcion;

import java.util.List;

public class BuscarGrupoAdapter extends RecyclerView.Adapter<BuscarGrupoAdapter.MiViewHolder>{
    private List<Grupo> lista;
    private View root ;
    private LayoutInflater inflater;
    private OpcionSubcripcion opcion;

    public BuscarGrupoAdapter(List<Grupo> lista, View root, LayoutInflater inflater,OpcionSubcripcion opcionSubcripcion) {
        this.lista = lista;
        this.root = root;
        this.inflater = inflater;
        opcion=opcionSubcripcion;
    }


    @NonNull
    @Override
    public BuscarGrupoAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_grupobuscar, parent, false);
        return new BuscarGrupoAdapter.MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuscarGrupoAdapter.MiViewHolder holder, int position) {

        Grupo i = lista.get(position);
        holder.TVNombre.setText(i.getNombre());
        holder.TVDetalle.setText(i.getDescripcion());
        Glide.with(root.getContext())//contexto
                .load(i.getAvatarGrupo())//url de la imagen
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
               .diskCacheStrategy(DiskCacheStrategy.ALL)// guarda en el cache
               .into(holder.imagen); // se encarga de setear la imagen
        if(i.isEstado()){

            holder.BTSubscri.setText("Eliminar Subscripcion");
            holder.BTSubscri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // poner el accion en interfaz
                    opcion.desubscribirme(i);
                }
            });
        }else{
            holder.BTSubscri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // poner el accion en interfaz
                    opcion.subscribirme(i);
                }
            });
        }

    };


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen;
        private TextView TVDetalle ,TVNombre;
        private Button BTSubscri;
        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.TVCVImagenGB);
            TVNombre = itemView.findViewById(R.id.TVCVNombreGB);
            TVDetalle = itemView.findViewById(R.id.TVCVDetalleGB);
            BTSubscri= itemView.findViewById(R.id.BTSubs);

        }
    }
}