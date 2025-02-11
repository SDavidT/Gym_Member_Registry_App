package com.example.registropegasusgym.CardView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.registropegasusgym.MainActivity;
import com.example.registropegasusgym.R;
import com.example.registropegasusgym.Registro.RegistroUsuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DescriptionActivity extends AppCompatActivity {

    TextView nombreDescription;
    TextView cedulaDescription;
    TextView estadoDescription;
    TextView fechacDescription;
    TextView fechaIngreso;
    ProgressDialog progressDialog, progressDialog2;
    EditText mensualidadesDescription;
    Button btnactualizar;
    Button btneliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Usuarios element =(Usuarios) getIntent().getSerializableExtra("Usuarios");
        nombreDescription=findViewById(R.id.tv_nombred);
        cedulaDescription=findViewById(R.id.tv_cedulad);
        estadoDescription=findViewById(R.id.tv_estadod);
        fechacDescription=findViewById(R.id.tv_fechaVd);
        mensualidadesDescription=findViewById(R.id.et_mensualidades);
        btnactualizar=findViewById(R.id.btn_actualizar);
        btneliminar=findViewById(R.id.btn_borrar);
        fechaIngreso = findViewById(R.id.tv_FechaIngreso2);

        nombreDescription.setText(element.getNombre());
        cedulaDescription.setText(element.getCedula());
        estadoDescription.setText(element.getEstado());
        fechacDescription.setText(element.getFechavencimiento());
        fechaIngreso.setText(element.getFechaingreso());

        ////////////////////////////////////////////////////////
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        fechaIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(DescriptionActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        /////////////////////////////////////////////////////////


        progressDialog=new ProgressDialog(DescriptionActivity.this);
        progressDialog.setMessage("Cargando...");
        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()){
                    updateData();
                    progressDialog.show();
                }

            }
        });

        ///////////////////////////////////////////////////////
        progressDialog2=new ProgressDialog(DescriptionActivity.this);
        progressDialog2.setMessage("Eliminando...");
        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
                progressDialog2.show();
            }
        });



    }
    //////////////////////////
    public boolean validar(){
        boolean retorno=true;
        String sMensaualidad=mensualidadesDescription.getText().toString();

        if (sMensaualidad.isEmpty()){
            mensualidadesDescription.setError("Complete el campo");
            retorno=false;
        }
        return retorno;
    }



    /////////////////////////

    public void updateData(){
        Usuarios element =(Usuarios) getIntent().getSerializableExtra("Usuarios");
        String sMensaualidad=mensualidadesDescription.getText().toString();
        String sCedula=cedulaDescription.getText().toString();
        String mesanterior=element.getMensualidad();
        String sFechaIngreso=fechaIngreso.getText().toString();

        //Integer n1,n2,result;
        //n1=Integer.parseInt(sMensaualidad.toString());
        //n2=Integer.parseInt(mesanterior.toString());
        //result=n1+n2;
        //String sresult=Integer.toString(result);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzzUTkv_s-fGayRcmqaZfEMSYhXkK2zU7L5afphVCWUdyTtiAQ9dnXiY5-BD35n_dnTMA/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                Context context = getApplicationContext();
                CharSequence text = "Registro Exitoso!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent i=new Intent(getApplicationContext(), ListaRegistro.class);
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
                params.put("action","updateItems");
                params.put("vCedula",sCedula);
                params.put("vMensualidad",sMensaualidad);
                params.put("vFechaI",sFechaIngreso);
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

    public void deleteData(){
        String sCedula2=cedulaDescription.getText().toString();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzzUTkv_s-fGayRcmqaZfEMSYhXkK2zU7L5afphVCWUdyTtiAQ9dnXiY5-BD35n_dnTMA/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog2.hide();
                Context context = getApplicationContext();
                CharSequence text = "Eliminado!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent i=new Intent(getApplicationContext(), ListaRegistro.class);
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
                params.put("action","deleteItems");
                params.put("vCedula",sCedula2);
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