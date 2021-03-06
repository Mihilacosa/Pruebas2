package com.example.pruebas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CapituloFragment extends Fragment {
    public static final String ID_NOVELA = "com.com.example.tarea_1.ID_NOVELA";
    private TextView Titulo;
    private TextView Contenido;
    private String id_novela;
    private String id_capitulo;
    private ArrayList<Integer> Capitulos_id = new ArrayList<>();
    private int posicion = 0;
    private Integer id_cap;
    private ScrollView scroll;

    MediaPlayer mediaPlayer;
    MenuItem play;
    MenuItem pause;

    private  String usuario = "";
    private FirebaseAuth mAuth;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_capitulo, container, false);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            SharedPreferences datos_usu = this.getActivity().getSharedPreferences("usuario_login", Context.MODE_PRIVATE);
            usuario = datos_usu.getString("usuario", "");
            if (!usuario.equals("")) {
                //setTitle("Hola " + usuario);
            }
        }

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.relaxing_instrumental);
        mediaPlayer.start();

        Button Anterior = v.findViewById(R.id.Anterior);
        Button Indice = v.findViewById(R.id.Indice);
        Button Siguiente = v.findViewById(R.id.Siguiente);

        Button Anterior2 = v.findViewById(R.id.Anterior2);
        Button Indice2 = v.findViewById(R.id.Indice2);
        Button Siguiente2 = v.findViewById(R.id.Siguiente2);

        scroll = v.findViewById(R.id.Cap_scroll);



        Bundle bundle = this.getArguments();
        id_novela = bundle.getString("id_nov");
        id_capitulo = bundle.getString("id_cap");
        id_cap = Integer.valueOf(id_capitulo);
        Capitulos_id = (ArrayList<Integer>) bundle.getSerializable("ARRAYLIST");

        for (int i = 0; i < Capitulos_id.size(); i++){
            if(Capitulos_id.get(i).equals(id_cap)){
                posicion = i;
            }
        }

        int cap_max = Capitulos_id.size() - 1;

        if(posicion == 0){
            Anterior.setEnabled(false);
            Anterior2.setEnabled(false);
        }

        if(posicion == cap_max){
            Siguiente.setEnabled(false);
            Siguiente2.setEnabled(false);
        }

        Titulo = v.findViewById(R.id.Titulo_cap);
        Contenido = v.findViewById(R.id.Contenido_cap);

        CargarCapitulo("https://tnowebservice.000webhostapp.com/Capitulo_seleccionado.php?id_capitulo=" + id_capitulo);

        Anterior.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(posicion != cap_max){
                    Siguiente.setEnabled(true);
                }

                posicion = posicion - 1;
                id_cap = Capitulos_id.get(posicion);
                id_capitulo =  String.valueOf(Capitulos_id.get(posicion));
                CargarCapitulo("https://tnowebservice.000webhostapp.com/Capitulo_seleccionado.php?id_capitulo=" + id_capitulo);

                if(posicion == 0){
                    Anterior.setEnabled(false);
                }else{
                    Anterior.setEnabled(true);
                }

                scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        Anterior2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(posicion != cap_max){
                    Siguiente2.setEnabled(true);
                }

                posicion = posicion - 1;
                id_cap = Capitulos_id.get(posicion);
                id_capitulo =  String.valueOf(Capitulos_id.get(posicion));
                CargarCapitulo("https://tnowebservice.000webhostapp.com/Capitulo_seleccionado.php?id_capitulo=" + id_capitulo);

                if(posicion == 0){
                    Anterior2.setEnabled(false);
                }else{
                    Anterior2.setEnabled(true);
                }

                scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        Siguiente.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                posicion = posicion + 1;
                id_cap = Capitulos_id.get(posicion);
                id_capitulo =  String.valueOf(Capitulos_id.get(posicion));
                CargarCapitulo("https://tnowebservice.000webhostapp.com/Capitulo_seleccionado.php?id_capitulo=" + id_capitulo);

                if(posicion == cap_max){
                    Siguiente.setEnabled(false);
                }else{
                    Siguiente.setEnabled(true);
                }

                if(posicion != 0){
                    Anterior.setEnabled(true);
                }

                scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        Siguiente2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                posicion = posicion + 1;
                id_cap = Capitulos_id.get(posicion);
                id_capitulo =  String.valueOf(Capitulos_id.get(posicion));
                CargarCapitulo("https://tnowebservice.000webhostapp.com/Capitulo_seleccionado.php?id_capitulo=" + id_capitulo);

                if(posicion == cap_max){
                    Siguiente2.setEnabled(false);
                }else{
                    Siguiente2.setEnabled(true);
                }

                if(posicion != 0){
                    Anterior2.setEnabled(true);
                }

                scroll.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        Indice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                VolverNovela();
            }
        });

        Indice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                VolverNovela();
            }
        });

        return v;
    }


    //Volver a novela
    private void VolverNovela() {
        Bundle bundle = new Bundle();
        bundle.putString("id",id_novela);
        NovelaFragment novela = new NovelaFragment();
        novela.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,novela).commit();
    }


    private void CargarCapitulo(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        Titulo.setText("Cap??tulo: " + jsonObject.getString("num_capitulo") + " - " + new String(jsonObject.getString("titulo").getBytes("ISO-8859-1"), "UTF-8"));
                        Contenido.setText(new String(jsonObject.getString("contenido").getBytes("ISO-8859-1"), "UTF-8"));
                    } catch (JSONException | UnsupportedEncodingException e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR DE CARGA DEL CAPITULO SELECCIONADA", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }
}
