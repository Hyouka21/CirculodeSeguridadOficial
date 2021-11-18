package com.sosa.circulodeseguridadoficial.utilidades;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.sosa.circulodeseguridadoficial.ui.login.Login;

public class Alerta extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Esta seguro de cerrar sesion?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sp = getActivity().getApplicationContext().getSharedPreferences("datos",0);
                        SharedPreferences.Editor ed= sp.edit();
                        ed.clear();
                        ed.commit();
                        Intent intent = new Intent(getActivity(), Login.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }
}