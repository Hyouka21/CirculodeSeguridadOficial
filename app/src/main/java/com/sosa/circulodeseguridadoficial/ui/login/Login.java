package com.sosa.circulodeseguridadoficial.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sosa.circulodeseguridadoficial.MainActivity;
import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.entidades.UsuarioDto;

public class Login extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private EditText emailE;
    private EditText claveE;
    private Button iniciarB;
    private TextView mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        inicializar();
        loginViewModel.inicioAutomatico();
        loginViewModel.getTokenMD().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String token) {
                loginViewModel.token(token);
            }
        });
        iniciarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.iniciar(emailE.getText().toString(),claveE.getText().toString());
            }
        });
        loginViewModel.getMensaje().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mensaje.setVisibility(View.VISIBLE);
            }
        });
        loginViewModel.getUsuario().observe(this, new Observer<UsuarioDto>() {
            @Override
            public void onChanged(UsuarioDto p) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void inicializar() {

        emailE = findViewById(R.id.ETEmail);
        claveE = findViewById(R.id.ETClave);
        iniciarB = findViewById(R.id.BTIniciar);
        mensaje = findViewById(R.id.TVMensaje);
    }

}