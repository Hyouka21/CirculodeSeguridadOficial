package com.sosa.circulodeseguridadoficial.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.entidades.dto.RegistrarUsuarioDto;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class RegistrarActivity extends AppCompatActivity {
    private RegistrarViewModel mViewModel;
    private static final String CERO = "0";
    public final Calendar c = Calendar.getInstance();
    private static final String BARRA = "/";
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    private String fecha;
    private ImageView imagen1;
    private EditText ETNick,ETEmail,ETClave;
    private TextView TVFecha ,TVImagenR;
    private RegistrarUsuarioDto registroUsu = new RegistrarUsuarioDto();
    private ImageButton BTFecha;
    private Button BTCrear,BTFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        iniciar();
        mViewModel = new ViewModelProvider(this).get(RegistrarViewModel.class);
        BTFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });
        BTFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });
        mViewModel.getFoto().observe(this, new Observer<byte[]>() {
            @Override
            public void onChanged(byte[] bytes) {
                registroUsu.setAvatar(Base64.encodeToString(bytes, Base64.DEFAULT));
                TVImagenR.setVisibility(View.VISIBLE);
            }
        });
        BTCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registroUsu.setFechaNacimiento(fecha);
                registroUsu.setClave(ETClave.getText().toString());
                registroUsu.setEmail(ETEmail.getText().toString());
                registroUsu.setNickName(ETNick.getText().toString());
                mViewModel.crearRegistro(registroUsu);

            }
        });
        mViewModel.getCrear().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                imagen1=null;
                TVImagenR.setVisibility(View.INVISIBLE);
                TVFecha.setText("");
                ETClave.setText("");
                ETEmail.setText("");
                ETNick.setText("");

            }
        });
    }
    void iniciar(){
        ETNick = findViewById(R.id.ETNickName);
        ETClave=  findViewById(R.id.ETClave);
        ETEmail = findViewById(R.id.ETEmail);
        BTFecha = findViewById(R.id.IBFechaR);
        imagen1 = findViewById(R.id.imageView2);
        TVFecha = findViewById(R.id.TVFechaNacimiento);
        TVImagenR= findViewById(R.id.TVImagenR);
        BTCrear = findViewById(R.id.BTCrearR);
        BTFoto = findViewById(R.id.BTFotoR);
    }
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
                TVFecha.setText(fecha);

            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }
    private void cargarImagen(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion..."),10);

    }

    private void convertirImagen(){
        BitmapDrawable drawable = (BitmapDrawable) imagen1.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        mViewModel.guardaFoto(b);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            Uri path=data.getData();
            imagen1.setImageURI(path);
            convertirImagen();
        }
    }
}