package com.example.appsuperhamburguesas01;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GuardadosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GuardadoAdapter guardadoAdapter;
    private List<Activo> guardadoList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardados);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        guardadoList = new ArrayList<>();
        guardadoAdapter = new GuardadoAdapter(guardadoList, this);
        recyclerView.setAdapter(guardadoAdapter);

        databaseHelper = new DatabaseHelper(this);
        loadGuardados();
    }

    private void loadGuardados() {
        guardadoList.clear();
        Cursor cursor = databaseHelper.getAllActivos();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String sistemaOperativo = cursor.getString(cursor.getColumnIndexOrThrow("sistema_operativo"));
                Activo activo = new Activo();
                activo.setId(id);
                activo.setNombre_de_activo(nombre);
                activo.setSistema_operativo(sistemaOperativo);
                guardadoList.add(activo);
            }
            cursor.close();
        }
        guardadoAdapter.notifyDataSetChanged();
    }
}