package com.example.appsuperhamburguesas01;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServidorAdapter extends RecyclerView.Adapter<ServidorAdapter.ServidorViewHolder> {

    private List<Servidor> servidorList;
    private ServidoresActivity activity;

    public static class ServidorViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreTextView;
        public TextView descripcionTextView;
        public ImageButton editButton;
        public ImageButton deleteButton;

        public ServidorViewHolder(View v) {
            super(v);
            nombreTextView = v.findViewById(R.id.nombreTextView);
            descripcionTextView = v.findViewById(R.id.descripcionTextView);
            editButton = v.findViewById(R.id.editButton);
            deleteButton = v.findViewById(R.id.deleteButton);
        }
    }

    public ServidorAdapter(List<Servidor> servidorList, ServidoresActivity activity) {
        this.servidorList = servidorList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ServidorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_servidor, parent, false);
        return new ServidorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServidorViewHolder holder, int position) {
        final Servidor servidor = servidorList.get(position);
        holder.nombreTextView.setText(servidor.getNombre());
        holder.descripcionTextView.setText(servidor.getDescripcion());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showEditDialog(servidor.getId(), servidor.getNombre(), servidor.getDescripcion());
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.deleteServidor(servidor.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return servidorList.size();
    }
}
