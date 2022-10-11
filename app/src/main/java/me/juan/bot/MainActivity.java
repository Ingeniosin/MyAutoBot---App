package me.juan.bot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.juan.bot.configuration.Configuration;
import me.juan.bot.models.Asesor;
import me.juan.bot.models.LocalData;
import me.juan.bot.api.actions.GetAsesorData;
import me.juan.bot.api.ServiceResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.main);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            LocalData localData = Configuration.getLocalData(this);
            if(localData != null) {
                GetAsesorData checkAsesorCode = new GetAsesorData();
                final ServiceResponse<Asesor> response = checkAsesorCode.execute(localData.getAsesor().getCodigo());
                if(response.isValid()) {
                    localData = new LocalData(response.getValue());
                    Configuration.saveLocalData(this, localData);
                } else {
                    localData = null;
                    Configuration.saveLocalData(this, null);
                }
            }
            Intent intent = localData == null ? new Intent(this, AuthActivity.class) : new Intent(this, HomeActivity.class);
            runOnUiThread(() -> {
                startActivity(intent);
                finish();
            });
        });
    }
}