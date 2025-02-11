package com.example.registropegasusgym.CardView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.example.registropegasusgym.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ListaRegistro extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView recyclerUsuarios;
    UsuariosAdaptador usuariosAdaptador;
    SearchView txtBuscar;
    Button btnVencidos;

    public String mensualidades;

    final ThreadLocal<ListView> listView = new ThreadLocal<ListView>();
    ProgressDialog loading;

    String estadoV="VENCIDA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_registro);
        txtBuscar=findViewById(R.id.txt_buscar);
        txtBuscar.setOnQueryTextListener(this);
        btnVencidos=findViewById(R.id.btn_usersVencidos);
        inicializarElementos();

        btnVencidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuariosAdaptador.filtrado2(estadoV);
            }
        });
    }

    private void inicializarElementos() {
        recyclerUsuarios=findViewById(R.id.recycler);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));

        loading =  ProgressDialog.show(this,"Cargando","Espere por favor..",false,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbzzUTkv_s-fGayRcmqaZfEMSYhXkK2zU7L5afphVCWUdyTtiAQ9dnXiY5-BD35n_dnTMA/exec?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    private void parseItems(String jsonResposnce) {
        //ArrayList<HashMap<String, String>> list = new ArrayList<>();
        List<Usuarios> usuariosList2= new ArrayList<>();
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");

            for (int i = 1; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                String vAddress = jo.getString("vAddress");
                String vCedula = jo.getString("vCedula");
                String vEstado=jo.getString("vEstado");
                String vMensualidad=jo.getString("vMensualidad");
                String vFechaVencimiento=jo.getString("vFechaVencimiento");
                String[] vvFechaVencimiento=vFechaVencimiento.split("T");
                String vFechaIngreso=jo.getString("vFechaIngreso");
                String[] vvFechaIngreso=vFechaIngreso.split("T");
                usuariosList2.add(new Usuarios(vAddress,vCedula,vEstado,vMensualidad,vvFechaVencimiento[0],vvFechaIngreso[0]));
                usuariosAdaptador=new UsuariosAdaptador(usuariosList2, this, new UsuariosAdaptador.OnItemClickListener() {
                    @Override
                    public void onItemClick(Usuarios item) {
                        //ShowToast(item.getMensualidad()+"Click");
                        moveToDescription(item);
                    }
                });
                recyclerUsuarios.setAdapter(usuariosAdaptador);
                loading.dismiss();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //private void ShowToast(String message){
        //Toast.makeText(this, message,Toast.LENGTH_SHORT).show();

   // }
    public void moveToDescription(Usuarios item){
        Intent intent=new Intent(this, DescriptionActivity.class);
        intent.putExtra("Usuarios", item);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        usuariosAdaptador.filtrado(s);
        return false;
    }
}