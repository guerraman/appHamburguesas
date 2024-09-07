package com.example.appsuperhamburguesas01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GuardadoAdapter extends RecyclerView.Adapter<GuardadoAdapter.GuardadoViewHolder> {

    private List<Activo> guardados;
    private Context context;
    private DatabaseHelper databaseHelper;

    public GuardadoAdapter(List<Activo> guardados, Context context) {
        this.guardados = guardados;
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public GuardadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guardado, parent, false);
        return new GuardadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuardadoViewHolder holder, int position) {
        Activo activo = guardados.get(position);
        holder.tvNombre.setText(activo.getNombre_de_activo());
        holder.tvSistemaOperativo.setText(activo.getSistema_operativo());

        // Manejar clics en el botón de eliminar
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la posición actual del adaptador
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Activo activoToDelete = guardados.get(adapterPosition);

                    // Eliminar el activo de la base de datos y de la lista
                    boolean isDeleted = databaseHelper.deleteActivo(activoToDelete.getId());
                    if (isDeleted) {
                        guardados.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);
                        Toast.makeText(context, "Activo eliminado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error al eliminar activo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return guardados.size();
    }

    public static class GuardadoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvSistemaOperativo;
        Button btnDelete;

        public GuardadoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvSistemaOperativo = itemView.findViewById(R.id.tvSistemaOperativo);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}