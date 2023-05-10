package ui.login;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import request.ApiClient;

public class ViewModelMain extends AndroidViewModel {

    private Context context;

    public ViewModelMain(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void login(String mail, String password){
        try {
            ApiClient.login(context, mail, password);
        }catch (Exception e){
            Toast.makeText(context, "Error al iniciar sesion" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
