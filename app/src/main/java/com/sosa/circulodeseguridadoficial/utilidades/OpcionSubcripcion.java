package com.sosa.circulodeseguridadoficial.utilidades;

import com.sosa.circulodeseguridadoficial.entidades.Grupo;
import com.sosa.circulodeseguridadoficial.entidades.Subscripcion;

public interface OpcionSubcripcion {
     void subscribirme(Grupo grupo);
     void desubscribirme(Grupo grupo);
}
