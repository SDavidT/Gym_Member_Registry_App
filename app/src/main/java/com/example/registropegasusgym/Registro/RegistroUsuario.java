package com.example.registropegasusgym.Registro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.registropegasusgym.CardView.Usuarios;
import com.example.registropegasusgym.CardView.UsuariosAdaptador;
import com.example.registropegasusgym.MainActivity;
import com.example.registropegasusgym.R;
import com.example.registropegasusgym.Registro.RegistroUsuario.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroUsuario extends AppCompatActivity {


    Boolean comparador=true;
    EditText etAddress;
    //EditText etCedula;
    Button btnInsert, btnLista;
    ProgressDialog progressDialog;
    TextView fechaIngreso;
    EditText etMensualidad;
    UsuariosAdaptador usuariosAdaptador;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        etAddress=findViewById(R.id.et_address_id);
        //etCedula=findViewById(R.id.et_cedula_id);
        //FechaI=findViewById(R.id.tv_FechaIngreso);
        btnInsert=findViewById(R.id.btn_insert_id);
        btnLista=findViewById(R.id.btn_lista);
        fechaIngreso=findViewById(R.id.tv_FechaIngreso);
        etMensualidad=findViewById(R.id.et_mensualidades);


        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        fechaIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(RegistroUsuario.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        fechaIngreso.setText(date);
                    }
                },year, month,day);
                dialog.show();
            }
        });


        progressDialog=new ProgressDialog(RegistroUsuario.this);
        progressDialog.setMessage("Cargando...");

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validar()){
                    //inicializarElementos();
                    addStudentData();
                    progressDialog.show();
                }
            }
        });
    }


    ////VALIDACION///
    public boolean validar(){
        boolean retorno=true;
        String sAddress=etAddress.getText().toString();
        //String sCedula=etCedula.getText().toString();
        String sFechaI=fechaIngreso.getText().toString();
        String sMensaualidad=etMensualidad.getText().toString();

        if (sAddress.isEmpty()){
            etAddress.setError("Complete el campo");
            retorno=false;
        }

        if (sFechaI.isEmpty()  || sFechaI=="Fecha de Ingreso"){
            fechaIngreso.setError("Complete el campo");
            retorno=false;
        }
        if(sMensaualidad.isEmpty()){
            etMensualidad.setError("Complete el campo");
            retorno=false;
        }
        if (comparador==false){
            etMensualidad.setError("Número ya existente!");
            retorno=false;
        }
        return retorno;
    }

    /////////////////


    //////////////////////////////////

    public void addStudentData(){
        String sAddress=etAddress.getText().toString();
        //String sCedula=etCedula.getText().toString();
        String sFechaI=fechaIngreso.getText().toString();
        String sMensaualidad=etMensualidad.getText().toString();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzzUTkv_s-fGayRcmqaZfEMSYhXkK2zU7L5afphVCWUdyTtiAQ9dnXiY5-BD35n_dnTMA/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                Context context = getApplicationContext();
                CharSequence text = "Registro Exitoso!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("action","addStudent");
                //params.put("vCedula",sCedula);
                params.put("vAddress",sAddress);
                params.put("vFechaI",sFechaI);
                params.put("vMensualidad",sMensaualidad);
                //params.put(vEstado,sEstado);
                return params;
            }
        };

        int socketTimeOut=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(socketTimeOut,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT );
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}