package com.sosa.circulodeseguridadoficial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.sosa.circulodeseguridadoficial.databinding.ActivityMainBinding;
import com.sosa.circulodeseguridadoficial.entidades.UsuarioDto;
import com.sosa.circulodeseguridadoficial.servicios.ServicioLocalizacion;
import com.sosa.circulodeseguridadoficial.utilidades.Alerta;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mViewModel;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private  Intent intentLocalizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        iniciarHeader();
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_slideshow,R.id.nav_grupo)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        binding.navView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Alerta alerta = new Alerta();
                alerta.show(getSupportFragmentManager(),"about");
                return true;

            }
        });
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    private void iniciarHeader(){
        NavigationView navigationView = binding.navView;
        View header = navigationView.getHeaderView(0);


        mViewModel.actualizarPerfil();
        mViewModel.getUsuario().observe(this, new Observer<UsuarioDto>() {
            @Override
            public void onChanged(UsuarioDto usuarioDto) {
                ImageView avatar = header.findViewById(R.id.ImgAvatar);
                TextView nombre = header.findViewById(R.id.TVNombre);
                TextView correo = header.findViewById(R.id.TVCorreo);
                Glide.with(getApplicationContext())//contexto
                        .load(usuarioDto.getAvatar())//url de la imagen
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)// guarda en el cache
                        .into(avatar); // se encarga de setear la imagen
                nombre.setText(usuarioDto.getNickName());
                correo.setText(usuarioDto.getEmail());

               // iniciarServicio()
            }
        });

    }
public void iniciarServicio(){
    SharedPreferences sp = getApplicationContext().getSharedPreferences("datos",0);
    String token = sp.getString("token","-1");
    intentLocalizacion= new Intent(getApplicationContext(), ServicioLocalizacion.class);
    intentLocalizacion.putExtra("token",token);
    getApplicationContext().startService(intentLocalizacion);
}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(intentLocalizacion!=null) {
            getApplicationContext().stopService(intentLocalizacion);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}