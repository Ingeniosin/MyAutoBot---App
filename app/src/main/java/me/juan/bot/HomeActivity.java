package me.juan.bot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import lombok.Getter;
import me.juan.bot.configuration.Configuration;
import me.juan.bot.models.Asesor;
import me.juan.bot.models.LocalData;
import me.juan.bot.services.NotificationLService;

public class HomeActivity extends AppCompatActivity {

    private Button logOutBtn, checkPermsBtn;
    private TextView nombreText, cantidadRegistrosText, estadoServidorText, marcasText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalData localData = Configuration.getLocalData(this);

/*        if(!localData.isGranted()) {
            checkPermissions();
            localData.setGranted(true);
            Configuration.saveLocalData(this, localData);
        }*/

        localData = Configuration.getLocalData(this);

        setContentView(R.layout.home);
        init(localData);

        Intent intent = new Intent(this, NotificationLService.class);
        startService(intent);


    }

    private void checkPermissions() {
        int i = ContextCompat.checkSelfPermission(this, Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE);
        boolean isGranted = i == PackageManager.PERMISSION_GRANTED;
        if(!isGranted) {
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        }
    }


    @SuppressLint("SetTextI18n")
    private void init(LocalData localData) {
        logOutBtn = findViewById(R.id.logOut);
        nombreText = findViewById(R.id.nombre);
        cantidadRegistrosText = findViewById(R.id.cantidadRegistros);
        estadoServidorText = findViewById(R.id.estadoServidor);
        marcasText = findViewById(R.id.marcas);
        checkPermsBtn = findViewById(R.id.checkPerms);

        Asesor asesor = Objects.requireNonNull(localData).getAsesor();
        nombreText.setText(asesor.getNombre() + " " + asesor.getApellido());
        cantidadRegistrosText.setText(String.valueOf(asesor.getCantidadRegistros()));
        marcasText.setText(asesor.getMarcas());
        estadoServidorText.setText("Normal");

        checkPermsBtn.setOnClickListener(v -> checkPermissions());

        logOutBtn.setOnClickListener(v -> {
            Configuration.saveLocalData(this, null);
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    @Override
    public void onBackPressed() {    }

}