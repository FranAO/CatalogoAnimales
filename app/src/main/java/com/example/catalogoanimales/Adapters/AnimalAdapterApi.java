package com.example.catalogoanimales.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.catalogoanimales.Activities.FormularioActivity;
import com.example.catalogoanimales.Activities.ListadoActivity;
import com.example.catalogoanimales.Models.Animal;
import com.example.catalogoanimales.R;

import java.util.List;

public class AnimalAdapterApi extends RecyclerView.Adapter<AnimalAdapterApi.AnimalViewHolder>
{

    private final Context context;
    private List<Animal> animales;
    private OnAnimalClickListener listener;
    private OnDeleteClickListener deleteListener;

    public interface OnAnimalClickListener
    {
        void onAnimalClick(Animal animal, int position);
    }

    public interface OnDeleteClickListener
    {
        void onDeleteClick(Animal animal, int position);
    }

    public AnimalAdapterApi(Context context, List<Animal> animales)
    {
        this.context = context;
        this.animales = animales;
    }

    public void setOnAnimalClickListener(OnAnimalClickListener listener)
    {
        this.listener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener)
    {
        this.deleteListener = listener;
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position)
    {
        Animal animal = animales.get(position);

        holder.tvNombre.setText(animal.getEspecie());
        holder.tvDetalles.setText(animal.getNombreCientifico());

        int iconResId = obtenerIconoPorTipo(animal.getTipo());
        holder.imgIcono.setImageResource(iconResId);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null)
            {
                listener.onAnimalClick(animal, position);
            }
        });

        holder.imgBorrar.setOnClickListener(v -> {
            if (deleteListener != null)
            {
                deleteListener.onDeleteClick(animal, position);
            }
        });

        holder.imgEditar.setOnClickListener(v -> {
            if (context instanceof ListadoActivity)
            {
                ((ListadoActivity) context).abrirFormularioParaEditar(position);
            }
        });
    }

    private int obtenerIconoPorTipo(String tipo)
    {
        if (tipo == null)
            return R.mipmap.huella;

        switch (tipo)
        {
            case "Mamifero":
                return R.mipmap.leon;
            case "Ave":
                return R.mipmap.ave;
            case "AveRapaz":
                return R.mipmap.rapaz;
            default:
                return R.mipmap.huella;
        }
    }

    @Override
    public int getItemCount()
    {
        return animales != null ? animales.size() : 0;
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView tvNombre;
        private final TextView tvDetalles;
        private final ImageView imgIcono;
        private final ImageView imgBorrar;
        private final ImageView imgEditar;

        public AnimalViewHolder(View itemView)
        {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDetalles = itemView.findViewById(R.id.tvDetalles);
            imgIcono = itemView.findViewById(R.id.imgIcono);
            imgBorrar = itemView.findViewById(R.id.imgBorrar);
            imgEditar = itemView.findViewById(R.id.imgEditar);
        }
    }
}