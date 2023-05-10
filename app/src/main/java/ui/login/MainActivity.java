package ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.examplelogin.R;
import com.example.examplelogin.databinding.ActivityMainBinding;

import ui.registro.RegistroActivity;

public class MainActivity extends AppCompatActivity {

    private ViewModelMain vm;
    private ActivityMainBinding binding;

    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnLogin = binding.btnLogin;
        btnRegister = binding.btnRegister;


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
    }
}