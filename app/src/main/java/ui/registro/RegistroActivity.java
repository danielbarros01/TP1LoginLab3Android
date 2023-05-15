package ui.registro;

import static android.Manifest.permission.CAMERA;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.examplelogin.databinding.ActivityRegistroBinding;


import java.io.File;

import models.Usuario;
import ui.login.MainActivity;

public class RegistroActivity extends AppCompatActivity {

    private ViewModelRegistro vm;
    private ActivityRegistroBinding binding;
    private EditText dni, nombre, apellido, mail, password;
    private Button btnGuardar, btnFoto;
    private ImageView imagen;
    private static int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ViewModelRegistro.class);

        configView();

        vm.login(getIntent().getBooleanExtra("login", false));

        vm.getUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario u) {
                dni.setText(String.valueOf(u.getDni()));
                nombre.setText(u.getNombre());
                apellido.setText(u.getApellido());
                mail.setText(u.getMail());
                password.setText(u.getPassword());
                vm.cargar();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File archivo = new File(getFilesDir(), "foto1.png");

                Usuario u = new Usuario(
                        nombre.getText().toString(),
                        apellido.getText().toString(),
                        mail.getText().toString(),
                        password.getText().toString(),
                        Long.parseLong(dni.getText().toString())
                        //archivo.getAbsolutePath()
                );

                vm.registrar(u);
            }
        });

        btnFoto.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivityForResult es otra forma de iniciar una activity, pero esperando desde donde la llamé un resultado
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }));

        vm.getFoto().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                imagen.setImageBitmap(bitmap);
            }
        });
        vm.cargar();
    }

    //Este método es llamado automáticamente cuando retorna de la cámara.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        vm.respuetaDeCamara(requestCode,resultCode,data,REQUEST_IMAGE_CAPTURE);
    }

    public void configView(){
        //widgets
        dni = binding.tvDni;
        nombre = binding.tvNombre;
        apellido = binding.tvApellido;
        mail = binding.tvMail;
        password = binding.tvPassword;
        imagen = binding.ivFotoPerfil;
        btnFoto = binding.btnFoto;
        btnGuardar = binding.btnGuardar;
        //-----
    }
}