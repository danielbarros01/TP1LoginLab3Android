package request;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import models.Usuario;

public class ApiClient {
    private static File file;

    private static File conectar(Context context) {
        if (file == null) {
            file = new File(context.getFilesDir(), "personal.dat");
        }

        return file;
    }

    public static void guardar(Context context, Usuario usuario) {
        File archivo = conectar(context);

        try {
            FileOutputStream fs = new FileOutputStream(archivo);
            BufferedOutputStream bs = new BufferedOutputStream(fs);
            ObjectOutputStream objectStream = new ObjectOutputStream(bs);

            objectStream.writeObject(usuario);
            bs.flush();
            objectStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Usuario leer(Context context) {
        File archivo = conectar(context);
        Usuario usuario = null;

        try {
            FileInputStream fs = new FileInputStream(archivo);
            BufferedInputStream bs = new BufferedInputStream(fs);
            ObjectInputStream objectStream = new ObjectInputStream(bs);

            usuario = (Usuario) objectStream.readObject();

            objectStream.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return usuario;
    }

    public static Usuario login(Context context, String mail, String password) {
        Usuario usuario = leer(context);
        File archivo = conectar(context);

        if(usuario != null){
            if (!mail.equals(usuario.getMail()) || !password.equals(usuario.getPassword())) {
                usuario = null;
            }
        }

        return usuario;
    }
}
