package com.example.appcrud;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.navigation.fragment.NavHostFragment;

import com.example.appcrud.databinding.FragmentSecondBinding;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    //Spinner y adapter
    private Spinner spinNombres2;
    private ArrayAdapter spinAdapter2;

    //botones y campos de texto
    private Button btnVer2;
    private Button btnEliminar;
    private Button btnEdit;
    private EditText txtNombre2;
    private EditText txtEquipo2;

    //Lista de equipos y equipo actual
    private ArrayList<Team> lista;
    private Team actual;

    //Controlador de bases de datos
    private MyOpenHelper db;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new MyOpenHelper(getContext());
        spinNombres2 = (Spinner) binding.spinner2;
        btnVer2 = (Button) binding.btnVer2;
        btnEdit = (Button) binding.btnEdit;
        btnEliminar = (Button) binding.btnEliminar;
        txtNombre2 = (EditText) binding.txtNombre2;
        txtEquipo2 = (EditText) binding.txtEquipo2;
        txtNombre2.setEnabled(false);
        txtEquipo2.setEnabled(false);


        lista = db.getTeams();
        spinAdapter2 = new ArrayAdapter(getContext(), android.support.design.R.layout.support_simple_spinner_dropdown_item,lista);
        spinNombres2.setAdapter(spinAdapter2);

        spinNombres2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SecondFragment.this.onItemSelected(adapterView, view, i, l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.btnVer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actual!=null) {
                    txtNombre2.setText(actual.getNombre());
                    txtEquipo2.setText(actual.getEquipo());
                }
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actual!=null) {
                    txtNombre2.setEnabled(true);
                    txtEquipo2.setEnabled(true);
                    actual.setNombre(txtNombre2.getText().toString());
                    actual.setEquipo(txtEquipo2.getText().toString());
                    db.update(actual);
                    lista = db.getTeams();
                    spinAdapter2 = new ArrayAdapter(getContext(), android.support.design.R.layout.support_simple_spinner_dropdown_item,lista);
                    spinNombres2.setAdapter(spinAdapter2);

                }
            }
        });

        binding.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(actual!=null) {
                    db.borrar(actual);
                    lista = db.getTeams();
                    spinAdapter2 = new ArrayAdapter(getContext(), android.support.design.R.layout.support_simple_spinner_dropdown_item,lista);
                    spinNombres2.setAdapter(spinAdapter2);
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner2) {
            //Si hay elementos en la base de datos, establecemos el equipo actual a partir del
            //indice del elemento seleccionado en el spinner
            if(lista.size()>0) {
                actual = lista.get(position);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}