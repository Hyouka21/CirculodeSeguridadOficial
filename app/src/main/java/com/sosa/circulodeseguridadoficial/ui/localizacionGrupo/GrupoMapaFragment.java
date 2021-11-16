package com.sosa.circulodeseguridadoficial.ui.localizacionGrupo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sosa.circulodeseguridadoficial.R;
import com.sosa.circulodeseguridadoficial.entidades.LocalizacionUsuario;
import com.sosa.circulodeseguridadoficial.entidades.dto.Coordenada;
import com.sosa.circulodeseguridadoficial.entidades.dto.IdentificadorDto;
import com.sosa.circulodeseguridadoficial.ui.login.LoginViewModel;

import java.util.List;

public class GrupoMapaFragment extends Fragment {
    private GrupoMapaFragmentViewModel fragmentViewModel;
    private OnMapReadyCallback callback;
    private Thread hilo ;
    private GoogleMap googleMapD;
    private SupportMapFragment mapFragment;
    private LatLng latlogEvento;

    private ActionMode actionMode;
    private ActionMode actionModeUsu;

    private IdentificadorDto ident;
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();

            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.crearEventoItem:

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("localiza",new Coordenada(""+latlogEvento.latitude ,""+latlogEvento.longitude));
                    bundle.putSerializable("ident",ident);
                    Navigation.findNavController(getView()).navigate(R.id.crearEventoFragment,bundle);
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };
    private ActionMode.Callback actionModeCallbackUsuario ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        fragmentViewModel = new ViewModelProvider(this).get(GrupoMapaFragmentViewModel.class);
        fragmentViewModel.getIdentificador().observe(getViewLifecycleOwner(), new Observer<IdentificadorDto>() {
            @Override
            public void onChanged(IdentificadorDto identificadorDto) {
                ident=identificadorDto;
                hilo(identificadorDto);


            }
        });


        fragmentViewModel.getLocalizacion().observe(getViewLifecycleOwner(), new Observer<List<LocalizacionUsuario>>() {
            @Override
            public void onChanged(List<LocalizacionUsuario> localizacionUsuarios) {


                    cargarLocalizaciones(localizacionUsuarios);
                    cargarMenu(localizacionUsuarios);
                    }



        });


        fragmentViewModel.setIdentificador(getArguments());
        return inflater.inflate(R.layout.fragment_grupo_mapa, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        callbackMap();
        mapFragment.getMapAsync(callback);
        getActivity().findViewById(R.id.buscarUsu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionModeUsu = getActivity().startActionMode(actionModeCallbackUsuario);
            }
        });
        hilo.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        hilo.interrupt();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hilo.interrupt();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void hilo(IdentificadorDto identificadorDto){
        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {

                        fragmentViewModel.obtenerLocalizaciones(identificadorDto);

                        Thread.sleep(20000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
    }
    public void callbackMap() {
        callback = new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latLng = null;
                googleMapD = googleMap;
                googleMapD.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(@NonNull LatLng latLng) {
                        // aqui iria la inflacion del menu
                        latlogEvento = latLng;
                        // Start the CAB using the ActionMode.Callback defined above
                        actionMode = getActivity().startActionMode(actionModeCallback);


                    }
                });
            }
        };
    }
    public void cargarLocalizaciones(List<LocalizacionUsuario> localizacionUsuarios){
        googleMapD.clear();
        for(LocalizacionUsuario locUsu : localizacionUsuarios)
        {

//            latLng= new LatLng(locUsu.getCoordenadaX(), locUsu.getCoordenadaY());
            Glide.with(getActivity())
                    .asBitmap()
                    .load(locUsu.getUrlAvatar())
                    .apply(new RequestOptions().override(100, 100))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            googleMapD.addMarker(new MarkerOptions().position(new LatLng(locUsu.getCoordenadaX(), locUsu.getCoordenadaY()))
                                    .title(locUsu.getNickName()).icon(BitmapDescriptorFactory.fromBitmap(resource)));
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            googleMapD.addMarker(new MarkerOptions().position(new LatLng(locUsu.getCoordenadaX(), locUsu.getCoordenadaY()))
                                    .title(locUsu.getNickName()));
                        }
                    });



        }


    }
    public void cargarMenu(List<LocalizacionUsuario> localizacionUsuarios) {

        actionModeCallbackUsuario = new ActionMode.Callback() {

            // Called when the action mode is created; startActionMode() was called
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate a menu resource providing context menu items
                MenuInflater inflater = mode.getMenuInflater();
                for (LocalizacionUsuario locUsu : localizacionUsuarios) {
                    Glide.with(getActivity())
                            .asBitmap()
                            .load(locUsu.getUrlAvatar())
                            .apply(new RequestOptions().override(100, 100))
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    Drawable drawable = new BitmapDrawable(getResources(), resource);
                                    menu.add("usuario").setTitle(locUsu.getNickName()).setIcon(drawable).getItemId();
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                 //   menu.add("usuario").setTitle(locUsu.getNickName()).setIcon(placeholder).getItemId();
                                }

                                @Override
                                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                    super.onLoadFailed(errorDrawable);
                                    menu.add("usuario").setTitle(locUsu.getNickName()).setIcon(R.drawable.juan).getItemId();
                                }
                            });


                }
                inflater.inflate(R.menu.usuariosbuscarmenu, menu);
                return true;
            }

            // Called each time the action mode is shown. Always called after onCreateActionMode, but
            // may be called multiple times if the mode is invalidated.
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false; // Return false if nothing is done
            }

            // Called when the user selects a contextual menu item
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                for (LocalizacionUsuario locUsu : localizacionUsuarios) {

                    if (item.getTitle() == locUsu.getNickName()) {

                        CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(locUsu.getCoordenadaX(), locUsu.getCoordenadaY()));
                        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
                        googleMapD.moveCamera(center);
                        googleMapD.animateCamera(zoom);


                        return true;

                    }




                }

                return false;
            }

                // Called when the user exits the action mode
                @Override
                public void onDestroyActionMode (ActionMode mode){
                    actionMode = null;
                }
            };


        }

}