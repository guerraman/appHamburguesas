package com.example.appsuperhamburguesas01;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonitoreoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button monitoreoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        monitoreoButton = findViewById(R.id.monitoreoButton);
        monitoreoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leerArchivoYProcesar();
            }
        });
    }

    private void leerArchivoYProcesar() {
        Map<String, Map<String, Integer>> ataquesMap = new HashMap<>();

        try {
            // Obtener el AssetManager para acceder a los archivos en la carpeta assets
            AssetManager assetManager = getAssets();
            // Abrir el archivo LogSecure.txt desde los assets
            InputStream inputStream = assetManager.open("LogSecure.txt");
            // Crear un BufferedReader para leer el archivo línea por línea
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Access denied with code")) {
                    String tipoAtaque = obtenerTipoAtaque(line);
                    String ip = obtenerIP(line);

                    // Verificar si ya existe el tipo de ataque en el mapa principal
                    if (ataquesMap.containsKey(tipoAtaque)) {
                        Map<String, Integer> innerMap = ataquesMap.get(tipoAtaque);
                        // Verificar si ya existe la IP en el mapa interno del tipo de ataque
                        if (innerMap.containsKey(ip)) {
                            innerMap.put(ip, innerMap.get(ip) + 1);
                        } else {
                            innerMap.put(ip, 1);
                        }
                    } else {
                        // Si no existe el tipo de ataque, crear un nuevo mapa interno
                        Map<String, Integer> innerMap = new HashMap<>();
                        innerMap.put(ip, 1);
                        ataquesMap.put(tipoAtaque, innerMap);
                    }
                }
            }
            // Cerrar el BufferedReader después de leer el archivo
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Actualizar el RecyclerView con los resultados
        adapter = new AtaquesAdapter(new ArrayList<>(ataquesMap.keySet()), ataquesMap);
        recyclerView.setAdapter(adapter);
    }

    private String obtenerTipoAtaque(String line) {
        // Implementa la lógica para extraer el tipo de ataque de la línea
        // Este es un ejemplo básico, tendrás que adaptarlo según el formato real de tus logs
        String[] parts = line.split(": ");
        if (parts.length > 1) {
            return parts[1].trim(); // Devuelve el tipo de ataque encontrado
        }
        return "Tipo de ataque desconocido";
    }

    private String obtenerIP(String line) {
        // Implementa la lógica para extraer la IP de la línea
        // Utiliza expresiones regulares para encontrar la IP en el formato adecuado
        String ipPattern = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b";
        Pattern pattern = Pattern.compile(ipPattern);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group();
        }
        return "IP desconocida";
    }
}
