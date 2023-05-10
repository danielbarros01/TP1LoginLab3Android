package ui.registro;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import models.Usuario;
import request.ApiClient;
import ui.login.MainActivity;

public class ViewModelRegistro extends AndroidViewModel {

    private Context context;

    public ViewModelRegistro(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void registrar(Usuario usuario) {
        //Validacion que no exista el dni
        try {
            ApiClient.guardar(context, usuario);

            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("msg", "Nuevo usuario guardado con exito");
            context.startActivity(intent);

        } catch (Exception e) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("msg", "Error al cargar nuevo usuario");
            context.startActivity(intent);
        }
    }
}
