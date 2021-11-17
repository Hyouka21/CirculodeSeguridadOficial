package com.sosa.circulodeseguridadoficial.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.entidades.Evento;
import com.sosa.circulodeseguridadoficial.entidades.Notificacion;

import java.util.List;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.MiViewHolder> {
    private List<Notificacion> lista;
    private View root;
    private LayoutInflater inflater;

    public NotificacionAdapter(List<Notificacion> lista, View root, LayoutInflater inflater) {
        this.lista = lista;
        this.root = root;
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public NotificacionAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_notificacion, parent, false);
        return new NotificacionAdapter.MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacionAdapter.MiViewHolder holder, int position) {

        Notificacion i = lista.get(position);
        Glide.with(root.getContext())//contexto
                .load(i.getAvatar())//url de la imagen
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)// guarda en el cache
                .into(holder.imagen);
      //  holder.TVNombre.setText("Nombre: "+i.getNombreUsuario());
        holder.TVTitulo.setText(i.getTitulo());
        holder.TVFecha.setText("Fecha de creacion: "+i.getFechaCreacion());
        holder.TVMensaje.setText(i.getMensaje());

    }




    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagen;

        private TextView TVTitulo,TVMensaje, TVNombre,TVFecha;


        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            TVTitulo = itemView.findViewById(R.id.TVTituloIN);
          //  TVNombre=itemView.findViewById(R.id.TVNombreIN);
            TVMensaje=itemView.findViewById(R.id.TVMensajeIN);
            TVFecha=itemView.findViewById(R.id.TVFechaCIN);
            imagen=itemView.findViewById(R.id.IVAvatarIN);
        }
    }
}