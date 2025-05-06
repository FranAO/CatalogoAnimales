package com.example.catalogoanimales.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catalogoanimales.Models.Animal;
import com.example.catalogoanimales.Models.Ave;
import com.example.catalogoanimales.Models.AveRapaz;
import com.example.catalogoanimales.Models.Mamifero;
import com.example.catalogoanimales.R;

public class FormularioActivity extends AppCompatActivity
{

    private EditText etEspecie, etNombreCientifico;
    private Button btnGuardar, btnLimpiar;
    private Spinner spTipoAnimal;
    private Animal animalActual;
    private boolean esEdicion = false;
    private final String[] tiposAnimal =
            {
                    "Animal",
                    "Mamifero",
                    "Ave",
                    "AveRapaz"
            };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        inicializarVistas();
        configurarSpinner();
        cargarDatosAnimal();
        configurarBotones();
    }

    private void inicializarVistas()
    {
        etEspecie = findViewById(R.id.etTitle);
        etNombreCientifico = findViewById(R.id.etDescription);
        btnGuardar = findViewById(R.id.btnSave);
        btnLimpiar = findViewById(R.id.btnClear);
        spTipoAnimal = findViewById(R.id.spAnimalType);
    }

    private void configurarSpinner()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tiposAnimal
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoAnimal.setAdapter(adapter);
    }

    private void cargarDatosAnimal()
    {
        animalActual = (Animal) getIntent().getSerializableExtra("animal_a_editar");
        esEdicion = animalActual != null;

        if (esEdicion)
        {
            etEspecie.setText(animalActual.getEspecie());
            etNombreCientifico.setText(animalActual.getNombreCientifico());
            spTipoAnimal.setSelection(obtenerPosicionTipo(animalActual.getTipo()));
        } else
        {
            animalActual = new Animal();
            animalActual.setTipo("Animal");
        }
    }

    private int obtenerPosicionTipo(String tipo)
    {
        for (int i = 0; i < tiposAnimal.length; i++)
        {
            if (tiposAnimal[i].equals(tipo))
            {
                return i;
            }
        }
        return 0;
    }

    private void configurarBotones()
    {
        btnLimpiar.setOnClickListener(v -> limpiarFormulario());
        btnGuardar.setOnClickListener(v -> guardarAnimal());
    }

    private void limpiarFormulario()
    {
        etEspecie.setText("");
        etNombreCientifico.setText("");
        spTipoAnimal.setSelection(0);
    }

    private void guardarAnimal()
    {
        String especie = etEspecie.getText().toString().trim();
        String nombreCientifico = etNombreCientifico.getText().toString().trim();
        String tipo = spTipoAnimal.getSelectedItem().toString();

        if (validarCampos(especie, nombreCientifico))
        {
            if (esEdicion)
            {
                actualizarAnimalExistente(especie, nombreCientifico, tipo);
            } else
            {
                crearNuevoAnimal(especie, nombreCientifico, tipo);
            }
            retornarResultado();
        }
    }

    private boolean validarCampos(String especie, String nombreCientifico)
    {
        if (especie.isEmpty() || nombreCientifico.isEmpty())
        {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void actualizarAnimalExistente(String especie, String nombreCientifico, String tipo)
    {
        animalActual.setEspecie(especie);
        animalActual.setNombreCientifico(nombreCientifico);
        animalActual.setTipo(tipo);
    }

    private void crearNuevoAnimal(String especie, String nombreCientifico, String tipo)
    {
        int nuevoId = getIntent().getIntExtra("ultimo_id", 0) + 1;
        animalActual.setId(nuevoId);
        animalActual.setEspecie(especie);
        animalActual.setNombreCientifico(nombreCientifico);
        animalActual.setTipo(tipo);

        animalActual = convertirATipoEspecifico(animalActual);
    }

    private Animal convertirATipoEspecifico(Animal animal)
    {
        switch (animal.getTipo())
        {
            case "Mamifero":
                return new Mamifero(
                        animal.getId(),
                        animal.getEspecie(),
                        animal.getNombreCientifico(),
                        animal.getHabitat(),
                        animal.getPesoPromedio(),
                        animal.getEstadoConservacion(),
                        animal.getTipo(),
                        animal.getInformacionAdicional(),
                        0.0, 0, ""
                );
            case "Ave":
                return new Ave(
                        animal.getId(),
                        animal.getEspecie(),
                        animal.getNombreCientifico(),
                        animal.getHabitat(),
                        animal.getPesoPromedio(),
                        animal.getEstadoConservacion(),
                        animal.getTipo(),
                        animal.getInformacionAdicional(),
                        0, "", ""
                );
            case "AveRapaz":
                return new AveRapaz(
                        animal.getId(),
                        animal.getEspecie(),
                        animal.getNombreCientifico(),
                        animal.getHabitat(),
                        animal.getPesoPromedio(),
                        animal.getEstadoConservacion(),
                        animal.getTipo(),
                        animal.getInformacionAdicional(),
                        0, "", "", 0, ""
                );
            default:
                return animal;
        }
    }

    private void retornarResultado()
    {
        Intent resultado = new Intent();
        if (esEdicion)
        {
            resultado.putExtra("animal_editado", animalActual);
            resultado.putExtra("posicion", getIntent().getIntExtra("posicion", -1));
        } else
        {
            resultado.putExtra("animal_nuevo", animalActual);
        }
        setResult(RESULT_OK, resultado);
        finish();
    }
}