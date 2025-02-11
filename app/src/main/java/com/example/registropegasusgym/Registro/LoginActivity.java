package com.example.registropegasusgym.Registro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registropegasusgym.MainActivity;
import com.example.registropegasusgym.R;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        //admin and admin

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    //correct
                    Toast.makeText(LoginActivity.this,"SESIÓN INICIADA",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                }else
                    //incorrect
                    Toast.makeText(LoginActivity.this,"INCORRECTO !!!",Toast.LENGTH_SHORT).show();
            }
        });


    }
}