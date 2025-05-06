package com.example.catalogoanimales.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.catalogoanimales.Models.Dato;
import com.example.catalogoanimales.Models.InformacionAdicional;
import com.example.catalogoanimales.R;

public class DetalleAnimal extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_animal);

        ImageView imgAnimal = findViewById(R.id.imgAnimal);
        TextView tvEspecie = findViewById(R.id.tvEspecie);
        TextView tvNombreCientifico = findViewById(R.id.tvNombreCientifico);
        TextView tvHabitat = findViewById(R.id.tvHabitat);
        TextView tvPeso = findViewById(R.id.tvPeso);
        TextView tvEstadoConservacion = findViewById(R.id.tvEstadoConservacion);
        TextView tvTipo = findViewById(R.id.tvTipo);
        TextView tvTemperatura = findViewById(R.id.tvTemperatura);
        TextView tvTiempoGestacion = findViewById(R.id.tvTiempoGestacion);
        TextView tvAlimentacion = findViewById(R.id.tvAlimentacion);
        TextView tvEnvergaduraAlas = findViewById(R.id.tvEnvergaduraAlas);
        TextView tvColorPlumaje = findViewById(R.id.tvColorPlumaje);
        TextView tvTipoPico = findViewById(R.id.tvTipoPico);
        TextView tvVelocidadVuelo = findViewById(R.id.tvVelocidadVuelo);
        TextView tvTipoPresa = findViewById(R.id.tvTipoPresa);
        TextView tvEsperanzaVida = findViewById(R.id.tvEsperanzaVida);
        TextView tvDatosAdicionales = findViewById(R.id.tvDatosAdicionales);

        Intent intent = getIntent();
        String tipo = intent.getStringExtra("tipo");

        if (tipo != null)
        {
            switch (tipo)
            {
                case "Mamifero":
                    imgAnimal.setImageResource(R.mipmap.leon);
                    break;
                case "Ave":
                    imgAnimal.setImageResource(R.mipmap.ave);
                    break;
                case "AveRapaz":
                    imgAnimal.setImageResource(R.mipmap.rapaz);
                    break;
                default:
                    imgAnimal.setImageResource(R.mipmap.huella);
            }
        }

        tvEspecie.setText(intent.getStringExtra("especie"));
        tvNombreCientifico.setText(intent.getStringExtra("nombreCientifico"));
        tvHabitat.setText("Hábitat: " + intent.getStringExtra("habitat"));
        tvPeso.setText("Peso promedio: " + intent.getIntExtra("pesoPromedio", 0) + " kg");
        tvEstadoConservacion.setText("Estado de conservación: " + intent.getStringExtra("estadoConservacion"));
        tvTipo.setText("Tipo: " + intent.getStringExtra("tipo"));

        if ("Mamifero".equals(intent.getStringExtra("tipo")))
        {
            tvTemperatura.setVisibility(View.VISIBLE);
            tvTiempoGestacion.setVisibility(View.VISIBLE);
            tvAlimentacion.setVisibility(View.VISIBLE);

            tvTemperatura.setText("Temperatura corporal: " + intent.getDoubleExtra("temperaturaCorporal", 0.0) + " °C");
            tvTiempoGestacion.setText("Tiempo de gestación: " + intent.getIntExtra("tiempoGestacion", 0) + " días");
            tvAlimentacion.setText("Alimentación: " + intent.getStringExtra("alimentacion"));
        } else if ("Ave".equals(intent.getStringExtra("tipo")))
        {
            tvEnvergaduraAlas.setVisibility(View.VISIBLE);
            tvColorPlumaje.setVisibility(View.VISIBLE);
            tvTipoPico.setVisibility(View.VISIBLE);

            tvEnvergaduraAlas.setText("Envergadura de Alas: " + intent.getIntExtra("envergaduraAlas", 0) + " cm");
            tvColorPlumaje.setText("Color Plumaje: " + intent.getStringExtra("colorPlumaje"));
            tvTipoPico.setText("Tipo de Pico: " + intent.getStringExtra("tipoPico"));
        } else if ("AveRapaz".equals(intent.getStringExtra("tipo")))
        {
            tvEnvergaduraAlas.setVisibility(View.VISIBLE);
            tvColorPlumaje.setVisibility(View.VISIBLE);
            tvTipoPico.setVisibility(View.VISIBLE);
            tvVelocidadVuelo.setVisibility(View.VISIBLE);
            tvTipoPresa.setVisibility(View.VISIBLE);

            tvEnvergaduraAlas.setText("Envergadura de Alas: " + intent.getIntExtra("envergaduraAlas", 0) + " cm");
            tvColorPlumaje.setText("Color Plumaje: " + intent.getStringExtra("colorPlumaje"));
            tvTipoPico.setText("Tipo de Pico: " + intent.getStringExtra("tipoPico"));
            tvVelocidadVuelo.setText("Velocidad de Vuelo: " + intent.getIntExtra("velocidadVuelo", 0) + " km/h");
            tvTipoPresa.setText("Tipo de Presa: " + intent.getStringExtra("tipoPresa"));
        }

        InformacionAdicional info = (InformacionAdicional) intent.getSerializableExtra("informacionAdicional");
        if (info != null)
        {
            tvEsperanzaVida.setText("Esperanza de vida: " + info.esperanzaVida + " años");

            if (info.datos != null && !info.datos.isEmpty())
            {
                tvDatosAdicionales.setVisibility(View.VISIBLE);
                StringBuilder datosStr = new StringBuilder("Datos adicionales:\n");
                for (Dato dato : info.datos) {
                    datosStr.append("• ").append(dato.nombreDato).append(": ").append(dato.valor).append("\n");
                }
                tvDatosAdicionales.setText(datosStr.toString());
            } else
            {
                tvDatosAdicionales.setVisibility(View.GONE);
            }
        }
    }
}