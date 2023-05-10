package ui.registro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.examplelogin.R;
import com.example.examplelogin.databinding.ActivityMainBinding;
import com.example.examplelogin.databinding.ActivityRegistroBinding;

import models.Usuario;
import ui.login.ViewModelMain;

public class RegistroActivity extends AppCompatActivity {

    private ViewModelRegistro vm;
    private ActivityRegistroBinding binding;
    private EditText dni, nombre, apellido, mail, password;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ViewModelRegistro.class);

        dni = binding.tvDni;
        nombre = binding.tvNombre;
        apellido = binding.tvApellido;
        mail = binding.tvMail;
        password = binding.tvPassword;
        btnGuardar = binding.btnGuardar;

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario u = new Usuario(
                        nombre.getText().toString(),
                        apellido.getText().toString(),
                        mail.getText().toString(),
                        password.getText().toString(),
                        Long.parseLong(dni.getText().toString())
                );

                vm.registrar(u);
            }
        });
    }
}