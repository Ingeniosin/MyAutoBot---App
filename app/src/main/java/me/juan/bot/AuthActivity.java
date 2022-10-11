package me.juan.bot;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.juan.bot.configuration.Configuration;
import me.juan.bot.models.LocalData;
import me.juan.bot.models.Asesor;
import me.juan.bot.api.actions.GetAsesorData;
import me.juan.bot.api.ServiceResponse;

public class AuthActivity extends AppCompatActivity {

    private EditText codigoEditor;
    private Button submitButton;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.auth);
        init();
    }

    private void init() {
        codigoEditor = findViewById(R.id.codigoEditor);
        submitButton = findViewById(R.id.submit);

        submitButton.setOnClickListener(v -> {
            String codigo = codigoEditor.getText().toString();
            checkCode(codigo);
        });
    }


    private void checkCode(String codigo) {
        submitButton.setEnabled(false);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            GetAsesorData checkAsesorCode = new GetAsesorData();
            final ServiceResponse<Asesor> response = checkAsesorCode.execute(codigo);
            runOnUiThread(() -> {
                if(toast != null)
                    toast.cancel();

                String message = response.isValid() ? "Bienvenido "+ response.getValue().getNombre() + " "+ response.getValue().getApellido() : "Código incorrecto: "+ response.getErrorMessage();
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                toast.show();

                submitButton.setEnabled(true);

                if(response.isValid()) {
                    LocalData localData = new LocalData(response.getValue());
                    Configuration.saveLocalData(this, localData);
                    startActivity(new Intent(this, HomeActivity.class));
                }
            });
        });
    }

    @Override
    public void onBackPressed() {    }
}