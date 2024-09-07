package com.example.appsuperhamburguesas01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ActivoAdapter extends RecyclerView.Adapter<ActivoAdapter.ActivoViewHolder> {

    private List<Activo> activos;
    private Context context;
    private DatabaseHelper databaseHelper;

    public ActivoAdapter(List<Activo> activos, Context context) {
        this.activos = activos;
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ActivoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activo, parent, false);
        return new ActivoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivoViewHolder holder, int position) {
        Activo activo = activos.get(position);
        holder.tvNombre.setText(activo.getNombre_de_activo());
        holder.tvSistemaOperativo.setText(activo.getSistema_operativo());

        holder.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = databaseHelper.addActivo(activo.getNombre_de_activo(), activo.getSistema_operativo());
                if (isInserted) {
                    Toast.makeText(context, "Activo guardado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al guardar activo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return activos.size();
    }

    public static class ActivoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvSistemaOperativo;
        Button btnGuardar;

        public ActivoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvSistemaOperativo = itemView.findViewById(R.id.tvSistemaOperativo);
            btnGuardar = itemView.findViewById(R.id.btnGuardar);
        }
    }
}