package com.sosa.circulodeseguridadoficial.ui.evento;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.databinding.CrearEventoFragmentBinding;
import com.sosa.circulodeseguridadoficial.databinding.FragmentHomeBinding;
import com.sosa.circulodeseguridadoficial.entidades.Grupo;
import com.sosa.circulodeseguridadoficial.entidades.dto.Coordenada;
import com.sosa.circulodeseguridadoficial.entidades.dto.CrearEventoDto;
import com.sosa.circulodeseguridadoficial.entidades.dto.IdentificadorDto;

import java.util.Calendar;

public class CrearEventoFragment extends Fragment {

    private CrearEventoViewModel mViewModel;
    private CrearEventoFragmentBinding binding;
    private CrearEventoDto crearEventoDto= new CrearEventoDto();
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    public final Calendar c = Calendar.getInstance();
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    private static final String BARRA = "/";
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    private String horas;
    private String fecha;
    public static CrearEventoFragment newInstance() {
        return new CrearEventoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CrearEventoFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(CrearEventoViewModel.class);
        Coordenada c = (Coordenada) getArguments().getSerializable("localiza");
        IdentificadorDto i = (IdentificadorDto) getArguments().getSerializable("ident");
        crearEventoDto.setIdentificador(i.getIdentificador());
        crearEventoDto.setCordenadaX(Float.parseFloat(c.getCoordenadaX()));
        crearEventoDto.setCoordenadaY(Float.parseFloat(c.getCoordeanadaY()));
        mViewModel.getCrear().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                Toast.makeText(getContext(), "Se guardo con exito", Toast.LENGTH_SHORT).show();
            }
        });
        binding.BTNCrearE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearEventoDto.setNombre(binding.ETCENombre.getText().toString());
                crearEventoDto.setDescripcion(binding.ETCEDescripcion.getText().toString());
                crearEventoDto.setFechaFinalizacion(fecha+" "+horas);
                mViewModel.crearEvento(crearEventoDto);
            }
        });
        binding.IBFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });
        binding.IBHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerHora();
            }
        });
        return root;
    }
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                fecha=(diaFormateado + BARRA + mesFormateado + BARRA + year);
                binding.TVFechaFinal.setText(fecha);

            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }
    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                horas = (horaFormateada + DOS_PUNTOS + minutoFormateado );
                binding.TVHoraFinal.setText(horas);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

}