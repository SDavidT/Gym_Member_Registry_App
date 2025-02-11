package com.example.registropegasusgym.CardView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registropegasusgym.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UsuariosAdaptador extends RecyclerView.Adapter<UsuariosAdaptador.ViewHolder>  {


    private List<Usuarios> usuariosList;
    private  Context context ;
    ///
    private List<Usuarios> usuariosList2;


    final UsuariosAdaptador.OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(Usuarios item);
    }

    public UsuariosAdaptador(List<Usuarios> usuariosList, Context context, UsuariosAdaptador.OnItemClickListener listener) {
        this.usuariosList = usuariosList;
        this.context = context;
        this.listener=listener;
        /////
        usuariosList2=new ArrayList<>();
        usuariosList2.addAll(usuariosList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_usuarios,parent,false);
        return new UsuariosAdaptador.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNombre.setText(usuariosList.get(position).getNombre());
        holder.txtCedula.setText(usuariosList.get(position).getCedula());
        holder.txtEstado.setText(usuariosList.get(position).getEstado());
        holder.itemView.setOnClickListener(view -> {
            listener.onItemClick(usuariosList.get(position));
        });

    }

    /////
    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            usuariosList.clear();
            usuariosList.addAll(usuariosList2);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Usuarios> collecion = usuariosList.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                usuariosList.clear();
                usuariosList.addAll(collecion);
            } else {
                for (Usuarios c : usuariosList2) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        usuariosList.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();

    }

    /////
    public void filtrado2(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            usuariosList.clear();
            usuariosList.addAll(usuariosList2);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Usuarios> collecion = usuariosList.stream()
                        .filter(i -> i.getEstado().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                usuariosList.clear();
                usuariosList.addAll(collecion);
            } else {
                for (Usuarios c : usuariosList2) {
                    if (c.getEstado().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        usuariosList.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();

    }

    ////

    @Override
    public int getItemCount() {
        return usuariosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNombre;
        private TextView txtCedula;
        private TextView txtEstado;


         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             txtNombre=itemView.findViewById(R.id.txt_nombre);
             txtCedula=itemView.findViewById(R.id.txt_cedula);
             txtEstado=itemView.findViewById(R.id.txt_estado);
         }
     }



}
