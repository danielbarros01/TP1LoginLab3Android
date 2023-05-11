package ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.Optional;

import models.Usuario;
import request.ApiClient;
import ui.registro.RegistroActivity;

public class ViewModelMain extends AndroidViewModel {

    private Context context;

    public ViewModelMain(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void login(String mail, String password){
        try {
            Usuario usuario = ApiClient.login(context, mail, password);

            if(usuario != null) {
                Intent intent = new Intent(context, RegistroActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("login", true);
                context.startActivity(intent);
            }else{
                Toast.makeText(context, "Usuario incorrecto o inexistente", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(context, "Error al iniciar sesion" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void mensaje(String mensaje){
        String msg = Optional.ofNullable(mensaje)
                .orElse("Bienvenido");
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
