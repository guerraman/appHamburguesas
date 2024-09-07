package com.example.appsuperhamburguesas01;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class AtaquesAdapter extends RecyclerView.Adapter<AtaquesAdapter.AtaqueViewHolder> {

    private List<String> tiposAtaques;
    private Map<String, Map<String, Integer>> ataquesMap;

    public static class AtaqueViewHolder extends RecyclerView.ViewHolder {
        public TextView tipoAtaque;
        public TextView ipList;
        public TextView cantidadList;

        public AtaqueViewHolder(View itemView) {
            super(itemView);
            tipoAtaque = itemView.findViewById(R.id.tipoAtaque);
            ipList = itemView.findViewById(R.id.ipList);
            cantidadList = itemView.findViewById(R.id.cantidadList);
        }
    }

    public AtaquesAdapter(List<String> tiposAtaques, Map<String, Map<String, Integer>> ataquesMap) {
        this.tiposAtaques = tiposAtaques;
        this.ataquesMap = ataquesMap;
    }

    @NonNull
    @Override
    public AtaqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ataque, parent, false);
        return new AtaqueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AtaqueViewHolder holder, int position) {
        String tipoAtaque = tiposAtaques.get(position);
        holder.tipoAtaque.setText(tipoAtaque);

        // Obtener el mapa interno de IPs y cantidad para el tipo de ataque actual
        Map<String, Integer> innerMap = ataquesMap.get(tipoAtaque);
        StringBuilder ipList = new StringBuilder();
        StringBuilder cantidadList = new StringBuilder();
        for (Map.Entry<String, Integer> entry : innerMap.entrySet()) {
            ipList.append(entry.getKey()).append("\n");
            cantidadList.append(entry.getValue()).append("\n");
        }
        holder.ipList.setText(ipList.toString());
        holder.cantidadList.setText(cantidadList.toString());
    }

    @Override
    public int getItemCount() {
        return tiposAtaques.size();
    }
}
