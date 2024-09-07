package com.example.appsuperhamburguesas01;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServidoresActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button addButton;
    private DBHelper dbHelper;
    private List<Servidor> servidorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servidores);

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addButton);

        dbHelper = new DBHelper(this);
        servidorList = dbHelper.getAllServidores();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ServidorAdapter(servidorList, this);
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_servidor, null);
        builder.setView(dialogView);

        final EditText nombreEditText = dialogView.findViewById(R.id.nombreEditText);
        final EditText descripcionEditText = dialogView.findViewById(R.id.descripcionEditText);

        builder.setTitle("Agregar Servidor")
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nombre = nombreEditText.getText().toString();
                        String descripcion = descripcionEditText.getText().toString();
                        dbHelper.addServidor(nombre, descripcion);
                        refreshList();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public void showEditDialog(final int id, String nombre, String descripcion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_servidor, null);
        builder.setView(dialogView);

        final EditText nombreEditText = dialogView.findViewById(R.id.nombreEditText);
        final EditText descripcionEditText = dialogView.findViewById(R.id.descripcionEditText);

        nombreEditText.setText(nombre);
        descripcionEditText.setText(descripcion);

        builder.setTitle("Editar Servidor")
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newNombre = nombreEditText.getText().toString();
                        String newDescripcion = descripcionEditText.getText().toString();
                        dbHelper.updateServidor(id, newNombre, newDescripcion);
                        refreshList();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public void deleteServidor(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Servidor")
                .setMessage("¿Está seguro de que desea eliminar este servidor?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteServidor(id);
                        refreshList();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void refreshList() {
        servidorList = dbHelper.getAllServidores();
        adapter = new ServidorAdapter(servidorList, this);
        recyclerView.setAdapter(adapter);
    }
}
