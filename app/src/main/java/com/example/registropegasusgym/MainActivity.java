package com.example.registropegasusgym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.lights.LightsManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.registropegasusgym.CardView.ListaRegistro;
import com.example.registropegasusgym.Registro.RegistroUsuario;

public class MainActivity extends AppCompatActivity {

    Button btnLista, btnregistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLista=findViewById(R.id.btn_lista);

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), ListaRegistro.class);
                startActivity(i);
                //finish();
            }
        });

        btnregistro=findViewById(R.id.btn_registro);

        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), RegistroUsuario.class);
                startActivity(i);
                finish();
            }
        });

    }
}