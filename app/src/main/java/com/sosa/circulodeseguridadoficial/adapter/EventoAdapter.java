package com.sosa.circulodeseguridadoficial.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.entidades.Evento;


import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.MiViewHolder> {
    private List<Evento> lista;
    private View root;
    private LayoutInflater inflater;

    public EventoAdapter(List<Evento> lista, View root, LayoutInflater inflater) {
        this.lista = lista;
        this.root = root;
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public EventoAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_evento, parent, false);
        return new EventoAdapter.MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoAdapter.MiViewHolder holder, int position) {

        Evento i = lista.get(position);
        holder.TVNombre.setText("Nombre: "+i.getNombre());
        holder.TVDescripcion.setText("Descripcion: "+i.getDescripcion());
        holder.TVFechaC.setText("Fecha de creacion: "+i.getFechaCreacion());
        holder.TVFechaF.setText("Fecha de finalizacion: "+i.getFechaFinalizacion());
        holder.TVNickName.setText("Creador: "+i.getNickName());

    }




    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {

        private TextView TVDescripcion, TVNombre,TVFechaF,TVFechaC, TVNickName;


        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            TVDescripcion = itemView.findViewById(R.id.TVDescripcionIE);
            TVNombre=itemView.findViewById(R.id.TVNombreIE);
            TVFechaC=itemView.findViewById(R.id.TVFechaCreacionIE);
            TVFechaF=itemView.findViewById(R.id.TVFechaFinalIE);
            TVNickName=itemView.findViewById(R.id.TVNickNameEI);
        }
    }
}