package ui.registro;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import models.Usuario;
import request.ApiClient;
import ui.login.MainActivity;

public class ViewModelRegistro extends AndroidViewModel {

    private Context context;
    private MutableLiveData<Usuario> userMutable = new MutableLiveData<Usuario>();

    public ViewModelRegistro(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Usuario> getUsuario() {
        if (userMutable == null) {
            this.userMutable = new MutableLiveData<>();
        }

        return userMutable;
    }

    public void registrar(Usuario usuario) {
        try {
            String msg = mensaje(usuario);

            ApiClient.guardar(context, usuario);

            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("msg", String.format(msg," con exito"));
            context.startActivity(intent);
        } catch (Exception e) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("msg", "Error al cargar los datos");
            context.startActivity(intent);
        }
    }

    public void login(Boolean login) {
        Usuario user = login ? ApiClient.leer(context) : null;

       if (user != null) {
            userMutable.setValue(user);
        }
    }

    public String mensaje(Usuario usuario){
        String msg = "";
        try {
            msg = ApiClient.leer(context).getDni() == usuario.getDni()
                    ? "Usuario actualizado"
                    : "Usuario guardado";

        }catch (NullPointerException e){
            msg = "Usuario guardado";
        }

        return msg;
    }
}
