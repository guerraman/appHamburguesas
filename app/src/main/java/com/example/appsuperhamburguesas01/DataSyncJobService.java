package com.example.appsuperhamburguesas01;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DataSyncJobService extends JobService {

    private static final String TAG = "DataSyncJobService";
    private static final String CHANNEL_ID = "custodio_channel";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");

        // Crear un hilo para realizar el trabajo en segundo plano
        new Thread(() -> {
            try {
                // Llamar a la función para consumir datos de la API
                String apiResponse = fetchDataFromAPI();

                // Verificar si hay algún registro con custodio "Diego Rojas"
                boolean custodioEncontrado = checkCustodioEncontrado(apiResponse);

                // Mostrar notificación basada en el resultado
                if (custodioEncontrado) {
                    showNotification("CUSTODIO ENCONTRADO", "Se encontró 'Diego Rojas' como custodio del activo");
                } else {
                    showNotification("No se encontró información relacionada", "No hay registros con 'Diego Rojas' como custodio del activo");
                }

                // Terminar el trabajo y notificar que se completó
                jobFinished(params, false);

            } catch (Exception e) {
                Log.e(TAG, "Error al ejecutar el trabajo", e);
                jobFinished(params, true); // Reintentar el trabajo si falla
            }
        }).start();

        return true;  // El trabajo se está ejecutando en segundo plano
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job stopped");
        return true;  // Reintentar el trabajo si se interrumpe
    }

    // Método para consumir datos de la API
    private String fetchDataFromAPI() throws IOException {
        // URL de la API de datos
        URL url = new URL("https://www.datos.gov.co/resource/46yq-tz63.json");

        // Conexión HTTP para obtener la respuesta
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    // Método para verificar si hay un registro con "Diego Rojas" como custodio
    private boolean checkCustodioEncontrado(String apiResponse) {
        if (apiResponse != null && !apiResponse.isEmpty()) {
            try {
                JSONArray jsonArray = new JSONArray(apiResponse);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String custodio = jsonObject.optString("custodio_del_activo", "");
                    if ("Diego Rojas".equalsIgnoreCase(custodio)) {
                        return true;
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, "Error al analizar JSON", e);
            }
        }
        return false;
    }

    // Método para mostrar una notificación
    private void showNotification(String title, String message) {
        createNotificationChannel();

        // Verificar y solicitar permiso de notificaciones en tiempo de ejecución para Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Permiso de notificaciones no concedido.");
            return;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.icons8_twitter_52) // Asegúrate de que este archivo exista en res/drawable o res/mipmap
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    // Método para crear un canal de notificación para Android Oreo y superior
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
