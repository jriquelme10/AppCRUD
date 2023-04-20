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
import android.widget.Toast;

import androidx.navigation.fragment.NavHostFragment;

import com.example.appcrud.databinding.FragmentFirstBinding;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;


    private EditText editNombre;
    private EditText editEquipo;
    private EditText txtNombre;
    private EditText txtEquipo;

    private Button btnGuardar;
    private Button btnVer;
    private Button btnEliminar;

    //Declaración del spinner y su Adapter
    private Spinner spinNombres;
    private ArrayAdapter spinAdapter;

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

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //EditTexts
        editNombre = (EditText) binding.editNombre;
        editEquipo = (EditText) binding.editEquipo;
        //boton
        btnGuardar = (Button) binding.btnGuardar;

        db = new MyOpenHelper(getContext());
        lista = db.getTeams();


        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = editNombre.getText().toString();
                String equipo = editEquipo.getText().toString();
                if (!nombre.isEmpty() && !equipo.isEmpty()){
                    db.insertar(nombre, equipo);
                    lista = db.getTeams();
                    editNombre.setText("");
                    editEquipo.setText("");
                    Toast.makeText(getContext(), "Equipo guardado correctamente", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}