package com.example.catalogoanimales.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.catalogoanimales.Adapters.AnimalAdapterApi;
import com.example.catalogoanimales.Models.Animal;
import com.example.catalogoanimales.Models.Ave;
import com.example.catalogoanimales.Models.AveRapaz;
import com.example.catalogoanimales.Models.InformacionAdicional;
import com.example.catalogoanimales.Models.Dato;
import com.example.catalogoanimales.Models.Mamifero;
import com.example.catalogoanimales.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListadoActivity extends AppCompatActivity implements AnimalAdapterApi.OnAnimalClickListener {

    private RecyclerView recyclerView;
    private AnimalAdapterApi adapter;
    private ProgressBar progressBar;
    private FloatingActionButton fabAdd;
    private final List<Animal> listaAnimales = new ArrayList<>();
    private RequestQueue requestQueue;
    private final String API_URL = "https://raw.githubusercontent.com/adancondori/TareaAPI/refs/heads/main/api/animales.json";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        requestQueue = Volley.newRequestQueue(this);

        inicializarVistas();
        configurarRecyclerView();
        configurarFab();

        cargarAnimalesDesdeAPI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null)
        {
            if (requestCode == 100)
            {
                Animal nuevoAnimal = (Animal) data.getSerializableExtra("animal_nuevo");
                if (nuevoAnimal != null)
                {
                    listaAnimales.add(nuevoAnimal);
                    adapter.notifyItemInserted(listaAnimales.size() - 1);
                }
            }
            else if (requestCode == 101)
            {
                Animal animalEditado = (Animal) data.getSerializableExtra("animal_editado");
                int posicion = data.getIntExtra("posicion", -1);

                if (animalEditado != null && posicion != -1 && posicion < listaAnimales.size())
                {
                    listaAnimales.set(posicion, animalEditado);
                    adapter.notifyItemChanged(posicion);
                }
            }
        }
    }

    public void abrirFormularioParaEditar(int position)
    {
        Intent intent = new Intent(this, FormularioActivity.class);
        intent.putExtra("animal_a_editar", listaAnimales.get(position));
        intent.putExtra("posicion", position);
        intent.putExtra("ultimo_id", obtenerUltimoId());
        startActivityForResult(intent, 101);
    }

    private int obtenerUltimoId()
    {
        if (listaAnimales.isEmpty()) return 0;
        int maxId = 0;
        for (Animal animal : listaAnimales)
        {
            if (animal.getId() > maxId)
            {
                maxId = animal.getId();
            }
        }
        return maxId;
    }

    private void inicializarVistas()
    {
        recyclerView = findViewById(R.id.rvAnimales);
        progressBar = findViewById(R.id.progressBar);
        fabAdd = findViewById(R.id.fabAdd);
    }

    private void configurarRecyclerView()
    {
        adapter = new AnimalAdapterApi(this, listaAnimales);
        adapter.setOnAnimalClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnDeleteClickListener((animal, position) -> {
            new androidx.appcompat.app.AlertDialog.Builder(ListadoActivity.this)
                    .setTitle("Eliminar Animal")
                    .setMessage("¿Estás seguro de que deseas eliminar este animal?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        listaAnimales.remove(position);
                        adapter.notifyItemRemoved(position);
                        Toast.makeText(ListadoActivity.this, "Animal eliminado", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

    }

    private void configurarFab()
    {
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(ListadoActivity.this, FormularioActivity.class);
            startActivityForResult(intent, 100);
        });
    }

    private void cargarAnimalesDesdeAPI()
    {
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest request = new JsonObjectRequest(API_URL, null,
                response -> {
                    try {
                        parsearRespuestaAPI(response);
                    } catch (JSONException e) {
                        Toast.makeText(this, "Error al procesar JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                },
                error -> {
                    Toast.makeText(this, "Error en la API: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
        );

        requestQueue.add(request);
    }

    private void parsearRespuestaAPI(JSONObject response) throws JSONException
    {
        listaAnimales.clear();
        JSONArray animalesArray = response.getJSONArray("animales");

        for (int i = 0; i < animalesArray.length(); i++)
        {
            JSONObject animalObj = animalesArray.getJSONObject(i);
            JSONObject infoJson = animalObj.getJSONObject("informacionAdicional");

            InformacionAdicional info = new InformacionAdicional();
            info.esperanzaVida = infoJson.optInt("esperanzaVida", 0);

            List<Dato> datosList = new ArrayList<>();
            JSONArray datosArray = infoJson.getJSONArray("datos");
            for (int j = 0; j < datosArray.length(); j++)
            {
                JSONObject datoObj = datosArray.getJSONObject(j);
                Dato dato = new Dato();
                dato.nombreDato = datoObj.optString("nombreDato", "");
                dato.valor = datoObj.optString("valor", "");
                datosList.add(dato);
            }
            info.datos = datosList;

            String tipo = animalObj.optString("tipo", "");

            switch (tipo)
            {
                case "Mamifero":
                    listaAnimales.add(crearMamiferoDesdeJSON(animalObj, info));
                    break;
                case "Ave":
                    listaAnimales.add(crearAveDesdeJSON(animalObj, info));
                    break;
                case "AveRapaz":
                    listaAnimales.add(crearAveRapazDesdeJSON(animalObj, info));
                    break;
                default:
                    listaAnimales.add(crearAnimalBaseDesdeJSON(animalObj, info));
            }
        }

        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    private Animal crearAnimalBaseDesdeJSON(JSONObject json, InformacionAdicional info) throws JSONException
    {
        return new Animal(
                json.optInt("id", 0),
                json.optString("especie", ""),
                json.optString("nombreCientifico", ""),
                json.optString("habitat", ""),
                json.optInt("pesoPromedio", 0),
                json.optString("estadoConservacion", ""),
                json.optString("tipo", ""),
                info
        );
    }

    private Mamifero crearMamiferoDesdeJSON(JSONObject json, InformacionAdicional info) throws JSONException
    {
        return new Mamifero(
                json.optInt("id", 0),
                json.optString("especie", ""),
                json.optString("nombreCientifico", ""),
                json.optString("habitat", ""),
                json.optInt("pesoPromedio", 0),
                json.optString("estadoConservacion", ""),
                json.optString("tipo", ""),
                info,
                json.optDouble("temperaturaCorporal", 0.0),
                json.optInt("tiempoGestacion", 0),
                json.optString("alimentacion", "")
        );
    }

    private Ave crearAveDesdeJSON(JSONObject json, InformacionAdicional info) throws JSONException
    {
        return new Ave(
                json.optInt("id", 0),
                json.optString("especie", ""),
                json.optString("nombreCientifico", ""),
                json.optString("habitat", ""),
                json.optInt("pesoPromedio", 0),
                json.optString("estadoConservacion", ""),
                json.optString("tipo", ""),
                info,
                json.optInt("envergaduraAlas", 0),
                json.optString("colorPlumaje", ""),
                json.optString("tipoPico", "")
        );
    }

    private AveRapaz crearAveRapazDesdeJSON(JSONObject json, InformacionAdicional info) throws JSONException
    {
        return new AveRapaz(
                json.optInt("id", 0),
                json.optString("especie", ""),
                json.optString("nombreCientifico", ""),
                json.optString("habitat", ""),
                json.optInt("pesoPromedio", 0),
                json.optString("estadoConservacion", ""),
                json.optString("tipo", ""),
                info,
                json.optInt("envergaduraAlas", 0),
                json.optString("colorPlumaje", ""),
                json.optString("tipoPico", ""),
                json.optInt("velocidadVuelo", 0),
                json.optString("tipoPresa", "")
        );
    }

    public void onAnimalClick(Animal animal, int position)
    {
        Intent intent = new Intent(this, DetalleAnimal.class);

        intent.putExtra("id", animal.getId());
        intent.putExtra("especie", animal.getEspecie());
        intent.putExtra("nombreCientifico", animal.getNombreCientifico());
        intent.putExtra("habitat", animal.getHabitat());
        intent.putExtra("pesoPromedio", animal.getPesoPromedio());
        intent.putExtra("estadoConservacion", animal.getEstadoConservacion());
        intent.putExtra("tipo", animal.getTipo());

        intent.putExtra("informacionAdicional", animal.getInformacionAdicional());

        if (animal instanceof Mamifero)
        {
            Mamifero m = (Mamifero) animal;
            intent.putExtra("temperaturaCorporal", m.getTemperaturaCorporal());
            intent.putExtra("tiempoGestacion", m.getTiempoGestacion());
            intent.putExtra("alimentacion", m.getAlimentacion());
        } else if (animal instanceof AveRapaz)
        {
            AveRapaz ar = (AveRapaz) animal;
            intent.putExtra("envergaduraAlas", ar.getEnvergaduraAlas());
            intent.putExtra("colorPlumaje", ar.getColorPlumaje());
            intent.putExtra("tipoPico", ar.getTipoPico());
            intent.putExtra("velocidadVuelo", ar.getVelocidadVuelo());
            intent.putExtra("tipoPresa", ar.getTipoPresa());
        } else if (animal instanceof Ave)
        {
            Ave a = (Ave) animal;
            intent.putExtra("envergaduraAlas", a.getEnvergaduraAlas());
            intent.putExtra("colorPlumaje", a.getColorPlumaje());
            intent.putExtra("tipoPico", a.getTipoPico());
        }

        startActivity(intent);
    }
}
