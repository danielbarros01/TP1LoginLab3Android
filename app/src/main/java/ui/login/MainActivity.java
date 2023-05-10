package ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.examplelogin.R;
import com.example.examplelogin.databinding.ActivityMainBinding;

import java.util.Optional;

import ui.registro.RegistroActivity;

public class MainActivity extends AppCompatActivity {

    private ViewModelMain vm;
    private ActivityMainBinding binding;

    private EditText mail, password;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ViewModelMain.class);


        btnLogin = binding.btnLogin;
        btnRegister = binding.btnRegister;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail = binding.etEmail;
                password = binding.etPassword;

                vm.login(
                        mail.getText().toString(),
                        password.getText().toString()
                );
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ir a otra vista
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                intent.putExtra("logueado",false);
                startActivity(intent);
            }
        });


        String msg = Optional.ofNullable(getIntent().getStringExtra("msg"))
                .orElse("Bienvenido");
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();

    }
}