package com.example.appsuperhamburguesas01;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    private static final int JOB_ID = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar los views
        editTextUsername = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword2);
        buttonLogin = findViewById(R.id.button);

        // Configurar el padding para soportar insets del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configurar el listener del botón de login
        buttonLogin.setOnClickListener(v -> handleLogin());

        // Programar el JobScheduler
        scheduleJob();
    }

    private void handleLogin() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor, ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
        } else {
            new LoginTask().execute(username, password);
        }
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            // Simulando una operación en segundo plano (e.g., llamada a una API)
            try {
                Thread.sleep(2000);  // Simulando una operación que toma tiempo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "admin".equals(username) && "admin".equals(password);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void scheduleJob() {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        ComponentName componentName = new ComponentName(this, DataSyncJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(2 * 60 * 1000); // Ejecutar cada 2 minutos

        // Programar el trabajo
        if (jobScheduler != null) {
            jobScheduler.schedule(builder.build());
            Log.d("MainActivity", "JobScheduler programado cada 2 minutos");
        } else {
            Log.e("MainActivity", "No se pudo obtener el servicio JobScheduler");
        }
    }
}
