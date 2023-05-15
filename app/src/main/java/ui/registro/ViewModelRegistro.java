package ui.registro;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import models.Usuario;
import request.ApiClient;
import ui.login.MainActivity;

public class ViewModelRegistro extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Usuario> userMutable = new MutableLiveData<Usuario>();
    private MutableLiveData<Bitmap> foto;
    private byte[] fotoBytes = new byte[0];

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

    public LiveData<Bitmap> getFoto() {
        if (foto == null) {
            foto = new MutableLiveData<>();
        }
        return foto;
    }

    public void registrar(Usuario usuario) {
        try {
            String msg = mensaje(usuario);

            if (fotoBytes.length > 0) {
                usuario.setFoto(fotoBytes);
            }

            ApiClient.guardar(context, usuario);

            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("msg", String.format(msg, " con exito"));
            context.startActivity(intent);
        } catch (Exception e) {
            Log.d("error1", e.getMessage());
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

    public String mensaje(Usuario usuario) {
        String msg = "";
        try {
            msg = ApiClient.leer(context).getDni() == usuario.getDni()
                    ? "Usuario actualizado"
                    : "Usuario guardado";

        } catch (NullPointerException e) {
            msg = "Usuario guardado";
        }

        return msg;
    }

    public void respuetaDeCamara(int requestCode, int resultCode, @Nullable Intent data, int REQUEST_IMAGE_CAPTURE) {
        Log.d("salida", requestCode + "");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Recupero los datos provenientes de la camara.
            Bundle extras = data.getExtras();
            //Casteo a bitmap lo obtenido de la camara.
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //Rutina para optimizar la foto,
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            foto.setValue(imageBitmap);

            //Rutina para convertir a un arreglo de byte los datos de la imagen
            byte[] b = baos.toByteArray();

            //Asignar el arreglo de bytes al atributo fotoBytes.
            fotoBytes = b;

            //Aquí podría ir la rutina para llamar al servicio que recibe los bytes.
            ApiClient.guardarFoto(context, b);
        }
    }

    public void cargar() {
        File archivo = new File(context.getFilesDir(), "foto1.png");

        Bitmap imageBitmap = BitmapFactory.decodeFile(archivo.getAbsolutePath());
        if (imageBitmap != null) {
            foto.setValue(imageBitmap);
        }
    }
}
